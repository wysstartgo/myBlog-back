//package com.ambition.common.quartz;
//
//import com.ambition.business.domain.QuartzEntity;
//import com.ambition.business.service.IQrtzJobDetailsService;
//import com.ambition.common.constants.Constants;
//import com.ambition.common.quartz.base.AStandardBaseJob;
//import org.apache.commons.lang3.StringUtils;
//import org.quartz.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.BeanFactoryUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Map;
//
///**
// * 初始化一个测试Demo任务
// * 创建者
// * 创建时间	2018年4月3日
// */
//@Component
//public class TaskRunner implements ApplicationRunner {
//
//	private final static Logger LOGGER = LoggerFactory.getLogger(TaskRunner.class);
//
//	@Autowired
//	private IQrtzJobDetailsService jobService;
//
//	@Autowired
//	private Scheduler scheduler;
//
//	@Resource
//	private ApplicationContext applicationContext;
//
//	@SuppressWarnings({"rawtypes", "unchecked"})
//	@Override
//	public void run(ApplicationArguments var) throws Exception {
//		Long count = jobService.countQuartzEntity();
//		if (count == 0) {
//			LOGGER.info("初始化定时任务");
//			Map<String, AStandardBaseJob> beanMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
//					AStandardBaseJob.class);
//			LOGGER.info("【task扫描注册已启动】-共找到task数为[{}]", beanMap != null ? beanMap.size() : 0);
//			beanMap.keySet().forEach(key -> {
//				AStandardBaseJob aStandardBaseJob = beanMap.get(key);
//				String className = aStandardBaseJob.getClass().getName();
//				LOGGER.info("[------------扫描到的任务的名称为:" + className + "----------------------]");
//			});
//			if (beanMap != null && beanMap.size() > 0) {
//				beanMap.forEach((key, value) -> {
//					if (!this.baseCheck(value)) {
//						return;
//					}
//					QuartzEntity quartz = new QuartzEntity();
//					quartz.setJobName(value.initCronKey());
//					quartz.setJobGroup(value.getClass().getSimpleName());
//					quartz.setDescription(value.getClass().getName());
//					quartz.setJobClassName(value.getClass().getName());
//					quartz.setCronExpression(value.initCronValue());
////					Class cls = Class.forName(quartz.getJobClassName());
////					cls.newInstance();
//					//构建job信息
//					JobDetail job = JobBuilder.newJob(value.getClass()).withIdentity(quartz.getJobName(),
//							quartz.getJobGroup())
//							.withDescription(quartz.getDescription()).build();
//					// 触发时间点
//					CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression());
//					Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger" + quartz.getJobName(), quartz.getJobGroup())
//							.startNow().withSchedule(cronScheduleBuilder).build();
//					//交由Scheduler安排触发
//					try {
//						scheduler.scheduleJob(job, trigger);
//					} catch (SchedulerException e) {
//						LOGGER.error("定时任务出错!",e);
//					}
//				});
//			}
//
//		}
//	}
//
//	/**
//	 * 基本校验
//	 *
//	 * @param aTaskStandardJob
//	 * @return
//	 * @Description
//	 * @author 唐宋
//	 */
//	public boolean baseCheck(AStandardBaseJob aTaskStandardJob) {
//		if (StringUtils.isBlank(aTaskStandardJob.initAppId())) {
//			LOGGER.error("【task扫描注册出现异常】-类[{}]-[appId]-不能为空", aTaskStandardJob.getClass());
//			return false;
//		}
//
//		if (StringUtils.isBlank(aTaskStandardJob.initCronKey())) {
//			LOGGER.error("【task扫描注册出现异常】-类[{}]-[CronKey]-不能为空", aTaskStandardJob.getClass());
//			return false;
//		}
//
//		if (StringUtils.isBlank(aTaskStandardJob.initCronValue())) {
//			LOGGER.error("【task扫描注册出现异常】-类[{}]-[CronValue]-不能为空", aTaskStandardJob.getClass());
//			return false;
//		}
//
//		if (aTaskStandardJob.initValidSign() == Constants.VALID_SIGN_N) {
//			LOGGER.info("【task扫描注册】-类[{}]-[valid_sign]-为无效", aTaskStandardJob.getClass());
//			return false;
//		}
//		return true;
//	}
//
//}