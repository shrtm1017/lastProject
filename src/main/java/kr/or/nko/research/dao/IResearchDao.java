package kr.or.nko.research.dao;

import java.util.List;

import kr.or.nko.research.model.AnswerVo;
import kr.or.nko.research.model.AttendVo;
import kr.or.nko.research.model.QuestionVo;
import kr.or.nko.research.model.ResearchVo;
import kr.or.nko.util.model.PageVo;

/**
 * IResearchDao.java
 *
 * @author PC07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * PC07 최초 생성
 *
 * </pre>
 */
/**
 * IResearchDao.java
 *
 * @author PC07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * PC07 최초 생성
 *
 * </pre>
 */
public interface IResearchDao {
	
	/**
	 * Method : insertResearch
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param researchVo
	 * @return
	 * Method 설명 : 설문조사 게시글 등록
	 */
	int insertResearch(ResearchVo researchVo);
	
	/**
	 * Method : updateResearch
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param researchVo
	 * @return
	 * Method 설명 : 설문조사 게시글 수정
	 */
	int updateResearch(ResearchVo researchVo);
	
	/**
	 * Method : insertQuestion
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param questionVo
	 * @return
	 * Method 설명 : 질문 등록
	 */
	int insertQuestion(QuestionVo questionVo);
	
	/**
	 * Method : insertAnswer
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param answerVo
	 * @return
	 * Method 설명 : 옵션 등록
	 */
	int insertAnswer(AnswerVo answerVo);
	
	/**
	 * Method : getAllResearch
	 * 작성자 : PC07
	 * 변경이력 :
	 * @return
	 * Method 설명 : 전체 설문조사 목록 조회
	 */
	List<ResearchVo> getAllResearch();
	
	/**
	 * Method : selectResearch
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param res_sq
	 * @return
	 * Method 설명 : 설문조사 상세 조회
	 */
	ResearchVo selectResearch(int res_sq);
	
	/**
	 * Method : selectQuestion
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param que_res_sq
	 * @return
	 * Method 설명 : 설문조사 상세 조회 - 질문 목록
	 */
	List<QuestionVo> selectQuestion(int que_res_sq);
	
	/**
	 * Method : selectAnswer
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param ans_que_sq
	 * @return
	 * Method 설명 : 설문조사 상세 조회 - 옵션 목록
	 */
	List<AnswerVo> selectAnswer(int ans_que_sq);
	
	/**
	 * Method : updatePeople
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param ans_sq
	 * @return
	 * Method 설명 : 선택한 사원 수 변경 - 옵션
	 */
	int updatePeople(int ans_sq);
	
	/**
	 * Method : insertAttend
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param attendVo
	 * @return
	 * Method 설명 : 설문조사 참여여부 등록
	 */
	int insertAttend(AttendVo attendVo);
	
	/**
	 * Method : selectAttend
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param att_emp_sq
	 * @return
	 * Method 설명 : 설문조사 참여여부 조회
	 */
	List<AttendVo> selectAttend(int att_emp_sq);
	
	/**
	 * Method : selectResult
	 * 작성자 : PC07
	 * 변경이력 :
	 * @return
	 * Method 설명 : 설문조사 결과 조회 - 백분율
	 */
	List<AnswerVo> selectResult();
	
	/**
	 * Method : updateHits
	 * 작성자 : PC07
	 * 변경이력 :
	 * @return
	 * Method 설명 : 조회수 업데이트
	 */
	int updateHits(int res_sq);
	
	int deleteResearch(int res_sq);
	
	int deleteQuestion(int que_res_sq);
	
	int deleteAnswer(int ans_que_sq);
	
	int deleteAttend(int att_que_sq);
	
	/**
	 * Method : delAttendResubmit
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param attendVo
	 * @return
	 * Method 설명 : 설문지 재제출 시 기존 데이터 삭제
	 */
	int delAttendResubmit(AttendVo attendVo);
	
	/**
	 * Method : updPeopleResubmit
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param ans_sq
	 * @return
	 * Method 설명 : 설문지 재제출 시 기존 데이터 수정
	 */
	int updPeopleResubmit(int ans_sq);
	
	/**
	 * Method : selectResearchPaging
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param pageVo
	 * @return
	 * Method 설명 : 설문조사 페이징 리스트 조회
	 */
	List<ResearchVo> selectResearchPaging(PageVo pageVo);
	
	/**
	 * Method : selectResearchPagingSearch
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param pageVo
	 * @return
	 * Method 설명 : 설문조사 검색
	 */
	List<ResearchVo> selectResearchPagingSearch(PageVo pageVo);

	int getResearchCnt();
	
	int getResearchCntSearch(PageVo pageVo);
	
	int updateResearchState(int res_sq);
	
	/**
	 * Method : getOngoingResearch
	 * 작성자 : PC07
	 * 변경이력 :
	 * @return
	 * Method 설명 : 진행 중인 설문 : 상위 5개
	 */
	List<ResearchVo> getOngoingResearch();
	
}
