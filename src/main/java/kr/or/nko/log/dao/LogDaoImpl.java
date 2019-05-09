package kr.or.nko.log.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.log.model.LogVo;

@Repository("logDao")
public class LogDaoImpl implements ILogDao{

	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int insertLog(LogVo logvo) {
		int insertLog = sqlSessionTemplate.insert("log.insertLog",logvo);
		return insertLog;
	}

	@Override
	public List<LogVo> selectLogList(Map<String, Object> map) {
		List<LogVo> selectLogList = sqlSessionTemplate.selectList("log.selectLogList",map);
		return selectLogList;
	}

	@Override
	public int updateLog(String log_id) {
		int updateLog = sqlSessionTemplate.update("log.updateLog",log_id);
		return updateLog;
	}

	@Override
	public LogVo selectLog(int log_id) {
		LogVo selectLog = sqlSessionTemplate.selectOne("log.selectLog",log_id);
		return selectLog;
	}

	@Override
	public int selectLogCnt(int log_id) {
		int selectLogCnt = sqlSessionTemplate.selectOne("log.selectLogCnt",log_id);
		return selectLogCnt;
	}
}
