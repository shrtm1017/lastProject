<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>[일반] 증명서 신청</strong></h1>
	        </div>
	    </div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4"> <br>
	  	<form id="applyfrm" action="${cp}/certificateapply" method="post">
	  	<div class="col-sm-1"></div>
	  	<div class="col-sm-10">
			<table class="table table-bordered text-center">
			   	<tbody>
			   		<tr>
			        	<th scope="row" style="width: 200px;">신청일자</th>
			            <td>
			            	<div class="input-group date">
					            <input type="text" class="form-control" id="crt_dt" name="crt_dt"/>
					            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
					        </div>
			        	</td>
						<th scope="row" style="width: 200px;">증명서 구분</th>
			            <td>
			            	<select class="selCertificate form-control" id="crt_div_sq" name="crt_div_sq">
			            		<option value="1">재직증명서</option>
			            		<option value="2">경력증명서</option>
			            	</select>
			        	</td>		        	
			        </tr>
			        
			        <tr>
			            <th scope="row">부서</th>
			            <td>
			            	<select class="selDpt form-control" id="crt_emp_dpt" name="crt_emp_dpt">
								<option value="0">부서를 선택하세요.</option>
								<c:forEach items="${allnulllist}" var="allnulllist">
								<optgroup label="${allnulllist.dpt_nm}">
				            		<c:forEach items="${alllist}" var="alllist">
										<c:choose>
											<c:when test="${alllist.dpt_hr_sq == allnulllist.dpt_sq}">
												<option value="${alllist.dpt_sq}">${alllist.dpt_nm}</option>
											</c:when>
										</c:choose>
									</c:forEach>
								</optgroup>
								</c:forEach>
			            	</select>
			            </td>
			            
			            <th scope="row">직급</th>
			            <td>
							<c:choose>
		                    	<c:when test="${empVo.emp_grade == '1' }"><input type="text" class="form-control" id="crt_emp_grade" name="crt_emp_grade" value="사장" readonly/></c:when>
		                    	<c:when test="${empVo.emp_grade == '2' }"><input type="text" class="form-control" id="crt_emp_grade" name="crt_emp_grade" value="전무" readonly/></c:when>
		                    	<c:when test="${empVo.emp_grade == '3' }"><input type="text" class="form-control" id="crt_emp_grade" name="crt_emp_grade" value="이사" readonly/></c:when>
		                    	<c:when test="${empVo.emp_grade == '4' }"><input type="text" class="form-control" id="crt_emp_grade" name="crt_emp_grade" value="부장" readonly/></c:when>
		                    	<c:when test="${empVo.emp_grade == '5' }"><input type="text" class="form-control" id="crt_emp_grade" name="crt_emp_grade" value="과장" readonly/></c:when>
		                    	<c:when test="${empVo.emp_grade == '6' }"><input type="text" class="form-control" id="crt_emp_grade" name="crt_emp_grade" value="팀장" readonly/></c:when>
		                    	<c:when test="${empVo.emp_grade == '7' }"><input type="text" class="form-control" id="crt_emp_grade" name="crt_emp_grade" value="대리" readonly/></c:when>
		                    	<c:otherwise>사원</c:otherwise>
		                    </c:choose>
			        	</td>
			        </tr>
		        	
		        	<tr>
		        		<th scope="row">핸드폰 번호</th>
			            <td>
				            <input type="text" class="form-control" id="crt_emp_phone" name="crt_emp_phone" value="${empVo.emp_phone}"/>
			        	</td>
		        		<th scope="row">이름</th>
			            <td>
				            <input type="text" class="form-control" id="crt_emp_nm" name="crt_emp_nm" value="${empVo.emp_nm }" readonly/>
			        	</td>
		        	</tr>
		        	
		        	<tr>
		        		<th scope="row">재직기간 </th>
			            <td colspan="3">
			           	 	<div class="col col-sm-4">
				            	<div>
						            <input type="text" class="form-control" id="crt_dos_str" name="crt_dos_str" value='<fmt:formatDate value="${empVo.emp_ent }" pattern="yyyy/MM/dd"/>' readonly/>
						         </div>
						     </div>    
						         <div class="col col-sm-1">
						        	     ~
						        </div>  
			           	 	<div class="col col-sm-4">
				            	<div class="input-group date">
						            <input type="text" class="form-control" id="crt_dos_end" name="crt_dos_end"/>
						            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						        </div>
					        </div>
			        	</td>
		        	</tr>
		        	
		        	 <tr>
			            <th scope="row">주소</th>
			            <td colspan="3">
			            	<div class="col col-sm-5">
			            		<input type="text" id="addr1" name="crt_emp_addr1" placeholder="주소" class="form-control" readonly>
			            	</div>
			            	<div class="col col-sm-1">
				            	<button id="zipcodeBtn" type="button" class="btn btn-outline-secondary">검색</button>
				            </div>		
			            </td>
			        </tr>
			        <tr>
			        	<th scope="row">상세주소</th>
			            <td colspan="3">
		            		<input type="text" id="addr2" name="crt_emp_addr2" placeholder="상세 주소" class="form-control">
			            </td>
			        </tr>
		        	<tr>
			            <th scope="row">용도</th>
			            <td colspan="3">
		            		<select class="selCertificate form-control" id="crt_purpose" name="crt_purpose">
			            		<option value="1">일반제출용</option>
			            		<option value="2">회사제출용</option>
			            		<option value="3">공공기관용</option>
			            		<option value="4">금융기관용</option>
			            	</select>
			            </td>
			        </tr>
		        	
		        	<tr>
		        		<th scope="row">제출처</th>
			            <td>
			            	<div>
					            <input type="text" class="form-control" id="crt_submission" name="crt_submission"/>
					        </div>
			        	</td>
		        		<th scope="row">제출예정일</th>
			            <td>
			            	<div class="input-group date">
					            <input type="text" class="form-control" id="crt_subdt" name="crt_subdt"/>
					            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
					        </div>
			        	</td>
		        	</tr>
			   	</tbody>
			</table>
		</div>
		<div class="col-sm-12 text-center" style="margin-top: 15px;">
		   	<button type="button" id="applyBtn" class="btn btn-primary btn-sm col-sm-1">신청</button>
			<button type="button" id="cancelBtn" class="btn btn-secondary btn-sm col-sm-1">취소</button>
		</div>
	  </form>
	</div>
