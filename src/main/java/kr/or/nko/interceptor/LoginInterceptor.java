package kr.or.nko.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.nko.employee.model.EmployeeVo;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	/**
	 * Method : preHandle
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * Method 설명 : controller method 실행전
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("ProfileInterceptor preHandle");
		
		//다른 인터셉터 혹은 controller로 요청을 계속 위임 처리
		//전처리
		//1.session에 userVO(LoginServlet에서 정상 로그인 처리 되었을시 저장한 속성)가
		//  있는지 확인(정상적으로 로그인한 상태인지 확인)
		HttpSession session = request.getSession();
		EmployeeVo employeeVo = (EmployeeVo)session.getAttribute("employeeVo");
		
		//2-1.정상 로그인 상태이면 --> chain.doFilter 호출
		if(employeeVo != null ){
			return true;
		}
		
		//2-2.로그인 상태가 아니라면 --> 로그인 화면으로 이동(/login -> get)
		//*** 모든 페이지에 대해 적용 해야하나??
		//세션이 필요한 페이지랑, 필요 없는 페이지랑 구분 필요
		//	세션 검증이 필요없는 요청(대다수는 검증이 필요함)
		//	** login 페이지 화면요청(/login -> get) session 검증이 필요 없음
		//	** login 요청 처리(/login post) session 검증이 필요 없음
		//	** /js/*, /css/* 하위 모든 파일
		//	ex) localhost/login
		else{
			String uri = request.getRequestURI(); // /login
			
			logger.debug("intercepter uri : {}", uri);
			
			//요청 처리
			// /js/CookieUtil.js, /js/jsCookie.js
			if(uri.equals(request.getContextPath() + "/login")
					|| uri.endsWith(".js") || uri.endsWith(".css")
					|| uri.endsWith(".jpg") || uri.endsWith(".jpeg")
					|| uri.endsWith(".gif") || uri.endsWith(".png")){
				return true;
			}else if(uri.equals(request.getContextPath() + "/findid") || 
					 uri.equals(request.getContextPath() + "/findpw")){
				return true;
			}
			else{
				response.sendRedirect("/login");
				return false;
			}
		}
				
	}

	/**
	 * Method : postHandle
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 * Method 설명 : controller method 실행후
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("ProfileInterceptor postHandle");
	}
	
}