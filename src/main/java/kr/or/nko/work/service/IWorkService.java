package kr.or.nko.work.service;

import java.util.List;
import java.util.Map;

import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.work.model.ProjectMemVo;
import kr.or.nko.work.model.ProjectVo;
import kr.or.nko.work.model.WorkFileVo;
import kr.or.nko.work.model.WorkHistoryVo;
import kr.or.nko.work.model.WorkRelationVo;
import kr.or.nko.work.model.WorkVo;

public interface IWorkService {
	/**
	 * Method : insertProject
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param projectVo
	 * @return
	 * Method 설명 : 프로젝트 등록
	 */
	public int insertProject(ProjectVo projectVo);
	
	/**
	 * Method : insertMember
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param projectMemVo
	 * @return
	 * Method 설명 : 프로젝트 멤버 등록
	 */
	public int insertMember(ProjectMemVo projectMemVo);
	
	/**
	 * Method : selectAllProjectList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @return
	 * Method 설명 : 프로젝트 전체 조회
	 */
	public List<ProjectVo> selectAllProjectList();
	
	/**
	 * Method : selectMemberList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param pro_sq
	 * @return
	 * Method 설명 : 프로젝트에 해당하는 멤버 조회
	 */
	public List<EmployeeVo> selectMemberList(int pro_sq);
	
	/**
	 * Method : selectMemberProjectList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param pro_mem
	 * @return
	 * Method 설명 : 멤버에 해당하는 프로젝트 조회
	 */
	public List<ProjectVo> selectProjectList(int pro_mem);
	
	/**
	 * Method : selectProject
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param pro_sq
	 * @return
	 * Method 설명 : 프로젝트 조회
	 */
	public ProjectVo selectProject(int pro_sq);
	
	/**
	 * Method : selectWorkList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 상위업무조회(자신을 포함한 상위업무만 조회되며 하위업무는 조회되지 않음)
	 */
	public List<WorkVo> selectWorkList(Map<String, Object> map);
	
	/**
	 * Method : insertWork
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param workVo
	 * @return
	 * Method 설명 : 업무등록
	 */
	public int insertWork(WorkVo workVo);
	
	/**
	 * Method : insertFile
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param workFileVo
	 * @return
	 * Method 설명 : 업무파일등록
	 */
	public int insertFile(WorkFileVo workFileVo);
	
	/**
	 * Method : selectFile
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param wk_fl_sq
	 * @return
	 * Method 설명 : 업무파일번호로 파일조회
	 */
	public WorkFileVo selectFile(int wk_fl_sq);
	
	/**
	 * Method : insertRelation
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param workRelationVo
	 * @return
	 * Method 설명 : 업무관련사원 등록
	 */
	public int insertRelation(WorkRelationVo workRelationVo);
	
	/**
	 * Method : insertHistory
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param workHistoryVo
	 * @return
	 * Method 설명 : 업무 작업내역 등록
	 */
	public int insertHistory(WorkHistoryVo workHistoryVo);
	
	/**
	 * Method : selectHistoryMap
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param his_wk_sq
	 * @return
	 * Method 설명 : 작업내역과 관련된 데이터조회(조건)
	 */
	public List<Map<String, Object>> selectHistoryMapList(Map<String, Object> map);
	
	/**
	 * Method : selectWorkMap
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 업무와 관련된 데이터조회(조건검색)
	 */
	public List<Map<String, Object>> selectWorkMap(Map<String, Object> map);
	
	/**
	 * Method : selectWorkPagingMap
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 업무 페이징 조회 및 업무수 조회
	 */
	public Map<String, Object> selectWorkPagingMap(Map<String, Object> map);
	
	/**
	 * Method : selectWork
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param wk_sq
	 * @return
	 * Method 설명 : 업무번호에 해당하는 업무조회
	 */
	public Map<String, Object> selectWork(int wk_sq);
	
	/**
	 * Method : updateWork
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param workVo
	 * @return
	 * Method 설명 : 업무수정
	 */
	public int updateWork(WorkVo workVo);
	
	/**
	 * Method : updateRelation
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param workRelationVo
	 * @return
	 * Method 설명 : 업무관련 수정
	 */
	public int updateRelation(WorkRelationVo workRelationVo);
	
	/**
	 * Method : selectFileList
	 * 작성자 : lee
	 * 변경이력 :
	 * @param wk_sq
	 * @return
	 * Method 설명 : 업무 첨부파일 리스트 조회
	 */
	public List<WorkFileVo> selectFileList(int wk_sq);
	
	/**
	 * Method : deleteFile
	 * 작성자 : lee
	 * 변경이력 :
	 * @param wk_fl_sq
	 * @return
	 * Method 설명 : 업부 첨부파일 삭제
	 */
	public int deleteFile(int wk_fl_sq);
	
	/**
	 * Method : selectCount
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param pro_sq
	 * @return
	 * Method 설명 : 프로젝트번호로 type, state별 업무수 조회
	 */
	public Map<String, Object> selectCount(int pro_sq);
	
	/**
	 * Method : deleteAllRelation
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param wk_sq
	 * @return
	 * Method 설명 : 업무관련 삭제
	 */
	public int deleteAllRelation(int wk_sq);
	
	/**
	 * Method : deleteAllFile
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param wk_sq
	 * @return
	 * Method 설명 : 업무첨부파일 모두삭제
	 */
	public int deleteAllFile(int wk_sq);
	
	/**
	 * Method : deleteAllHistory
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param wk_sq
	 * @return
	 * Method 설명 : 업무작업내역 모두삭제
	 */
	public int deleteAllHistory(int wk_sq);
	
	/**
	 * Method : deleteAllWork
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param wk_sq
	 * @return
	 * Method 설명 : 업무(하위업무 포함) 모두삭제
	 */
	public int deleteAllWork(int wk_sq);
	
	/**
	 * Method : selectHistoryList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param his_wk_sq
	 * @return
	 * Method 설명 : 해당 업무 작업내역 리스트 조회
	 */
	public List<Map<String, Object>> selectHistoryList(int his_wk_sq);
	
	/**
	 * Method : selectGanttMap
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 간트차트에 표시될 내용 계층형으로 조회
	 */
	public List<Map<String, Object>> selectGanttMap(Map<String, Object> map);
}