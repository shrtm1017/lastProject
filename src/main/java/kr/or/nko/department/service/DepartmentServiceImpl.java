package kr.or.nko.department.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.department.dao.IDepartmentDao;
import kr.or.nko.department.model.DepartmentVo;

@Service("departmentService")
public class DepartmentServiceImpl implements IDepartmentService {

	@Resource(name="departmentDao")
	public IDepartmentDao departmentDao;
	
	@Override
	public DepartmentVo selectDepartment(int dpt_sq) {
		return departmentDao.selectDepartment(dpt_sq);
	}
	
	@Override
	public List<DepartmentVo> selectDepartmentList() {
		return departmentDao.selectDepartmentList();
	}
	
	@Override
	public List<DepartmentVo> getAllDepartment() {
		return departmentDao.getAllDepartment();
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
		return departmentDao.selectDepart(dpt_nm);
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
		return departmentDao.selectDepartNull();
	}
	
}