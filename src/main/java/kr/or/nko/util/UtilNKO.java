package kr.or.nko.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import kr.or.nko.admin.model.AuthorityVo;
import kr.or.nko.admin.service.IAuthorityService;
import kr.or.nko.callingcard.model.CallingCardVo;
import kr.or.nko.callingcard.service.ICallingService;
import kr.or.nko.certificate.model.CertificateVo;
import kr.or.nko.certificate.service.ICertificateService;
import kr.or.nko.department.model.DepartmentVo;
import kr.or.nko.department.service.IDepartmentService;
import kr.or.nko.email.model.EmlFileVo;
import kr.or.nko.email.model.EmlReceiveVo;
import kr.or.nko.email.model.EmlTempFileVo;
import kr.or.nko.email.service.IEmailService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.employee.service.IEmployeeService;
import kr.or.nko.util.model.PageVo;
import kr.or.nko.work.model.ProjectVo;
import kr.or.nko.work.service.IWorkService;

@Service("utilNKO") 
public class UtilNKO {
	
	private Logger logger = LoggerFactory.getLogger(UtilNKO.class);
	
	@Resource(name="employeeService")
	private IEmployeeService employeeService;
	
	@Resource(name="departmentService")
	private IDepartmentService departmentService;
	
	@Resource(name="callingService")
	private ICallingService callingService;
	
	@Resource(name="workService")
	private IWorkService workService;
	
	@Resource(name="authorityService")
	private IAuthorityService authorityService;
	
	@Resource(name="certificateService")
	private ICertificateService certificateService;

	@Resource(name="emailService")
	private IEmailService emailService;
	

