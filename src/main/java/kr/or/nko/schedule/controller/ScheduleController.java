package kr.or.nko.schedule.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nko.admin.model.AuthorityVo;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.schedule.service.IScheduleService;

@Controller
public class ScheduleController {
	
	private Logger logger = LoggerFactory.getLogger(ScheduleController.class);
	
	@Resource(name="scheduleService")
	private IScheduleService scheduleService;
	
	/**
	* Method : pesonSch
	* 작성자 : pc15
	* 변경이력 :
	* @return
	* Method 설명 : 개인일정 캘린더
	*/
	@RequestMapping("/pesonSch")
	public String pesonSch(Model model,HttpSession session){
		
		// 로그인했을 때 사번
	    EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();
		
		String str = Integer.toString(memId);
		
		List<AuthorityVo> authlist = scheduleService.authoSearch(str);
		
		int memDpt = empvo.getEmp_dpt();
		model.addAttribute("memDpts",memDpt);
		model.addAttribute("scd_emp_sq",authlist);
		model.addAttribute("scd_div_sq",1);
		
		return "calendar";
	}
	
	/**
	* Method : departSch
	* 작성자 : pc15
	* 변경이력 :
	* @return
	* Method 설명 : 부서일정 캘린더
	*/
	@RequestMapping("/departSch")
	public String departSch(Model model,HttpSession session){
		
		// 로그인했을 때 사번
	    EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();
		
		String str = Integer.toString(memId);
		
		List<AuthorityVo> authlist = scheduleService.authoSearch(str);
		
		int memDpt = empvo.getEmp_dpt();
		model.addAttribute("memDpts",memDpt);
		model.addAttribute("scd_emp_sq",authlist);
		model.addAttribute("scd_div_sq",2);
		
		return "calendar";
	}
	
	/**
	* Method : companySch
	* 작성자 : pc15
	* 변경이력 :
	* @return
	* Method 설명 : 회사일정 캘린더
	*/
	@RequestMapping("/companySch")
	public String companySch(Model model,HttpSession session){
		
		// 로그인했을 때 사번
	    EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();
		
		String str = Integer.toString(memId);
		
		List<AuthorityVo> authlist = scheduleService.authoSearch(str);
		
		int memDpt = empvo.getEmp_dpt();
		model.addAttribute("memDpts",memDpt);
		model.addAttribute("scd_emp_sq",authlist);
		model.addAttribute("scd_div_sq",3);
		
		return "calendar";
	}
	
}
