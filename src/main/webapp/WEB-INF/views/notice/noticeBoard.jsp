<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<head>
<style type="text/css">

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

				<c:if test="${board_div == 1 }">
					<h1 style="padding-bottom: 0px;">
						<strong>공지사항</strong>
					</h1>
				</c:if>
				<c:if test="${board_div == 2 }">
					<h1 style="padding-bottom: 0px;">
						<strong>경조사</strong>
					</h1>
						
				</c:if>
				<c:if test="${board_div == 3 }">
					<h1 style="padding-bottom: 0px;">
						<strong>승진인사</strong>
					</h1>
				</c:if>

			</div>
		</div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4">
		<br>
		<div>
			<div class="col-sm-2"></div>
			<div class="col-sm-8">
				<div class="input-group">
					<select id="selectBox" name="selector"
						class="form-control col-sm-2">
						<option value="1">제목</option>
					</select> <input id="SerchText" type="text" class="form-control"
						placeholder="" aria-controls="bootstrap-data-table">
					<button id="boardSerch" class="btn btn-info" style="border-radius: 5px;">
						<i class="fa fa-search"></i>
					</button>
				</div>
			</div>
			<div class="col-sm-1">
				<c:if test="${sessionScope.authority == 1 && board_div == 1}">
					<button type="button" class="btn btn-secondary" style="border-radius: 5px;"
						id="notice_register ">글쓰기</button>
				</c:if>
				<c:if test="${sessionScope.authority == 1 && board_div == 3}">
					<button type="button" class="btn btn-secondary" style="border-radius: 5px;"
						id="notice_register">글쓰기</button>
				</c:if>
				<c:if test="${board_div == 2}">
					<button type="button" class="btn btn-secondary" style="border-radius: 5px;"
						id="notice_register">글쓰기</button>
				</c:if>
			</div>
			<br> <br> <br>
			<div class="form-group">
				<div class="col-sm-1"></div>
				
				<table id="table" class="table col-sm-10">
					<thead style="background-color: #002266";>
						<tr>
							<th scope="row"
								style="width: 60px; color: white">번호</th>
							<th
								style="text-align: center; color: white">제목</th>
							<th
								style="width: 100px; text-align: center; color: white">등록일</th>
							<th
								style="width: 100px; text-align: center; color: white">등록자</th>
							<th
								style="width: 80px; text-align: center; color: white">조회</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${NoticeList }" var="NoticeList">
							<tr>
								<td>${NoticeList.ntc_sq }</td>
								<td class="ntc_nm_td"><a href="#" class="ntc_nm"
									value="${NoticeList.ntc_sq }">${NoticeList.ntc_nm }</a></td>
								<td><fmt:formatDate value="${NoticeList.ntc_dt }"
										pattern="yyyy/MM/dd" /></td>
								<td style="text-align: center;">${NoticeList.emp_nm }</td>
								<td style="text-align: center;">${NoticeList.ntc_hits }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<br>
			<div class="col-sm-1"></div>

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
									href="${cp}/noticeBoard?page=1&ntc_div=${board_div}"
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
										href="${cp}/noticeBoard?page=${i}&ntc_div=${board_div}"
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
										href="${cp}/noticeBoard?page=${i}&ntc_div=${board_div}"
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
									href="${cp}/noticeBoard?page=${lastPage}&ntc_div=${board_div}"
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

<form id="frm" action="${cp }/notice_register" method="get">
	<input type="hidden" name="ntc_div" value="${board_div}"> <input
		type="hidden" id="selCode" name="selCode" /> <input type="hidden"
		id="SerchText2" name="ntc_info" />
</form>
<form id="notice_detail" action="${cp }/detail" method="get">
	<input type="hidden" id="ntc_sq" name="ntc_sq"> <input
		type="hidden" name="ntc_div" value="1">
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
		// 		console.log($(this).data("ntc_sq"));
		// 		var ntc_sq = $(this).data("ntc_sq");

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
