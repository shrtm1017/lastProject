<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="breadcrumbs">
	<div class="col-sm-4">
		<div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>업무</strong></h1>
			</div>
		</div>
	</div>
</div>

<form id="frm" action="${cp}/workUpdate" method="post" enctype="multipart/form-data">
	<div class="col-lg-12 col-sm-4"> <br>
		<div class="card">
			<div class="card-body">
				<div class="col col-md-12 form-group">
					<div class="col col-md-1 text-right">
						<label><strong>프로젝트</strong></label>
					</div>
	
					<div class="col col-md-5">
						<select name="selectProject" class="form-control">
							<c:forEach items="${projectList}" var="project">
								<c:choose>
									<c:when test="${project.pro_sq == workMap.PRO_SQ}">
										<option selected="selected" value="${project.pro_sq}">${project.pro_nm}</option>
									</c:when>
									<c:otherwise>
										<option value="${project.pro_sq}">${project.pro_nm}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>

					<div class="col col-md-1 text-right">
						<label><strong>유형</strong></label>
					</div>
	
					<div class="col col-md-5">
						<select name="selectType" class="form-control">
							<option <c:if test="${workMap.WK_TYPE == '새기능'}">selected="selected"</c:if> value="새기능">새기능</option>
							<option <c:if test="${workMap.WK_TYPE == '결함'}">selected="selected"</c:if> value="결함">결함</option>
							<option <c:if test="${workMap.WK_TYPE == '지원'}">selected="selected"</c:if> value="지원">지원</option>
							<option <c:if test="${workMap.WK_TYPE == '공지'}">selected="selected"</c:if> value="공지">공지</option>
						</select>
					</div>
				</div>
	
				<div class="col col-md-12 form-group">
					<div class="col col-md-1 text-right">
						<label><strong>제목</strong></label>
					</div>
	
					<div class="col col-md-11">
						<input name="inputTitle" type="text" class="form-control" value="${workMap.WK_NM}"/>
					</div>
				</div>
				
				<div class="col col-md-12 form-group">
					<div class="col col-md-1 text-right">
						<label><strong>내용</strong></label>
					</div>
	
					<div class="col col-md-11">
						<textarea id="smarteditor" name="smarteditor" rows="10" cols="100" style="width:1150px; height:300px;">${workMap.WK_CON}</textarea>
					</div>
				</div>
				
				<div class="col col-md-12 form-group">
					<div class="col col-md-1 text-right">
						<label><strong>상태</strong></label>
					</div>
	
					<div class="col col-md-5">
						<select name="selectState" class="form-control">
							<option <c:if test="${workMap.WK_STATE == '신규'}">selected="selected"</c:if> value="신규">신규</option>
							<option <c:if test="${workMap.WK_STATE == '진행'}">selected="selected"</c:if> value="진행">진행</option>
							<option <c:if test="${workMap.WK_STATE == '완료'}">selected="selected"</c:if> value="완료">완료</option>
						</select>
					</div>
					
					<div class="col col-md-1 text-right">
						<label><strong>상위일감</strong></label>
					</div>
					
					<div class="col col-md-5 input-group">
						<button type="button" class="btn btn-secondary"><i class="fa fa-search"></i></button>
						<input name="inputWork" type="text" class="form-control" style="float: left" value=""/>
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
					
					<div class="col col-md-5 text-right">
						<div class="input-group date">
							<input name="dateStart" type="text" class="form-control" value="<fmt:formatDate value="${workMap.WK_STR_DT}" pattern="yyyy-MM-dd"/>">
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
							<option <c:if test="${workMap.WK_PROGRESS == 0}">selected="selected"</c:if> value="0">0 %</option>
							<option <c:if test="${workMap.WK_PROGRESS == 10}">selected="selected"</c:if> value="10">10 %</option>
							<option <c:if test="${workMap.WK_PROGRESS == 20}">selected="selected"</c:if> value="20">20 %</option>
							<option <c:if test="${workMap.WK_PROGRESS == 30}">selected="selected"</c:if> value="30">30 %</option>
							<option <c:if test="${workMap.WK_PROGRESS == 40}">selected="selected"</c:if> value="40">40 %</option>
							<option <c:if test="${workMap.WK_PROGRESS == 50}">selected="selected"</c:if> value="50">50 %</option>
							<option <c:if test="${workMap.WK_PROGRESS == 60}">selected="selected"</c:if> value="60">60 %</option>
							<option <c:if test="${workMap.WK_PROGRESS == 70}">selected="selected"</c:if> value="70">70 %</option>
							<option <c:if test="${workMap.WK_PROGRESS == 80}">selected="selected"</c:if> value="80">80 %</option>
							<option <c:if test="${workMap.WK_PROGRESS == 90}">selected="selected"</c:if> value="90">90 %</option>
							<option <c:if test="${workMap.WK_PROGRESS == 100}">selected="selected"</c:if> value="100">100 %</option>
						</select>
					</div>
					
					<div class="col col-md-1 text-right">
						<label><strong>완료기한</strong></label>
					</div>
					
					<div class="col col-md-5">
						<div class="input-group date">
							<input name="dateEnd" type="text" class="form-control" value="<fmt:formatDate value="${workMap.WK_END_DT}" pattern="yyyy-MM-dd"/>"/>
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
						<%-- 기존의 첨부파일 --%>
						<c:forEach items="${workFileList}" var="workFile">
							<a href="${cp}/workFile?wk_fl_sq=${workFile.wk_fl_sq}" class="btn btn-link" style="padding: 0px;">
								${workFile.wk_fl_nm}
							</a>
							&nbsp;
							<a href="${cp}/workFileDelete?wk_sq=${workMap.WK_SQ}&wk_fl_sq=${workFile.wk_fl_sq}">x</a>
							<br>
						</c:forEach>
						<div id="file" class="col col-md-4"></div>
						
						<%-- 파일속성은 value값 문자를 지정할 수 없기때문에 file속성이 들어간 태그의 id값과 일치하는 label을 만들어서 문자를 따로 지정해야함 --%>
						<label for="attach"><i class="fa fa-save"></i></label>
						<input id="attach" name="attach" type="file" max="5" multiple style="display: none;"/>
					</div>
				</div>
				
				<div class="col col-md-12 form-group">
					<c:forEach items="${historyList}" var="history" varStatus="status">
	                   	<%-- date 포맷팅하여 보여주기(작업내역 관련) --%>
	                   	<%-- 인덱스가 첫번째인경우 년월일 출력하고 첫번째값이 아니면 이전인덱스 년월일과 현재인덱스 년월일을 비교해서 같지않을때만 년월일 출력 --%>
	                   	<c:choose>
							<c:when test="${status.index == 0}">
								<h3><fmt:formatDate value="${history.HIS_DT}" pattern="yyyy-MM-dd" /></h3>
							</c:when>
							<c:otherwise>
								<c:if
									test="${fn:substring(historyList[status.index-1].HIS_DT, 0, 10) != fn:substring(historyList[status.index].HIS_DT, 0, 10)}">
									<h3><fmt:formatDate value="${history.HIS_DT}" pattern="yyyy-MM-dd" /></h3>
								</c:if>
							</c:otherwise>
						</c:choose>
						
						<strong style="color: #008299;"><fmt:formatDate value="${history.HIS_DT}" pattern="HH:mm" /></strong>
						<strong>${history.EMP_NM}</strong>
	                    <label style="color: #5D5D5D;">${history.HIS_CON}</label>
	                    <hr style="margin-top: 0px;">
					</c:forEach>
				</div>
				
				<%-- 업무정보 --%>
				<input name="wk_sq" type="hidden" value="${workMap.WK_SQ}"/>
				
				<div class="col col-md-12 text-center">
					<button type="button" id="btnUpd" class="btn btn-sm btn-primary col-sm-1">수정</button>
					<button type="button" id="btnDel" class="btn btn-sm btn-danger col-sm-1">삭제</button>
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
	var workArr = [];
	
	//프로젝트 selectbox 선택시 상위일감도 바뀌어야함
	function ajaxProjectWorkSelect(){
		//프로젝트 selectbox 값(val()와 val(데이터)는 다르다!!!)
		var selectProject = $("select[name=selectProject]").val();
		
		//업무번호
		var wksq = "${workMap.WK_SQ}";
		
		var data = {"pro_sq" : selectProject, "wk_sq" : wksq};
		console.log(data);
		$.ajax({
			url : "${cp}/ajaxRequestBody/projectWorkSelect",
			method : "post",
			async:false, //ajax는 비동기 방식으로 async의 값을 주지 않으면 기본값으로 true가 셋팅이되어 비동기방식으로 ajax가 수행
					     //여기서 async의 값을 false로 주면 동기방식으로 전역변수에 셋팅 할 수 있게 됨
			data : JSON.stringify(data), //JSON.stringify(데이터)는 해당 데이터를 문자열로 변환해줌
			dataType : "json",	//server로부터 받으려는 데이터타입
			contentType : "application/json; charset=utf-8", //client가 전송하는 데이터타입 
			success : function(data){
				//배열 초기화
				workArr = [];
				
				//상위일감을 받아서 전역배열에 넣기
				for(var i=0; i<data.length; i++){
					//현재일감의 상위일감과 조회한 일감을 비교하여 존재하면 상위일감 정보를 표시
					if("${workMap.WK_HR_SQ}" == data[i].wk_sq){
						$("input[name=inputWork]").val(data[i].wk_sq + ":" + data[i].wk_nm);
					}
					
					//조회한 일감에서 자신이 아닐때만 배열에 추가(자기자신은 상위일감이 될수없음)
					if("${workMap.WK_SQ}" != data[i].wk_sq){
						workArr.push(data[i].wk_sq + ":" + data[i].wk_nm);
					}
				}
				
				console.log(workArr);
			}
		});
	}
	
	//기본값 세팅
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
	//프로젝트 selectbox 이벤트
	$("select[name=selectProject]").on("change", function(){
		//selectbox 변경시 상위업무 input박스 초기화
		$("input[name=inputWork]").val("");
		
		//배열을 ajax로 새로 받아와 세팅한후 자동완성 기능 실행
		ajaxProjectWorkSelect();
		
		//자동완성 기능으로 source에 오는 데이터는 배열형태
		newJquery(function() {
			newJquery("input[name=inputWork]").autocomplete({
				source: workArr
			});
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
		
		var data = {"pro_sq" : selectProject};
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
					if(arr[i].emp_sq == "${workMap.EMP_SQ}"){
						html += "<option selected='selected' value="+arr[i].emp_sq+">"+arr[i].emp_nm+"</option>";
					}else{
						html += "<option value="+arr[i].emp_sq+">"+arr[i].emp_nm+"</option>";
					}
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
	
		//수정버튼 클릭이벤트
		$("#btnUpd").on("click", function(){
			if(confirm("저장하시겠습니까?")) {
				//id가 smarteditor인 textarea에 에디터에서 대입
				oEditors.getById["smarteditor"].exec("UPDATE_CONTENTS_FIELD", []);
				
				//이부분에 에디터 validation 검증
				if(validation()) {
					$("#frm").submit();
				}
			}
		});
		
		//삭제버튼 클릭이벤트
		$("#btnDel").on("click", function(){
			$("#frm").attr("action", "${cp}/workDelete");
			$("#frm").submit();
		});
	});
</script>