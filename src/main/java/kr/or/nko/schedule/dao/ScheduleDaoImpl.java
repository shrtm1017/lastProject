package kr.or.nko.schedule.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.admin.model.AuthorityVo;
import kr.or.nko.schedule.model.ScheduleVo;

@Repository("scheduleDao")
public class ScheduleDaoImpl implements IScheduleDao{

	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int insertSchedule(ScheduleVo vo) {
		return sqlSessionTemplate.insert("schdule.insertSchedule",vo);
	}

	@Override
	public List<ScheduleVo> selectSchedule(Map map) {
		return sqlSessionTemplate.selectList("schdule.selectSchedule", map);
	}

	@Override
	public ScheduleVo readSchedule(Map map) {
		return sqlSessionTemplate.selectOne("schdule.readSchedule", map);
	}

	@Override
	public int updateSchedule(Map map) {
		return sqlSessionTemplate.update("schdule.updateSchedule", map);
	}

	@Override
	public int deleteSchedule(Map map) {
		return sqlSessionTemplate.delete("schdule.deleteSchedule", map);
	}

	@Override
	public List<AuthorityVo> authoSearch(String str) {
		return sqlSessionTemplate.selectList("schdule.authoSearch", str);
	}

} 
