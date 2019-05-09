package kr.or.nko.login;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.message.model.MessageReceiveVo;
import kr.or.nko.message.service.IMessageService;

@Repository
public class WebSocketHandler extends TextWebSocketHandler {
	
	List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	
	@Resource(name="messageService")
	private IMessageService	messageService;

	private Logger logger = LoggerFactory.getLogger(WebSocketHandler.class); 
	 
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//웹 소켓 연결이 종료되고 나서 서버단에서 실행해야할 일들을 정의해주는 메소드
		logger.debug("WebSocket 연결 종료!");
		sessions.remove(session);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//연결이 성사 되고 나서 해야할 일들
		logger.debug("WebSocket 연결 성공!");
		
		sessions.add(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//웹소켓 서버단으로 메세지가 도착했을때 해주어야할 일들을 정의하는 메소드
		logger.debug("WebSocket 메시지 전달!");
		
		EmployeeVo evo = (EmployeeVo) session.getAttributes().get("employeeVo");
		// 메시지의 갯수를 측정
		
		logger.debug(message.getPayload());
		logger.debug("session.getId()={}",session.getId());
		
		for(WebSocketSession sess : sessions){
			if(!sess.getId().equals(session.getId())){ // 내가 아닐때 (1번째는 무조건 내가 list에 내가 들어가니까)
				
				logger.debug("session.getId()={}",session.getId());
				logger.debug("2");
				logger.debug("message.getPayload()={}",message.getPayload());
				logger.debug("sess.getId()={}",sess.getId());
				
				// , 없애는거 처리하기 
				String res = message.getPayload();
				String arr[] = res.split(", ");
				
				// jsp에서 보낸 아이이를 받을때 사용 : message.getPayload()
				EmployeeVo evoSess = (EmployeeVo) sess.getAttributes().get("employeeVo");
				
				List<MessageReceiveVo> rvo = messageService.selectRec(Integer.toString(evoSess.getEmp_sq()));
				
				int result;
				if(rvo.size()==0){
					result=0;
				}else{
					result = rvo.size();
				}
				
				sess.sendMessage(new TextMessage(Integer.toString(result))); 
			}else{
				// 나와 동일한 세션아이디일 경우 (내 화면에서 몇개의 메시지가 왔는지 확인하기 위한 과정)
				List<MessageReceiveVo> rvo = messageService.selectRec((Integer.toString(evo.getEmp_sq())));
				
				logger.debug(message.getPayload());
				logger.debug("message.getPayload()={}",message.getPayload());
				
				int result;
				if(rvo.size()==0){
					result=0;
				}else{
					result = rvo.size();
				}
				
				sess.sendMessage(new TextMessage(Integer.toString(result))); 
			}
		}
	}
}
