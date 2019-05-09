package kr.or.nko.email.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.nko.email.model.EmlFileVo;
import kr.or.nko.email.model.EmlReceiveVo;
import kr.or.nko.email.model.EmlSendVo;
import kr.or.nko.email.model.EmlTempFileVo;
import kr.or.nko.email.model.EmlTempVo;
import kr.or.nko.email.service.IEmailService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.employee.service.IEmployeeService;
import kr.or.nko.util.UtilNKO;

@Controller
public class EmailController {
	
	private Logger logger = LoggerFactory.getLogger(EmailController.class);
	
	@Resource(name="emailService")
	private IEmailService emailService;
	
	@Resource(name="employeeService")
	private IEmployeeService employeeService;
	
	@Resource(name="utilNKO")
	private UtilNKO utilNKO;
	
	@RequestMapping(path="/emailSend", method=RequestMethod.GET)
	public String emailSendView(HttpSession session, Model model, @RequestParam Map<String, Object> paramMap){
		logger.debug("/emailSend -> paramMap : {}", paramMap);
		
		//임시메일번호가 null이 아니면 임시메일함에서 넘어온것이고, null이면 새로 작성하는 임시메일임
		if(paramMap.get("eml_temp_sq") != null){
			//임시메일번호
			int eml_temp_sq = Integer.parseInt((String)paramMap.get("eml_temp_sq"));
			
			//임시메일번호와 읽은표시 세팅후 임시메일 수정후 조회
			EmlTempVo emlTempVo = new EmlTempVo();
			emlTempVo.setEml_temp_sq(eml_temp_sq);
			emlTempVo.setEml_temp_chk("Y");
			
			//임시메일 읽은표시 수정
			int updateTemp = emailService.updateTemp(emlTempVo);
			logger.debug("/emailSend -> updateTemp : {}", updateTemp);
			
			//임시메일 조회후 model에 추가
			List<EmlTempVo> emlTempList = emailService.selectTempList(emlTempVo);
			model.addAttribute("emlTemp", emlTempList.get(0));
			logger.debug("/emailSend -> emlTemp : {}", emlTempList.get(0));
			logger.debug("/emailSend -> emlTemp eml_temp_con : {}", emlTempList.get(0).getEml_temp_con());
			logger.debug("/emailSend -> emlTempList : {}", emlTempList);
			
			//임시메일 해당 임시파일 리스트 조회후 model에 추가
			List<EmlTempFileVo> emlTempFileList = emailService.selectTempFileList(eml_temp_sq);
			model.addAttribute("emlTempFileList", emlTempFileList);
			logger.debug("/emailSend -> emlTempFileList : {}", emlTempFileList);
			
			//임시메일번호를 session에 세팅
			session.setAttribute("eml_temp_sq", eml_temp_sq);
		}else{
			//새로고침시 원래 초기화되는게 맞음
			session.removeAttribute("eml_temp_sq");
		}
		
		//모든 사원 정보 model에 추가
		List<EmployeeVo> employeeList = employeeService.selectAllList();
		model.addAttribute("employeeList", employeeList);
		
		return "emailSend";
	}
	
