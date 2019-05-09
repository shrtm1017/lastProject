package kr.or.nko.employeecontactmanage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.nko.employeecontactmanage.dao.IEmployeeContactManageDao;
import kr.or.nko.employeecontactmanage.model.EmployeeContactManageVo;
import kr.or.nko.util.model.PageVo;

@Service("employeeContactService")
public class EmployeeContactManageServiceImpl implements IEmployeeContactManageService{
	@Resource(name="employeeContactDao")
	private IEmployeeContactManageDao employeeContactManageDao;
	private Logger logger = LoggerFactory.getLogger(EmployeeContactManageServiceImpl.class);

	@Override
	public  Map<String,Object>EmployeeContactPagingList(PageVo pageVo ,EmployeeContactManageVo EmployeeContactManageVo) {
		
		Map<String, Object> EmployeeContactPagingList = new HashMap<String, Object>();
		EmployeeContactPagingList.put("page",pageVo.getPage());
		EmployeeContactPagingList.put("pageSize", pageVo.getPageSize());
		EmployeeContactPagingList.put("PagingListCnt", employeeContactManageDao.EmployeeContactManageCnt());
		EmployeeContactPagingList.put("PagingList", employeeContactManageDao.EmployeeContactPagingList(EmployeeContactPagingList));

		return EmployeeContactPagingList;
	}

	@Override
	public List<EmployeeContactManageVo> EmployeeContactAllList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> EmployeeContact_Serch(PageVo pageVo ,EmployeeContactManageVo EmployeeContactManageVo) {
		Map<String, Object> EmployeeContact_Serch = new HashMap<String, Object>();
		EmployeeContact_Serch.put("page",pageVo.getPage());
		EmployeeContact_Serch.put("pageSize", pageVo.getPageSize());
		EmployeeContact_Serch.put("emp_nm", EmployeeContactManageVo.getEmp_nm());
		EmployeeContact_Serch.put("emp_sq", EmployeeContactManageVo.getEmp_sq());
		logger.debug("EmployeeContactManageVo.getEmp_sq()"+EmployeeContactManageVo.getEmp_sq());
		logger.debug("EmployeeContactManageVo.getEmp_nm()"+EmployeeContactManageVo.getEmp_nm());
		EmployeeContact_Serch.put("PagingListSerchCnt", employeeContactManageDao.EmployeeContactManageSerchCnt(EmployeeContactManageVo));
		EmployeeContact_Serch.put("PagingList", employeeContactManageDao.EmployeeContact_Serch(EmployeeContact_Serch));

		return EmployeeContact_Serch;
	}


	@Override
	public int EmployeeContactManageUpdate(EmployeeContactManageVo EmployeeContactManageVo) {
		int EmployeeContactManageUpdate =employeeContactManageDao.EmployeeContactManageUpdate(EmployeeContactManageVo);
		return EmployeeContactManageUpdate;
	}

	@Override
	public int EmployeeContactManageDelete(int emp_Sq) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EmployeeContactManageVo Selectemployeecontact(int emp_Sq) {
		EmployeeContactManageVo Selectemployeecontact =employeeContactManageDao.Selectemployeecontact(emp_Sq);
		return Selectemployeecontact;
	}

}
