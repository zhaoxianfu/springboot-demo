package com.redis.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @ClassName:DateTimeUtil
 * @Despriction: 操作时间工具类
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/17  21:26
 * @Version1.0
 **/

@Slf4j
public class DateTimeUtil {

    /**
     * 默认的日期格式
     */
    public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String DATE_PATTERN_NOT_SPLIT = "yyyyMMdd";

    public static final String DATE_PATTERN_YEAR = "yyyy";

    public static final String DATE_NUMBER_PATTERN = "yyyyMMddHHmmss";

    public static final String DATE_TIME_PATTERN_MM = "yyyy-MM-dd HH:mm";

    /**
     * 获取昨天的开始时间
     */
    public static Date getYesterdayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return getStartTimeOfDay(calendar);
    }

    /**
     * 获取指定时间内昨天的开始时间
     */
    public static Date getYesterdayStartByParams(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return getStartTimeOfDay(calendar);
    }

    /**
     * 获取指定时间内昨天的结束时间
     */
    public static Date getYesterdayEndByParams(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return getEndTimeOfDay(calendar);
    }

    /**
     * 获取指定时间+1（明天）的开始时间
     */
    public static Date getTomorrowStartByParam(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return getStartTimeOfDay(calendar);
    }

    /**
     * 获取参数:date的开始时间
     */
    public static Date getStartTimeByParam(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getStartTimeOfDay(calendar);
    }

    /**
     * 获取参数:date的结束时间
     */
    public static Date getEndTimeByParam(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getEndTimeOfDay(calendar);
    }

    /**
     * 获取昨天的结束时间
     *
     * @return
     */
    public static Date getYesterdayEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return getEndTimeOfDay(calendar);
    }

    /**
     * 获取本周的开始时间
     *
     * @return
     */
    public static Date getThisWeekStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getStartTimeOfDay(calendar);
    }

    /**
     * 获取本周的结束时间
     *
     * @return
     */
    public static Date getThisWeekEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return getEndTimeOfDay(calendar);
    }

    /**
     * 获取上周的开始时间
     *
     * @return
     */
    public static Date getLastWeekStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getStartTimeOfDay(calendar);
    }

    /**
     * 获取上周的结束时间
     *
     * @return
     */
    public static Date getLastWeekEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return getEndTimeOfDay(calendar);
    }

    /**
     * 获取当月的开始时间
     *
     * @return
     */
    public static Date getThisMonthStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getStartTimeOfDay(calendar);
    }

    /**
     * 获取当月的结束时间
     *
     * @return
     */
    public static Date getThisMonthEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return getEndTimeOfDay(calendar);
    }

    /**
     * 获取上月的开始时间
     *
     * @return
     */
    public static Date getLastMonthStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getStartTimeOfDay(calendar);
    }

    /**
     * 获取上月的结束时间
     *
     * @return
     */
    public static Date getLastMonthEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return getEndTimeOfDay(calendar);
    }

    /**
     * 获取一天的开始时间
     *
     * @return
     */
    public static Date getStartTimeOfDay(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取一天的结束时间
     *
     * @return
     */
    public static Date getEndTimeOfDay(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59,
                59);
        return calendar.getTime();
    }

    /**
     * 将一个日期增加count天
     *
     * @param date
     * @param count
     * @return
     */
    public static Date addDate(Date date, int count) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, count);
        return cal.getTime();
    }

    /**
     * 将一个日期增加count月
     *
     * @param date
     * @param count
     * @return
     */
    public static Date addMonth(Date date, int count) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, count);
        return cal.getTime();
    }

    /**
     * 将日期格式化成一个字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFm = new SimpleDateFormat(pattern);
        String dateTime = dateFm.format(date);
        return dateTime;
    }

    /**
     * 功能描述: 将日期转换成默认格式字符串<br>
     */
    public static String dateString(Date date) {
        return dateString(date, DEFAULT_TIME_PATTERN);
    }

    /**
     * 功能描述: 将毫秒转换成日期<br>
     *
     * @param millis 毫秒数
     */
    public static Date convert2Date(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.getTime();
    }

    /**
     * 计算两个日期之间相差月数 （date1- date2）
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int monthsBetween(Date date1, Date date2) {
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(date1);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(date2);
        System.err.println(cal1.get(Calendar.MONTH) + "=====" + cal2.get(Calendar.MONTH));
        return (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH)
                - cal2.get(Calendar.MONTH);
    }


    /**
     * 向上取整
     * 算出既然经过月份
     *
     * @param before
     * @param after
     * @return
     * @throws Exception
     */

    public static int monthsBetweenUp(String before, String after) {
        int betweenMonth = 0;
        Calendar tempDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar beforeDate = Calendar.getInstance();
        Calendar afterDate = Calendar.getInstance();
        try {
            beforeDate.setTime(sdf.parse(before));
            afterDate.setTime(sdf.parse(after));
            tempDate.setTime(sdf.parse(after));
        } catch (ParseException e) {
            log.error("发生异常{}", e.getMessage());
        }
        tempDate.set(Calendar.DAY_OF_MONTH, tempDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        betweenMonth = (afterDate.get(Calendar.YEAR) - beforeDate.get(Calendar.YEAR)) * 12 + afterDate.get(Calendar.MONTH)
                - beforeDate.get(Calendar.MONTH);
        //如果日大于或者相等（直接加1)
        if (afterDate.get(Calendar.DATE) >= beforeDate.get(Calendar.DATE)) {
            betweenMonth = betweenMonth + 1;
            return betweenMonth;
        }
        //是否为最后一天（起始日期）
        if (afterDate.get(Calendar.DATE) == tempDate.get(Calendar.DATE)) {
            betweenMonth = betweenMonth + 1;
        }
        return betweenMonth;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param startDate 较小的时间
     * @param endDate   较大的时间
     * @return 相差天数
     */
    public static long daysBetween(Date startDate, Date endDate) {
        Calendar startC = DateUtils.toCalendar(startDate);
        Calendar endC = DateUtils.toCalendar(endDate);
        startC.set(Calendar.HOUR_OF_DAY, 0);
        startC.set(Calendar.MINUTE, 0);
        startC.set(Calendar.SECOND, 0);
        startC.set(Calendar.MILLISECOND, 0);

        endC.set(Calendar.HOUR_OF_DAY, 0);
        endC.set(Calendar.MINUTE, 0);
        endC.set(Calendar.SECOND, 0);
        endC.set(Calendar.MILLISECOND, 0);

        return (endC.getTimeInMillis() - startC.getTimeInMillis()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 计算两个日期之间相差的分钟
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差分钟
     */
    public static void main(Date smdate, Date bdate) {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // try {
        // smdate = sdf.parse(sdf.format(smdate));
        // bdate = sdf.parse(sdf.format(bdate));
        // } catch (ParseException e) {
        // e.printStackTrace();
        // }
        // Calendar cal = Calendar.getInstance();
        // cal.setTime(smdate);
        // long time1 = cal.getTimeInMillis();
        // cal.setTime(bdate);
        // long time2 = cal.getTimeInMillis();


    }

    public static String getHourMin(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        SimpleDateFormat df = new SimpleDateFormat("kk:mm");
        return df.format(date);
    }

    public static boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能描述: <br>
     * date间隔多少个双休日
     *
     * @param date
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static int countWeekendDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        // TODO 可以加入判断节日方法
        boolean flag = DateTimeUtil.isWeekend(calendar.getTime());
        int count = 0;
        while (flag) {
            count++;
            calendar.add(Calendar.DATE, -1);
            // TODO 可以加入判断节日方法
            flag = DateTimeUtil.isWeekend(calendar.getTime());
        }

        return count;
    }

    /**
     * 功能描述: <br>
     * 〈获取俩个时间的年差〉
     *
     * @param beginDate
     * @param endDate
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static int yearBetween(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            return 0;
        }
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginDate);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);

        return endCalendar.get(Calendar.YEAR) - beginCalendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述: <br>
     * 〈获取小时差〉
     *
     * @param minDate
     * @param maxDate
     */
    public static long getHourDifference(Date minDate, Date maxDate) {
        long hour = (maxDate.getTime() - minDate.getTime()) / (60 * 60 * 1000);
        return hour;
    }

    public static long getMinuteDifference(Date minDate, Date maxDate) {
        long minute = (maxDate.getTime() - minDate.getTime()) / (60 * 1000);
        return minute;
    }

    public static long getMillisecondDifference(Date minDate, Date maxDate) {
        long millisecond = maxDate.getTime() - minDate.getTime();
        return millisecond;
    }

    /**
     * 〈添加小时〉
     */
    public static Date addHour(Date date, int hour) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR_OF_DAY, hour);
        return ca.getTime();
    }

    /**
     * 功能描述: <br>
     * 〈俩时间相减〉
     *
     * @param beginDate
     * @param endDate
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String minusTime(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            return StringUtils.EMPTY;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff = beginDate.getTime() - endDate.getTime();
        String reverted = diff > 0 ? "后" : "前";
        diff = Math.abs(diff);
        if (diff < 3 * 60 * 1000) {
            return "刚刚";
        } else if (diff < 60 * 60 * 1000) {
            return (int) (Math.floor((diff) / 60 / 1000)) + "分钟" + reverted;
        } else if (diff < 24 * 60 * 60 * 1000) {
            return (int) (Math.floor((diff) / 60 / 60 / 1000)) + "小时" + reverted;
        } else if (diff < 10 * 24 * 60 * 60 * 1000L) {
            return (int) (Math.floor((diff) / 24 / 60 / 60 / 1000)) + "天" + reverted;
        } else {
            try {
                return format.format(beginDate);
            } catch (Exception e) {
                log.error("发生异常{}", e.getMessage(), e);
                return StringUtils.EMPTY;
            }
        }
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉获取当前日期三个月前的日期
     *
     * @param date
     * @param amount 负数为指定日期之前的日期，正数为指定日期后的日期
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date getMonthDateBefor(Date date, int amount) {
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(date);// 把当前时间赋给日历
        calendar.add(Calendar.MONTH, amount); // 设置为前N月
        return calendar.getTime(); // 得到前N月的时间
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉获取指定日期N天后的日期
     *
     * @param date   指定日期
     * @param amount N天(amount 负数为指定日期之前的日期，正数为指定日期后的日期)
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date getDateTime(Date date, int amount) {
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(date);// 把当前时间赋给日历
        calendar.add(Calendar.DATE, amount); // 设置为前N天
        return calendar.getTime(); // 得到前N天的时间
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉获取两日期相隔天数
     *
     * @param date
     * @param date2
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static int getDateMinus(Date date, Date date2) {
        Calendar nowDate = Calendar.getInstance(), oldDate = Calendar.getInstance();
        nowDate.setTime(date);
        oldDate.setTime(date2);
        long timeNow = nowDate.getTimeInMillis();
        long timeOld = oldDate.getTimeInMillis();
        long days = (timeNow - timeOld) / (1000 * 60 * 60 * 24);// 化为天
        return (int) days;
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 功能描述: 字符串转为日期【自适应格式】
     *
     * @param dateStr
     * @return
     */
    public static Date strToDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        String pattern = null;
        if (dateStr.length() < DEFAULT_TIME_PATTERN.length()) {
            if (dateStr.indexOf("-") > 0) { // 2016-12-12或2016-12-12 12:34:56等等
                String[] dateStrs = dateStr.split("-");
                if (dateStrs.length == 3) { // 2016-12-12...
                    String yyyy = dateStrs[0], mm = dateStrs[1], ddhh = dateStrs[2];
                    if (ddhh.length() < 3) {
                        pattern = ((yyyy.length() >= 4) ? "yyyy" : "yyyy".substring(0, yyyy.length())) + "-"
                                + ((mm.length() < 2) ? "M" : "MM") + "-" + ((ddhh.length() < 2) ? "d" : "dd");
                    } else {
                        String dd = ddhh.substring(0, 2).trim();
                        pattern = ((yyyy.length() >= 4) ? "yyyy" : "yyyy".substring(0, yyyy.length())) + "-"
                                + ((mm.length() < 2) ? "M" : "MM") + "-" + ((dd.length() < 2) ? "d" : "dd");
                        int idx = ddhh.indexOf(" ");
                        if (idx > 0) {
                            String hhmmss = ddhh.substring(idx + 1);
                            if (!"".equals(hhmmss)) {
                                if (hhmmss.indexOf(":") > 0) { // 12:34或12:34:56
                                    String[] dateStrsTmp = hhmmss.split(":");
                                    if (dateStrsTmp.length == 1) {
                                        String hh = dateStrsTmp[0];
                                        pattern = pattern + " " + ((hh.length() < 2) ? "H" : "HH");
                                    } else if (dateStrsTmp.length == 2) {
                                        String hh = dateStrsTmp[0], m = dateStrsTmp[1];
                                        pattern = pattern + " " + ((hh.length() < 2) ? "H" : "HH") + ":"
                                                + ((m.length() < 2) ? "m" : "mm");
                                    } else if (dateStrsTmp.length == 3) {
                                        String hh = dateStrsTmp[0], m = dateStrsTmp[1], ss = dateStrsTmp[2];
                                        pattern = pattern + " " + ((hh.length() < 2) ? "H" : "HH") + ":"
                                                + ((m.length() < 2) ? "m" : "mm") + ":"
                                                + ((ss.length() < 2) ? "s" : "ss");
                                    }
                                } else if (hhmmss.length() < 3) {
                                    pattern = pattern + " " + ((hhmmss.length() < 2) ? "H" : "HH");
                                }
                            }
                        }
                    }
                } else if (dateStrs.length == 2) { // 2015-12
                    String yyyy = dateStrs[0], mm = dateStrs[1];
                    pattern = ((yyyy.length() >= 4) ? "yyyy" : "yyyy".substring(0, yyyy.length())) + "-"
                            + ((mm.length() < 2) ? "M" : "MM");
                }
            } else if (dateStr.indexOf(":") > 0) { // 12:34或12:34:56
                String[] dateStrs = dateStr.split(":");
                if (dateStrs.length == 1) {
                    String hh = dateStrs[0];
                    pattern = (hh.length() < 2) ? "H" : "HH";
                } else if (dateStrs.length == 2) {
                    String hh = dateStrs[0], mm = dateStrs[1];
                    pattern = ((hh.length() < 2) ? "H" : "HH") + ":" + ((mm.length() < 2) ? "m" : "mm");
                } else if (dateStrs.length == 3) {
                    String hh = dateStrs[0], mm = dateStrs[1], ss = dateStrs[2];
                    pattern = ((hh.length() < 2) ? "H" : "HH") + ":" + ((mm.length() < 2) ? "m" : "mm") + ":"
                            + ((ss.length() < 2) ? "s" : "ss");
                }
            } else if (dateStr.matches("^[0-9]*$")) { // 20161212123456【2016-12-12 12:34:56】
                int length = dateStr.length();
                if (length > 3 && length < 15) { // 从年开始
                    pattern = "yyyyMMddHHmmss".substring(0, length);
                }
            }
        } else {
            pattern = DEFAULT_TIME_PATTERN;
        }
        // System.out.print(dateStr + " ： " + pattern + " ");
        if (pattern == null || dateStr.length() < pattern.length()) {
            return null;
        }
        return strToDate(dateStr, pattern);
    }

    public static Date strToDate(String dateStr, String pattern) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }

        pattern = StringUtils.isEmpty(pattern) ? DEFAULT_TIME_PATTERN : pattern;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date result = null;
        try {
            result = format.parse(dateStr);
        } catch (ParseException e) {
            log.error("发生异常{}", e.getMessage());
        }
        return result;
    }

    /**
     * 功能描述: 获得当前年<br>
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    /**
     * 将日期转换为String类型的日期
     */
    public static String date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

}
