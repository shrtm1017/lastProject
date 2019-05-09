package kr.or.nko.admin.service;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.nko.admin.model.AuthorityVo;
import kr.or.nko.admin.service.IAuthorityService;
import kr.or.nko.testconfig.LogicTestConfig;

public class AuthorityServiceTest extends LogicTestConfig{
	
	@Resource(name="authorityService")
	private IAuthorityService authorityService;

	@Test
	public void testSelectAuth() {
		/***Given***/
		int emp_sq = 111111;
		
		/***When***/
		AuthorityVo vo = authorityService.selectAuth(emp_sq);
		
		/***Then***/
		assertNotNull(vo);
	}

}
