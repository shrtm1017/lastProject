package kr.or.nko.annual.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nko.annual.model.GiveAnnualVo;
import kr.or.nko.annual.model.UseAnnualVo;
import kr.or.nko.annual.service.AnnualService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.employee.service.IEmployeeService;
import kr.or.nko.util.model.PageVo;

@Controller
public class AnnualController {
	@Resource(name = "annualService")
	private AnnualService annualService;
	private Logger logger = LoggerFactory.getLogger(AnnualController.class);

	@Resource(name = "employeeService")
	private IEmployeeService employeeService;

	@RequestMapping("/annualDay")
	public String annualDay(Model model, GiveAnnualVo annualVo, PageVo pageVo, HttpSession session) {
		EmployeeVo EmployeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		annualVo.setAnu_emp_sq(EmployeeVo.getEmp_sq());

		Map<String, Object> resultMap = annualService.annualList(annualVo, pageVo);

		model.addAllAttributes(resultMap);
		int Page = pageVo.getPage();
		int annualListCnt = (int) resultMap.get("listAnnualCnt");
		int lastPage = (annualListCnt / pageVo.getPageSize()) + (annualListCnt % pageVo.getPageSize() > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((Page - 1) / 10) * 10 + 1;
		int endPage = startPage + 10 - 1;
		model.addAttribute("page", pageVo.getPage());
		model.addAttribute("pageSize", pageVo.getPageSize());
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastpage", lastPage);

		return "annualDay";
	}

//	@RequestMapping("/Use_annualDay")
//	public String Use_annualDay(UseAnnualVo annualVo, Model model, HttpSession session) {
//		EmployeeVo EmployeeVo = (EmployeeVo) session.getAttribute("employeeVo");
//		annualVo.setAnu_emp_sq(EmployeeVo.getEmp_sq());
//		logger.debug("annualVo.getAnu_emp_sq() " + annualVo.getAnu_emp_sq());
//		int apvCount = annualService.select_UseAnnualApv(annualVo.getAnu_emp_sq());
//		logger.debug("apvCount " + apvCount);
//		if (apvCount >= 4) {
//			annualService.insert_UseAnnualDay(annualVo);
//		} else {
//			return "redirect:/Use_annualDay_List";
//		}
//		return "redirect:/Use_annualDay_List";
//
//	}
	@RequestMapping("/Use_annualDay_List")
	public String Use_annualDay_List(Model model, UseAnnualVo annualVo, PageVo pageVo, HttpSession session){
		EmployeeVo EmployeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		annualVo.setAnu_emp_sq(EmployeeVo.getEmp_sq());

		Map<String, Object> resultMap = annualService.Use_annualDayList(annualVo, pageVo);

		model.addAllAttributes(resultMap);
		int Page = pageVo.getPage();
		int annualListCnt = (int) resultMap.get("Use_annualDayListCnt");
		int lastPage = (annualListCnt / pageVo.getPageSize()) + (annualListCnt % pageVo.getPageSize() > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((Page - 1) / 10) * 10 + 1;
		int endPage = startPage + 10 - 1;
		model.addAttribute("page", pageVo.getPage());
		model.addAttribute("pageSize", pageVo.getPageSize());
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastpage", lastPage);
		return "Use_annualDay_List";
		
	}

	@RequestMapping("/annualAdd")
	public String annualAdd() {
		annualDayPlus();

		return "redirect:/annualDay";
	}

	public void annualDayPlus() {
		List<EmployeeVo> list = employeeService.getAllEmp();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date todate = new Date();

		String giveday = sdf.format(todate);
		for (int i = 0; i < list.size(); i++) {

			GiveAnnualVo vo = new GiveAnnualVo();
			vo.setAnu_days(15);
			vo.setAnu_remains(15);
			vo.setAnu_emp_sq(list.get(i).getEmp_sq());
			vo.setAnu_give_dt(giveday);
			annualService.insertAnnual(vo);
		}
	}

	@Scheduled(cron = "0 0 0 1 1 *")
	public void annualDaysSchedule() {
		List<EmployeeVo> list = employeeService.getAllEmp();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date todate = new Date();

		String giveday = sdf.format(todate);
		for (int i = 0; i < list.size(); i++) {
			GiveAnnualVo vo = new GiveAnnualVo();
			vo.setAnu_days(15);
			vo.setAnu_remains(15);
			vo.setAnu_emp_sq(list.get(i).getEmp_sq());
			vo.setAnu_give_dt(giveday);
			annualService.insertGiveAnnual(vo);
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// UseAnnualVo();
	}

}
