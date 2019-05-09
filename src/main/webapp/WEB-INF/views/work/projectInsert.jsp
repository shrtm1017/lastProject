<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="breadcrumbs">
	<div class="col-sm-4">
		<div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>프로젝트 생성</strong></h1>
			</div>
		</div>
	</div>
</div>

<form id="frm" action="${cp}/projectInsert" method="post">
<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<div class="col col-md-2"></div>
		<div class="col col-md-8">
			<div class="card">
				<div class="card-body card-block">
					<div class="form-group">
						<label><strong>공개여부</strong></label>
						&nbsp;
						<label class="switch switch-text switch-info switch-pill">
							<input name="inputOpen" type="hidden"/>
							<input name="checkOpen" type="checkbox" class="switch-input"/>
							<span data-off="Off" data-on="On" class="switch-label"></span>
							<span class="switch-handle"></span>
						</label>
					</div>
					<hr style="border: dashed 1px #A6A6A6;">
					<div class="form-group">
						<label><strong>프로젝트명</strong></label>
						<input name="inputProjectName" type="text" class="form-control"/>
					</div>
					
					<div class="form-group">
						<label><strong>설명</strong></label>
						<input name="inputExplain" type="text" class="form-control"/>
					</div>
					
					<div class="form-group">
						<label><strong>구성원</strong></label>
						<input type="hidden" name="selectMember"/>
						<select name="selectProMem" data-placeholder="구성원" multiple="multiple" 
						class="standardSelect" tabindex="-1" style="display: none;">
							<c:forEach items="${departmentList}" var="department">
								<optgroup label="${department.dpt_nm}">
									<c:forEach items="${sessionScope.employeeList}" var="employee">
										<c:choose>
											<%-- fn태그(시작되는 문자열값을 비교하기위해 사용) --%>
											<%-- 
												fn은 el안에서 사용하며 startsWith의 1번째값은 대상값, 2번째값은 비교값으로
												${employee.emp_dpt} 형식으로 값을 표현해야하지만
												여기서는 그냥 employee.emp_dpt 형식으로 사용해야함!
											 --%>
											<c:when test="${fn:startsWith(employee.emp_dpt, department.dpt_sq)}">
												<option value="${employee.emp_sq}">${employee.emp_nm}</option>
											</c:when>
										</c:choose>
									</c:forEach>
								</optgroup>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-12 form-group">
						<br>
						<div class="text-center">
							<button id="btnInsert" type="button" class="btn btn-sm btn-primary col-sm-2">생성</button>
							<button id="btnCancel" type="button" class="btn btn-sm btn-secondary col-sm-2">취소</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</form>

<form id="cancelFrm" action="${cp}/workManage" method="get">
</form>

<script type="text/javascript">
	//select박스 선택 이벤트
	jQuery(document).ready(function() {
		jQuery(".standardSelect").chosen({
			disable_search_threshold : 10,
			no_results_text : "Oops, nothing found!",
			width : "100%"
		});
	});
	
	$(document).ready(function(){
		//체크박스 값 off로 세팅
		$("input[name=checkOpen]").val("off");
		
		//체크박스 변경 이벤트
		$("input[name=checkOpen]").on("change", function(){
			//체크박스 체크상태 확인
			var checked = $("input[name=checkOpen]").prop("checked");
			console.log("change공개여부" + checked);
			
			if(checked){
				$("input[name=checkOpen]").val("on");
				console.log("내용1" + $("input[name=checkOpen]").val());
			}else{
				$("input[name=checkOpen]").val("off");
				console.log("내용2" + $("input[name=checkOpen]").val());
			}
		});
		
		// 생성 버튼 이벤트
		$("#btnInsert").on("click", function(){
			console.log("프로젝트명" + $("input[name=inputProjectName]").val());
			console.log("설명" + $("input[name=inputExplain]").val());
			console.log("공개여부" + $("input[name=checkOpen]").val());
			
			//체크박스 값을 넘기기위한 hidden 태그값 세팅
			$("input[name=inputOpen]").val($("input[name=checkOpen]").val());
			
			//select값을 담기위한 배열생성
			var selectProMemArray = new Array();
			
			//$("#selectProMem option:selected").size(); - 선택한 옵션의 사이즈
			console.log($("select[name=selectProMem] option:selected").size());
			console.log($("select[name=selectProMem]").val()); //배열형태라서 $("#selectProMem").val()[0] 형식으로 접근가능 
			
			var selectProMemSize = $("select[name=selectProMem] option:selected").size();
			
			for(var i=0; i<selectProMemSize; i++){
				console.log($("select[name=selectProMem]").val()[i]);
				selectProMemArray.push($("select[name=selectProMem]").val()[i]);
			}
			console.log("배열 사이즈 : " + selectProMemArray.length);
			
			//select 여러값을 넘기기위한 hidden 태그값 세팅(배열형태로 넘어감)
			$("input[name=selectMember]").val(selectProMemArray);
			
			//구성원이 선택되지 않으면 생성불가
			if(selectProMemArray.length < 1){
				alert("구성원을 선택해주세요!");
				return;
			}
			
			//유효성체크
			if($("input[name=inputProjectName]").val().trim() == ""){
				alert("프로젝트명을 입력해주세요!");
				$("input[name=inputProjectName]").focus();
				return;
			}
			if($("input[name=inputExplain]").val().trim() == ""){
				alert("설명을 입력해주세요!");
				$("input[name=inputExplain]").focus();
				return;
			}
			
			//전송
			$("#frm").submit();
		});
		
		// 취소 버튼 이벤트
		$("#btnCancel").on("click", function(){
			$("#cancelFrm").submit();
		});
	});
</script>