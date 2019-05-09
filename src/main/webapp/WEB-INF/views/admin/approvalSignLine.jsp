<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="tags" uri="http://tiles.apache.org/tags-tiles"%>
<div class="breadcrumbs">
	<div class="col-sm-4">
		<div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>결재라인 관리</strong></h1>
			</div>
		</div>
	</div>
</div>

<div style="width: 250px; height: 700px; float: left; ">
	<div style="height: 800px; float: left; overflow: auto; background-color: white;">
		<div style="width: 250px; height:400px; float: left;" >
			<ul class="tree" >
				<li><a href="">NK OFFICE</a></li>
				
				<li><span>대표</span>
					<ul>
						<li><a href="#"><strong>김난경</strong></a></li>
					</ul>
				</li>
				<li><a href="#about">부서</a>
					<ul>
						<c:forEach items="${dptList }" var="dpt">
							<li><button  type="button" class="btnajax btn btn-link" data-hrsq='<c:if test="${dpt.dpt_hr_sq == null || dpt.dpt_hr_sq != null }">${dpt.dpt_sq }</c:if>'>${dpt.dpt_nm}</button>
								<ul>
									<!-- 상위 부서번호 == 사원 부서번호 확인하는 부분 추가 -->
									<c:forEach items="${empList}" var="emp">
										<c:if test="${emp.emp_dpt == dpt.dpt_sq}">
											<li><a href="#jobs3">${emp.emp_nm}</a></li>
										</c:if>
									</c:forEach>
									
									<c:forEach items="${dptdownList}" var="dptdown">
										<c:if test="${dpt.dpt_sq == dptdown.dpt_hr_sq }">
											<li><a href="#jobs3">${dptdown.dpt_nm}</a>
												<ul>
													<c:forEach items="${empList}" var="emp">
														<c:if test="${emp.emp_dpt == dptdown.dpt_sq}">
															<li><a href="#jobs3">${emp.emp_nm}</a></li>
														</c:if>
													</c:forEach>
												</ul>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</li>
						</c:forEach>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</div>

<div class="form-group" style="margin-top: 30px;">
	<div class="col col-sm-8">
		<div class="input-group" style="padding-left: 160px;">
			<strong>사원명</strong>&nbsp;&nbsp;
			<input type="text" id="inputText"  name="inputText" placeholder="사원명 검색"
				 aria-controls="bootstrap-data-table" class="form-control col-sm-8" />
			<button type="button" id="btnSearch" class="btn btn-info" ><i class="fa fa-search"></i></button>
		</div>
	</div>
</div>

<div>
	<div class="col col-sm-8 text-center">
		<div class="form-group" style="padding-left: 150px; margin-top: 30px;">
			<table class="table table-bordered text-center" style="overflow: auto; width: 600px;">
				<thead>
					<tr role="row">
						<th style="width: 30px;">
							<input type="checkbox" name="checkAll" />
						</th>
						<th>부서</th>
						<th>직급</th>
						<th>이름</th>
					</tr>
				</thead>
				<tbody id="searchbody">
	
				</tbody>
			</table>
		</div>

		<form id="linefrm" action="${cp}/insertApprovalLine" method="post">
			<input type="hidden" id="p_Approval_emp_sq" name="p_Approval_emp_sq" value=""> 
			<input type="hidden" id="p_Reference_emp_sq" name="p_Reference_emp_sq" value="">
			<input type="hidden" id="param_apv_div_sq" name="param_apv_div_sq" value="">
			<div class="form-group text-center">
				<div style="padding-top: 20px; padding-bottom: 20px;">
					<button type="button" id="btnApproval"
						class="btn btn-secondary btn-sm col-sm-2">결재</button>
					<button type="button" id="btnReference"
						class="btn btn-secondary btn-sm col-sm-2" style="padding-left: 10px;">수신참조</button>
				</div>
			</div>
		</form>
		<hr style="margin-left: 50px; margin-bottom: 20px;">
		<div class="form-group">
			<div class="input-group" style="padding-left: 160px;">
				<label for="select" class=" form-control-label"><strong>결재라인명</strong></label>&nbsp;&nbsp;
				<form id="selfrm" action="${cp}/selectbox" method="post">
					<input type="hidden" id="selvalue" name="selvalue" value="" />
					<select name="select" id="selApvDiv" class="form-control" style="width: 500px;">
						<option selected="selected">결재라인명을 선택하세요.</option>
						<c:forEach items="${apvDivList}" var="divList">
							<option value="${divList.div_apv_sq }">${divList.div_apv_nm}</option>
						</c:forEach>
					</select>
				</form>
			</div>
		</div>
	</div>
