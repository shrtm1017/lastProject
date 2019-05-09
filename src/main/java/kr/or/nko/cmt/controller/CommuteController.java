package kr.or.nko.cmt.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.nko.cmt.model.CmtVo;
import kr.or.nko.cmt.service.ICommuteService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.util.model.PageVo;

@Controller
public class CommuteController {
	private Logger logger = LoggerFactory.getLogger(CommuteController.class);
	@Resource(name = "cmtService")
	private ICommuteService commuteService;



	@RequestMapping("/GotoworkCmt")
	public String gotoWorkCmt(CmtVo cmtVo, HttpSession Session, Model model, PageVo pageVo) {
		EmployeeVo employeeVo = (EmployeeVo) Session.getAttribute("employeeVo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd/HHmmss");
		SimpleDateFormat Newsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat checkdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		String todate = sdf.format(date);
		String[] a = todate.split("/");

		String morningDate = "090100";
		String lunchDate = "120100";
		// String OffworkDate= "180100";

		int MorningDate1 = Integer.parseInt(morningDate);
		int LunchDate1 = Integer.parseInt(lunchDate);
		// int OffworkDate1 = Integer.parseInt(OffworkDate);
		// 현재시간
		int splitDate = Integer.parseInt(a[1]);
		int todayDate = Integer.parseInt(a[0]);
		// 변수에 담기..
		checkdf.format(todayDate);
		cmtVo.setCmt_emp_sq(employeeVo.getEmp_sq());
		cmtVo.setCmt_end_tm("");

		if (splitDate <= MorningDate1) {
			String NewDate = Newsdf.format(date);
			cmtVo.setCmt_srt_tm(NewDate);
			cmtVo.setCmt_div("출근");
		} else if (splitDate > MorningDate1 && splitDate <= LunchDate1) {
			String NewDate = Newsdf.format(date);
			cmtVo.setCmt_srt_tm(NewDate);
			cmtVo.setCmt_div("지각");

		} else if (splitDate > LunchDate1) {
			String NewDate = Newsdf.format(date);
			cmtVo.setCmt_srt_tm(NewDate);
			cmtVo.setCmt_div("결근");

		}
		

		model.addAttribute("todayDate",todayDate);
		model.addAttribute("todayCheck",commuteService.selectCmtCheckList(cmtVo));
		logger.debug("++",commuteService.selectCmtCheckList(cmtVo).size());
		commuteService.cmtInsert(cmtVo);
		return "redirect:/main";

	}

	@RequestMapping("/GotoHomeCmt")
	public String gotoHomeCmt(CmtVo cmtVo, HttpSession Session) {
		EmployeeVo employeeVo = (EmployeeVo) Session.getAttribute("employeeVo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String todate = sdf.format(date);
		cmtVo.setCmt_emp_sq(employeeVo.getEmp_sq());
		CmtVo cmt_srt_tm=commuteService.selectCmtCheck(cmtVo);
		cmt_srt_tm.setCmt_end_tm(todate);
		logger.debug("Cmt_end_tm" + cmt_srt_tm.getCmt_end_tm());
		commuteService.cmtUpdate(cmt_srt_tm);
		return "redirect:/main";

	}

	@RequestMapping(path = {"/CmtLookup" }, method = { RequestMethod.GET })
	public String CmtLookup(CmtVo cmtVo, HttpSession Session,PageVo pageVo,Model model) {
		EmployeeVo employeeVo = (EmployeeVo) Session.getAttribute("employeeVo");
		cmtVo.setCmt_emp_sq(employeeVo.getEmp_sq());
		Map<String, Object> resultMap = commuteService.selectCmtPageList(cmtVo, pageVo);
		model.addAllAttributes(resultMap);
		int Page = pageVo.getPage();
		int cmtCnt = (int) resultMap.get("cmtCnt");
		logger.debug("%%"+cmtCnt);
		int lastPage = (cmtCnt / pageVo.getPageSize()) + (cmtCnt % pageVo.getPageSize() > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((Page - 1) / 10) * 10 + 1;
		int endPage = startPage + 10 - 1;
		model.addAttribute("page", pageVo.getPage());
		model.addAttribute("pageSize", pageVo.getPageSize());
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastpage", lastPage);

		return "timeManagement";
		
	}
	@RequestMapping(path = {"/CmtSearchLookup" }, method = { RequestMethod.GET })
	public String CmtSearchLookup(CmtVo cmtVo, HttpSession Session,PageVo pageVo,Model model) {
		logger.debug("++"+cmtVo.getCmt_end_tm()+cmtVo.getCmt_srt_tm());
		EmployeeVo employeeVo = (EmployeeVo) Session.getAttribute("employeeVo");
		cmtVo.setCmt_emp_sq(employeeVo.getEmp_sq());

		Map<String, Object> resultMap = commuteService.selectCmtSearchList(cmtVo, pageVo);
		model.addAllAttributes(resultMap);
		int Page = pageVo.getPage();
		int cmtCnt = (int) resultMap.get("cmtSearchCnt");
		int lastPage = (cmtCnt / pageVo.getPageSize()) + (cmtCnt % pageVo.getPageSize() > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((Page - 1) / 10) * 10 + 1;
		int endPage = startPage + 10 - 1;
		model.addAttribute("page", pageVo.getPage());
		model.addAttribute("pageSize", pageVo.getPageSize());
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastpage", lastPage);
		model.addAttribute("cmt_srt_tm",cmtVo.getCmt_srt_tm());
		model.addAttribute("cmt_end_tm",cmtVo.getCmt_end_tm());

		return "timeManagement";

	}



}
