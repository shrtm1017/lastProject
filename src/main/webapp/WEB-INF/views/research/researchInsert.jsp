<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<form id="frm" action="${cp }/researchInsert" method="post" class="form-horizontal" role="form">
			<br>
			<div class="card">
				<div class="card-header" style="background-color: #002266; color: white;">
					<strong class="card-title">설문지 등록</strong>
				</div>
				<br>
				<div class="card-body">
					<div id="divHead" class="col-lg-12">
						<div class="form-group">
							<div class="col col-sm-2" style="text-align: right;">
								<label for="res_title" class="control-label">제목</label>
							</div>
							<div class="col col-sm-8">
								<input type="text" class="form-control" id="res_nm" name="res_nm" placeholder="제목" />
							</div>
						</div>
						<br>
						<div class="form-group">
							<div class="col col-sm-2" style="text-align: right;">
								<label for="res_title" class="control-label">마감</label>
							</div>
							<div class="col col-sm-8">
								<div class="input-group date">
						            <input type="text" placeholder="마감일" class="form-control" id="res_end_dt" name="res_end_dt"/>
						            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						        </div>
							</div>
						</div>
						<br>
						<hr>
						<div class="form-group">
							<div class="col col-sm-10" style="text-align: right;">
								<button id="qAddBtn" type="button" class="btn btn-outline-secondary btn-sm col-sm-2">
									<i class="fa fa-pencil"></i>
									질문추가
								</button>
							</div>
						</div>
						<br>
					</div>
					<div id="divContent" class="col-lg-12">
						<div class="divQuestion1 form-group">
							<br><br>
							<div class="col col-sm-2" style="text-align: right;">
								<label id="lbQuestion1" for="res_title" class="control-label">질문</label>
							</div>
							<div class="col col-sm-6">
								<input id="question1" type="text" class="question form-control" placeholder="질문" />
							</div>
							<div class="col col-sm-4">
								<button data-question="1" type="button" class="oAddBtn btn btn-outline-secondary btn-sm">
									<i class="fa fa-lightbulb-o"></i>
									옵션추가
								</button>
								<button data-question="1" type="button" class="qRemoveBtn btn btn-outline-danger btn-sm">
									<i class="fa fa-eraser"></i>
									질문삭제
								</button>
							</div>
						</div>
						<div id="divOption1">
							<div id="1_option1" class="divQuestion1 form-group">
								<br>
								<div class="col col-sm-2" style="text-align: right;">
									<button data-question="1" data-option="1" type="button" class="oRemoveBtn btn btn-warning btn-sm">
										<i class="fa fa-trash-o"></i>
									</button>
								</div>
								<div class="col col-sm-8">
									<input type="text" id="option1_1" class="option option1 form-control" placeholder="옵션" />
								</div>
							</div>
							<div id="1_option2" class="divQuestion1 form-group">
								<br>
								<div class="col col-sm-2" style="text-align: right;">
									<button data-question="1" data-option="2" type="button" class="oRemoveBtn btn btn-warning btn-sm">
										<i class="fa fa-trash-o"></i>
									</button>
								</div>
								<div class="col col-sm-8">
									<input type="text" id="option2_1" class="option option1 form-control" placeholder="옵션" />
								</div>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col col-sm-12" style="text-align: center;">
							<br><br>
							<button id="insBtn" type="button" class="btn btn-secondary col-sm-1">등록</button>
							<button id="cancelBtn" type="button" class="btn btn-danger col-sm-1">취소</button>
						</div>
					</div>
					<div id="divHidden">
					</div>
				</div>
			</div>
		</form>
	</div>
</div>

