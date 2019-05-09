package kr.or.nko.login;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.nko.admin.model.AuthorityVo;
import kr.or.nko.admin.service.IAuthorityService;
import kr.or.nko.approval.model.SignalVo;
import kr.or.nko.approval.service.IApprovalService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.employee.service.IEmployeeService;
import kr.or.nko.log.model.LogVo;
import kr.or.nko.log.service.ILogService;
import kr.or.nko.todolist.model.TodolistVo;
import kr.or.nko.todolist.service.ITodolistService;
import kr.or.nko.util.KISA_SHA256;

@Controller
public class LoginController {
	
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Resource(name = "employeeService")
	private IEmployeeService employeeService;
	
	@Resource(name="authorityService")
	private IAuthorityService authorityService;
	
	@Resource(name="todolistService")
	private ITodolistService todolistService;
	
	@Resource(name="approvalService")
	private IApprovalService approvalService;
	
	@Resource(name="logService")
	private ILogService logService;

	@RequestMapping(path = { "/login" }, method = { RequestMethod.GET })
	public String loginView() {
		return "login/login";
	}

	@RequestMapping(path = { "/login" }, method = { RequestMethod.POST })
	public String loginProcess(EmployeeVo EmployeeVo, HttpSession session, Model model, RedirectAttributes rats,HttpServletRequest request) {
		EmployeeVo dbEmployeeVo = employeeService.selectEmployee((EmployeeVo.getEmp_sq()));
		
		if (dbEmployeeVo == null) {
			rats.addFlashAttribute("msg", "로그인에 실패하였습니다.");
			return "redirect:/login";
		}else if(dbEmployeeVo.getEmp_flag().equals("fire") ){
			rats.addFlashAttribute("msg", "삭제된 계정입니다.");
			return "redirect:/login";
			
		}
		
		if (dbEmployeeVo.getEmp_sq() == EmployeeVo.getEmp_sq()
				&& dbEmployeeVo.getEmp_pass().equals(KISA_SHA256.encrypt(EmployeeVo.getEmp_pass()))) {
			
			LogVo logvo = new LogVo();
			String inetAddress = null;
			inetAddress = request.getRemoteAddr();
			logvo.setLog_ip(inetAddress);
			session.setAttribute("employeeVo", dbEmployeeVo);

			logvo.setLog_id(dbEmployeeVo.getEmp_sq());
			logvo.setSession_id(session.getId());
			logService.insertLog(logvo);
			session.setAttribute("session_id", session.getId());
			rats.addFlashAttribute("msg", "환영합니다.");
			//권한 확인
			AuthorityVo authorityVo = authorityService.selectAuth(dbEmployeeVo.getEmp_sq());
			if(authorityVo != null){
				session.setAttribute("authority", authorityVo.getAut_admin_sq());
			}
			
			// todolist수 확인
			EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
			int memId = empvo.getEmp_sq();
			int todosize = 0;
			
			List<TodolistVo> todolist = todolistService.selectTodo(memId);
			
			for(int i=0;i<todolist.size();i++){
				if(todolist.get(i).getTdl_comp().equals("n")){
					todosize++;
				}
			}
			session.setAttribute("todoSize",todosize);
			session.setAttribute("memIds", memId);
					
			// 전자결재 알림 수 조회
			List<SignalVo> signalList = approvalService.selectSignal(dbEmployeeVo.getEmp_sq());
			session.setAttribute("signalSize", signalList.size());
			session.setAttribute("signalList", signalList);
			
			return "redirect:/main";
		} else {
			rats.addFlashAttribute("msg", "로그인에 실패하였습니다.");
			return "redirect:/login";
		}
	}
	
	//아이디 찾기
	
	@RequestMapping(path={"/findid"}, method={RequestMethod.GET})
	public String find_id(){
		
		return "login/find_Id";
	}
	
