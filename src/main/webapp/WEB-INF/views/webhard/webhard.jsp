<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>자료실</strong></h1>
	        </div>
	    </div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<div class="col-sm-1"></div>
		<div class="form-group col-sm-10">
			<div class="input-group">
				<input type="text" class="form-control" id="serachDataName"/>
				<button type="button" id="searchFBtn"class="btn btn-info" data-toggle="modal" data-target="#detailModal" style="border-radius:5px; "><i class="fa fa-search"></i></button>
				&nbsp;&nbsp;
				<button type="button" id="insFBtn"class="btn btn-secondary" data-toggle="modal" data-target="#detailModal" style="border-radius:5px;">자료 등록</button>
			</div>
		</div>
		<br><br><br>
		<div class="col-md-1"></div>
		<table class="table table-bordered col-sm-10">
			<thead style="background-color: #002266;">
		       	<tr class="text-center">
		           	<th scope="col" style="color: white;">제목</th>
		           	<th scope="col" style="width: 100px; color: white;">작성자</th>
		           	<th scope="col" style="width: 200px; color: white;">작성일시</th>
		           	<th scope="col" style="width: 90px; color: white;">다운로드</th>
		           	<th scope="col" style="width: 90px; color: white;">다운내역</th>
		      	</tr>
			</thead>
			<tbody>
				<c:forEach items="${dataList }" var="datas">
					<tr class="text-left">
					
						<c:choose>
							<c:when test="${memGrade == 1}">
								<td class="master" data-toggle="modal" data-target="#deleteModal" data-master="${datas.data_sq }">
									<a>&nbsp;${datas.data_nm }</a>
								</td>
							</c:when>
							<c:otherwise>
								<td>
									<a>&nbsp;${datas.data_nm }</a>
								</td>
							</c:otherwise>
						</c:choose>
						
						<td class="text-center">
							${datas.data_man }
						</td>
						<td class="text-center">
							<fmt:formatDate value='${datas.data_dt}' pattern='yy.MM.dd hh:mm' />
						</td>
						<td class="text-center">
							<button type="button" name="btnDown" class="btn btn-primary btn-sm" data-sq="${datas.data_sq }">
							<i class="fa fa-download"></i>
							</button>
						</td>
						<td class="text-center">
							<button type="button" name="btnDownHis" class="btn btn-info btn-sm" data-sqs="${datas.data_sq }" data-toggle="modal" data-target="#historyModal">
								<i class="fa fa-file-text-o"></i>
							</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>
		<div class="col-sm-1"></div>
		
		<c:set var="lastPage" value="${Integer(dataCnt / pageSize + (dataCnt % pageSize > 0 ? 1 : 0)) }" />
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
							<a href="${cp}/dataSearch?page=1&keyword=${keyword}" aria-controls="bootstrap-data-table" tabindex="0" class="page-link"><i class="fa fa-angle-double-left"></i></a>
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
								<a href="${cp}/dataSearch?page=${i}&keyword=${keyword}" aria-controls="bootstrap-data-table" data-dt-idx="1" tabindex="0" class="page-link">${i}</a>
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
								<a href="${cp}/dataSearch?page=${i}&keyword=${keyword}" aria-controls="bootstrap-data-table" data-dt-idx="1" tabindex="0" class="page-link">${i}</a>
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
							<a href="${cp}/dataSearch?page=${lastPage}&keyword=${keyword}" aria-controls="bootstrap-data-table" tabindex="0" class="page-link"><i class="fa fa-angle-double-right"></i></a>
						</li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</div>

<div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">자료 등록</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form  id="frm" action="${cp }/insert" method="post" enctype="multipart/form-data" >
					<div class="form-group">
						<label><strong>제목</strong></label>
						<input type="text" id="dataTitle" name="dataTitle" class="form-control"/>
					</div>
					<div class="form-group">
						<label class="col-sm-12"><strong>파일</strong></label>
						<input type="file" name="dataFile" multiple/>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" id="submitBtn" class="btn btn-info btn-sm col-sm-2">저장</button>
				&nbsp;
				<button type="button" class="btn btn-secondary btn-sm col-sm-2" data-dismiss="modal">종료</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">자료 삭제</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<form  id="frm" action="${cp }/insert" method="post" enctype="multipart/form-data" >
				<div class="modal-body">
					<label>삭제하시겠습니까?</label>
				</div>
				<div class="modal-footer">
					<button type="button" id="deleteBtn" class="btn btn-danger btn-sm col-sm-3">삭제</button>
					&nbsp;
					<button type="button" class="btn btn-secondary btn-sm col-sm-3" data-dismiss="modal">종료</button>
				</div>
			</form>
		</div>
	</div>
