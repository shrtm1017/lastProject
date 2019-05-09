package kr.or.nko.research.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.research.model.AnswerVo;
import kr.or.nko.research.model.AttendVo;
import kr.or.nko.research.model.QuestionVo;
import kr.or.nko.research.model.ResearchVo;
import kr.or.nko.util.model.PageVo;

@Repository("researchDao")
public class ResearchDaoImpl implements IResearchDao {

	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int insertResearch(ResearchVo researchVo) {
		return sqlSessionTemplate.insert("research.insertResearch", researchVo);
	}

	@Override
	public int insertQuestion(QuestionVo questionVo) {
		return sqlSessionTemplate.insert("question.insertQuestion", questionVo);
	}

	@Override
	public int insertAnswer(AnswerVo answerVo) {
		return sqlSessionTemplate.insert("answer.insertAnswer", answerVo);
	}
	
	@Override
	public int updateResearch(ResearchVo researchVo) {
		return sqlSessionTemplate.update("research.updateResearch", researchVo);
	}

	@Override
	public List<ResearchVo> getAllResearch() {
		return sqlSessionTemplate.selectList("research.getAllResearch");
	}

	@Override
	public ResearchVo selectResearch(int res_sq) {
		return sqlSessionTemplate.selectOne("research.selectResearch", res_sq);
	}

	@Override
	public List<QuestionVo> selectQuestion(int que_res_sq) {
		return sqlSessionTemplate.selectList("question.selectQuestion", que_res_sq);
	}

	@Override
	public List<AnswerVo> selectAnswer(int ans_que_sq) {
		return sqlSessionTemplate.selectList("answer.selectAnswer", ans_que_sq);
	}

	@Override
	public int updatePeople(int ans_sq) {
		return sqlSessionTemplate.update("answer.updatePeople", ans_sq);
	}

	@Override
	public int insertAttend(AttendVo attendVo) {
		return sqlSessionTemplate.insert("attend.insertAttend", attendVo);
	}

	@Override
	public List<AttendVo> selectAttend(int att_emp_sq) {
		return sqlSessionTemplate.selectList("attend.selectAttend", att_emp_sq);
	}

	@Override
	public List<AnswerVo> selectResult() {
		return sqlSessionTemplate.selectList("answer.selectResult");
	}

	@Override
	public int updateHits(int res_sq) {
		return sqlSessionTemplate.update("research.updateHits", res_sq);
	}

	@Override
	public int deleteResearch(int res_sq) {
		return sqlSessionTemplate.delete("research.deleteResearch", res_sq);
	}

	@Override
	public int deleteQuestion(int que_res_sq) {
		return sqlSessionTemplate.delete("question.deleteQuestion", que_res_sq);
	}

	@Override
	public int deleteAnswer(int ans_que_sq) {
		return sqlSessionTemplate.delete("answer.deleteAnswer", ans_que_sq);
	}

	@Override
	public int deleteAttend(int att_que_sq) {
		return sqlSessionTemplate.delete("attend.deleteAttend", att_que_sq);
	}

	@Override
	public int delAttendResubmit(AttendVo attendVo) {
		return sqlSessionTemplate.delete("attend.delAttendResubmit", attendVo);
	}

	@Override
	public int updPeopleResubmit(int ans_sq) {
		return sqlSessionTemplate.update("answer.updPeopleResubmit", ans_sq);
	}

	@Override
	public List<ResearchVo> selectResearchPaging(PageVo pageVo) {
		return sqlSessionTemplate.selectList("research.selectResearchPaging", pageVo);
	}

	@Override
	public List<ResearchVo> selectResearchPagingSearch(PageVo pageVo) {
		return sqlSessionTemplate.selectList("research.selectResearchPagingSearch", pageVo);
	}

	@Override
	public int getResearchCnt() {
		return sqlSessionTemplate.selectOne("research.getResearchCnt");
	}

	@Override
	public int getResearchCntSearch(PageVo pageVo) {
		return sqlSessionTemplate.selectOne("research.getResearchCntSearch", pageVo);
	}

	@Override
	public int updateResearchState(int res_sq) {
		return sqlSessionTemplate.update("research.updateResearchState", res_sq);
	}

	@Override
	public List<ResearchVo> getOngoingResearch() {
		return sqlSessionTemplate.selectList("research.getOngoingResearch");
	}
	
}
