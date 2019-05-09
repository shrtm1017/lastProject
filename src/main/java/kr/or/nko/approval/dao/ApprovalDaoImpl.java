package kr.or.nko.approval.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.approval.model.ApprovalDivVo;
import kr.or.nko.approval.model.ApprovalEleVo;
import kr.or.nko.approval.model.ApprovalLineVo;
import kr.or.nko.approval.model.ApprovalRefVo;
import kr.or.nko.approval.model.ApprovalVo;
import kr.or.nko.approval.model.SignalVo;
import kr.or.nko.employee.model.EmployeeVo;

@Repository("approvalDao")
public class ApprovalDaoImpl implements IApprovalDao {

	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<ApprovalDivVo> getAllApvDiv() {
		return sqlSessionTemplate.selectList("approval.getAllApvDiv");
	}

	@Override
	public ApprovalDivVo selectApvDiv(int div_apv_sq) {
		return sqlSessionTemplate.selectOne("approval.selectApvDiv", div_apv_sq);
	}
	
	@Override
	public List<ApprovalLineVo> getAllApvLine(ApprovalLineVo apvLineVo) {
		return sqlSessionTemplate.selectList("approval.getAllApvLine", apvLineVo);
	}

	@Override
	public List<ApprovalVo> getAllApv() {
		return sqlSessionTemplate.selectList("approval.getAllApv");
	}
	
	@Override
	public List<ApprovalVo> selectApvList(int apv_sign) {
		return sqlSessionTemplate.selectList("approval.selectApvList", apv_sign);
	}

	@Override
	public List<ApprovalEleVo> selectApvEleList(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("approval.selectApvEleList", map);
	}
	
	@Override
	public int getApvEleCnt(int apv_emp_sq) {
		return sqlSessionTemplate.selectOne("approval.getApvEleCnt", apv_emp_sq);
	}
	
	@Override
	public ApprovalEleVo selectApvEle(int apv_sq) {
		return sqlSessionTemplate.selectOne("approval.selectApvEle", apv_sq);
	}
	
	@Override
	public int insertApvEle(ApprovalEleVo apvEleVo) {
		return sqlSessionTemplate.insert("approval.insertApvEle", apvEleVo);
	}
	
	@Override
	public int insertApv(ApprovalVo apvVo) {
		return sqlSessionTemplate.insert("approval.insertApv", apvVo);
	}
	
	@Override
	public List<Map<String, Object>> select_Searchresult(String emp_nm) {
		return sqlSessionTemplate.selectList("approval.select_SearchResult",emp_nm);
	}

	@Override
	public int updApvState(ApprovalVo apvVo) {
		return sqlSessionTemplate.update("approval.updApvState", apvVo);
	}

	@Override
	public int updApvEleState(ApprovalEleVo apvEleVo) {
		return sqlSessionTemplate.update("approval.updApvEleState", apvEleVo);
	}

	@Override
	public ApprovalVo selectApv(ApprovalVo apvVo) {
		return sqlSessionTemplate.selectOne("approval.selectApv", apvVo);
	}

	@Override
	public List<ApprovalVo> getAllApvList(ApprovalVo apvVo) {
		return sqlSessionTemplate.selectList("approval.getAllApvList", apvVo);
	}

	@Override
	public List<ApprovalEleVo> searchApvStartDate(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("approval.searchApvStartDate", map);
	}

	@Override
	public List<ApprovalEleVo> searchApvSignDate(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("approval.searchApvSignDate", map);
	}

	@Override
	public List<EmployeeVo> selectReference(int apa_div_apv) {
		return sqlSessionTemplate.selectList("approval.selectReference", apa_div_apv);
	}

	@Override
	public int insertApvRef(ApprovalRefVo apvRefVo) {
		return sqlSessionTemplate.insert("approval.insertApvRef", apvRefVo);
	}

	@Override
	public List<ApprovalRefVo> getAllApvRef() {
		return sqlSessionTemplate.selectList("approval.getAllApvRef");
	}

	@Override
	public List<ApprovalEleVo> selectApvRef(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("approval.selectApvRef", map);
	}

	@Override
	public int getApvRefCnt(int apr_emp_sq) {
		return sqlSessionTemplate.selectOne("approval.getApvRefCnt", apr_emp_sq);
	}
	
	@Override
	public List<ApprovalEleVo> searchApvRefStartDate(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("approval.searchApvRefStartDate", map);
	}
	
	@Override
	public List<ApprovalEleVo> searchApvRefEndDate(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("approval.searchApvRefEndDate", map);
	}
	
	@Override
	public int getApvRefCntStartDate(Map<String, Object> map) {
		return sqlSessionTemplate.selectOne("approval.getApvRefCntStartDate", map);
	}
	