	//파일을 올리면 -> ajax로 업로드하고 -> 파일리스트로 파일라벨을 출력
	@RequestMapping(path="/ajax/emailFileUpload", method=RequestMethod.POST)
	@ResponseBody
	public List<EmlTempFileVo> emailFileUpload(MultipartHttpServletRequest request, HttpSession session) throws Exception{
		//multi file
		List<MultipartFile> fileList = request.getFiles("attach");
		
		logger.debug("/ajax/emailFileUpload -> list : {}", fileList);
		logger.debug("/ajax/emailFileUpload -> listSize : {}", fileList.size());
		
		//세션에서 임시메일번호가 존재하지않으면 임시메일 등록, 존재하면 임시메일번호 가져오기
		if(session.getAttribute("eml_temp_sq") == null){
			//처음 임시메일 등록
			EmlTempVo emlTempVo = new EmlTempVo();
			emlTempVo.setEml_temp_nm("");
			emlTempVo.setEml_temp_con("");
			emlTempVo.setEml_temp_rec("");
			emlTempVo.setEml_temp_emp_sq(((EmployeeVo)session.getAttribute("employeeVo")).getEmp_sq());
			int insertResult = emailService.insertTemp(emlTempVo);
			
			//정상적으로 등록된 경우에만
			if(insertResult == 1){
				session.setAttribute("eml_temp_sq", emlTempVo.getEml_temp_sq());
			}
			
			logger.debug("/emailSend -> insertResult : {}", insertResult);
		}
		
		//세션에서 임시메일번호 가져오기
		int eml_temp_sq = (int) session.getAttribute("eml_temp_sq");;
		
		//파일등록결과
		int fileResult = 0;
		
		//파일이름과 저장경로 초기화
		String eml_temp_fl_nm = "";
		String eml_temp_fl_path = "";
		
		//파일 등록하기
		for(int i=0; i<fileList.size(); i++){
			if(fileList.get(i).getSize() > 0 && eml_temp_sq != 0){
				logger.debug("/ajax/emailFileUpload -> filename : {}", fileList.get(i).getOriginalFilename());
				
				//파일이름과 저장경로 설정
				eml_temp_fl_nm = fileList.get(i).getOriginalFilename();
				eml_temp_fl_path = "d:\\picture\\mail\\" + UUID.randomUUID().toString();
				//eml_temp_fl_path = "\\\\Sem-pc\\공유폴더\\최종프로젝트\\1조\\images\\" + UUID.randomUUID().toString();
				
				//파일전송
				fileList.get(i).transferTo(new File(eml_temp_fl_path));
				
				//파일명과 저장경로 세팅
				EmlTempFileVo emlTempFileVo = new EmlTempFileVo();
				emlTempFileVo.setEml_temp_sq(eml_temp_sq);
				emlTempFileVo.setEml_temp_fl_nm(eml_temp_fl_nm);
				emlTempFileVo.setEml_temp_fl_path(eml_temp_fl_path);
				
				fileResult += emailService.insertTempFile(emlTempFileVo);
			}
		}
		
		//파일등록결과
		logger.debug("/ajax/emailFileUpload -> fileResult : {}", fileResult);
		
		//해당 메일 파일리스트 가져오기
		List<EmlTempFileVo> emlTempFileList = emailService.selectTempFileList(eml_temp_sq);

		return emlTempFileList;
	}
	
