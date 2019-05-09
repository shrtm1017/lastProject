package kr.or.nko.department.service;

import java.util.List;

import kr.or.nko.department.model.DepartmentVo;

public interface IDepartmentService {

	DepartmentVo selectDepartment(int dpt_sq);

	List<DepartmentVo> selectDepartmentList();
	
	List<DepartmentVo> getAllDepartment();
	
	/**
	 * Method : selectDepart
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param dpt_nm
	 * @return
	 * Method 설명 : 부서명으로 부서 조회
	 */
	public DepartmentVo selectDepart(String dpt_nm);

	
	/**
	 * Method : selectDepartNull
	 * 작성자 : pc11
	 * 변경이력 :
	 * @return
	 * Method 설명 : 상위부서에 해당하는 부서 조회(dpt_hr_sq가 null인 부서)
	 */
	public List<DepartmentVo> selectDepartNull();

}