<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>사원 등록</strong></h1>
	        </div>
	    </div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<form id="frm" action="${cp }/userInsert" method="post"
					class="form-horizontal" role="form" enctype="multipart/form-data">
			<sup style="color: red;">*</sup> <small>표시는 필수 입력 항목입니다.</small>
			<table class="table table-bordered">
				<thead style="background-color: #91B4C8; color: white;">
			       	<tr>
			           	<th scope="col" colspan="4">기본 정보</th>
			      	</tr>
			   	</thead>
			    <tbody>
			        <tr>
			            <th scope="row" style="width: 200px;">사원번호 <sup style="color: red;">*</sup></th>
			            <td colspan="2" style="width: 650px;">
			            	<div class="col col-sm-10">
			            		<input type="text" id="emp_sq" name="emp_sq" placeholder="숫자만 입력 가능합니다." 
			            			class="form-control onlyNumber" maxlength="30">
			            	</div>
			            	<button type="button" class="btn btn-sm btn-warning" id="idCheckBtn">사원번호 중복 체크</button>
			            </td>
			            <td rowspan="5" style="width: 200px; text-align: center;">
			            	<img src="/images/noimg.png" />
			            	<label class="btn btn-sm btn-outline-secondary">
						    	사진등록 <input type="file" name="userImg" style="display: none;">
						    </label>
						    <div id="divFile">
			            	</div>
						</td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;">이름 <sup style="color: red;">*</sup></th>
			            <td colspan="2" style="width: 650px;">
			            	<div class="col col-sm-12">
			            		<input type="text" id="emp_nm" name="emp_nm" placeholder="이름" class="form-control">
			            	</div>
						</td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;" rowspan="2">비밀번호 <sup style="color: red;">*</sup></th>
			            <td colspan="2" style="width: 650px;">
			            	<div class="col col-sm-12">
			            		<input type="password" id="pass" name="emp_pass" placeholder="비밀번호" class="form-control">
			            	</div>
			            </td>
			        </tr>
			        <tr>
			            <td colspan="2" style="width: 650px;">
			            	<div class="col col-sm-12">
			            		<input type="password" id="passCheck" placeholder="비밀번호 확인" class="form-control">
			            	</div>
			            </td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;" rowspan="2">주소</th>
			            <td colspan="2" style="width: 650px;">
			            	<div class="col col-sm-11">
			            		<input type="text" id="addr1" name="emp_addr1" placeholder="주소" class="form-control" readonly>
			            	</div>
			            	<button id="zipcodeBtn" type="button" class="btn btn-outline-secondary">검색</button>	
			            </td>
			        </tr>
			        <tr>
			            <td colspan="3" style="width: 650px;">
			            	<div class="col col-sm-12">
			            		<input type="text" id="addr2" name="emp_addr2" placeholder="상세 주소" class="form-control">
			            	</div>
			            </td>
			        </tr>
			    </tbody>
			</table>
			
			<table class="table table-bordered">
				<thead style="background-color: #91B4C8; color: white;">
			       	<tr>
			           	<th scope="col" colspan="4">연락처 정보</th>
			      	</tr>
			   	</thead>
			    <tbody>
			        <tr>
			            <th scope="row" style="width: 200px;">전화번호 <sup style="color: red;">*</sup></th>
			            <td colspan="2" style="width: 850px;">
			            	<div class="col col-sm-12">
			            		<input type="text" id="emp_phone" name="emp_phone" 
			            			placeholder="-를 제외한 숫자만 입력하세요." class="form-control onlyNumber">
			            	</div>
			            </td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;">개인이메일 <sup style="color: red;">*</sup></th>
			            <td colspan="2" style="width: 850px;">
			            	<div class="col col-sm-12">
			            		<input type="text" id="emp_psn_email" name="emp_psn_email" placeholder="개인이메일" class="form-control">
			            	</div>
			            </td>
			        </tr>
			        <tr>
			        	<th scope="row" style="width: 200px;">회사이메일 <sup style="color: red;">*</sup></th>
			            <td colspan="2" style="width: 850px;">
			            	<div class="col col-sm-12">
			            		<input type="text" id="emp_com_email" name="emp_com_email" placeholder="회사이메일" class="form-control">
			            	</div>
			            </td>
			        </tr>
			    </tbody>
			</table>
			
			<table class="table table-bordered">
				<thead style="background-color: #91B4C8; color: white;">
			       	<tr>
			           	<th scope="col" colspan="4">부서 정보</th>
			      	</tr>
			   	</thead>
			    <tbody>
			        <tr>
			            <th scope="row" style="width: 200px;">부서 <sup style="color: red;">*</sup></th>
			            <td colspan="2" style="width: 850px;">
			            	<div class="col col-sm-2">
				            	<select class="selDpt1 form-control" name="emp_dpt">
				            		<option value="1">마스터부</option>
				            		<option value="2">관리부</option>
				            		<option value="3">경영기획부</option>
				            		<option value="4">SW개발부</option>
				            		<option value="5">디자인부</option>
				            	</select>
			            	</div>
			            	<div class="col col-sm-2">
				            	<select class="selDpt2 form-control" name="dpt_hr_sq">
				            		<option value="">----------------</option>
				            	</select>
			            	</div>
			            </td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;">직급 <sup style="color: red;">*</sup></th>
			            <td colspan="2" style="width: 850px;">
			            	<div class="col col-sm-4">
				            	<select class="form-control" name="emp_grade">
				            		<option value="1">사장</option>
				            		<option value="2">전무</option>
				            		<option value="3">이사</option>
				            		<option value="4">부장</option>
				            		<option value="5">과장</option>
				            		<option value="6">팀장</option>
				            		<option value="7">대리</option>
				            		<option value="8" selected>사원</option>
				            	</select>
			            	</div>
			            </td>
			        </tr>
			        <tr>
			        	<th scope="row" style="width: 200px;">입사일 <sup style="color: red;">*</sup></th>
			            <td colspan="2" style="width: 850px;">
			           	 	<div class="col col-sm-4">
				            	<div class="input-group date">
						            <input type="text" class="form-control" id="emp_ent" name="emp_ent"/>
						            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						        </div>
					        </div>
			        	</td>
			        </tr>
			    </tbody>
			</table>
			
			<input type="hidden" id="idCheck" value="" />
		</form>
		<br>
		<button type="button" id="insBtn" class="btn btn-primary">저장</button>
	</div>
