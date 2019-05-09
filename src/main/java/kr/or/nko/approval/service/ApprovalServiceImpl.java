package kr.or.nko.approval.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.approval.dao.IApprovalDao;
import kr.or.nko.approval.model.ApprovalDivVo;
import kr.or.nko.approval.model.ApprovalEleVo;
import kr.or.nko.approval.model.ApprovalLineVo;
import kr.or.nko.approval.model.ApprovalRefVo;
import kr.or.nko.approval.model.ApprovalVo;
import kr.or.nko.approval.model.SignalVo;
import kr.or.nko.employee.model.EmployeeVo;

@Service("approvalService")
public class ApprovalServiceImpl implements IApprovalService {

	@Resource(name="approvalDao")
	private IApprovalDao approvalDao;
	
	@Override
	public List<ApprovalDivVo> getAllApvDiv() {
		return approvalDao.getAllApvDiv();
	}

	@Override
	public ApprovalDivVo selectApvDiv(int div_apv_sq) {
		return approvalDao.selectApvDiv(div_apv_sq);
	}
	
	@Override
	public List<ApprovalLineVo> getAllApvLine(ApprovalLineVo apvLineVo) {
		return approvalDao.getAllApvLine(apvLineVo);
	}
	
	@Override
	public int insertApvEle(ApprovalEleVo apvEleVo) {
		return approvalDao.insertApvEle(apvEleVo);
	}
	
	@Override
	public List<ApprovalVo> getAllApv() {
		return approvalDao.getAllApv();
	}
	
	@Override
	public List<ApprovalVo> selectApvList(int apv_sign) {
		return approvalDao.selectApvList(apv_sign);
	}
	
	@Override
	public List<ApprovalEleVo> selectApvEleList(Map<String, Object> map) {
		return approvalDao.selectApvEleList(map);
	}
	
	@Override
	public int getApvEleCnt(int apv_emp_sq) {
		return approvalDao.getApvEleCnt(apv_emp_sq);
	}

	@Override
	public ApprovalEleVo selectApvEle(int apv_sq) {
		return approvalDao.selectApvEle(apv_sq);
	}
	
	@Override
	public int insertApv(ApprovalVo apvVo) {
		return approvalDao.insertApv(apvVo);
	}
	
	@Override
	public List<Map<String, Object>> select_Searchresult(String emp_nm) {
		return approvalDao.select_Searchresult(emp_nm);
	}

	@Override
	public int updApvState(ApprovalVo apvVo) {
		return approvalDao.updApvState(apvVo);
	}

	@Override
	public int updApvEleState(ApprovalEleVo apvEleVo) {
		return approvalDao.updApvEleState(apvEleVo);
	}

	@Override
	public ApprovalVo selectApv(ApprovalVo apvVo) {
		return approvalDao.selectApv(apvVo);
	}

	@Override
	public List<ApprovalVo> getAllApvList(ApprovalVo apvVo) {
		return approvalDao.getAllApvList(apvVo);
	}

	@Override
	public List<ApprovalEleVo> searchApvStartDate(Map<String, Object> map) {
		return approvalDao.searchApvStartDate(map);
	}

	@Override
	public List<ApprovalEleVo> searchApvSignDate(Map<String, Object> map) {
		return approvalDao.searchApvSignDate(map);
	}

	@Override
	public List<EmployeeVo> selectReference(int apa_div_apv) {
		return approvalDao.selectReference(apa_div_apv);
	}

	@Override
	public int insertApvRef(ApprovalRefVo apvRefVo) {
		return approvalDao.insertApvRef(apvRefVo);
	}

	@Override
	public List<ApprovalRefVo> getAllApvRef() {
		return approvalDao.getAllApvRef();
	}

	@Override
	public List<ApprovalEleVo> selectApvRef(Map<String, Object> map) {
		return approvalDao.selectApvRef(map);
	}
	
	@Override
	public int getApvRefCnt(int apr_emp_sq) {
		return approvalDao.getApvRefCnt(apr_emp_sq);
	}
	
	@Override
	public List<ApprovalEleVo> searchApvRefStartDate(Map<String, Object> map) {
		return approvalDao.searchApvRefStartDate(map);
	}

	@Override
	public List<ApprovalEleVo> searchApvRefEndDate(Map<String, Object> map) {
		return approvalDao.searchApvRefEndDate(map);
	}
	
