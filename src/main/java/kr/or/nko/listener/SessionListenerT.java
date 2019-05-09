package kr.or.nko.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.log.model.LogVo;
import kr.or.nko.log.service.ILogService;

public class SessionListenerT implements HttpSessionListener {
	private Logger logger = LoggerFactory.getLogger(SessionListenerT.class);

	@Override
	public void sessionCreated(HttpSessionEvent event) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());

//		EmployeeVo employeevo = (EmployeeVo) event.getSession().getAttribute("employeeVo");
		logger.debug("session++"+event.getSession().getId());
		ILogService logService = (ILogService) context.getBean("logService");
//		LogVo logvo= logService.selectLog(employeevo.getEmp_sq());
//		logger.debug("sessiondestroyed" + employeevo);
		logService.updateLog(event.getSession().getId());
	}

}
