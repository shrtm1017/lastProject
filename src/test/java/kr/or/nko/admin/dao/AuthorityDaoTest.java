package kr.or.nko.admin.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.nko.admin.dao.IAuthorityDao;
import kr.or.nko.admin.model.AuthorityVo;
import kr.or.nko.testconfig.LogicTestConfig;

public class AuthorityDaoTest extends LogicTestConfig{

	@Resource(name="authorityDao")
	private IAuthorityDao authorityDao;
	
	@Test
	public void testSelectAuth() {
		/***Given***/
		int emp_sq = 111111;
		
		/***When***/
		AuthorityVo vo = authorityDao.selectAuth(emp_sq);

		/***Then***/
		assertNotNull(vo);
	}

}