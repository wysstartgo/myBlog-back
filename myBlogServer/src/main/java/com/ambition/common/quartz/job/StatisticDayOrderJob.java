//package com.ambition.common.quartz.job;
//
//import com.ambition.business.domain.SysGroup;
//import com.ambition.business.service.IMedicineOrderStatisticDayService;
//import com.ambition.business.service.IMedicineOutserviceOrderStatisticDayService;
//import com.ambition.business.service.ISysGroupService;
//import com.ambition.common.constants.Constants;
//import com.ambition.common.quartz.base.AStandardBaseJob;
//import com.ambition.common.util.DateUtil;
//import org.apache.commons.collections.CollectionUtils;
//import org.quartz.DisallowConcurrentExecution;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.List;
//
///**
// * <pre>
// *
// *
// * @title: StatisticDayOrderJob
// * @description:
// * @company:
// * @author: wuys
// * @datetime: 2019-09-10 17:14
// * </pre>
// */
//@Component
//@DisallowConcurrentExecution
//public class StatisticDayOrderJob extends AStandardBaseJob  {
//
//	private static final Logger LOG = LoggerFactory.getLogger(StatisticDayOrderJob.class);
//
//
//	@Value("${static_day_task_cronkey}")
//	private String cronKey;
//
//	@Value("${static_day_task_cronvalue}")
//	private String cronValue;
//
//	@Value("${static_day_task_validsign}")
//	private Integer validSign;
//
//	@Resource
//	private ISysGroupService sysGroupService;
//
//	@Resource
//	private IMedicineOrderStatisticDayService medicineOrderStatisticDayService;
//
//	@Resource
//	private IMedicineOutserviceOrderStatisticDayService medicineOutserviceOrderStatisticDayService;
//
//
//	@Override
//	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//		//统计当天的订单信息 medicine_order表中的药品订单详情信息
//		List<SysGroup> sysGroupList = sysGroupService.getSysGroupList();
//		if (CollectionUtils.isNotEmpty(sysGroupList)){
//			LOG.info("=======================================");
//			LOG.info("======执行当天订单信息统计任务======");
//			LOG.info("=======================================");
//			Date now = new Date();
//			Date startDate = DateUtil.beginOfDate(now);
//			Date endDate = DateUtil.endOfDate(now);
//			sysGroupList.forEach(sysGroup -> {
//				medicineOrderStatisticDayService.deleteStatisticByDate(sysGroup.getId(),startDate,endDate);
//				medicineOrderStatisticDayService.doStatisticDay(sysGroup.getId(),startDate);
//				medicineOutserviceOrderStatisticDayService.deleteStatisticByDate(sysGroup.getId(),startDate,endDate);
//				medicineOutserviceOrderStatisticDayService.doStatisticDay(sysGroup.getId(),startDate);
//			});
//		}
//	}
//
//
//	@Override
//	public String initAppId() {
//		return Constants.APP_ID;
//	}
//
//	@Override
//	public String initCronKey() {
//		return this.cronKey;
//	}
//
//	@Override
//	public String initCronValue() {
//		return this.cronValue;
//	}
//
//	@Override
//	public int initValidSign() {
//		return this.validSign;
//	}
//}
