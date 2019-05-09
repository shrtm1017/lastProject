package kr.or.nko.research.service;

import java.util.List;
import java.util.Map;

import kr.or.nko.research.model.AnswerVo;
import kr.or.nko.research.model.AttendVo;
import kr.or.nko.research.model.QuestionVo;
import kr.or.nko.research.model.ResearchVo;
import kr.or.nko.util.model.PageVo;

public interface IResearchService {
	
	int insertResearch(ResearchVo researchVo);
	
	int insertQuestion(QuestionVo questionVo);
	
	int insertAnswer(AnswerVo answerVo);

	int updateResearch(ResearchVo researchVo);
	
	List<ResearchVo> getAllResearch();
	
	ResearchVo selectResearch(int res_sq);
	
	List<QuestionVo> selectQuestion(int que_res_sq);
	
	List<AnswerVo> selectAnswer(int ans_que_sq);
	
	int updatePeople(int ans_sq);

	int insertAttend(AttendVo attendVo);

	List<AttendVo> selectAttend(int att_emp_sq);

	List<AnswerVo> selectResult();
	
	int updateHits(int res_sq);
	
	int deleteResearch(int res_sq);
	
	int deleteQuestion(int que_res_sq);
	
	int deleteAnswer(int ans_que_sq);
	
	int deleteAttend(int att_que_sq);

	int delAttendResubmit(AttendVo attendVo);

	int updPeopleResubmit(int ans_sq);
	
	Map<String, Object> selectResearchPaging(PageVo pageVo);
	
	Map<String, Object> selectResearchPagingSearch(PageVo pageVo);

	int updateResearchState(int res_sq);
	
	List<ResearchVo> getOngoingResearch();
	
}
