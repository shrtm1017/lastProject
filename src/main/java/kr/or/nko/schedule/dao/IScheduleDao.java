package kr.or.nko.schedule.dao;

import java.util.List;
import java.util.Map;

import kr.or.nko.admin.model.AuthorityVo;
import kr.or.nko.schedule.model.ScheduleVo;

public interface IScheduleDao{
	
	public int insertSchedule(ScheduleVo vo);
	
	public List<ScheduleVo> selectSchedule(Map map);
	
	public ScheduleVo readSchedule(Map map);
	
	public int updateSchedule(Map map);
	
	public int deleteSchedule(Map map);
	
	public List<AuthorityVo> authoSearch(String str);
}
