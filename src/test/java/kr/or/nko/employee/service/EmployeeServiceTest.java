package kr.or.nko.employee.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.testconfig.LogicTestConfig;
import kr.or.nko.util.KISA_SHA256;

public class EmployeeServiceTest extends LogicTestConfig{
	
	private org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeeServiceTest.class);
	
	@Resource(name="employeeService")
	private IEmployeeService employeeService;
	
	@Test
	public void testSelectAllList() {
		/***Given***/
		
		/***When***/
		List<EmployeeVo> list = employeeService.selectAllList();
		/***Then***/
		assertTrue(list.size() > 1);
	}
	
	@Test
	public void testEncryptList() {
		/***Given***/
		/***When***/
	int encryptPass = employeeService.encryptPass();
		/***Then***/
		assertTrue(encryptPass> 1);
	}
	
	/**
	* Method : testInsertEmployee
	* 작성자 : pc15
	* 변경이력 :
	* Method 설명 : 회원가입 테스트
	*/
	@Test
	public void testInsertEmployee(){
		/***Given***/
		Date date= new Date();
		
		/***When***/
		EmployeeVo evo = new EmployeeVo();
		evo.setEmp_sq(0);
		evo.setEmp_dpt(201);
		evo.setEmp_grade(0);
		evo.setEmp_nm("qwe");
		evo.setEmp_pass("123");
		evo.setEmp_phone("123");
		evo.setEmp_com_email("123");
		evo.setEmp_psn_email("123");
		evo.setEmp_ent(date);
		evo.setEmp_fnl_mod(date);
		evo.setEmp_flag("n");
		
		int cnt = employeeService.insertEmployee(evo);
		
		/***Then***/
		assertEquals(1, cnt);
	}
	
	@Test
	public void testT(){
		/***Given***/
		String password = "asdasd";
		/***When***/
		String encPass = KISA_SHA256.encrypt(password);
		/***Then***/
		logger.debug("encPass : {}",encPass);
		logger.debug("encPass : {}",encPass);
		logger.debug("encPass : {}",encPass);
		logger.debug("encPass : {}",encPass);
		
		assertEquals(encPass, "5fd924625f6ab16a19cc987c7c56ae181349e4ba675f843d5a1ebaacdb8");
	}

}