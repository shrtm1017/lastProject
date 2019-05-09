<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>쪽지</strong></h1>
	        </div>
	    </div>
	</div>
</div>
<div>
	<div class="col-lg-12">
		<form id="frm" action="${cp }/messageInserts" method="post" class="form-horizontal" role="form">
			<br>
			<div class="card">
				<div class="card-header" style="background-color: #002266; color: white;">
					<strong class="card-title">쪽지 쓰기</strong>
				</div>
				<br>
				<div class="card-body">
					<div id="divHead" class="col-lg-12">
						<div class="form-group">
							<div class="col col-sm-2" style="text-align: right;">
								<label for="res_title" class="control-label">받는사람</label>
							</div>
							<div class="col col-sm-8">
								<input type="text" class="form-control" id="res_nm" name="res_nm" placeholder="" value="${recallins }" />
							</div>
						</div>
						<br>
						<hr>
						<div class="col col-sm-10" style="text-align: right;">
							<button id="myBtn" type="button" class="btn btn-warning btn-sm col-sm-1">
								<i class="fa fa-smile-o"></i>
								내게쓰기
							</button>
							<button id="addBtn" type="button" class="btn btn-warning btn-sm col-sm-1">
								<i class="fa fa-plus"></i>
								받는사람
							</button>
						</div>
						<div class="col col-sm-10" style="padding-top: 30px; padding-left: 230.16px;">
							<textarea name="contentarea" id="contentarea" rows="10" class="form-control"></textarea>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col col-sm-12" style="text-align: center;">
							<br><br>
							<button id="insBtn" type="button" class="btn btn-primary btn-sm col-sm-1">전송</button>
							<button id="cancelBtn" type="button" class="btn btn-secondary btn-sm col-sm-1">취소</button>
						</div>
					</div>
					<div id="divHidden">
					</div>
				</div>
			</div>
		</form>
	</div>
</div>

<form id="cancelFrm" action="${cp }/messageList">
</form>


<div id='wrap' class="col-lg-12">
	<div class="modal modal-center fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-center">
			<div class="modal-content" style="text-align: center;">
				<div class="modal-body">
					<form id="modalInfo" action="${cp }/insertSchedule">
						<table class="table">
							<thead>
								<tr>
									<th style="width: 50px; text-align: center;"></th>
									<th scope="col" style="width: 150px; text-align: center;">사원번호</th>
									<th scope="col" style="width: 150px; text-align: center;">사원명</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${emplist }" var="research">
									<tr>
										<td>
											<div class="checkbox">			
												<input type="checkbox" name="selectcheck" class="form-check-input" value="${research.emp_nm}(${research.emp_sq})" >
											</div>
										</td>	
										<td>
											${research.emp_sq }
										</td>
										<td>
											${research.emp_nm }
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<div class="form-group">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<input type="button" id="selectbtn" class="btn btn-primary btn-sm col-sm-3" value="선택">
					&nbsp;
					<input type="button" id="closebtn" class="btn btn-secondary btn-sm col-sm-3" value="종료">
				</div>
			</div>
		</div>
	</div>
</div>

<style>
.modal.modal-center {
	text-align: center;
}

@media screen and (min-width: 500px) {
	.modal.modal-center:before {
		display: inline-block;
		vertical-align: middle;
		content: " ";
		height: 100%;
	}
}

.modal-dialog.modal-center {
	display: inline-block;
	text-align: left;
	vertical-align: middle;
	padding-top: 300px;
}
</style>

<!-- Web socket CDN -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.js"></script>


<script>
	
	$(document).ready(function() {
		
		sockMe.onopen = onOpen;
		
		$("#addBtn").on("click",function(){
			$('#myModal').show();
		});
		
		$("#closebtn").on("click",function(){
			$('#myModal').hide();
		});
		
		$("#myBtn").on("click",function(){
			var memId=${memId};
			$("#res_nm").val(memId);
		});
		
		
		var selectR = '';
		 $(function(){
			  $('input[name="selectcheck"]').click(function(){
			   var output = '';
			   $('input[name="selectcheck"]:checked').each(function(index,item){
			    if(index!=0){
			     output += ', ';
			    }
			    output += $(this).val();
			   });
			   
			   selectR = output;
			  });
		 });
		 
		$("#selectbtn").on("click",function(){
			$("#res_nm").val(selectR);
			$('#myModal').hide();
		});
		
		
		$(".input-group.date").datepicker({
            calendarWeeks: false,
            todayHighlight: true,
            autoclose: true,
            format: "yyyy/mm/dd",
            language: "kr"
        });

		$("#insBtn").on("click", function() {
			// 빈 항목 체크
			if ($("#res_nm").val().trim() == "") {
				alert("받는이를 입력해 주세요.");
				$("#res_nm").focus();
				$("#divHidden").html("");
				return;
			}
			
			// 웹소켓 처리			
			sockMe.send(selectR);
// 			sockMe.onopen = onOpen;
			
			$("#frm").submit();
		});
		
		$("#cancelBtn").on("click", function() {
			$("#cancelFrm").submit();
		});
	});
</script>