<form id="cancelFrm" action="${cp }/researchList" method="get" class="form-horizontal" role="form">
</form>

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

		$("#insBtn").on("click", function() {
			// 빈 항목 체크
			if ($("#res_nm").val().trim() == "") {
				alert("제목을 입력해 주세요.");
				$("#res_nm").focus();
				$("#divHidden").html("");
				return;
			}
			if ($("#res_end_dt").val().trim() == "") {
				alert("마감일을 선택해 주세요.");
				$("#res_end_dt").focus();
				$("#divHidden").html("");
				return;
			}
			
			var qLen = $(".question").length;
			var oLen = $(".option").length;
			
			if (qLen < 1) {
				alert("질문을 추가해 주세요.");
				$("#divHidden").html("");
				return;
			}
			
			for (var i = 0; i < qLen; i++) {
				var idInfo = document.getElementsByClassName("question")[i].id;
				// 빈 항목 체크
				if ($("#" + idInfo).val().trim() == "") {
					alert("질문 내용을 입력해 주세요.");
					$("#" + idInfo).focus();
					$("#divHidden").html("");
					return;
				}
				
				var qNum = idInfo.split("question");
				var text = $("#" + idInfo).val();
				
				var html = "<input type='hidden' name='question' value='Q" + qNum[1] + "_" + text + "' />";
				$("#divHidden").append(html);
			}
			
			for (var i = 0; i < oLen; i++) {
				var idInfo = document.getElementsByClassName("option")[i].id;
				// 빈 항목 체크
				if ($("#" + idInfo).val().trim() == "") {
					alert("옵션 내용을 입력해 주세요.");
					$("#" + idInfo).focus();
					$("#divHidden").html("");
					return;
				}
				
				var qNum = idInfo.split("_");
				var text = $("#" + idInfo).val();
				
				var html = "<input type='hidden' name='option' value='Q" + qNum[1] + "_" + text + "' />";
				$("#divHidden").append(html);
			}
			
			$("#frm").submit();
		});
		
		$("#cancelBtn").on("click", function() {
			$("#cancelFrm").submit();
		});
		
		var qNum = 1;	// 질문 수
		var oNum = 2;	// 옵션 수
		$("#qAddBtn").on("click", function() {
			// 질문 번호			
			qNum++;
			
			option1 = oNum + 1;
			option2 = option1 + 1;
			oNum += 2;
			
			var text = "<div class='divQuestion" + qNum +" form-group'>";
			text += "		<br><br>";
			text += "		<div class='col col-sm-2' style='text-align: right;'>";
			text += "		<label id='lbQuestion" + qNum + "' for='res_title' class='control-label'>질문</label>";
			text += "	</div>";
			text += "	<div class='col col-sm-6'>";
			text += "		<input id='question" + qNum + "' type='text' class='question form-control' placeholder='질문' />";
			text += "	</div>";
			text += "	<div class='col col-sm-4'>";
			text += "		<button data-question='" + qNum + "' type='button' class='oAddBtn btn btn-outline-secondary btn-sm'>";
			text += "			<i class='fa fa-lightbulb-o'></i>";
			text += "			옵션추가";
			text += "		</button>";
			text += "		<button data-question='" + qNum + "' type='button' class='qRemoveBtn btn btn-outline-danger btn-sm'>";
			text += "			<i class='fa fa-eraser'></i>";
			text += "			질문삭제";
			text += "		</button>";
			text += "	</div>";
			text += "</div>";
			text += "<div id='divOption" + qNum + "'>"
			text += "	<div id='" + qNum + "_option" + option1 + "' class='divQuestion" + qNum +" form-group'>";
			text += "		<br>";
			text += "		<div class='col col-sm-2' style='text-align: right;'>";
			text += "			<button data-question='" + qNum + "' data-option='" + option1 + "' type='button' class='oRemoveBtn btn btn-warning btn-sm'>";
			text += "				<i class='fa fa-trash-o'></i>";
			text += "			</button>";
			text += "		</div>";
			text += "		<div class='col col-sm-8'>";
			text += "			<input type='text' id='option" + option1 + "_" + qNum + "' class='option option" + qNum + " form-control' placeholder='옵션' /></div>";
			text += "	</div>";
			text += "	<div id='" + qNum + "_option" + option2 + "' class='divQuestion" + qNum +" form-group'>";
			text += "		<br>";
			text += "		<div class='col col-sm-2' style='text-align: right;'>";
			text += "			<button data-question='" + qNum + "' data-option='" + option2 + "'' type='button' class='oRemoveBtn btn btn-warning btn-sm'>";
			text += "				<i class='fa fa-trash-o'></i>";
			text += "			</button>";
			text += "		</div>";
			text += "		<div class='col col-sm-8'>";
			text += "			<input type='text' id='option" + option2 + "_" + qNum + "' class='option option" + qNum + " form-control' placeholder='옵션' />";
			text += "		</div>";
			text += "	</div>";
			text += "</div>";
			
			$("#divContent").append(text);
        });
		
        $(document).on("click", ".qRemoveBtn", function() {
        	var qNum = $(this).data("question");
        	
        	$(".divQuestion" + qNum).remove();
        });
		
		$(document).on("click", ".oAddBtn", function() {
			var qNum = $(this).data("question");
			
			var optionCnt = $(".option" + qNum).length;
        	
        	if (optionCnt < 5) {
				oNum++;
				
				var text = "";
				text += "	<div id='" + qNum + "_option" + oNum + "' class='divQuestion" + qNum +" form-group'>";
				text += "		<br>";
				text += "		<div class='col col-sm-2' style='text-align: right;'>";
				text += "			<button data-question='" + qNum + "' data-option='" + oNum + "' type='button' class='oRemoveBtn btn btn-warning btn-sm'>";
				text += "				<i class='fa fa-trash-o'></i>";
				text += "			</button>";
				text += "		</div>";
				text += "		<div class='col col-sm-8'>";
				text += "			<input type='text' id='option" + oNum + "_" + qNum + "' class='option option" + qNum + " form-control' placeholder='옵션' />";
				text += "		</div>";
				text += "	</div>";
				
				$("#divOption" + qNum).append(text);
        	} else {
        		alert("옵션은 최대 5개까지 등록할 수 있습니다.")
        	}
        });
        
        $(document).on("click", ".oRemoveBtn", function() {
        	var qNum = $(this).data("question");
        	var oNum = $(this).data("option");
        	
        	var optionCnt = $(".option" + qNum).length;
        	
        	if (optionCnt > 2) {
				$("#" + qNum + "_option" + oNum).remove();
				$(this).remove();
        	} else {
        		alert("옵션은 2개 이상 존재해야 합니다.")
        	}
        });
	});
</script>