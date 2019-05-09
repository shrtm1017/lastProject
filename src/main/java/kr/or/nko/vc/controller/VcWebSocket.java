package kr.or.nko.vc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.employee.service.IEmployeeService;

public class VcWebSocket extends TextWebSocketHandler{
	
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	private Logger logger = LoggerFactory.getLogger(VcWebSocket.class);
	
	@Resource(name="employeeService")
	IEmployeeService employeeService;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionList.add(session);
		logger.debug("{} 연결됨", session.getId());
		
		EmployeeVo employeeVo = (EmployeeVo) session.getAttributes().get("employeeVo");
		logger.debug("{} 연결됨", employeeVo);
		
		//접속여부 Y로 수정
		employeeVo.setEmp_on("Y");
		employeeService.updateOn(employeeVo);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		EmployeeVo employeeVo = (EmployeeVo) session.getAttributes().get("employeeVo");
		logger.debug("로그인한 유저 : {}", employeeVo);
		logger.debug("{} - {}로 부터 {} 받음", employeeVo.getEmp_nm(), session.getId(), message.getPayload());
		for (WebSocketSession sess : sessionList) {
			sess.sendMessage(new TextMessage(employeeVo.getEmp_sq() + "," + employeeVo.getEmp_nm() + ",: " + message.getPayload()));
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessionList.remove(session);
		logger.debug("{} 연결 끊김", session.getId());
		
		EmployeeVo employeeVo = (EmployeeVo) session.getAttributes().get("employeeVo");
		logger.debug("{} 연결 끊김", employeeVo);
		
		//접속여부 N으로 수정
		employeeVo.setEmp_on("N");
		employeeService.updateOn(employeeVo);
	}

}