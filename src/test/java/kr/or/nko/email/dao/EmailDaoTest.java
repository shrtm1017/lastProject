package kr.or.nko.email.dao;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
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
import javax.mail.search.FlagTerm;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import kr.or.nko.email.service.IEmailService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.testconfig.LogicTestConfig;

public class EmailDaoTest extends LogicTestConfig{
	
	private Logger logger = LoggerFactory.getLogger(EmailDaoTest.class);
	
	@Resource(name="emailService")
	private IEmailService emailService;
	
	/**
	 * Method : strSplit
	 * 작성자 : pc11
	 * 변경이력 :
	 * Method 설명 : 이메일 형식 구분하기 테스트
	 */
	@Test
	public void strSplit(){
		String email = "sksvkdlxj77@nkby.shop>";
		boolean flag = email.endsWith("nkby.shop") || email.endsWith("nkby.shop>");
		
		logger.debug("진실 혹은 거짓 : {}", flag);
		
		String email2 = "<바다빛 <sksvkdlxj77@nkby.shop>";
		int s = email2.lastIndexOf("<");
		int s2 = email2.lastIndexOf(">");
		
		logger.debug("< 시작인덱스 : {}", s);
		logger.debug("> 시작인덱스 : {}", s2);
		logger.debug("이메일 : {}", email2.substring(s+1, s2));
		
		// \를 replaceAll 해야할 경우 \\\\로 써줘야 인식함(\\가 \를 의미하기때문에)
		String str1 = "\"asdasd\"";
		String str2 = "\\asdasd\\";
		String str3 = "\\\"asdasd\\\"";
		logger.debug("{}", str1);
		logger.debug("{}", str1.replace('\"', '\\'));
		logger.debug("{}", str1.replaceAll("\"", "\\\\\""));
		logger.debug("{}", str2);
		logger.debug("{}", str2.replace('\\', '\"'));
		logger.debug("{}", str2.replaceAll("\\\\", "ss"));
		logger.debug("{}", str3);
	}

	/**
	 * Method : testEmailReceiveImap
	 * 작성자 : pc11
	 * 변경이력 :
	 * @throws MessagingException
	 * Method 설명 : imap방식으로 이메일 받기 테스트
	 */
	@Test
	public void testEmailReceiveImap() throws MessagingException {
		//읽어올 email
		String host = "imap.daum.net";
		final String user = "sksvkdlxj77@nkby.shop";
		final String password = "test111111";

		//daum은 ssl 사용여부 설정 해줘야함.
		Properties props = new Properties();
		props.put("mail.imap.host", host);
		props.put("mail.imap.auth", "true");
		props.put("mail.imap.ssl.enable", "true");
		props.put("mail.imap.port", "993");
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		//메일서버 연결
		Store store = session.getStore("imap");
		store.connect();
		
		//폴더명 출력
		for(int i=0; i<store.getDefaultFolder().list().length; i++){
			logger.debug("{}", store.getDefaultFolder().list()[i].getName());
		}

		//수신함은 INBOX 한폴더안에 담겨있음
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);

		Message[] messages = folder.getMessages();

		Address[] a;
		
		//해당 메세지의 정보 출력
		for (Message message : messages) {
			logger.debug(":::::::::::::::::::::::::::::::::::");
			logger.debug("제목 : {}", message.getSubject());
			logger.debug("폴더 : {}", message.getFolder());
			logger.debug("보낸사람 : {}", message.getFrom()[0]);
			if((a = message.getRecipients(Message.RecipientType.TO)) != null) {
				for(int j=0; j<a.length; j++){
					logger.debug("받는사람 : {}", a[j].toString());
				}
			}
		}

