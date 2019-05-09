package kr.or.nko.employee.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.nko.department.model.DepartmentVo;
import kr.or.nko.department.service.IDepartmentService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.employee.service.IEmployeeService;
import kr.or.nko.log.service.ILogService;
import kr.or.nko.util.KISA_SHA256;

@Controller
public class EmployeeController {

	private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Resource(name = "employeeService")
	private IEmployeeService employeeService;
	@Resource(name = "departmentService")
	private IDepartmentService departmentService;
	
	@Resource(name="logService")
	private ILogService logService;

	@RequestMapping("/userInfo")
	public String UserInfoView(HttpSession session, Model model) {
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");

		EmployeeVo eVo = employeeService.selectEmployee(employeeVo.getEmp_sq());
		DepartmentVo departmentVo = departmentService.selectDepartment(employeeVo.getEmp_dpt());

		model.addAttribute("eVo", eVo);
		model.addAttribute("departmentVo", departmentVo);
		return "userInfo";
	}

	@RequestMapping("/userImg")
	public void profileImg(HttpServletRequest req, HttpServletResponse resp, @RequestParam("emp_sq") int emp_sq)
			throws IOException {
		resp.setHeader("Content-Disposition", "attachment; filename=profile.png");
		resp.setContentType("image/png");

		EmployeeVo empInfoVo = employeeService.selectEmployee(emp_sq);

		FileInputStream fis;
		if (empInfoVo != null && empInfoVo.getEmp_img_realpath() != null) {
			fis = new FileInputStream(new File(empInfoVo.getEmp_img_realpath()));
		}

		else {
			ServletContext application = req.getServletContext();
			String noimgPath = application.getRealPath("/images/noimg.png");
			fis = new FileInputStream(new File(noimgPath));
		}

		ServletOutputStream sos = resp.getOutputStream();

		byte[] buff = new byte[512];
		int len = 0;
		while ((len = fis.read(buff)) > -1) {
			sos.write(buff);
		}

		sos.close();
		fis.close();
	}

	@RequestMapping(path = { "/userPassCheck" }, method = { RequestMethod.GET })
	public String UserPassCheckView() {
		return "userPassCheck";
	}

	@RequestMapping(path = { "/userPassCheck" }, method = { RequestMethod.POST })
	public String UserPassCheckProcess(@RequestParam("emp_pass") String emp_pass, HttpSession session,
			RedirectAttributes ra) {
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");

		if (KISA_SHA256.encrypt(emp_pass).equals(employeeVo.getEmp_pass()))
			return "redirect:/userInfo";
		else {
			ra.addFlashAttribute("msg", "비밀번호가 일치하지 않습니다.");
			return "redirect:/userPassCheck";
		}
	}

	@RequestMapping(path = { "/userModify" }, method = { RequestMethod.GET })
	public String UserModifyView(HttpSession session, Model model) {
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");

		EmployeeVo eVo = employeeService.selectEmployee(employeeVo.getEmp_sq());
		DepartmentVo departmentVo = departmentService.selectDepartment(employeeVo.getEmp_dpt());

		model.addAttribute("eVo", eVo);
		model.addAttribute("departmentVo", departmentVo);
		return "userModify";
	}

