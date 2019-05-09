<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>사원 관리</strong></h1>
	        </div>
	    </div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<table class="table table-bordered">
			<thead style="background-color: #91B4C8; color: white;">
		       	<tr>
		           	<th scope="col" colspan="4">기본 정보</th>
		      	</tr>
		   	</thead>
		    <tbody>
		        <tr>
		            <th scope="row" style="width: 200px;">사원번호</th>
		            <td colspan="2" style="width: 650px;">${eVo.emp_sq }</td>
		            <td rowspan="4" style="width: 200px;"><img src="${cp }/userImg?emp_sq=${eVo.emp_sq }" /></td>
		        </tr>
		        <tr>
		            <th scope="row" style="width: 200px;">이름</th>
		            <td colspan="2" style="width: 650px;">${eVo.emp_nm }</td>
		        </tr>
		        <tr>
		            <th scope="row" style="width: 200px;" rowspan="2">주소</th>
		            <td colspan="2" style="width: 650px;">${eVo.emp_addr1 }</td>
		        </tr>
		        <tr>
		            <td colspan="2" style="width: 650px;">${eVo.emp_addr2 }</td>
		        </tr>
		    </tbody>
		</table>
		
		<table class="table table-bordered">
			<thead style="background-color: #91B4C8; color: white;">
		       	<tr>
		           	<th scope="col" colspan="4">연락처 정보</th>
		      	</tr>
		   	</thead>
		    <tbody>
		        <tr>
		            <th scope="row" style="width: 200px;">전화번호</th>
		            <td colspan="2" style="width: 850px;">${eVo.emp_phone }</td>
		        </tr>
		        <tr>
		            <th scope="row" style="width: 200px;">개인이메일</th>
		            <td colspan="2" style="width: 850px;">${eVo.emp_psn_email }</td>
		        </tr>
		        <tr>
		        	<th scope="row" style="width: 200px;">회사이메일</th>
		            <td colspan="2" style="width: 850px;">${eVo.emp_com_email }</td>
		        </tr>
		    </tbody>
		</table>
		
		<table class="table table-bordered">
			<thead style="background-color: #91B4C8; color: white;">
		       	<tr>
		           	<th scope="col" colspan="4">부서 정보</th>
		      	</tr>
		   	</thead>
		    <tbody>
		        <tr>
		            <th scope="row" style="width: 200px;">부서</th>
		            <td colspan="2" style="width: 850px;">${departmentVo.dpt_nm }</td>
		        </tr>
		        <tr>
		            <th scope="row" style="width: 200px;">직급</th>
		            <td colspan="2" style="width: 850px;">
		            	<c:choose>
	                    	<c:when test="${eVo.emp_grade == '1' }">사장</c:when>
	                    	<c:when test="${eVo.emp_grade == '2' }">전무</c:when>
	                    	<c:when test="${eVo.emp_grade == '3' }">이사</c:when>
	                    	<c:when test="${eVo.emp_grade == '4' }">부장</c:when>
	                    	<c:when test="${eVo.emp_grade == '5' }">과장</c:when>
	                    	<c:when test="${eVo.emp_grade == '6' }">팀장</c:when>
	                    	<c:when test="${eVo.emp_grade == '7' }">대리</c:when>
	                    	<c:otherwise>사원</c:otherwise>
	                    </c:choose>
		            </td>
		        </tr>
		        <tr>
		        	<th scope="row" style="width: 200px;">입사일</th>
		            <td colspan="2" style="width: 850px;">
		            	<fmt:formatDate value="${eVo.emp_ent }" pattern="yyyy-MM-dd" />
		            </td>
		        </tr>
		        <tr>
		        	<th scope="row" style="width: 200px;">최종 수정일</th>
		            <td colspan="2" style="width: 850px;">
		            	<fmt:formatDate value="${eVo.emp_fnl_mod }" pattern="yyyy-MM-dd" />
		            </td>
		        </tr>
		    </tbody>
		</table>
		<br>
		<button id="updBtn" class="btn btn-primary">수정</button>
		<button id="cancelBtn" class="btn btn-danger">삭제</button>
	</div>
</div>

<form id="frm" action="${cp }/userUpdate" method="get" class="form-horizontal" role="form">
	<input type="hidden" id="emp_sq" name="emp_sq" />
</form>

<script>
	$(document).ready(function() {
		<c:if test="${msg != null}">
			alert("${msg}");
		</c:if>
	
		$("#updBtn").on("click", function() {
			$("#emp_sq").val("${eVo.emp_sq }");
			
			$("#frm").submit();
		});
		$("#cancelBtn").on("click", function() {
			$("#emp_sq").val("${eVo.emp_sq }");
			$("#frm").attr("action", "${cp}/deleteEmp");
			$("#frm").submit();
		});
	});
</script>