<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="breadcrumbs">
	<div class="col-sm-4">
		<div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>Gantt Chart</strong></h1>
			</div>
		</div>
	</div>
</div>

<form id="frm" action="${cp}/ganttConditionSelect" method="get">
	<div class="col-lg-12 col-sm-4"> <br>
	    <div class="card">
	        <div class="card-body">
	        	<div class="col col-md-12">
					<div class="col col-md-1">
						<label for="inline-checkbox1" class="form-check-label">
							<input type="checkbox" value="option1" class="form-check-input" checked disabled="disabled">상태
						</label>
					</div>
					
					<div class="col col-md-3">
						<select name="selectState">
							<option <c:if test="${sessionScope.selectState == '1'}">selected</c:if> value="1">진행중</option>
							<option <c:if test="${sessionScope.selectState == '2'}">selected</c:if> value="2">완료됨</option>
							<option <c:if test="${sessionScope.selectState == '3'}">selected</c:if> value="3">모두</option>
						</select>
					</div>
					
					<div class="col col-md-3">
						<label for="inline-checkbox1" class="form-check-label">
							검색조건 추가
						</label>
						
						<select name="selectCondition" style="width: 200px;">
							<option></option>
							<%-- 프로젝트가 없는 사원인 경우 프로젝트 검색조건 제외 --%>
							<c:if test="${sessionScope.notIn == null}">
								<option value="1">프로젝트</option>
							</c:if>
							<option value="2">유형</option>
							<option value="3">담당자</option>
						</select>
					</div>
					
					<div style="float: right;">
						<a href="${cp}/workInsert">
							<button type="button" class="btn btn-secondary">업무 등록</button>
						</a>
					</div>
				</div>
			
				<c:forEach items="${sessionScope.selConditionList}" var="selCondition">
					<c:choose>
						<c:when test="${selCondition == '1'}">
							<div class="col col-md-12">
								<div class="col col-md-1">
									<label for="inline-checkbox1" class="form-check-label">
										<input type="checkbox" value="option1" class="form-check-input" checked disabled="disabled">프로젝트
									</label>
								</div>
								
								<div class="col col-md-3">
									<select name="selectProjectState">
										<option <c:if test="${sessionScope.selectProjectState == '1'}">selected</c:if> value="1">이다</option>
										<option <c:if test="${sessionScope.selectProjectState == '2'}">selected</c:if> value="2">아니다</option>
									</select>
								</div>
								
								<div class="col col-md-3">
									<select name="selectProject" style="width: 292px;">
										<c:forEach items="${sessionScope.projectList}" var="project">
											<option <c:if test="${sessionScope.pro_sq == project.pro_sq}">selected</c:if> value="${project.pro_sq}">${project.pro_nm}</option>
										</c:forEach>
									</select>
								</div>
								
							</div>
						</c:when>
						
						<c:when test="${selCondition == '2'}">
							<div class="col col-md-12">
								<div class="col col-md-1">
									<label for="inline-checkbox1" class="form-check-label">
										<input type="checkbox" value="option1" class="form-check-input" checked disabled="disabled">유형
									</label>
								</div>
								
								<div class="col col-md-3">
									<select name="selectTypeState">
										<option <c:if test="${sessionScope.selectTypeState == '1'}">selected</c:if> value="1">이다</option>
										<option <c:if test="${sessionScope.selectTypeState == '2'}">selected</c:if> value="2">아니다</option>
									</select>
								</div>
								
								<div class="col col-md-3">
									<select name="selectType" style="width: 292px;">
										<option <c:if test="${sessionScope.wk_type == '새기능'}">selected</c:if> value="새기능">새기능</option>
										<option <c:if test="${sessionScope.wk_type == '결함'}">selected</c:if> value="결함">결함</option>
										<option <c:if test="${sessionScope.wk_type == '지원'}">selected</c:if> value="지원">지원</option>
										<option <c:if test="${sessionScope.wk_type == '공지'}">selected</c:if> value="공지">공지</option>
									</select>
								</div>
							</div>
						</c:when>
						
						<c:when test="${selCondition == '3'}">
							<div class="col col-md-12">
								<div class="col col-md-1">
									<label for="inline-checkbox1" class="form-check-label ">
										<input type="checkbox" value="option1" class="form-check-input" checked disabled="disabled">담당자
									</label>
								</div>
								
								<div class="col col-md-3">
									<select name="selectChargerState">
										<option <c:if test="${sessionScope.selectChargerState == '1'}">selected</c:if> value="1">이다</option>
										<option <c:if test="${sessionScope.selectChargerState == '2'}">selected</c:if> value="2">아니다</option>
									</select>
								</div>
								
								<div class="col col-md-3">
									<select name="selectCharger" style="width: 292px;">
										<option <c:if test="${sessionScope.wk_emp_sq == employee.emp_sq}">selected</c:if> value="${sessionScope.employeeVo.emp_sq}">나</option>
										<c:forEach items="${sessionScope.employeeList}" var="employee">
											<c:if test="${employee.emp_sq != sessionScope.employeeVo.emp_sq}">
												<option <c:if test="${sessionScope.wk_emp_sq == employee.emp_sq}">selected</c:if> value="${employee.emp_sq}">${employee.emp_nm}</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</div>
						</c:when>
					</c:choose>
				</c:forEach>
				
				<div class="col col-md-12" style="margin-top: 15px; margin-bottom: 15px;">
					<div class="input-group">
						<button id="btnSelect" type="button" class="btn btn-sm btn-info col-sm-1">적용</button>
						&nbsp;
						<button id="btnDelete" type="button" class="btn btn-sm btn-secondary col-sm-1">지우기</button>
					</div>
				</div>
				
				<div class="col col-md-12">
					<table border="1">
						<tbody>
							<tr>
								<td style="background-color: #6c757d;" rowspan="2">
									<div style="width: 400px;">
									</div>
								</td>
								<td colspan="${lastDay}">
									<div style="width: 900px;" align="center">
										<%-- 해당월을 출력하며 연월을 형식에 맞게 세팅 --%>
										${month}
										<c:set var="yearmonth" value="${year}-${month}"/>
									</div>
								</td>
							</tr>
							<tr>
								<%-- 해당월의 일수를 label로 출력하며 중앙정렬 --%>
								<c:forEach begin="1" end="${lastDay}" varStatus="i">
									<td align="center">
										<label style="width: 24px; margin: 0px; padding: 0px;">${i.index}</label>
									</td>
								</c:forEach>
							</tr>
							<tr>
								<td>
									<div style="width: 400px; height: ${(fn:length(projectList) + fn:length(ganttMapList)) * 50}px;">
										<%-- 프로젝트 정보 --%>
										<c:forEach items="${projectList}" var="project">
											<label><i class="fa fa-archive"></i> ${project.pro_nm}</label>
											<br>
											<%-- 해당프로젝트번호가 같으면 업무정보 출력 --%>
											<c:forEach items="${ganttMapList}" var="ganttMap">
												<c:if test="${project.pro_sq == ganttMap.PRO_SQ}">
													<%-- level을 가지고 계층형으로 표시 --%>
													<label>
														<c:set var="level" value="${ganttMap.WK_LEVEL}"/>
														<c:forEach begin="1" end="${level}">
															&nbsp;&nbsp;
														</c:forEach>
														
														<i class="fa fa-file-text-o"></i>&nbsp;
														
														<%-- 시작날짜와 오늘날짜를 포맷팅하여 세팅 --%>
														<fmt:formatDate var="wkstrdt" value="${ganttMap.WK_STR_DT}" pattern="yyyy-MM-dd"/>
														<fmt:formatDate var="nowdate" value="${nowDate}" pattern="yyyy-MM-dd"/>
														
														<%-- 제목 글자수는 공백포함 15글자로 제한하여 출력 --%>
														<%-- 월을 비교하여 오늘월-시작월이 음수이면 시작전 아니면 이미 시작됨 --%>
														<%-- 월을 비교하여 오늘월-시작월이 0이고, 일자를 비교하여 오늘날짜-시작날짜가 음수이면 시작전 양수이면 이미 시작됨 --%>
														<c:choose>
															<c:when test="${(fn:substring(nowdate, 5, 7) - fn:substring(wkstrdt, 5, 7) <= 0) && (fn:substring(nowdate, 8, 10) - fn:substring(wkstrdt, 8, 10) < 0)}">
																<a href="${cp}/workDetail?wk_sq=${ganttMap.WK_SQ}" style="color: green;">${ganttMap.WK_TYPE} #${ganttMap.WK_SQ}</a>
																: ${fn:substring(ganttMap.WK_NM, 0, 15)}
															</c:when>
															<c:otherwise>
																<a href="${cp}/workDetail?wk_sq=${ganttMap.WK_SQ}" style="color: red;">${ganttMap.WK_TYPE} #${ganttMap.WK_SQ}</a>
																: ${fn:substring(ganttMap.WK_NM, 0, 15)}																
															</c:otherwise>
														</c:choose>
													</label>
													<br>
												</c:if>
											</c:forEach>
										</c:forEach>
									</div>
								</td>
								<td colspan="${lastDay}">
									<div style="width: 900px; height: ${(fn:length(projectList) + fn:length(ganttMapList)) * 50}px;">
										<%-- 프로젝트 정보 --%>
										<c:forEach items="${projectList}" var="project">
											<label>&nbsp;</label>
											<br>
											<%-- 해당프로젝트번호가 같으면 업무정보 출력 --%>
											<c:forEach items="${ganttMapList}" var="ganttMap">
												<c:if test="${project.pro_sq == ganttMap.PRO_SQ}">
													<%-- 날짜비교하여(시작날짜가 해당년월이면) --%>
													<c:if test="${yearmonth == fn:substring(ganttMap.WK_STR_DT, 0, 7)}">
														<%-- 시작날짜 세팅 --%>
														<c:set var="stDate" value="${fn:substring(ganttMap.WK_STR_DT, 8, 10)}"/>
														
														<%-- 종료예상일자가 해당년월이면 종료날짜 세팅하고 아니면 해당년월의 마지막날짜를 종료날짜에 세팅 --%>
														<c:choose>
															<c:when test="${yearmonth == fn:substring(ganttMap.WK_END_DT, 0, 7)}">
																<c:set var="edDate" value="${fn:substring(ganttMap.WK_END_DT, 8, 10)}"/>
															</c:when>
															<c:otherwise>
																<c:set var="edDate" value="${lastDay}"/>
															</c:otherwise>
														</c:choose>
														
														<%-- 시작날짜와 종료날짜 사이의 일수 + 1을 해야 시작날짜부터 종료날짜까지 날짜임 --%>
														<c:set var="betweenDate" value="${edDate - stDate + 1}"/>
														
														<%-- 진행바 넓이는 사이의 일수 * 고정크기29px, margin-left는 시작일수-1 * 고정크기29px를 줘야 해당시작일부터 표시됨 --%>
														<%-- 높이와 margin-bottom은 업무라벨에 맞춤 --%>
														<div class="progress" style="width: ${betweenDate * 29}px; margin-left: ${(stDate-1) * 29}px; color: #6c757d; margin-bottom: 8px; height: 24px;">
															<div class="progress-bar bg-success" role="progressbar" aria-valuenow="${ganttMap.WK_PROGRESS}" aria-valuemin="0" aria-valuemax="100"
																 style="width: ${ganttMap.WK_PROGRESS}%;">
																${ganttMap.WK_PROGRESS}%
															</div>
															
															<%-- 해당업무 정보를 가지고 있는 span이며 위치는 absolute로해야 종속되지 않고 처음상태는 hide 상태 --%>
															<%-- border 속성으로 두께와 선을 설정하여 테두리 표시 --%>
															<%-- 정보가 담겨있는 span 처음상태는 display none으로  숨기기 --%>
															<span class="progressSpan" style="position: absolute; width: 250px; height: 130px; background-color: white; border-color: #6c757d; border: 1px solid; display: none;">
																<a href="${cp}/workDetail?wk_sq=${ganttMap.WK_SQ}" style="color: #007bff;">${ganttMap.WK_TYPE} #${ganttMap.WK_SQ}</a>
																: ${fn:substring(ganttMap.WK_NM, 0, 15)} <br><br>
																<strong>프로젝트</strong> : ${ganttMap.PRO_NM} <br>
																<strong>상태</strong> : ${ganttMap.WK_STATE} <br>
																<strong>시작시간</strong> : <fmt:formatDate value="${ganttMap.WK_STR_DT}" pattern="yyyy-MM-dd"/> <br>
																<strong>완료기한</strong> : <fmt:formatDate value="${ganttMap.WK_END_DT}" pattern="yyyy-MM-dd"/> <br>
																<strong>담당자</strong> : ${ganttMap.EMP_NM} <br>
															</span>
														</div>
													</c:if>
													
													<%-- 날짜비교하여(시작날짜가 해당년월이 아니면 - 지난날짜이거나 미래의 날짜이면) --%>
													<c:if test="${yearmonth != fn:substring(ganttMap.WK_STR_DT, 0, 7)}">
														<%-- 높이와 margin-bottom은 업무라벨에 맞춤 --%>
														<div class="progress" style="width: 30px; margin-left: 0px; color: #6c757d; margin-bottom: 8px; height: 24px;">
															<div class="progress-bar bg-success" role="progressbar" aria-valuenow="${ganttMap.WK_PROGRESS}" aria-valuemin="0" aria-valuemax="100"
																 style="width: 0%;">
																${ganttMap.WK_PROGRESS}%
															</div>
															
															<%-- 해당업무 정보를 가지고 있는 span이며 위치는 absolute로해야 종속되지 않고 처음상태는 hide 상태 --%>
															<%-- border 속성으로 두께와 선을 설정하여 테두리 표시 --%>
															<%-- 정보가 담겨있는 span 처음상태는 display none으로  숨기기 --%>
															<span class="progressSpan" style="position: absolute; width: 250px; height: 130px; background-color: white; border-color: #6c757d; border: 1px solid; display: none;">
																<a href="${cp}/workDetail?wk_sq=${ganttMap.WK_SQ}" style="color: #007bff;">${ganttMap.WK_TYPE} #${ganttMap.WK_SQ}</a>
																: ${fn:substring(ganttMap.WK_NM, 0, 15)} <br><br>
																<strong>프로젝트</strong> : ${ganttMap.PRO_NM} <br>
																<strong>상태</strong> : ${ganttMap.WK_STATE} <br>
																<strong>시작시간</strong> : <fmt:formatDate value="${ganttMap.WK_STR_DT}" pattern="yyyy-MM-dd"/> <br>
																<strong>완료기한</strong> : <fmt:formatDate value="${ganttMap.WK_END_DT}" pattern="yyyy-MM-dd"/> <br>
																<strong>담당자</strong> : ${ganttMap.EMP_NM} <br>
															</span>
														</div>
													</c:if>
												</c:if>
											</c:forEach>
										</c:forEach>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					
				</div>
			</div>	
	    </div>
	</div>
