<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>사원 연락처</strong></h1>
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
					</select>
					<input id="SerchText" type="text" class="form-control" placeholder=""
						aria-controls="bootstrap-data-table">
					<button id="employeeContactSerch_btn" class="btn btn-info">
						<i class="fa fa-search"></i>
					</button>
				</div>
			</div>
		</div>
		<br><br>
		<table id="table" class="table text-center">
			<thead class="thead-dark">
				<tr>
					<th style="width: 100px;">사원번호</th>
					<th style="width: 200px;">이름</th>
					<th style="width: 200px;">부서</th>
					<th style="width: 200px;">직급</th>
					<th>휴대폰</th>
					<th>이메일</th>
					<c:if test="${sessionScope.authority == 1}">
						<th style="width: 80px;"></th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${PagingList }" var="paging">
					<tr class="employeeContactTr" data-emp_sq="${paging.emp_sq}">
						<td>${paging.emp_sq}</td>
						<td>${paging.emp_nm}</td>
						<td>${paging.dpt_nm }</td>
						<td>
							<c:choose>
								<c:when test="${paging.emp_grade == 1}">
									사장	
								</c:when>
								<c:when test="${paging.emp_grade == 2}">
									전무
								</c:when>
								<c:when test="${paging.emp_grade == 3}">
									이사	
								</c:when>
								<c:when test="${paging.emp_grade == 4}">
									부장	
								</c:when>
								<c:when test="${paging.emp_grade == 5}">
									과장	
								</c:when>
								<c:when test="${paging.emp_grade == 6}">
									팀장	
								</c:when>
								<c:when test="${paging.emp_grade == 7}">
									대리	
								</c:when>
								<c:when test="${paging.emp_grade == 8}">
									사원	
								</c:when>
							</c:choose>
						</td>
						<td>${paging.emp_phone}</td>
						<td>${paging.emp_psn_email}</td>
						<c:if test="${sessionScope.authority == 1}">
							<td>
								<button data-emp_sq="${paging.emp_sq }" type="button"
									class="insBtn btn btn-outline-secondary btn-sm col-sm-10"
									id="employeeContactModify">수정</button>
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="row">
			<div class="col-sm-12 col-md-7">
				<div class="dataTables_paginate paging_simple_numbers"
					id="bootstrap-data-table_paginate">
					<c:set var="lastPage" value="${lastpage}" />
					<ul class="pagination">
						<!-- 첫번째 페이지 -->
						<c:choose>
							<c:when test="${page == 1}">
								<li class="paginate_button page-item previous disabled"
									id="bootstrap-data-table_previous"><a
									aria-controls="bootstrap-data-table" tabindex="0"
									class="page-link"><i class="fa fa-angle-double-left"></i></a></li>
							</c:when>
							<c:otherwise>
								<li id="bootstrap-data-table_previous"><a
									href="${cp}/employeeContactMange?page=1"
									aria-controls="bootstrap-data-table" tabindex="0"
									class="page-link"><i class="fa fa-angle-double-left"></i></a></li>
							</c:otherwise>
						</c:choose>

						<!-- 페이지 -->
						<c:choose>
							<c:when test="${startPage == lastPageStartPage}">
								<%-- 마지막페이지가 해당되는 페이지의 시작페이지이면 다음로직 실행 --%>
								<c:forEach begin="${lastPageStartPage}" end="${lastPage}"
									var="i">
									<c:set var="active" value="" />
									<c:if test="${i == page}">
										<c:set var="active"
											value="paginate_button page-item active" />
									</c:if>
									<li class="${active}"><a
										href="${cp}/employeeContactMange?page=${i}"
										aria-controls="bootstrap-data-table" data-dt-idx="1"
										tabindex="0" class="page-link">${i}</a></li>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach begin="${startPage}" end="${endPage}" var="i">
									<c:set var="active" value="" />
									<c:if test="${i == page}">
										<c:set var="active"
											value="paginate_button page-item active" />
									</c:if>
									<li class="${active}"><a
										href="${cp}/employeeContactMange?page=${i}"
										aria-controls="bootstrap-data-table" data-dt-idx="1"
										tabindex="0" class="page-link">${i}</a></li>
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
									href="${cp}/employeeContactMange?page=${lastPage}"
									aria-controls="bootstrap-data-table" tabindex="0"
									class="page-link"><i class="fa fa-angle-double-right"></i></a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<form id="frm" action="${cp }/employee_modify" method="get">
	<input type="hidden" name="selCode" id="selCode"> <input
		type="hidden" name="emp_info" id="SerchText2"> <input
		type="hidden" name="emp_sq" id="emp_sq">
