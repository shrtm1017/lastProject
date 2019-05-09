package kr.or.nko.approval.dao;

import java.util.List;
import java.util.Map;

import kr.or.nko.approval.model.ApprovalDivVo;
import kr.or.nko.approval.model.ApprovalEleVo;
import kr.or.nko.approval.model.ApprovalLineVo;
import kr.or.nko.approval.model.ApprovalRefVo;
import kr.or.nko.approval.model.ApprovalVo;
import kr.or.nko.approval.model.SignalVo;
import kr.or.nko.employee.model.EmployeeVo;

public interface IApprovalDao {
	
	List<ApprovalEleVo> getAllApvEleList(Map<String, Object> map);
	
	List<ApprovalDivVo> getAllApvDiv();

	ApprovalDivVo selectApvDiv(int div_apv_sq);
	
	List<ApprovalLineVo> getAllApvLine(ApprovalLineVo apvLineVo);

	List<ApprovalVo> getAllApv();

	List<ApprovalVo> selectApvList(int apv_sign);
	
	List<ApprovalEleVo> selectApvEleList(Map<String, Object> map);

	int getApvEleCnt(int apv_emp_sq);
	
	ApprovalEleVo selectApvEle(int apv_sq);
	
	int insertApvEle(ApprovalEleVo apvEleVo);
	
	int insertApv(ApprovalVo apvVo);
	
	List<Map<String, Object>> select_Searchresult(String emp_nm);
	
	int updApvState(ApprovalVo apvVo);
	
	int updApvEleState(ApprovalEleVo apvEleVo);
	
	ApprovalVo selectApv(ApprovalVo apvVo);

	List<ApprovalVo> getAllApvList(ApprovalVo apvVo);
	
	/**
	 * Method : searchApvStartDate
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 내가 받은 결재 - 검색 (기안일, 결재상태, 문서분류, 문서명)
	 */
	List<ApprovalEleVo> searchApvStartDate(Map<String, Object> map);

	/**
	 * Method : searchApvSignDate
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 내가 받은 결재 - 검색 (결재일, 결재상태, 문서분류, 문서명)
	 */
	List<ApprovalEleVo> searchApvSignDate(Map<String, Object> map);
	
	/**
	 * Method : selectReference
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param apa_div_apv
	 * @return
	 * Method 설명 : 기안하기 - 수신참조자 조회
	 */
	List<EmployeeVo> selectReference(int apa_div_apv);
	
	/**
	 * Method : insertApvRef
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param apvRefVo
	 * @return
	 * Method 설명 : 기안하기 - 수신참조자 등록
	 */
	int insertApvRef(ApprovalRefVo apvRefVo);
	
	/**
	 * Method : getAllApvRef
	 * 작성자 : PC07
	 * 변경이력 :
	 * @return
	 * Method 설명 : 전체 수신참조자 목록 조회
	 */
	List<ApprovalRefVo> getAllApvRef();
	
	/**
	 * Method : selectApvRef
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 내가 받은 참조문서 조회
	 */
	List<ApprovalEleVo> selectApvRef(Map<String, Object> map);
	
	/**
	 * Method : getApvRefCnt
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param apr_emp_sq
	 * @return
	 * Method 설명 : 내가 받은 참조문서 count 조회
	 */
	int getApvRefCnt(int apr_emp_sq);
	
	/**
	 * Method : searchApvRefStartDate
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 참조문서함 - 검색 (기안일, 결재상태, 문서분류, 문서명)
	 */
	List<ApprovalEleVo> searchApvRefStartDate(Map<String, Object> map);
	
	/**
	 * Method : searchApvRefEndDate
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 참조문서함 - 검색 (최종결재일, 결재상태, 문서분류, 문서명)
	 */
	List<ApprovalEleVo> searchApvRefEndDate(Map<String, Object> map);
	
	/**
	 * Method : getApvRefCntStartDate
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 참조문서함 검색 count - 기안일
	 */
	int getApvRefCntStartDate(Map<String, Object> map);
	
	/**
	 * Method : getApvRefCntStartDate
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 참조문서함 검색 count - 결재종료일
	 */
	int getApvRefCntEndDate(Map<String, Object> map);
	
	/**
	 * Method : updateApvRefHit
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param apvRefVo
	 * @return
	 * Method 설명 : 참조문서함 - 문서 조회 여부 업데이트
	 */
	int updateApvRefHit(ApprovalRefVo apvRefVo);
	
	/**
	 * Method : searchApvEleStartDate
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 내가 올린 결재 - 검색 (기안일, 결재상태, 문서분류, 문서명)
	 */
	List<ApprovalEleVo> searchApvEleStartDate(Map<String, Object> map);
	
	/**
	 * Method : searchApvEleEndDate
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 내가 올린 결재 - 검색 (결재종료일, 결재상태, 문서분류, 문서명)
	 */
	List<ApprovalEleVo> searchApvEleEndDate(Map<String, Object> map);
	
