package kr.or.nko.admin.dao;

import kr.or.nko.admin.model.AuthorityVo;

public interface IAuthorityDao {
	
	/**
	 * Method : selectAuth
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param emp_sq
	 * @return
	 * Method 설명 : 권한 조회
	 */
	public AuthorityVo selectAuth(int emp_sq);
}