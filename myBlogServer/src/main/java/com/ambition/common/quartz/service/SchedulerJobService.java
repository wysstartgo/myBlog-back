package com.ambition.common.quartz.service;//package com.ambition.common.quartz.service;
//
//import com.ambition.common.quartz.base.AStandardBaseJob;
//import org.apache.commons.lang3.StringUtils;
//import org.quartz.JobDataMap;
//import org.quartz.SchedulerException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.BeanFactoryUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
//import org.springframework.scheduling.quartz.JobDetailFactoryBean;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.text.ParseException;
//import java.util.Date;
//import java.util.Map;
//
//@Service
//public class SchedulerJobService {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerJobService.class);
//
//    @Autowired
//    private SchedulerFactoryBean schedulerFactoryBean;
//
//
//    @Resource
//    private ApplicationContext applicationContext;
//
//    @PostConstruct
//    public void setupJobs() throws ParseException, SchedulerException {
//        Map<String, AStandardBaseJob> beanMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
//                AStandardBaseJob.class);
//        LOGGER.info("【task扫描注册已启动】-共找到task数为[{}]", beanMap != null ? beanMap.size() : 0);
//        beanMap.keySet().forEach(key -> {
//            AStandardBaseJob aStandardBaseJob = beanMap.get(key);
//            String className = aStandardBaseJob.getClass().getName();
//            LOGGER.info("[------------扫描到的任务的名称为:" + className + "----------------------]");
//        });
//        if (beanMap != null && beanMap.size() > 0) {
//            beanMap.forEach((key, value) -> {
//                if (!this.baseCheck(value)) {
//                    return;
//                }
//                try {
//                    scheduleJob(value.getClass(), null, value.initCronValue(), StringBuilderHolder.getGlobal().
//                                    append(value.initAppId()).append("-").append(value.initCronKey()).toString(),
//                            value.initCronKey());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                } catch (SchedulerException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//        // scheduleJob(ContentStatisticsJob.class, null, "1/1 * * * * ?",
//        // "exampleJob", "trigger1");
//    }
//
//    public Date scheduleJob(Class<? extends QuartzJobBean> object, JobDataMap jMap, String frequency, String jobName,
//							String triggerName) throws ParseException, SchedulerException {
//        JobDetailFactoryBean jobDetailFactoryBean = schedulerJobFactory.job(object, jMap, jobName);
//        CronTriggerFactoryBean cronTriggerFactoryBean = schedulerTriggerFactory
//                .jobTrigger(jobDetailFactoryBean.getObject(), frequency, triggerName);
//        return schedulerFactoryBean.getScheduler().scheduleJob(jobDetailFactoryBean.getObject(),
//                cronTriggerFactoryBean.getObject());
//    }
//
//    /**
//     * 基本校验
//     *
//     * @Description
//     * @author 唐宋
//     * @param aTaskStandardJob
//     * @return
//     */
//    public boolean baseCheck(AStandardBaseJob aTaskStandardJob) {
//        if (StringUtils.isBlank(aTaskStandardJob.initAppId())) {
//            LOGGER.error("【task扫描注册出现异常】-类[{}]-[appId]-不能为空", aTaskStandardJob.getClass());
//            return false;
//        }
//
//        if (StringUtils.isBlank(aTaskStandardJob.initCronKey())) {
//            LOGGER.error("【task扫描注册出现异常】-类[{}]-[CronKey]-不能为空", aTaskStandardJob.getClass());
//            return false;
//        }
//
//        if (StringUtils.isBlank(aTaskStandardJob.initCronValue())) {
//            LOGGER.error("【task扫描注册出现异常】-类[{}]-[CronValue]-不能为空", aTaskStandardJob.getClass());
//            return false;
//        }
//
//        if (aTaskStandardJob.initValidSign() == BaseGlobalConstants.VALID_SIGN_N) {
//            LOGGER.info("【task扫描注册】-类[{}]-[valid_sign]-为无效", aTaskStandardJob.getClass());
//            return false;
//        }
//        return true;
//    }
//
//}