	/**
	 * Method : getApvEleCntStartDate
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 내가 올린 결재 검색 count - 기안일
	 */
	int getApvEleCntStartDate(Map<String, Object> map);
	
	/**
	 * Method : getApvEleCntEndDate
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 내가 올린 결재 검색 count - 결재종료일
	 */
	int getApvEleCntEndDate(Map<String, Object> map);
	
	/**
	 * Method : insertSignal
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param signalVo
	 * @return
	 * Method 설명 : 알림 등록
	 */
	int insertSignal(SignalVo signalVo);
	
	/**
	 * Method : deleteSignal
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param sig_sq
	 * @return
	 * Method 설명 : 알림 삭제
	 */
	int deleteSignal(int sig_sq);
	
	/**
	 * Method : selectSignal
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param sig_emp_sq
	 * @return
	 * Method 설명 : 알림 조회
	 */
	List<SignalVo> selectSignal(int sig_emp_sq);
	
	/**
	 * Method : selectSignalSq
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param sig_sq
	 * @return
	 * Method 설명 : 특정 알림 조회
	 */
	SignalVo selectSignalSq(int sig_sq);
	
	/**
	 * Method : selectAllApvLine
	 * 작성자 : PC23
	 * 변경이력 :
	 * @param apa_div_apv
	 * @return
	 * Method 설명 : 결재라인 전체조회
	 */
	List<ApprovalLineVo> selectAllApvLine(int apa_div_apv);
	
	/**
	 * Method : insertApprovalLine
	 * 작성자 : PC23
	 * 변경이력 :
	 * @return
	 * Method 설명 : 결재라인 추가 
	 */
	int insertApvLine(ApprovalLineVo approvalLineVo);
	
	/**
	 * Method : updateApvLine
	 * 작성자 : PC23
	 * 변경이력 :
	 * @return
	 * Method 설명 : 결재라인 수정 
	 */
	int updateApvLine(ApprovalLineVo approvalLineVo);
	
	/**
	 * Method : deleteApvLine
	 * 작성자 : PC23
	 * 변경이력 :
	 * @return
	 * Method 설명 : 결재라인 삭제 (아직 명확하지않아서 잘모르겠음 ..ㄷㄷㄷ)
	 */
	int deleteApvLine(int div_sq);
	
	/**
	 * Method : searchAllApvEleStartDate
	 * 작성자 : PC23
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 전체문서함 - 검색 (기안일, 결재상태, 문서분류, 문서명)
	 */
	List<ApprovalEleVo> searchAllApvEleStartDate(Map<String, Object> map);
	
	/**
	 * Method : searchAllApvEleEndDate
	 * 작성자 : PC23
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 전체문서함 - 검색 (결재종료일, 결재상태, 문서분류, 문서명)
	 */
	List<ApprovalEleVo> searchAllApvEleEndDate(Map<String, Object> map);
	
	/**
	 * Method : selectEmpAllList
	 * 작성자 : PC23
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 부서에 따른 회원 조회
	 */
	List<EmployeeVo> selectEmpAllList(int hr_sq);
	
	/**
	 * Method : selectSignList
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param apv_sign
	 * @return
	 * Method 설명 : 내가 받은 결재 목록
	 */
	List<ApprovalEleVo> selectSignList(int apv_sign);
	
	/**
	 * Method : selectSignListRef
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param apv_sign
	 * @return
	 * Method 설명 : 내가 받은 결재 문서 - 수신참조자 목록
	 */
	List<ApprovalRefVo> selectSignListRef(int apv_sign);
	
	/**
	 * Method : selectApvRefEmp
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param apr_sq
	 * @return
	 * Method 설명 : 수신참조자 사원 정보
	 */
	List<EmployeeVo> selectApvRefEmp(int apr_sq);
	
	/**
	 * Method : getApvRef
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param apr_sq
	 * @return
	 * Method 설명 : 문서별 수신참조자 정보 : 사원번호순
	 */
	List<ApprovalRefVo> getApvRef(int apr_sq);

	/**
	 * Method : getAllApvEmpList
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param apv_sq
	 * @return
	 * Method 설명 : 결재라인 정보 : 사원명
	 */
	List<EmployeeVo> getAllApvEmpList(int apv_sq);
	
	/**
	 * Method : getAllApvEleCnt
	 * 작성자 : PC07
	 * 변경이력 :
	 * @return
	 * Method 설명 : 전체문서함 count 조회
	 */
	int getAllApvEleCnt();
	
	/**
	 * Method : searchAllApvEleCntStartDate
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 전체문서함 검색 count - 기안일
	 */
	int searchAllApvEleCntStartDate(Map<String, Object> map);

	/**
	 * Method : searchAllApvEleCntEndDate
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 전체문서함 검색 count - 결재종료일
	 */
	int searchAllApvEleCntEndDate(Map<String, Object> map);
	
	/**
	 * Method : getApvStateCnt
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param apvEleVo
	 * @return
	 * Method 설명 : 내가 올린 결재 - 상태별 count 
	 */
	int getApvStateCnt(ApprovalEleVo apvEleVo);
	
}
