package kr.or.nko.research.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.research.dao.IResearchDao;
import kr.or.nko.research.model.AnswerVo;
import kr.or.nko.research.model.AttendVo;
import kr.or.nko.research.model.QuestionVo;
import kr.or.nko.research.model.ResearchVo;
import kr.or.nko.util.model.PageVo;

@Service("researchService")
public class ResearchServiceImpl implements IResearchService {
	
	@Resource(name="researchDao")
	private IResearchDao researchDao;

	@Override
	public int insertResearch(ResearchVo researchVo) {
		return researchDao.insertResearch(researchVo);
	}

	@Override
	public int insertQuestion(QuestionVo questionVo) {
		return researchDao.insertQuestion(questionVo);
	}

	@Override
	public int insertAnswer(AnswerVo answerVo) {
		return researchDao.insertAnswer(answerVo);
	}

	@Override
	public int updateResearch(ResearchVo researchVo) {
		return researchDao.updateResearch(researchVo);
	}
	
	@Override
	public List<ResearchVo> getAllResearch() {
		return researchDao.getAllResearch();
	}

	@Override
	public ResearchVo selectResearch(int res_sq) {
		return researchDao.selectResearch(res_sq);
	}

	@Override
	public List<QuestionVo> selectQuestion(int que_res_sq) {
		return researchDao.selectQuestion(que_res_sq);
	}

	@Override
	public List<AnswerVo> selectAnswer(int ans_que_sq) {
		return researchDao.selectAnswer(ans_que_sq);
	}

	@Override
	public int updatePeople(int ans_sq) {
		return researchDao.updatePeople(ans_sq);
	}

	@Override
	public int insertAttend(AttendVo attendVo) {
		return researchDao.insertAttend(attendVo);
	}

	@Override
	public List<AttendVo> selectAttend(int att_emp_sq) {
		return researchDao.selectAttend(att_emp_sq);
	}

	@Override
	public List<AnswerVo> selectResult() {
		return researchDao.selectResult();
	}

	@Override
	public int updateHits(int res_sq) {
		return researchDao.updateHits(res_sq);
	}

	@Override
	public int deleteResearch(int res_sq) {
		return researchDao.deleteResearch(res_sq);
	}

	@Override
	public int deleteQuestion(int que_res_sq) {
		return researchDao.deleteQuestion(que_res_sq);
	}

	@Override
	public int deleteAnswer(int ans_que_sq) {
		return researchDao.deleteAnswer(ans_que_sq);
	}

	@Override
	public int deleteAttend(int att_que_sq) {
		return researchDao.deleteAttend(att_que_sq);
	}

	@Override
	public int delAttendResubmit(AttendVo attendVo) {
		return researchDao.delAttendResubmit(attendVo);
	}

	@Override
	public int updPeopleResubmit(int ans_sq) {
		return researchDao.updPeopleResubmit(ans_sq);
	}

	@Override
	public Map<String, Object> selectResearchPaging(PageVo pageVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("researchList", researchDao.selectResearchPaging(pageVo));
		resultMap.put("researchCnt", researchDao.getResearchCnt());
		
		return resultMap;
	}

	@Override
	public Map<String, Object> selectResearchPagingSearch(PageVo pageVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("researchList", researchDao.selectResearchPagingSearch(pageVo));
		resultMap.put("researchCnt", researchDao.getResearchCntSearch(pageVo));
		
		return resultMap;
	}

	@Override
	public int updateResearchState(int res_sq) {
		return researchDao.updateResearchState(res_sq);
	}

	@Override
	public List<ResearchVo> getOngoingResearch() {
		return researchDao.getOngoingResearch();
	}

}
