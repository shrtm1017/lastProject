package kr.or.nko.employeecontactmanage.service;

import java.util.List;
import java.util.Map;

import kr.or.nko.employeecontactmanage.model.EmployeeContactManageVo;
import kr.or.nko.util.model.PageVo;

public interface IEmployeeContactManageService {
	public  Map<String,Object> EmployeeContactPagingList(PageVo pageVo ,EmployeeContactManageVo EmployeeContactManageVo);
	public List<EmployeeContactManageVo> EmployeeContactAllList();
	public Map<String, Object> EmployeeContact_Serch(PageVo pageVo ,EmployeeContactManageVo EmployeeContactManageVo);
//	public int Notice_Register(EmployeeContactManageVo EmployeeContactManageVo);
	public int EmployeeContactManageUpdate(EmployeeContactManageVo EmployeeContactManageVo);
	public int EmployeeContactManageDelete(int emp_Sq);
	public EmployeeContactManageVo Selectemployeecontact(int emp_Sq);

}
