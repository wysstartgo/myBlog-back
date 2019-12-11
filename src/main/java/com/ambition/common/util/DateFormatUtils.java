/**
 *
 */
package com.ambition.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @Description
 * @author 张国栋
 * @date 2017年7月5日 下午2:02:53
 */
public class DateFormatUtils {

    public static final SimpleDateFormat SDF_HHMM = new SimpleDateFormat("HH:mm");

    public static final SimpleDateFormat SDFY_M_DHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat SPECIAL_M_DHMS = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    public static final SimpleDateFormat SDFY_M_DHM = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final SimpleDateFormat SDFYMDHMS = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final SimpleDateFormat SDFYMDHM = new SimpleDateFormat("yyyyMMddHHmm");

    public static final SimpleDateFormat SDFYMD = new SimpleDateFormat("yyyyMMdd");

    public static final SimpleDateFormat SDFYM = new SimpleDateFormat("yyyyMM");

    public static final SimpleDateFormat SDFMD = new SimpleDateFormat("MMdd");

    public static final SimpleDateFormat SDF_YM = new SimpleDateFormat("yyyy-MM");

    public static final SimpleDateFormat SDF_YMD = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat sdfymdhmsSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static final SimpleDateFormat UTC_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");

    public static final SimpleDateFormat CRON_FORMAT = new SimpleDateFormat("ss mm HH dd MM ? yyyy");



    public static final String INITIAL_DATE = "1970-01-01";


    public static Date parseUTCDate(String date) throws ParseException {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        synchronized (UTC_FORMAT) {
            date = date.replace("Z", " UTC");
            return UTC_FORMAT.parse(date);
        }
    }

    public static String formatSdfymdhmsSSS(Date date) {
        if (date == null) {
            return null;
        }

        synchronized (sdfymdhmsSSS) {
            return sdfymdhmsSSS.format(date);
        }
    }

    public static Date getInitialDate() throws ParseException {
        return SDFYMD.parse(INITIAL_DATE);
    }

    public static Date parseSdfymdhmsSSS(String dateStr) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        synchronized (sdfymdhmsSSS) {
            return sdfymdhmsSSS.parse(dateStr);
        }
    }

    public static String formatYm(Date date) {
        if (date == null) {
            return null;
        }

        synchronized (SDFYM) {
            return SDFYM.format(date);
        }
    }

    public static String formatY_m_dhms(Date date) {
        if (date == null) {
            return null;
        }

        synchronized (SDFY_M_DHMS) {
            return SDFY_M_DHMS.format(date);
        }
    }

    public static String formatSpecialY_m_dhms(Date date) {
        if (date == null) {
            return null;
        }

        synchronized (SPECIAL_M_DHMS) {
            return SPECIAL_M_DHMS.format(date);
        }
    }

    public static String formatYmdHms(Date date) {
        if (date == null) {
            return null;
        }

        synchronized (SDFYMDHMS) {
            return SDFYMDHMS.format(date);
        }
    }

    public static String formatYmdHm(Date date) {
        if (date == null) {
            return null;
        }

        synchronized (SDFYMDHM) {
            return SDFYMDHM.format(date);
        }
    }

    public static Date parseY_m_dhms(String dateStr) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        synchronized (SDFY_M_DHMS) {
            return SDFY_M_DHMS.parse(dateStr);
        }
    }

    public static Date parseSpecialY_m_dhms(String dateStr) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        synchronized (SPECIAL_M_DHMS) {
            return SPECIAL_M_DHMS.parse(dateStr);
        }
    }

    public static Date parse_YmdHm(String dateStr) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        synchronized (SDFY_M_DHM) {
            return SDFY_M_DHM.parse(dateStr);
        }
    }

    public static Date parseYmdHms(String dateStr) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        synchronized (SDFYMDHMS) {
            return SDFYMDHMS.parse(dateStr);
        }
    }

    public static Date parseYmdHm(String dateStr) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        synchronized (SDFYMDHM) {
            return SDFYMDHM.parse(dateStr);
        }
    }

    public static Date parseYmd(String dateStr) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        synchronized (SDFYMD) {
            return SDFYMD.parse(dateStr);
        }
    }

    public static String formatYmd(Date date) {
        if (date == null) {
            return null;
        }

        synchronized (SDFYMD) {
            return SDFYMD.format(date);
        }
    }

    public static Date parse_Ymd(String dateStr) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        synchronized (SDF_YMD) {
            return SDF_YMD.parse(dateStr);
        }
    }


    public static String format_Ymd(Date date) {
        if (date == null) {
            return null;
        }

        synchronized (SDF_YMD) {
            return SDF_YMD.format(date);
        }
    }

    public static String format_Hhmm(Date date) {
        if (date == null) {
            return null;
        }

        synchronized (SDF_HHMM) {
            return SDF_HHMM.format(date);
        }
    }

    public static String format_YmdHm(Date date){
        if (date == null){
            return null;
        }
        synchronized (SDFY_M_DHM){
            return SDFY_M_DHM.format(date);
        }
    }

    public static Date parseMd(String dateStr) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        synchronized (SDFMD) {
            return SDFMD.parse(dateStr);
        }
    }

    public static String formatMd(Date date) {
        if (date == null) {
            return null;
        }

        synchronized (SDFMD) {
            return SDFMD.format(date);
        }
    }

    public static Date parse_Ym(String dateStr) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        synchronized (SDF_YM) {
            return SDF_YM.parse(dateStr);
        }
    }

    public static String format_Ym(Date date) {
        if (date == null) {
            return null;
        }

        synchronized (SDF_YM) {
            return SDF_YM.format(date);
        }
    }

    public static String format_cron(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        StringBuilder sb = new StringBuilder();
        LocalTime localTime = localDateTime.toLocalTime();
        LocalDate localDate = localDateTime.toLocalDate();
        sb.append(localTime.getSecond()).append(" ").append(localTime.getMinute()).append(" ").append(localTime.getHour())
                .append(" ").append(localDate.getDayOfMonth()).append(" ").append(localDate.getMonthValue()).append(" ").append("?");
        return sb.toString();
    }


    /**
     * 获取到秒数
     * @param date
     * @return
     * @throws ParseException
     */
    public static Long dateToStartTimeSecs(Date date) throws ParseException {
        if (date == null) {
            return null;
        }
        String time = format_Ymd(date);
        return parse_Ymd(time).getTime() / 1000;
    }

    /**
     * 获取到秒数
     * @param date
     * @return
     * @throws ParseException
     */
    public static Long dateToEndTimeSecs(Date date) throws ParseException {
        if (date == null) {
            return null;
        }

        String time = format_Ymd(addDay(1, date));
        return (parse_Ymd(time).getTime() / 1000) - 1;
    }

    /**
     *
     * @param year
     * @return
     */
    public static Date addYear(int year, Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    public static Date addMonth(int month, Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static Date addDay(int day, Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    public static Date addMinute(int minute, Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date addWeek(int week, Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.WEEK_OF_YEAR, week);
        return calendar.getTime();
    }

    public static void main(String[] args) throws ParseException {

        //System.out.println(stringToDate("201905071503"));
    }
}
