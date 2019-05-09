package kr.or.nko.log.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.log.dao.ILogDao;
import kr.or.nko.log.model.LogVo;
import kr.or.nko.util.model.PageVo;

@Service("logService")
public class LogServiceImpl implements ILogService {
	private Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
	@Resource(name = "logDao")
	private ILogDao logDao;

	@Override
	public int insertLog(LogVo logvo) {
		return logDao.insertLog(logvo);
	}

	@Override
	public int updateLog(String logvo) {
		return logDao.updateLog(logvo);
	}

	@Override
	public LogVo selectLog(int log_id) {
		return logDao.selectLog(log_id);
	}

	@Override
	public Map<String, Object> selectLogList(LogVo logvo, PageVo pageVo) {
		Map<String, Object> selectLogList = new HashMap<String, Object>();
		selectLogList.put("log_id", logvo.getLog_id());
		selectLogList.put("page", pageVo.getPage());
		selectLogList.put("pageSize", pageVo.getPageSize());
//		selectLogList.put("selectLogCnt", logDao.selectLogCnt(employeeVo.getEmp_sq()));
		selectLogList.put("selectLogList", logDao.selectLogList(selectLogList));

		return selectLogList;
	}

	@Override
	public int selectLogCnt(int log_id) {
		return logDao.selectLogCnt(log_id);
	}

}
