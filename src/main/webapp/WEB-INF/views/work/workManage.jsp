<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="breadcrumbs">
	<div class="col-sm-4">
		<div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>내 업무 관리</strong></h1>
			</div>
		</div>
	</div>
</div>

<form id="frm" action="${cp}/conditionSelect" method="get">
<div>
	<div class="col-lg-12 col-sm-4"> <br>
	    <div class="card">
	        <div class="card-body">
	            <div class="custom-tab">
	                <nav>
	                    <div class="nav nav-tabs" id="nav-tab" role="tablist">
	                    	<!-- c:if태그로 탭 활성화 판단 -->
	                        <a href="#custom-nav-project" class="nav-item nav-link <c:if test="${tabCode == '1'}">active show</c:if>" data-toggle="tab" role="tab" aria-selected="true">프로젝트</a>
	                        <a href="#custom-nav-work" class="nav-item nav-link <c:if test="${tabCode == '2'}">active show</c:if>" data-toggle="tab" role="tab" aria-selected="false">작업내역</a>
	                        <a href="#custom-nav-condition" class="nav-item nav-link <c:if test="${tabCode == '3'}">active show</c:if>" data-toggle="tab" role="tab" aria-selected="false">일감</a>
	                    </div>
	                </nav>
	                <div class="tab-content pl-3 pt-2" id="nav-tabContent">
	                    <div class="tab-pane fade <c:if test="${tabpaneCode == '1'}">active show</c:if>" id="custom-nav-project" role="tabpanel">
	                        <div class="col col-md-12">
								<c:forEach items="${sessionScope.projectList}" var="project">
									<a href="${cp}/projectDetail?pro_sq=${project.pro_sq}">
										<button id="btnProject" type="button" class="btn btn-link">
											<i class="fa fa-link"></i>&nbsp; ${project.pro_nm}
										</button>
									</a>
									<br>
								</c:forEach>
								
								<!-- 관리자인 경우에만 프로젝트 생성메뉴가 있음 -->
								<c:if test="${sessionScope.authority == '1'}">
									<div style="margin-top: 15px; margin-bottom: 10px;">
										<a href="${cp}/projectInsert">
											<button type="button" class="btn btn-secondary">
												<i class="fa fa-magic"></i>
												프로젝트 생성
											</button>
										</a>
									</div>
								</c:if>
							</div>
	                    </div>
	                    <div class="tab-pane fade <c:if test="${tabpaneCode == '2'}">active show</c:if>" id="custom-nav-work" role="tabpanel">
	                        <%-- map으로 이루어진 list는 .키값(대문자임)으로 접근하여 사용함!!(중요) --%>
	                        <c:forEach items="${workHistoryMapList}" var="workHistoryMap" varStatus="status">
	                        	<%-- date 포맷팅하여 보여주기(작업내역 관련) --%>
	                        	<%-- 인덱스가 첫번째인경우 년월일 출력하고 첫번째값이 아니면 이전인덱스 년월일과 현재인덱스 년월일을 비교해서 같지않을때만 년월일 출력 --%>
	                        	<c:choose>
	                        		<c:when test="${status.index == 0}">
	                        			<h3><fmt:formatDate value="${workHistoryMap.HIS_DT}" pattern="yyyy-MM-dd"/></h3>
	                        		</c:when>
	                        		<c:otherwise>
	                        			<c:if test="${fn:substring(workHistoryMapList[status.index-1].HIS_DT, 0, 10) != fn:substring(workHistoryMapList[status.index].HIS_DT, 0, 10)}">
				                        	<h3><fmt:formatDate value="${workHistoryMap.HIS_DT}" pattern="yyyy-MM-dd"/></h3>
	                        			</c:if>
	                        		</c:otherwise>
	                        	</c:choose>
		                        <h6>
			                        <strong style="color: #008299;"><fmt:formatDate value="${workHistoryMap.HIS_DT}" pattern="HH:mm"/></strong>
			                        ${workHistoryMap.PRO_NM}
			                        -
			                        <a href="${cp}/workDetail?wk_sq=${workHistoryMap.WK_SQ}"><span class="badge badge-secondary">${workHistoryMap.WK_TYPE}#${workHistoryMap.HIS_WK_SQ}</span></a>
			                        :
		                        	${workHistoryMap.HIS_NM}
		                        </h6>
		                        <strong>${workHistoryMap.EMP_NM}</strong>
		                        <label style="color: #5D5D5D;">${workHistoryMap.HIS_CON}</label>
		                        <hr style="margin-top: 0px;">
	                        </c:forEach>
	                    </div>
	                    <div class="tab-pane fade <c:if test="${tabpaneCode == '3'}">active show</c:if>" id="custom-nav-condition" role="tabpanel">
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
							
							<div class="col-md-12" style="margin-top: 15px; margin-bottom: 15px;">
								<div class="input-group">
									<button id="btnSelect" type="button" class="btn btn-sm btn-info col-sm-1">적용</button>
									&nbsp;
									<button id="btnDelete" type="button" class="btn btn-sm btn-secondary col-sm-1">지우기</button>
								</div>
							</div>
							
							<div class="col col-md-12">
								<table id="" class="table dataTable no-footer text-center"
									role="grid" aria-describedby="bootstrap-data-table_info">
									<thead>
										<tr role="row">
											<th>No.</th>
											<th>프로젝트</th>
											<th>유형</th>
											<th>상태</th>
											<th>제목</th>
											<th>담당자</th>
											<th>변경일시</th>
										</tr>
									</thead>
									<tbody>
										<%-- map으로 이루어진 list는 .키값(대문자임)으로 접근하여 사용함!!(중요) --%>
										<c:forEach items="${sessionScope.workMapList}" var="workMap">
											<tr class="workTr" data-worksq="${workMap.WK_SQ}" role="row">
												<td>${workMap.WK_SQ}</td>
												<td>${workMap.PRO_NM}</td>
												<td>${workMap.WK_TYPE}</td>
												<td>${workMap.WK_STATE}</td>
												<td>${workMap.WK_NM}</td>
												<td>${workMap.EMP_NM}</td>
												<td><fmt:formatDate value="${workMap.WK_UPD_DT}" pattern="yyyy-MM-dd HH:mm"/></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<%-- 업무정보 --%>
								<input name="wk_sq" type="hidden"/>
								
								<%-- 페이지네이션 --%>
								<div class="col-sm-12 col-md-7">
									<div class="dataTables_paginate paging_simple_numbers"
										id="bootstrap-data-table_paginate">
										<ul class="pagination">
											<!-- 첫번째 페이지 -->
											<c:choose>
												<c:when test="${page == 1}">
													<li class="paginate_button page-item previous disabled" id="bootstrap-data-table_previous">
														<button name="page" aria-controls="bootstrap-data-table" tabindex="0" class="page-link"><i class="fa fa-angle-double-left"></i></button>
													</li>
												</c:when>
												<c:otherwise>
													<li id="bootstrap-data-table_previous">
														<button name="page" value="1" aria-controls="bootstrap-data-table" tabindex="0" class="page-link"><i class="fa fa-angle-double-left"></i></button>
													</li>
												</c:otherwise>
											</c:choose>
											
											<!-- 페이지 -->
											<c:choose>
												<c:when test="${startPage == lastPageStartPage}"> <%-- 마지막페이지가 해당되는 페이지의 시작페이지이면 다음로직 실행 --%>
													<c:forEach begin="${lastPageStartPage}" end="${lastPage}" var="i">
														<c:set var="active" value="" />
														<c:if test="${i == page}">
															<c:set var="active" value="paginate_button page-item active" />
														</c:if>
														<li class="${active}">
															<button name="page" value="${i}" aria-controls="bootstrap-data-table" tabindex="0" class="page-link">${i}</button>
														</li>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach begin="${startPage}" end="${endPage}" var="i">
														<c:set var="active" value="" />
														<c:if test="${i == page}">
															<c:set var="active" value="paginate_button page-item active" />
														</c:if>
														<li class="${active}">
															<button name="page" value="${i}" aria-controls="bootstrap-data-table" tabindex="0" class="page-link">${i}</button>
														</li>
													</c:forEach>
												</c:otherwise>
											</c:choose>
											
											<!-- 마지막페이지 -->
											<c:choose>
												<c:when test="${page == lastPage}">
													<li class="paginate_button page-item next disabled" id="bootstrap-data-table_next">
														<button name="page" aria-controls="bootstrap-data-table" tabindex="0" class="page-link"><i class="fa fa-angle-double-right"></i></button>
													</li>
												</c:when>
												<c:otherwise>
													<li id="bootstrap-data-table_next">
														<button name="page" value="${lastPage}" aria-controls="bootstrap-data-table" tabindex="0" class="page-link"><i class="fa fa-angle-double-right"></i></button>
													</li>
												</c:otherwise>
											</c:choose>
										</ul>
									</div>
								</div>
							</div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