</form>

<script>
	$(document).ready(function() {
		//상태 select박스
		$("select[name=selectState]").on("change", function() {
			//선택코드(진행중, 이다, 아니다, 완료됨, 모두)
			console.log($("select[name=selectState]").val());
		});

		//검색조건 리스트를 배열에 넣어서 표현해야함(el을 for문으로 조작하려면)
		var selConditionArray = new Array();

		<c:forEach items="${sessionScope.selConditionList}" var="selCondition">
			console.log("${selCondition}");
			selConditionArray.push("${selCondition}");
		</c:forEach>

		for (var i = 0; i < selConditionArray.length; i++) {
			$("select[name=selectCondition] option[value='" + selConditionArray[i] + "']").prop("disabled", true);
		}

		//검색조건 select박스
		$("select[name=selectCondition]").on("change", function() {
			//선택코드(프로젝트, 유형, 담당자)
			console.log($("select[name=selectCondition]").val());
			var selCondition = $("select[name=selectCondition]").val();

			if (selCondition != "") {
				$("#frm").submit();
			}
		});

		//적용버튼 이벤트
		$("#btnSelect").on("click", function() {
			//속성바꿀때 사용
			$("#frm").attr("action", "${cp}/ganttConditionWorkSelect");
			$("#frm").submit();
		});

		//지우기버튼 이벤트
		$("#btnDelete").on("click", function() {
			//속성바꿀때 사용
			$("#frm").attr("action", "${cp}/ganttConditionDelete");
			$("#frm").submit();
		});
		
		//마우스오버 이벤트
		$(".progress").mouseenter(function(){
			//진행바에 마우스가 올라가면 정보가 담겨있는 span 보이기
			$(this).find("span.progressSpan").show();
			console.log("마우스 들어옴");
		});
		
		//마우스리브 이벤트
		$(".progress").mouseleave(function(){
			//진행바에 마우스가 벗어나면 정보가 담겨있는 span 숨기기
			$(this).find("span.progressSpan").hide();
			console.log("마우스 나감");
		});
	});
	
</script>