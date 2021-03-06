package kr.or.nko.work.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.work.dao.IWorkDao;
import kr.or.nko.work.model.ProjectMemVo;
import kr.or.nko.work.model.ProjectVo;
import kr.or.nko.work.model.WorkFileVo;
import kr.or.nko.work.model.WorkHistoryVo;
import kr.or.nko.work.model.WorkRelationVo;
import kr.or.nko.work.model.WorkVo;

@Service("workService")
public class WorkServiceImpl implements IWorkService{

	@Resource(name="workDao")
	private IWorkDao workDao;
	
	/**
	 * Method : insertProject
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param projectVo
	 * @return
	 * Method 설명 : 프로젝트 등록
	 */
	@Override
	public int insertProject(ProjectVo projectVo) {
		return workDao.insertProject(projectVo);
	}

	/**
	 * Method : insertMember
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param projectMemVo
	 * @return
	 * Method 설명 : 프로젝트 멤버 등록
	 */
	@Override
	public int insertMember(ProjectMemVo projectMemVo) {
		return workDao.insertMember(projectMemVo);
	}

	/**
	 * Method : selectAllProjectList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @return
	 * Method 설명 : 프로젝트 전체 조회
	 */
	@Override
	public List<ProjectVo> selectAllProjectList() {
		return workDao.selectAllProjectList();
	}

	/**
	 * Method : selectMemberList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param pro_sq
	 * @return
	 * Method 설명 : 프로젝트에 해당하는 멤버 조회
	 */
	@Override
	public List<EmployeeVo> selectMemberList(int pro_sq) {
		return workDao.selectMemberList(pro_sq);
	}

	/**
	 * Method : selectProjectList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param pro_mem
	 * @return
	 * Method 설명 : 멤버에 해당하는 프로젝트 조회
	 */
	@Override
	public List<ProjectVo> selectProjectList(int pro_mem) {
		return workDao.selectProjectList(pro_mem);
	}
	
	/**
	 * Method : selectProject
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param pro_sq
	 * @return
	 * Method 설명 : 프로젝트 조회
	 */
	@Override
	public ProjectVo selectProject(int pro_sq) {
		return workDao.selectProject(pro_sq);
	}

	/**
	 * Method : selectWorkList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 상위업무조회(자신을 포함한 상위업무만 조회되며 하위업무는 조회되지 않음)
	 */
	@Override
	public List<WorkVo> selectWorkList(Map<String, Object> map) {
		return workDao.selectWorkList(map);
	}

	/**
	 * Method : insertWork
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param workVo
	 * @return
	 * Method 설명 : 업무등록
	 */
	@Override
	public int insertWork(WorkVo workVo) {
		return workDao.insertWork(workVo);
	}
	
	/**
	 * Method : insertFile
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param workFileVo
	 * @return
	 * Method 설명 : 업무파일등록
	 */
	@Override
	public int insertFile(WorkFileVo workFileVo) {
		return workDao.insertFile(workFileVo);
	}
	
	/**
	 * Method : selectFile
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param wk_fl_sq
	 * @return
	 * Method 설명 : 업무파일번호로 파일조회
	 */
	@Override
	public WorkFileVo selectFile(int wk_fl_sq) {
		return workDao.selectFile(wk_fl_sq);
	}
	
	/**
	 * Method : insertRelation
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param workRelationVo
	 * @return
	 * Method 설명 : 업무관련사원 등록
	 */
	@Override
	public int insertRelation(WorkRelationVo workRelationVo) {
		return workDao.insertRelation(workRelationVo);
	}

	/**
	 * Method : insertHistory
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param workHistoryVo
	 * @return
	 * Method 설명 : 업무 작업내역 등록
	 */
	@Override
	public int insertHistory(WorkHistoryVo workHistoryVo) {
		return workDao.insertHistory(workHistoryVo);
	}

	/**
	 * Method : selectHistoryMap
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param his_wk_sq
	 * @return
	 * Method 설명 : 작업내역과 관련된 데이터조회(조건)
	 */
	@Override
	public List<Map<String, Object>> selectHistoryMapList(Map<String, Object> map) {
		return workDao.selectHistoryMapList(map);
	}

