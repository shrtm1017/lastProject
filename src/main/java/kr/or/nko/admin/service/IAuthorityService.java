package kr.or.nko.admin.service;

import kr.or.nko.admin.model.AuthorityVo;

public interface IAuthorityService {
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