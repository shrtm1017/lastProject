<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>사원 관리</strong></h1>
	        </div>
	    </div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<form id="frm" action="${cp }/userUpdate" method="post"
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
			            <td rowspan="4" style="width: 200px; text-align: center;">
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
			            <td colspan="2" style="width: 650px;">${eVo.emp_nm }</td>
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
			            <td colspan="2" style="width: 650px;">
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
			            <td colspan="2" style="width: 850px;">
			            	<div class="col col-sm-12">
			            		<input type="text" name="emp_com_email" placeholder="회사이메일" class="form-control" value="${eVo.emp_com_email }">
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
			            <th scope="row" style="width: 200px;">부서</th>
			            <td colspan="2" style="width: 850px;">
			            	<div class="col col-sm-2">
				            	<select class="selDpt1 form-control" name="emp_dpt">
				            		<option value="1"<c:if test="${departmentVo.dpt_sq == '1' || departmentVo.dpt_hr_sq == '1'}">selected</c:if>>마스터부</option>
				            		<option value="2"<c:if test="${departmentVo.dpt_sq == '2' || departmentVo.dpt_hr_sq == '2'}">selected</c:if>>관리부</option>
				            		<option value="3"<c:if test="${departmentVo.dpt_sq == '3' || departmentVo.dpt_hr_sq == '3'}">selected</c:if>>경영기획부</option>
				            		<option value="4"<c:if test="${departmentVo.dpt_sq == '4' || departmentVo.dpt_hr_sq == '4'}">selected</c:if>>SW개발부</option>
				            		<option value="5"<c:if test="${departmentVo.dpt_sq == '5' || departmentVo.dpt_hr_sq == '5'}">selected</c:if>>디자인부</option>
				            	</select>
			            	</div>
			            	<div class="col col-sm-2">
				            	<select class="selDpt2 form-control" name="dpt_hr_sq">
				            		<option value="">--------------</option>
				            		<c:forEach var="dpt" items="${dptList }">
				            			<c:if test="${dpt.dpt_hr_sq == departmentVo.dpt_hr_sq }">
				            				<option value="${dpt.dpt_sq}"
				            					<c:if test="${dpt.dpt_sq == departmentVo.dpt_sq }">selected</c:if>
				            				>${dpt.dpt_nm}</option>
				            			</c:if>
				            		</c:forEach>
				            	</select>
			            	</div>
			            </td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;">직급</th>
			            <td colspan="2" style="width: 850px;">
			            	<div class="col col-sm-2">
				            	<select class="form-control" name="emp_grade">
				            		<option value="1" <c:if test="${eVo.emp_grade == '1' }">selected</c:if>>사장</option>
				            		<option value="2" <c:if test="${eVo.emp_grade == '2' }">selected</c:if>>전무</option>
				            		<option value="3" <c:if test="${eVo.emp_grade == '3' }">selected</c:if>>이사</option>
				            		<option value="4" <c:if test="${eVo.emp_grade == '4' }">selected</c:if>>부장</option>
				            		<option value="5" <c:if test="${eVo.emp_grade == '5' }">selected</c:if>>과장</option>
				            		<option value="6" <c:if test="${eVo.emp_grade == '6' }">selected</c:if>>팀장</option>
				            		<option value="7" <c:if test="${eVo.emp_grade == '7' }">selected</c:if>>대리</option>
				            		<option value="8" <c:if test="${eVo.emp_grade == '8' }">selected</c:if>>사원</option>
				            	</select>
			            	</div>
			            </td>
			        </tr>
			        <tr>
			        	<th scope="row" style="width: 200px;">입사일</th>
			            <td colspan="2" style="width: 850px;">
			            	<fmt:formatDate value="${eVo.emp_ent }" pattern="yyyy-MM-dd" />
			            </td>
			        </tr>
			        <tr>
			        	<th scope="row" style="width: 200px;">최종 수정일</th>
			            <td colspan="2" style="width: 850px;">
			            	<fmt:formatDate value="${eVo.emp_fnl_mod }" pattern="yyyy-MM-dd" />
			            </td>
		        	</tr>
			    </tbody>
			</table>
			
			<input type="hidden" name="emp_sq" value="${eVo.emp_sq }" />
		</form>
		<br>
		<button type="button" id="updBtn" class="btn btn-primary">저장</button>
		<button type="button" id="cancelBtn" class="btn btn-danger">취소</button>
	</div>
</div>

<form id="infoFrm" action="${cp }/userDetail" method="get" class="form-horizontal" role="form">
	<input type="hidden" name="emp_sq" value="${eVo.emp_sq }" />
</form>

<!-- 다음 주소 api -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
	$(document).ready(function() {
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
		
		$("#updBtn").on("click", function() {
			$("#frm").submit();
		});
		
		$("#cancelBtn").on("click", function() {
			$("#infoFrm").submit();
		});
		
		$(".selDpt1").on("change", function() {
			$(".selDpt2").html("");
			
			var dpt_sq = $(this).val();
			
			var dptSqList  = new Array(); 
			var dptHrList  = new Array(); 
			var dptNmList  = new Array(); 
			
			var text = "<option value=''>--------------</option>";
			
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
	});
</script>