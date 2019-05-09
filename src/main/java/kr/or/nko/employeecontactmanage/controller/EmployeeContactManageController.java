package kr.or.nko.employeecontactmanage.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.nko.employeecontactmanage.model.EmployeeContactManageVo;
import kr.or.nko.employeecontactmanage.service.IEmployeeContactManageService;
import kr.or.nko.notice.model.NoticeVo;
import kr.or.nko.util.UtilNKO;
import kr.or.nko.util.model.PageVo;

@Controller
public class EmployeeContactManageController {
	@Resource(name = "employeeContactService")
	private IEmployeeContactManageService employeeService;

	@Resource(name = "utilNKO")
	private UtilNKO utilNKO;
	
	private Logger logger = LoggerFactory.getLogger(EmployeeContactManageController.class);

	@RequestMapping("/employeeContactMange")
	public String employeeContactView(Model model, PageVo PageVo, EmployeeContactManageVo EmployeeContactManageVo) {
		Map<String, Object> resultMap = employeeService.EmployeeContactPagingList(PageVo, EmployeeContactManageVo);
		model.addAllAttributes(resultMap);
		int page = PageVo.getPage();
		int PagingListCnt = (int) resultMap.get("PagingListCnt");
		int lastPage = (PagingListCnt / PageVo.getPageSize()) + (PagingListCnt % PageVo.getPageSize() > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((page - 1) / 10) * 10 + 1;
		int endPage = startPage + 10 - 1;
		model.addAttribute("page", PageVo.getPage());
		model.addAttribute("pageSize", PageVo.getPageSize());
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastpage", lastPage);
		return "employeeContactMange";

	}

	@RequestMapping(path = { "/employee_modify" }, method = { RequestMethod.GET })
	public String employee_modifyView(EmployeeContactManageVo EmployeeContactManageVo, Model model) {
		EmployeeContactManageVo EmployeeContactManage = employeeService
				.Selectemployeecontact(EmployeeContactManageVo.getEmp_sq());
		model.addAttribute("EmployeeContactVo", EmployeeContactManageVo);
		model.addAttribute("EmployeeContactManage", EmployeeContactManage);

		return "employeeContactmodify";

	}

	@RequestMapping(path = { "/employeeContact_Update" }, method = { RequestMethod.GET })
	public String employeeContact_Update(EmployeeContactManageVo EmployeeContactManageVo, Model model) {

		int employeeContactUpdate = employeeService.EmployeeContactManageUpdate(EmployeeContactManageVo);
		// return
		// "redirect:/employee_modify?emp_sq="+EmployeeContactManageVo.getEmp_sq();
		return "redirect:/employeeContactMange";

	}

	@RequestMapping(path = { "/employeeContact_Serch" }, method = { RequestMethod.POST })
	public String employeeContact_SerchView(Model model, @RequestParam("emp_info") String emp_info, PageVo pageVo,
			String selCode) {
		EmployeeContactManageVo EmployeeContactManageVo = new EmployeeContactManageVo();

		if (selCode.equals("1")) {
			EmployeeContactManageVo.setEmp_nm(emp_info);
			logger.debug("@@@"+emp_info);
		} else if (selCode.equals("2")) {
			boolean check = utilNKO.checkStrToInt(emp_info);
			if (check) {
				int emp_sq = Integer.parseInt(emp_info);
				EmployeeContactManageVo.setEmp_sq(emp_sq);
				logger.debug("@@@"+emp_info);
			}
		}
		logger.debug("@@@@"+emp_info);
		logger.debug("@@@@"+selCode);
		Map<String, Object> resultMap = employeeService.EmployeeContact_Serch(pageVo, EmployeeContactManageVo);
		
		model.addAllAttributes(resultMap);

		int Page = pageVo.getPage();
		int PagingListSerchCnt = (int) resultMap.get("PagingListSerchCnt");
		int lastPage = (PagingListSerchCnt / pageVo.getPageSize()) + (PagingListSerchCnt % pageVo.getPageSize() > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((Page - 1) / 10) * 10 + 1;
		int endPage = startPage + 10 - 1;
		model.addAttribute("page", pageVo.getPage());
		model.addAttribute("pageSize", pageVo.getPageSize());
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastpage", lastPage);
		return "employeeContactMange";

	}
}
