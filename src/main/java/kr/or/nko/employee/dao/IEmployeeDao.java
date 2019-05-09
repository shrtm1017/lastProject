package kr.or.nko.employee.dao;

import java.util.List;
import java.util.Map;

import kr.or.nko.employee.model.EmployeeVo;

public interface IEmployeeDao {
	
	int encryptPass(EmployeeVo employeeVo);
	
	public EmployeeVo selectEmployee(int emp_sq);
	
	/**
	 * Method : select_Empfindid
	 * 작성자 : pc23
	 * 변경이력 :
	 * @return
	 * Method 설명 : 아이디 찾기위한 회원 검색
	 */
	public EmployeeVo select_Empfindid(EmployeeVo employeeVo);
	
	/**
	 * Method : select_Empfindpw
	 * 작성자 : pc11
	 * 변경이력 :
	 * @return
	 * Method 설명 : 비밀번호 찾기위한 회원 검색
	 */
	public EmployeeVo select_Empfindpw(EmployeeVo employeeVo);
	
	/**
	 * Method : insertEmployee
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param employeeVo
	 * @return
	 * Method 설명 : 관리자 - 사원 정보 등록
	 */
	public int insertEmployee(EmployeeVo employeeVo);
	
	/**
	 * Method : updateEmployee
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param employeeVo
	 * @return
	 * Method 설명 : 회원 - 내 정보 수정
	 */
	public int updateEmployee(EmployeeVo employeeVo);
	
	/**
	 * Method : updateManage
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param employeeVo
	 * @return
	 * Method 설명 : 관리자 - 사원 정보 수정
	 */
	public int updateManage(EmployeeVo employeeVo);
	
	/**
	 * Method : selectAllList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @return
	 * Method 설명 : 전체 회원 조회
	 */
	public List<EmployeeVo> selectAllList();
	
	/**
	 * Method : selectListSearch
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param employeeVo
	 * @return
	 * Method 설명 : 검색조건에 따라 조회
	 */
	public List<EmployeeVo> selectListSearch(EmployeeVo employeeVo);
	
	/**
	 * Method : selectEmpPagingList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 사원 페이지 리스트 조회
	 */
	public List<EmployeeVo> selectEmpPagingList(Map<String, Object> map);
	
	/**
	 * Method : getEmpCnt
	 * 작성자 : pc11
	 * 변경이력 :
	 * @return
	 * Method 설명 : 전체 사원수를 조회
	 */
	public int getEmpCnt();
	
	/**
	 * Method : getSearchEmpCnt
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param employeeVo
	 * @return
	 * Method 설명 : 조건검색 사원수 조회
	 */
	public int getSearchEmpCnt(EmployeeVo employeeVo);

	/**
	 * Method : updateUserSign
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param employeeVo
	 * @return
	 * Method 설명 : 서명 등록
	 */
	public int updateEmpSign(EmployeeVo employeeVo);
	
	//퇴사
	public int deleteEmp(EmployeeVo employeeVo);
	
	/**
	 * Method : getAllEmp
	 * 작성자 : PC07
	 * 변경이력 :
	 * @return
	 * Method 설명 : 전체 사원 리스트 : 퇴사한 사원 제외
	 */
	public List<EmployeeVo> getAllEmp();
	
	/**
	 * Method : updateOn
	 * 작성자 : lee
	 * 변경이력 :
	 * @param employeeVo
	 * @return
	 * Method 설명 : 접속여부 수정
	 */
	public int updateOn(EmployeeVo employeeVo);
	
}