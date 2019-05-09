package kr.or.nko.callingcard.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.nko.callingcard.model.CallingCardVo;
import kr.or.nko.callingcard.service.ICallingService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.util.UtilNKO;
import kr.or.nko.util.model.PageVo;

@Controller
public class CallingcardController {
	
	
	private Logger logger = LoggerFactory.getLogger(CallingcardController.class);
	
	@Resource(name="callingService")
	private ICallingService callingService;
	
	@Resource(name="utilNKO")
	private UtilNKO utilNKO;
	
	
	@RequestMapping(path="/cardList", method=RequestMethod.GET)
	public String callingCardPagingList(Model model, PageVo pageVo,HttpServletRequest req, HttpSession session){
		
		EmployeeVo empVO = (EmployeeVo) req.getSession().getAttribute("employeeVo");
		int emp_sq = empVO.getEmp_sq();
		model.addAttribute("emp_sq", emp_sq);
		
		utilNKO.callingcardPaging("1", model, pageVo, new CallingCardVo(),session);
		// 타일즈 적용한 이름
		return "callingCardList";
	}
	
	@RequestMapping(path="/cardSelect", method=RequestMethod.GET)
	public String cardSelect(PageVo pageVo, String selCode, String selText, Model model,HttpServletRequest req,HttpSession session){
		
		EmployeeVo empVO = (EmployeeVo) req.getSession().getAttribute("employeeVo");
		int emp_sq = empVO.getEmp_sq();
		model.addAttribute("emp_sq", emp_sq);
		
		
		CallingCardVo callingCardVo = new CallingCardVo();
		
		// 검색 조건에 따라 
		if(selCode.equals("1")){
			String cal_nm = selText;
			callingCardVo.setCal_nm(cal_nm);
		}
		else if(selCode.equals("2")){
			String cal_com = selText;
			callingCardVo.setCal_com(cal_com);
		}
		else if(selCode.equals("3")){
			String cal_grade = selText;
			callingCardVo.setCal_grade(cal_grade);
		}
		
		model.addAttribute("selCode", selCode);
		model.addAttribute("selText", selText);
		
		utilNKO.callingcardPaging("2", model, pageVo, callingCardVo,session);
		
		return "callingCardList";
	}
	
	
	
	//명함 등록
	
	@RequestMapping(path="/cardForm", method=RequestMethod.GET)
	public String callingCardForm(HttpServletRequest req,Model model ){
		
		EmployeeVo empVO = (EmployeeVo) req.getSession().getAttribute("employeeVo");
		int cal_emp_sq = empVO.getEmp_sq();
		
		model.addAttribute("cal_emp_sq", Integer.toString(cal_emp_sq));
		
		return "callingCardForm";
		
	}
	
	@RequestMapping(path="/cardForm", method=RequestMethod.POST)
	public String callingCardForm_post(CallingCardVo callingCardVo, Model model,
									   String cal_nm,String cal_grade,String cal_com,
									   String cal_phone,String cal_com_phone,String cal_mail,
									   String cal_emp_sq,String cal_addr){
		
		logger.debug("사원번호~~~ {}", cal_emp_sq);
		logger.debug("명함이름~~~ {}", cal_nm);
		logger.debug("명함직급~~~ {}", cal_grade);
		logger.debug("명함회사~~~ {}", cal_com);
		logger.debug("명함번호~~~ {}", cal_phone);
		logger.debug("회사번호~~~ {}", cal_com_phone);
		logger.debug("명함메일~~~ {}", cal_mail);
		logger.debug("명함주소~~~ {}", cal_addr);
		
		int int_emp_sq = Integer.parseInt(cal_emp_sq);
		
		CallingCardVo callvo = new CallingCardVo();
		
		callvo.setCal_emp_sq(int_emp_sq);
		callvo.setCal_nm(cal_nm);
		callvo.setCal_phone(cal_phone);
		callvo.setCal_com(cal_com);
		callvo.setCal_mail(cal_mail);
		callvo.setCal_com_phone(cal_com_phone);
		callvo.setCal_grade(cal_grade);
		callvo.setCal_addr(cal_addr);
		
		
		int insert = callingService.insert_callingcard(callvo);
		logger.debug("업데이트 숫자는요!!!!!!!!!!!!!!! {}",insert);
		if(insert == 1){
			return "redirect:/cardList";
		}
		
			return "callingCardForm";
		
	}
	
	
	// 명함 수정
	
	@RequestMapping(path="/cardModify", method=RequestMethod.GET)
	public String modify_callingcard(String cal_sq, Model model, CallingCardVo callingCardVo){
		
		int int_cal_sq = Integer.parseInt(cal_sq);
		
		callingCardVo = callingService.select_callingcarddetail(int_cal_sq);
		logger.debug("명함번호가 몇번인가  {}", cal_sq);
		
		model.addAttribute("callingcardVo", callingCardVo);
		model.addAttribute("cal_sq", cal_sq);
		
		return "callingCardModify";
	}
	
	@RequestMapping(path="/cardModify", method=RequestMethod.POST)
	public String modify_callingcard_post(CallingCardVo callingCardVo,Model model,
			                              @RequestParam("cal_sq") String cal_sq,
										  @RequestParam("cal_nm") String cal_nm,
										  @RequestParam("cal_grade") String cal_grade,
										  @RequestParam("cal_com") String cal_com,
										  @RequestParam("cal_phone") String cal_phone,
										  @RequestParam("cal_com_phone") String cal_com_phone,
										  @RequestParam("cal_mail") String cal_mail,
										  @RequestParam("cal_addr") String cal_addr
										  ){
		
		callingCardVo = callingService.select_callingcarddetail(Integer.parseInt(cal_sq));
		
		callingCardVo.setCal_nm(cal_nm);
		callingCardVo.setCal_grade(cal_grade);
		callingCardVo.setCal_com(cal_com);
		callingCardVo.setCal_phone(cal_phone);
		callingCardVo.setCal_com_phone(cal_com_phone);
		callingCardVo.setCal_mail(cal_mail);
		callingCardVo.setCal_addr(cal_addr);
		
		int update = callingService.update_callingcard(callingCardVo);
		
		if(update == 1){
			return "redirect:/cardList";
		}
		else{
			return "redirect:/cardModify";
		}
	}
	

	@RequestMapping(path="/cardDelete", method=RequestMethod.POST)
	public String delete_callingcard_post(@RequestParam("del_cal_sq") List<Integer> del_cal_sq){
		
		int int_cal_sq = 0;
		int delete=0;
		
		for(int i =0; i <del_cal_sq.size(); i++ ){
			int_cal_sq = del_cal_sq.get(i);
			delete += callingService.delete_callingcard(int_cal_sq);
		}
		if(delete <= 1){
			return "redirect:/cardList";
		}else{
			return "redirect:/cardList";
		}
	}
	 

}