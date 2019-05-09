<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>사원 연락처 수정</strong></h1>
	        </div>
	    </div>
	</div>
</div>
<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<form id="update_frm" action="${cp }/employeeContact_Update">
			<div class="form-group">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<div class="card">
						<div class="card-body card-block">
							<div class="form-group">
								<label for="cal_nm" class=" form-control-label"><strong>사원번호</strong></label>
								<input type="text" id="cal_nm" name="cal_nm" placeholder="사원번호"
									class="form-control" value="${EmployeeContactManage.emp_sq }"
									readonly="readonly">
							</div>
							<div class="form-group">
								<label for="cal_nm" class=" form-control-label"><strong>이름</strong></label>
								<input type="text" id="cal_nm" name="cal_nm" placeholder="이름"
									class="form-control" value="${EmployeeContactManage.emp_nm }"
									readonly="readonly">
							</div>
							<div class="form-group">
								<label for="cal_grade" class=" form-control-label"><strong>부서</strong></label>
								<input type="text" id="cal_grade" name="cal_grade"
									placeholder="부서" class="form-control"
									value="${EmployeeContactManage.dpt_nm }" readonly="readonly">
							</div>
							<div class="form-group">
								<label for="cal_phone" class=" form-control-label"><strong>휴대폰 번호</strong></label>
								<input type="text" id="emp_phone" name="emp_phone"
									placeholder="휴대폰 번호" class="form-control"
									value="${EmployeeContactManage.emp_phone }">
							</div>
							<div class="form-group">
								<label for="cal_com_phone" class=" form-control-label"><strong>회사 번호</strong></label>
								<input type="text" id="emp_com_phone" name="emp_com_phone"
									placeholder="Enter your company-number" class="form-control"
									value="${EmployeeContactManage.emp_com_phone }">
							</div>
							<div class="form-group">
								<label for="cal_mail" class=" form-control-label"><strong>이메일</strong></label>
								<input type="text" id="emp_psn_email" name="emp_psn_email"
									placeholder="Enter your Email" class="form-control"
									value="${EmployeeContactManage.emp_psn_email }">
							</div>
							<br>
							<div class="form-group text-center">
								<button type="button" class="btn btn-primary btn-sm col-sm-2" id="employee_modify">저장</button>
								<button id="btnCancel" type="button" class="btn btn-secondary btn-sm col-sm-2">취소</button>
							</div>
						</div>
						<input type="hidden" name="emp_sq" value="${EmployeeContactManage.emp_sq}">
					</div>
				</div>
			</div>
		</form>
	</div>
</div>

<form id="cancelFrm" action="${cp}/employeeContactMange" method="get" class="form-horizontal" role="form">
</form>

<script type="text/javascript">
	$(document).ready(function() {
		$("#employee_modify").click(function() {
			$("#update_frm").submit();
		})
		
		$("#btnCancel").on("click", function() {
			$("#cancelFrm").submit();
		});
	});
</script>