</div>
<div>
	<div style="float: left; padding-top: 20px; margin-left: 100px; width: 350px; height: 300px;">
		<table class="table text-center">
			<thead>
				<tr>
					<th style="width: 550px;">결재자</th>
				</tr>
			</thead>
			<tbody id="approval_tbody">
		
			</tbody>
		</table>
	</div>
	<div style="float: right; padding-top: 20px; margin-right: 280px; width: 350px; height: 300px;">
		<table class="table text-center">
			<thead>
				<tr>
					<th style="width: 550px;">수신참조자</th>
				</tr>
			</thead>
			<tbody id="Reference_tbody">
				
			</tbody>
		</table>
	</div>
</div>

<div class="col col-sm-12">
	<div class="form-group text-center" style="margin-top: 30px;">
		<button id="addLine" class="btn btn-primary btn-sm col-sm-2">저장</button>
		<button id="cancel" class="btn btn-danger btn-sm col-sm-2">취소</button>
	</div>
</div>
	

	
<!-- treemenu 추가 -->
<link rel="stylesheet" type="text/css" href="${cp}/js/folding/jquery.treemenu.css">
<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="${cp}/js/folding/jquery.treemenu.js"></script>
<script>
		var newJquery = $.noConflict(true);
		
		newJquery(function(){
			newJquery(".tree").treemenu({delay:240}).openActive();
		});
		
		newJquery(document).ready(function () {
			
			newJquery(".btnajax").on("click", function() {
			
				var hr_sq = newJquery(this).data("hrsq");
				console.log(hr_sq);
				
				newJquery.ajax({
					url : "${cp}/ajaxSelectList",
					method : "POST",
					data : JSON.stringify(hr_sq), //JSON.stringify(데이터)는 해당 데이터를 문자열로 변환해줌
					dataType : "json",	//server로부터 받으려는 데이터타입
					contentType : "application/json; charset=UTF-8", //client가 전송하는 데이터타입 
					success : function(data){
						
						var arr_all = data;
						console.log(arr_all.length);
						var html = "";
						
						for(var i =0; i < arr_all.length; i++){
							html += "<tr role='row' align='center'>";	
							html += "<td><input type='checkbox' name='check_line' value='"+arr_all[i].emp_nm+"' /><input type='hidden' name='emp_sq' value='"+arr_all[i].emp_sq+"' /></td>";	
							html += "<td>"+arr_all[i].dpt_nm+"</td>";	
							if(arr_all[i].emp_grade == 1){html += "<td>사장</td>"};
							if(arr_all[i].emp_grade == 2){html += "<td>전무</td>"};
							if(arr_all[i].emp_grade == 3){html += "<td>이사</td>"};
							if(arr_all[i].emp_grade == 4){html += "<td>부장</td>"};
							if(arr_all[i].emp_grade == 5){html += "<td>과장</td>"};
							if(arr_all[i].emp_grade == 6){html += "<td>팀장</td>"};
							if(arr_all[i].emp_grade == 7){html += "<td>대리</td>"};
							if(arr_all[i].emp_grade == 8){html += "<td>사원</td>"};
							html += "<td>"+arr_all[i].emp_nm+"</td>";	
							html += "</tr>";	
						}
						newJquery("#searchbody").html(html);
					}
				}); 
			}); 
			
			newJquery("#btnSearch").on("click", function() {
				console.log(newJquery("#inputText").val());

				var param = {"inputText": newJquery("#inputText").val()};
				
				newJquery.ajax({
					url : "${cp}/ajaxSignLine",
					method : "POST",
					data : JSON.stringify(param), //JSON.stringify(데이터)는 해당 데이터를 문자열로 변환해줌
					dataType : "json",	//server로부터 받으려는 데이터타입
					contentType : "application/json; charset=UTF-8", //client가 전송하는 데이터타입 
					success : function(data){
						console.log(data);
						
						var arr = data;
						var html = "";
						
						for(var i =0; i < arr.length; i++){
							html += "<tr role='row' align='center'>";	
							html += "<td><input type='checkbox' name='check_line' value='"+arr[i].EMP_NM+"' /><input type='hidden' name='emp_sq' value='"+arr[i].EMP_SQ+"' /></td>";	
							html += "<td>"+arr[i].DPT_NM+"</td>";	
							if(arr[i].EMP_GRADE == 1){html += "<td>사장</td>"};
							if(arr[i].EMP_GRADE == 2){html += "<td>전무</td>"};
							if(arr[i].EMP_GRADE == 3){html += "<td>이사</td>"};
							if(arr[i].EMP_GRADE == 4){html += "<td>부장</td>"};
							if(arr[i].EMP_GRADE == 5){html += "<td>과장</td>"};
							if(arr[i].EMP_GRADE == 6){html += "<td>팀장</td>"};
							if(arr[i].EMP_GRADE == 7){html += "<td>대리</td>"};
							if(arr[i].EMP_GRADE == 8){html += "<td>사원</td>"};
							html += "<td>"+arr[i].EMP_NM+"</td>";	
							html += "</tr>";	
						}
						newJquery("#searchbody").html(html);
					}
				}); 
				
			});
			
			var Approval_emp_sq = new Array();
			var Reference_emp_sq = new Array();
			// 결재 버튼클릭시 이름이 결재자로 가게
			newJquery("#btnApproval").on("click", function() {

			newJquery("input[name=check_line]:checked").each(function() {
				
					console.log(newJquery(this).val());
					
 					var checkval = newJquery(this).val()+"\n";
 					var checknexval = newJquery(this).next().val();
					var html = "";
					
					html += "<tr>";
					html += "<td>"; 
					html += checkval;
					html += "</td>";
					html += "<td><button type='button' class='btnApvDel btn btn-outline-danger btn-sm' data-appsq='"+checknexval+"'><i class='fa fa-times'></i></button></td>";
					html += "</tr>";
					
					// 특정 인덱스번째값
					Approval_emp_sq.push(Number(checknexval));
 					
 					newJquery("#p_Approval_emp_sq").val(Approval_emp_sq);
					newJquery("#approval_tbody").append(html);
				});
 					newJquery("#btnApproval").submit();
					console.log(Approval_emp_sq);
			});	

			// 수신참조 버튼클릭시 이름이 수신참조자로 가게
			newJquery("#btnReference").on("click", function() {

				newJquery("input[name=check_line]:checked").each(function() {
					
					var checkval = newJquery(this).val()+"\n";
					var checknexval = newJquery(this).next().val();
					var html = "";
					
					html += "<tr>";
					html += "<td>"; 
					html += checkval;
					html += "</td>";
					html += "<td><button type='button' class='btnRefDel btn btn-outline-danger btn-sm' data-refsq='"+checknexval+"'><i class='fa fa-times'></i></button></td>";
					html += "</tr>";
					// 특정 인덱스번째값
					
					Reference_emp_sq.push(checknexval);
					
 					newJquery("#p_Reference_emp_sq").val(Reference_emp_sq);
					newJquery("#Reference_tbody").append(html);
				});
					newJquery("#btnReference").submit();
					console.log(Reference_emp_sq);
			});
			
			// reflash 할 곳에 담아주기
			var arr_reflash = new Array();
			var arr_reflash_ref = new Array();
			
			// select 선택값 
			newJquery("#selApvDiv").on("change", function(){
				// value 값으로 선택
				var selvalue = $(this).val();
			 	console.log(selvalue);
			 	
				newJquery("#param_apv_div_sq").val(selvalue);
				newJquery("#selvalue").val(selvalue);
				
				newJquery.ajax({
					url : "${cp}/ajaxSelectval",
					method : "POST",
					data : selvalue, //JSON.stringify(데이터)는 해당 데이터를 문자열로 변환해줌
					dataType : "json",	//server로부터 받으려는 데이터타입
					contentType : "application/json; charset=UTF-8", //client가 전송하는 데이터타입 
					success : function(data){
						console.log(data);
						
						arr_reflash = data;
						arr_reflash_ref = data;
						var arrsel = data;
						var html = "";
						Approval_emp_sq = [];
						
						for(var i =0; i < arrsel.length; i++){
							if(arrsel[i].apa_code == "1"){
								html += "<tr>";
								html += "<td>"; 
										html += arrsel[i].emp_nm +"\n";
										Approval_emp_sq.push(arrsel[i].apa_emp);
								html += "</td>";
								html += "<td><button type='button' class='btnApvDel btn btn-outline-danger btn-sm' data-appsq='"+arrsel[i].apa_emp+"'><i class='fa fa-times'></i></button></td>";
								html += "</tr>";
							}
						}
						
						newJquery("#approval_tbody").html(html);	
						console.log("결재자 번호 몇 개 "+ Approval_emp_sq );
						// 추가
	 					newJquery("#p_Approval_emp_sq").val(Approval_emp_sq);
						
						var Ref_html = "";
						Reference_emp_sq = [];
						
						for(var i =0; i < arrsel.length; i++){
							if(arrsel[i].apa_code == "2"){
								Ref_html += "<tr>";
								Ref_html += "<td>"; 
									Ref_html += arrsel[i].emp_nm +"\n";
									Reference_emp_sq.push(arrsel[i].apa_emp);
								Ref_html += "</td>";
								Ref_html += "<td><button type='button' class='btnRefDel btn btn-outline-danger btn-sm' data-refsq='"+arrsel[i].apa_emp+"'><i class='fa fa-times'></i></button></td>";
								Ref_html += "</tr>";
							}
						}
							
						newJquery("#Reference_tbody").html(Ref_html);	
						console.log("수신자 번호 몇 개 "+ Reference_emp_sq );
						//추가
	 					newJquery("#p_Reference_emp_sq").val(Reference_emp_sq);
					}
				}); 
			});
			
			//결재자 삭제 부분
			// 동적으로 버튼생성할때는 이런식으로 해야지 잘먹힘 
			$(document).on("click", ".btnApvDel", function() {
				
				var app_sq = newJquery(this).data("appsq");
				console.log(app_sq);
				
				var index = Approval_emp_sq.indexOf(app_sq);
				console.log('index value : '+ index);
				
				console.log('app_arr index : '+ Approval_emp_sq.splice(index,1));
				console.log('app_arr value : '+ Approval_emp_sq);
				
				console.log("&&&&&&&&");
				console.log('arr_reflash : '+arr_reflash);
				console.log("&&&&&&&&");
				
				var freshhtml = "";
				
				for(var i = 0; i < arr_reflash.length; i++){
					if(arr_reflash[i].apa_code == "1" && arr_reflash[i].apa_emp == app_sq){
						arr_reflash.splice(i,1);
					}
				}
				
				Approval_emp_sq = []; //배열 다시넣을때 초기화 
				for(var i = 0; i < arr_reflash.length; i++){
					if(arr_reflash[i].apa_code == "1"){
						console.log("*********************");
						console.log(arr_reflash[i].apa_emp);
						freshhtml += "<tr>";
						freshhtml += "<td>"; 
						freshhtml += arr_reflash[i].emp_nm +"\n";
						Approval_emp_sq.push(arr_reflash[i].apa_emp);
						freshhtml += "</td>";
						freshhtml += "<td><button type='button' class='btnApvDel btn btn-outline-danger btn-sm' data-appsq='"+arr_reflash[i].apa_emp+"'><i class='fa fa-times'></i></button></td>";
						freshhtml += "</tr>";
					}
				}
				newJquery("#approval_tbody").html(freshhtml);
				newJquery("#p_Approval_emp_sq").val(Approval_emp_sq);
			});
			
			// 수신참조자 삭제부분
			$(document).on("click", ".btnRefDel", function() {
				var ref_sq = newJquery(this).data("refsq");
				
				var index = Reference_emp_sq.indexOf(ref_sq);
				console.log('sdssd'+index);
				
				Reference_emp_sq.splice(index,1);
				
				for(var i = 0; i < arr_reflash_ref.length; i++){
					if(arr_reflash_ref[i].apa_code == "2" && arr_reflash_ref[i].apa_emp == ref_sq){
						arr_reflash_ref.splice(i,1);
					}
				}
				
				var refhtml = "";
				Reference_emp_sq = []; //배열 다시 초기화
				for(var i = 0; i < arr_reflash_ref.length; i++){
										
					if(arr_reflash_ref[i].apa_code == "2"){
						refhtml += "<tr>";
						refhtml += "<td>"; 
						refhtml += arr_reflash_ref[i].emp_nm +"\n";
						Reference_emp_sq.push(arr_reflash_ref[i].apa_emp);
						refhtml += "</td>";
						refhtml += "<td><button type='button' class='btnRefDel btn btn-outline-danger btn-sm' data-refsq='"+arr_reflash_ref[i].apa_emp+"'><i class='fa fa-times'></i></button></td>";
						refhtml += "</tr>";
					}
				}
				newJquery("#Reference_tbody").html(refhtml);
				newJquery("#p_Reference_emp_sq").val(Reference_emp_sq);
			});
			
			// 저장버튼 클릭시 인서트
			newJquery("#addLine").on("click", function() {
				newJquery("#linefrm").submit();
			});

			
	});


</script>
