package com.star.android.carporange.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DayUtil {
	
	 public static int getCurrentMonthDay() {  
         
	        Calendar a = Calendar.getInstance();  
	        a.set(Calendar.DATE, 1);  
	        a.roll(Calendar.DATE, -1);  
	        int maxDate = a.get(Calendar.DATE);  
	        return maxDate;  
	    }  
	  
	    /** 
	     * ������ �� ��ȡ��Ӧ���·� ���� 
	     * */  
	    public static int getDaysByYearMonth(int year, int month) {  
	          
	        Calendar a = Calendar.getInstance();  
	        a.set(Calendar.YEAR, year);  
	        a.set(Calendar.MONTH, month - 1);  
	        a.set(Calendar.DATE, 1);  
	        a.roll(Calendar.DATE, -1);  
	        int maxDate = a.get(Calendar.DATE);  
	        return maxDate;  
	    }  
	      
	    /** 
	     * �������� �ҵ���Ӧ���ڵ� ���� 
	     */  
	    public static String getDayOfWeekByDate(String date) {  
	        String dayOfweek = "-1";  
	        try {  
	            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");  
	            Date myDate = myFormatter.parse(date);  
	            SimpleDateFormat formatter = new SimpleDateFormat("E");  
	            String str = formatter.format(myDate);  
	            dayOfweek = str;  
	              
	        } catch (Exception e) {  
	            System.out.println("����!");  
	        }  
	        return dayOfweek;  
	    }  
	    
}
