package kr.or.nko.certificate.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.nko.certificate.model.CertificateDivVo;
import kr.or.nko.certificate.model.CertificateVo;
import kr.or.nko.certificate.service.ICertificateService;
import kr.or.nko.department.model.DepartmentVo;
import kr.or.nko.department.service.IDepartmentService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.util.UtilNKO;
import kr.or.nko.util.model.PageVo;

@Controller
public class CertificateController {
	
	private Logger logger = LoggerFactory.getLogger(CertificateController.class);
	
	@Resource(name="certificateService")
	private ICertificateService certificateService;
	
	@Resource(name="departmentService")
	private IDepartmentService departmentService;
	
	@Resource(name="utilNKO")
	private UtilNKO utilNKO;
	
	
	// list
	@RequestMapping(path="ctfList", method=RequestMethod.GET)
	public String certificateList(HttpSession session, Model model,PageVo pageVo){
		
		List<CertificateDivVo> crtDivList = certificateService.selectCrtDivList();
		model.addAttribute("crtDivList", crtDivList);
		//list 뽑아주는 거 하나
		utilNKO.certificatePaging("1", model, pageVo, new CertificateVo(), session);
		
		return "certificateList";
	}
	
	@RequestMapping(path="ctfList", method=RequestMethod.POST)
	public String certificateList_post(HttpSession session, Model model){
		
		
		
		return "certificateList";
	}
	
	// 검색시 
	@RequestMapping(path="searchList", method=RequestMethod.GET)
	public String certificateSearchList(HttpSession session, Model model,PageVo pageVo,
										@RequestParam("crt_div_sq") int crt_div_sq,
										@RequestParam("crt_whether") String crt_whether
										){
		List<CertificateDivVo> crtDivList = certificateService.selectCrtDivList();
		model.addAttribute("crtDivList", crtDivList);

		logger.debug("증명서 구분번호 : {}" , crt_div_sq);	
		logger.debug("증명서 상태 : {}" , crt_whether);	
		
		CertificateVo certificateVo = new CertificateVo();
		
		
		if(crt_div_sq == 1){
			certificateVo.setCrt_div_sq(crt_div_sq);
		}
		if(crt_div_sq == 2){
			certificateVo.setCrt_div_sq(crt_div_sq);
		}
		if(crt_whether.equals("1")){
			certificateVo.setCrt_whether(crt_whether);
		}
		if(crt_whether.equals("2")){
			certificateVo.setCrt_whether(crt_whether);
		}
		if(crt_whether.equals("3")){
			certificateVo.setCrt_whether(crt_whether);
		}
		
		model.addAttribute("crt_div_sq", crt_div_sq);
		model.addAttribute("crt_whether", crt_whether);
		
		//list 뽑아주는 거 하나
		utilNKO.certificatePaging("2", model, pageVo, certificateVo, session);
		
		return "certificateList";
	}
	
	//신청
	@RequestMapping(path="/certificateapply",method=RequestMethod.GET)
	public String form(HttpSession session, Model model){
		
		EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");
		
		List<DepartmentVo> getalllist = departmentService.getAllDepartment();
		List<DepartmentVo> getallnulllist = departmentService.selectDepartNull();
		
		
		model.addAttribute("empVo", empVo);
		model.addAttribute("alllist", getalllist);
		model.addAttribute("allnulllist", getallnulllist);
		
		return "certificateapply";
	}
	
