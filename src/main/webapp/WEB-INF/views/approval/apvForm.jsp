<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>기안하기</strong></h1>
	        </div>
	    </div>
	</div>
</div>

<form id="frm" action="${cp }/apvInsert" method="post"
		class="form-horizontal" role="form" enctype="multipart/form-data">
	<div>
		<div class="col-lg-12 col-sm-4"> <br>
			<div class="form-group">
				<div class="col col-sm-2">
	            	<h3>${apvDivVo.div_apv_nm }</h3>
	           	</div>
	           	<div class="col col-sm-10 text-right">
					<button id="apvLineBtn" type="button" data-toggle="modal" data-target="#lineModal" class="btn btn-secondary col-sm-2">결재라인지정</button>
					<button id="reportBtn" type="button" class="btn btn-secondary col-sm-2">상신</button>
	           	</div>
	           	<br><br>
           	</div>
			<table class="table table-bordered" style="border-color: #002266;">
			    <tbody>
			        <tr>
			            <th scope="row" style="width: 200px;" class="text-center">문서번호</th>
			            <td style="width: 400px;"></td>
			            <th scope="row" style="width: 200px;" class="text-center">기안일</th>
			            <td id="tdDate" style="width: 400px;"></td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;" class="text-center">기안부서</th>
			            <td>${dptVo.dpt_nm }</td>
			            <th scope="row" style="width: 200px;" class="text-center">기안자</th>
			            <td>${empVo.emp_nm }</td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;" class="text-center">수신참조자</th>
			            <td id="tdApvRef">
			            	<c:forEach items="${refList }" var="ref">
								${ref.emp_nm }&nbsp;
							</c:forEach>
			            </td>
			            <th scope="row" style="width: 200px;" class="text-center">시행자</th>
			            <td>
			            	<select id="apv_executer" name="apv_executer" data-placeholder="시행자를 선택하세요." class="standardSelect" tabindex="1">
								<option value="0" selected></option>
								<c:forEach items="${empList }" var="emp">
								    <option value="${emp.emp_sq }">${emp.emp_nm }</option>
								</c:forEach>
							</select>
			            </td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;" class="text-center">제목</th>
			            <td colspan="3">
			            	<input type="text" id="apv_nm" name="apv_nm" placeholder="제목" class="form-control col-sm-11">
			            </td>
			        </tr>
			        <c:if test="${apvDivVo.div_apv_sq eq '1' }">
			        	<tr>
				            <th scope="row" style="width: 200px;" class="text-center">기간</th>
				            <td>
				            	<div class="form-group">
					            	<div class="col col-sm-6">
						            	<div class="input-group date">
								            <input type="text" class="form-control" id="apv_anu_str" name="apv_anu_str" placeholder="시작일" />
								            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								        </div>
						        	</div>
						        	<div class="col col-sm-6">
						            	<div class="input-group date">
								            <input type="text" class="form-control" id="apv_anu_end" name="apv_anu_end" placeholder="종료일" />
								            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								        </div>
						        	</div>
				            	</div>
				            </td>
				            <th scope="row" style="width: 200px;" class="text-center">구분</th>
				            <td>
			            		<select class="form-control col-sm-9" id="apv_anu_div" name="apv_anu_div">
				            		<option value="1">연차</option>
				            		<option value="2">반차</option>
				            		<option value="3">외출</option>
				            		<option value="4">조퇴</option>
				            		<option value="5">경조</option>
				            		<option value="6">교육</option>
				            		<option value="7">기타</option>
			            		</select>
			            	</td>
			        	</tr>
			        </c:if>
			        <tr id="trTime" style="display: none;">
			        	<th scope="row" style="width: 200px;" class="text-center">시간</th>
			        	<td colspan="3">
			        		<input type="text" id="apv_time" placeholder="시간" class="form-control col-sm-11">
			        	</td>
			        </tr>
			        <tr>
			            <th scope="row" style="width: 200px;" class="text-center">본문</th>
			        	<td colspan="3" class="text-center">
			        		<textarea id="apv_form" rows="10" cols="100" style="width:1000px; height:500px;">
			        			${apvDivVo.div_apv_form }
			        		</textarea>
			        	</td>
			        </tr>
			        <tr>
			        	<th scope="row" style="width: 200px;" class="text-center">첨부파일</th>
			        	<td colspan="3" style="width: 200px;">
						    <input type="file" name="apv_fl" />
						</td>
			        </tr>
			    </tbody>
			</table>
			
			<input type="hidden" id="apv_div_sq" name="apv_div_sq" value="${apvDivVo.div_apv_sq }" />	<!-- 결재구분 -->
			<input type="hidden" name="apv_emp_sq" value="${empVo.emp_sq }" />			<!-- 기안자 -->
			<input type="hidden" name="apv_depart" value="${dptVo.dpt_sq }" />			<!-- 기안부서 -->
			<input type="hidden" id="apv_con" name="apv_con" />							<!-- 본문 -->
			<input type="hidden" id="restAnu" value="${restAnu }" />					<!-- 연차 잔여 일수 -->
			<input type="hidden" id="apv_anu_time" name="apv_anu_time" />				<!-- 반차 시간 -->
		</div>
	</div>

	<div class="modal fade" id="lineModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="lineModalLabel">
						결재라인지정
					</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="col">
						<sup style="color: red;">*</sup>&nbsp;<strong>결재자</strong><br>
						<input type="hidden" name="selectSign"/>
						<select name="selectSignLine" data-placeholder="결재자" multiple="multiple" 
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
												<c:if test="${employee.emp_grade != '8' && employee.emp_flag == 'n'}">
													<option value="${employee.emp_sq}">
														${employee.emp_nm}
														<c:choose>
															<c:when test="${employee.emp_grade == '1' }">사장</c:when>
									                    	<c:when test="${employee.emp_grade == '2' }">전무</c:when>
									                    	<c:when test="${employee.emp_grade == '3' }">이사</c:when>
									                    	<c:when test="${employee.emp_grade == '4' }">부장</c:when>
									                    	<c:when test="${employee.emp_grade == '5' }">과장</c:when>
									                    	<c:when test="${employee.emp_grade == '6' }">팀장</c:when>
									                    	<c:when test="${employee.emp_grade == '7' }">대리</c:when>
								                    	</c:choose>
								                    	&nbsp;
													</option>
												</c:if>
											</c:when>
										</c:choose>
									</c:forEach>
								</optgroup>
							</c:forEach>
						</select>
					</div>
					<br>
					<div class="col">
						<strong>수신참조자</strong><br>
						<input type="hidden" name="selectRef"/>
						<select name="selectRefLine" data-placeholder="수신참조자" multiple="multiple" 
						class="standardSelect" tabindex="-1" style="display: none;">
							<c:forEach items="${departmentList}" var="department">
								<optgroup label="${department.dpt_nm}">
									<c:forEach items="${sessionScope.employeeList}" var="employee">
										<c:choose>
											<c:when test="${fn:startsWith(employee.emp_dpt, department.dpt_sq)}">
												<c:if test="${employee.emp_grade != '8' && employee.emp_flag == 'n'}">
													<option value="${employee.emp_sq}">
														${employee.emp_nm}
														<c:choose>
															<c:when test="${employee.emp_grade == '1' }">사장</c:when>
									                    	<c:when test="${employee.emp_grade == '2' }">전무</c:when>
									                    	<c:when test="${employee.emp_grade == '3' }">이사</c:when>
									                    	<c:when test="${employee.emp_grade == '4' }">부장</c:when>
									                    	<c:when test="${employee.emp_grade == '5' }">과장</c:when>
									                    	<c:when test="${employee.emp_grade == '6' }">팀장</c:when>
									                    	<c:when test="${employee.emp_grade == '7' }">대리</c:when>
								                    	</c:choose>
								                    	&nbsp;
													</option>
												</c:if>
											</c:when>
										</c:choose>
									</c:forEach>
								</optgroup>
							</c:forEach>
						</select>
					</div>
					<br>
				</div>
				<div class="modal-footer">
					<button type="button" id="btnLineSave" data-dismiss="modal" class="btn btn-primary btn-sm col-sm-2">저장</button>
					&nbsp;
					<button type="button" data-dismiss="modal" class="btn btn-secondary btn-sm col-sm-2">닫기</button>
				</div>
			</div>
		</div>
	</div>
	
	<input type="hidden" name="apvLineFlag" />
