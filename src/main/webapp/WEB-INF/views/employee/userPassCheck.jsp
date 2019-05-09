<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>내 정보</strong></h1>
	        </div>
	    </div>
	</div>
</div>
<div>
	<div class="login-content">
		<div class="login-logo">
	        <a href="${cp }/main"><img class="align-content" src="images/logo1.png" style="width: 250px;"></a>
		</div>
	    <div class="card">
	    	<div class="card-body">
		        <form id="frm" action="${cp }/userPassCheck" method="post" class="form-horizontal" role="form">
		            <div class="form-group">
		                <strong>비밀번호 확인</strong>
		                <input name="emp_pass" type="password" class="form-control" placeholder="비밀번호">
		            </div>
		            <button id="passBtn" class="btn btn-primary col-sm-12 text-center">확인</button>
		        </form>
	        </div>
	    </div>
	</div>
</div>

<script>
	$(document).ready(function() {
		<c:if test="${msg != null}">
			alert("${msg}");
		</c:if>
	
		$("#passBtn").on("click", function() {
			$("frm").submit();
		});
	});
</script>
