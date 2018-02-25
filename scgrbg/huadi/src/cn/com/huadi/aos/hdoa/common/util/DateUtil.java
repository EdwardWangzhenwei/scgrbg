package cn.com.huadi.aos.hdoa.common.util;


/*
 * 常用日期函数
 */

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	/*
	 * 用来全局控制 上一周，本周，下一周的周数变化  
	 */
    private static int weeks = 0;       
    private static int MaxYear;//一年最大天数   
    /**   
     * @param args   
     */   
    public static void main(String[] args) {    
    	DateUtil tt = new DateUtil();
    	System.out.println("获取当天日期:"+tt.getToday()); 
    	System.out.println("获取当天时间:"+tt.setDateFormatDivZero(tt.getDateTime())); 
    	System.out.println("获取当天时间:"+tt.setDateFormatDivZero(tt.getToday("yyyy年MM月dd日"))); 
        System.out.println("获取当天日期:"+tt.getToday("yyyy年MM月dd日")); 
        System.out.println("获取当天星期:"+tt.getWeekDayName(tt.getToday("yyyy年MM月dd日"))); 
        System.out.println("获取某日期3天前的日期:"+tt.getOffday(tt.strToDate("2009-9-8"),-3));  
        System.out.println("获取某日期3天前的日期:"+tt.setDateFormatDivZero(tt.getOffday(tt.strToDate("2009-9-8"),"yyyy年MM月dd日",-3)));  
        System.out.println("获取当天日期3天前的日期:"+tt.getOffday("yyyy年MM月dd日",-3));  
        System.out.println("获取当天日期10天后的日期:"+tt.getOffday("yyyy年MM月dd日",10));
        System.out.println("获取本周一日期:"+tt.getWeekBegin());    
        System.out.println("获取本周日的日期~:"+tt.getWeekEnd());    
        System.out.println("获取上周一日期:"+tt.getYesWeekBegin());    
        System.out.println("获取上周日日期:"+tt.getYesWeekEnd());    
        System.out.println("获取下周一日期:"+ tt.getNextWeekBegin());    
        System.out.println("获取下周日日期:"+tt.getNextWeekEnd());    
        System.out.println("获取本月第一天日期:"+tt.getMonthBegin());    
        System.out.println("获取本月最后一天日期:"+tt.getMonthEnd());    
        System.out.println("获取上月第一天日期:" + tt.getYesMonthBegin());    
        System.out.println("获取上月最后一天的日期:" + tt.getYesMonthEnd());    
        System.out.println("获取下月第一天日期:"+tt.getNextMonthBegin());    
        System.out.println("获取下月最后一天日期:"+tt.getNextMonthEnd());    
        System.out.println("获取本年的第一天日期:"+tt.getYearBegin());    
        System.out.println("获取本年最后一天日期:"+tt.getYearEnd());    
        System.out.println("获取去年的第一天日期:"+tt.getPreYearBegin());    
        System.out.println("获取去年的最后一天日期:"+tt.getPreYearEnd());    
        System.out.println("获取明年第一天日期:"+tt.getNextYearBegin());    
        System.out.println("获取明年最后一天日期:"+tt.getNextYearEnd());    
        System.out.println("获取本季度第一天到最后一天:"+tt.getThisSeasonTime(11));    
        System.out.println("获取两个日期之间间隔天数2008-12-1~2009-9-2:"+tt.dateCalculate("2008-12-1","2009-9-2"));    
        System.out.println("数值去零"+tt.divZero("08"));
    }
	public DateUtil()
    {
    }
	/*
     * @see     取得当前时间（格式为：yyy-MM-dd HH:mm:ss）
     * @return String
     */
    public static String getDateTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sDate = sdf.format(new Date());
        return sDate;
    }
	/*
	 * 获取当前日期
	 */
	public static String getToday()
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
		Calendar lastDate = Calendar.getInstance();    
    
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 获取当前日期，按给定日期格式返回
	 */
	public static String getToday(String dateformat)
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat(dateformat);        
   
		Calendar lastDate = Calendar.getInstance();    
    
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 获取当前日期偏移天数的日期
	 */
	public static String getOffday(int OffDay)
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
		Calendar lastDate = Calendar.getInstance();    
		lastDate.add(Calendar.DATE,OffDay);//加上偏移天数   
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 获取给定日期偏移天数的日期
	 */
	public static String getOffday(Date inDate,int OffDay)
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
		Calendar lastDate = Calendar.getInstance(); 
		lastDate.setTime(inDate);
		lastDate.add(Calendar.DATE,OffDay);//加上偏移天数   
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 获取给定日期偏移天数的日期，按给定格式返回
	 */
	public static String getOffday(Date inDate,String dateformat,int OffDay)
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat(dateformat);        
		Calendar lastDate = Calendar.getInstance(); 
		lastDate.setTime(inDate);
		lastDate.add(Calendar.DATE,OffDay);//加上偏移天数   
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 获取当前日期偏移天数的日期，按给定日期格式返回
	 */
	public static String getOffday(String dateformat,int OffDay)
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat(dateformat);        
   
		Calendar lastDate = Calendar.getInstance();    
		lastDate.add(Calendar.DATE,OffDay);//加上偏移天数   
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 获取一月前日期
	 */
	public static String getOneMonth()
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
		Calendar lastDate = Calendar.getInstance();    
		lastDate.add(Calendar.MONTH,-1);//减去一个月   
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 获取一月前日期，按给定日期格式返回
	 */
	public static String getOneMonth(String dateformat)
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat(dateformat);        
   
		Calendar lastDate = Calendar.getInstance();    
		lastDate.add(Calendar.MONTH,-1);//减去一个月   
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 获取一年前日期
	 */
	public static String getOneYear()
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
		Calendar lastDate = Calendar.getInstance();    
		lastDate.add(Calendar.YEAR,-1);//减去一年  
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 获取一年前日期，按给定日期格式返回
	 */
	public static String getOneYear(String dateformat)
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat(dateformat);        
   
		Calendar lastDate = Calendar.getInstance();    
		lastDate.add(Calendar.YEAR,-1);//减去一年  
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 把日期解析为年、月、日、时、分、秒
	 */
	public static  Calendar parseDateTime(String baseDate)
    {
        Calendar cal = null;
        cal = new GregorianCalendar();
        int yy = Integer.parseInt(baseDate.substring(0, 4));
        int mm = Integer.parseInt(baseDate.substring(5, 7)) - 1;
        int dd = Integer.parseInt(baseDate.substring(8, 10));
        int hh = 0;
        int mi = 0;
        int ss = 0;
        if(baseDate.length() > 12)
        {
            hh = Integer.parseInt(baseDate.substring(11, 13));
            mi = Integer.parseInt(baseDate.substring(14, 16));
            ss = Integer.parseInt(baseDate.substring(17, 19));
        }
        cal.set(yy, mm, dd, hh, mi, ss);
        
        return cal;
    }
	/*
	 * 根据给定的日期值，获得年份的值
	 */
    public static int getYear(String strDate)
    {
        Calendar cal = parseDateTime(strDate);
        return  cal.get(Calendar.YEAR);
    }
	/*
	 * 根据给定的日期值，获得日的值
	 */
    public static int getDay(String strDate)
    {
        Calendar cal = parseDateTime(strDate);
        return  cal.get(Calendar.DATE);
    }
    /*
	 * 根据给定的日期值，获得月的值
	 */
    public static int getMonth(String strDate)
    {
        Calendar cal = parseDateTime(strDate);

        return cal.get(Calendar.MONTH) + 1;
    }
    /*
	 * 根据给定的日期值，获得小时的值
	 */
    public static int getHour(String strDate)
    {
        Calendar cal = parseDateTime(strDate);

        return cal.get(Calendar.HOUR);
    }
    /*
	 * 根据给定的日期值，获得分钟的值
	 */
    public static int getMinute(String strDate)
    {
        Calendar cal = parseDateTime(strDate);

        return cal.get(Calendar.MINUTE);
    }
    /*
	 * 根据给定的日期值，获得秒的值
	 */
    public static int getSecond(String strDate)
    {
        Calendar cal = parseDateTime(strDate);

        return cal.get(Calendar.SECOND);
    }
    /*
	 * 根据给定的日期值，获得星期的值
	 */
    public static int getWeekDay(String strDate)
    {
        Calendar cal = parseDateTime(strDate);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    /*
	 * 根据给定的日期值，获得星期几
	 */
	public static String getWeekDayName(String strDate)
    {
        String mName[] = {
            "日", "一", "二", "三", "四", "五", "六"
        };
        int iWeek = getWeekDay(strDate);
        iWeek = iWeek - 1;
        return "星期" + mName[iWeek];
    }
	/*
	 * 获取本周第一天日期
	 */
	public static String getWeekBegin()
	{
		weeks = 0;    
        int mondayPlus = getMondayPlus();  
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
        GregorianCalendar currentDate = new GregorianCalendar();    
        currentDate.add(GregorianCalendar.DATE, mondayPlus);    
        Date monday = currentDate.getTime();    
            
        //DateFormat df = DateFormat.getDateInstance();    
        String preMonday = df.format(monday);    
        return preMonday;
	}
	/*
	 * 获取本周最后一天日期
	 */
	public static String getWeekEnd()
	{
		int mondayPlus = getMondayPlus();   
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
        GregorianCalendar currentDate = new GregorianCalendar();    
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);    
        Date monday = currentDate.getTime();    
        //DateFormat df = DateFormat.getDateInstance();    
        String preMonday = df.format(monday);    
        return preMonday; 
	}
	/*
	 * 获得本周星期日的日期
	 */      
    public static String getCurrentWeekday() {    
        weeks = 0;    
        int mondayPlus = getMondayPlus();    
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
        GregorianCalendar currentDate = new GregorianCalendar();    
        currentDate.add(GregorianCalendar.DATE, mondayPlus+6);    
        Date monday = currentDate.getTime();    
            
        //DateFormat df = DateFormat.getDateInstance();    
        String preMonday = df.format(monday);    
        return preMonday;    
    }
    /*
     * 获得当前日期与本周日相差的天数
     */ 
    private static int getMondayPlus() {    
        Calendar cd = Calendar.getInstance();    
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......    
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1;         //因为按中国礼拜一作为第一天所以这里减1    
        if (dayOfWeek == 0) {    
            return -6;    
        } else {    
            return 1 - dayOfWeek;    
        }    
    }
	/*
	 * 获取上周第一天日期
	 */
	public static String getYesWeekBegin()
	{
		weeks--;    
        int mondayPlus = getMondayPlus(); 
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
        GregorianCalendar currentDate = new GregorianCalendar();    
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);    
        Date monday = currentDate.getTime();    
        //DateFormat df = DateFormat.getDateInstance();    
        String preMonday = df.format(monday);    
        return preMonday;
	}
	/*
	 * 获取上周最后一天日期
	 */
	public static String getYesWeekEnd()
	{
		weeks=0;    
        weeks--;    
        int mondayPlus = getMondayPlus();    
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
        GregorianCalendar currentDate = new GregorianCalendar();     
        currentDate.add(GregorianCalendar.DATE, mondayPlus+weeks);    
        Date monday = currentDate.getTime();    
        //DateFormat df = DateFormat.getDateInstance();    
        String preMonday = df.format(monday);    
        return preMonday;
	}
	/*
	 * 获取下周第一天日期
	 */
	public static String getNextWeekBegin()
	{   
        int mondayPlus = getMondayPlus();  
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
        GregorianCalendar currentDate = new GregorianCalendar();    
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 );    
        Date monday = currentDate.getTime();    
        //DateFormat df = DateFormat.getDateInstance();    
        String preMonday = df.format(monday);    
        return preMonday;
	}
	/*
	 * 获得下周星期日的日期 
	 */ 
    public static String getNextWeekEnd() 
    {    
        int mondayPlus = getMondayPlus();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
        GregorianCalendar currentDate = new GregorianCalendar();    
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7+6);    
        Date monday = currentDate.getTime();    
        //DateFormat df = DateFormat.getDateInstance();    
        String preMonday = df.format(monday);    
        return preMonday;    
    }
	/*
	 * 获取本月第一天日期
	 */
	public static String getMonthBegin()
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
		Calendar lastDate = Calendar.getInstance();    
		lastDate.set(Calendar.DATE,1);//设为当前月的1号    
		str=sdf.format(lastDate.getTime());    
		return str; 
	}
	/*
	 * 获取本月最后一天日期
	 */
	public static String getMonthEnd()
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
		Calendar lastDate = Calendar.getInstance();    
		lastDate.set(Calendar.DATE,1);//设为当前月的1号    
       	lastDate.add(Calendar.MONTH,1);//加一个月，变为下月的1号    
       	lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天    
       
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 获取上月第一天日期
	 */
	public static String getYesMonthBegin()
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
		Calendar lastDate = Calendar.getInstance();    
		lastDate.set(Calendar.DATE,1);//设为当前月的1号    
		lastDate.add(Calendar.MONTH,-1);//减一个月，变为下月的1号    
		str=sdf.format(lastDate.getTime());    
		return str;
	}
	/*
	 * 获取上月最后一天日期
	 */
	public  static String getYesMonthEnd()
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
       	Calendar lastDate = Calendar.getInstance();    
       	lastDate.add(Calendar.MONTH,-1);//减一个月    
       	lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天     
       	lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天     
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 获取下月第一天日期
	 */
	public  static String getNextMonthBegin()
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
       	Calendar lastDate = Calendar.getInstance();    
       	lastDate.add(Calendar.MONTH,1);//减一个月    
       	lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天     
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 获取下月最后一天日期
	 */
	public  static String getNextMonthEnd()
	{
		String str = "";    
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
       	Calendar lastDate = Calendar.getInstance();    
       	lastDate.add(Calendar.MONTH,1);//减一个月    
       	lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天   
       	lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天
       	str=sdf.format(lastDate.getTime());    
       	return str; 
	}
	/*
	 * 获取本年第一天日期
	 */
	public static String getYearBegin()
	{
		String str = "";    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
        Calendar lastDate = Calendar.getInstance();    
        lastDate.set(Calendar.DAY_OF_YEAR, 1);    
        str=sdf.format(lastDate.getTime());    
        return str; 
	}
	/*
	 * 获取本年最后一天日期
	 */
	public static String getYearEnd()
	{
		String str = "";    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
        Calendar lastDate = Calendar.getInstance();       
        lastDate.set(Calendar.DAY_OF_YEAR, 1);    
        lastDate.roll(Calendar.DAY_OF_YEAR, -1);    
        str=sdf.format(lastDate.getTime());    
        return str;      
    }

	/*
	 * 获得明年第一天的日期
	 */
    public static String getNextYearBegin(){    
        String str = "";    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
        Calendar lastDate = Calendar.getInstance();    
        lastDate.add(Calendar.YEAR,1);//加一个年    
        lastDate.set(Calendar.DAY_OF_YEAR, 1);    
        str=sdf.format(lastDate.getTime());    
        return str;      
    }
    /*
     * 获得明年最后一天的日期
     */
    public static String getNextYearEnd(){    
        String str = "";    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
        Calendar lastDate = Calendar.getInstance();    
        lastDate.add(Calendar.YEAR,1);//加一个年    
        lastDate.set(Calendar.DAY_OF_YEAR, 1);    
        lastDate.roll(Calendar.DAY_OF_YEAR, -1);    
        str=sdf.format(lastDate.getTime());    
        return str;      
    }
	/*
	 * 获得上年第一天的日期 * 
	 */
    public static String getPreYearBegin(){    
    	String str = "";    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
   
        Calendar lastDate = Calendar.getInstance();    
        lastDate.add(Calendar.YEAR,-1);//减一个年    
        lastDate.set(Calendar.DAY_OF_YEAR, 1);    
        str=sdf.format(lastDate.getTime());    
        return str;     
    }    
    /*
     * 获得上年最后一天的日期 
     */    
    public static String getPreYearEnd(){    
    	 String str = "";    
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
    
         Calendar lastDate = Calendar.getInstance();    
         lastDate.add(Calendar.YEAR,-1);//减一个年    
         lastDate.set(Calendar.DAY_OF_YEAR, 1);    
         lastDate.roll(Calendar.DAY_OF_YEAR, -1);    
         str=sdf.format(lastDate.getTime());    
         return str;    
    }    
    /*
     * 获得本年有多少天
     */
    public static int getMaxYear(){    
        Calendar cd = Calendar.getInstance();    
        cd.set(Calendar.DAY_OF_YEAR,1);//把日期设为当年第一天    
        cd.roll(Calendar.DAY_OF_YEAR,-1);//把日期回滚一天。    
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);     
        return MaxYear;    
    } 
	/*
	 * 获取上半年第一天日期
	 */
	public  static String getYearPreBegin()
	{
		Date date = new Date();    
        SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");       
        String  years  = dateFormat.format(date);       
        return years+"-1-1";
	}
	/*
	 * 获取上半年最后一天日期
	 */
	public  static String getYearPreEnd()
	{
		Date date = new Date();    
        SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");       
        String  years  = dateFormat.format(date);       
        return years+"-6-30";
	}
	/*
	 * 获取下半年第一天日期
	 */
	public static String getYearLastBegin()
	{
		Date date = new Date();    
        SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");   
        String  years  = dateFormat.format(date);       
        return years+"-7-1";
	}
	/*
	 * 获取下半年最后一天日期
	 */
	public String getYearLastEnd()
	{
		Date date = new Date();    
        SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");      
        String  years  = dateFormat.format(date);       
        return years+"-12-31";
	}

	/*
	 * 日期计算,返回两个日期之间的天数
	 */
	public static String dateCalculate(String DateBegin,String DateEnd)
	{
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");    
        long day = 0;    
        try {    
        	java.util.Date date = myFormatter.parse(DateBegin);    
        	java.util.Date mydate = myFormatter.parse(DateEnd);    
        	day = (mydate.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);    
        } catch (Exception e) {    
        	return "";    
        }    
        return day + "";
	}
	/*
	 * 时间计算,返回两个时间之间的时分秒串，以时间的格式返回
	 */
	public static String timeCalculate(String TimeBegin,String TimeEnd)
	{
		return "";
	}

   /*
    * 获得本季度 
    */
    public static String getThisSeasonTime(int month){    
        int array[][] = {{1,2,3},{4,5,6},{7,8,9},{10,11,12}};    
        int season = 1;    
        if(month>=1&&month<=3){    
            season = 1;    
        }    
        if(month>=4&&month<=6){ 
        	season = 2;    
        }    
        if(month>=7&&month<=9){    
            season = 3;    
        }    
        if(month>=10&&month<=12){    
            season = 4;    
        }    
        int start_month = array[season-1][0];    
        int end_month = array[season-1][2];    
            
        Date date = new Date();    
        SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");//可以方便地修改日期格式       
        String  years  = dateFormat.format(date);       
        int years_value = Integer.parseInt(years);    
            
        int start_days =1;//years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);    
        int end_days = getLastDayOfMonth(years_value,end_month);    
        String seasonDate = years_value+"-"+start_month+"-"+start_days+";"+years_value+"-"+end_month+"-"+end_days;    
        return seasonDate;    
            
    }    
        
	    /*   
	     * 获取某年某月的天数   
	     * @param year 年   
	     * @param month 月   
	     * @return 最后一天   
	     */   
    public static int getLastDayOfMonth(int year, int month) 
    {    
    	if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8   
               || month == 10 || month == 12) {    
           return 31;    
    	}    
    	if (month == 4 || month == 6 || month == 9 || month == 11) {    
           return 30;    
    	}    
    	if (month == 2) {    
           if (isLeapYear(year)) {    
               return 29;    
           } else {    
               return 28;    
           }    
    	}    
    	return 0;    
    }    
	   /*   
	    * 是否闰年   
	    * @param year 年   
	    * @return    
	    */   
    public static boolean isLeapYear(int year) {    
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);    
    }
    private int getYearPlus(){    
        Calendar cd = Calendar.getInstance();    
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);//获得当天是一年中的第几天    
        cd.set(Calendar.DAY_OF_YEAR,1);//把日期设为当年第一天    
        cd.roll(Calendar.DAY_OF_YEAR,-1);//把日期回滚一天。    
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);    
        if(yearOfNumber == 1){    
            return -MaxYear;    
        }else{    
            return 1-yearOfNumber;    
        }    
    }    
	  /*   
       * 将短时间格式字符串转换为时间 yyyy-MM-dd      
       * param strDate   
       * return   
       */   
    public static Date strToDate(String strDate) {    
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");    
       ParsePosition pos = new ParsePosition(0);    
       Date strtodate = formatter.parse(strDate, pos);    
       return strtodate;    
    }
   /*
    * 计算指定日期某年的第几周
    * return  interger
    * throws ParseException
    */
    public static int getWeekNumOfYearDay(String strDate ) throws ParseException
    {
    	Calendar calendar = Calendar.getInstance();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date curDate = format.parse(strDate);
    	calendar.setTime(curDate);
    	int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
    	return iWeekNum;
    }
   /*
    * 计算某年某周的开始日期
    * return  interger
    * throws ParseException
    */
    public static String getYearWeekFirstDay(int yearNum,int weekNum) throws ParseException 
    {
    
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.YEAR, yearNum);
    	cal.set(Calendar.WEEK_OF_YEAR, weekNum);
    	cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    	//分别取得当前日期的年、月、日
    	String tempYear = Integer.toString(yearNum);
    	String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
    	String tempDay = Integer.toString(cal.get(Calendar.DATE));
    	String tempDate = tempYear + "-" +tempMonth + "-" + tempDay;
    	return setDateFormat(tempDate,"yyyy-MM-dd");

   }
   /*
    * 计算某年某周的结束日期
    * return  interger
    * throws ParseException
    */
   public static String getYearWeekEndDay(int yearNum,int weekNum) throws ParseException 
   {
	   Calendar cal = Calendar.getInstance();
	   cal.set(Calendar.YEAR, yearNum);
	   cal.set(Calendar.WEEK_OF_YEAR, weekNum + 1);
	   cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
       //分别取得当前日期的年、月、日
	   String tempYear = Integer.toString(yearNum);
	   String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
	   String tempDay = Integer.toString(cal.get(Calendar.DATE));
	   String tempDate = tempYear + "-" +tempMonth + "-" + tempDay;
	   return setDateFormat(tempDate,"yyyy-MM-dd");
   }
   
   
   /*
    * 计算某年某月的开始日期
    * return  interger
    * throws ParseException
    */
   public static String getYearMonthFirstDay(int yearNum,int monthNum) throws ParseException 
   {
    
    //分别取得当前日期的年、月、日
	   String tempYear = Integer.toString(yearNum);
	   String tempMonth = Integer.toString(monthNum);
	   String tempDay = "1";
	   String tempDate = tempYear + "-" +tempMonth + "-" + tempDay;
	   return setDateFormat(tempDate,"yyyy-MM-dd");
    
   }
   /*
    * 计算某年某月的结束日期
    * return  interger
    * throws ParseException
    */
   public static String getYearMonthEndDay(int yearNum,int monthNum) throws ParseException 
   {
    

      //分别取得当前日期的年、月、日
	   String tempYear = Integer.toString(yearNum);
	   String tempMonth = Integer.toString(monthNum);
	   String tempDay = "31";
	   if (tempMonth.equals("1") || tempMonth.equals("3") || tempMonth.equals("5") || tempMonth.equals("7") ||tempMonth.equals("8") || tempMonth.equals("10") ||tempMonth.equals("12")) {
		   tempDay = "31";
	   }
	   if (tempMonth.equals("4") || tempMonth.equals("6") || tempMonth.equals("9")||tempMonth.equals("11")) {
		   tempDay = "30";
	   }
	   if (tempMonth.equals("2")) {
		   if (isLeapYear(yearNum)) {
			   tempDay = "29";
		   } else {
			   tempDay = "28";
		   }
	   }
	   String tempDate = tempYear + "-" +tempMonth + "-" + tempDay;
	   return setDateFormat(tempDate,"yyyy-MM-dd");

   	}
   /*
    * 取得指定时间的给定格式()
    * return String
    */
   public static String setDateFormat(String myDate,String strFormat) throws ParseException
   {

       SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
       String sDate = sdf.format(sdf.parse(myDate));

       return sDate;
   }
   /*
    * 去掉日期中月、日的0
    * return String
    */
   public static String setDateFormatDivZero(String myDate)
   {
	   String sDate ="";
	   if(myDate.length()>12)
	   {
		   sDate = getYear(myDate) + "年" + getMonth(myDate) + "月" + getDay(myDate) + "日" + getHour(myDate) + "时" + getMinute(myDate) + "分" + getSecond(myDate) + "秒";
	   }else
	   {
		   sDate = getYear(myDate) + "年" + getMonth(myDate) + "月" + getDay(myDate) + "日";
	   }
       return sDate;
   }
   /*
    * 去掉日期中月、日的0
    * return String
    */
   public static String divZero(String inString)
   {

	   int tempNum = Integer.parseInt(inString);
	   
       return Integer.toString(tempNum);
   }
   //java中怎样计算两个时间如：“21:57”和“08:20”相差的分钟数、小时数 java计算两个时间差小时 分钟 秒 .  
   public static int timeSubtract(String strBegin,String strEnd){
	   int res=-1;
	   if(strBegin!=null&&!"".equals(strBegin)&&strEnd!=null&&!"".equals(strEnd)){
	       SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	       Date begin = null;   
	       Date end = null;   
	       try {   
		       begin = dfs.parse(strBegin);   
		       end = dfs.parse(strEnd);   
		       long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒   
		       
		       long day1 = between / (24 * 3600);   
		       long hour1 = between % (24 * 3600) / 3600;   
		       long minute1 = between % 3600 / 60;   
		       long second1 = between % 60;  
		       res= (int) (day1*24+hour1);
	       } catch (ParseException e) {   
	       e.printStackTrace();   
	       }  	 
	   }
	   return res;
   }  
   /** 
    * 日期转换成字符串 
    * @param date 
    * @return 
    */  
   public static String dateToString(Date date){  
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
       return format.format(date);  
   }  
}