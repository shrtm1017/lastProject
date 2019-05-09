package kr.or.nko.admin.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.admin.dao.IAuthorityDao;
import kr.or.nko.admin.model.AuthorityVo;

@Service("authorityService")
public class AuthorityServiceImpl implements IAuthorityService{
	
	@Resource(name="authorityDao")
	private IAuthorityDao authorityDao;

	/**
	 * Method : selectAuth
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param emp_sq
	 * @return
	 * Method 설명 : 권한 조회
	 */
	@Override
	public AuthorityVo selectAuth(int emp_sq) {
		return authorityDao.selectAuth(emp_sq);
	}

}