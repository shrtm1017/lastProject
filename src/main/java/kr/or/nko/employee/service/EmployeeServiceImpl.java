package kr.or.nko.employee.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.employee.dao.IEmployeeDao;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.util.KISA_SHA256;

@Service("employeeService")
public class EmployeeServiceImpl implements IEmployeeService {
	
	@Resource(name="employeeDao")
	private IEmployeeDao employeeDao;
	
	@Override
	public int encryptPass() { //중간에 만들다가 맘
		EmployeeVo employeeVo = new EmployeeVo();
		int encryptPass=0;
		List<EmployeeVo> encryptPassList =employeeDao.selectAllList();
		for (int i = 0; i < encryptPassList.size(); i++) {
			String changePassword = encryptPassList.get(i).getEmp_pass();
			String Sha256Pass=KISA_SHA256.encrypt(changePassword);
			employeeVo.setEmp_pass(Sha256Pass);
			employeeVo.setEmp_sq(encryptPassList.get(i).getEmp_sq());
			encryptPass +=employeeDao.encryptPass(employeeVo);
		}
		return encryptPass;
	}

	@Override
	public EmployeeVo selectEmployee(int emp_sq) {
		EmployeeVo employeeVo = employeeDao.selectEmployee(emp_sq);
		return employeeVo;
	}
	
	@Override
	public int insertEmployee(EmployeeVo employeeVo) {
		return employeeDao.insertEmployee(employeeVo);
	}
	
	@Override
	public int updateEmployee(EmployeeVo employeeVo) {
		return employeeDao.updateEmployee(employeeVo);
	}

	@Override
	public int updateManage(EmployeeVo employeeVo) {
		return employeeDao.updateManage(employeeVo);
	}
	
	/**
	 * Method : selectAllList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @return
	 * Method 설명 : 전체회원 조회
	 */
	@Override
	public List<EmployeeVo> selectAllList() {
		return employeeDao.selectAllList();
	}

	/**
	 * Method : selectListSearch
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param employeeVo
	 * @return
	 * Method 설명 : 검색조건에 따라 조회
	 */
	@Override
	public List<EmployeeVo> selectListSearch(EmployeeVo employeeVo) {
		return employeeDao.selectListSearch(employeeVo);
	}
	
	/**
	 * Method : select_Empfindid
	 * 작성자 : pc23
	 * 변경이력 :
	 * @param employeeVo
	 * @return
	 * Method 설명 :아이디 찾을때 사원 조회
	 */
	@Override
	public EmployeeVo select_Empfindid(EmployeeVo employeeVo) {
		EmployeeVo findemployeeVo = employeeDao.select_Empfindid(employeeVo);
		return findemployeeVo;
	}
	
	/**
	 * Method : select_Empfindpw
	 * 작성자 : pc23
	 * 변경이력 :
	 * @param employeeVo
	 * @return
	 * Method 설명 :비밀번호 찾을때 사원 조회
	 */
	@Override
	public EmployeeVo select_Empfindpw(EmployeeVo employeeVo) {
		EmployeeVo findPwemployeeVo = employeeDao.select_Empfindpw(employeeVo);
		return findPwemployeeVo;
	}

	/**
	 * Method : selectEmpPagingList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @param employeeVo
	 * @return
	 * Method 설명 : 사원 페이지 리스트 조회
	 */
	@Override
	public Map<String, Object> selectEmpPagingList(Map<String, Object> map, EmployeeVo employeeVo) {
		//결과값을 두개 담아서 return 하기위해 Map 객체 사용
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("empPagingList", employeeDao.selectEmpPagingList(map));
		resultMap.put("empCnt", employeeDao.getEmpCnt());
		resultMap.put("searchEmpCnt", employeeDao.getSearchEmpCnt(employeeVo));
		
		return resultMap;
	}

	@Override
	public int updateEmpSign(EmployeeVo employeeVo) {
		return employeeDao.updateEmpSign(employeeVo);
	}

	@Override
	public int deleteEmp(EmployeeVo employeeVo) {
		return employeeDao.deleteEmp(employeeVo);
	}

	@Override
	public List<EmployeeVo> getAllEmp() {
		return employeeDao.getAllEmp();
	}

	@Override
	public int updateOn(EmployeeVo employeeVo) {
		return employeeDao.updateOn(employeeVo);
	}

}