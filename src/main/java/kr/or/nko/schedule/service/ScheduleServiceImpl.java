package kr.or.nko.schedule.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.admin.model.AuthorityVo;
import kr.or.nko.schedule.dao.IScheduleDao;
import kr.or.nko.schedule.model.ScheduleVo;

@Service("scheduleService")
public class ScheduleServiceImpl implements IScheduleService{
	
	@Resource(name="scheduleDao")
	private IScheduleDao scheduleDao;
	
	
	@Override
	public int insertSchedule(ScheduleVo vo) {
		return scheduleDao.insertSchedule(vo);
	}

	@Override
	public List<ScheduleVo> selectSchedule(Map map) {
		return scheduleDao.selectSchedule(map);
	}

	@Override
	public ScheduleVo readSchedule(Map map) {
		return scheduleDao.readSchedule(map);
	}

	@Override
	public int updateSchedule(Map map) {
		return scheduleDao.updateSchedule(map);
	}

	@Override
	public int deleteSchedule(Map map) {
		return scheduleDao.deleteSchedule(map);
	}

	@Override
	public List<AuthorityVo> authoSearch(String str) {
		return scheduleDao.authoSearch(str);
	}

}
