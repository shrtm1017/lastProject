<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>내가 받은 결재</strong></h1>
	        </div>
	    </div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<div class="col-sm-1"></div>
		<form id="frm"  action="${cp }/apvReceiveSearch" method="get" class="form-horizontal" role="form">
			<table class="table table-bordered col-sm-10">
				<tr>
					<th style="vertical-align: middle; text-align: center; width: 200px;">검색일자</th>
					<td>
						<div class="form-group text-center">
							<div class="col col-sm-3">
				            	<select class="form-control" name="selDt">
				            		<option value="1">기안일</option>
				            		<option value="2">결재일</option>
				            	</select>
			            	</div>
			            	<div class="col col-sm-3">
				            	<div class="input-group date">
						            <input type="text" class="form-control" name="apv_str_dt" />
						            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						        </div>
					        </div>
					        <div class="col col-sm-1">
					        	~
					        </div>
					        <div class="col col-sm-3">
				            	<div class="input-group date">
						            <input type="text" class="form-control" name="apv_end_dt" />
						            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						        </div>
					        </div>
						</div>
					</td>
				</tr>
				<tr>
					<th style="vertical-align: middle; text-align: center;">결재상태</th>
					<td>
						<div class="col col-sm-3">
			            	<select class="form-control" name="apv_sign_chk">
			            		<option value="100">전체</option>
			            		<option value="0">미결</option>
			            		<option value="1">승인</option>
			            		<option value="2">반려</option>
			            	</select>
		            	</div>
					</td>
				</tr>
				<tr>
					<th style="vertical-align: middle; text-align: center;">문서분류</th>
					<td>
						<!-- DB에서 데이터 조회 -->
						<div class="col col-sm-3">
			            	<select class="form-control" name="apv_div_sq">
			            		<option value="0">전체</option>
				            	<c:forEach items="${apvDivList }" var="apvDiv">
									<option value="${apvDiv.div_apv_sq }">${apvDiv.div_apv_nm }</option>
								</c:forEach>
			            	</select>
		            	</div>
					</td>
				</tr>
				<tr>
					<th style="vertical-align: middle; text-align: center;">문서명</th>
					<td>
						<div class="col col-md-10">
							<div class="input-group">
								<input type="text" id="apv_nm" name="apv_nm" class="form-control" />
								<div class="input-group-btn">
									<button id="searchBtn" class="btn btn-info">
										<i class="fa fa-search"></i>
									</button>
				            	</div>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</form>
		
		<div class="col-sm-1"></div>
		<table class="table table-bordered col-sm-10">
			<thead class="thead-dark">
		       	<tr class="text-center">
		           	<th scope="col">문서번호</th>
		           	<th scope="col">제목</th>
		           	<th scope="col">기안자</th>
		           	<th scope="col">기안일</th>
		           	<th scope="col">결재일</th>
		           	<th scope="col">상태</th>
		      	</tr>
			</thead>
			<tbody>
				<c:forEach items="${apvEleList }" var="apvEle">
					<tr class="text-center">
						<td>
							<button type="button" class="btn btn-link" data-apvsq="${apvEle.apv_sq }" data-toggle="modal" data-target="#detailModal">
								${apvEle.apv_sq }
							</button>
						</td>
						<td>
							<button type="button" class="btn btn-link" data-apvsq="${apvEle.apv_sq }" data-toggle="modal" data-target="#detailModal">
								${apvEle.apv_nm }
							</button>
						</td>
						<td>
							<c:set var="loopFlag" value="false"/>
							<c:forEach items="${empList }" var="apvEmp">
								<c:if test="${not loopFlag}"> 
									<c:if test="${apvEle.apv_emp_sq eq apvEmp.emp_sq}">
										${apvEmp.emp_nm }
										<c:set var="loopFlag" value="true"/> 
									</c:if>
								</c:if>
							</c:forEach>
						</td>
						<td>
			            	<fmt:formatDate value="${apvEle.apv_dt }" pattern="yyyy-MM-dd" />
						</td>
						<td>
							<c:set var="apvDateFlag" value="false"/>
							<c:forEach items="${approvalList }" var="apv">
								<c:if test="${not apvDateFlag}">
									<c:if test="${apvEle.apv_sq eq apv.apv_sq && empVo.emp_sq eq apv.apv_sign}">
										<c:choose>
											<c:when test="${apv.apv_date != null}">
												<fmt:formatDate value="${apv.apv_date }" pattern="yyyy-MM-dd" />
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
										<c:set var="apvDateFlag" value="true"/> 
									</c:if>
								</c:if>
							</c:forEach>
						</td>
						<td id="tdState${apvEle.apv_sq }">
							<c:set var="apvStateFlag" value="false"/>
							<c:forEach items="${approvalList }" var="apv">
								<c:if test="${not apvStateFlag}">
									<c:if test="${apvEle.apv_sq eq apv.apv_sq && empVo.emp_sq eq apv.apv_sign}">
										<c:choose>
											<c:when test="${apv.apv_sign_chk == '0'}">
												<strong>미결</strong>
											</c:when>
											<c:when test="${apv.apv_sign_chk == '1'}">
												<label style="color: navy;"><strong>승인</strong></label>
											</c:when>
											<c:when test="${apv.apv_sign_chk == '2'}">
												<label style="color: red;"><strong>반려</strong></label>
											</c:when>
										</c:choose>
										<c:set var="apvStateFlag" value="true"/> 
									</c:if>
								</c:if>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set var="lastPage" value="${Integer(apvEleCnt / pageSize + (apvEleCnt % pageSize > 0 ? 1 : 0)) }" />
		<c:set var="lastPageFirstValue" value="${Integer((lastPage - 1) / 10) * 10 + 1 }" />

        <c:set var="startPage" value="${Integer((page - 1) / 10) * 10 + 1 }" />
        <c:set var="endPage" value="${startPage + 10 - 1 }" />
        
        <br>
		<div class="col-sm-1"></div>
		<div class="dataTables_paginate paging_simple_numbers"
			id="bootstrap-data-table_paginate">
			<ul class="pagination">
				<c:choose>
					<c:when test="${page == '1'}">
						<li class="paginate_button page-item previous disabled" id="bootstrap-data-table_previous">
							<a aria-controls="bootstrap-data-table" tabindex="0" class="page-link"><i class="fa fa-angle-double-left"></i></a>
						</li>
					</c:when>
					<c:otherwise>
						<li id="bootstrap-data-table_previous">
							<a href="${cp}/apvReceiveSearch?page=1&selDt=${selDt}&apv_str_dt=${apv_str_dt}&apv_end_dt=${apv_end_dt}&apv_sign_chk=${apv_sign_chk}&apv_div_sq=${apv_div_sq}&apv_nm=${apv_nm}" aria-controls="bootstrap-data-table" tabindex="0" class="page-link"><i class="fa fa-angle-double-left"></i></a>
						</li>
					</c:otherwise>
				</c:choose>
				
				<!-- 페이지 -->
				<c:choose>
					<c:when test="${startPage == lastPageFirstValue}">
						<c:forEach begin="${lastPageFirstValue}" end="${lastPage}" var="i">
							<c:set var="active" value="" />
							<c:if test="${i == page}">
								<c:set var="active" value="paginate_button page-item active" />
							</c:if>
							<li class="${active}">
								<a href="${cp}/apvReceiveSearch?page=${i}&selDt=${selDt}&apv_str_dt=${apv_str_dt}&apv_end_dt=${apv_end_dt}&apv_sign_chk=${apv_sign_chk}&apv_div_sq=${apv_div_sq}&apv_nm=${apv_nm}" aria-controls="bootstrap-data-table" data-dt-idx="1" tabindex="0" class="page-link">${i}</a>
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
								<a href="${cp}/apvReceiveSearch?page=${i}&selDt=${selDt}&apv_str_dt=${apv_str_dt}&apv_end_dt=${apv_end_dt}&apv_sign_chk=${apv_sign_chk}&apv_div_sq=${apv_div_sq}&apv_nm=${apv_nm}" aria-controls="bootstrap-data-table" data-dt-idx="1" tabindex="0" class="page-link">${i}</a>
							</li>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${page == lastPage}">
						<li class="paginate_button page-item next" id="bootstrap-data-table_next">
							<a aria-controls="bootstrap-data-table" tabindex="0" class="page-link"><i class="fa fa-angle-double-right"></i></a>
						</li>
					</c:when>
					<c:otherwise>
						<li id="bootstrap-data-table_next">
							<a href="${cp}/apvReceiveSearch?page=${lastPage}&selDt=${selDt}&apv_str_dt=${apv_str_dt}&apv_end_dt=${apv_end_dt}&apv_sign_chk=${apv_sign_chk}&apv_div_sq=${apv_div_sq}&apv_nm=${apv_nm}" aria-controls="bootstrap-data-table" tabindex="0" class="page-link"><i class="fa fa-angle-double-right"></i></a>
						</li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</div>