	/**
	 * Method : CheckStrToInt
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param s
	 * @return
	 * Method 설명 : string이 변환 가능한지 체크
	 */
	public boolean checkStrToInt(String s){
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Method : empPaging
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param type
	 * @param model
	 * @param pageVo
	 * @param employeeVo
	 * Method 설명 : 관리자 회원관리에서 페이징 처리
	 */
	public void empPaging(String type, Model model, PageVo pageVo, EmployeeVo employeeVo){
		//페이지 정보 받아오기
		int page = pageVo.getPage();
		int pageSize = pageVo.getPageSize();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		
		//사원페이징리스트 조회
		Map<String, Object> resultMap = null;
		List<EmployeeVo> empPagingList = null;
		
		//타입이 1이면 userManage 요청, 타입이 2이면 userSelect요청
		int empCnt = 0;
		if(type.equals("1")){
			resultMap = employeeService.selectEmpPagingList(map, employeeVo);
			empPagingList = (List<EmployeeVo>) resultMap.get("empPagingList");
			
			empCnt = (int) resultMap.get("empCnt");
		}else if(type.equals("2")){
			map.put("emp_nm", employeeVo.getEmp_nm());
			map.put("emp_sq", employeeVo.getEmp_sq());
			map.put("emp_dpt", employeeVo.getEmp_dpt());
			
			resultMap = employeeService.selectEmpPagingList(map, employeeVo);
			empPagingList = (List<EmployeeVo>) resultMap.get("empPagingList");
			
			empCnt = (int) resultMap.get("searchEmpCnt");
		}
		
		//페이지처리에 필요한 정보 구하기
		int lastPage = empCnt/pageSize + (empCnt%pageSize > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((page - 1) / 10) * 10 + 1; 
		int endPage = startPage + 10 - 1;
		
		//모델에 페이지에 관한 정보 추가
		model.addAttribute("employeeList", empPagingList);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		List<DepartmentVo> departmentList = new ArrayList<DepartmentVo>();
		//부서조회
		for(EmployeeVo empVo : empPagingList){
			departmentList.add(departmentService.selectDepartment(empVo.getEmp_dpt()));
		}
		//모델에 부서정보리스트 추가
		model.addAttribute("departmentList", departmentList);
		
		logger.debug("UtilNKO.empPaging -> employeeList : {}", empPagingList);
		logger.debug("UtilNKO.empPaging -> departmentList : {}", departmentList);
	}
	
	/**
	 * Method : projectCheckAuthority
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param employeeVo
	 * @param model
	 * Method 설명 : 프로젝트 권한 확인하여 리스트 추가
	 */
	public void projectCheckAuthority(EmployeeVo employeeVo, HttpSession session){
		//권한 확인
		AuthorityVo authorityVo = authorityService.selectAuth(employeeVo.getEmp_sq());
		
		int authority = 0;
		if(authorityVo != null){
			authority = authorityVo.getAut_admin_sq();
		}
		
		//관리자는 모든 프로젝트 관리
		List<ProjectVo> projectList = workService.selectAllProjectList();
		//공개프로젝트
		List<ProjectVo> openProjectList = new ArrayList<ProjectVo>();
		
		//관리자인지 아닌지 체크
		if(authority != 1){
			//공개프로젝트 구하기
			for(ProjectVo projectVo : projectList){
				if(projectVo.getPro_open().equals("on")){
					openProjectList.add(projectVo);
				}
			}
			
			//사원은 자기가 속한 프로젝트만 관리
			projectList = workService.selectProjectList(employeeVo.getEmp_sq());
			
			//기존 프로젝트에 공개프로젝트 추가
			projectList.addAll(openProjectList);
		}
		
		//세션에 프로젝트리스트 추가
		session.setAttribute("projectList", projectList);
		
		//selectHistoryMapList를 실행하기 위한 파라미터 맵
		Map<String, Object> map = new HashMap<String, Object>();
		
		//프로젝트가 없는 사원일 경우 not in 쿼리를 실행하기위해 모든 프로젝트를 받아옴
		//프로젝트가 없는 사원일 경우  notIn 파라미터를 세션에 추가 및 파라미터 맵에 notIn 추가, 있는 사원일 경우 notIn 파라미터를 세션에서 제거
		if(projectList.size() == 0){
			projectList = workService.selectAllProjectList();
			session.setAttribute("notIn", "notIn");
			map.put("notIn", "notIn");
		}else{
			session.removeAttribute("notIn");
		}
		
		//list를 생성하여 project번호를 담기 
		List<Integer> prosqList = new ArrayList<>();
		for(ProjectVo project : projectList){
			prosqList.add(project.getPro_sq());
		}
		session.setAttribute("prosqList", prosqList);
		
		//map에 project 번호를 가진 list 담기
		map.put("projectList", prosqList);
		map.put("his_wk_sq", 0);
		
		//작업내역조회
		List<Map<String, Object>> workHistoryMapList = workService.selectHistoryMapList(map);
		
		//세션에 작업내역리스트 추가
		session.setAttribute("workHistoryMapList", workHistoryMapList);
	}
	
	/**
	 * Method : workPaging
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @param pageVo
	 * Method 설명 : 업무페이징
	 */
	public void workPaging(Map<String, Object> map, PageVo pageVo, HttpSession session){
		
		//세션에 기존페이지에 관한 정보 삭제
		session.removeAttribute("workMapList");
		session.removeAttribute("page");
		session.removeAttribute("pageSize");
		session.removeAttribute("lastPage");
		session.removeAttribute("lastPageStartPage");
		session.removeAttribute("startPage");
		session.removeAttribute("endPage");
		
		//세션에서 프로젝트 리스트 받아와서 프로젝트번호가 담긴 리스트를 map 담아서 조회(업무조회시 필요)
		//프로젝트가 없는 사원일경우 session에 notIn 파라미터가 담겨있기때문에 체크한후 null이 아니면 notIn을 파라미터 맵에 추가
		if(session.getAttribute("notIn") != null){
			map.put("notIn", "notIn");
		}
		map.put("projectList", session.getAttribute("prosqList"));
		
		//페이지 정보 받아오기
		int page = pageVo.getPage();
		int pageSize = pageVo.getPageSize();
		
		map.put("page", page);
		map.put("pageSize", pageSize);
		
		//업무페이징리스트 조회
		Map<String, Object> resultMap = null;
		List<Map<String, Object>> workMapPagingList = null;
		
		resultMap = workService.selectWorkPagingMap(map);
		workMapPagingList = (List<Map<String, Object>>) resultMap.get("workMapPagingList");
		
		int workCnt = (int) resultMap.get("workCnt");
		
		//페이지처리에 필요한 정보 구하기
		int lastPage = workCnt/pageSize + (workCnt%pageSize > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((page - 1) / 10) * 10 + 1; 
		int endPage = startPage + 10 - 1;
		
		//세션에 페이지에 관한 정보 추가
		session.setAttribute("workMapList", workMapPagingList);
		session.setAttribute("page", page);
		session.setAttribute("pageSize", pageSize);
		session.setAttribute("lastPage", lastPage);
		session.setAttribute("lastPageStartPage", lastPageStartPage);
		session.setAttribute("startPage", startPage);
		session.setAttribute("endPage", endPage);
		
		logger.debug("UtilNKO.workPaging -> workMapList : {}", workMapPagingList);
	}
	
	/**
	 * Method : dateSeek
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param model
	 * @throws Exception
	 * Method 설명 : 날짜구하기
	 */
	public void dateSeek(Model model){
		//오늘날짜
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String now = sdf.format(date);
		
		//20190410 -> 201904만 자르기
		String yearMonth = now.substring(0, 6);
		//20190410 -> 2019만 자르기
		String year = now.substring(0, 4);
		//20190410 -> 04만 자르기
		String month = now.substring(4, 6);
		
		//해당월의 첫번째날 세팅
		Calendar cal = Calendar.getInstance();
		Date firstDate = null;
		try {
			firstDate = sdf.parse(yearMonth + "01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(firstDate);
		
		//해당월의 마지막날 구하기
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = null;
		try {
			lastDate = sdf.parse(yearMonth + lastDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		logger.debug("UtilNKO.dateSeek -> date : {}", date);
		logger.debug("UtilNKO.dateSeek -> now : {}", now);
		logger.debug("UtilNKO.dateSeek -> yearMonth : {}", yearMonth);
		logger.debug("UtilNKO.dateSeek -> year : {}", year);
		logger.debug("UtilNKO.dateSeek -> month : {}", month);
		logger.debug("UtilNKO.dateSeek -> firstDate : {}", firstDate);
		logger.debug("UtilNKO.dateSeek -> lastDay : {}", lastDay);
		logger.debug("UtilNKO.dateSeek -> lastDate : {}", lastDate);
		
		try {
			model.addAttribute("nowDate", new SimpleDateFormat("yyyy-MM-dd").parse(year+"-"+month+"-"+now.substring(6, 8)));
			model.addAttribute("firstDate", new SimpleDateFormat("yyyy-MM-dd").parse(year+"-"+month+"-01"));
			model.addAttribute("lastDate", new SimpleDateFormat("yyyy-MM-dd").parse(year+"-"+month+"-"+lastDay));
			logger.debug("UtilNKO.dateSeek -> nowDate : {}", new SimpleDateFormat("yyyy-MM-dd").parse(year+"-"+month+"-"+now.substring(6, 8)));
			logger.debug("UtilNKO.dateSeek -> firstDate : {}", new SimpleDateFormat("yyyy-MM-dd").parse(year+"-"+month+"-01"));
			logger.debug("UtilNKO.dateSeek -> lastDate : {}", new SimpleDateFormat("yyyy-MM-dd").parse(year+"-"+month+"-"+lastDay));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		model.addAttribute("yearMonth", yearMonth);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("lastDay", lastDay);
	}
	
	/**
	 * Method : emailSend
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param httpSession
	 * @param email_to
	 * @param email_title
	 * @param email_content
	 * @return
	 * @throws Exception
	 * Method 설명 : 이메일 보내기(첨부파일 포함)
	 */
	public String emailSend(HttpSession httpSession, String email_to, String email_title, String email_content) throws Exception {
		//세션에서 접속한 사원의 정보 가져오기
		EmployeeVo employeeVo = (EmployeeVo) httpSession.getAttribute("employeeVo");
		
		//보내는 사람 email
		String host = "smtp.daum.net";
		final String user = employeeVo.getEmp_com_email();
		final String password = "test111111";
		
		//받는 사람 email
		String to = email_to;

		//daum은 ssl 사용여부 설정 해줘야함.
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.port", "465");
		
		//계정인증절차
		//첨부파일이 존재하는경우 getDefaultInstance로 사용하면 오류발생하는경우가 생기기 때문에 getInstance로 사용!!!
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		String send = "";
		//메일 정보
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			//제목
			message.setSubject(email_title);

			//밑으로는 첨부파일 관련
			//Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			//Now set the actual message
			messageBodyPart.setText(email_content);

			//Create a multipart message
			Multipart multipart = new MimeMultipart();

			//Set text message part
			multipart.addBodyPart(messageBodyPart);

			//해당 메일의 첨부파일 리스트 가져오기
			int eml_temp_sq = (int) httpSession.getAttribute("eml_temp_sq");
			List<EmlTempFileVo> emlTempFileList = emailService.selectTempFileList(eml_temp_sq);
			
			logger.debug("UtilNKO -> eml_temp_sq : {}", eml_temp_sq);
			logger.debug("UtilNKO -> emlTempFileList : {}", emlTempFileList);
			
			//첨부파일 추가
			if(emlTempFileList.size() > 0){
				for(int i=0; i<emlTempFileList.size(); i++){
					String path = emlTempFileList.get(i).getEml_temp_fl_path();
					String name = emlTempFileList.get(i).getEml_temp_fl_nm();
					
					//Part two is attachment
					messageBodyPart = new MimeBodyPart();
					String filename = path; //파일경로가 포함된 이름
					DataSource source = new FileDataSource(filename);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(MimeUtility.encodeText(name, "euc-kr", "B")); //첨부파일 보낼때 한글설정
					multipart.addBodyPart(messageBodyPart);
				}
			}

			//Send the complete message parts
			message.setContent(multipart);
			
			//전송
			Transport.send(message);
			
			send = "success";
		} catch (MessagingException e) {
			e.printStackTrace();
			send = "fail";
		}
		
		return send;
	}
	
	/**
	 * Method : getFileName
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param source
	 * @return
	 * @throws Base64DecodingException
	 * @throws UnsupportedEncodingException
	 * Method 설명 : 이메일 받아올때 한글 깨지는 현상 해결 코드
	 */
	public static String getFileName(String source) throws Base64DecodingException, UnsupportedEncodingException {
		//1. 찾아낼 패턴 지정 
		String pattern = "=\\?(.*?)\\?(.*?)\\?(.*?)\\?=";
		StringBuilder buffer = new StringBuilder();
		//2. default charset 지정
		String charsetMain = "UTF-8";
		String charsetSub = "B";
		Pattern r = Pattern.compile(pattern);
		Matcher matcher = r.matcher(source);
		//3. 내용 찾아서 decoding
		while (matcher.find()) {
			charsetMain = matcher.group(1);
			charsetSub = matcher.group(2);
			buffer.append(new String(Base64.decode(matcher.group(3)), charsetMain));
		}
		//4. decoding할 게 없다면 그대로 반환
		if(buffer.toString().isEmpty()){
			buffer.append(source);
		}
		return buffer.toString();
	}
	
	/**
	 * Method : emailReceivePop
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param email
	 * @param pass
	 * Method 설명 : pop방식으로 이메일을 받아오는데 한번읽어오면 휴지통으로 보내고 보내는사람, 받는사람 조건을 설정
	 */
	public void emailReceivePop(String email, String pass){
		String host = "pop.daum.net";
		String mailStoreType = "pop3s";
		String userEmail = email;
		String password = "test111111";
		
		logger.debug("*****************UtilNKO.emailReceivePop******************");
		try {
			//create properties field
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "pop3");
			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			properties.put("mail.pop3.ssl.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);
			//emailSession.setDebug(true); --> 디버깅 정보를 화면에 보여줌

			//create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore(mailStoreType);
			store.connect(host, userEmail, password);

			//create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
//			emailFolder.open(Folder.READ_ONLY);
			emailFolder.open(Folder.READ_WRITE); //Folder.READ_WRITE인 경우 close 옵션을 true 설정시 한번 읽은 이메일은 휴지통으로 보내짐

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			//retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.getMessages();
			logger.debug("messages.length : {}", messages.length);
			
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				logger.debug("*************************************************");
				Address[] a;
				
				//FROM
				//보내는사람이 @nkby.shop으로된 메일주소가 아닌것만
				boolean fromFlag = false;
				if ((a = message.getFrom()) != null) {
					for (int j = 0; j < a.length; j++){
						if(!a[j].toString().endsWith("@nkby.shop") && !a[j].toString().endsWith("@nkby.shop>")){
							fromFlag = true;
						}
					}
				}
				
				//TO
				//받는사람이 @nkby.shop으로 된 자기주소가 포함된경우만
				boolean toFlag = false;
				if ((a = message.getRecipients(Message.RecipientType.TO)) != null) {
					for (int j = 0; j < a.length; j++){
						if(a[j].toString().endsWith(userEmail) || a[j].toString().endsWith("<"+userEmail+">")){
							toFlag = true;
						}
					}
				}
				
				if(fromFlag && toFlag){
					writePart(message, userEmail, new EmlReceiveVo());
					message.setFlag(Flags.Flag.DELETED, true); //메세지 한번읽으면 휴지통으로 보내게 설정
				}
			}

			//close the store and folder objects
			emailFolder.close(true); //true 설정시 Folder.READ_WRITE인 경우 한번 읽은 이메일은 휴지통으로 보내짐
			store.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writePart(Part p, String userEmail, EmlReceiveVo emlReceiveVo) throws Exception {
		if (p instanceof Message){
			Message m = (Message) p;
			
			//Call methos writeEnvelope
			logger.debug("UtilNKO.writePart -> This is the message envelope");
			logger.debug("*************************************************");
			Address[] a;
			
			//외부에서 보낸 이메일에 받는사람이메일을 가지고 사원번호 검색하여 세팅
			EmployeeVo employeeVo = new EmployeeVo();
			employeeVo.setEmp_com_email(userEmail);
			List<EmployeeVo> employeeList = employeeService.selectListSearch(employeeVo);
			
			if(employeeList.size() > 0){
				int emp_rec_sq = employeeService.selectListSearch(employeeVo).get(0).getEmp_sq();
				emlReceiveVo.setEml_emp_rec_sq(emp_rec_sq);
			}
			
			//외부에서 보낸메일 기본정보 세팅
			emlReceiveVo.setEml_rec_dt(m.getReceivedDate());
			emlReceiveVo.setEml_rec_chk("N");
			emlReceiveVo.setEml_rec_con("");
			emlReceiveVo.setEml_rec_ipt("N");
			
			//외부에서 보낸메일주소 세팅
			String fromEmail = m.getFrom()[0].toString();
			logger.debug("fromEmail : {}", fromEmail);
			logger.debug("fromEmail : {}", fromEmail.substring(fromEmail.lastIndexOf("<")+1, fromEmail.lastIndexOf(">")));
			emlReceiveVo.setEml_send_email(fromEmail.substring(fromEmail.lastIndexOf("<")+1, fromEmail.lastIndexOf(">")));
			
			//외부에서 보낸메일 제목 세팅
			//SUBJECT
			if (m.getSubject() != null){
				logger.debug("SUBJECT: {}", m.getSubject());
				emlReceiveVo.setEml_rec_nm(m.getSubject());
			}else{
				emlReceiveVo.setEml_rec_nm("");
			}
			
			//FROM
			if ((a = m.getFrom()) != null) {
				for (int j = 0; j < a.length; j++){
					logger.debug("FROM : {}", a[j].toString());
				}
			}
			//TO
			if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
				for (int j = 0; j < a.length; j++){
					logger.debug("TO : {}", a[j].toString());
				}
			}
			
			logger.debug("emlReceiveVo : {}", emlReceiveVo);
			int insertReceive = emailService.insertReceive(emlReceiveVo);
			logger.debug("insertReceive : {}", insertReceive);
		}
		
		logger.debug("FILENAME: {}" + p.getFileName());
		if(p.getFileName() != null){
			logger.debug("파일이름이 존재하는경우 정상 FILENAME: {}" + getFileName(p.getFileName()));
		}
		logger.debug("CONTENT-TYPE: {}" + p.getContentType());
		logger.debug("*************************************************");

		//check if the content is plain text
		if (p.isMimeType("text/plain") && (p.getFileName() == null)) { //파일이름이 없을때만 기본 text임
			logger.debug("This is plain text");
			logger.debug("*************************************************");
			logger.debug("{}", (String) p.getContent());
			
			//db에 content에 저장할 내용
			if(p.getContent() != null){
				emlReceiveVo.setEml_rec_con((String) p.getContent());
			}else{
				emlReceiveVo.setEml_rec_con("");
			}
			
			int updateReceive = emailService.updateReceive(emlReceiveVo);
			logger.debug("updateReceive : {}", updateReceive);
		}
		//타입은 text/plain인데 파일이름이 존재하는경우는 txt파일임
		else if(p.isMimeType("text/plain") && (p.getFileName() != null)){
			logger.debug("This is text file");
			logger.debug("*************************************************");
			
			//입출력의 성능 향상을 위해서 버퍼를 이용하는 보조 스트림
			FileOutputStream fout = null;
			BufferedOutputStream bout = null;
			
			try {
//				String path = "\\\\Sem-pc\\공유폴더\\최종프로젝트\\1조\\images\\"+UUID.randomUUID().toString();
				String path = "d:\\picture\\recMail\\" + UUID.randomUUID().toString();
				fout = new FileOutputStream(path);
				
				//버퍼의 크기를 지정하지 않으면 기본적으로 버퍼의 크기가
				//8192 byte(8Kb)로 설정된다.
				
				//버퍼의 크기가 5인 스트림 생성
				bout = new BufferedOutputStream(fout, 5);
				
				//파일내용을 잘라서 내보냄
				for(int i=0; i<p.getContent().toString().length(); i++) {
					bout.write(p.getContent().toString().charAt(i));
				}
				
				bout.flush(); //작업을 종료하기 전에 남아있는 데이터를 모두 출력시킨다.
				bout.close();
				
				//파일이름과 경로를 db에 저장
				EmlFileVo emlFileVo = new EmlFileVo();
				emlFileVo.setEml_rec_sq(emlReceiveVo.getEml_rec_sq());
				emlFileVo.setEml_fl_nm(getFileName(p.getFileName()));
				emlFileVo.setEml_fl_path(path);
				int insertFile = emailService.insertFile(emlFileVo);
				logger.debug("emlFileVo : {}", emlFileVo);
				logger.debug("insertFile : {}", insertFile);
				
				logger.debug("text file 작업 끝...");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		//check if the content has attachment
		else if (p.isMimeType("multipart/*")) {
			logger.debug("This is a Multipart");
			logger.debug("*************************************************");
			Multipart mp = (Multipart) p.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++){
				writePart(mp.getBodyPart(i), userEmail, emlReceiveVo);
			}
		}
		//check if the content is a nested message
		else if (p.isMimeType("message/rfc822")) {
			logger.debug("This is a Nested Message");
			logger.debug("*************************************************");
			writePart((Part) p.getContent(), userEmail, emlReceiveVo);
		}
		//check if the content is an inline image
		//***java에서 외부로 보낸 메일의 첨부파일 형식은 application/octet-stream 형식임!!(중요)
		else if (p.isMimeType("image/jpeg") || p.isMimeType("image/jpg") || p.isMimeType("image/png")
				|| p.isMimeType("application/octet-stream")) {
			logger.debug("-----> image/jpeg, image/jpg, image/png or application-occtext-stream");
			Object o = p.getContent();

			InputStream x = (InputStream) o;
			// Construct the required byte array
			System.out.println("x.length = " + x.available());
			int i = 0;
			byte[] bArray = new byte[x.available()];
			while ((i = (int) ((InputStream) x).available()) > 0) {
				int result = (int) (((InputStream) x).read(bArray));
				if (result == -1){
					break;
				}
			}
			
			//파일이름에 한글이 포함되었을시 정상적으로 변환하여 생성
//			String path = "\\\\Sem-pc\\공유폴더\\최종프로젝트\\1조\\images\\"+UUID.randomUUID().toString();
			String path = "d:\\picture\\recMail\\" + UUID.randomUUID().toString();
			FileOutputStream f2 = new FileOutputStream(path);
			
			f2.write(bArray);
			
			//파일이름과 경로를 db에 저장
			EmlFileVo emlFileVo = new EmlFileVo();
			emlFileVo.setEml_rec_sq(emlReceiveVo.getEml_rec_sq());
			emlFileVo.setEml_fl_nm(getFileName(p.getFileName()));
			emlFileVo.setEml_fl_path(path);
			int insertFile = emailService.insertFile(emlFileVo);
			logger.debug("emlFileVo : {}", emlFileVo);
			logger.debug("insertFile : {}", insertFile);
			
		}
		/*//image이면 해당 프로젝트에 저장하는 로직
		else if (p.getContentType().contains("image/")) {
			System.out.println("content type" + p.getContentType());
			File f = new File("image" + new Date().getTime() + ".jpg");
			DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			com.sun.mail.util.BASE64DecoderStream test = (com.sun.mail.util.BASE64DecoderStream) p.getContent();
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = test.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
		}
		*/
		else {
			Object o = p.getContent();
			if (o instanceof String) {
				logger.debug("This is a string");
				logger.debug("*************************************************");
				logger.debug((String) o);
				
				//html형식을 db에 저장
				emlReceiveVo.setEml_rec_con((String)o);
				int updateReceive = emailService.updateReceive(emlReceiveVo);
				logger.debug("updateReceive : {}", updateReceive);
				
			} else if (o instanceof InputStream) {
				logger.debug("This is just an input stream");
				logger.debug("*************************************************");
				InputStream is = (InputStream) o;
				is = (InputStream) o;
				int c;
				while ((c = is.read()) != -1){
					System.out.write(c);
				}
			} else {
				logger.debug("This is an unknown type");
				logger.debug("*************************************************");
				logger.debug(o.toString());
			}
		}

	}
	
	
	/**
	 * Method : callingcardPaging
	 * 작성자 : pc23
	 * 변경이력 :
	 * @param type
	 * @param model
	 * @param pageVo
	 * @param callingCardVo
	 * Method 설명 : 명함 관리에서 페이징 처리
	 */
	public void callingcardPaging(String type, Model model, PageVo pageVo, CallingCardVo callingCardVo, HttpSession session){
		//페이지 정보 받아오기
		int page = pageVo.getPage();
		int pageSize = pageVo.getPageSize();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		
		//사원페이징리스트 조회
		Map<String, Object> resultMap = null;
		List<CallingCardVo> callingcardPagingList = null;
		
		map.put("authority",session.getAttribute("authority"));
		map.put("emp_sq",((EmployeeVo)session.getAttribute("employeeVo")).getEmp_sq());
		
		//타입이 1이면 일반List 요청, 타입이 2이면 callingcardSelect요청
		int cardCnt = 0;
		if(type.equals("1")){
			resultMap = callingService.selectCallingCardPaging_Search(map, callingCardVo);
			callingcardPagingList = (List<CallingCardVo>) resultMap.get("cardpaginglist");
			
			cardCnt = (int) resultMap.get("cardCnt");
		}else if(type.equals("2")){
			map.put("cal_nm", callingCardVo.getCal_nm());
			map.put("cal_com", callingCardVo.getCal_com());
			map.put("cal_grade", callingCardVo.getCal_grade());
			
			resultMap = callingService.selectCallingCardPaging_Search(map, callingCardVo);
			callingcardPagingList = (List<CallingCardVo>) resultMap.get("cardpaginglist");
			
			cardCnt = (int) resultMap.get("SearchcardCnt");
		}
		
		//페이지처리에 필요한 정보 구하기
		int lastPage = cardCnt/pageSize + (cardCnt%pageSize > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((page - 1) / 10) * 10 + 1; 
		int endPage = startPage + 10 - 1;
		
		//모델에 페이지에 관한 정보 추가
		model.addAttribute("cardpaginglist", callingcardPagingList);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
	}
	
	
	/**
	 * Method : certificatePaging
	 * 작성자 : pc23
	 * 변경이력 :
	 * @param type
	 * @param model
	 * @param pageVo
	 * @param certificateVo
	 * Method 설명 : 증명서 관리에서 리스트 페이징
	 */
	public void certificatePaging(String type, Model model, PageVo pageVo, CertificateVo certificateVo, HttpSession session){
		//페이지 정보 받아오기
		int page = pageVo.getPage();
		int pageSize = pageVo.getPageSize();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		
		//사원페이징리스트 조회
		Map<String, Object> resultMap = null;
		List<CertificateVo> certificatePagingList = null;
		
		map.put("authority",session.getAttribute("authority"));
		map.put("emp_sq",((EmployeeVo)session.getAttribute("employeeVo")).getEmp_sq());
		
		//타입이 1이면 일반List 요청, 타입이 2이면 callingcardSelect요청
		int CrtCnt = 0;
		if(type.equals("1")){
			resultMap = certificateService.selectCertificatePaging_Search(map, certificateVo);
			certificatePagingList = (List<CertificateVo>) resultMap.get("CrtPagingList");
			
			CrtCnt = (int) resultMap.get("CrtCnt");
		}else if(type.equals("2")){
			map.put("crt_div_sq", certificateVo.getCrt_div_sq());
			map.put("crt_whether", certificateVo.getCrt_whether());
			
			resultMap = certificateService.selectCertificatePaging_Search(map, certificateVo);
			certificatePagingList = (List<CertificateVo>) resultMap.get("CrtPagingList");
			
			CrtCnt = (int) resultMap.get("SearchCrtCnt");
		}
		
		//페이지처리에 필요한 정보 구하기
		int lastPage = CrtCnt/pageSize + (CrtCnt%pageSize > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((page - 1) / 10) * 10 + 1; 
		int endPage = startPage + 10 - 1;
		
		//모델에 페이지에 관한 정보 추가
		model.addAttribute("crtPagingList", certificatePagingList);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

	}
	
}