	/**
	 * Method : selectWorkMap
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 업무와 관련된 데이터 조회(조건검색)
	 */
	@Override
	public List<Map<String, Object>> selectWorkMap(Map<String, Object> map) {
		return workDao.selectWorkMap(map);
	}

	/**
	 * Method : selectWorkPagingMap
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 업무 페이징 조회 및 업무수 조회
	 */
	@Override
	public Map<String, Object> selectWorkPagingMap(Map<String, Object> map) {
		//결과값을 두개 담아서 return 하기위해 Map 객체 사용
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("workMapPagingList", workDao.selectWorkPagingMap(map));
		resultMap.put("workCnt", workDao.getWorkCnt(map));
		
		return resultMap;
	}
	
	/**
	 * Method : selectWork
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param wk_sq
	 * @return
	 * Method 설명 : 업무번호에 해당하는 업무조회
	 */
	@Override
	public Map<String, Object> selectWork(int wk_sq) {
		return workDao.selectWork(wk_sq);
	}

	/**
	 * Method : updateWork
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param workVo
	 * @return
	 * Method 설명 : 업무수정
	 */
	@Override
	public int updateWork(WorkVo workVo) {
		return workDao.updateWork(workVo);
	}

	/**
	 * Method : updateRelation
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param workRelationVo
	 * @return
	 * Method 설명 : 업무관련 수정
	 */
	@Override
	public int updateRelation(WorkRelationVo workRelationVo) {
		return workDao.updateRelation(workRelationVo);
	}
	
	/**
	 * Method : selectFileList
	 * 작성자 : lee
	 * 변경이력 :
	 * @param wk_sq
	 * @return
	 * Method 설명 : 업무 첨부파일 리스트 조회
	 */
	@Override
	public List<WorkFileVo> selectFileList(int wk_sq) {
		return workDao.selectFileList(wk_sq);
	}
	
	/**
	 * Method : deleteFile
	 * 작성자 : lee
	 * 변경이력 :
	 * @param wk_fl_sq
	 * @return
	 * Method 설명 : 업부 첨부파일 삭제
	 */
	@Override
	public int deleteFile(int wk_fl_sq) {
		return workDao.deleteFile(wk_fl_sq);
	}
	
	/**
	 * Method : selectCount
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param pro_sq
	 * @return
	 * Method 설명 : 프로젝트번호로 type, state별 업무수 조회
	 */
	@Override
	public Map<String, Object> selectCount(int pro_sq) {
		return workDao.selectCount(pro_sq);
	}
	
	/**
	 * Method : deleteRelation
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param wk_sq
	 * @return
	 * Method 설명 : 업무관련 삭제
	 */
	@Override
	public int deleteAllRelation(int wk_sq) {
		return workDao.deleteAllRelation(wk_sq);
	}
	
	/**
	 * Method : deleteAllFile
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param wk_sq
	 * @return
	 * Method 설명 : 업무첨부파일 모두삭제
	 */
	@Override
	public int deleteAllFile(int wk_sq) {
		return workDao.deleteAllFile(wk_sq);
	}

	/**
	 * Method : deleteAllHistory
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param wk_sq
	 * @return
	 * Method 설명 : 업무작업내역 모두삭제
	 */
	@Override
	public int deleteAllHistory(int wk_sq) {
		return workDao.deleteAllHistory(wk_sq);
	}

	/**
	 * Method : deleteAllWork
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param wk_sq
	 * @return
	 * Method 설명 : 업무(하위업무 포함) 모두삭제
	 */
	@Override
	public int deleteAllWork(int wk_sq) {
		return workDao.deleteAllWork(wk_sq);
	}

	/**
	 * Method : selectHistoryList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param his_wk_sq
	 * @return
	 * Method 설명 : 해당 업무 작업내역 리스트 조회
	 */
	@Override
	public List<Map<String, Object>> selectHistoryList(int his_wk_sq) {
		return workDao.selectHistoryList(his_wk_sq);
	}
	
	/**
	 * Method : selectGanttMap
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 간트차트에 표시될 내용 계층형으로 조회
	 */
	@Override
	public List<Map<String, Object>> selectGanttMap(Map<String, Object> map) {
		return workDao.selectGanttMap(map);
	}
	
}