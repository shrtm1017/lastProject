<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmtrt" uri="http://java.sun.com/jstl/fmt_rt" %>

<div class="breadcrumbs">
	<div class="col-sm-4">
		<div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;">
				<c:choose>
					<c:when test="${sessionScope.authority == 1}">
						<strong>[관리자] 증명서 관리</strong>
					</c:when>
					<c:otherwise>
						<strong>[일반] 증명서 관리</strong>
					</c:otherwise>
				</c:choose>
				</h1>
			</div>
		</div>
	</div>
</div>

<div class="col-lg-12 col-sm-4">
	<br>
	<div class="col-sm-1"></div>
	<form id="searchfrm" action="${cp}/searchList" method="get">
		<table class="table table-bordered col-sm-10">
			<tbody>
				<tr class="text-center">
					<th>증명서 구분</th>
					<td><select class="selCertificate form-control"
						id="crt_div_sq" name="crt_div_sq">
							<option value="0">전체</option>
							<c:forEach items="${crtDivList}" var="crtdiv">
								<option value="${crtdiv.crt_div_sq}">${crtdiv.crt_div_nm}</option>
							</c:forEach>
					</select></td>
					<th>처리상태</th>
					<td><select class="selCertificate form-control"
						id="crt_whether" name="crt_whether">
							<option value="0">전체</option>
							<option value="1">승인대기</option>
							<option value="2">발급완료</option>
							<option value="3">반려</option>
					</select></td>
				</tr>
			</tbody>
		</table>
	</form>
	<div class="text-center">
		<button id="btnSearch" class="btn btn-info">
			<i class="fa fa-search"> 검색</i>
		</button>
		<c:if test="${sessionScope.authority != 1}">
			<a href="${cp}/certificateapply">
				<button class="btn btn-secondary">
					<i class="fa fa-inbox"> 신청</i>
				</button>
			</a>
		</c:if>
	</div>
	<br>
	<div class="col-sm-1"></div>
	<table class="table table-bordered col-sm-10">
		<thead class="thead-dark">
			<tr class="text-center">
				<th>발급번호</th>
				<th>신청일자</th>
				<th>증명서구분</th>
				<th>처리상태</th>
				<th>처리일</th>
				<th>상세보기</th>
			</tr>
		</thead>
		<tbody id="crt_tbody">
			<c:forEach items="${crtPagingList}" var="crt" varStatus="i">
				<tr class="text-center" role="row">
					<td>
						<button type="button" class="btndetail btn btn-link"
							data-toggle="modal" data-target="#detailModal${crt.crt_sq}">${i.index+1}</button>
					</td>
					<td><fmt:formatDate value="${crt.crt_dt}" pattern="yyyy-MM-dd" /></td>
					<td><c:if test="${crt.crt_div_sq == 1}">재직증명서</c:if> 
						<c:if test="${crt.crt_div_sq == 2}">경력증명서</c:if>
					</td>
					<td><c:if test="${crt.crt_whether == 1}">
							<label style="color: #476600;"><strong>승인대기</strong></label>
						</c:if> <c:if test="${crt.crt_whether == 2}">
							<label style="color: navy;"><strong>발급완료</strong></label>
						</c:if> <c:if test="${crt.crt_whether == 3}">
							<label style="color: red;"><strong>반려</strong></label>
						</c:if></td>
					<td><c:choose>
							<c:when test="${crt.crt_sign_dt != null }">
								<fmt:formatDate value="${crt.crt_sign_dt}" pattern="yyyy-MM-dd" />
							</c:when>
							<c:otherwise>처리 중</c:otherwise>
						</c:choose></td>
					<td>
						<button type="button" class="btndetail btn btn-link"
							data-toggle="modal" data-target="#detailModal${crt.crt_sq}">상세보기</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<div class="col-sm-1"></div>
	<div class="dataTables_paginate paging_simple_numbers"
		id="bootstrap-data-table_paginate" style="width: 130px;">
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
						href="${cp}/ctfList?page=1&crt_div_sq=${crt_div_sq}&crt_whether=${crt_whether}"
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
							href="${cp}/ctfList?page=${i}&crt_div_sq=${crt_div_sq}&crt_whether=${crt_whether}"
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
							href="${cp}/ctfList?page=${i}&crt_div_sq=${crt_div_sq}&crt_whether=${crt_whether}"
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
						href="${cp}/ctfList?page=${lastPage}&crt_div_sq=${crt_div_sq}&crt_whether=${crt_whether}"
						aria-controls="bootstrap-data-table" tabindex="0"
						class="page-link"><i class="fa fa-angle-double-right"></i></a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</div>

	<c:forEach items="${crtPagingList}" var="crt">
		<div class="modal fade" id="detailModal${crt.crt_sq}" tabindex="-1"
			role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<c:choose>
							<c:when test="${crt.crt_whether == 2 }">
								<div class="text-center col-sm-12">
									<h2 class="modal-title" id="detailModalLabel">
										<c:if test="${crt.crt_div_sq == 1 }">재   직   증   명   서</c:if>
										<c:if test="${crt.crt_div_sq == 2 }">경   력   증   명   서</c:if>
									</h2>
								</div>
							</c:when>
							<c:otherwise>
								<c:if test="${crt.crt_whether == 1 }">
									<h5 class="modal-title" id="detailModalLabel">증명서 상세 보기(승인 대기)</h5>
								</c:if>
								<c:if test="${crt.crt_whether == 3 }">
									<h5 class="modal-title" id="detailModalLabel">증명서 상세 보기(반려)</h5>
								</c:if>
							</c:otherwise>
						</c:choose>
						<c:if test="${crt.crt_whether == 1 || crt.crt_whether == 3 }">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
						</c:if>
					</div>

					<div class="modal-body">
						<table class="table table-bordered">
							<thead style="background-color: #91B4C8; color: white;">
							</thead>

							<tbody>
								<c:choose>
									<c:when test="${crt.crt_whether == 2 }">
										<tr class="text-center">
											<th class="text-center" scope="row" style="width: 200px;">회사명
											</th>
											<td colspan="4" style="width: 850px;">
												<div class="col col-sm-10">
													<div class="input-group date">
														<strong>NK Office</strong>
													</div>
												</div>
											</td>
											<th class="text-center" scope="row" style="width: 200px;">성
												명</th>
											<td colspan="2" style="width: 850px;">
												<div class="col col-sm-10">${crt.crt_emp_nm }</div>
											</td>
										</tr>

										<tr class="text-center">
											<th class="text-center" scope="row" style="width: 200px;">주소</th>
											<td colspan="6" style="width: 650px;">
												<div class="col col-sm-12">${crt.crt_emp_addr1}
													${crt.crt_emp_addr2}</div>
											</td>
										</tr>
										<tr class="text-center">
											<th class="text-center" scope="row" style="width: 200px;">직급</th>
											<td colspan="4" style="width: 850px;">
												<div class="col col-sm-8">
													<div>
														<c:choose>
															<c:when test="${crt.crt_emp_grade == '1' }">사장</c:when>
															<c:when test="${crt.crt_emp_grade == '2' }">전무</c:when>
															<c:when test="${crt.crt_emp_grade == '3' }">이사</c:when>
															<c:when test="${crt.crt_emp_grade == '4' }">부장</c:when>
															<c:when test="${crt.crt_emp_grade == '5' }">과장</c:when>
															<c:when test="${crt.crt_emp_grade == '6' }">팀장</c:when>
															<c:when test="${crt.crt_emp_grade == '7' }">대리</c:when>
															<c:otherwise>사원</c:otherwise>
														</c:choose>
													</div>
												</div>
											</td>

											<th class="text-center" scope="row" style="width: 200px;">부서</th>
											<td colspan="4" style="width: 850px;">
												<div class="col col-sm-10">
													<c:if test="${crt.crt_emp_dpt == 201 }">인사팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 202 }">회계팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 301 }">기술개발팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 302 }">마케팅팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 303 }">영업팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 401 }">임베디드개발팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 402 }">게임개발팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 403 }">WEB개발팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 501 }">디자인1팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 502 }">디자인2팀</c:if>
												</div>
											</td>
										</tr>

										<tr class="text-center">
											<th class="text-center" scope="row" style="width: 200px;">재직기간
											</th>
											<td colspan="8" style="width: 850px;">
												<div class="col col-sm-5">
													<div class="text-center">
														<fmt:formatDate value="${crt.crt_dos_str}"
															pattern="yyyy.MM.dd" />
													</div>
												</div>
												<div class="col col-sm-1">~</div>
												<div class="col col-sm-5">
													<div class="text-center">
														<c:if test="${crt.crt_div_sq == 1 }">현재 재직중</c:if>
														<c:if test="${crt.crt_div_sq == 2 }">
															<fmt:formatDate value="${crt.crt_dos_end}"
																pattern="yyyy.MM.dd" />
														</c:if>
													</div>
												</div>
											</td>
										</tr>
										<tr class="text-center">
											<th class="text-center" scope="row" style="width: 200px;">용도</th>
											<td colspan="8" style="width: 650px;">
												<div class="col col-sm-10">
													<c:if test="${crt.crt_con == 1 }">일반제출용</c:if>
													<c:if test="${crt.crt_con == 2 }">회사제출용</c:if>
													<c:if test="${crt.crt_con == 3 }">공공기관용</c:if>
													<c:if test="${crt.crt_con == 4 }">금융기관용</c:if>
												</div>
											</td>
										</tr>

										<tr class="text-center">
											<th class="text-center" scope="row" style="width: 200px;">제출처</th>
											<td colspan="4" style="width: 650px;">
												<div class="col col-sm-12">
													<div>${crt.crt_submission }</div>
												</div>
											</td>
											<th class="text-center" scope="row" style="width: 200px;">제출예정일</th>
											<td colspan="4" style="width: 650px;">
												<div class="col col-sm-10">
													<div class="input-group date">
														<fmt:formatDate value="${crt.crt_subdt}"
															pattern="yyyy-MM-dd" />
													</div>
												</div>
											</td>
										</tr>
									</c:when>

									<c:otherwise>
										<tr>
											<th class="text-center" scope="row" style="width: 200px;">신청일자
											</th>
											<td colspan="4" style="width: 850px;">
												<div class="col col-sm-10">
													<div class="input-group date">
														<fmt:formatDate value="${crt.crt_dt }"
															pattern="yyyy-MM-dd" />
													</div>
												</div>
											</td>
											<th class="text-center" scope="row" style="width: 200px;">증명서
												구분</th>
											<td colspan="2" style="width: 850px;">
												<div class="col col-sm-10">
													<c:if test="${crt.crt_div_sq == 1 }">재직증명서</c:if>
													<c:if test="${crt.crt_div_sq == 2 }">경력증명서</c:if>
												</div>
											</td>
										</tr>

										<tr>
											<th class="text-center" scope="row" style="width: 200px;">부서</th>
											<td colspan="4" style="width: 850px;">
												<div class="col col-sm-10">
													<c:if test="${crt.crt_emp_dpt == 201 }">인사팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 202 }">회계팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 301 }">기술개발팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 302 }">마케팅팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 303 }">영업팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 401 }">임베디드개발팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 402 }">게임개발팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 403 }">WEB개발팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 501 }">디자인1팀</c:if>
													<c:if test="${crt.crt_emp_dpt == 502 }">디자인2팀</c:if>
												</div>
											</td>

											<th class="text-center" scope="row" style="width: 200px;">직급</th>
											<td colspan="4" style="width: 850px;">
												<div class="col col-sm-8">
													<div>
														<c:choose>
															<c:when test="${crt.crt_emp_grade == '1' }">사장</c:when>
															<c:when test="${crt.crt_emp_grade == '2' }">전무</c:when>
															<c:when test="${crt.crt_emp_grade == '3' }">이사</c:when>
															<c:when test="${crt.crt_emp_grade == '4' }">부장</c:when>
															<c:when test="${crt.crt_emp_grade == '5' }">과장</c:when>
															<c:when test="${crt.crt_emp_grade == '6' }">팀장</c:when>
															<c:when test="${crt.crt_emp_grade == '7' }">대리</c:when>
															<c:otherwise>사원</c:otherwise>
														</c:choose>
													</div>
												</div>
											</td>
										</tr>

										<tr>
											<th class="text-center" scope="row" style="width: 200px;">핸드폰
												번호</th>
											<td colspan="4" style="width: 850px;">
												<div class="col col-sm-12">
													<div>${crt.crt_emp_phone}</div>
												</div>
											</td>
											<th class="text-center" scope="row" style="width: 200px;">이름</th>
											<td colspan="4" style="width: 850px;">
												<div class="col col-sm-10">
													<div>${crt.crt_emp_nm }</div>
												</div>
											</td>
										</tr>

										<tr class="text-center">
											<th class="text-center" scope="row" style="width: 200px;">재직기간
											</th>
											<td colspan="8" style="width: 850px;">
												<div class="col col-sm-3">
													<div class="input-group date">
														<fmt:formatDate value="${crt.crt_dos_str}"
															pattern="yyyy.MM.dd" />
													</div>
												</div>
												<div class="col col-sm-1">~</div>
												<div class="col col-sm-3">
													<div class="input-group date">
														<c:if test="${crt.crt_div_sq == 1 }">현재 재직중</c:if>
														<c:if test="${crt.crt_div_sq == 2 }">
															<fmt:formatDate value="${crt.crt_dos_end}"
																pattern="yyyy.MM.dd" />
														</c:if>
													</div>
												</div>
											</td>
										</tr>

										<tr>
											<th class="text-center" scope="row" style="width: 200px;">주소</th>
											<td colspan="6" style="width: 650px;">
												<div class="col col-sm-12">${crt.crt_emp_addr1}</div>
											</td>
										</tr>
										<tr>
											<th class="text-center" scope="row" style="width: 200px;">상세주소</th>
											<td colspan="6" style="width: 650px;">
												<div class="col col-sm-12">${crt.crt_emp_addr2}</div>
											</td>
										</tr>
										<tr>
											<th class="text-center" scope="row" style="width: 200px;">용도</th>
											<td colspan="8" style="width: 650px;">
												<div class="col col-sm-10">
													<c:if test="${crt.crt_con == 1 }">일반제출용</c:if>
													<c:if test="${crt.crt_con == 2 }">회사제출용</c:if>
													<c:if test="${crt.crt_con == 3 }">공공기관용</c:if>
													<c:if test="${crt.crt_con == 4 }">금융기관용</c:if>
												</div>
											</td>
										</tr>

										<tr>
											<th class="text-center" scope="row" style="width: 200px;">제출처</th>
											<td colspan="4" style="width: 650px;">
												<div class="col col-sm-12">
													<div>${crt.crt_submission }</div>
												</div>
											</td>
											<th class="text-center" scope="row" style="width: 200px;">제출예정일</th>
											<td colspan="4" style="width: 650px;">
												<div class="col col-sm-10">
													<div class="input-group date">
														<fmt:formatDate value="${crt.crt_subdt}"
															pattern="yyyy-MM-dd" />
													</div>
												</div>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<c:if test="${crt.crt_whether == 2 }">
							<br>
							<br>
							<div align="center">
								<c:if test="${crt.crt_div_sq == 1 }">
									<strong>상기인은 <fmt:formatDate
											value="${crt.crt_dos_str}" pattern="yyyy년 MM월 dd일" />에 입사하여
										현재 재직중에 있음을 증명합니다.
									</strong>
								</c:if>
								<c:if test="${crt.crt_div_sq == 2 }">
									<strong>위와 같이 경력을 증명함.</strong>
								</c:if>
							</div>
							<br>
							<br>
							<div align="center">
								<jsp:useBean id="now" class="java.util.Date" />
								<h5>
									<strong><fmt:formatDate	value="${crt.crt_sign_dt}" pattern="yyyy년 MM월 dd일" /></strong>
								</h5>
							</div>
							<br>
							<br>
							<div align="center" style="margin-left: 370px;">
								<img src="images/all.png">
							</div>
						</c:if>

					</div>
					<div class="modal-footer" align="center">
						<c:choose>
							<c:when test="${sessionScope.authority == 1}">
								<c:choose>
									<c:when test="${crt.crt_whether == 2 || crt.crt_whether == 3 }">
										<button type="button"
											class="btnConfirm btn btn-primary btn-sm col-sm-2"
											data-dismiss="modal">확인</button>
									</c:when>
									<c:otherwise>
										<button type="button"
											class="btnApproval btn btn-primary btn-sm col-sm-2"
											data-crtsq="${crt.crt_sq}" data-dismiss="modal">승인</button>
										&nbsp;
										<button type="button"
											class="btnRefuse btn btn-danger btn-sm col-sm-2"
											data-crtsq="${crt.crt_sq}" data-dismiss="modal">반려</button>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${crt.crt_whether == 1 || crt.crt_whether == 3 }">
										<button type="button"
											class="btnConfirm btn btn-primary btn-sm col-sm-2"
											data-dismiss="modal">확인</button>
									</c:when>
									<c:otherwise>
										<button type="button"
											class="btnDown btn btn-success btn-sm col-sm-2"
											data-dismiss="modal" data-crtdiv="${crt.crt_div_sq}">다운로드</button>
										&nbsp;
										<button type="button"
											class="btnConfirm btn btn-primary btn-sm col-sm-2"
											data-dismiss="modal">확인</button>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
	

	<script src="https://html2canvas.hertzen.com/dist/html2canvas.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.min.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			// 검색 버튼 눌렀을 시
			$("#btnSearch").on("click", function() {
				$("#searchfrm").submit();
			});

			$(".btnApproval").on("click", function() {
				var crt_sq = $(this).data("crtsq");
				var str_crt_sq = String(crt_sq);

				var data = {
					crt_sq : str_crt_sq,
					crt_whether : "2"
				};

				$.ajax({
					url : "${cp}/crtStateUpdate",
					method : "post",
					data : JSON.stringify(data), // data를 json 문자열로 전송한다 
					dataType : "json", // server에게 희망하는 리턴 타입을 명시
					contentType : "application/json; charset=utf-8", // client 전송하는 데이터 타입
					success : function(data) {
						location.reload();
					}
				});
			});

			$(".btnRefuse").on("click", function() {
				var crt_sq = $(this).data("crtsq");
				var str_crt_sq = String(crt_sq);

				var data = {
					crt_sq : str_crt_sq,
					crt_whether : "3"
				};

				$.ajax({
					url : "${cp}/crtStateUpdate",
					method : "post",
					data : JSON.stringify(data), // data를 json 문자열로 전송한다 
					dataType : "json", // server에게 희망하는 리턴 타입을 명시
					contentType : "application/json; charset=utf-8", // client 전송하는 데이터 타입
					success : function(data) {
						location.reload();
					}
				});
			});

			$(".btnDown").click(function() {
				var div = $(this).data("crtdiv");

				html2canvas(document.body, {
					onrendered : function(canvas) {

						var imgData = canvas.toDataURL('image/png');
						var doc = new jsPDF('p', 'mm', 'a4');

						doc.addImage(imgData, 'PNG', -131, -12, 477, 370);
						if (div == 1) {
							doc.save('재직증명서.pdf');
						}
						if (div == 2) {
							doc.save('경력증명서.pdf');
						}
					}
				});

			});
		});
	</script>