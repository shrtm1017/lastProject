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
	<div class="col-lg-12">
		<form id="frm" action="${cp }/researchDetail" method="post" class="form-horizontal" role="form">
			<br>
			<div class="card">
				<div class="card-header" style="background-color: #002266; color: white;">
					<strong class="card-title">설문 참여</strong>
				</div>
				<br>
				<div class="card-body">
					<div id="divHead" class="col-lg-12">
						<div class="form-group">
							<div class="col col-sm-3" style="text-align: right;">
							</div>
							<div class="col col-sm-7">
								<label for="res_title" class="control-label">
									<c:choose>
				                    	<c:when test="${resVo.res_state == '1' }">
				                    		<h5>
					                    		<button type="button" class="btn btn-outline-info btn-sm">
					                    			<i class="fa fa-star-half-o"></i>
					                    			진행 중
					                    		</button>
					                    		&nbsp;
				                    			<strong>${resVo.res_nm }</strong>
				                    		</h5>
				                    	</c:when>
				                    	<c:when test="${resVo.res_state == '0' }">
				                    		<h5>
					                    		<button type="button" class="btn btn-outline-secondary btn-sm">
					                    			<i class="fa fa-star"></i>
					                    			마감
					                    		</button>
					                    		&nbsp;
					                    		<strong>${resVo.res_nm }</strong>
					                    	</h5>
										</c:when>
			                    	</c:choose>
								</label>
							</div>
						</div>
						<br>
						<div class="form-group">
							<div class="col col-sm-8" style="text-align: right; color: #5D5D5D;">
								<label for="res_title" class="control-label">마감일</label>
							</div>
							<div class="col col-sm-4">
								<div class="input-group date">
						            <label for="res_title" class="control-label" style="color: #5D5D5D;">
						            	<fmt:formatDate value="${resVo.res_end_dt }" pattern="yyyy-MM-dd" />
						            </label>
						        </div>
							</div>
						</div>
						<br>
						<div class="form-group">
							<div class="col col-sm-8" style="text-align: right; color: #5D5D5D;">
								<label for="res_title" class="control-label">등록자</label>
							</div>
							<div class="col col-sm-4">
								<div class="input-group date">
						            <label for="res_title" class="control-label" style="color: #5D5D5D;">
						            	${res_emp_nm }
						            </label>
						        </div>
							</div>
						</div>
						<br>
						<hr>
					</div>
					<div id="divContent" class="col-lg-12">
						<c:forEach items="${queList }" var="question">
							<div class="form-group">
								<br><br>
								<div class="col col-sm-3" style="text-align: right;">
									<label class="control-label">Q.</label>
								</div>
								<div class="col col-sm-7">
									<label class="control-label">${question.que_con }</label>
								</div>
							</div>
							
							<c:if test="${attend == '0' && resVo.res_state == '1' }">
								<c:forEach items="${ansList }" var="answer">
									<c:if test="${answer.ans_que_sq == question.que_sq }">
										<div class="form-group">
											<br>
											<div class="col col-sm-3">
											</div>
											<div class="col col-sm-7">
												<input type="radio" name="radio${question.que_sq }" value="${answer.ans_sq }" class="option form-check-input">
													<label class="control-label">${answer.ans_con }</label>
												</input>
											</div>
										</div>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${attend == '1' || resVo.res_state == '0' }">
								<c:forEach items="${resultList }" var="answer">
									<c:if test="${answer.ans_que_sq == question.que_sq }">
										<div class="form-group">
											<div class="col col-sm-3" style="text-align: right;">
												<i class="fa fa-user"></i>
												${answer.ans_peo }
											</div>
											<div class="progress mb-1" style="width: 600px; height: 25px; color: black;">
												<div class="progress-bar bg-warning" role="progressbar" style="color: black; font-size: 120%; width: ${answer.ans_cnt}%;" aria-valuenow="${answer.ans_cnt}" aria-valuemin="0" aria-valuemax="100">
													<strong>${answer.ans_con}</strong>
												</div>
											</div>
										</div>
									</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>
					</div>
					
					<div class="form-group">
						<div class="col col-sm-12" style="text-align: center;">
							<br><br>
							<c:if test="${attend == '0' && resVo.res_state == '1' }">
								<button id="submitBtn" type="button" class="btn btn-primary col-sm-1">제출</button>
							</c:if>
							<c:if test="${attend == '1' && resVo.res_state == '1' }">
								<button id="resubmitBtn" type="button" class="btn btn-primary col-sm-1">재제출</button>
							</c:if>
							
							<c:if test="${employeeVo.emp_sq == resVo.res_emp_sq }">
								<button id="updateBtn" type="button" class="btn btn-secondary col-sm-1">수정</button>
								<button id="deleteBtn" type="button" class="btn btn-secondary col-sm-1">삭제</button>
							</c:if>
							
							<button id="confirmBtn" type="button" class="btn btn-secondary col-sm-1">확인</button>
						</div>
					</div>
					<div id="divHidden">
					</div>
					<input type="hidden" name="res_sq" value="${resVo.res_sq }" />
				</div>
			</div>
		</form>
	</div>
</div>

<form id="listFrm" action="${cp }/researchList" method="get" class="form-horizontal" role="form">
</form>

<form id="reFrm" action="${cp }/researchResubmit" method="get" class="form-horizontal" role="form">
	<input type="hidden" name="res_sq" value="${resVo.res_sq }" />
</form>

<form id="updFrm" action="${cp }/researchUpdate" method="get" class="form-horizontal" role="form">
	<input type="hidden" name="res_sq" value="${resVo.res_sq }" />
</form>

<form id="delFrm" action="${cp }/researchDelete" method="post" class="form-horizontal" role="form">
	<input type="hidden" name="res_sq" value="${resVo.res_sq }" />
</form>

<script>
	$(document).ready(function() {
		<c:if test="${msg != null}">
			alert("${msg}");
		</c:if>
		
		$("#submitBtn").on("click", function() {
			var queList  = new Array(); 
			
			<c:forEach items="${queList}" var="question">
				queList.push("${question.que_sq}");
			</c:forEach>
			
			for (var i = 0; i < queList.length; i++) {
				if ($("input:radio[name='radio" + queList[i] + "']").is(":checked") == true) {
					var ans_sq = $("input[name='radio" + queList[i] + "']:checked").val();
					
					var html = "<input type='hidden' name='option' value='" + queList[i] + "_" + ans_sq + "' />";
					$("#divHidden").append(html);
				} else {
					alert("옵션을 선택해 주세요.");
					$("#divHidden").html("");
					return;
				}
			}
			
			$("#frm").submit();
		});
		
		$("#resubmitBtn").on("click", function() {
			$("#reFrm").submit();
		});
		
		$("#updateBtn").on("click", function() {
			$("#updFrm").submit();
		});
		
		$("#deleteBtn").on("click", function() {
			$("#delFrm").submit();
		});
		
		$("#confirmBtn").on("click", function() {
			$("#listFrm").submit();
		});
	});
</script>