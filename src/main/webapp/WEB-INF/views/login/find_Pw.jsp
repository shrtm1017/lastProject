<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
        
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>NK Office</title>
    <meta name="description" content="Sufee Admin - HTML5 Admin Template">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/styless.css">
    <!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/html5shiv/3.7.3/html5shiv.min.js"></script> -->
	
	<!-- jsp page로 lib 포함하는방법(정적 include) -->
	<%@ include file="/WEB-INF/views/login/jsLogin.jsp" %>
</head>

<body
	style="background-image:url('${cp}/css/login/startup-594091_1920.jpg');">

	<div class="container">
		<header>
			<div style="padding-top: 150px;"></div>
		</header>
		<section>
			<div id="container_demo" style="vertical-align: middle;">
				<a class="hiddenanchor" id="toregister"></a> <a class="hiddenanchor"
					id="tologin"></a>
				<div id="wrapper">
					<div id="login" class="animate form">
						<form action="${cp}/findpw" method="post" id="frm">
							<h1><img src="images/logo1.png" alt="Logo" style="width: 250px;"></h1>
							<p>
								<label for="username" class="uname">사원번호 </label> 
								<input type="text" id="pw_emp_sq" name="pw_emp_sq" placeholder="사원번호">
							</p>
							<p>
								<label for="password" >휴대폰번호</label>
								<input type="text" id="pw_emp_phone" name="pw_emp_phone"  placeholder="휴대폰번호">
								 <label>${find_msg}</label>
							</p>

							<button type="button" class="btn btn-primary" id="btn_findpw"
								style="width: 386.75px;">비밀번호 찾기</button>
						</form>
						<br>
						<div>
						<button type="button" id="loginBtn" style="width: 386.75px;" class="btn btn-success">로그인 화면으로 이동</button>
					</div>
					</div>
				</div>
			</div>
		</section>
	</div>

<form id="loginFrm"  action="${cp }/login" method="get" class="form-horizontal" role="form">
</form>

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-23581568-13" type="be04315a8c760dba8930125f-text/javascript"></script>
<script type="be04315a8c760dba8930125f-text/javascript">
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-23581568-13');
</script>
<script src="https://ajax.cloudflare.com/cdn-cgi/scripts/a2bd7673/cloudflare-static/rocket-loader.min.js" data-cf-settings="be04315a8c760dba8930125f-|49" defer=""></script>
<script>
	$(document).ready(function() {
		<c:if test="${find_msg != null}">
		alert("${find_msg}");
		</c:if>
		
		$("#btn_findpw").on("click", function(){
			if ($("#pw_emp_sq").val().trim() == "") {
				alert("사원번호를 입력해 주세요.");
				$("#pw_emp_sq").focus();
				return;
			}
			if ($("#pw_emp_phone").val().trim() == "") {
				alert("핸드폰 번호를 입력해 주세요.");
				$("#pw_emp_phone").focus();
				return;	
			}
			
			var emp_sq = $("#pw_emp_sq").val();
			var emp_phone = $("#pw_emp_phone").val();
			
			console.log("emp_sq :"+emp_sq);
			console.log("emp_phone :"+emp_phone);
			
			if(emp_sq != null && emp_phone != null) {
				$("#frm").submit();
			}
			else{
				alert("빈칸을 채워주세요");
			}

		});
		
		$("#loginBtn").on("click", function() {
			$("#loginFrm").submit();
		});
	});
</script>
</body>
</html>