<div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="detailModalLabel">
				</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body">
				<div id="divRefu" style="display: none;">
					<table class="table table-bordered col-sm-12">
						<tr>
							<th style="width : 130px; text-align: center;">반려사유</th>
							<td id="mApvRefu"></td>
						</tr>
					</table>
				</div>
				<div id="divReturnForm" style="display: none;">
					<table class="col-sm-12">
						<tr>
							<th style="width : 130px; text-align: center;">반려사유</th>
							<td>
								<div class="input-group">
									<input type="text" id="apv_refu" class="form-control" />
									<button type="button" id="btnReturn" class="btn btn-secondary"><small>제출</small></button>
									<button type="button" id="btnReturnCancel" class="btn btn-danger"><small>취소</small></button>
								</div>
							</td>
						</tr>
					</table>
					<br>
				</div>
				<table class="table table-bordered col-sm-5 text-center" style="float: left;">
					<tbody>
						<tr>
							<th style="width: 130px;">문서번호</th>
							<td id="mApvSq"></td>
						</tr>
						<tr>
							<th>기안일</th>
							<td id="mApvDt"></td>
						</tr>
						<tr>
							<th>기안부서</th>
							<td id="mApvDptNm"></td>
						</tr>
						<tr>
							<th>기안자</th>
							<td id="mApvEmpNm"></td>
						</tr>
					</tbody>						
				</table>
				<table class="table table-bordered col-sm-6 text-center" style="float: right;">
					<thead>
						<tr id="mApvSignDpt"></tr>
					</thead>
					<tbody>
						<tr id="mApvSign"></tr>
						<tr id="mApvSignNm"></tr>
					</tbody>
				</table>
				<table class="table table-bordered">
				    <tbody>
				    	<tr>
				    		<th scope="row" style="width: 200px; vertical-align: middle;" class="text-center">수신참조자</th>
				    		<td id="mApvRef" style="width: 180px; vertical-align: middle;"></td>
				    		<th scope="row" style="width: 200px; vertical-align: middle;" class="text-center">시행자</th>
				    		<td id="mApvExeNm" style="width: 180px; vertical-align: middle;"></td>
				    	</tr>
				        <tr>
				            <th scope="row" style="width: 200px;" class="text-center">제목</th>
				            <td id="mApvNm" colspan="3"></td>
				        </tr>
			        	<tr id="trTerm" style="display: none;">
				            <th scope="row" style="width: 200px;" class="text-center">기간</th>
				            <td id="mApvTerm" colspan="3"></td>
				    	</tr>
				    	<tr id="trAnuDiv" style="display: none;">
				            <th scope="row" style="width: 200px;" class="text-center">구분</th>
				            <td id="mApvAnuDiv" colspan="3"></td>
			        	</tr>
				        <tr>
				        	<td id="mApvCon" colspan="4"></td>
				        </tr>
				        <tr>
				        	<th scope="row" style="width: 200px;" class="text-center">첨부파일</th>
				        	<td id="mApvFile" colspan="3" style="width: 200px;"></td>
				        </tr>
				    </tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary btn-sm col-sm-2" data-dismiss="modal">확인</button>
			</div>
		</div>
	</div>
