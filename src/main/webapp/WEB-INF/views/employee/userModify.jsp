<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>내 정보</strong></h1>
	        </div>
	    </div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<form id="frm" action="${cp }/userModify" method="post"
					class="form-horizontal" role="form" enctype="multipart/form-data">
			<table class="table table-bordered">
				<thead style="background-color: #91B4C8; color: white;">
			       	<tr>
			           	<th scope="col" colspan="4">기본 정보</th>
			      	</tr>
			   	</thead>
			    <tbody>
			        <tr>
			            <th scope="row" style="width: 200px;">사원번호</th>
			            <td colspan="2" style="width: 650px;">${eVo.emp_sq }</td>
			            <td rowspan="5" style="width: 200px; text-align: center;">
			            	<img src="${cp }/userImg?emp_sq=${eVo.emp_sq }" />
			            	<label class="btn btn-sm btn-outline-secondary">
						    	사진등록 <input type="file" name="userImg" style="display: none;">
						    </label>
						    <div id="divFile">
			            	</div>
						</td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;">이름</th>
			            <td colspan="2" style="width: 650px;">
			            	${eVo.emp_nm }
			            	&nbsp;&nbsp;
			            	<button type="button" id="signBtn" class="btn btn-outline-secondary btn-sm"
			            		data-toggle="modal" data-target="#signModal">
			            		서명등록
			            	</button>
			            </td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;" rowspan="2">비밀번호</th>
			            <td colspan="2" style="width: 650px;">
			            	<div class="col col-sm-12">
			            		<input type="password" id="pass" name="emp_pass" placeholder="새 비밀번호" class="form-control">
			            	</div>
			            </td>
			        </tr>
			        <tr>
			            <td colspan="2" style="width: 650px;">
			            	<div class="col col-sm-12">
			            		<input type="password" id="passCheck" placeholder="새 비밀번호 확인" class="form-control">
			            	</div>
			            </td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;" rowspan="2">주소</th>
			            <td colspan="2" style="width: 650px;">
			            	<div class="col col-sm-11">
			            		<input type="text" id="addr1" name="emp_addr1" placeholder="주소" class="form-control" value="${eVo.emp_addr1 }" readonly>
			            	</div>
			            	<button id="zipcodeBtn" type="button" class="btn btn-outline-secondary">검색</button>	
			            </td>
			        </tr>
			        <tr>
			            <td colspan="3" style="width: 650px;">
			            	<div class="col col-sm-12">
			            		<input type="text" id="addr2" name="emp_addr2" placeholder="상세 주소" class="form-control" value="${eVo.emp_addr2 }">
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
			            <th scope="row" style="width: 200px;">전화번호</th>
			            <td colspan="2" style="width: 850px;">
			            	<div class="col col-sm-12">
			            		<input type="text" name="emp_phone" placeholder="전화번호" class="form-control" value="${eVo.emp_phone }">
			            	</div>
			            </td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;">개인이메일</th>
			            <td colspan="2" style="width: 850px;">
			            	<div class="col col-sm-12">
			            		<input type="text" name="emp_psn_email" placeholder="개인이메일" class="form-control" value="${eVo.emp_psn_email }">
			            	</div>
			            </td>
			        </tr>
			        <tr>
			        	<th scope="row" style="width: 200px;">회사이메일</th>
			            <td colspan="2" style="width: 850px;">${eVo.emp_com_email }</td>
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
			            <th scope="row" style="width: 200px;">부서</th>
			            <td colspan="2" style="width: 850px;">${departmentVo.dpt_nm }</td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;">직급</th>
			            <td colspan="2" style="width: 850px;">
			            	<c:choose>
		                    	<c:when test="${eVo.emp_grade == '1' }">사장</c:when>
		                    	<c:when test="${eVo.emp_grade == '2' }">전무</c:when>
		                    	<c:when test="${eVo.emp_grade == '3' }">이사</c:when>
		                    	<c:when test="${eVo.emp_grade == '4' }">부장</c:when>
		                    	<c:when test="${eVo.emp_grade == '5' }">과장</c:when>
		                    	<c:when test="${eVo.emp_grade == '6' }">팀장</c:when>
		                    	<c:when test="${eVo.emp_grade == '7' }">대리</c:when>
		                    	<c:otherwise>사원</c:otherwise>
		                    </c:choose>
			            </td>
			        </tr>
			        <tr>
			        	<th scope="row" style="width: 200px;">입사일</th>
			            <td colspan="2" style="width: 850px;">
			            	<fmt:formatDate value="${eVo.emp_ent }" pattern="yyyy-MM-dd" />
			            </td>
			        </tr>
			    </tbody>
			</table>
		</form>
		<br>
		<button type="button" id="updBtn" class="btn btn-primary">저장</button>
		<button type="button" id="cancelBtn" class="btn btn-danger">취소</button>
	</div>
