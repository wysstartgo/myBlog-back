package com.ambition.common.keygen;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * snowflake算法一个很大的优点就是不需要依赖任何第三方组件，笔者想继续保留这个优点：毕竟多一个依赖的组件，多一个风险，并增加了系统的复杂性。
 * snowflake算法给workerId预留了10位，即workId的取值范围为[0,
 * 1023]，事实上实际生产环境不大可能需要部署1024个分布式ID服务，所以：将workerId取值范围缩小为[0, 511]，[512,
 * 1023]这个范围的workerId当做备用workerId。workId为0的备用workerId是512，workId为1的备用workerId是513，以此类推……
 * 
 * 说明：如果你的业务真的需要512个以上分布式ID服务才能满足需求，那么不需要继续往下看了，这个方案不适合你^^；
 * 
 * 核心优化代码在方法tryGenerateKeyOnBackup()中，BACKUP_COUNT即备份workerId数越多，sequence服务避免时钟回拨影响的能力越强，但是可部署的sequence服务越少，设置BACKUP_COUNT为3，最多可以部署1024/(3+1)即256个sequence服务，完全够用，抗时钟回拨影响的能力也得到非常大的保障。
 *
 *
 * 
 * 参考：https://www.jianshu.com/p/98c202f64652?utm_campaign=haruki&utm_content=note&utm_medium=reader_share&utm_source=weixin
 */
public final class CommonKeyGenerator implements KeyGenerator {

    private static final Logger log = LoggerFactory.getLogger(CommonKeyGenerator.class);

    public static final CommonKeyGenerator INSTANCE = new CommonKeyGenerator();

    private static final long EPOCH;

    private static final long SEQUENCE_BITS = 12L;

    private static final long WORKER_ID_BITS = 10L;

    private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;

    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

    /**
     * 每台workerId服务器有3个备份workerId, 备份workerId数量越多, 可靠性越高, 但是可部署的sequence ID服务越少
     */
    private static final long BACKUP_COUNT = 3;

    /**
     * 实际的最大workerId的值<br/>
     * workerId原则上上限为1024, 但是需要为每台sequence服务预留BACKUP_AMOUNT个workerId,
     */
    private static final long WORKER_ID_MAX_VALUE = (1L << WORKER_ID_BITS) / (BACKUP_COUNT + 1);

    /**
     * 目前用户生成ID的workerId
     */
    private static long workerId;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.NOVEMBER, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // EPOCH是服务器第一次上线时间点, 设置后不允许修改
        EPOCH = calendar.getTimeInMillis();
    }

    private long sequence;

    private long lastTime;

    /**
     * 保留workerId和lastTime, 以及备用workerId和其对应的lastTime
     */
    private static Map<Long, Long> workerIdLastTimeMap = new ConcurrentHashMap<>();

    static {
        // 初始化workerId和其所有备份workerId与lastTime
        // 假设workerId为0且BACKUP_AMOUNT为4, 那么map的值为: {0:0L, 256:0L, 512:0L,
        // 768:0L}
        // 假设workerId为2且BACKUP_AMOUNT为4, 那么map的值为: {2:0L, 258:0L, 514:0L,
        // 770:0L}
        for (int i = 0; i <= BACKUP_COUNT; i++) {
            workerIdLastTimeMap.put(workerId + (i * WORKER_ID_MAX_VALUE), 0L);
        }
        System.out.println("workerIdLastTimeMap:" + workerIdLastTimeMap);
    }

    /**
     * 最大容忍时间, 单位毫秒, 即如果时钟只是回拨了该变量指定的时间, 那么等待相应的时间即可; 考虑到sequence服务的高性能, 这个值不易过大
     */
    private static final long MAX_BACKWARD_MS = 3;

    /**
     * Set work process id.
     * 
     * @param workerId
     *            work process id
     */
    public static void setWorkerId(final long workerId) {
        Preconditions.checkArgument(workerId >= 0L && workerId < WORKER_ID_MAX_VALUE);
        CommonKeyGenerator.workerId = workerId;
    }

    /**
     * Generate key. 考虑时钟回拨, 与sharding-jdbc源码的区别就在这里</br>
     * 缺陷: 如果连续两次时钟回拨, 可能还是会有问题, 但是这种概率极低极低
     * 
     * @return key type is @{@link Long}.
     * @Author 阿飞
     */
    @Override
    public synchronized long generateKey() {
        long currentMillis = System.currentTimeMillis();

        // 当发生时钟回拨时
        if (lastTime > currentMillis) {
            // 如果时钟回拨在可接受范围内, 等待即可
            if (lastTime - currentMillis < MAX_BACKWARD_MS) {
                try {
                    Thread.sleep(lastTime - currentMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                tryGenerateKeyOnBackup(currentMillis);
            }
        }

        // 如果和最后一次请求处于同一毫秒, 那么sequence+1
        if (lastTime == currentMillis) {
            if (0L == (sequence = ++sequence & SEQUENCE_MASK)) {
                currentMillis = waitUntilNextTime(currentMillis);
            }
        } else {
            // 如果是一个更近的时间戳, 那么sequence归零
            sequence = 0;
        }

        lastTime = currentMillis;
        // 更新map中保存的workerId对应的lastTime
        workerIdLastTimeMap.put(CommonKeyGenerator.workerId, lastTime);

        if (log.isDebugEnabled()) {
            log.debug("{}-{}-{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(lastTime)), workerId,
                    sequence);
        }

        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(lastTime)) + " -- "
                + workerId + " -- " + sequence + " -- " + workerIdLastTimeMap);
        return ((currentMillis - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS)
                | sequence;
    }

    /**
     * 尝试在workerId的备份workerId上生成
     * 
     * @param currentMillis
     *            当前时间
     */
    private long tryGenerateKeyOnBackup(long currentMillis) {
        System.out.println("try GenerateKey OnBackup, map:" + workerIdLastTimeMap);

        // 遍历所有workerId(包括备用workerId, 查看哪些workerId可用)
        for (Map.Entry<Long, Long> entry : workerIdLastTimeMap.entrySet()) {
            CommonKeyGenerator.workerId = entry.getKey();
            // 取得备用workerId的lastTime
            Long tempLastTime = entry.getValue();
            lastTime = tempLastTime == null ? 0L : tempLastTime;

            // 如果找到了合适的workerId
            if (lastTime <= currentMillis) {
                return lastTime;
            }
        }

        // 如果所有workerId以及备用workerId都处于时钟回拨, 那么抛出异常
        throw new IllegalStateException("Clock is moving backwards, current time is " + currentMillis
                + " milliseconds, workerId map = " + workerIdLastTimeMap);
    }

    private long waitUntilNextTime(final long lastTime) {
        long time = System.currentTimeMillis();
        while (time <= lastTime) {
            time = System.currentTimeMillis();
        }
        return time;
    }
}