</div>	
<form id="canclefrm" action="${cp}/ctfList"></form>

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
		
		$(".selDpt").on("change", function() {
		
			console.log($("select[name=crt_emp_dpt]").val());
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
		
		// 신청버튼 클릭했을시 이벤트
		$("#applyBtn").on("click", function() {
			
			if($("#crt_emp_grade").val() == "사장"){
				$("#crt_emp_grade").val("1");	
			}
			if($("#crt_emp_grade").val() == "전무"){
				$("#crt_emp_grade").val("2");	
			}
			if($("#crt_emp_grade").val() == "이사"){
				$("#crt_emp_grade").val("3");	
			}
			if($("#crt_emp_grade").val() == "부장"){
				$("#crt_emp_grade").val("4");	
			}
			if($("#crt_emp_grade").val() == "과장"){
				$("#crt_emp_grade").val("5");	
			}
			if($("#crt_emp_grade").val() == "팀장"){
				$("#crt_emp_grade").val("6");	
			}
			if($("#crt_emp_grade").val() == "대리"){
				$("#crt_emp_grade").val("7");	
			}
			if($("#crt_emp_grade").val() == "사원"){
				$("#crt_emp_grade").val("8");	
			}
			
			console.log($("#crt_emp_grade").val());
			$("#applyfrm").submit();
		});
		
		$("#cancelBtn").on("click", function() {
			$("#canclefrm").submit();
		});
		
		
	});	
</script>		
	