	@RequestMapping(path = { "/userModify" }, method = { RequestMethod.POST })
	public String UserModifyProcess(EmployeeVo eVo, @RequestPart("userImg") MultipartFile userImg, HttpSession session,
			RedirectAttributes ra) throws IllegalStateException, IOException {
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		eVo.setEmp_sq(employeeVo.getEmp_sq());

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

		// 비밀번호 수정 요청 여부
		// 사용자가 값을 입력하지 않은 경우 => 기존 비밀번호 유지
		if (eVo.getEmp_pass().equals("")) {
			EmployeeVo eVoForPass = employeeService.selectEmployee(employeeVo.getEmp_sq());
			eVo.setEmp_pass(eVoForPass.getEmp_pass());
		}
		// 사용자가 비밀번호를 신규 등록한 경우
		else
			eVo.setEmp_pass(KISA_SHA256.encrypt(eVo.getEmp_pass()));

		int cnt = 0;
		try {
			cnt = employeeService.updateEmployee(eVo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 정상 입력
		if (cnt == 1) {
			ra.addFlashAttribute("msg", "정상 수정되었습니다.");
			return "redirect:/userInfo";
		}

		else {
			return "redirect:/userModify";
		}
	}

	@RequestMapping(path = { "/userSignSave" }, method = { RequestMethod.POST })
	public String userSignSaveProcess(@RequestParam("signUrl") String signUrl, HttpSession session, Model model) {
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		String emp_sign_nm = UUID.randomUUID().toString();
		String emp_sign = null;

		final String BASE_64_PREFIX = "data:image/png;base64,";
		Path path = Paths.get("\\\\Sem-pc\\공유폴더\\최종프로젝트\\1조\\images\\");

		byte[] bytes = null;

		if (signUrl.startsWith(BASE_64_PREFIX))
			bytes = Base64.getDecoder().decode(signUrl.substring(BASE_64_PREFIX.length()));

		try {
			Files.copy(new ByteArrayInputStream(bytes), path.resolve(emp_sign_nm));
			emp_sign = path.resolve(emp_sign_nm).toAbsolutePath().toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int cnt = 0;
		if (emp_sign != null) {
			employeeVo.setEmp_sign(emp_sign);
			cnt = employeeService.updateEmpSign(employeeVo);
		}

		EmployeeVo eVo = employeeService.selectEmployee(employeeVo.getEmp_sq());
		DepartmentVo departmentVo = departmentService.selectDepartment(employeeVo.getEmp_dpt());
		model.addAttribute("eVo", eVo);
		model.addAttribute("departmentVo", departmentVo);

		if (cnt == 1) {
			model.addAttribute("msg", "서명이 등록되었습니다.");
		} else {
			model.addAttribute("msg", "서명이 등록되지 않았습니다. 다시 시도해 주세요.");
		}

		return "userModify";
	}

	@RequestMapping("/userSign")
	public void signImg(HttpServletRequest req, HttpServletResponse resp, @RequestParam("emp_sq") int emp_sq)
			throws IOException {
		resp.setHeader("Content-Disposition", "attachment; filename=sign.png");
		resp.setContentType("image/png");

		EmployeeVo empVo = employeeService.selectEmployee(emp_sq);

		FileInputStream fis;
		if (empVo != null && empVo.getEmp_sign() != null) {
			fis = new FileInputStream(new File(empVo.getEmp_sign()));
		} else {
			ServletContext application = req.getServletContext();
			String noimgPath = application.getRealPath("/images/noimg.png");
			fis = new FileInputStream(new File(noimgPath));
		}

		ServletOutputStream sos = resp.getOutputStream();

		byte[] buff = new byte[512];
		int len = 0;
		while ((len = fis.read(buff)) > -1) {
			sos.write(buff);
		}

		sos.close();
		fis.close();
	}

	@RequestMapping(path = { "/userLogout" }, method = { RequestMethod.GET })
	public String UserLogout(HttpSession session) {
		logger.debug("sessionid"+session.getId());
		logService.updateLog(session.getId());
		session.invalidate();
		return "redirect:/login";
	}

	@RequestMapping("/deleteEmp")
	public String UserDelete(HttpSession session, EmployeeVo employeeVo) {
		employeeVo.setEmp_nm(" ");
		employeeVo.setEmp_pass(" ");
		employeeVo.setEmp_phone(" ");
		employeeVo.setEmp_com_phone("");
		employeeVo.setEmp_addr1("");
		employeeVo.setEmp_addr2("");
		employeeVo.setEmp_com_email(" ");
		employeeVo.setEmp_psn_email(" ");
		 employeeVo.setEmp_flag("fire");
		 employeeVo.setEmp_sq(employeeVo.getEmp_sq());
		 employeeService.deleteEmp(employeeVo);
		 logger .debug("@@"+employeeVo.getEmp_sq());
		return "redirect:/userManage";

	}

}
