package kr.or.nko.log.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.log.model.LogVo;
import kr.or.nko.log.service.ILogService;
import kr.or.nko.util.model.PageVo;

@Controller
public class LogController {
	@Resource(name="logService")
	private ILogService logService;
	
	@RequestMapping("/logSelectList")
	public String logSelectList(LogVo logvo,PageVo pageVo,Model model,HttpSession session){
		
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		logvo.setLog_id(employeeVo.getEmp_sq());
		Map<String, Object> resultMap = logService.selectLogList(logvo, pageVo);
		model.addAllAttributes(resultMap);
		int selectLogCnt  = logService.selectLogCnt(employeeVo.getEmp_sq());
		int Page = pageVo.getPage();
//		int selectLogCnt = (int) resultMap.get("selectLogCnt");
		int lastPage = (selectLogCnt / pageVo.getPageSize()) + (selectLogCnt % pageVo.getPageSize() > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((Page - 1) / 10) * 10 + 1;
		int endPage = startPage + 10 - 1;

		model.addAttribute("emp_sq",employeeVo.getEmp_sq());
		model.addAttribute("page", pageVo.getPage());
		model.addAttribute("pageSize", pageVo.getPageSize());
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastpage", lastPage);
		
		
		return "userLog";
		
	}
	

}
