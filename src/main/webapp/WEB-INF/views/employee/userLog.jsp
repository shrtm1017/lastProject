<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<head>
<style type="text/css">
.ntc_nm_td a:LINK {
	color: black;
}

.ntc_nm_td a:HOVER {
	color: purple;
}

.ntc_nm_td a:ACTIVE {
	color: purple
}
</style>
</head>

<div class="breadcrumbs">
	<div class="col-sm-4">
		<div class="page-header float-left" id="page-title-style">
			<div class="page-title">
				<h1 style="padding-bottom: 0px;">
					<strong>로그인 기록</strong>
				</h1>
			</div>
		</div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4">
		<br>
		<div class="row">
			<div class="col-sm-12">
				<div class="col-md-1"></div>
				<table id="table" class="table text-center col-md-10">
					<thead>
						<tr>
							<th>접속일시</th>
							<th>종료일시</th>
							<th>접속한 ip</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach items="${selectLogList }" var="list">
							<c:choose>
								<c:when test="${list.log_end_dt != null}">
									<tr>
										<td>${list.log_str_dt }</td>
										<td>${list.log_end_dt }</td>
										<c:choose>
											<c:when test="${list.log_ip == '0:0:0:0:0:0:0:1' }">
												<td>localhost</td>
											</c:when>
											<c:otherwise>
												<td>${list.log_ip }</td>
											</c:otherwise>
										</c:choose>

									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<td>${list.log_str_dt }</td>
										<td>종료 미확인</td>
										<c:choose>
											<c:when test="${list.log_ip == '0:0:0:0:0:0:0:1' }">
												<td>localhost</td>
											</c:when>
											<c:otherwise>
												<td>${list.log_ip }</td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tbody>
				</table>
				<br>
				<div class="form-group">
					<div class="col-md-1"></div>
					<div class="row col-md-9">
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
											href="${cp}/logSelectList?page=1"
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
												<c:set var="active" value="paginate_button page-item active" />
											</c:if>
											<li class="${active}"><a
												href="${cp}/logSelectList?page=${i}"
												aria-controls="bootstrap-data-table" data-dt-idx="1"
												tabindex="0" class="page-link">${i}</a></li>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach begin="${startPage}" end="${endPage}" var="i">
											<c:set var="active" value="" />
											<c:if test="${i == page}">
												<c:set var="active" value="paginate_button page-item active" />
											</c:if>
											<li class="${active}"><a
												href="${cp}/logSelectList?page=${i}"
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
											href="${cp}/logSelectList?page=${lastPage}"
											aria-controls="bootstrap-data-table" tabindex="0"
											class="page-link"><i class="fa fa-angle-double-right"></i></a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
					</div>
					<div class="col-md-2" style="text-align: center;">
						<button type="button" class="btn btn-info">
							<a href="${cp}/userInfo" style="color: white;">내 정보</a>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
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
		$("#boardSerch").on("click", function() {
			//input값
			console.log($("#SerchText").val());
			var SerchText = $("#SerchText").val();
			$("#SerchText2").val(SerchText);

			//속성바꿀때 사용
			$("#frm").attr("method", "post");
			$("#frm").attr("action", "${cp}/notice_Serch");

			$("#frm").submit();
		})
	});

	$("#notice_register").click(function() {
		$("#frm").submit();
	})

	$(".ntc_nm").on("click", function() {
		var ntc_sq = $(this).attr('value');

		$("#ntc_sq").val(ntc_sq);
		$("#notice_detail").submit();
	});
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