</div>

<div class="modal fade" id="historyModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">다운로드 내역</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form  id="frm" action="${cp }/insert" method="post" enctype="multipart/form-data" >
					<div class="form-row">
						<div class="form-group col-sm-6">
							<div class="input-group">
								<input type="text" placeholder="사원명 검색" id="dataTitleEmp" name="dataTitle" class="form-control"/>
								<button type="button" id="searchBtn" class="btn btn-info btn-sm">
									<i class="fa fa-search"></i>
								</button>
							</div>
							<br>
							<label id="searchLabel"></label>
						</div> 
						<div class="form-group col-sm-6">
							<textarea rows="5" id="areaHis" cols="10" class="form-control" readonly="readonly" style="height: 300px;"></textarea>
						</div>
 					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary btn-sm col-sm-2" data-dismiss="modal">종료</button>
			</div>
		</div>
	</div>
</div>

<form id="frmDown" action="${cp }/download">
	<input type="hidden" id="down" name="down"/>
</form>

<form id="frmDownHis" action="${cp }/downloadHis">
	<input type="hidden" id="downHis" name="downHis"/>
</form>

<form id="frmdel" action="${cp }/delData">
	<input type="hidden" id="deldown" name="deldown"/>
</form>

<form id="frmSerach" action="${cp }/dataSearch">
	<input type="hidden" id="keyword" name="keyword" />
	<input type="hidden" id="searchData" name="searchData"/>
</form>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

<script>
	var result;
	
	$(document).ready(function(){
		
		
	});
	
// 	if ($("#dataTitle").val().trim == '') {
// 		alert("제목을 입력하세요.");
// 		$("#dataTitle").focus();
// 		return false;
// 	}
	
	$("#searchFBtn").on("click",function(){
		$("#searchData").val($("#serachDataName").val());
		$("#keyword").val($("#serachDataName").val());
		$("#frmSerach").submit();
	});
	
	$(document).on("click",".master",function(){
		
		var master = $(this).data("master");
		console.log(master);
		
		$("#deldown").val(master);
	});
	
	$(document).on("click","#deleteBtn",function(){
		
		$("#deldown").val();
		

		$("#frmdel").submit();
	});
	
	$(document).on("click","button[name=btnDown]",function(){
		$("#down").val($(this).data("sq"));
		$("#frmDown").submit();
	});
	
	$("#searchBtn").on("click",function(){
		
		var serach = $("#dataTitleEmp").val();
		
		console.log(serach);
		
		$.ajax({
			url : "/searchEmp",
			type : "POST",
			dataType : "json",
			data : JSON.stringify(serach),  
			contentType : "application/json; charset=UTF-8",
			success : function(data) {
				console.log(data);
				
				var result = ""; 
				
				for(var i=0;i<data.length;i++){
					result += data[i].emp_nm +" ("+data[i].emp_sq+") ";
				}
				
				$("#searchLabel").text(result);
				
			}
		});
	});
	
	
	$(document).on("click","button[name=btnDownHis]",function(){
		$("#downHis").val($(this).data("sqs"));
		
		var sqchk = $(this).data("sqs");
		
		var param = {
			sq : sqchk
		};
		
		$.ajax({
			url : "/downloadHis",
			type : "POST",
			dataType : "json",
			data : JSON.stringify(sqchk), 
			contentType : "application/json; charset=UTF-8",
			success : function(data) {
				var result = "";
				console.log(data);
				for(var i=0;i<data.length;i++){
					result += data[i].data_nm +" ("+data[i].data_emp_sq+")";
					result += "\n";
				}
				
				$("#areaHis").val(result);
			}
		});
	});
	
	$("#submitBtn").on("click",function(){
		$("#frm").submit();
	});
</script>