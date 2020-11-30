package common.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	public static String toYYYYMMDD( Date date ) {
		SimpleDateFormat	yyyyMMdd	= new SimpleDateFormat( "yyyy-MM-dd" );
		
		if(date == null){
		    return null;
		}
		return yyyyMMdd.format( date );
	}
	
	public static String toHHmm( Date date ) {
		SimpleDateFormat	HHmm		= new SimpleDateFormat( "HH:mm" );
		if(date == null){
            return null;
        }
		return HHmm.format( date );
	}
	
    /**
     * 날짜를 형 변환한다.
     * @param date yyyyMMddHHmmss 타입의 날짜 문자
     * @return "yyyy-MM-dd" 변환된 날짜. 변환 실패시에는 date를 return
     */
    public static String formatDate(String date) {
        return formatDate(date, null);
    }
    
	/**
	 * 날짜를 형 변환한다.
	 * @param date yyyyMMddHHmmss 타입의 날짜 문자
	 * @param delimiter 날짜 구분자. 기본값은 "-"
	 * @return 변환된 날짜. 변환 실패시에는 date를 return
	 */
	public static String formatDate(String date, String delimiter) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    
	    if(delimiter == null) {
	        delimiter = "-";
	    }
	    
        SimpleDateFormat tgdf = new SimpleDateFormat("yyyy" + delimiter + "MM" + delimiter + "dd");
        
	    try {
            Date dt = sdf.parse(date);
            
            return tgdf.format(dt);
        } catch (ParseException e) {
            return date;
        }
	}
	
	public static String formatFullDate(String date){
		return formatFullDate(date, null);
	}

	public static String formatFullDate(String date, String delimiter){
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	  	
	    if(delimiter == null) {
	  		delimiter = "-";
	  	}
	  	
	    SimpleDateFormat tgdf = new SimpleDateFormat("yyyy" + delimiter + "MM" + delimiter + "dd");
	    try {
	          Date dt = sdf.parse(date);
	          return tgdf.format(dt);
	      } catch (ParseException e) {
	          return date;
	      }
	}

    /**
     * 시간을 형 변환한다.
     * @param date yyyyMMddHHmmss 타입의 날짜 문자
     * @return "HH:mm" 변환된 시간. 변환 실패시에는 date를 return
     */
    public static String formatTime(String date) {
        return formatTime(date, null);
    }
    
    /**
     * 시간을 형 변환한다.
     * @param date yyyyMMddHHmmss 타입의 날짜 문자
     * @param delimiter 시간 구분자. 기본값은 ":"
     * @return 변환된 시간. 변환 실패시에는 date를 return
     */
    public static String formatTime(String date, String delimiter) {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        
        if(delimiter == null) {
            delimiter = ":";
        }
        
        SimpleDateFormat tgdf = new SimpleDateFormat("HH" + delimiter + "mm");
        
        try {
            Date dt = sdf.parse(date);
            
            return tgdf.format(dt);
        } catch (ParseException e) {
            return date;
        }
    }
    
    
    /**
     * 시간을 형 변환한다.
     * @param date yyyyMMddHHmmss 타입의 날짜 문자
     * @return "HH:mm" 변환된 시간. 변환 실패시에는 date를 return
     */
    public static String formatFullTime(String date) {
        return formatFullTime(date, null);
    }
    
    /**
     * 시간을 형 변환한다.
     * @param date yyyyMMddHHmmss 타입의 날짜 문자
     * @param delimiter 시간 구분자. 기본값은 ":"
     * @return 변환된 시간. 변환 실패시에는 date를 return
     */
    public static String formatFullTime(String date, String delimiter) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        
        if(delimiter == null) {
            delimiter = ":";
        }
        
        SimpleDateFormat tgdf = new SimpleDateFormat("HH" + delimiter + "mm");
        
        try {
            Date dt = sdf.parse(date);
            
            return tgdf.format(dt);
        } catch (ParseException e) {
            return date;
        }
    }

    /**
     * 날짜 시간을 형 변환한다. yyyy-MM-dd HH:mm 으로 변환
     * @param dateTime yyyyMMddHHmmss 타입의 날짜 문자
     * @return 변환된 날짜 시간. 변환 실패시에는 dateTime을 return
     */
    public static String formatDateTime(String dateTime) {
        return formatDate(dateTime) + " " + formatTime(dateTime);
    }

    public static Date toDate( String yyyyMMdd, String HHmm ) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmm");
        return dateFormat.parse( yyyyMMdd + " " + HHmm );
    }
	
    
    public static String dateConverter( String date, String inFormat, String outFormat ) {
        
    	SimpleDateFormat inDateFormat = new SimpleDateFormat ( inFormat,Locale.ENGLISH);
    	SimpleDateFormat outDateFormat = new SimpleDateFormat ( outFormat );
    	
    	ParsePosition pos = new ParsePosition ( 0 );
//		Date frmTime = inDateFormat.parse ( date, pos );
		
    	Date frmTime = new Date(date);
    	
        return outDateFormat.format ( frmTime );
    }

    /**
     * for example, convert "2012-01-25 20:14:25" to "2012-01-25"  
     * @param draftDate
     * @return if draftDate is null or isn't 19 length, return blank, ""
     */
    public static String toYYYY_MM_DD( String draftDate ) {
    	if ( draftDate == null || draftDate.length() < 19 ) {
			return "";
		}
    	return draftDate.substring( 0, 10 );
    }
    
    /**
     * for example, convert "2012-01-25 20:14:25" to "20:14"
     * @param draftDate
     * @return @return if draftDate is null or isn't 19 length, return blank, ""
     */
    public static String toHH_MM( String draftDate ) {
    	if ( draftDate == null || draftDate.length() < 19 ) {
    		return "";
    	}
    	return draftDate.substring( 11, 16 );
    }
    
    public static String getToday( String dateFormat ) {
    	 SimpleDateFormat date = new SimpleDateFormat(dateFormat);
    	 return date.format(new Date());
    }
    
	/**
	 * 요일을 계산
	 */
//	public static String getWeekDay(String day) {
//		Calendar cal = Calendar.getInstance();
//		String date = day.replaceAll("-", "");
//
//		cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
//		cal.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
//		cal.set(Calendar.DATE, Integer.parseInt(date.substring(6, 8)));
//
//		String[] dayOfWeek = { "일", "월", "화", "수", "목", "금", "토" };
//
//		String weekday = dayOfWeek[cal.get(Calendar.DAY_OF_WEEK) - 1];
//
//		return weekday;
//	} 
	
//	public static String getFormatDateWithWeekDay(String day) {
//		
//		String date = "";
//		
//		if(day == null) {
//			date = "";
//		} else if(day.length() == 8) {
//			//yyyymmdd
//			date = formatDate(day) + "(" + getWeekDay(day) + ")";
//		} else if(day.length() == 10 ) {
//			//yyyy-mm-dd 양식
//			date = day + "(" + getWeekDay(day) + ")";
//		} else if(day.length() > 10 && day.length() < 19)  {
//			String tmp = day.substring(0, 10);
//			date = tmp + "(" + getWeekDay(tmp) + ")";
//		} else {
//			date = day;
//		}
//		
//		return date;
//	}
}