package kr.or.nko.vc.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(HandshakeInterceptor.class);
	
	//웹소켓 사용시 httpsession에 있는 정보를 사용하기위해 사용!!!
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		
		logger.debug("HandshakeInterceptor 과정");
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}
}