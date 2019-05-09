<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="breadcrumbs">
	<div class="col-sm-4">
		<div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>업무 등록</strong></h1>
			</div>
		</div>
	</div>
</div>

<form id="frm" action="${cp}/workInsert" method="post" enctype="multipart/form-data">
	<div class="col-lg-12 col-sm-4"> <br>
		<div class="card">
			<div class="card-body">
				<div class="col col-md-12 form-group">
					<div class="col col-md-1 text-right">
						<label><strong>프로젝트</strong></label>
					</div>
	
					<div class="col col-md-5">
						<select name="selectProject" class="form-control">
							<option value="${sessionScope.projectVo.pro_sq}">${sessionScope.projectVo.pro_nm}</option>
						</select>
					</div>
					<div class="col col-md-1 text-right">
						<label><strong>유형</strong></label>
					</div>
	
					<div class="col col-md-5">
						<select name="selectType" class="form-control">
							<option value="새기능">새기능</option>
							<option value="결함">결함</option>
							<option value="지원">지원</option>
							<option value="공지">공지</option>
						</select>
					</div>
				</div>
				
				<div class="col col-md-12 form-group">
					<div class="col col-md-1 text-right">
						<label><strong>제목</strong></label>
					</div>
	
					<div class="col col-md-11">
						<input name="inputTitle" type="text" class="form-control"/>
					</div>
				</div>
				
				<div class="col col-md-12 form-group">
					<div class="col col-md-1 text-right">
						<label><strong>내용</strong></label>
					</div>
	
					<div class="col col-md-11">
						<textarea id="smarteditor" name="smarteditor" rows="10" cols="100" style="width:1150px; height:300px;"></textarea>
					</div>
				</div>
				
				<div class="col col-md-12 form-group">
					<div class="col col-md-1 text-right">
						<label><strong>상태</strong></label>
					</div>
	
					<div class="col col-md-5">
						<select name="selectState" class="form-control">
							<option value="신규">신규</option>
						</select>
					</div>
					
					<div class="col col-md-1 text-right">
						<label><strong>상위일감</strong></label>
					</div>
					
					<div class="col col-md-5 input-group">
						<button class="btn btn-secondary"><i class="fa fa-search"></i></button>
						<input name="inputWork" type="text" class="form-control" style="float: left"/>
					</div>
				</div>
				
				<div class="col col-md-12 form-group">
					<div class="col col-md-1 text-right">
						<label><strong>담당자</strong></label>
					</div>
					
					<div class="col col-md-5">
						<select name="selectCharger" class="form-control">
							<%-- ajax로 동적으로 담당자 옵션을 추가함 --%>
						</select>
					</div>
					
					<div class="col col-md-1 text-right">
						<label><strong>시작일</strong></label>
					</div>
					
					<div class="col col-md-5">
						<div class="input-group date">
							<input name="dateStart" type="text" class="form-control">
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>
				
				<div class="col col-md-12 form-group">
					<div class="col col-md-1 text-right">
						<label><strong>진척도</strong></label>
					</div>
					
					<div class="col col-md-5">
						<select name="selectProgress" class="form-control">
							<option value="0">0 %</option>
							<option value="10">10 %</option>
							<option value="20">20 %</option>
							<option value="30">30 %</option>
							<option value="40">40 %</option>
							<option value="50">50 %</option>
							<option value="60">60 %</option>
							<option value="70">70 %</option>
							<option value="80">80 %</option>
							<option value="90">90 %</option>
							<option value="100">100 %</option>
						</select>
					</div>
					
					<div class="col col-md-1 text-right">
						<label><strong>완료기한</strong></label>
					</div>
					
					<div class="col col-md-5">
						<div class="input-group date">
							<input name="dateEnd" type="text" class="form-control">
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>
				<div class="col col-md-12 form-group">
					<div class="col col-md-6"></div>
					
					<div class="col col-md-1 text-right">
						<label for="attachfile"><strong>첨부파일</strong></label>
					</div>
	
					<div class="col col-md-5">
						<%-- 파일명을 가지는 라벨을 동적으로 생성하기 위해 id값을 부여해서 jquery html 속성을 사용 --%>
						<div id="file" class="col col-md-4"></div>
						
						<%-- 파일속성은 value값 문자를 지정할 수 없기때문에 file속성이 들어간 태그의 id값과 일치하는 label을 만들어서 문자를 따로 지정해야함 --%>
						<label for="attach"><i class="fa fa-save"></i></label>
						<input id="attach" name="attach" type="file" max="5" multiple style="display: none;"/>
					</div>
				</div>
				
				<div class="col col-md-12 text-center">
					<button type="button" id="btnIns" class="btn btn-sm btn-primary col-sm-1">등록</button>
				</div>
			</div>
		</div>
	</div>
