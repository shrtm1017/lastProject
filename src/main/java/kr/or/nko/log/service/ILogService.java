package kr.or.nko.log.service;

import java.util.Map;

import kr.or.nko.log.model.LogVo;
import kr.or.nko.util.model.PageVo;

public interface ILogService {
	public int insertLog(LogVo logvo);
	public int updateLog(String log_id);
	public LogVo selectLog(int log_id);
	public Map<String,Object> selectLogList (LogVo logvo,PageVo pageVo);
	public int selectLogCnt(int log_id);
}
