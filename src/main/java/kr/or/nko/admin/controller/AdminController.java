package kr.or.nko.admin.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.nko.department.model.DepartmentVo;
import kr.or.nko.department.service.IDepartmentService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.employee.service.IEmployeeService;
import kr.or.nko.util.KISA_SHA256;
import kr.or.nko.util.UtilNKO;
import kr.or.nko.util.model.PageVo;

@Controller
public class AdminController {
	
	private Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Resource(name="employeeService")
	private IEmployeeService employeeService;
	
	@Resource(name="departmentService")
	private IDepartmentService departmentService;
	
	@Resource(name="utilNKO")
	private UtilNKO utilNKO;
	
	@RequestMapping(path="/userManage", method=RequestMethod.GET)
	public String UserManageView(PageVo pageVo, Model model){
		
		utilNKO.empPaging("1", model, pageVo, new EmployeeVo());
		
		return "userManage";
	}

	@RequestMapping(path="/userSelect", method=RequestMethod.GET)
	public String UserSelect(PageVo pageVo, String selCode, String selText, Model model){

		logger.debug("selCode : {}", selCode);
		logger.debug("selText : {}", selText);
		
		EmployeeVo employeeVo = new EmployeeVo();
		//이름검색인지 사원번호검색인지 부서명검색인지 판단
		if(selCode.equals("1")){
			String emp_nm = selText;
			employeeVo.setEmp_nm(emp_nm);
		}else if(selCode.equals("2")){
			//string이 int로 변환이 가능한지 체크
			boolean check = utilNKO.checkStrToInt(selText);
			
			if(check){
				int emp_sq = Integer.parseInt(selText);
				employeeVo.setEmp_sq(emp_sq);
			}
		}else if(selCode.equals("3") && !selText.equals("")){
			String emp_dpt = selText;
			DepartmentVo departmentVo = departmentService.selectDepart(emp_dpt);
			//부서정보가 null이 아니면 부서코드 세팅 null이면 없는 부서코드인 -1을 세팅
			if(departmentVo != null){
				employeeVo.setEmp_dpt(departmentVo.getDpt_sq());
			}else{
				employeeVo.setEmp_dpt(-1);
			}
		}
		
		//검색한후에 페이지를 넘겨도 검색한 결과가 유지되게 selCode와 selText 넘어온것을 다시 세팅
		model.addAttribute("selCode", selCode);
		model.addAttribute("selText", selText);
		
		utilNKO.empPaging("2", model, pageVo, employeeVo);
		
		return "userManage";
	}
	
	@RequestMapping(path="/userDetail", method=RequestMethod.GET)
	public String UserDetail(EmployeeVo paramEmpVo, Model model){
		//파라미터로 넘어온 사원번호
		int emp_sq = paramEmpVo.getEmp_sq();
		
		//사원번호로 사원조회
		EmployeeVo eVo = employeeService.selectEmployee(emp_sq);
		//부서조회
		DepartmentVo departmentVo = departmentService.selectDepartment(eVo.getEmp_dpt());
		
		//모델에 사원객체와 부서객체 추가
		model.addAttribute("eVo", eVo);
		model.addAttribute("departmentVo", departmentVo);
		
		logger.debug("emp_sq : {}", emp_sq);
		
		return "userDetail";
	}
	
	@RequestMapping(path="/userUpdate", method=RequestMethod.GET)
	public String UserUpdateView(EmployeeVo paramEmpVo, Model model) {
		int emp_sq = paramEmpVo.getEmp_sq();
		
		// 사원번호로 사원 조회
		EmployeeVo eVo = employeeService.selectEmployee(emp_sq);
		
		// 부서 정보 조회
		DepartmentVo departmentVo = departmentService.selectDepartment(eVo.getEmp_dpt());

		// 전체 하위 부서 조회
		List<DepartmentVo> dptList = departmentService.getAllDepartment();
		
		//모델에 사원객체와 부서객체 추가
		model.addAttribute("eVo", eVo);
		model.addAttribute("departmentVo", departmentVo);
		model.addAttribute("dptList", dptList);
		
		logger.debug("emp_sq : {}", emp_sq);
		
		return "userUpdate";
	}
	
