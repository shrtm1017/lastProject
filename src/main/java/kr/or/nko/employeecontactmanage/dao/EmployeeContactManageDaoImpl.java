package kr.or.nko.employeecontactmanage.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.employeecontactmanage.model.EmployeeContactManageVo;

@Repository("employeeContactDao")
public class EmployeeContactManageDaoImpl implements IEmployeeContactManageDao {
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<EmployeeContactManageVo> EmployeeContactPagingList(Map<String, Object> map) {
		List<EmployeeContactManageVo> EmployeeContactPagingList = sqlSessionTemplate
				.selectList("employeecontact.employeecontactPagingList", map);
		return EmployeeContactPagingList;
	}

	@Override
	public List<EmployeeContactManageVo> EmployeeContactAllList() {
		List<EmployeeContactManageVo> EmployeeContactPagingList = sqlSessionTemplate
				.selectList("employeecontact.employeecontactAllList");
		return EmployeeContactPagingList;
	}

	@Override
	public List<EmployeeContactManageVo> EmployeeContact_Serch(Map<String, Object> map) {
		List<EmployeeContactManageVo> EmployeeContact_Serch = sqlSessionTemplate
				.selectList("employeecontact.SerchEmployeecontactList",map);
		return EmployeeContact_Serch;
	}

	@Override
	public int EmployeeContactManageCnt() {
		int EmployeeContactManageCnt = sqlSessionTemplate.selectOne("employeecontact.employeecontactListCnt");
		return EmployeeContactManageCnt;
	}

	@Override
	public int EmployeeContactManageSerchCnt(EmployeeContactManageVo EmployeeContactManageVo) {
		int EmployeeContactManageCnt = sqlSessionTemplate.selectOne("employeecontact.EmployeeContactManageSerchCnt",EmployeeContactManageVo);
		return EmployeeContactManageCnt;
	}

	@Override
	public int EmployeeContactManageUpdate(EmployeeContactManageVo EmployeeContactManageVo) {
		int EmployeeContactManageUpdate = sqlSessionTemplate.update("employeecontact.updateemployeecontact",
				EmployeeContactManageVo);
		return EmployeeContactManageUpdate;
	}

	@Override
	public EmployeeContactManageVo Selectemployeecontact(int emp_Sq) {
		EmployeeContactManageVo Selectemployeecontact = sqlSessionTemplate
				.selectOne("employeecontact.selectemployeecontact", emp_Sq);
		return Selectemployeecontact;
	}

}