</div>
</form>

<script>
	$(document).ready(function(){
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

		for(var i=0; i<selConditionArray.length; i++){
			$("select[name=selectCondition] option[value='"+ selConditionArray[i] +"']").prop("disabled", true);
		}
		
		//검색조건 select박스
		$("select[name=selectCondition]").on("change", function() {
			//선택코드(프로젝트, 유형, 담당자)
			console.log($("select[name=selectCondition]").val());
			var selCondition = $("select[name=selectCondition]").val();
			
			if(selCondition != ""){
				$("#frm").submit();
			}
		});
		
		//적용버튼 이벤트
		$("#btnSelect").on("click", function(){
			//속성바꿀때 사용
			$("#frm").attr("action", "${cp}/conditionWorkSelect");
			$("#frm").submit();
		});
		
		//지우기버튼 이벤트
		$("#btnDelete").on("click", function(){
			//속성바꿀때 사용
			$("#frm").attr("action", "${cp}/conditionDelete");
			$("#frm").submit();
		});
		
		//업무정보 클릭시 상세보기로 넘어감
		$(".workTr").on("click", function() {
			console.log("data-worksq : " + $(this).data("worksq"));
			var wk_sq = $(this).data("worksq");

			$("input[name=wk_sq]").val(wk_sq);
			
			//속성바꿀때 사용
			$("#frm").attr("action", "${cp}/workDetail");
			$("#frm").submit();
		});
		
		//페이지 이벤트
		$("button[name=page]").on("click", function(){
			$("#frm").attr("action", "${cp}/conditionWorkSelect");
			$("#frm").submit();
		});
		
	});
</script>