</div>

<!-- 다음 주소 api -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
	$(document).ready(function() {
		<c:if test="${msg != null}">
			alert("${msg}");
		</c:if>
		
		$(".input-group.date").datepicker({
            calendarWeeks: false,
            todayHighlight: true,
            autoclose: true,
            format: "yyyy/mm/dd",
            language: "kr"
        });

		$("input[name=userImg]").on("change", function () {
            var file = this.files[0],
            fileName = file.name,
            fileSize = file.size;

            $("#divFile").html("<i class='fa fa-picture-o'></i> " + fileName);
        });
		
		//우편번호 검색 버튼 클릭 이벤트
		$("#zipcodeBtn").on("click", function() {
			new daum.Postcode({
				oncomplete : function(data) {
					// 기본주소(도로주소) : data.roadAddress
					$("#addr1").val(data.roadAddress);
	
					// 상세주소 input focus
					$("#addr2").focus();
				}
			}).open();
		});
		
		$(".selDpt1").on("change", function() {
			$(".selDpt2").html("");
			
			var dpt_sq = $(this).val();
			
			var dptSqList  = new Array(); 
			var dptHrList  = new Array(); 
			var dptNmList  = new Array(); 
			
			var text = "<option value=''>----------------</option>";
			
			<c:forEach items="${dptList}" var="dpt">
				dptSqList.push("${dpt.dpt_sq}");
				dptHrList.push("${dpt.dpt_hr_sq}");
				dptNmList.push("${dpt.dpt_nm}");
			</c:forEach>

			for (var i = 0; i < dptSqList.length; i++) {
				if (dptHrList[i] == dpt_sq) {
					text += "<option value='" + dptSqList[i] + "'>" + dptNmList[i] + "</option>";
				}
			}			
			
			$(".selDpt2").html(text);
		});
		
		$("#pass").on("change", function() {
			$("#passCheck").removeClass("is-invalid");
			
			var pass = $("#pass").val();
			var passCheck = $("#passCheck").val();
			
			if (pass != passCheck) {
				$("#passCheck").addClass("is-invalid");
			} else {
				$("#passCheck").addClass("is-valid");
			}
		});
		
		$("#passCheck").on("change", function() {
			$("#passCheck").removeClass("is-invalid");
			
			var pass = $("#pass").val();
			var passCheck = $("#passCheck").val();
			
			if (pass != passCheck) {
				$("#passCheck").addClass("is-invalid");
			} else {
				$("#passCheck").addClass("is-valid");
			}
		});
		
		$("#insBtn").on("click", function() {
			var pass = $("#pass").val();
			var passCheck = $("#passCheck").val();
			var idCheck = $("#idCheck").val();
			
			// 빈 항목 체크
			if ($("#emp_sq").val().trim() == "") {
				alert("사원번호를 입력해 주세요.");
				$("#emp_sq").focus();
				return;
			}
			if ($("#emp_nm").val().trim() == "") {
				alert("사원명을 입력해 주세요.");
				$("#emp_nm").focus();
				return;
			}
			if (pass.trim() == "") {
				alert("비밀번호를 입력해 주세요.");
				$("#pass").focus();
				return;
			}
			if ($("#emp_phone").val().trim() == "") {
				alert("전화번호를 입력해 주세요.");
				$("#emp_phone").focus();
				return;
			}
			if ($("#emp_psn_email").val().trim() == "") {
				alert("개인이메일을 입력해 주세요.");
				$("#emp_psn_email").focus();
				return;
			}
			if ($("#emp_com_email").val().trim() == "") {
				alert("회사이메일을 입력해 주세요.");
				$("#emp_com_email").focus();
				return;
			}
			if ($("#emp_ent").val().trim() == "") {
				alert("입사일을 선택해 주세요.");
				$("#emp_ent").focus();
				return;
			}
			
			// 비밀번호 재확인
			if (pass != passCheck) {
				alert("비밀번호가 일치하지 않습니다.");
				$("#passCheck").focus();
				return;
			}
			
			// 사원번호 중복 체크
			if (idCheck == "") {
				alert("사원번호 중복 체크를 해 주세요.");
				$("#emp_sq").removeClass("is-valid");
				$("#emp_sq").addClass("is-invalid");
				return;
			}
			if (idCheck == "false") {
				alert("등록할 수 없는 사원번호 입니다.");
				return;
			}
			
			$("#frm").submit();
		});
		
		$("#emp_sq").on("change", function() {
			$("#idCheck").val("");
		});
		
		$("#idCheckBtn").on("click", function() {
			var emp_sq = $("#emp_sq").val();
			
			if (emp_sq != "") {
				$.ajax({
		    		async	 : true,
					url 	 : "${cp }/userIdCheck",
					method 	 : "post",
					data 	 : emp_sq,  
					dataType : "json",
					contentType : "application/json; charset=utf-8",
					success  : function(data) {
						// 0 : 등록 가능
						// 1 : 중복
						if (data == 0) {
							alert("등록 가능한 사원번호 입니다.");
							$("#idCheck").val("true");
							$("#emp_sq").removeClass("is-invalid");
							$("#emp_sq").addClass("is-valid");
						} 
						else if (data == 1) {
							alert("등록할 수 없는 사원번호 입니다.");
							$("#idCheck").val("false");
							$("#emp_sq").removeClass("is-valid");
							$("#emp_sq").addClass("is-invalid");
						}
					}
				});
			} else {
				alert("사원번호를 입력해 주세요.");
				$("#idCheck").val("");
				$("#emp_sq").removeClass("is-valid");
				$("#emp_sq").addClass("is-invalid");
				$("#emp_sq").focus();
			}
		});
		
		$(".onlyNumber").keyup(function(event){
            if (!(event.keyCode >=37 && event.keyCode<=40)) {
                var inputVal = jQuery(this).val();
                jQuery(this).val(inputVal.replace(/[^0-9]/gi,''));
            }
        });
	});
</script>
