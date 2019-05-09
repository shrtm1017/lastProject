<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>설문조사</strong></h1>
	        </div>
	    </div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<div class="form-group">
			<div class="col col-md-2">
			</div>
			<div class="col col-md-8">
				<div class="input-group">
					<select class="form-control col-sm-2" id="selCondition">
	            		<option value="2">제목</option>
	            		<option value="1">진행 중</option>
	            		<option value="0">마감</option>
            		</select>
					<input id="txtKeyword" type="text" placeholder="제목, 상태 검색" class="form-control" />
					<div class="input-group-btn">
						<button id="searchBtn" class="btn btn-info">
							<i class="fa fa-search"></i>
						</button>
					</div>
				</div>
			</div>
			<div class="col col-md-2" style="text-align: right;">
				<button type="button" id="insBtn" class="btn btn-secondary col-sm-7">등록</button>
			</div>
		</div>
		<br><br>
		<table class="table">
			<thead style="background-color: #002266; color: white;">
		       	<tr>
		           	<th scope="col" style="width: 80px; text-align: center;">No.</th>
		           	<th scope="col">제목</th>
		           	<th scope="col" style="width: 150px; text-align: center;">등록일</th>
		           	<th scope="col" style="width: 150px; text-align: center;">마감일</th>
		           	<th scope="col" style="width: 100px; text-align: center;">상태</th>
		           	<th scope="col" style="width: 100px; text-align: center;">조회수</th>
		      	</tr>
			</thead>
			<tbody>
				<c:forEach items="${researchList }" var="research">
					<tr>
			            <td style="text-align: center;">
			            	<a href="${cp}/researchDetail?res_sq=${research.res_sq }" style="color: black;">
			            		${research.res_sq }
			            	</a>
			            </td>
			            <td>
			            	<a href="${cp}/researchDetail?res_sq=${research.res_sq }" style="color: black;">
			            		${research.res_nm }
			            	</a>
			            </td>
			            <td style="text-align: center;">
			            	<fmt:formatDate value="${research.res_str_dt }" pattern="yyyy-MM-dd" />
			            </td>
			            <td style="text-align: center;">
			            	<fmt:formatDate value="${research.res_end_dt }" pattern="yyyy-MM-dd" />
			            </td>
			            <td style="text-align: center;">
			            	<c:choose>
		                    	<c:when test="${research.res_state == '1' }"><strong style="color: #476600;">진행 중</strong></c:when>
		                    	<c:when test="${research.res_state == '0' }"><strong>마감</strong></c:when>
	                    	</c:choose>
	                    </td>
			            <td style="text-align: center;">${research.res_hit }</td>
		       		</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>
	    <c:set var="lastPage" value="${Integer(researchCnt / pageSize + (researchCnt % pageSize > 0 ? 1 : 0)) }" />
		<c:set var="lastPageFirstValue" value="${Integer((lastPage - 1) / 10) * 10 + 1 }" />

        <c:set var="startPage" value="${Integer((page - 1) / 10) * 10 + 1 }" />
        <c:set var="endPage" value="${startPage + 10 - 1 }" />
		
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
							<a href="${cp}/researchSearch?page=1&condition=${condition}&keyword=${keyword}" aria-controls="bootstrap-data-table" tabindex="0" class="page-link"><i class="fa fa-angle-double-left"></i></a>
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
								<a href="${cp}/researchSearch?page=${i}&condition=${condition}&keyword=${keyword}" aria-controls="bootstrap-data-table" data-dt-idx="1" tabindex="0" class="page-link">${i}</a>
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
								<a href="${cp}/researchSearch?page=${i}&condition=${condition}&keyword=${keyword}" aria-controls="bootstrap-data-table" data-dt-idx="1" tabindex="0" class="page-link">${i}</a>
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
							<a href="${cp}/researchSearch?page=${lastPage}&condition=${condition}&keyword=${keyword}" aria-controls="bootstrap-data-table" tabindex="0" class="page-link"><i class="fa fa-angle-double-right"></i></a>
						</li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</div>

<form id="insFrm" action="${cp }/researchInsert" method="get" class="form-horizontal" role="form">
</form>

<form id="searchFrm" action="${cp }/researchSearch" method="get" class="form-horizontal" role="form">
	<input type="hidden" id="condition" name="condition" />
	<input type="hidden" id="keyword" name="keyword" />
</form>

<script>
	$(document).ready(function() {
		<c:if test="${msg != null}">
			alert("${msg}");
		</c:if>
	
		$("#insBtn").on("click", function() {
			$("#insFrm").submit();
		});
		
		$("#searchBtn").on("click", function() {
			var condition = $("#selCondition option:selected").val();
			
			if (condition == 2) {
				var keyword = $("#txtKeyword").val();
				$("#keyword").val(keyword);
				$("#condition").val("t");
			}
			else {
				$("#keyword").val(condition);
				$("#condition").val("s");
			}
			
			$("#searchFrm").submit();
		});
	});
</script>