</form>

<script>
	var oEditors = [];
	$(document).ready(function() {
		<c:if test="${msg != null}">
			alert("${msg}");
		</c:if>

        var d = new Date();
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

	    if (month.length < 2) month = '0' + month;
	    if (day.length < 2) day = '0' + day;
	
	    var currentDate = [year, month, day].join('-');
        
        $("#tdDate").html(currentDate);
		
		$(".input-group.date").datepicker({
            calendarWeeks: false,
            todayHighlight: true,
            autoclose: true,
            format: "yyyy/mm/dd",
            language: "kr"
        });
        
		// Editor Setting
		nhn.husky.EZCreator.createInIFrame({
			oAppRef : oEditors,
			elPlaceHolder : "apv_form",
			sSkinURI : "/SE2/SmartEditor2Skin.html",
			fCreator : "createSEditor2",
			htParams : {
				bUseToolbar : true,
				bUseVerticalResizer : true,
				bUseModeChanger : true, 
			}
		});

		$("#apv_anu_div").on("change", function() {
			var apv_anu_div = $("#apv_anu_div option:selected").val();
			
			if (apv_anu_div == '2') {
				$("#trTime").show();
			} else {
				$("#trTime").hide();
			}
			
		});

		
		$("#reportBtn").on("click", function() {
			if(confirm("상신하시겠습니까?")) {
				// id가 smarteditor인 textarea에 에디터에서 대입
				oEditors.getById["apv_form"].exec("UPDATE_CONTENTS_FIELD", []);

				// 이부분에 에디터 validation 검증
				if(validation()) {
					var apv_con = $("#apv_form").val();
					$("#apv_con").val(apv_con);
					$("#frm").submit();
				}
			}
		});
		
		// 필수값 Check
		function validation(){
			if($("#apv_nm").val() == '') {
				alert("제목을 입력하세요.");
				$("#apv_nm").focus();
				return false;
			}
			
			if($("#apv_executer option:selected").val() == '0') {
				alert("시행자를 선택하세요.");
				return false;
			}
			
			// 근태신청서 : 기간일, 구분 체크
			var restAnu = $("#restAnu").val();
			var apv_div_sq = $("#apv_div_sq").val();
			if (apv_div_sq == '1') {
				if ($("#apv_anu_str").val().trim() == "") {
					alert("시작일을 선택해 주세요.");
					$("#apv_anu_str").focus();
					return false;
				}
				if ($("#apv_anu_end").val().trim() == "") {
					alert("종료일을 선택해 주세요.");
					$("#apv_anu_end").focus();
					return false;
				}
				
				var apv_anu_div = $("#apv_anu_div option:selected").val();
				// 연차
				if (apv_anu_div == '1') {
					// 연차 일수 체크
					var apv_anu_str = $("#apv_anu_str").val();
					var arr_anu_str = apv_anu_str.split("/");

					var apv_anu_end = $("#apv_anu_end").val();
					var arr_anu_end = apv_anu_end.split("/");
					
					var startDate = new Date(arr_anu_str[0], arr_anu_str[1] - 1, arr_anu_str[2]);
				    var endDate = new Date(arr_anu_end[0], arr_anu_end[1] - 1, arr_anu_end[2]);
				    
				    var count = 0;
			        var tempDate = startDate;
				    while(true) {  
				        if(tempDate.getTime() > endDate.getTime()) {
				        	console.log("count : " + count);
				            break;
				        } else {
				            var day = tempDate.getDay();
				            
				            if(day != 0 && day != 6) {
				            	count++;
				            } 
				            
				            tempDate.setDate(tempDate.getDate() + 1);
				        }
				    }
				    
					if (restAnu >= count) {
						console.log("연차 사용 가능");
					} else {
						alert("현재 사용 가능한 연차는 " + restAnu + "일입니다.");
						return;
					}
				} 
				// 반차
				else if(apv_anu_div == '2') {
					// 시간 미입력 체크
					if ($("#apv_time").val().trim() == "") {
						alert("시간을 입력해 주세요.");
						$("#apv_time").focus();
						return;
					}
					
					var apv_anu_str = $("#apv_anu_str").val();
					var apv_anu_end = $("#apv_anu_end").val();
					if (apv_anu_str != apv_anu_end) {
						alert("반차 사용 시 시작일과 종료일을 동일하게 선택해 주세요.");
						return;
					}
					
					// 연차 일수 체크
					if (restAnu >= 0.5) {
						$("#apv_anu_time").val($("#apv_time").val());
						console.log("반차 사용 가능");
					} else {
						alert("현재 사용 가능한 연차는 " + restAnu + "일입니다.");
						return;
					}
				}
			}
			
			var contents = $.trim(oEditors[0].getContents());
			if(contents == '<p>&nbsp;</p>' || contents == ''){ // 기본적으로 아무것도 입력하지 않아도 <p>&nbsp;</p> 값이 입력되어 있음. 
				alert("내용을 입력하세요.");
				oEditors.getById['apv_form'].exec('FOCUS');
				return false;
			}

			return true;
		}
		
		$("#btnLineSave").on("click", function() {
			// 결재자 select값을 담기 위한 배열 생성
			var selectSignArray = new Array();
			
			console.log($("select[name=selectSignLine] option:selected").size());
			console.log($("select[name=selectSignLine]").val());
			
			var selectSignSize = $("select[name=selectSignLine] option:selected").size();
			
			if (selectSignSize == 0) {
				alert("결재자를 선택해 주세요.");
				return;
			}
			
			for(var i=0; i<selectSignSize; i++){
				console.log($("select[name=selectSignLine]").val()[i]);
				selectSignArray.push($("select[name=selectSignLine]").val()[i]);
			}
			console.log("결재자 배열 사이즈 : " + selectSignArray.length);
			
			$("input[name=selectSign]").val(selectSignArray);
			
			// 수신참조자 select값을 담기 위한 배열 생성
			var selectRefArray = new Array();
			
			console.log($("select[name=selectRefLine] option:selected").size());
			console.log($("select[name=selectRefLine]").val());
			
			var selectRefSize = $("select[name=selectRefLine] option:selected").size();
			
			for(var i=0; i<selectRefSize; i++){
				console.log($("select[name=selectRefLine]").val()[i]);
				selectRefArray.push($("select[name=selectRefLine]").val()[i]);
			}
			console.log("수신참조자 배열 사이즈 : " + selectRefArray.length);
			
			$("input[name=selectRef]").val(selectRefArray);
			
			// 결재 문서 양식 - 수신참조자 업데이트
			var selectedTextRef = $("select[name=selectRefLine] option:selected").text();
			$("#tdApvRef").html(selectedTextRef);
			
			$("input[name=apvLineFlag]").val("t");
			alert("결재라인이 저장되었습니다.");
		});
		
		$(".standardSelect").chosen({
            disable_search_threshold: 10,
            no_results_text: "Oops, nothing found!",
            width: "100%"
        });
	});
</script>