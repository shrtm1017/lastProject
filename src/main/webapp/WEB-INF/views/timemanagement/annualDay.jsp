<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>근태 관리</strong></h1>
	        </div>
	    </div>
	</div>
</div>

<div class="content">
	<div class="content mt-3">
		<div class="animated fadeIn">
			<div class="row">
				<div class="col-sm-12">
					<div class="card">
						<div class="card-header" style="background-color: white;">
							<a href="${cp }/CmtLookup">출퇴근 내역</a>&nbsp;&nbsp;
							<a href="${cp }/annualDay" style="color: navy;"><strong>연차 지급 내역</strong></a>&nbsp;&nbsp;
							<a href="${cp }/Use_annualDay_List">연차 사용 내역</a>
							
							<c:if test="${sessionScope.authority == 1 }">
								<button id="cmtSearch" class="btn btn-info btn-sm" style="float: right;">연차 추가</button>
							</c:if>
						</div>
						<div class="card-body">
							<div class="col-md-1"></div>
							<table id="table" class="table col-md-10 text-center">
								<thead>
									<tr>
										<th style="width: 200px;">지급일자</th>
										<th>지급일수</th>
										<th>남은일수</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${listAnnual }" var="listAnnual">
										<tr>
											<td>${listAnnual.anu_give_dt }</td>
											<td>${listAnnual.anu_days }</td>
											<td>${listAnnual.anu_remains }</td>
										</tr>
										<!-- 내용 -->
									</c:forEach>
								</tbody>
							</table>
							<br>
							<div class="col-sm-1"></div>
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
														href="${cp}/annualDay?page=1"
														aria-controls="bootstrap-data-table" class="page-link"><i class="fa fa-angle-double-left"></i></a></li>
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
															href="${cp}/annualDay?page=${i}"
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
															href="${cp}/annualDay?page=${i}"
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
														href="${cp}/annualDay?page=${lastPage}"
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
				</div>
			</div>
		</div>
	</div>
</div>

<form id="frm" action="CmtSearchLookup" method="get"></form>


<script>
	$(document).ready(function() {

		//검색버튼 클릭시
		$("#cmtSearch").on("click", function() {
			//input값

			//속성바꿀때 사용
			$("#frm").attr("method", "get");
			$("#frm").attr("action", "/annualAdd");

			$("#frm").submit();
		});
	});
</script>

<!-- 				<script src="/js/data-table/jquery-2.1.4.min.js" -->
<!-- 			type="text/javascript"></script> -->
<!-- 		<script src="/js/data-table/datatables.min.js" type="text/javascript"></script> -->
<!-- 		<script src="/js/data-table/dataTables.bootstrap.min.js" -->
<!-- 			type="text/javascript"></script> -->
<!-- 		<script src="/js/data-table/dataTables.buttons.min.js" -->
<!-- 			type="text/javascript"></script> -->
<!-- 		<script src="/js/data-table/buttons.bootstrap.min.js" -->
<!-- 			type="text/javascript"></script> -->
<!-- 		<script src="/js/data-table/jszip.min.js" type="text/javascript"></script> -->
<!-- 		<script src="/js/data-table/pdfmake.min.js" type="text/javascript"></script> -->
<!-- 		<script src="/js/data-table/vfs_fonts.js" type="text/javascript"></script> -->
<!-- 		<script src="/js/data-table/buttons.html5.min.js" -->
<!-- 			type="text/javascript"></script> -->
<!-- 		<script src="/js/data-table/buttons.print.min.js" -->
<!-- 			type="text/javascript"></script> -->
<!-- 		<script src="/js/data-table/buttons.colVis.min.js" -->
<!-- 			type="text/javascript"></script> -->
<!-- 		<script src="/js/data-table/datatables-init.js" type="text/javascript"></script> -->


<!-- Global site tag (gtag.js) - Google Analytics -->
<!-- 		<script async="" -->
<!-- 			src="https://www.googletagmanager.com/gtag/js?id=UA-23581568-13" -->
<!-- 			type="text/javascript"></script> -->