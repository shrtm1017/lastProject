package kr.or.nko.email.controller;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.nko.testconfig.WebTestConfig;

public class EmailControllerTest extends WebTestConfig{
	
	private Logger logger = LoggerFactory.getLogger(EmailControllerTest.class);
	
	@Test
	public void test(){
		logger.debug("메롱");
	}

}