</form>

<!-- autocomplete에 관한 jquery-ui에 관한 라이브러리 -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
<script>
	//상위일감 업무 배열
	var workArr = new Array();
	
	//프로젝트 selectbox 선택시 상위일감도 바뀌어야함
	function ajaxProjectWorkSelect(){
		//프로젝트 selectbox 값
		var selectProject = $("select[name=selectProject]").val();
		
		var data = {"pro_sq" : selectProject};
		$.ajax({
			url : "${cp}/ajaxRequestBody/projectWorkSelect",
			method : "post",
			async:false, //ajax는 비동기 방식으로 async의 값을 주지 않으면 기본값으로 true가 셋팅이되어 비동기방식으로 ajax가 수행
					     //여기서 async의 값을 false로 주면 동기방식으로 전역변수에 셋팅 할 수 있게 됨
			data : JSON.stringify(data), //JSON.stringify(데이터)는 해당 데이터를 문자열로 변환해줌
			dataType : "json",	//server로부터 받으려는 데이터타입
			contentType : "application/json; charset=utf-8", //client가 전송하는 데이터타입 
			success : function(data){
				//상위일감을 받아서 전역배열에 넣기
				for(var i=0; i<data.length; i++){
					workArr.push(data[i].wk_sq + ":" + data[i].wk_nm);
				}
				
				console.log(workArr);
			}
		});
	}
	
	ajaxProjectWorkSelect();
	
	//autocomplete에 관한 jquery 사용시 기존의 jquery와 충돌되어 해결하는 방법
	//autocomplete에 관한 jquery 선언한후 밑에서 var 변수명 = $.noConflict(true)을 써주고 $대신 변수명을 사용해주면된다
	var newJquery = $.noConflict(true);
	
	//자동완성 기능으로 source에 오는 데이터는 배열형태
	newJquery(function() {
		newJquery("input[name=inputWork]").autocomplete({
			source: workArr
		});
	});
</script>

