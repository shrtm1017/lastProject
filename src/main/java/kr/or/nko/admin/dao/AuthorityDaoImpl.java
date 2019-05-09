package kr.or.nko.admin.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.admin.model.AuthorityVo;

@Repository("authorityDao")
public class AuthorityDaoImpl implements IAuthorityDao{
	
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

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
		return sqlSessionTemplate.selectOne("authority.selectAuth", emp_sq);
	}

}