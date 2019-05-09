package kr.or.nko.employee.service;

import java.util.List;
import java.util.Map;

import kr.or.nko.employee.model.EmployeeVo;

public interface IEmployeeService {
	
	int encryptPass();

	public EmployeeVo selectEmployee(int emp_sq);
	
	/**
	 * Method : select_Empfindid
	 * 작성자 : pc23
	 * 변경이력 :
	 * @return
	 * Method 설명 : 아이디찾기시 사원 조회
	 */
	public EmployeeVo select_Empfindid(EmployeeVo employeeVo);
	
	/**
	 * Method : select_Empfindpw
	 * 작성자 : pc23
	 * 변경이력 :
	 * @return
	 * Method 설명 : 비밀번호찾기시 사원 조회
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
	int insertEmployee(EmployeeVo employeeVo);
	
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
	 * Method 설명 : 전체회원 조회
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
	 * @param employeeVo
	 * @return
	 * Method 설명 : 사원 페이지 리스트 조회
	 */
	Map<String, Object> selectEmpPagingList(Map<String, Object> map, EmployeeVo employeeVo);

	/**
	 * Method : updateEmpSign
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param employeeVo
	 * @return
	 * Method 설명 : 서명 등록
	 */
	public int updateEmpSign(EmployeeVo employeeVo);
	
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