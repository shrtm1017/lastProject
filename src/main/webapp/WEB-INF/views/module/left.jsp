<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- left메뉴 아이콘과 메뉴폰트 css 수정 --%>
<style>
.navbar .navbar-nav li > a .menu-icon {
    color: #FFFFFF;
    float: left;
    margin-top: 8px;
    width: 55px;
    text-align: left;
    z-index: 9;
}

.navbar .navbar-nav li > a {
    background: none !important;
    color: #FFFFFF !important;
    display: inline-block;
    font-family: 'Open Sans';
    font-size: 18px;
    line-height: 30px;
    padding: 10px 0;
    position: relative;
    width: 100%;
    font-weight: bold;
}

.navbar .navbar-nav li.menu-item-has-children .sub-menu i {
    color: #FFFFFF;
    float: left;
    padding: 0;
    position: absolute;
    left: 0;
    font-size: 14px;
    top: 9px;
}

</style>

<aside id="left-panel" class="left-panel">
    <nav class="navbar navbar-expand-sm navbar-default">
        <div class="navbar-header">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-menu" aria-controls="main-menu" aria-expanded="false" aria-label="Toggle navigation">
                <i class="fa fa-bars"></i>
            </button>
            <a class="navbar-brand" href="${cp}/main"><img src="images/logo2.png" alt="Logo"></a>
        </div>
	
        <div id="main-menu" class="main-menu collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <h3 class="menu-title">
                	<a id="aChat"><i class="fa fa-comments-o fa-2x" style="color: #FFFFFF;"></i></a>
                	&nbsp;&nbsp;
                	<a id="aUsers"><i class="fa fa-users fa-2x" style="color: #FFFFFF;"></i></a>
                </h3>
                
                <li id="liChat" style="display: none;">
                	<div class="input-group">
	                	<input type='text' id='inputChat' class="form-control" style="color: #FFFFFF; background-color: #272C33;" />
	                	&nbsp;
						<input type='button' id='btnChat' value='전송' class="btn btn-info"/>
					</div>
					<%-- div태그의 word-break:break-all; word-wrap:break-word; 속성은 자동 줄바꿈!!! --%>
					<div id="divChat" style="overflow-y: auto; max-width: inherit; height: 400px; word-break:break-all; word-wrap:break-word; background-color: #272C33; color: #8B939B;
						border-style: solid;
						border-color: #CED4DA;
						border-width: 1px;
						border-radius: 0.5em;
						border-top-left-radius: 0.5em;
						border-top-right-radius: 0.5em;
						border-bottom-left-radius: 0.5em;
						border-bottom-right-radius: 0.5em;">
					</div>
                </li>
                
                <li id="liUsers" style="display: none;">
                	<div class="input-group">
					</div>
					<%-- div태그의 word-break:break-all; word-wrap:break-word; 속성은 자동 줄바꿈!!! --%>
					<div id="divUsers" style="overflow-y: auto; max-width: inherit; height: 400px; width: 230px; word-break:break-all; word-wrap:break-word; background-color: #272C33; color: #8B939B;
						border-style: solid;
						border-color: #CED4DA;
						border-width: 1px;
						border-radius: 0.5em;
						border-top-left-radius: 0.5em;
						border-top-right-radius: 0.5em;
						border-bottom-left-radius: 0.5em;
						border-bottom-right-radius: 0.5em;">
					</div>
                </li>
                
                <li class="menu-item-has-children dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="menu-icon fa fa-clipboard"></i>전자결재</a>
                    <ul class="sub-menu children dropdown-menu">
                        <li><i class="fa fa-pencil-square-o"></i><a href="${cp}/apvList">기안하기</a></li>
                        <li><i class="fa fa-folder-open"></i><a href="${cp}/apvReceive">내가 받은 결재</a></li>
                        <li><i class="fa fa-folder-open-o"></i><a href="${cp}/apvSend">내가 올린 결재</a></li>
                        <li><i class="fa fa-folder-o"></i><a href="${cp}/apvReference">참조문서함</a></li>
                    </ul>
                </li>
                
                <li class="menu-item-has-children dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="menu-icon fa fa-tasks"></i>업무 관리</a>
                    <ul class="sub-menu children dropdown-menu">
						<li><i class="fa fa-archive"></i><a href="${cp}/workManage">내 업무 관리</a></li>
	                    <li><i class="fa fa-align-left"></i><a href="${cp}/ganttChart">Gantt 차트</a></li>
                    </ul>
                </li>
                
                <li class="menu-item-has-children dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="menu-icon fa fa-calendar-o"></i>일정 관리</a>
                    <ul class="sub-menu children dropdown-menu">
                        <li><i class="fa fa-calendar"></i><a href="${cp}/pesonSch">개인 일정</a></li>
                        <li><i class="fa fa-calendar"></i><a href="${cp}/departSch">부서 일정</a></li>
                        <li><i class="fa fa-calendar"></i><a href="${cp}/companySch">회사 일정</a></li>
                        <li><i class="fa fa-list"></i><a href="${cp}/toDoList">To Do List</a></li>
                    </ul>
                </li>
                
                <li class="menu-item-has-children dropdown">
                	<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="menu-icon fa fa-bars"></i>게시판</a>
                	<ul class="sub-menu children dropdown-menu">
	                	    	<li><i class="fa fa-bullhorn"></i><a href="${cp}/noticeBoard?ntc_div=1">공지사항</a></li>
	            	        	<li><i class="fa fa-envelope-o"></i><a href="${cp}/noticeBoard?ntc_div=2">경조사</a></li>
	            	        	<li><i class="fa fa-trophy"></i><a href="${cp}/noticeBoard?ntc_div=3">승진인사</a></li>
	            	        	<li><i class="fa fa-download"></i><a href="${cp}/datas">자료실</a></li>
	                    <li><i class="fa fa-pencil"></i><a href="${cp}/researchList">설문조사</a></li>
        	    	</ul>
                </li>
                
                <li class="menu-item-has-children dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="menu-icon fa fa-laptop"></i>마이데스크</a>
                    <ul class="sub-menu children dropdown-menu">
                    	<li><i class="fa fa-user"></i><a href="${cp}/userPassCheck">내 정보</a></li>
                        <li><i class="fa fa-bell"></i><a href="${cp}/CmtLookup">근태 관리</a></li>
                        <c:if test="${sessionScope.authority != 1}">
	                        <li><i class="fa fa-file-text"></i><a href="${cp}/ctfList">증명서 관리</a></li>
                        </c:if>
                        <li><i class="fa fa-suitcase"></i><a href="${cp}/cardList">명함 관리</a></li>
                        <li><i class="fa fa-book"></i><a href="${cp}/employeeContactMange">사원 연락처</a></li>
                    </ul>
                </li>
                
                <li class="menu-item-has-children dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="menu-icon fa fa-envelope"></i>웹 메일</a>
                    <ul class="sub-menu children dropdown-menu">
                        <li><i class="fa fa-pencil"></i><a href="${cp}/emailSend">메일 쓰기</a></li>
                        <li><i class="fa fa-envelope"></i><a href="${cp}/receiveMailbox">받은 메일함</a></li>
                        <li><i class="fa fa-envelope-o"></i><a href="${cp}/sendMailbox">보낸 메일함</a></li>
                        <li><i class="fa fa-edit"></i><a href="${cp}/tempMailbox">임시 메일함</a></li>
                    </ul>
                </li>
                
                <li class="menu-item-has-children dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="menu-icon fa fa-desktop"></i>화상회의</a>
                    <ul class="sub-menu children dropdown-menu">
                        <li><i class="fa fa-video-camera"></i><a href="${cp}/vc">회의실</a></li>
                    </ul>
                </li>
                
                <c:if test="${sessionScope.authority == 1}">
                	<li class="menu-item-has-children dropdown">
	                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="menu-icon fa fa-cogs"></i>관리자</a>
	                    <ul class="sub-menu children dropdown-menu">
	                        <li><i class="fa fa-group"></i><a href="${cp}/userManage">사원 관리</a></li>
	                        <li><i class="fa fa-ellipsis-h"></i><a href="${cp}/approvalSignLine">결재라인 관리</a></li>
	                        <li><i class="fa fa-folder"></i><a href="${cp}/apvAllList">전체 문서함</a></li>
	                        <li><i class="fa fa-file-text-o"></i><a href="${cp}/ctfList">증명서 관리</a></li>
	                    </ul>
	                </li>
                </c:if>
            </ul>
        </div>
    </nav>