	//파일을 올리면 -> ajax로 업로드하고 -> 파일리스트로 파일라벨을 출력
	@RequestMapping(path="/ajax/emailFileDelete", method=RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public List<EmlTempFileVo> emailFileDelete(@RequestBody Map<String, Object> map, HttpSession session){
		//파일번호를 받아와서 삭제하기
		int eml_temp_fl_sq = (int) map.get("eml_temp_fl_sq");
		int fileResult = emailService.deleteTempFile(eml_temp_fl_sq);
		
		//파일삭제결과
		logger.debug("/ajax/emailFileDelete -> fileResult : {}", fileResult);
		
		//세션에서 임시메일번호 가져오기
		int eml_temp_sq = (int) session.getAttribute("eml_temp_sq");
		
		//해당 메일 파일리스트 가져오기
		List<EmlTempFileVo> emlTempFileList = emailService.selectTempFileList(eml_temp_sq);
		
		return emlTempFileList;
	}
	
	@RequestMapping(path="/ajax/emailTempUpdate", method=RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public void emailTempUpdate(@RequestBody Map<String, Object> map, HttpSession session){
		logger.debug("/ajax/emailTempUpdate -> map : {}", map);
		
		String email_to = (String) map.get("email_to") == null ? "" : (String) map.get("email_to");
		String email_title = (String) map.get("email_title") == null ? "" : (String) map.get("email_title");
		String email_content = (String) map.get("email_content") == null ? "" : (String) map.get("email_content");
		
		EmlTempVo emlTempVo = new EmlTempVo();
		emlTempVo.setEml_temp_rec(email_to);
		emlTempVo.setEml_temp_nm(email_title);
		emlTempVo.setEml_temp_con(email_content);
		emlTempVo.setEml_temp_emp_sq(((EmployeeVo)session.getAttribute("employeeVo")).getEmp_sq());
		
		//세션에 임시메일번호가 존재하면 update, 존재하지않으면 insert
		if(session.getAttribute("eml_temp_sq") != null){
			emlTempVo.setEml_temp_sq((int) session.getAttribute("eml_temp_sq"));
			int updateResult = emailService.updateTemp(emlTempVo);
			logger.debug("/emailTempUpdate -> updateResult : {}", updateResult);
		}else{
			int insertResult = emailService.insertTemp(emlTempVo);
			logger.debug("/emailTempUpdate -> insertResult : {}", insertResult);
			
			//세션에 임시메일번호 세팅
			session.setAttribute("eml_temp_sq", emlTempVo.getEml_temp_sq());
		}
		logger.debug("/emailTempUpdate -> emlTempVo : {}", emlTempVo);
	}
	
	@RequestMapping(path="/emailSend", method=RequestMethod.POST)
	public String emailSend(@RequestParam Map<String, Object> map, HttpSession session) throws Exception{
		logger.debug("/emailSend -> map : {}", map);
		
		String[] email_to = ((String) map.get("email_to")).split(",");
		String email_title = (String) map.get("email_title") == null ? "" : (String) map.get("email_title");
		String email_content = (String) map.get("email_content") == null ? "" : (String) map.get("email_content");
		
		//임시메일이 없는경우 임시메일을 먼저 insert한후 시퀀스번호를 가져와야함
		EmlTempVo emlTempVo = new EmlTempVo();
		emlTempVo.setEml_temp_rec((String) map.get("email_to"));
		emlTempVo.setEml_temp_nm(email_title);
		emlTempVo.setEml_temp_con(email_content);
		emlTempVo.setEml_temp_emp_sq(((EmployeeVo)session.getAttribute("employeeVo")).getEmp_sq());
		
		if(session.getAttribute("eml_temp_sq") == null){
			int insertResult = emailService.insertTemp(emlTempVo);
			logger.debug("/emailSend -> insertResult : {}", insertResult);
			
			//세션에 임시메일번호 세팅
			session.setAttribute("eml_temp_sq", emlTempVo.getEml_temp_sq());
		}
		logger.debug("/emailSend -> emlTempVo : {}", emlTempVo);
		
		//세션에서 임시메일번호 가져오기
		int eml_temp_sq = (int) session.getAttribute("eml_temp_sq");
		
		//세션에서 접속한 사원의 정보 가져오기
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		
		//받는 이메일 주소가 여러개일때를 고려하여 첨부파일은 한번만 등록하기 위한 변수
		boolean attachFlag = false;
		//보낸 이메일을 한번만 등록하기 위한 변수
		boolean sendFlag = false;
		//전송결과
		int insertSend = 0;
		
		//이메일 보내기
		for(int i=0; i<email_to.length; i++){
			//mail api로 이메일 보내기
			String send = utilNKO.emailSend(session, email_to[i], email_title, email_content);
			logger.debug("/emailSend -> send : {}", send);
			
			//정상적으로 이메일을 보낸경우(보낸메일함에는 1번만 등록됨)
			if(send.equals("success") && sendFlag == false){
				//보낸메일함에 등록후 -> 파일함에 등록한후 -> 받은메일 등록 -> 임시파일 삭제 -> 임시메일 삭제
				EmlSendVo emlSendVo = new EmlSendVo();
				emlSendVo.setEml_send_sq(eml_temp_sq);
				emlSendVo.setEml_emp_send_sq(employeeVo.getEmp_sq());
				emlSendVo.setEml_send_nm(email_title);
				emlSendVo.setEml_send_con(email_content);
				emlSendVo.setEml_send_rec((String) map.get("email_to"));
				insertSend = emailService.insertSend(emlSendVo);
				
				logger.debug("/emailSend -> insertSend : {}", insertSend);
				
				sendFlag = true;
			}
			
			//보낸메일에 정상적으로 등록된 경우
			if(insertSend == 1){
				EmlFileVo emlFileVo = new EmlFileVo();
				EmlReceiveVo emlReceiveVo = new EmlReceiveVo();
				
				//임시파일리스트 정보를 가지고 파일함 등록
				List<EmlTempFileVo> emlTempFileList = emailService.selectTempFileList(eml_temp_sq);
				if(emlTempFileList.size() > 0 && attachFlag == false){
					for(int j=0; j<emlTempFileList.size(); j++){
						//파일 정보 세팅하고 등록
						emlFileVo.setEml_send_sq(eml_temp_sq);
						emlFileVo.setEml_fl_nm(emlTempFileList.get(j).getEml_temp_fl_nm());
						emlFileVo.setEml_fl_path(emlTempFileList.get(j).getEml_temp_fl_path());
						int insertFile = emailService.insertFile(emlFileVo);
						
						logger.debug("/emailSend -> insertFile : {}", insertFile);
					}
					attachFlag = true; //첨부파일을 등록했으면 상태변화 변수를 true로 설정
				}
				
				//받는사람의 사원번호를 조회하여 존재하면 받은메일함에 등록하고 존재하지않으면 사원이 아니라 외부사람이므로 등록하지 않는다
				EmployeeVo paramEmployeeVo = new EmployeeVo();
				paramEmployeeVo.setEmp_com_email(email_to[i]);
				List<EmployeeVo> employeeList = employeeService.selectListSearch(paramEmployeeVo);
				
				if(employeeList.size() > 0){
					int emp_rec_sq = employeeService.selectListSearch(paramEmployeeVo).get(0).getEmp_sq();
					emlReceiveVo.setEml_emp_rec_sq(emp_rec_sq);
					emlReceiveVo.setEml_emp_send_sq(employeeVo.getEmp_sq());
					emlReceiveVo.setEml_send_sq(eml_temp_sq);
					emlReceiveVo.setEml_rec_nm(email_title);
					emlReceiveVo.setEml_rec_con(email_content);
					emlReceiveVo.setEml_rec_chk("N");
					emlReceiveVo.setEml_rec_ipt("N");
					emlReceiveVo.setEml_send_email(employeeVo.getEmp_com_email());
					
					int insertReceive = emailService.insertReceive(emlReceiveVo);
					
					logger.debug("/emailSend - > insertReceive : {}", insertReceive);
				}
			}
		}
		
		//메일이 전송되면 임시파일과 임시메일 삭제
		int deleteTempFile = emailService.deleteTempFiles(eml_temp_sq);
		int deleteTemp = emailService.deleteTemp(eml_temp_sq);
		
		logger.debug("/emailSend -> deleteTemp : {}", deleteTemp);
		logger.debug("/emailSend -> deleteTempFile : {}", deleteTempFile);
		
		//메일이 전송되면 세션에서 임시메일번호 삭제
		session.removeAttribute("eml_temp_sq");
		
		return "redirect:/sendMailbox";
	}
	
	@RequestMapping(path="/ajax/emailSearch", method=RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public List<EmployeeVo> emailSearch(@RequestBody Map<String, Object> map){
		
		logger.debug("/ajax/emailSearch -> map : {}", map);
		EmployeeVo employeeVo = new EmployeeVo();
		employeeVo.setEmp_nm((String) map.get("email_search"));
		employeeVo.setEmp_com_email((String) map.get("email_search"));
		
		List<EmployeeVo> employeeList = employeeService.selectListSearch(employeeVo);
		logger.debug("/ajax/emailSearch -> employeeList : {}", employeeList);
		
		return employeeList;
	}
	
	@RequestMapping(path="/ajax/emailSelect", method=RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public List<EmployeeVo> emailSelect(@RequestBody Map<String, Object> map){
		
		logger.debug("/ajax/emailSelect -> map : {}", map);
		EmployeeVo employeeVo = new EmployeeVo();
		employeeVo.setEmp_com_email((String) map.get("email_select"));
		
		List<EmployeeVo> employeeList = employeeService.selectListSearch(employeeVo);
		logger.debug("/ajax/emailSelect -> employeeList : {}", employeeList);
		
		return employeeList;
	}
	
	@RequestMapping(path="/sendMailbox", method=RequestMethod.GET)
	public String sendMailbox(HttpSession session, Model model){
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eml_emp_send_sq", employeeVo.getEmp_sq());
		
		//보낸메일리스트 조회
		List<EmlSendVo> emlSendList = emailService.selectSendList(map);
		logger.debug("/sendMailbox -> emlSendList : {}", emlSendList);
		
		//모델에 보낸메일리스트 추가
		model.addAttribute("emlSendList", emlSendList);
		
		return "sendMailbox";
	}
	
	@RequestMapping(path="/sendMailboxDetail", method=RequestMethod.GET)
	public String sendMailboxDetail(@RequestParam Map<String, Object> paramMap, Model model){
		logger.debug("/sendMailboxDetail -> paramMap : {}", paramMap);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eml_send_sq", (String)paramMap.get("eml_send_sq"));
		
		//보낸메일리스트 조회
		List<EmlSendVo> emlSendList = emailService.selectSendList(map);
		logger.debug("/sendMailboxDetail -> emlSendList : {}", emlSendList);
		
		//모델에 보낸메일리스트 추가
		model.addAttribute("emlSendList", emlSendList);
		
		//보낸메일 파일리스트 조회
		List<EmlFileVo> emlFileList = emailService.selectFileList(map);
		logger.debug("/sendMailboxDetail -> emlFileList : {}", emlFileList);
		
		//모델에 파일리스트 추가
		model.addAttribute("emlFileList", emlFileList);
		
		return "sendMailboxDetail";
	}
	
	@RequestMapping(path="/sendMailboxFile", method=RequestMethod.GET)
	public String sendMailboxFile(@RequestParam Map<String, Object> paramMap, Model model){
		logger.debug("/sendMailboxFile -> paramMap : {}", paramMap);
		
		String eml_fl_sq = (String) paramMap.get("eml_fl_sq");
		model.addAttribute("eml_fl_sq", eml_fl_sq);
		
		return "sendMailboxFileView";
	}
	
	@RequestMapping(path="/ajax/sendMailUpdate", method=RequestMethod.POST)
	@ResponseBody
	public void sendMailUpdate(@RequestBody Map<String, Object> paramMap){
		logger.debug("/ajax/sendMailUpdate -> paramMap : {}", paramMap);
		
		//보낸메일번호와 중요표시 가져오기
		String eml_send_sq = (String) paramMap.get("eml_send_sq");
		String eml_send_ipt = (String) paramMap.get("eml_send_ipt");
		
		//보낸메일번호와 중요표시를 세팅하고 update
		EmlSendVo emlSendVo = new EmlSendVo();
		emlSendVo.setEml_send_sq(Integer.parseInt(eml_send_sq));
		emlSendVo.setEml_send_ipt(eml_send_ipt);
		
		int updateSend = emailService.updateSend(emlSendVo);
		logger.debug("/ajax/sendMailUpdate -> updateSend : {}", updateSend);
	}
	
	@RequestMapping(path="/sendMailDelete", method=RequestMethod.GET)
	public String sendMailDelete(@RequestParam Map<String, Object> paramMap){
		logger.debug("/sendMailDelete -> paramMap : {}", paramMap);
		
		//보낸메일번호 가져오기
		String[] eml_send_sq = ((String) paramMap.get("eml_send_sq")).split(",");
		
		//보낸메일번호와 삭제여부를 세팅하고 update
		EmlSendVo emlSendVo = new EmlSendVo();
		int updateSend = 0;
		for(int i=0; i<eml_send_sq.length; i++){
			emlSendVo.setEml_send_sq(Integer.parseInt(eml_send_sq[i]));
			emlSendVo.setEml_send_del("Y");
			updateSend = emailService.updateSend(emlSendVo);
		}
		logger.debug("/sendMailDelete -> updateSend : {}", updateSend);
		
		return "redirect:/sendMailbox";
	}
	
	@RequestMapping(path="/tempMailbox", method=RequestMethod.GET)
	public String tempMailbox(HttpSession session, Model model){
		//세션에서 접속한 사원정보 가져오기
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		
		//사원번호 세팅한후 임시메일 조회
		EmlTempVo emlTempVo = new EmlTempVo();
		emlTempVo.setEml_temp_emp_sq(employeeVo.getEmp_sq());
		
		List<EmlTempVo> emlTempList = emailService.selectTempList(emlTempVo);
		logger.debug("/tempMailbox -> emlTempList : {}", emlTempList);
		
		//모델에 임시메일리스트 추가
		model.addAttribute("emlTempList", emlTempList);
		
		return "tempMailbox";
	}
	
	@RequestMapping(path="/ajax/tempMailUpdate", method=RequestMethod.POST)
	@ResponseBody
	public void tempMailUpdate(@RequestBody Map<String, Object> paramMap, HttpSession session, Model model){
		logger.debug("/ajax/tempMailUpdate -> paramMap : {}", paramMap);
		
		//임시메일번호와 읽은표시 가져오기
		String eml_temp_sq = (String) paramMap.get("eml_temp_sq");
		String eml_temp_chk = (String) paramMap.get("eml_temp_chk");
		
		//임시메일번호와 읽은표시를 세팅하고 update
		EmlTempVo emlTempVo = new EmlTempVo();
		emlTempVo.setEml_temp_sq(Integer.parseInt(eml_temp_sq));
		emlTempVo.setEml_temp_chk(eml_temp_chk);
		
		int updateSend = emailService.updateTemp(emlTempVo);
		logger.debug("/ajax/tempMailUpdate -> updateSend : {}", updateSend);
	}
	
	@RequestMapping(path="/tempMailDelete", method=RequestMethod.GET)
	public String tempMailDelete(@RequestParam Map<String, Object> paramMap){
		logger.debug("/tempMailDelete -> paramMap : {}", paramMap);
		
		//임시메일번호 가져오기
		String[] eml_temp_sq = ((String) paramMap.get("eml_temp_sq")).split(",");
		
		//임시메일번호로 임시파일 먼저 삭제후 임시메일 삭제
		int deleteTemp = 0;
		int deleteTempFile = 0;
		
		for(int i=0; i<eml_temp_sq.length; i++){
			List<EmlTempFileVo> emlTempFileList = emailService.selectTempFileList(Integer.parseInt(eml_temp_sq[i]));
			
			//임시파일이 존재할때 임시파일 삭제후 임시메일 삭제, 존재하지않으면 임시메일 삭제
			if(emlTempFileList.size() > 0){
				deleteTempFile = emailService.deleteTempFiles(Integer.parseInt(eml_temp_sq[i]));
				logger.debug("/tempMailDelete -> deleteTempFile : {}", deleteTempFile);
				
				//해당임시메일의 임시파일이 해당임시파일리스트 사이즈만큼 삭제 성공하면 임시메일 삭제
				if(deleteTempFile == emlTempFileList.size()){
					deleteTemp = emailService.deleteTemp(Integer.parseInt(eml_temp_sq[i]));
					logger.debug("/tempMailDelete -> deleteTemp : {}", deleteTemp);
				}
			}else{
				deleteTemp = emailService.deleteTemp(Integer.parseInt(eml_temp_sq[i]));
				logger.debug("/tempMailDelete -> deleteTemp : {}", deleteTemp);
			}
			
		}
		
		return "redirect:/tempMailbox";
	}
	
	@RequestMapping(path="/receiveMailbox", method=RequestMethod.GET)
	public String receiveMailbox(HttpSession session, Model model){
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		
		//외부에서 받은메일 db에 저장
		utilNKO.emailReceivePop(employeeVo.getEmp_com_email(), "test111111");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eml_emp_rec_sq", employeeVo.getEmp_sq());
		
		//받은메일리스트 조회
		List<EmlReceiveVo> emlReceiveList = emailService.selectReceiveList(map);
		logger.debug("/receiveMailbox -> emlReceiveList : {}", emlReceiveList);
		
		//모델에 받은메일리스트 추가
		model.addAttribute("emlReceiveList", emlReceiveList);
		
		return "receiveMailbox";
	}
	
	@RequestMapping(path="/ajax/receiveMailUpdate", method=RequestMethod.POST)
	@ResponseBody
	public void receiveMailUpdate(@RequestBody Map<String, Object> paramMap, HttpSession session, Model model){
		logger.debug("/ajax/receiveMailUpdate -> paramMap : {}", paramMap);
		
		//받은메일번호와 중요표시, 읽은표시 가져오기
		String eml_rec_sq = (String) paramMap.get("eml_rec_sq");
		String eml_rec_ipt = (String) paramMap.get("eml_rec_ipt");
		String eml_rec_chk = (String) paramMap.get("eml_rec_chk");
		
		//받은메일번호와 중요표시, 읽은표시를 세팅하고 update
		EmlReceiveVo emlReceiveVo = new EmlReceiveVo();
		emlReceiveVo.setEml_rec_sq(Integer.parseInt(eml_rec_sq));
		emlReceiveVo.setEml_rec_ipt(eml_rec_ipt);
		emlReceiveVo.setEml_rec_chk(eml_rec_chk);
		
		int updateRec = emailService.updateReceive(emlReceiveVo);
		logger.debug("/ajax/receiveMailUpdate -> updateRec : {}", updateRec);
	}
	
	@RequestMapping(path="/receiveMailDelete", method=RequestMethod.GET)
	public String receiveMailDelete(@RequestParam Map<String, Object> paramMap){
		logger.debug("/sendMailDelete -> paramMap : {}", paramMap);
		
		//받은메일번호 가져오기
		String[] eml_rec_sq = ((String) paramMap.get("eml_rec_sq")).split(",");
		
		//받은메일번호와 삭제여부를 세팅하고 update
		EmlReceiveVo emlReceiveVo = new EmlReceiveVo();
		int updateSend = 0;
		for(int i=0; i<eml_rec_sq.length; i++){
			emlReceiveVo.setEml_rec_sq(Integer.parseInt(eml_rec_sq[i]));
			emlReceiveVo.setEml_rec_del("Y");
			updateSend = emailService.updateReceive(emlReceiveVo);
		}
		logger.debug("/receiveMailDelete -> updateSend : {}", updateSend);
		
		return "redirect:/receiveMailbox";
	}
	
	@RequestMapping(path="/receiveMailboxDetail", method=RequestMethod.GET)
	public String receiveMailboxDetail(@RequestParam Map<String, Object> paramMap, Model model){
		logger.debug("/receiveMailboxDetail -> paramMap : {}", paramMap);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eml_rec_sq", (String)paramMap.get("eml_rec_sq"));
		map.put("eml_send_sq", (String)paramMap.get("eml_send_sq"));
		
		//받은메일번호와 읽은표시를 세팅하고 update
		EmlReceiveVo emlReceiveVo = new EmlReceiveVo();
		emlReceiveVo.setEml_rec_sq(Integer.parseInt((String)paramMap.get("eml_rec_sq")));
		emlReceiveVo.setEml_rec_chk("Y");
		
		int updateRec = emailService.updateReceive(emlReceiveVo);
		logger.debug("/ajax/receiveMailboxDetail -> updateRec : {}", updateRec);
		
		//받은메일리스트 조회
		List<EmlReceiveVo> emlReceiveList = emailService.selectReceiveList(map);
		logger.debug("/receiveMailboxDetail -> emlReceiveList : {}", emlReceiveList);
		
		//모델에 받은메일리스트 추가
		model.addAttribute("emlReceiveList", emlReceiveList);
		
		//받은메일 파일리스트 조회
		List<EmlFileVo> emlFileList = emailService.selectFileList(map);
		logger.debug("/receiveMailboxDetail -> emlFileList : {}", emlFileList);
		
		//모델에 파일리스트 추가
		model.addAttribute("emlFileList", emlFileList);
		
		return "receiveMailboxDetail";
	}
	
	//	cron에서
	//	* * * * * * 의미하는것은 각각
	//	초 분 시 일 월 요일 의미함(한칸씩만 띄어야함, 초기값은 1초마다)
	//	
	//	ex) 5   *   *   *   *   * : 매 5초(1분 5초, 2분 5초...)에 실행
	//		*/5 *   *   *   *   * : 5초마다 실행
	//		*   *   17  *   *   * : 매일 17시 마다 실행
	@Scheduled(cron="*/5 * * * * *")
	public void tempTask() {
		
	}
}