	@RequestMapping(path={"/findid"}, method={RequestMethod.POST})
	public String find_id_post(EmployeeVo employeeVo,Model model,
							   String emp_nm, String emp_phone,RedirectAttributes rats){
		
		logger.debug("사원 이름은 : {}", emp_nm);
		logger.debug("사원 전화번호는 : {}", emp_phone);
		employeeVo.setEmp_nm(emp_nm);
		employeeVo.setEmp_phone(emp_phone);
		
		EmployeeVo vo = new EmployeeVo(); 
		vo = employeeService.select_Empfindid(employeeVo);
		String msg = "";
		String value = "";
		if(vo != null){
			msg = "회원님이 입력하신 정보와 일치하는 아이디가 있습니다.";
			value = "찾으시는 아이디는 <strong style='text-decoration: underline;'>'"+ vo.getEmp_sq() + "'</strong>입니다.";
			model.addAttribute("msg", msg);
			model.addAttribute("value", value);
			model.addAttribute("EmployeeVo", vo);
			
			return "login/find_Id";
		}else{
			msg = "회원님이 입력하신 정보와 일치하는 정보가 없습니다.";
			model.addAttribute("msg", msg);
			rats.addFlashAttribute("msg", "일치하는 정보가 없습니다.");
			
			return "redirect:/findid";
		}
	}
	
	//비밀번호 찾기
	
	@RequestMapping(path="/findpw", method=RequestMethod.GET)
	public String find_pw(){
		
		return "login/find_Pw";
	}
	
	@RequestMapping(path="/findpw", method=RequestMethod.POST)
	public String find_pw_post(EmployeeVo employeeVo, String pw_emp_sq , String pw_emp_phone, Model model,RedirectAttributes rats){
		
		int findpw_emp_sq = Integer.parseInt(pw_emp_sq);
		
		employeeVo.setEmp_sq(findpw_emp_sq);
		employeeVo.setEmp_phone(pw_emp_phone);
		

		EmployeeVo findpwVo = new EmployeeVo();
		findpwVo = employeeService.select_Empfindpw(employeeVo);
		
		String find_msg = "";
		
		if(findpwVo != null ){
			
			String send_result = sendEmail(findpwVo.getEmp_psn_email().toLowerCase());
			
			if(send_result.equals("error")){
				find_msg = "메일 전송 오류, 다시 시도해 주세요";
			}
			else{
				findpwVo.setEmp_pass(KISA_SHA256.encrypt(send_result));
				employeeService.updateEmployee(findpwVo);
				find_msg = "임시 비밀번호를 회원님의 이메일로 전송하였습니다";
				rats.addFlashAttribute("find_msg", find_msg);
			}
			return "redirect:/login";			
		}else{
			find_msg = " 입력하신 정보와 일치하는 정보가 존재하지 않습니다.";
			model.addAttribute("find_msg", find_msg);
			
			return "login/find_Pw";
		}
		
	}
	// javamail.api 사용해서 이메일 전송
	public String sendEmail(String to_email) {
		
		//보내는 사람 email
		String host = "smtp.daum.net";
		final String user = "sksvkdlxj77@nkby.shop";
		final String password = "test111111";
		
		//받는 사람 email
		String to = to_email;

		// Get the session object
		// daum은 ssl 사용여부 설정 해줘야함.
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.port", "465");
		
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		String[] SecurityCode = UUID.randomUUID().toString().split("-");
		
		// 메일 정보
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// 제목
			message.setSubject("[NKOffice] 임시 비밀번호 발송");

			// 내용
			message.setText("[임시 비밀번호 : "+ SecurityCode[0] +" 입니다.]");
			
			// 전송
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
			return "error";
		}
		return SecurityCode[0];
	}
	
	@RequestMapping(path="/socketEmployeeList", method=RequestMethod.POST)
	@ResponseBody
	public List<EmployeeVo> socketEmployeeList(HttpSession session){
		session.setAttribute("socketEmployeeList", (List<EmployeeVo>)employeeService.getAllEmp());
		
		logger.debug("소켓 {}", session.getAttribute("socketEmployeeList"));
		return (List<EmployeeVo>)employeeService.getAllEmp();
	}
	
}