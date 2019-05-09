package kr.or.nko.util;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTest {

	private Logger logger = LoggerFactory.getLogger(DateTest.class);
	
	@Test
	public void testDate() throws Exception {
		//오늘날짜
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String now = sdf.format(date);
		
		//20190410 -> 201904 월까지만 자르기
		String yearMonth = now.substring(0, 6);
		
		//해당월의 첫번째날 세팅
		Calendar cal = Calendar.getInstance();
		Date firstDate = sdf.parse(yearMonth + "01");
		cal.setTime(firstDate);
		
		//해당월의 마지막날 구하기
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = sdf.parse(yearMonth + lastDay);
		
		logger.debug("{}", date);
		logger.debug("{}", now);
		logger.debug("{}", yearMonth);
		logger.debug("{}", firstDate);
		logger.debug("{}", lastDay);
		logger.debug("{}", lastDate);
	}

}