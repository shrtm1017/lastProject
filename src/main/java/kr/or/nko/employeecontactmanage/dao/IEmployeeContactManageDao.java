package kr.or.nko.employeecontactmanage.dao;

import java.util.List;
import java.util.Map;

import kr.or.nko.employeecontactmanage.model.EmployeeContactManageVo;

public interface IEmployeeContactManageDao {
	public List<EmployeeContactManageVo> EmployeeContactPagingList(Map<String, Object> map);
	public List<EmployeeContactManageVo> EmployeeContactAllList();
	public List<EmployeeContactManageVo> EmployeeContact_Serch(Map<String, Object> map);
//	public int Notice_Register(EmployeeContactManageVo EmployeeContactManageVo);
	public int EmployeeContactManageCnt();
	public int EmployeeContactManageSerchCnt(EmployeeContactManageVo EmployeeContactManageVo);
	public int EmployeeContactManageUpdate(EmployeeContactManageVo EmployeeContactManageVo);
	public EmployeeContactManageVo Selectemployeecontact(int emp_Sq);
}
