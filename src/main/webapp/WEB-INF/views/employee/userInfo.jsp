<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs">
	<div class="col-sm-4">
		<div class="page-header float-left" id="page-title-style">
			<div class="page-title">
				<h1 style="padding-bottom: 0px;">
					<strong>내 정보</strong>
				</h1>
			</div>
		</div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4">
		<br>
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
					<td rowspan="4" style="width: 200px;"><img
						src="${cp }/userImg?emp_sq=${eVo.emp_sq }" /></td>
				</tr>
				<tr>
					<th scope="row" style="width: 200px;">이름</th>
					<td colspan="2" style="width: 650px;">${eVo.emp_nm }
						&nbsp;&nbsp; <c:choose>
							<c:when test="${eVo.emp_sign != null }">
								<button type="button" class="btn btn-outline-secondary btn-sm"
									data-toggle="modal" data-target="#signModal">서명</button>
							</c:when>
							<c:otherwise>
								<button type="button" id="signBtn"
									class="btn btn-outline-secondary btn-sm">서명</button>
							</c:otherwise>
						</c:choose>
					</td>
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
					<td colspan="2" style="width: 850px;"><c:choose>
							<c:when test="${eVo.emp_grade == '1' }">사장</c:when>
							<c:when test="${eVo.emp_grade == '2' }">전무</c:when>
							<c:when test="${eVo.emp_grade == '3' }">이사</c:when>
							<c:when test="${eVo.emp_grade == '4' }">부장</c:when>
							<c:when test="${eVo.emp_grade == '5' }">과장</c:when>
							<c:when test="${eVo.emp_grade == '6' }">팀장</c:when>
							<c:when test="${eVo.emp_grade == '7' }">대리</c:when>
							<c:otherwise>사원</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<th scope="row" style="width: 200px;">입사일</th>
					<td colspan="2" style="width: 850px;"><fmt:formatDate
							value="${eVo.emp_ent }" pattern="yyyy-MM-dd" /></td>
				</tr>
			</tbody>
		</table>
		<br>
		<button type="button" id="updBtn" class="btn btn-primary">수정</button>
		<button type="button" id="LogBtn" class="btn btn-danger">로그인
			기록</button>
	</div>
</div>

<div class="modal fade" id="signModal" tabindex="-1" role="dialog"
	aria-labelledby="signModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document" style="width: 350px">
		<div class="modal-content">
			<div class="modal-header">
				<h6 class="modal-title" id="signModalLabel">${eVo.emp_nm }님의
					서명입니다.</h6>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body">
				<img src="${cp }/userSign?emp_sq=${eVo.emp_sq }" />
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal"
					class="btn btn-primary btn-sm col-sm-3">확인</button>
			</div>
		</div>
	</div>
</div>
<form id="frm" action="${cp }/userModify" method="get"
	class="form-horizontal" role="form"></form>
<script>
var cp = "${cp}";
	$(document).ready(function() {
		<c:if test="${msg != null}">
			alert("${msg}");
		</c:if>
	
		$("#updBtn").on("click", function() {
			$("#frm").submit();
		});
		
		$("#signBtn").on("click", function() {
			alert("등록된 서명이 없습니다.");
		});
		
		$("#LogBtn").on("click", function() {
		$("#frm").attr("action", cp + "/logSelectList");
 			$("#frm").submit();
		});

	});

	
</script>