	@Override
	public int getApvRefCntEndDate(Map<String, Object> map) {
		return sqlSessionTemplate.selectOne("approval.getApvRefCntEndDate", map);
	}
	
	@Override
	public int updateApvRefHit(ApprovalRefVo apvRefVo) {
		return sqlSessionTemplate.update("approval.updateApvRefHit", apvRefVo);
	}
	
	@Override
	public List<ApprovalEleVo> searchApvEleStartDate(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("approval.searchApvEleStartDate", map);
	}
	
	@Override
	public List<ApprovalEleVo> searchApvEleEndDate(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("approval.searchApvEleEndDate", map);
	}
	
	@Override
	public int getApvEleCntStartDate(Map<String, Object> map) {
		return sqlSessionTemplate.selectOne("approval.getApvEleCntStartDate", map);
	}
	
	@Override
	public int getApvEleCntEndDate(Map<String, Object> map) {
		return sqlSessionTemplate.selectOne("approval.getApvEleCntEndDate", map);
	}
	
	@Override
	public int insertSignal(SignalVo signalVo) {
		return sqlSessionTemplate.insert("approval.insertSignal", signalVo);
	}
	
	@Override
	public int deleteSignal(int sig_sq) {
		return sqlSessionTemplate.delete("approval.deleteSignal", sig_sq);
	}
	
	@Override
	public List<SignalVo> selectSignal(int sig_emp_sq) {
		return sqlSessionTemplate.selectList("approval.selectSignal", sig_emp_sq);
	}
	
	@Override
	public SignalVo selectSignalSq(int sig_sq) {
		return sqlSessionTemplate.selectOne("approval.selectSignalSq", sig_sq);
	}

	@Override
	public List<ApprovalLineVo> selectAllApvLine(int apa_div_apv) {
		
		return sqlSessionTemplate.selectList("approval.selectAllApvLine",apa_div_apv);
	}

	@Override
	public int insertApvLine(ApprovalLineVo approvalLineVo) {
		return sqlSessionTemplate.insert("approval.insertApvLine", approvalLineVo);
	}

	@Override
	public int updateApvLine(ApprovalLineVo approvalLineVo) {
		return sqlSessionTemplate.update("approval.updateApvLine", approvalLineVo);
	}

	@Override
	public int deleteApvLine(int div_sq) {
		
		return sqlSessionTemplate.delete("approval.deleteApvLine",div_sq);
	}
	@Override
	public List<ApprovalEleVo> getAllApvEleList(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("approval.getAllApvEleList", map);
	}

	@Override
	public List<ApprovalEleVo> searchAllApvEleStartDate(Map<String, Object> map) {
		
		return sqlSessionTemplate.selectList("approval.searchAllApvEleStartDate", map);
	}

	@Override
	public List<ApprovalEleVo> searchAllApvEleEndDate(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("approval.searchAllApvEleEndDate", map);
	}

	@Override
	public List<EmployeeVo> selectEmpAllList(int hr_sq) {
		return sqlSessionTemplate.selectList("approval.selectEmpAllList", hr_sq);
	}

	@Override
	public List<ApprovalEleVo> selectSignList(int apv_sign) {
		return sqlSessionTemplate.selectList("approval.selectSignList", apv_sign);
	}

	@Override
	public List<ApprovalRefVo> selectSignListRef(int apv_sign) {
		return sqlSessionTemplate.selectList("approval.selectSignListRef", apv_sign);
	}

	@Override
	public List<EmployeeVo> selectApvRefEmp(int apr_sq) {
		return sqlSessionTemplate.selectList("approval.selectApvRefEmp", apr_sq);
	}

	@Override
	public List<ApprovalRefVo> getApvRef(int apr_sq) {
		return sqlSessionTemplate.selectList("approval.getApvRef", apr_sq);
	}

	@Override
	public List<EmployeeVo> getAllApvEmpList(int apv_sq) {
		return sqlSessionTemplate.selectList("approval.getAllApvEmpList", apv_sq);
	}

	@Override
	public int getAllApvEleCnt() {
		return sqlSessionTemplate.selectOne("approval.getAllApvEleCnt");
	}

	@Override
	public int searchAllApvEleCntStartDate(Map<String, Object> map) {
		return sqlSessionTemplate.selectOne("approval.searchAllApvEleCntStartDate", map);
	}

	@Override
	public int searchAllApvEleCntEndDate(Map<String, Object> map) {
		return sqlSessionTemplate.selectOne("approval.searchAllApvEleCntEndDate", map);
	}

	@Override
	public int getApvStateCnt(ApprovalEleVo apvEleVo) {
		return sqlSessionTemplate.selectOne("approval.getApvStateCnt", apvEleVo);
	}

}