</aside>

<!-- Web socket CDN -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.js"></script>
<script>
	//handshake를 한다
	const chatSock = new SockJS("<c:url value='/webSocketTest'/>");
	
	//서버와 연결이 되었을 때
	function chatOnOpen(evt) {
		$("#divChat").append("<b style='color: #FFFFFF;'>연결됨</b><br>");
		socketEmployeeList();
	}
	
	//서버와 연결을 끊었을 때
	function chatOnClose(evt) {
		$("#divChat").append("<b style='color: #FFFFFF;'>연결 끊김</b><br>");
	}
	
	//메시지 전송
	function chatSendMessage() {
		chatSock.send($("#inputChat").val());
	}
	
	//서버로부터 메시지를 받았을 때
	function chatOnMessage(msg) { //socket에서 정보를 수신했을 때 실행된다. msg.data로 정보가 들어온다.
		console.log(msg);
		var data = msg.data;
		console.log(data);
		
		var html = "";
		//사용자
		var user = data.split(":")[0];
		//사용자 번호와 이름
		var userSq = user.split(",")[0];
		var userNm = user.split(",")[1];
		//사용자 이름을 제외한 메세지
		var text = data.substring(data.split(":")[0].length+1, data.length);
		
		//사용자 번호를 로그인한 사원번호와 비교하여 일치하면 자신이고 일치하지 않으면 다른사람임
		if(userSq == "${employeeVo.emp_sq}"){
			$("#divChat").append("<img class='user-avatar rounded-circle' src='/userImg?emp_sq="+ userSq +"' style='width:32px; height:32px;'>" + "&nbsp;<b style='color: #FFFFFF;'>나:&nbsp;"+ text +" </b><br>");
		}else{
			$("#divChat").append("<img class='user-avatar rounded-circle' src='/userImg?emp_sq="+ userSq +"' style='width:32px; height:32px;'>" + "&nbsp;<b style='color: #FFFFFF;'>" + userNm + ":&nbsp;"+ text +" </b><br>");
		}
		
		//div 스크롤 자동으로 밑으로 고정
		const top = $("#divChat").prop("scrollHeight");
		$("#divChat").scrollTop(top);
	}
	
	//접속한 유저리스트 가져오는 ajax함수
	function socketEmployeeList(){
		$.ajax({
			url : "${cp}/socketEmployeeList",
			method : "post",
			async: false,
			dataType : "json",	//server로부터 받으려는 데이터타입
			contentType : "application/json; charset=utf-8", //client가 전송하는 데이터타입 
			success : function(data){
				var arr = data;
				var html = "";
				for(var i=0; i<arr.length; i++){
					if(arr[i].emp_on == "Y"){
						html += "<img class='user-avatar rounded-circle' src='/userImg?emp_sq="+ arr[i].emp_sq +"' style='width:32px; height:32px;'>" + "&nbsp;<b style='color: #FFFFFF;'>" + arr[i].emp_nm + "</b><br>";
					}
				}
				
				$("#divUsers").html(html);
			}
		});
	}
	
	$(document).ready(function(){
		//서버가 열렸을때
		chatSock.onopen = chatOnOpen;
		
		//handshake가 완료되고 connection이 맺어지면 실행된다
		chatSock.onmessage = chatOnMessage;
		chatSock.onclose = chatOnClose;
		
		//채팅전송버튼 클릭시
		$("#btnChat").click(function() {
			console.log($("#inputChat").val());
			chatSendMessage();
			$("#inputChat").val("");
			$("#inputChat").focus();
		});

		//엔터키를 입력시
		$("#inputChat").keydown(function(key) {
			if (key.keyCode == 13) { //엔터
				console.log($("#inputChat").val());
				chatSendMessage();
				$("#inputChat").val("");
				$("#inputChat").focus();
			}
		});
		
		//setInterval 값
		var timeId = null;
		
		//채팅 a태그 클릭 이벤트
		$("#aChat").on("click", function(){
			//메뉴항목들의 display 속성 체크
			var flagDisplay = $(".menu-item-has-children").css("display");
			console.log(flagDisplay);
			
			//list-item이면 숨기고, none이면 보이기
			if(flagDisplay == "list-item"){
				$(".menu-item-has-children").hide();
				$("#liUsers").hide();
				$("#liChat").show();
			}else{
				$(".menu-item-has-children").show();
				$("#liUsers").hide();
				$("#liChat").hide();
			}
			
			//접속한 유저리스트 값 가져오는 ajax함수 중지
			clearInterval(timeId);
		});
		
		//사원목록 a태그 클릭 이벤트
		$("#aUsers").on("click", function(){
			//메뉴항목들의 display 속성 체크
			var flagDisplay = $(".menu-item-has-children").css("display");
			console.log(flagDisplay);
			
			//list-item이면 숨기고, none이면 보이기
			if(flagDisplay == "list-item"){
				$(".menu-item-has-children").hide();
				$("#liChat").hide();
				$("#liUsers").show();
				
				//접속한 유저리스트 값 가져오는 ajax함수 시작
				timeId = setInterval("socketEmployeeList()", 1000);
			}else{
				$(".menu-item-has-children").show();
				$("#liChat").hide();
				$("#liUsers").hide();
				
				//접속한 유저리스트 값 가져오는 ajax함수 중지
				clearInterval(timeId);
			}
		});
		
	});
</script>