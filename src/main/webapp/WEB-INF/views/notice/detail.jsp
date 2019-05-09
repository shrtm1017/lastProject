<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<style type="text/css">
.filebox a {
	cursor: pointer
}

.filebox .hide {
	display: none;
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

<div class="content mt-3">
	<div class="row">
		<div style="float: right; width: 80%">
			<div class="col-md-12">
				<div class="col-sm-12">
					<div class="form-group" style="margin-bottom: 0px;">
						<strong style="font-size: 20px;">${NoticeVo.ntc_nm }</strong> <label
							style="color: #5D5D5D; float: right; font-size: 14px;"> <fmt:formatDate
								value="${ NoticeVo.ntc_dt }" pattern="yyyy/MM/dd  hh:mm" />
						</label>
					</div>
					<!-- <strong style="float: left; font-size: 20px;">도현아</strong> -->
					<!-- 컨텐츠 정보를 화면에 출력하는 로직 작성 -->
					<div class="col-md-12"
						style="padding-left: 0px; padding-right: 0px;">
						<hr style="padding-top: 0px;">
						<span><img class="rounded-circle"
							style="width: 30px; height: 30px;"
							src="${cp }/userImg?emp_sq=${NoticeVo.ntc_emp_sq }"
							alt="Card image cap">&nbsp;&nbsp;<strong>${NoticeVo.emp_nm}(${NoticeVo.ntc_emp_sq})</strong></span>

					</div>
					<div class="filebox text-right" style="float: right;">
						<c:if test="${NoticeVo.ntc_fl_nm != null }">
							<a herf="#">첨부파일</a>
							<div class="hide">
								<a href="${cp}/NoticeDownload?ntc_sq=${NoticeVo.ntc_sq}">${NoticeVo.ntc_fl_nm}</a>
							</div>
							<br>
						</c:if>
					</div>
					<div style="padding-top: 100px;" class="sys-container sys-detail-container sys-post-contents">${NoticeVo.ntc_con }</div>


				</div>
			</div>
			<!-- 댓글 시작 -->
			<div id="cmt_start" class="box1" style="margin-top: 200px;">

				<c:if test="${sessionScope.authority == 1 && board_div == 1}">
					<div>
						<button type="button" id="btn_modify" class="btn btn-outline-secondary btn-sm col-sm-1">수정</button>
						<button type="button" id="btn_delete" class="btn btn-outline-secondary btn-sm col-sm-1">삭제</button>
						
					</div>
				</c:if>
								<c:if test="${sessionScope.authority == 1 && board_div == 3}">
					<div>
						<button type="button" id="btn_modify" class="btn btn-outline-secondary btn-sm col-sm-1">수정</button>
						<button type="button" id="btn_delete" class="btn btn-outline-secondary btn-sm col-sm-1">삭제</button>
						
					</div>
				</c:if>
				<c:if test="${emp_sq == NoticeVo.ntc_emp_sq && board_div == 2 || sessionScope.authority == 1 && board_div == 2}">
					<div>
						<button type="button" id="btn_modify" class="btn btn-outline-secondary btn-sm col-sm-1">수정</button>
						<button type="button" id="btn_delete" class="btn btn-outline-secondary btn-sm col-sm-1">삭제</button>
						
					</div>
				</c:if>
				
				

				<br style="clear: both;">
				<div
					style="display: block; background: #eee; padding: 20px; border-radius: 10px;">
					<div
						style="display: block; width: 7%; height: 100%; float: left; padding: 10px; text-align: right; font-weight: bold;">
					</div>
					<textarea id="rpy_context"
						style="width: 85%; resize: none; height: 80px; float: left; margin-right: 1%;"></textarea>
					<button id="reply_write" style="width: 7%; height: 80px;"
						class="btn btn-info" type="button">
						댓글<br>작성
					</button>
				</div>
				<br style="clear: both;">
				<div class="table-responsive">
					<table class="table table-striped box1">
						<thead>
							<tr>
								<th></th>
								<th></th>
								<th style="width: 700px;">댓글내용</th>
								<th>작성자</th>
								<th>작성일시</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${replyList }" var="reply">
								<c:choose>
									<c:when test="${reply.rpy_del_if != 'del' }">
										<tr class="rpyListTr" data-rpy_sq="${reply.rpy_sq}">
											<td></td>
											<td></td>
											<td>${reply.rpy_con}
												<div id="reply"></div>
											</td>
											<td>${reply.rpy_emp_sq }</td>
											<td><fmt:formatDate value="${ reply.rpy_dt }"
													pattern="yyyy/MM/dd  hh:mm:ss" /></td>
											<td>
												<c:if
													test="${sessionScope.authority == 1 || reply.rpy_emp_sq == employeeVo.emp_sq}">
													<a href="#" class="delete_reply" value="${reply.rpy_sq}">[삭제]</a>

												</c:if>
												<div class="reply_on">
													<p>
														<a href="#">[답글]</a>
													</p>
													<div style="display: none"></div>
												</div>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td></td>
											<td></td>
											<td colspan="4">삭제된 댓글입니다.</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>

						</tbody>
					</table>

					<%-- 페이징 --%>
					<c:set var="lastPage" value="${lastpage}" />
					<div class="dataTables_paginate paging_simple_numbers"
						id="bootstrap-data-table_paginate">
						<nav style="text-align: center;">
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
											href="${cp}/detail?page=1&ntc_sq=${NoticeVo.ntc_sq }&ntc_div=${NoticeVo.ntc_div}"
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
												href="${cp}/detail?page=${i}&ntc_sq=${NoticeVo.ntc_sq}&ntc_div=${NoticeVo.ntc_div}"
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
												href="${cp}/detail?page=${i}&ntc_sq=${NoticeVo.ntc_sq}&ntc_div=${NoticeVo.ntc_div}"
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
											href="${cp}/detail?page=${lastPage}&ntc_sq=${NoticeVo.ntc_sq}&ntc_div=${NoticeVo.ntc_div}"
											aria-controls="bootstrap-data-table" tabindex="0"
											class="page-link"><i class="fa fa-angle-double-right"></i></a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</nav>
					</div>
					<!-- 페이징 종료 -->
				</div>
				<!-- 컨텐츠 종료 -->
			</div>
		</div>
	</div>
</div>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script>
	<c:if test="${sessionScope.msg != null}">
		alert("${sessionScope.msg}");
		<%session.removeAttribute("msg");%>
	</c:if>
	$(document).on("click",".delete_reply",function(){
		var delete_sq=	$(this).attr('value');
		var rpy_del_if = "del";
		$("#rpy_sq").val(delete_sq);
		$("#rpy_del_if ").val(rpy_del_if );
		
		$("#send_frm").attr("method", "post");
		$("#send_frm").attr("action", contextPath + "/delete_reply");
		$("#send_frm").submit();
		console.log(delete_sq);
	})
	
	$(document).on("click",".reply_on>p",function(){
		if($(this).next().css("display")=="none"){
			// 버튼 클릭시 Row 값 가져오기
            var str = ""
            var tdArr = new Array();    // 배열 선언
            var replyBtn = $(this);
            
            var tr = replyBtn.parent().parent().parent();
            var td = tr.children();
            
            var tdText = td.eq(2).text();
            
            td.eq(2).append("\
            <div id='reply'>└답글<textarea id='rpy_comment_area' style='width: 100%; resize: none; height: 50px; float: left; margin-right: 1%';/>\
            <div><button type='button' id='comment_rg' class='btn btn-secondary'>작성</button>\
            </div>");

			$(this).next().show();
			$(this).children("a").text("[취소]");
		}else{
			$(this).next().hide();
            var str = ""
                var tdArr = new Array();    // 배열 선언
                var replyBtn = $(this);
                
                var tr = replyBtn.parent().parent().parent();
                var td = tr.children();
                
                var tdText = td.eq(2).text();

			  td.eq(2).children().hide();
			$(this).children("a").text("[답글]");
	
		}
		$("#comment_rg").on("click",function(){
			var rpy_comment_s = $("#rpy_comment_area").val();
			if(rpy_comment_s==""){
				alert("댓글 내용을 입력해 주세요.")
				return;
			}
			
			$("#rpy_comment_s").val(rpy_comment_s);
		
			init();
			$("#send_frm").attr("method", "post");
			$("#send_frm").attr("action", contextPath + "/reply_comment");
			$("#send_frm").submit();
		});
		
		$(".rpyListTr").on("click", function() {
			console.log($(this).data("rpy_sq"));
			var rpy_sq= $(this).data("rpy_sq");

			$("#rpy_sq").val(rpy_sq);
			$("#comment_rg").submit();
		});

});

function addFileBox() {
	 if(document.getElementById("filebox").innerHTML == "┌보기"){

		  document.all['sb1'].style.display = "block";

		  document.getElementById("sc1").innerHTML = "└접기";

		 } else {

		  document.all['sb1'].style.display = "none";

		  document.getElementById("sc1").innerHTML = "┌보기";

		 }
	}

	
$(".filebox>a").on("click",function() {
	var box = $(this).next("div");
	if(box.is(":visible") ){
		box.slideUp();
	}else{
		box.slideDown();
	}
	
	});

	var contextPath = "${cp}";
	function init(){
		
		$("#ntc_sq").val("${NoticeVo.ntc_sq}");
		
	}
	
	//수정 서블렛
	$("#btn_modify").on("click",function(){
		init();
		$("#send_frm").attr("action", contextPath + "/modify");
		$("#send_frm").submit();
	});
	
	//삭제 서블렛
	$("#btn_delete").on("click",function(){
		
		if(confirm("삭제하시겠습니까?")) {
			init();
			$("#send_frm").attr("method", "get");
			$("#send_frm").attr("action", contextPath + "/delete");
			$("#send_frm").submit();
		}

	});
	
	//답글 서블렛
	$("#btn_repost").on("click",function(){
		init();
		$("#send_frm").attr("action", contextPath + "/postRepost");
		$("#send_frm").submit();
	});
	
	
	//댓글 작업 시작
	// 댓글 수정
	$(".btn_cmt_modify").on("click",function(){
		init();
		var s_cmtNo = $(this).closest(".cmtListTr").data("cmtno");
		$("#s_cmtNo").val(s_cmtNo);
		$("#send_frm").attr("method", "post");
		$("#send_frm").attr("action", contextPath + "/cmtModify");
		$("#send_frm").submit();
	});
	
	// 댓글 삭제
	$(".btn_cmt_delete").on("click",function(){
		init();
		var s_cmtYn = $(this).closest(".cmtListTr").data("cmtyn");
		$("#s_cmtYn").val(s_cmtYn);
		var s_cmtNo = $(this).closest(".cmtListTr").data("cmtno");
		$("#s_cmtNo").val(s_cmtNo);
		$("#send_frm").attr("method", "post");
		$("#send_frm").attr("action", contextPath + "/cmtModify");
		$("#send_frm").submit();
	});
	
	// 댓글 작성
	
	$("#reply_write").on("click",function(){
		
		var rpy_cons = $("#rpy_context").val();
		if(rpy_cons==""){
			alert("댓글 내용을 입력해 주세요.")
			return;
		}
		
		$("#rpy_cons").val(rpy_cons);

		init();
		
		$("#send_frm").attr("method", "post");
		$("#send_frm").attr("action", contextPath + "/replyInsert");
		$("#send_frm").submit();
		
		
	

	});
	
</script>

<form id="send_frm" action="" method="get">
	<input type="hidden" name="ntc_sq" value="${NoticeVo.ntc_sq }"
		id="ntc_sq" /> <input type="hidden" name="ntc_div"
		value="${NoticeVo.ntc_div }" id="ntc_div" /> <input type="hidden"
		name="ntc_fl_path" value="${NoticeVo.ntc_fl_path }" id="ntc_fl_path" />
	<input type="hidden" name="rpy_con" id="rpy_cons" /> <input
		type="hidden" name="rpy_ntc_sq" value="${NoticeVo.ntc_sq}" /> <input
		type="hidden" name="rpy_comment" id="rpy_comment_s" /> <input
		type="hidden" name="rpy_sq" id="rpy_sq" /> <input type="hidden"
		name="rpy_del_if" id="rpy_del_if" />
</form>
</body>
</html>