</form>

<script src="${cp}/js/data-table/jquery-2.1.4.min.js"
	type="2937cbea7ac98aca49c79d89-text/javascript"></script>
<script src="${cp}/js/data-table/datatables.min.js"
	type="2937cbea7ac98aca49c79d89-text/javascript"></script>
<script src="${cp}/js/data-table/dataTables.bootstrap.min.js"
	type="2937cbea7ac98aca49c79d89-text/javascript"></script>
<script src="${cp}/js/data-table/dataTables.buttons.min.js"
	type="2937cbea7ac98aca49c79d89-text/javascript"></script>
<script src="${cp}/js/data-table/buttons.bootstrap.min.js"
	type="2937cbea7ac98aca49c79d89-text/javascript"></script>
<script src="${cp}/js/data-table/jszip.min.js"
	type="2937cbea7ac98aca49c79d89-text/javascript"></script>
<script src="${cp}/js/data-table/pdfmake.min.js"
	type="2937cbea7ac98aca49c79d89-text/javascript"></script>
<script src="${cp}/js/data-table/vfs_fonts.js"
	type="2937cbea7ac98aca49c79d89-text/javascript"></script>
<script src="${cp}/js/data-table/buttons.html5.min.js"
	type="2937cbea7ac98aca49c79d89-text/javascript"></script>
<script src="${cp}/js/data-table/buttons.print.min.js"
	type="2937cbea7ac98aca49c79d89-text/javascript"></script>
<script src="${cp}/js/data-table/buttons.colVis.min.js"
	type="2937cbea7ac98aca49c79d89-text/javascript"></script>
<script src="${cp}/js/data-table/datatables-init.js"
	type="2937cbea7ac98aca49c79d89-text/javascript"></script>


<script type="2937cbea7ac98aca49c79d89-text/javascript">
</script>
<script>
	$(document).on("click", ".insBtn", function() {
		console.log($(this).data("emp_sq"));
		var emp_sq = $(this).data("emp_sq");
		$("#emp_sq").val(emp_sq);
		$("#frm").submit();
		//input 포커스
		$("#SerchText").focus();

		var selCode = 1;
		$("#selCode").val(selCode);

		//select박스
		$("#selectBox").on("change", function() {
			//input 포커스
			$("#SerchText").focus();
			console.log($("select[name=selector]").val())
			selCode = $("select[name=selector]").val();
			$("#selCode").val(selCode);
		});

		//검색버튼 클릭시
		// 			$("#employeeContactSerch_btn").on("click", function() {
		// 				//input값
		// 				console.log($("#SerchText").val());
		// 				var SerchText = $("#SerchText").val();
		// 				$("#SerchText2").val(SerchText);

		// 				//속성바꿀때 사용00
		// 				$("#frm").attr("method", "post");
		// 				$("#frm").attr("action", "${cp}/employeeContact_Serch");

		// 				$("#frm").submit();
		// 			})
	});

	$("#employeeContactSerch_btn").click(function() {
		//input값
		selCode = $("select[name=selector]").val();
		$("#selCode").val(selCode);

		console.log($("#SerchText").val());
		var SerchText = $("#SerchText").val();
		$("#SerchText2").val(SerchText);

		//속성바꿀때 사용00
		$("#frm").attr("method", "post");
		$("#frm").attr("action", "${cp}/employeeContact_Serch");

		$("#frm").submit();

		// 			$("#frm").submit();
	})
	// 		$(".employeeContactTr").on("click", function() {
	// 			console.log($(this).data("emp_sq"));
	// 			var emp_sq =$(this).data("emp_sq");

	// 			$("#emp_sq").val(emp_sq);
	// 			$("#employeeContactModify").submit();
	// 		});
</script>


<!-- Global site tag (gtag.js) - Google Analytics -->
<script async
	src="https://www.googletagmanager.com/gtag/js?id=UA-23581568-13"
	type="2937cbea7ac98aca49c79d89-text/javascript">
</script>
<script type="2937cbea7ac98aca49c79d89-text/javascript">
</script>
<script
	src="https://ajax.cloudflare.com/cdn-cgi/scripts/a2bd7673/cloudflare-static/rocket-loader.min.js"
	data-cf-settings="2937cbea7ac98aca49c79d89-|49" defer="">
</script>
