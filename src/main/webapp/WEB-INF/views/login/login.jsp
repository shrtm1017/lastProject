<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<%@ include file="/WEB-INF/views/login/jsLogin.jsp"%>
</head>
<body style="background-image:url('${cp}/css/login/startup-594091_1920.jpg');">
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
						<form action="${cp}/login" method="post" id="loginfrm">
							<h1><img src="images/logo1.png" alt="Logo" style="width: 250px;"></h1>
							<p>
								<label for="username" class="uname">사원번호 </label> <input
									id="emp_sq" name="emp_sq" type="text" placeholder="사원번호"
									maxlength="6" oninput="maxLengthCheck(this)" onkeypress="inNumber();" />
							</p>
							<p>
								<label for="password" class="youpasswd">비밀번호</label> <input
									id="emp_pass" name="emp_pass" type="password"
									placeholder="비밀번호" />
							</p>
							<div class="checkbox">
								<label> <input type="checkbox" id="rememberme"
									value="remember-me"> 사원번호 저장
								</label>
							</div>

							<!-- 							<button type="submit" class="btn btn-success" id="loginButton">로그인</button> -->
							<button type="submit" class="btn btn-success" id="loginButton"
								style="width: 386.75px;">로그인</button>
						</form>
						<div class="col-md-12"></div>

						<div class="social-login-content">
							<div class="social-button" style="text-align: center;">
								<a href="${cp}/findid" style="width: 190px;">
									<button type="button" class="btn btn-info btn-addon mt-2" style="width: 386.75px;">아이디
										찾기</button>
								</a> <a href="${cp}/findpw" style="width: 386.75px;"><button
										type="button" class="btn btn-primary btn-addon mt-2" style="width: 386.75px;">비밀번호
										찾기</button></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>

	<form action="${cp}/login" method="post" id="frmtest"></form>


	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async
		src="https://www.googletagmanager.com/gtag/js?id=UA-23581568-13"
		type="be04315a8c760dba8930125f-text/javascript"></script>
	<script type="be04315a8c760dba8930125f-text/javascript">
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-23581568-13');
</script>
	<script
		src="https://ajax.cloudflare.com/cdn-cgi/scripts/a2bd7673/cloudflare-static/rocket-loader.min.js"
		data-cf-settings="be04315a8c760dba8930125f-|49" defer=""></script>

	<script>
		$(document).ready(function() {
			if ("${msg}" != "") {
				alert("${msg}");
			}

			if ("${find_msg}" != "") {
				alert("${find_msg}");
			}

			console.log("cp : ${cp}");
			if (Cookies.get("emp_sq")) {
				$("#emp_sq").val(Cookies.get("emp_sq"));
				$("#rememberme").prop("checked", true);
			}

			$("#loginButton").click(function() {
				console.log("sign click");
				if ($("#emp_sq").val().length == 0) {
					alert("사원번호를 입력하세요");
					$("#emp_sq").focus();
					return false;
				}

				if ($("#rememberme").prop("checked")) {
					Cookies.set("emp_sq", $("#emp_sq").val(), {
						expires : 30
					});
					Cookies.set("rememberme", "b", {
						expires : 30
					});
				} else {
					Cookies.remove("emp_sq");
					Cookies.remove("rememberme");
				}

			});

		});
		$("#loginButton").on("click", function() {
			$("loginfrm").submit();
		});
		function maxLengthCheck(object) {
			if (object.value.length > object.maxLength) {
				object.value = object.value.slice(1, object.maxLength);
			}
		}
		 function inNumber(){
		        if(event.keyCode<48 || event.keyCode>57){
		           event.returnValue=false;
		        }
		    }
	</script>

</body>
</html>