	@RequestMapping(path="/userUpdate", method=RequestMethod.POST)
	public String UserUpdateProcess(EmployeeVo eVo,
									@RequestParam("dpt_hr_sq")String dpt_hr_sq,
									@RequestPart("userImg")MultipartFile userImg,
									RedirectAttributes ra) throws IllegalStateException, IOException {
		// 회원 사진 업로드
		String emp_img_path = "";
		String emp_img_realpath = "";
		
		if (userImg.getSize() > 0) {
			emp_img_path = userImg.getOriginalFilename();
			
			emp_img_realpath = "\\\\Sem-pc\\공유폴더\\최종프로젝트\\1조\\images\\" + UUID.randomUUID().toString();
			userImg.transferTo(new File(emp_img_realpath));
		}
	
		eVo.setEmp_img_path(emp_img_path);
		eVo.setEmp_img_realpath(emp_img_realpath);
		
		if (!dpt_hr_sq.equals("")) {
			eVo.setEmp_dpt(Integer.parseInt(dpt_hr_sq));
		}
		
		int cnt = 0;
		try {
			cnt = employeeService.updateManage(eVo);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 정상 입력
		if (cnt == 1) {
			ra.addFlashAttribute("msg", "정상 수정되었습니다.");
			ra.addAttribute("emp_sq", eVo.getEmp_sq());
			return "redirect:/userDetail";
		}
		
		else {
			ra.addAttribute("emp_sq", eVo.getEmp_sq());
			return "redirect:/userUpdate";
		}
	}
	
	@RequestMapping(path="/userInsert", method=RequestMethod.GET)
	public String UserInsertView(Model model) {
		// 전체 하위 부서 조회
		List<DepartmentVo> dptList = departmentService.getAllDepartment();

		model.addAttribute("dptList", dptList);
		
		return "userInsert";
	}
	
	@RequestMapping(path="/userInsert", method=RequestMethod.POST)
	public String UserInsertProcess(EmployeeVo eVo,
									@RequestParam("dpt_hr_sq")String dpt_hr_sq,
									@RequestParam("emp_ent")String emp_ent,
									@RequestPart("userImg")MultipartFile userImg,
									RedirectAttributes ra) throws IllegalStateException, IOException, ParseException {
		// 회원 사진 업로드
		String emp_img_path = "";
		String emp_img_realpath = "";
		
		if (userImg.getSize() > 0) {
			emp_img_path = userImg.getOriginalFilename();
			
			emp_img_realpath = "\\\\Sem-pc\\공유폴더\\최종프로젝트\\1조\\images\\" + UUID.randomUUID().toString();
			userImg.transferTo(new File(emp_img_realpath));
		}
	
		eVo.setEmp_img_path(emp_img_path);
		eVo.setEmp_img_realpath(emp_img_realpath);
		
		// 하위 부서를 선택한 경우
		if (!dpt_hr_sq.equals("")) {
			eVo.setEmp_dpt(Integer.parseInt(dpt_hr_sq));
		}
		
		// 비밀번호 암호화
		eVo.setEmp_pass(KISA_SHA256.encrypt(eVo.getEmp_pass()));
		
		Date emp_ent_date = new java.text.SimpleDateFormat("yyyy/MM/dd").parse(emp_ent);
		eVo.setEmp_ent(emp_ent_date);
		
		int cnt = 0;
		try {
			cnt = employeeService.insertEmployee(eVo);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 정상 입력
		if (cnt == 1) {
			ra.addFlashAttribute("msg", "정상 등록되었습니다.");
			return "redirect:/userInsert";
		}
		
		else {
			ra.addFlashAttribute("msg", "사원 정보가 등록되지 않았습니다. 다시 시도해 주세요.");
			return "redirect:/userInsert";
		}
	}
	
	@RequestMapping(path="/userIdCheck", produces={"application/json"})
	@ResponseBody
	public int UserIdCheck(@RequestBody int emp_sq) {
		logger.debug("emp_sq : {}", emp_sq);
		
		EmployeeVo eVo = employeeService.selectEmployee(emp_sq);
		
		if (eVo == null) {
			return 0;
		} else {
			return 1;
		}
	}
	
}