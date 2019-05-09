<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>기안하기</strong></h1>
	        </div>
	    </div>
	</div>
</div>
<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<div class="col-sm-1"></div>
		<table class="table table-bordered col-sm-10">
			<thead class="thead-dark">
		       	<tr class="text-center">
		           	<th scope="col" style="width: 80px;">No.</th>
		           	<th scope="col">양식명</th>
		           	<th style="width: 200px;"></th>
		      	</tr>
			</thead>
			<tbody>
				<c:forEach items="${apvDivList }" var="apvDiv">
					<tr class="text-center">
						<td>${apvDiv.div_apv_sq }</td>
						<td>${apvDiv.div_apv_nm }</td>
						<td>
							<button data-divsq="${apvDiv.div_apv_sq }" type="button" class="insBtn btn btn-outline-secondary btn-sm col-sm-10">작성</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<form id="frm" action="${cp }/apvForm" method="get" class="form-horizontal" role="form">
	<input type="hidden" id="div_apv_sq" name="div_apv_sq" />
</form>

<script>
	$(document).ready(function() {
		<c:if test="${msg != null}">
			alert("${msg}");
		</c:if>
	
		$(".insBtn").on("click", function() {
			var div_apv_sq = $(this).data("divsq");
			
			$("#div_apv_sq").val(div_apv_sq);
			$("#frm").submit();
		});
	});
</script>