</div>

<input type="hidden" id="emp_sq" value="${empVo.emp_sq }" />
<input type="hidden" id="sign_apv_sq" />

<form id="signUpdFrm"  action="${cp }/userModify" method="get" class="form-horizontal" role="form">
</form>

<script>
	$(document).ready(function() {
		$(".input-group.date").datepicker({
            calendarWeeks: false,
            todayHighlight: true,
            autoclose: true,
            format: "yyyy/mm/dd",
            language: "kr"
        });
		
		$("#searchBtn").on("click", function() {
			$("#frm").submit();
		});
		
		$(document).on("click", ".signBtn", function() {
			var apv_sq = $(this).data("sign");
			var emp_sq = $(this).data("empsq");

			var data = { apv_sign : emp_sq };
			$.ajax({
				url 	 : "${cp}/apvSignCheck",
				method 	 : "post",
				data	 : JSON.stringify(data),
				dataType : "json",									// server에게 희망하는 리턴 타입을 명시
				contentType : "application/json; charset=utf-8",	// client 전송하는 데이터 타입
				success  : function(data) {
					if (data) {
						ajaxSign(apv_sq, emp_sq);
					} else {
						alert("등록된 서명이 없습니다. 서명 등록 페이지로 이동합니다.");
						$("#signUpdFrm").submit();
					}
				}
			});
		});
		
		function ajaxSign(apv_sq, emp_sq) {
			var data = { apv_sq : apv_sq, apv_sign : emp_sq, apv_sign_chk : "1" };
			$.ajax({
				url 	 : "${cp}/apvStateUpdate",
				method 	 : "post",
				data	 : JSON.stringify(data),	// data를 json 문자열로 전송한다 
				dataType : "json",					// server에게 희망하는 리턴 타입을 명시
				contentType : "application/json; charset=utf-8",	// client 전송하는 데이터 타입
				success  : function(data) {
					alert("[" + data.apv_sq + "] 승인되었습니다.");
					
					var date = new Date(data.apv_date),
				        month = '' + (date.getMonth() + 1),
				        day = '' + date.getDate(),
				        year = date.getFullYear();

				    if (month.length < 2) month = '0' + month;
				    if (day.length < 2) day = '0' + day;

				    var apv_date = [year, month, day].join('-');
					
			 		$("#tdSign" + apv_sq).html("<img src='${cp }/userSign?emp_sq=" + data.apv_sign + "' style='width: 80px; height: 40px;' /><br><small>" + apv_date + "</small>");
			 		$("#tdState" + apv_sq).html("<label style='color: navy;'><strong>승인</strong></label>");
				}
			});
		}
		
		$(document).on("click", "#btnReturnForm", function() {
			var apv_sq = $(this).data("apvsq");
			$("#sign_apv_sq").val(apv_sq);
			$("#divReturnForm").show();
		});
		
		$(document).on("click", "#btnReturnCancel", function() {
			$("#apv_refu").val("");
			$("#divReturnForm").hide();
		});
		
		$("#btnReturn").on("click", function() {
			var apv_sq = $("#sign_apv_sq").val();
			var emp_sq = $("#emp_sq").val();
			var apv_refu = $("#apv_refu").val();
			
			var data = { apv_sq : apv_sq, apv_sign : emp_sq, apv_sign_chk : "2", apv_refu : apv_refu };
			$.ajax({
				url 	 : "${cp}/apvStateUpdate",
				method 	 : "post",
				data	 : JSON.stringify(data),	// data를 json 문자열로 전송한다 
				dataType : "json",					// server에게 희망하는 리턴 타입을 명시
				contentType : "application/json; charset=utf-8",	// client 전송하는 데이터 타입
				success  : function(data) {
					$("#apv_refu").val("");
					$("#divReturnForm").hide();
					
					alert("[" + data.apv_sq + "] 반려되었습니다.");
					
					var date = new Date(data.apv_date),
				        month = '' + (date.getMonth() + 1),
				        day = '' + date.getDate(),
				        year = date.getFullYear();

				    if (month.length < 2) month = '0' + month;
				    if (day.length < 2) day = '0' + day;

				    var apv_date = [year, month, day].join('-');
					
			 		$("#tdSign" + apv_sq).html("<label style='color: red; width: 80px;'><strong>반려</strong></label><br><small>" + apv_date + "</small>");
			 		$("#tdState" + apv_sq).html("<label style='color: red;'><strong>반려</strong></label>");
				}
			});
		});
	});
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script>
	var jquery = $.noConflict(true);
	
	jquery("#detailModal").on("show.bs.modal", function(event){
		var button = jquery(event.relatedTarget);
		var apv_sq = button.data("apvsq");

		var data = { apv_sq : apv_sq };
		$.ajax({
			url 	 : "${cp}/apvDetail",
			method 	 : "post",
			data	 : JSON.stringify(data),
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success  : function(data) {
				jquery("#detailModalLabel").html(data.apv_div_nm);
				jquery("#mApvSq").html(data.apv_sq);
				jquery("#mApvDt").html(data.apv_dt);
				jquery("#mApvDptNm").html(data.apv_dpt_nm);
				jquery("#mApvEmpNm").html(data.apv_emp_nm);
				jquery("#mApvExeNm").html(data.apv_exe_nm);
				jquery("#mApvNm").html(data.apv_nm);
				jquery("#mApvCon").html(data.apv_con);
				
				// 수신참조자
				if (data.apv_ref_emp != null) {
					var refEmpList = data.apv_ref_emp;
					var refList = data.apv_ref;
					var str = "";
					
					$.each(refEmpList, function(i){
						if (refList[i].apr_hit == '0') {
							str += "<i class='fa fa-circle-o'></i>";
						} else if (refList[i].apr_hit == '1') {
							str += "<i class='fa fa-check-circle-o'></i>";
						}
		                str += "&nbsp;" + refEmpList[i].emp_nm + "<br>";
		           	});
					
					jquery("#mApvRef").html(str);
				}
				
				// 첨부파일
				if (data.apv_fl_nm != null) {
					jquery("#mApvFile").html("<a href='${cp }/fileDownload?apv_sq=" + data.apv_sq + "' class='btn btn-link'>" + data.apv_fl_nm + "</a>");
				} else {
					jquery("#mApvFile").html("첨부파일 없음");
				}
				
				// 반려사유
				if (data.apv_refu != null) {
					jquery("#mApvRefu").html(data.apv_refu);
					jquery("#divRefu").show();
				} else {
					jquery("#divRefu").hide();
				}
				
				// 근태신청서 - 기간, 구분
				if (data.apv_term != null) {
					jquery("#mApvTerm").html(data.apv_term);
					jquery("#mApvAnuDiv").html(data.apv_anu_div);

					jquery("#trTerm").show();
					jquery("#trAnuDiv").show();
				} else {
					jquery("#trTerm").hide();
					jquery("#trAnuDiv").hide();
				}
				
				// 결재라인
				var apvList = data.apv_list;
				var apvEmpList = data.apv_emp_list;
				var apvDateList = data.apv_date_list;
				var strDpt = "";
				var strSign = "";
				var strNm = "";
				var emp_sq = jquery("#emp_sq").val();
				
				$.each(apvEmpList, function(i){
					if (apvEmpList[i].emp_grade == '1') {
						strDpt += "<th>사장</th>";
					} else if (apvEmpList[i].emp_grade == '2') {
						strDpt += "<th>전무</th>";
					} else if (apvEmpList[i].emp_grade == '3') {
						strDpt += "<th>이사</th>";
					} else if (apvEmpList[i].emp_grade == '4') {
						strDpt += "<th>부장</th>";
					} else if (apvEmpList[i].emp_grade == '5') {
						strDpt += "<th>과장</th>";
					} else if (apvEmpList[i].emp_grade == '6') {
						strDpt += "<th>팀장</th>";
					} else if (apvEmpList[i].emp_grade == '7') {
						strDpt += "<th>대리</th>";
					} else {
						strDpt += "<th>사원</th>"
					}
					
					// 결재자 == 로그인한 회원
					if (apvList[i].apv_sign == emp_sq) {
						// 미결
						if (apvList[i].apv_sign_chk == '0') {
							strSign += "<td id='tdSign" + apvList[i].apv_sq + "' style='vertical-align: middle; height: 90px;'>";
							strSign += "<button type='button' data-sign='" + apvList[i].apv_sq + "' data-empsq='" + apvList[i].apv_sign + "' class='signBtn btn btn-outline-primary btn-sm col-sm-12'>";
							strSign += "승인";
							strSign += "</button>";
							strSign += "<button type='button' data-apvsq=" + apvList[i].apv_sq + " id='btnReturnForm' class='btn btn-outline-danger btn-sm col-sm-12'>";
							strSign += "반려";
							strSign += "</button>";
						}
						// 승인
						else if (apvList[i].apv_sign_chk == '1') {
							strSign += "<td style='vertical-align: middle;'>";
							strSign += "<img src='${cp }/userSign?emp_sq=" + apvList[i].apv_sign + "' style='width: 80px; height: 40px;' /><br>";
							strSign += "<small>" + apvDateList[i] + "</small>";
							strSign += "</td>"
						}
						// 반려
						else {
							strSign += "<td style='vertical-align: middle; height: 90px;'>";
							strSign += "<label style='color: red; width: 80px;'><strong>반려</strong></label><br>";
							strSign += "<small>" + apvDateList[i] + "</small>";
							strSign += "</td>";
						}
						
					} else {
						if (apvList[i].apv_sign_chk == '1') {
							strSign += "<td style='vertical-align: middle;'>";
							strSign += "<img src='${cp }/userSign?emp_sq=" + apvList[i].apv_sign + "' style='width: 80px; height: 40px;' /><br>";
							strSign += "<small>" + apvDateList[i] + "</small>";
							strSign += "</td>";
						}
						else if (apvList[i].apv_sign_chk == '2') {
							strSign += "<td style='vertical-align: middle; height: 90px;'>";
							strSign += "<label style='color: red; width: 80px;'><strong>반려</strong></label><br>";
							strSign += "<small>" + apvDateList[i] + "</small>";
							strSign += "</td>";
						}
						else {
							strSign += "<td style='background-color: #EAEAEA; height: 90px;'></td>";
						}
					}
					
					strNm += "<td><label style='font-size: 11pt; width: 80px;'>" + apvEmpList[i].emp_nm + "</label></td>";
	           	});
				
				jquery("#mApvSignDpt").html(strDpt);
				jquery("#mApvSign").html(strSign);
				jquery("#mApvSignNm").html(strNm);
			}
		});
	});
</script>