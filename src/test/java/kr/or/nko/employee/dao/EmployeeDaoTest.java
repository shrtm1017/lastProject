package kr.or.nko.employee.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.testconfig.LogicTestConfig;

public class EmployeeDaoTest extends LogicTestConfig{
	
	@Resource(name="employeeDao")
	private IEmployeeDao employeeDao;

	@Test
	public void testSelectAllList() {
		/***Given***/
		
		/***When***/
		List<EmployeeVo> list = employeeDao.selectAllList();
		
		/***Then***/
		assertTrue(list.size() > 1);
	}
	
	@Test
	public void testSelect() {
		/***Given***/
		int emp_sq = 111111;
		
		/***When***/
		EmployeeVo empVo = employeeDao.selectEmployee(emp_sq);
		
		/***Then***/
		assertNotNull(empVo);
	}
	
	@Test
	public void testUpdate() {
		/***Given***/
		int emp_sq = 180901;
		String emp_sign = "\\\\Sem-pc\\공유폴더\\최종프로젝트\\1조\\images\\f9af86b6-af34-4109-bf2c-a3ad4fec6bcc.png";
		
		/***When***/
		EmployeeVo empVo = new EmployeeVo();
		empVo.setEmp_sq(emp_sq);
		empVo.setEmp_sign(emp_sign);
		int result = employeeDao.updateEmpSign(empVo);
		
		/***Then***/
		assertEquals(1, result);
	}

}