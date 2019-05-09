<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>명함 등록</strong></h1>
	        </div>
	    </div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<form id="frm" action="${cp}/cardForm" method="post" role="form">
			<div class="form-group">
				<div class="col col-md-2"></div>
				<div class="col col-md-8">
					<input type="hidden" id="cal_emp_sq" name="cal_emp_sq" value="${cal_emp_sq}">
					<div class="card">
						<div class="card-body card-block">
							<div class="form-group">
								<label for="cal_nm" class=" form-control-label"><strong>이름</strong></label>
								<input type="text" id="cal_nm" name="cal_nm" placeholder="이름" class="form-control">
							</div>
							<div class="form-group">
								<label for="cal_grade" class=" form-control-label"><strong>직책(직급)</strong></label>
								<input type="text" id="cal_grade" name="cal_grade" placeholder="직책(직급)" class="form-control">
							</div>
							<div class="form-group">
								<label for="cal_com" class=" form-control-label"><strong>회사명</strong></label>
								<input type="text" id="cal_com" name="cal_com" placeholder="회사명" class="form-control">
							</div>
							<div class="form-group">
								<div class="form-group">
									<label for="cal_phone" class=" form-control-label"><strong>휴대폰 번호</strong></label>
									<input type="text" id="cal_phone" name="cal_phone" placeholder="휴대폰 번호" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<div class="form-group">
									<label for="cal_com_phone" class=" form-control-label"><strong>회사 전화번호</strong></label>
									<input type="text" id="cal_com_phone" name="cal_com_phone" placeholder="회사 전화번호" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label for="cal_mail" class=" form-control-label"><strong>이메일</strong></label>
								<input type="text" id="cal_mail" name="cal_mail" placeholder="이메일" class="form-control">
							</div>
							
							<div class="form-group">
								<label for="cal_addr" class=" form-control-label"><strong>주소</strong></label>
								<div class="input-group">
									<input type="text" id="cal_addr" name="cal_addr" placeholder="주소" class="form-control" >
					            	<button id="zipcodeBtn" type="button" class="btn btn-outline-secondary">검색</button>	
								</div>
							</div>
							<br>
							<div class="form-group text-center">
								<button id="btnUp" type="submit" class="btn btn-primary btn-sm col-sm-2">등록</button>
								<button id="btnCancel" type="button" class="btn btn-secondary col-sm-2 btn-sm">취소</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>

<form id="cancelFrm" action="${cp }/cardList" method="get" class="form-horizontal" role="form">
</form>

<!-- 다음 주소 api -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
	$(document).ready(function() {
		$("#btnCancel").on("click", function() {
			$("#cancelFrm").submit();
		});
		
		//우편번호 검색 버튼 클릭 이벤트
		$("#zipcodeBtn").on("click", function() {
			new daum.Postcode({
				oncomplete : function(data) {
					// 기본주소(도로주소) : data.roadAddress
					$("#cal_addr").val(data.roadAddress);
	
				}
			}).open();
		});
		
		
	});
</script>