	@RequestMapping(path="/certificateapply",method=RequestMethod.POST)
	public String form_post(HttpSession session, Model model,
							@RequestParam("crt_dt") String crt_dt,@RequestParam("crt_div_sq") String crt_div_sq,
							@RequestParam("crt_emp_dpt") String emp_dpt,@RequestParam("crt_emp_grade") String emp_grade,
							@RequestParam("crt_emp_phone") String emp_phone,@RequestParam("crt_emp_nm") String emp_nm,
							@RequestParam("crt_dos_str") String crt_dos_str,@RequestParam("crt_dos_end") String crt_dos_end,
							@RequestParam("crt_emp_addr1") String emp_addr1,@RequestParam("crt_emp_addr2") String emp_addr2,
							@RequestParam("crt_purpose") String crt_purpose,@RequestParam("crt_submission") String crt_submission,
							@RequestParam("crt_subdt") String crt_subdt) throws ParseException{
		
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		
		logger.debug("신청일 : {}",crt_dt);
		logger.debug("증명서구분번호 : {}",crt_div_sq);
		logger.debug("사원부서번호 : {}",emp_dpt);
		logger.debug("사원직급 : {}",emp_grade);
		logger.debug("핸드폰 : {}",emp_phone);
		logger.debug("이름 : {}", emp_nm);
		logger.debug("입사일 : {}",crt_dos_str);
		logger.debug("톼사ㅣ일 : {}",crt_dos_end);
		logger.debug("주소 : {}",emp_addr1);
		logger.debug("상세주소 : {}",emp_addr2);
		logger.debug("용도 : {}",crt_purpose);
		logger.debug("제출처 : {}",crt_submission);
		logger.debug("제출예정일 : {}",crt_subdt);
		logger.debug("사원번호 : {}",empvo.getEmp_sq());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		
		CertificateVo certificateVo = new CertificateVo();
		
		certificateVo.setCrt_emp_sq(empvo.getEmp_sq());  // 사원번호
		certificateVo.setCrt_div_sq(Integer.parseInt(crt_div_sq));  // 증명서 구분번호
		certificateVo.setCrt_dt(sdf.parse(crt_dt)); // 증명서 신청일
		certificateVo.setCrt_whether("1");	      // 처음은 무조건 1: 승인대기 2: 발급완료 3:반려
		certificateVo.setCrt_emp_dpt(Integer.parseInt(emp_dpt));    // 사원부서
		certificateVo.setCrt_emp_grade(Integer.parseInt(emp_grade)); // 사원직급
		certificateVo.setCrt_emp_phone(emp_phone); // 사원 핸드폰번호
		certificateVo.setCrt_emp_nm(emp_nm);	   // 사원 이름
		certificateVo.setCrt_dos_str(sdf.parse(crt_dos_str)); // 입사일
		certificateVo.setCrt_dos_end(sdf.parse(crt_dos_end)); // 퇴사일
		certificateVo.setCrt_emp_addr1(emp_addr1);// 사원주소
		certificateVo.setCrt_emp_addr2(emp_addr2);// 사원 주소
		certificateVo.setCrt_submission(crt_submission); // 제출처
		certificateVo.setCrt_subdt(sdf.parse(crt_subdt));   // 제출 예정일
		certificateVo.setCrt_con(crt_purpose);  // 제출용도 1:일반용 2: 회사제출 3:공공기관 4:금융기관
		
		
		int insert = certificateService.insertCrt(certificateVo);
		
		if(insert == 1){
			return "redirect:/ctfList";
		}else{
			return "certificateapply";
		}
		
	}
	
	
	@RequestMapping(path="crtStateUpdate",produces={"application/json"})
	@ResponseBody
	public CertificateVo stateupdate(@RequestBody CertificateVo certificateVo,HttpSession session) throws ParseException{
		
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int crt_emp_sq = empvo.getEmp_sq();
		
		CertificateVo crtVo = certificateService.select_Crtdetail(certificateVo.getCrt_sq());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date time = new Date();
		String today = sdf.format(time); 
		
		crtVo.setCrt_emp_app_sq(crt_emp_sq); // 관리자 아이디
		crtVo.setCrt_sign_dt(sdf.parse(today)); // 승인한 오늘날짜
		
		if(certificateVo.getCrt_whether().equals("2")){  // 2: 승인
			crtVo.setCrt_whether("2"); 
		}if(certificateVo.getCrt_whether().equals("3")){ // 3: 반려
			crtVo.setCrt_whether("3");
		}
		
		int update = certificateService.updateCrt(crtVo);
		
		return crtVo;
	}
	
	
	//다운로드를 어디서 해야할까요?
	
	
}