</div>

<div class="modal fade" id="signModal" tabindex="-1" role="dialog" aria-labelledby="signModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document" style="width: 350px">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="signModalLabel">
					서명 등록
				</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="input-group col-sm-7">
					<button type="button" class="btn btn-sm" style="background: white; color: black;">
						<i class="fa fa-magic"></i>&nbsp;브러쉬
					</button>
					<select id="selwidth" class="form-control-sm">
		                <option value="1">1px</option>
						<option value="3" selected>3px</option>
						<option value="5">5px</option>
		            </select>
		        </div>
				<canvas id="canvas" width="300" height="200">
					이 브라우저는 캔버스를 지원하지 않습니다.
				</canvas>
			</div>
			<div class="modal-footer">
				<button type="button" id="clearBtn" class="btn btn-secondary btn-sm col-sm-3">지우기</button>
				&nbsp;
				<button type="button" id="saveBtn" class="btn btn-primary btn-sm col-sm-3">저장</button>
			</div>
		</div>
	</div>
</div>

<form id="infoFrm" action="${cp }/userInfo" method="get" class="form-horizontal" role="form">
</form>
<form id="signFrm" action="${cp }/userSignSave" method="post" class="form-horizontal" role="form">
	<input type="hidden" id="signUrl" name="signUrl" />
</form>

<!-- 다음 주소 api -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
	var canvas;
	var ctx;
	var sx, sy;						// 현재 위치
	var drawing = false;			// 현재 그리는 중인가?

	window.onload = function() {
		canvas = document.getElementById("canvas");
		if (canvas == null || canvas.getContext == null) return;
		ctx = canvas.getContext("2d");
		ctx.lineCap="round";
		ctx.lineWidth = 3;
		
		// 현재 위치를 저장한다.
		canvas.onmousedown = function(e) {
			e.preventDefault();
			sx = canvasX(e.clientX);
			sy = canvasY(e.clientY);
			drawing = true;
		}
		
		// 현재 위치에서 새로 이동한 곳까지 선을 그린다.
		canvas.onmousemove = function(e) {
			if (drawing) {
				e.preventDefault();
				ctx.beginPath();
				ctx.moveTo(sx, sy);
				sx = canvasX(e.clientX);
				sy = canvasY(e.clientY);
				ctx.lineTo(sx, sy);
				ctx.stroke();
			}
		}
	
		// 그리기를 종료한다.
		canvas.onmouseup = function(e) {
			drawing = false;
		}			
	}

	function canvasX(clientX) {
		var bound = canvas.getBoundingClientRect();
		var bw = 5;
		return (clientX - bound.left - bw) * (canvas.width / (bound.width - bw * 2));
	}
	
	function canvasY(clientY) {
		var bound = canvas.getBoundingClientRect();
		var bw = 5;
		return (clientY - bound.top - bw) * (canvas.height / (bound.height - bw * 2));
	}

	$(document).ready(function() {
		<c:if test="${msg != null}">
			alert("${msg}");
		</c:if>
	
		$("#selwidth").on("change", function() {
			ctx.lineWidth = selwidth.value;
		});
	
		$("#clearBtn").on("click", function() {
			ctx.clearRect(0, 0, canvas.width, canvas.height);
		});
		
		$("#saveBtn").on("click", function() {
			var dataURL = canvas.toDataURL();
			
			$("#signUrl").val(dataURL);
			$("#signFrm").submit();
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
		
		$("#updBtn").on("click", function() {
			var pass = $("#pass").val();
			var passCheck = $("#passCheck").val();
			
			if (pass != passCheck) {
				alert("비밀번호가 일치하지 않습니다.");
			} else {
				$("#frm").submit();
			}
		});
		
		$("#cancelBtn").on("click", function() {
			$("#infoFrm").submit();
		});
	});
</script>