		store.close();
	}
	
	/**
	 * Method : testEmailReceivePop
	 * 작성자 : pc11
	 * 변경이력 :
	 * @throws MessagingException
	 * Method 설명 : pop방식으로 이메일 받기 테스트
	 */
	@Test
	public void testEmailReceivePop() throws MessagingException {
		//읽어올 email
		String host = "pop.daum.net";
		final String user = "sksvkdlxj77@nkby.shop";
		final String password = "test111111";

		//daum은 ssl 사용여부 설정 해줘야함.
		Properties props = new Properties();
		props.put("mail.pop3.host", host);
		props.put("mail.pop3.auth", "true");
		props.put("mail.pop3.ssl.enable", "true");
		props.put("mail.pop3.port", "995");
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		//메일서버 연결
		Store store = session.getStore("pop3");
		store.connect();
		
		//폴더명 출력
		for(int i=0; i<store.getDefaultFolder().list().length; i++){
			logger.debug("{}", store.getDefaultFolder().list()[i]);
		}

		//수신함은 INBOX 한폴더안에 담겨있음
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);

		Message[] messages = folder.getMessages();

		//해당 메세지의 정보 출력
		for (Message message : messages) {
			logger.debug(":::::::::::::::::::::::::::::::::::");
			logger.debug("제목 : {}", message.getSubject());
			logger.debug("폴더 : {}", message.getFolder());
			logger.debug("보낸사람 : {}", message.getFrom()[0]);
		}

		store.close();
	}
	
	/**
	 * Method : testEmailSend
	 * 작성자 : pc11
	 * 변경이력 :
	 * Method 설명 : 첨부파일 제외 이메일 보내기 테스트
	 */
	@Test
	public void testEmailSend() {
		String error = "";
		
		//보내는 사람 email
		String host = "smtp.daum.net";
		final String user = "sksvkdlxj77@nkby.shop";
		final String password = "test111111";
		
		//받는 사람 email
		String to = "SKSVKDLXJ77@NKBY.SHOP";

		//daum은 ssl 사용여부 설정 해줘야함.
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
		
		//메일 정보
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sksvkdlxj77@nkby.shop")); //여기서 보내는주소 세팅(테스트해야함)
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			//제목
			message.setSubject("[NKOffice] 임시 비밀번호 발송");

			//내용
			message.setText("[임시 비밀번호 : "+ SecurityCode[0] +" ]");
			
			//전송
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
			error = "error";
		}
		
		error = SecurityCode[0];
	}
	
	/**
	 * Method : emailSendAttach
	 * 작성자 : pc11
	 * 변경이력 :
	 * @throws UnsupportedEncodingException
	 * Method 설명 : 첨부파일보내기 테스트
	 */
	@Test
	public void emailSendAttach() throws UnsupportedEncodingException{
		//보내는 사람 email
		String host = "smtp.daum.net";
		final String user = "sksvkdlxj77@nkby.shop";
		final String password = "test111111";
		
		//받는 사람 email
		String to = "sksvkdlxj77@nkby.shop";

		//daum은 ssl 사용여부 설정 해줘야함.
		Properties props = new Properties();
		props.put("mail.pop.host", host);
		props.put("mail.pop.auth", "true");
		props.put("mail.pop.ssl.enable", "true");
		props.put("mail.pop.port", "465");
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		String[] SecurityCode = UUID.randomUUID().toString().split("-");
		String error = "";
		//메일 정보
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			//제목
			message.setSubject("[NKOffice] 임시 비밀번호 발송");
			
			//밑으로는 첨부파일 관련
			//Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			//Now set the actual message
			messageBodyPart.setText("[임시 비밀번호 : "+ SecurityCode[0] +" ]");

			//Create a multipart message
			Multipart multipart = new MimeMultipart();

			//Set text message part
			multipart.addBodyPart(messageBodyPart);

			//Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = "D:\\레인저스사진\\김보검.jpg"; //파일경로
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(MimeUtility.encodeText("김보검", "euc-kr", "B")); //첨부파일 보낼때 한글설정
			multipart.addBodyPart(messageBodyPart);
			
			messageBodyPart = new MimeBodyPart();
			String filename2 = "D:\\레인저스사진\\더터비.jpg"; //파일경로
			DataSource source2 = new FileDataSource(filename2);
			messageBodyPart.setDataHandler(new DataHandler(source2));
			messageBodyPart.setFileName(MimeUtility.encodeText("김보검2", "euc-kr", "B")); //첨부파일 보낼때 한글설정
			multipart.addBodyPart(messageBodyPart);

			//Send the complete message parts
			message.setContent(multipart);
			
			//전송
			Transport.send(message);
			
			error = SecurityCode[0];
		} catch (MessagingException e) {
			e.printStackTrace();
			error = "error";
		}
		
		logger.debug("error : {}", error);
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
	 * Method : testPopReceiveMail
	 * 작성자 : pc11
	 * 변경이력 :
	 * Method 설명 : pop방식으로 첨부파일 포함 외부메일을 받아오기
	 */
	@Test
	public void testPopReceiveMail() {
		String host = "pop.daum.net";
		String mailStoreType = "pop3";
		String user = "sksvkdlxj77@nkby.shop";
		String password = "test111111";

		//Call method fetch
		fetch(host, mailStoreType, user, password);
	}
	
	public static void fetch(String pop3Host, String storeType, String user, String password) {
		try {
			//create properties field
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "pop3");
			properties.put("mail.pop3.host", pop3Host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			//properties.put("mail.pop3.ssl.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);
			//emailSession.setDebug(true); --> 디버깅 정보를 화면에 보여줌

			//create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("pop3s");
			store.connect(pop3Host, user, password);

			//create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			//retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.getMessages();
			System.out.println("messages.length---" + messages.length);
			
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				System.out.println("---------------------------------");
				writePart(message);
			}

			//close the store and folder objects
			emailFolder.close(false);
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

	/*
	 * This method checks for content-type based on which, it processes and
	 * fetches the content of the message
	 */
	public static void writePart(Part p) throws Exception {
		if (p instanceof Message){
			//Call methos writeEnvelope
			writeEnvelope((Message) p);
		}

		System.out.println("----------------------------");
		//테스트중
		System.out.println("FILENAME: " + p.getFileName());
		if(p.getFileName() != null){
			System.out.println("FILENAME2: " + getFileName(p.getFileName()));
		}
		System.out.println("CONTENT-TYPE: " + p.getContentType());

		//check if the content is plain text
		if (p.isMimeType("text/plain")) {
			System.out.println("This is plain text");
			System.out.println("---------------------------");
			System.out.println((String) p.getContent());
		}
		//타입은 text/plain인데 파일이름이 존재하는경우는 txt파일임
		else if(p.isMimeType("text/plain") && (p.getFileName() != null)){
			System.out.println("This is text file");
			System.out.println("---------------------------");
			
			//입출력의 성능 향상을 위해서 버퍼를 이용하는 보조 스트림
			FileOutputStream fout = null;
			BufferedOutputStream bout = null;
			
			try {
				fout = new FileOutputStream("d:\\temp\\" + UUID.randomUUID().toString() + getFileName(p.getFileName()));
				
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
				System.out.println("작업 끝...");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		//check if the content has attachment
		else if (p.isMimeType("multipart/*")) {
			System.out.println("This is a Multipart");
			System.out.println("---------------------------");
			Multipart mp = (Multipart) p.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++){
				writePart(mp.getBodyPart(i));
			}
		}
		//check if the content is a nested message
		else if (p.isMimeType("message/rfc822")) {
			System.out.println("This is a Nested Message");
			System.out.println("---------------------------");
			writePart((Part) p.getContent());
		}
		//check if the content is an inline image
		//***java에서 외부로 보낸 메일의 첨부파일 형식은 application/octet-stream 형식임!!(중요)
		else if (p.isMimeType("image/jpeg") || p.isMimeType("image/jpg") || p.isMimeType("image/png") || p.isMimeType("application/octet-stream")) {
			System.out.println("--------> image/jpeg");
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
			//FileOutputStream f2 = new FileOutputStream("d:\\temp\\"+UUID.randomUUID().toString()+".jpg");
			//파일이름에 한글이 포함되었을시 정상적으로 변환하여 생성
			FileOutputStream f2 = new FileOutputStream("d:\\temp\\"
									+UUID.randomUUID().toString()+""+getFileName(p.getFileName()));
			f2.write(bArray);
		} else if (p.getContentType().contains("image/")) {
			System.out.println("content type" + p.getContentType());
			File f = new File("image" + new Date().getTime() + ".jpg");
			DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			com.sun.mail.util.BASE64DecoderStream test = (com.sun.mail.util.BASE64DecoderStream) p.getContent();
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = test.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
		} else {
			Object o = p.getContent();
			if (o instanceof String) {
				System.out.println("This is a string");
				System.out.println("---------------------------");
				System.out.println((String) o);
			} else if (o instanceof InputStream) {
				System.out.println("This is just an input stream");
				System.out.println("---------------------------");
				InputStream is = (InputStream) o;
				is = (InputStream) o;
				int c;
				while ((c = is.read()) != -1){
					System.out.write(c);
				}
			} else {
				System.out.println("This is an unknown type");
				System.out.println("---------------------------");
				System.out.println(o.toString());
			}
		}

	}

	/*
	 * This method would print FROM,TO and SUBJECT of the message
	 */
	public static void writeEnvelope(Message m) throws Exception {
		System.out.println("This is the message envelope");
		System.out.println("---------------------------");
		Address[] a;

		//FROM
		if ((a = m.getFrom()) != null) {
			for (int j = 0; j < a.length; j++){
				System.out.println("FROM: " + a[j].toString());
			}
		}
		//TO
		if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
			for (int j = 0; j < a.length; j++){
				System.out.println("TO: " + a[j].toString());
			}
		}
		//SUBJECT
		if (m.getSubject() != null){
			System.out.println("SUBJECT: " + m.getSubject());
		}
	}
	
	/**
	 * Method : testPopMail
	 * 작성자 : pc11
	 * 변경이력 :
	 * Method 설명 : 한번읽어오면 삭제여부 조건 설정과 보내는사람, 받는사람 조건 추가
	 */
	@Test
	public void testPopMail(){
		String host = "pop.daum.net";
		String mailStoreType = "pop3s";
		String user = "sksvkdlxj77@nkby.shop";
		String password = "test111111";
		
		try {
			//create properties field
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "pop3");
			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			//properties.put("mail.pop3.ssl.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);
			//emailSession.setDebug(true); --> 디버깅 정보를 화면에 보여줌

			//create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore(mailStoreType);
			store.connect(host, user, password);

			//create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
//			emailFolder.open(Folder.READ_ONLY);
			emailFolder.open(Folder.READ_WRITE); //Folder.READ_WRITE인 경우 close 옵션을 true 설정시 한번 읽은 이메일은 휴지통으로 보내짐

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			//retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.getMessages();
			System.out.println("messages.length---" + messages.length);
			
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				System.out.println("---------------------------------");
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
						if(a[j].toString().endsWith("sksvkdlxj77@nkby.shop") || a[j].toString().endsWith("<sksvkdlxj77@nkby.shop>")){
							toFlag = true;
						}
					}
				}
				
				if(fromFlag && toFlag){
					writePart2(message);
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
	
	public static void writePart2(Part p) throws Exception {
		if (p instanceof Message){
			Message m = (Message) p;
			
			//Call methos writeEnvelope
			System.out.println("This is the message envelope");
			System.out.println("---------------------------");
			Address[] a;

			//FROM
			if ((a = m.getFrom()) != null) {
				for (int j = 0; j < a.length; j++){
					System.out.println("FROM: " + a[j].toString());
				}
			}
			//TO
			if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
				for (int j = 0; j < a.length; j++){
					System.out.println("TO: " + a[j].toString());
				}
			}
			//SUBJECT
			if (m.getSubject() != null){
				System.out.println("SUBJECT: " + m.getSubject());
			}
		}

		System.out.println("----------------------------");
		//테스트중
		System.out.println("FILENAME: " + p.getFileName());
		if(p.getFileName() != null){
			System.out.println("FILENAME2: " + getFileName(p.getFileName()));
		}
		System.out.println("CONTENT-TYPE: " + p.getContentType());

		//check if the content is plain text
		if (p.isMimeType("text/plain") && (p.getFileName() == null)) { //파일이름이 없을때만 기본 text임
			System.out.println("This is plain text");
			System.out.println("---------------------------");
			System.out.println((String) p.getContent());
		}
		//타입은 text/plain인데 파일이름이 존재하는경우는 txt파일임
		else if(p.isMimeType("text/plain") && (p.getFileName() != null)){
			System.out.println("This is text file");
			System.out.println("---------------------------");
			
			//입출력의 성능 향상을 위해서 버퍼를 이용하는 보조 스트림
			FileOutputStream fout = null;
			BufferedOutputStream bout = null;
			
			try {
				fout = new FileOutputStream("d:\\temp\\" + UUID.randomUUID().toString() + getFileName(p.getFileName()));
				
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
				System.out.println("작업 끝...");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		//check if the content has attachment
		else if (p.isMimeType("multipart/*")) {
			System.out.println("This is a Multipart");
			System.out.println("---------------------------");
			Multipart mp = (Multipart) p.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++){
				writePart2(mp.getBodyPart(i));
			}
		}
		//check if the content is a nested message
		else if (p.isMimeType("message/rfc822")) {
			System.out.println("This is a Nested Message");
			System.out.println("---------------------------");
			writePart2((Part) p.getContent());
		}
		//check if the content is an inline image
		//***java에서 외부로 보낸 메일의 첨부파일 형식은 application/octet-stream 형식임!!(중요)
		else if (p.isMimeType("image/jpeg") || p.isMimeType("image/jpg") || p.isMimeType("image/png")
				|| p.isMimeType("application/octet-stream")) {
			System.out.println("--------> image/jpeg, image/jpg, image/png or application-occtext-stream");
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
			//FileOutputStream f2 = new FileOutputStream("d:\\temp\\"+UUID.randomUUID().toString()+".jpg");
			//파일이름에 한글이 포함되었을시 정상적으로 변환하여 생성
			FileOutputStream f2 = new FileOutputStream("d:\\temp\\"
									+UUID.randomUUID().toString()+""+getFileName(p.getFileName()));
			f2.write(bArray);
		} else if (p.getContentType().contains("image/")) {
			System.out.println("content type" + p.getContentType());
			File f = new File("image" + new Date().getTime() + ".jpg");
			DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			com.sun.mail.util.BASE64DecoderStream test = (com.sun.mail.util.BASE64DecoderStream) p.getContent();
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = test.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
		} else {
			Object o = p.getContent();
			if (o instanceof String) {
				System.out.println("This is a string");
				System.out.println("---------------------------");
				System.out.println((String) o);
			} else if (o instanceof InputStream) {
				System.out.println("This is just an input stream");
				System.out.println("---------------------------");
				InputStream is = (InputStream) o;
				is = (InputStream) o;
				int c;
				while ((c = is.read()) != -1){
					System.out.write(c);
				}
			} else {
				System.out.println("This is an unknown type");
				System.out.println("---------------------------");
				System.out.println(o.toString());
			}
		}

	}

}