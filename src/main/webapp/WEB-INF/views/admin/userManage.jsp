<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<div class="form-group">
			<div class="col col-md-2"></div>
			<div class="col col-md-8">
				<div class="input-group">
					<select id="selectBox" name="selector" class="form-control col-sm-2">
						<option value="1">이름</option>
						<option value="2">사원번호</option>
						<option value="3">부서명</option>
					</select>
					<input id="inputText" type="text" name="input1-group2" placeholder="" class="form-control">
					<div class="input-group-btn">
						<button id="btnSearch" class="btn btn-secondary">
							<i class="fa fa-search"></i>
						</button>
					</div>
				</div>
			</div>
			<div class="col col-md-2" style="text-align: right;">
				<button type="button" id="insBtn" class="btn btn-secondary col-sm-7">사원 등록</button>
			</div>
		</div>
		<br><br>
		<table class="table text-center">
			<thead style="background-color: #91B4C8; color: white;">
				<tr>
					<th scope="col" style="width: 200px;">사원번호</th>
					<th scope="col" style="width: 200px;">사원명</th>
					<th scope="col" style="width: 200px;">부서</th>
					<th scope="col" style="width: 200px;">직급</th>
					<th scope="col" style="width: 200px;">입사일</th>
					<th scope="col" style="width: 200px;">최종수정일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${employeeList}" var="employee" varStatus="i">
					<c:if test="${employee.emp_flag == 'n' }">
						<tr class="empTr" data-empsq="${employee.emp_sq}">
							<th scope="row">${employee.emp_sq}</th>
							<td>${employee.emp_nm}</td>
							<td>${departmentList[i.index].dpt_nm}</td>
							<!-- employeeList의 i번째 인덱스의 부서 -->
							<td><c:choose>
									<c:when test="${employee.emp_grade == 1}">
									사장
								</c:when>
									<c:when test="${employee.emp_grade == 2}">
									전무
								</c:when>
									<c:when test="${employee.emp_grade == 3}">
									이사
								</c:when>
									<c:when test="${employee.emp_grade == 4}">
									부장
								</c:when>
									<c:when test="${employee.emp_grade == 5}">
									과장
								</c:when>
									<c:when test="${employee.emp_grade == 6}">
									팀장
								</c:when>
									<c:when test="${employee.emp_grade == 7}">
									대리
								</c:when>
									<c:when test="${employee.emp_grade == 8}">
									사원
								</c:when>
								</c:choose>
							</td>
							<td>
		            			<fmt:formatDate value="${employee.emp_ent }" pattern="yyyy-MM-dd" />
							</td>
							<td>
		            			<fmt:formatDate value="${employee.emp_fnl_mod }" pattern="yyyy-MM-dd" />
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
		<br>
		<div class="dataTables_paginate paging_simple_numbers"
			id="bootstrap-data-table_paginate">
			<ul class="pagination">
				<!-- 첫번째 페이지 -->
				<c:choose>
					<c:when test="${page == '1'}">
						<li class="paginate_button page-item previous disabled"
							id="bootstrap-data-table_previous"><a
							aria-controls="bootstrap-data-table" tabindex="0"
							class="page-link"><i class="fa fa-angle-double-left"></i></a></li>
					</c:when>
					<c:otherwise>
						<li id="bootstrap-data-table_previous"><a
							href="${cp}/userSelect?page=1&selCode=${selCode}&selText=${selText}"
							aria-controls="bootstrap-data-table" tabindex="0"
							class="page-link"><i class="fa fa-angle-double-left"></i></a></li>
					</c:otherwise>
				</c:choose>
	
				<!-- 페이지 -->
				<c:choose>
					<c:when test="${startPage == lastPageStartPage}">
						<%-- 마지막페이지가 해당되는 페이지의 시작페이지이면 다음로직 실행 --%>
						<c:forEach begin="${lastPageStartPage}" end="${lastPage}" var="i">
							<c:set var="active" value="" />
							<c:if test="${i == page}">
								<c:set var="active" value="paginate_button page-item active" />
							</c:if>
							<li class="${active}"><a
								href="${cp}/userSelect?page=${i}&selCode=${selCode}&selText=${selText}"
								aria-controls="bootstrap-data-table" data-dt-idx="1" tabindex="0"
								class="page-link">${i}</a></li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach begin="${startPage}" end="${endPage}" var="i">
							<c:set var="active" value="" />
							<c:if test="${i == page}">
								<c:set var="active" value="paginate_button page-item active" />
							</c:if>
							<li class="${active}"><a
								href="${cp}/userSelect?page=${i}&selCode=${selCode}&selText=${selText}"
								aria-controls="bootstrap-data-table" data-dt-idx="1" tabindex="0"
								class="page-link">${i}</a></li>
						</c:forEach>
					</c:otherwise>
				</c:choose>
	
				<!-- 마지막페이지 -->
				<c:choose>
					<c:when test="${page == (lastPage)}">
						<li class="paginate_button page-item next"
							id="bootstrap-data-table_next"><a
							aria-controls="bootstrap-data-table" tabindex="0"
							class="page-link"><i class="fa fa-angle-double-right"></i></a></li>
					</c:when>
					<c:otherwise>
						<li id="bootstrap-data-table_next"><a
							href="${cp}/userSelect?page=${lastPage}&selCode=${selCode}&selText=${selText}"
							aria-controls="bootstrap-data-table" tabindex="0"
							class="page-link"><i class="fa fa-angle-double-right"></i></a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</div>

<form id="frm" action="${cp}/userDetail" method="get">
	<input type="hidden" id="emp_sq" name="emp_sq" /> <input type="hidden"
		id="selCode" name="selCode" /> <input type="hidden" id="selText"
		name="selText" />
</form>

<form id="insFrm" action="${cp}/userInsert" method="get" class="form-horizontal" role="form">
</form>

<script>
	$(document).ready(function() {
		// input 포커스
		$("#inputText").focus();

		var selCode = 1;
		$("#selCode").val(selCode);

		// select 박스
		$("#selectBox").on("change", function() {
			//input 포커스
			$("#inputText").focus();

			//선택코드(이름, 사원번호, 부서명)
			console.log($("select[name=selector]").val());
			selCode = $("select[name=selector]").val();
			$("#selCode").val(selCode);
		});

		// 검색 버튼 클릭 시
		$("#btnSearch").on("click", function() {
			//input값
			console.log($("#inputText").val());
			var selText = $("#inputText").val();
			$("#selText").val(selText);

			//속성바꿀때 사용
			$("#frm").attr("action", "${cp}/userSelect");

			$("#frm").submit();
		});

		// 사원 정보 클릭 시 상세보기로 넘어감
		$(".empTr").on("click", function() {
			console.log("data-empsq : " + $(this).data("empsq"));
			var emp_sq = $(this).data("empsq");

			$("#emp_sq").val(emp_sq);
			$("#frm").submit();
		});
		
		// 사원 등록
		$("#insBtn").on("click", function() {
			$("#insFrm").submit();
		});
	});
</script>