<script>
	var oEditors = []; // 개발되어 있는 소스에 맞추느라, 전역변수로 사용하였지만, 지역변수로 사용해도 전혀 무관 함.
	
	// 필수값 Check
	function validation(){
		var title = $("input[name=inputTitle]").val().trim();
		if(title == ""){
			alert("제목을 입력하세요.");
			$("input[name=inputTitle]").focus();
			return false;
		}
		
		//상위업무 유효성 체크
		var work = $("input[name=inputWork]").val().trim();
		console.log(">" + work + "<")
		var flag = false;
		for(var i=0; i<workArr.length; i++){
			if(work == workArr[i]){
				flag = true;
			}
		}
		if(work == ""){
			flag = true;
		}
		if(!flag){
			alert("유효하지 않은 상위업무입니다.");
			$("input[name=inputWork]").focus();
			return false;
		}
		
		var dateStart = $("input[name=dateStart]").val().trim();
		if(dateStart == ""){
			alert("시작일을 선택하세요.");
			$("input[name=dateStart]").focus();
			return false;
		}
		
		var dateEnd = $("input[name=dateEnd]").val().trim();
		if(dateEnd == ""){
			alert("완료기한을 선택하세요.");
			$("input[name=dateEnd]").focus();
			return false;
		}
		
		//날짜 유효체크(시작날짜가 종료예정날짜보다 늦을수없음)
		var date1 = new Date(dateStart);
		var date2 = new Date(dateEnd);
		if(date2 - date1 < 0){
			alert("완료기한이 시작일보다 이전일 수 없습니다.");
			$("input[name=dateStart]").focus();
			return false;
		}
		
		console.log(work);
		console.log(dateStart);
		console.log(dateEnd);
		
		return true;
	}
	
	//프로젝트 selectbox 선택시 구성원 selectbox값도 바뀌어야함(화면이 플리커링되지 않아야함)
	function ajaxProjectMemberSelect(){
		//프로젝트 selectbox 값
		var selectProject = $("select[name=selectProject]").val();
		
		var data = {pro_sq : selectProject};
		$.ajax({
			url : "${cp}/ajaxRequestBody/projectMemberSelect",
			method : "post",
			//data : "userId=brown&userNm=브라운",
			//data : $("#frm").serialize(),	//보낼 파라미터가 많을때 사용하는 유용한 방법
			data : JSON.stringify(data), //JSON.stringify(데이터)는 해당 데이터를 문자열로 변환해줌
			dataType : "json",	//server로부터 받으려는 데이터타입
			contentType : "application/json; charset=utf-8", //client가 전송하는 데이터타입 
			success : function(data){
				var arr = data;
										
				var html = "";
				for(var i=0; i<arr.length; i++){
					html += "<option value="+arr[i].emp_sq+">"+arr[i].emp_nm+"</option>";
				}
				
				$("select[name=selectCharger]").html(html);
			}
		});
	}
	
	$(document).ready(function() {
		// 스마트에디터부분
		// Editor Setting
		nhn.husky.EZCreator.createInIFrame({
			oAppRef : oEditors, // 전역변수 명과 동일해야 함.
			elPlaceHolder : "smarteditor", // 에디터가 그려질 textarea ID 값과 동일 해야 함.
			sSkinURI : "/SE2/SmartEditor2Skin.html", // Editor HTML
			fCreator : "createSEditor2", // SE2BasicCreator.js 메소드명이니 변경 금지 X
			htParams : {
				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
				bUseToolbar : true,
				// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
				bUseVerticalResizer : true,
				// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
				bUseModeChanger : true, 
			}
		});
		
		//프로젝트 selectbox 이벤트
		ajaxProjectMemberSelect(); //초기값 세팅
		$("select[name=selectProject]").on("change", function(){
			ajaxProjectMemberSelect();
		});
		
		//첨부파일 이벤트
		$("#attach").on("change", function(){
			var attach = document.getElementById("attach");
			var files = attach.files;
			var filenameLabel = "";
			
			//파일 개수제한
			if(files.length > 5){
				alert("파일은 최대 5개까지 첨부 가능합니다.");
				return;
			}
			
			for(var i=0; i<files.length; i++){
				console.log(files[i].name);
				filenameLabel += "<label class='badge badge-light'>"+files[i].name+"</label> <br>"; //동적으로 파일명을 가지고 라벨 생성
			}
			
			//파일명을 가지는 라벨을 동적으로 생성하기 위해 id값을 부여해서 jquery html 속성을 사용
			$("#file").html(filenameLabel);
		});
		
		//datePicker 이벤트(datePicker를 사용하려면 3가지 파일이 필요함)
		$(".input-group.date").datepicker({
            calendarWeeks: false,
            todayHighlight: true,
            autoclose: true,
            format: "yyyy-mm-dd",
            language: "kr"
        });
	
		//등록버튼 클릭이벤트
		$("#btnIns").on("click", function(){
			if(confirm("저장하시겠습니까?")) {
				//id가 smarteditor인 textarea에 에디터에서 대입
				oEditors.getById["smarteditor"].exec("UPDATE_CONTENTS_FIELD", []);
				
				//이부분에 에디터 validation 검증
				if(validation()) {
					$("#frm").submit();
				}
			}
		});
	});
</script>