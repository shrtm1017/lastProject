package kr.or.nko.department.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.department.model.DepartmentVo;

@Repository("departmentDao")
public class DepartmentDaoImpl implements IDepartmentDao {
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public DepartmentVo selectDepartment(int dpt_sq) {
		return sqlSessionTemplate.selectOne("department.selectDepartment", dpt_sq);
	}
	
	@Override
	public List<DepartmentVo> selectDepartmentList() {
		return sqlSessionTemplate.selectList("department.selectDepartmentList");
	}
	
	@Override
	public List<DepartmentVo> getAllDepartment() {
		return sqlSessionTemplate.selectList("department.getAllDepartment");
	}
	
	@Override
	public DepartmentVo selectDepartCode(String str) {
		return sqlSessionTemplate.selectOne("department.selectDepartCode", str);
	}

	/**
	 * Method : selectDepart
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param dpt_nm
	 * @return
	 * Method 설명 : 부서명으로 부서 조회
	 */
	@Override
	public DepartmentVo selectDepart(String dpt_nm) {
		return sqlSessionTemplate.selectOne("department.selectDepart", dpt_nm);
	}

	/**
	 * Method : selectDepartNull
	 * 작성자 : pc11
	 * 변경이력 :
	 * @return
	 * Method 설명 : 상위부서에 해당하는 부서 조회(dpt_hr_sq가 null인 부서)
	 */
	@Override
	public List<DepartmentVo> selectDepartNull() {
		return sqlSessionTemplate.selectList("department.selectDepartNull");
	}

}