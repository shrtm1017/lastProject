<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<header id="header" class="header">
    <div class="header-menu">
        <div class="col-sm-7">
            <a id="menuToggle" class="menutoggle pull-left"><i class="fa fa fa-tasks"></i></a>
            <div class="header-left">
                <div class="dropdown for-notification">
                    <button class="btn btn-secondary dropdown-toggle" type="button" id="notification" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fa fa-bell"></i>
                        <span class="count bg-danger">${signalSize }</span>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="notification" style="background-color: #FFFFD2;">
                        <p style="color: black;"><i class="fa fa-lightbulb-o"></i> <strong>전자결재</strong></p>
						<c:if test="${signalSize != '0' }">
	                    	<c:forEach items="${signalList }" var="signal">
								<a class="dropdown-item" href="${cp}/apvSignal?sig_sq=${signal.sig_sq }">
                           			${signal.sig_con }
		                    	</a>
	                    	</c:forEach>
						</c:if>
                    </div>
                </div>
                
                <div class="dropdown for-message">
                    <button class="btn btn-secondary dropdown-toggle" type="button"
                        id="message"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fa fa-envelope-o"></i>
                        <span class="count bg-primary" id="countMs"></span>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="message" style="background-color: #FFFFD2;">
                        <p style="color: black;"><i class="fa fa-envelope-o"></i> <strong>쪽지</strong></p>
	                    <a class="dropdown-item media" href="#">
	                        <span id="messageId" class="message media-body">
	                        	쪽지를 확인하세요.
	                            <strong><span style="color: #008299;" class="time float-right" id="countM"></span></strong>
	                        </span>
	                    </a>
                    </div>
                </div>

                <div class="dropdown for-notification">
                    <button class="btn btn-secondary dropdown-toggle" type="button" id="notification" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fa fa-check-square-o"></i>
                        <span id="countnum" class="count bg-success">${todoSize }</span>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="notification" style="background-color: #FFFFD2;">
                        <p style="color: black;"><i class="fa fa-check-square-o"></i> <strong>To Do List</strong></p>
                        <a class="dropdown-item media" href="#">
                            <span id="todoChk">
                            	<label style="color: #476600;"><strong>${todoSize }</strong></label>
                            	개의 할 일이 남았습니다.
                            </span>
                    	</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-sm-5">
            <div class="user-area dropdown float-right">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <img class="user-avatar rounded-circle" src="${cp}/userImg?emp_sq=${employeeVo.emp_sq }" alt="User Avatar" style="width: 40px; height: 40px;">
                </a>

                <div class="user-menu dropdown-menu" style="background-color: #D9E5FF;">
                    <a class="nav-link" href="${cp }/userPassCheck"><i class="fa fa-user"></i> 내 정보</a>
                    <a class="nav-link" href="${cp }/userLogout"><i class="fa fa-power-off"></i> 로그아웃</a>
                </div>
            </div>
        </div>
    </div>
</header>

<form id="frm33">
</form>

<script type="text/javascript" src="http://jsgetip.appspot.com"></script>
<!-- Web socket CDN -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.js"></script>

<script>
	let sockMe = new SockJS("<c:url value='/count'/>");
	sockMe.onmessage = onMessage;
	sockMe.onclose = onClose;
	
	$(document).ready(function(){
		$("#todoChk").on("click",function(){
			$("#frm33").attr("action","${cp}/toDoList");
			$("#frm33").submit();
		});
		
		jQuery("#messageId").on("click",function(){
			jQuery("#frm33").attr("action","${cp}/messageList");
			jQuery("#frm33").submit();
		});
		
		sockMe.onopen = onOpen;
		
	});
	
    function send_message() { // 메시지 전송
		console.log("메시지 전달 한다");
		sockMe.send("${memId}");
    }
   
    function onOpen(evt) 
    {
		console.log("서버 오픈");
    	send_message("로그인시 보내는 메시지"); 
    }
    function onMessage(evt) { // 서버로부터 메시지를 받았을 때
    	console.log("성공");
    	console.log(evt.data);
    	
    	// data는 보낸인원
    	
    	$("#countM").text(evt.data);
    	$("#countMs").text(evt.data);
    }
    function onError(evt) {
    	console.log("error 문제");
    }
    function onClose(evt) { // 서버와 연결을 끊었을 때
    	console.log("server 종료");
    }
    
</script> 