	@Override
	public int getApvRefCntStartDate(Map<String, Object> map) {
		return approvalDao.getApvRefCntStartDate(map);
	}
	
	@Override
	public int getApvRefCntEndDate(Map<String, Object> map) {
		return approvalDao.getApvRefCntEndDate(map);
	}
	
	@Override
	public int updateApvRefHit(ApprovalRefVo apvRefVo) {
		return approvalDao.updateApvRefHit(apvRefVo);
	}
	
	@Override
	public List<ApprovalEleVo> searchApvEleStartDate(Map<String, Object> map) {
		return approvalDao.searchApvEleStartDate(map);
	}
	
	@Override
	public List<ApprovalEleVo> searchApvEleEndDate(Map<String, Object> map) {
		return approvalDao.searchApvEleEndDate(map);
	}
	
	@Override
	public int getApvEleCntStartDate(Map<String, Object> map) {
		return approvalDao.getApvEleCntStartDate(map);
	}
	
	@Override
	public int getApvEleCntEndDate(Map<String, Object> map) {
		return approvalDao.getApvEleCntEndDate(map);
	}
	
	@Override
	public int insertSignal(SignalVo signalVo) {
		return approvalDao.insertSignal(signalVo);
	}
	
	@Override
	public int deleteSignal(int sig_sq) {
		return approvalDao.deleteSignal(sig_sq);
	}
	
	@Override
	public List<SignalVo> selectSignal(int sig_emp_sq) {
		return approvalDao.selectSignal(sig_emp_sq);
	}
	
	@Override
	public SignalVo selectSignalSq(int sig_sq) {
		return approvalDao.selectSignalSq(sig_sq);
	}
	
	@Override
	public List<ApprovalLineVo> selectAllApvLine(int apa_div_apv) {
		return approvalDao.selectAllApvLine(apa_div_apv);
	}

	@Override
	public int insertApvLine(ApprovalLineVo approvalLineVo) {
		return approvalDao.insertApvLine(approvalLineVo);
	}

	@Override
	public int updateApvLine(ApprovalLineVo approvalLineVo) {
		return approvalDao.updateApvLine(approvalLineVo);
	}

	@Override
	public int deleteApvLine(int div_sq) {
		return approvalDao.deleteApvLine(div_sq);
	}
	@Override
	public List<ApprovalEleVo> getAllApvEleList(Map<String, Object> map) {
		return approvalDao.getAllApvEleList(map);
	}

	@Override
	public List<ApprovalEleVo> searchAllApvEleStartDate(Map<String, Object> map) {
		return approvalDao.searchAllApvEleStartDate(map);
	}

	@Override
	public List<ApprovalEleVo> searchAllApvEleEndDate(Map<String, Object> map) {
		return approvalDao.searchAllApvEleEndDate(map);
	}

	@Override
	public List<EmployeeVo> selectEmpAllList(int hr_sq) {
		return approvalDao.selectEmpAllList(hr_sq);
	}

	@Override
	public List<ApprovalEleVo> selectSignList(int apv_sign) {
		return approvalDao.selectSignList(apv_sign);
	}

	@Override
	public List<ApprovalRefVo> selectSignListRef(int apv_sign) {
		return approvalDao.selectSignListRef(apv_sign);
	}

	@Override
	public List<EmployeeVo> selectApvRefEmp(int apr_sq) {
		return approvalDao.selectApvRefEmp(apr_sq);
	}

	@Override
	public List<ApprovalRefVo> getApvRef(int apr_sq) {
		return approvalDao.getApvRef(apr_sq);
	}

	@Override
	public List<EmployeeVo> getAllApvEmpList(int apv_sq) {
		return approvalDao.getAllApvEmpList(apv_sq);
	}

	@Override
	public int getAllApvEleCnt() {
		return approvalDao.getAllApvEleCnt();
	}

	@Override
	public int searchAllApvEleCntStartDate(Map<String, Object> map) {
		return approvalDao.searchAllApvEleCntStartDate(map);
	}

	@Override
	public int searchAllApvEleCntEndDate(Map<String, Object> map) {
		return approvalDao.searchAllApvEleCntEndDate(map);
	}

	@Override
	public int getApvStateCnt(ApprovalEleVo apvEleVo) {
		return approvalDao.getApvStateCnt(apvEleVo);
	}

}