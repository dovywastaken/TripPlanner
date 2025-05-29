package com.spring.controller.post;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatter {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yy.MM.dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yy.MM.dd.HH:mm");

    
    public String formatBoardDate(Date postDate) 
    {
        Date currentDate = new Date();
        Date todayMidnight = getMidnight(currentDate);
        Date tomorrowMidnight = getNextMidnight(todayMidnight);
        
        // 게시물 작성 시간이 오늘 자정부터 내일 자정 전까지인지 확인
        if (postDate.compareTo(todayMidnight) >= 0 && postDate.before(tomorrowMidnight)) {
            // 오늘 자정부터 내일 자정 전이라면 시간으로 표시
            return timeFormat.format(postDate);
        } else {
            // 그 외의 경우 날짜로 표시
            return dateFormat.format(postDate);
        }
    }
    
    //년,월,일,시간:분 형태로 Date객체의 데이터를 가공해주는 함수 (Post DTO에서는 Timestamp 객체를 쓰고있음)
    public String formatPostDate(Date postDate) {return datetimeFormat.format(postDate);} 
    

    private Date getMidnight(Date date) 
    {
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getNextMidnight(Date date) 
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
}

