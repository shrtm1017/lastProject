package kr.or.nko.log.dao;

import java.util.List;
import java.util.Map;

import kr.or.nko.log.model.LogVo;

public interface ILogDao {
	
	public int insertLog(LogVo logvo);
	public List<LogVo> selectLogList(Map<String, Object> map);
	public LogVo selectLog(int log_id);
	public int updateLog(String log_id);
	public int selectLogCnt(int log_id);
}
