package kr.or.nko.employee.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.employee.model.EmployeeVo;

@Repository("employeeDao")
public class EmployeeDaoImpl implements IEmployeeDao {
	
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int encryptPass(EmployeeVo employeeVo) {
		return sqlSessionTemplate.update("employee.encrytPass",employeeVo);
	}

	@Override
	public EmployeeVo selectEmployee(int emp_sq) {
		EmployeeVo employeeVo = sqlSessionTemplate.selectOne("employee.selectEmployee", emp_sq);
		return employeeVo;
	}
	
	@Override
	public int insertEmployee(EmployeeVo employeeVo) {
		return sqlSessionTemplate.insert("employee.insertEmployee",employeeVo);
	}
	
	@Override
	public int updateEmployee(EmployeeVo employeeVo) {
		return sqlSessionTemplate.update("employee.updateEmployee", employeeVo);
	}
	
	@Override
	public int updateManage(EmployeeVo employeeVo) {
		return sqlSessionTemplate.update("employee.updateManage", employeeVo);
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
		return sqlSessionTemplate.selectList("employee.selectAllList");
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
		return sqlSessionTemplate.selectList("employee.selectListSearch", employeeVo);
	}

	/**
	 * Method : select_Empfindid
	 * 작성자 : pc23
	 * 변경이력 :
	 * @param emp_nm,emp_phone
	 * @return
	 * Method 설명 : 아이디 찾을때 쓰는 메소드
	 */
	@Override
	public EmployeeVo select_Empfindid(EmployeeVo employeeVo) {
		
		return sqlSessionTemplate.selectOne("employee.select_emp_findid",employeeVo);
	}

	/**
	 * Method : selectEmpPagingList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 사원 페이지 리스트 조회
	 */
	@Override
	public List<EmployeeVo> selectEmpPagingList(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("employee.selectEmpPaging", map);
	}

	/**
	 * Method : getEmpCnt
	 * 작성자 : pc11
	 * 변경이력 :
	 * @return
	 * Method 설명 : 전체 사원수를 조회
	 */
	@Override
	public int getEmpCnt() {
		return sqlSessionTemplate.selectOne("employee.getEmpCnt");
	}

	/**
	 * Method : getSearchEmpCnt
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param employeeVo
	 * @return
	 * Method 설명 : 조건검색 사원수 조회
	 */
	@Override
	public int getSearchEmpCnt(EmployeeVo employeeVo) {
		return sqlSessionTemplate.selectOne("employee.getSearchEmpCnt", employeeVo);
	}
	
	/**
	 * Method : select_Empfindpw
	 * 작성자 : pc23
	 * 변경이력 :
	 * @param emp_nm,emp_phone
	 * @return
	 * Method 설명 : 비밀번호 찾을때 쓰는 메소드
	 */
	@Override
	public EmployeeVo select_Empfindpw(EmployeeVo employeeVo) {
		return sqlSessionTemplate.selectOne("employee.select_emp_findpw",employeeVo);
	}

	@Override
	public int updateEmpSign(EmployeeVo employeeVo) {
		return sqlSessionTemplate.update("employee.updateEmpSign", employeeVo);
	}

	@Override
	public int deleteEmp(EmployeeVo employeeVo) {
		return sqlSessionTemplate.update("employee.deleteEmp", employeeVo);
	}

	@Override
	public List<EmployeeVo> getAllEmp() {
		return sqlSessionTemplate.selectList("employee.getAllEmp");
	}

	@Override
	public int updateOn(EmployeeVo employeeVo) {
		return sqlSessionTemplate.update("employee.updateOn", employeeVo);
	}

}