<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="breadcrumbs">
	<div class="col-sm-4">
		<div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>메일 쓰기</strong></h1>
			</div>
		</div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4">
		<br>
		<form id="frm" action="${cp}/emailSend" method="post" class="form-horizontal" role="form" enctype="multipart/form-data">
			<div class="form-group">
				<div class="col col-sm-10">
					<button id="btnSend" type="button" class="btn btn-secondary col-sm-2">전송</button>
					<label id="labelTemp" style="margin-left: 10px; position: absolute;"></label>
				</div>
				<br><br>
			</div>

			<table class="table table-bordered" style="border-color: #002266;">
				<tbody>
					<tr>
						<th scope="row" style="width: 200px; vertical-align: middle;" class="text-center">받는 사람</th>
						<td colspan="3" style="width: 200px;">
							<button type="button" id="btnEmail" class="btn btn-info" style="float: right;">주소록</button>
							<div id="divFrom">
								<%-- overflow: hidden; 속성으로 스크롤 숨기기 --%>
								<textarea id="textareaFrom" rows="1" cols="1" style="resize: none; max-width: 1000px; max-height: 30px; overflow: hidden; width: 200px; margin-top: 5px;"></textarea>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row" style="width: 200px;" class="text-center">제목</th>
						<td colspan="3" style="width: 200px;">
							<input type="text" name="email_title" placeholder="제목" class="form-control">
						</td>
					</tr>
					<tr>
						<th scope="row" style="width: 200px; vertical-align: middle;" class="text-center">첨부파일</th>
						<td colspan="3" style="width: 200px;">
							<%-- 파일명을 가지는 라벨을 동적으로 생성하기 위해 id값을 부여해서 jquery html 속성을 사용 --%>
							<div id="divFile" class="col col-md-4"></div>
							
							<%-- 파일속성은 value값 문자를 지정할 수 없기때문에 file속성이 들어간 태그의 id값과 일치하는 label을 만들어서 문자를 따로 지정해야함 --%>
							<label for="attach"><i class="fa fa-save"></i></label> 
							<input id="attach" name="attach" type="file" multiple="multiple" style="display: none;" />
						</td>
					</tr>
					<tr>
						<td colspan="4" class="text-center">
							<textarea id="textareaEmail" rows="10" cols="100" style="width: 1310px; height: 300px;"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			
			<%-- 모달창 이벤트 --%>
			<%-- 모달창 처음상태는 display none으로 숨기기 --%>
			<div class="modal fade show" id="myModal" tabindex="-1" role="dialog" aria-labelledby="smallmodalLabel" style="display: none;">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">주소록</h5>
                            <button type="button" id="btnCancelX" class="close">
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        	<%-- 검색창 --%>
                        	<div class="input-group">
								<input id="inputSearch" type="text" placeholder="이름 또는 이메일 주소로 검색" style="width: 250px;" class="form-control" />
								
								<button type="button" id="btnSearch" class="btn btn-info"><i class="fa fa-search"></i></button>                        	
                        	</div>
                        	<hr>
                        	<div id="divEmail" style="padding-left: 10px;">
	                        	<%-- 주소록 출력 --%>
	                            <c:forEach items="${employeeList}" var="employee">
	                            	<c:if test="${employee.emp_flag != 'fire'}">
		                            	<div class="divEmails">
				                        	<input type="checkbox" name="inputCheck" value="${employee.emp_com_email}"/> &nbsp;
				                        	<label style="width: 150px;">"<strong>${employee.emp_nm}</strong>"</label>
				                        	<label>&lt;${employee.emp_com_email}&gt;</label> <br>
		                            	</div>
	                            	</c:if>
	                            </c:forEach>
                        	</div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" id="btnConfirm" class="btn btn-primary btn-sm col-sm-3">확인</button>
                            &nbsp;
                            <button type="button" id="btnCancel" class="btn btn-secondary btn-sm col-sm-3">취소</button>
                        </div>
                    </div>
                </div>
            </div>
            
			<input type="hidden" id="email_to" name="email_to"/> <%-- 받는사람 --%>
			<input type="hidden" id="email_content" name="email_content"/> <%-- 본문 --%>
		</form>
	</div>
</div>

<%-- 중간에 ""가 들어가는 문자열때문에 el전체를 ''로 감싸줘야함!!! --%>
<input type="hidden" id="eml_temp_nm" value='${emlTemp.eml_temp_nm}'/> <%-- 임시제목 --%>
<input type="hidden" id="eml_temp_con" value='${emlTemp.eml_temp_con}'/> <%-- 임시본문 --%>

<!-- autocomplete에 관한 jquery-ui에 관한 라이브러리 -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
<script>
	//이메일 주소 배열
	var emailArr = new Array();
	
	//textarea에서 email 입력시 자동완성 기능을 위해 데이터를 가져오기
	function ajaxEmailSelect(){
		//email 값
		var email_select = $("#textareaFrom").val();
		
		var data = {"email_select" : email_select};
		$.ajax({
			url : "${cp}/ajax/emailSelect",
			method : "post",
			async:false, //ajax는 비동기 방식으로 async의 값을 주지 않으면 기본값으로 true가 셋팅이되어 비동기방식으로 ajax가 수행
					     //여기서 async의 값을 false로 주면 동기방식으로 전역변수에 셋팅 할 수 있게 됨
			data : JSON.stringify(data), //JSON.stringify(데이터)는 해당 데이터를 문자열로 변환해줌
			dataType : "json",	//server로부터 받으려는 데이터타입
			contentType : "application/json; charset=utf-8", //client가 전송하는 데이터타입 
			success : function(data){
				//이메일주소를 받아서 전역배열에 넣기
				for(var i=0; i<data.length; i++){
					emailArr.push(data[i].emp_com_email);
				}
				
				console.log("ajax로 받아온 autocomplete에 사용되는 이메일배열: " + emailArr);
			}
		});
	}
	
	ajaxEmailSelect();
	
	//autocomplete에 관한 jquery 사용시 기존의 jquery와 충돌되어 해결하는 방법
	//autocomplete에 관한 jquery 선언한후 밑에서 var 변수명 = $.noConflict(true)을 써주고 $대신 변수명을 사용해주면된다
	var newJquery = $.noConflict(true);
	
	//자동완성 기능으로 source에 오는 데이터는 배열형태
	newJquery(function() {
		newJquery("#textareaFrom").autocomplete({
			source: emailArr
		});
	});
</script>

<script>
	var oEditors = []; // 개발되어 있는 소스에 맞추느라, 전역변수로 사용하였지만, 지역변수로 사용해도 전혀 무관 함.
	
	var toArr = []; //이메일 주소를 저장할 배열
	var toEmpArr = []; //해당 이메일의 사원번호를 저장할 배열
	
	//글자크기 구하기
	function charSize(content){
		 var regExp1 = /^[0-9]+$/; //숫자
		 var regExp2 = /^[a-z]+$/; //영문소문자
		 var regExp3 = /^[A-Z]+$/; //영문대문자
		 var regExp4 = /^[ㄱ-ㅎ|가-힣]+$/; //한글
		 
		 var size = 0;
		 for(var i=0; i<content.length; i++){
			 //글자를 각각 잘라서 숫자인지 영문소문자인지 영문대문자인지 한글인지 체크해서 글자크기 세팅
			 if(content.charAt(i).match(regExp1)){
				 size += 9;
			 }else if(content.charAt(i).match(regExp2)){
				 if(content.charAt(i).match(/^[f|i|j|l|r]+$/)){ //특정문자는 더작음
					 size += 5;
				 }else{
					 size += 9;
				 }
			 }else if(content.charAt(i).match(regExp3)){
				 if(content.charAt(i).match(/^[F|I|J|L|R]+$/)){ //특정문자는 더작음
					 size += 7;
				 }else{
					 size += 10;
				 }
			 }else if(content.charAt(i).match(regExp4)){
				 size += 16;
			 }else{
				 size += 15;
			 }
		 }
		 
		 console.log(content + "이메일에 해당하는 글자크기 사이즈: " + size);
		 return size+15; //x버튼 크기인 20을 마지막에 더해줌
	}
	
	//이메일 유효체크
	function emailCheck(content){
		//이메일 정규표현식
		var regEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		
		var flag = false;
		//유효한 이메일인지 정규표현식으로 검사
		if(content.match(regEmail)){
			flag = true;
		}else{
			flag = false;
		}
		
		return flag;
	}
	
	//임시저장 이벤트
    function emailTempSave(){
		//받는사람, 제목, 내용의 값을 가져옴
		$("input[name=email_to]").val(toArr); //이메일배열값을 받는사람value에 세팅
    	var email_to = $("input[name=email_to]").val();
    	var email_title = $("input[name=email_title]").val();
    	var email_content = $.trim(oEditors[0].getContents()); //변경된 textarea 값(스마트에디터 적용되어있어서 이렇게 값을 가져와야함)
		console.log(email_content);
    	
    	//ajax로 값을 넘겨서 수정하거나 등록하기
    	var param = {"email_to" : email_to, "email_title" : email_title, "email_content" : email_content};
    	$.ajax({
			url : "${cp}/ajax/emailTempUpdate",
			method : "post",
			data : JSON.stringify(param), //JSON.stringify(데이터)는 해당 데이터를 문자열로 변환해줌
			dataType : "json",	//server로부터 받으려는 데이터타입
			contentType : "application/json; charset=utf-8", //client가 전송하는 데이터타입 
			success : function(data){
				console.log(data);
			}
		});
    	
    	//날짜 세팅
    	var date = new Date();
        var year = date.getFullYear();
    	var month = '' + (date.getMonth() + 1);
    	var day = '' + date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        
        if (month.length < 2) month = "0" + month;
	    if (day.length < 2) day = "0" + day;
	    
	    var currentDate = [year, month, day].join("-");
	    var currentTime = [hour, minute, second].join(":");
	    
    	console.log("임시저장되었습니다." + currentDate + " " + currentTime);
    	$("#labelTemp").html("임시보관함에 저장함. <small>" + currentDate + " " + currentTime + "</small>");
    }
	
	//임시메일함에서 넘어온 기존의 임시메일정보
	function preEmlTempData(){
		//받는사람을 el로 받아오기
		var eml_temp_rec = "${emlTemp.eml_temp_rec}";
		console.log("받는사람 정보: "+ eml_temp_rec);
		
		//,로 구분되어 한번에 넘어오는 값을 ,로 자르고 배열에 넣음
		var recArr = eml_temp_rec.split(",");
		console.log(",로 구분된 받는사람 배열 사이즈: " + recArr.length);
		
		//받는이메일주소의 사이즈가 0보다 클때만
		if(recArr.length > 0){
			for(var i=0; i<recArr.length; i++){
				var html = "";
				//기존의 받아온 주소에 공백인지 체크
				if(recArr[i] != ""){
					//기존의 받아온 이메일이 유효한 이메일인지 체크해서 테두리색상 변경
					if(emailCheck(recArr[i])){
						//글자타입을 체크해서 width 설정
						html += "<div style='width: "+ charSize(recArr[i]) +"px; height: 30px; background-color: white; border: 1px solid green; margin-top: 5px;'>";
					}else{
						html += "<div style='width: "+ charSize(recArr[i]) +"px; height: 30px; background-color: white; border: 1px solid red; margin-top: 5px;'>";
					}
						html += 	"<span class='spanFrom'>";
	                	html += 		recArr[i];
	                	html += 	"</span>";
	                	html += 	"<a class='a-delete'>X</a>";
	                	html += "</div>";
	                
                	//배열에 이메일 주소값을 맨앞에 추가
                    toArr.unshift(recArr[i]);
                	
                  	//html을 div에 붙이기
                    $("#divFrom").prepend(html);
				}
			}
		}
		
		var filenameLabel = "";
		//임시파일 정보를 jstl을 이용하여 파일 라벨 동적으로 생성
		<c:forEach items="${emlTempFileList}" var="emlTempFile">
			filenameLabel += "<label class='badge badge-light'>"+"${emlTempFile.eml_temp_fl_nm}"+"</label>"; //동적으로 파일명을 가지고 라벨 생성
			filenameLabel += "<button type='button' class='btn btn-link' data-emltempflsq='"+"${emlTempFile.eml_temp_fl_sq}"+"'>X</button> <br>"; //해당 파일의 x버튼
		</c:forEach>
		
		//파일명을 가지는 라벨을 동적으로 생성하기 위해 id값을 부여해서 jquery html 속성을 사용
		$("#divFile").html(filenameLabel);
		
		//기존 제목과 내용 세팅
		//javascript 영역에서 el 사용시 중간에 ""들어가는 문자 처리를 못하기때문에 위에서 받은후 javascript 영역에서 처리
		var eml_temp_nm = $("#eml_temp_nm").val();
		var eml_temp_con = $("#eml_temp_con").val();
		
		$("input[name=email_title]").val(eml_temp_nm);
		$("#textareaEmail").val(eml_temp_con);
	}
	
	$(document).ready(function() {
		//임시메일함에서 넘어온 기존의 임시메일정보
		preEmlTempData();
		
		//키를 누를경우
		$("#textareaFrom").keyup(function(key){
			var content = $(this).val();
			
			console.log("키를 눌렀을때 textarea value값: " + content);
			
			//키를 눌렀는데 엔터키인 경우 문자열로 처리하지않고 공백으로처리
			if(key.keyCode == 13){
				$(this).val("");
			}
			
			//백스페이스키와 엔터키가 아닐때만 체크
			if(key.keyCode != 8 && key.keyCode != 13){
				//해당글자수를 초과하면 초기화
				if(content.length > 30){
					console.log("30글자가 넘었습니다.");
					$("#textareaFrom").attr("style", "resize: none; max-width: 1000px; max-heigth: 30px; width: 200px; overflow: hidden; margin-top: 5px;");
					$(this).val("");
					return false;
				}else{
					//공백이면 처음세팅 아니면 넓이는 글자타입별로 계산하여 세팅
					if(content == ""){
						$("#textareaFrom").attr("style", "resize: none; max-width: 1000px; max-heigth: 30px; width: 200px; overflow: hidden; margin-top: 5px;");
					}else{
						$("#textareaFrom").attr("style", "resize: none; max-width: 1000px; max-heigth: 30px; width: "+ charSize(content) + "px; overflow: hidden; margin-top: 5px;");
					}
				}
			}
		});
		
		//textareaFrom에서 엔터키를 누를 경우
        $("#textareaFrom").keydown(function(key) {
            //키의 코드가 13번일 경우 (13번은 엔터키)
            if(key.keyCode == 13) {
                console.log("엔터키를 눌렀습니다.");
                
                //내용 가져오기
                var content = $(this).val();
                
                //공백이 아닐경우에만 태그생성
                var html = "";
                if(content != ""){
                	//유효한 이메일인지 체크해서 테두리색상 변경
 					if(emailCheck(content)){
 						//글자타입을 체크해서 width 설정
 						html += "<div style='width: "+ charSize(content) +"px; height: 30px; background-color: white; border: 1px solid green; margin-top: 5px;'>";
 					}else{
 						html += "<div style='width: "+ charSize(content) +"px; height: 30px; background-color: white; border: 1px solid red; margin-top: 5px;'>";
 					}
 						html += 	"<span class='spanFrom'>";
	                    html += 		content;
	                    html += 	"</span>";
	                    html += 	"<a class='a-delete'><strong>x</strong></a>";
	                    html += "</div>";
                    
                    //배열에 이메일 주소값을 맨앞에 추가
	                toArr.unshift($("#textareaFrom").val());
	                
                    //html을 div에 붙이기
                    $("#divFrom").prepend(html);
                    //속성과 value 초기화
                    $("#textareaFrom").attr("style", "resize: none; max-width: 1000px; max-heigth: 30px; width: 200px; overflow: hidden; margin-top: 5px;");
                    $("#textareaFrom").val("");
                }
                
                console.log("이메일 주소들: " + toArr);
            }
        });
		
      	//textareaFrom에서 백스페이스를 누를 경우
        $("#textareaFrom").keydown(function(key) {
            //키의 코드가 8번일 경우 (8번은 백스페이스키)
            if(key.keyCode == 8) {
            	//내용 가져오기
            	var content = $(this).val();
            	
            	//공백인 경우에만 백스페이스 이벤트
            	if(content == ""){
	                console.log("백스페이스를 눌렀습니다.");
	                
	                //속성 초기화
	                $("#textareaFrom").attr("style", "resize: none; max-width: 1000px; max-heigth: 30px; width: 200px; overflow: hidden; margin-top: 5px;");
	                
	                //이메일 배열에서 지우려는 요소의 값을 제거
	                toArr.splice($.inArray($(this).prev().val(), toArr), 1);
	                console.log("이메일 주소들: " + toArr);
	                
	                //해당 textarea의 이전요소를 삭제
	                console.log("textarea 이전요소인 이메일 span 태그: " + $(this).prev());
	                $(this).prev().remove();
            	}else{
            		//넓이는 한글자크기만큼 줄어듬
                	$("#textareaFrom").attr("style", "resize: none; max-width: 1000px; max-heigth: 30px; width: "+ charSize(content) +"px; overflow: hidden; margin-top: 5px;");
            	}
            }
        });
      	
      	//이메일 x버튼 이벤트
      	$(document).on("click", ".a-delete", function(){
      		//span 태그는 val값이 아니라 text값
      		console.log($(this).prev().text());
      		//이메일 배열에서 지우려는 요소의 값을 제거
            toArr.splice($.inArray($(this).prev().text(), toArr), 1);
            console.log("이메일 주소들: " + toArr);
      		
      		$(this).parent().remove();
      	});
      	
        //10초마다 임시저장
        setInterval("emailTempSave()", 10000);
        
     	//필수값 Check
		function validation(){
     		//받는사람 유효성 체크
     		if(toArr.length < 1){
     			alert("받는 사람을 입력하세요.");
     			$("textareaFrom").focus();
     			return false;
     		}else{
     			for(var i=0; i<toArr.length; i++){
     				//유효한 이메일인지 체크
     				if(!emailCheck(toArr[i])){
     					alert("유효하지 않은 이메일이 속해있습니다.");
     					return false;
     				}
     			}
     		}
     		
//      	//제목 유효성 체크
// 			if($("#email_title").val() == '') {
// 				alert("제목을 입력하세요.");
// 				$("#email_title").focus();
// 				return false;
// 			}
			
// 			//내용 유효성 체크
// 			var contents = $.trim(oEditors[0].getContents());
// 			if(contents == '<p>&nbsp;</p>' || contents == ''){ //기본적으로 아무것도 입력하지 않아도 <p>&nbsp;</p> 값이 입력되어 있음. 
// 				alert("내용을 입력하세요.");
// 				oEditors.getById['apv_form'].exec('FOCUS');
// 				return false;
// 			}

			return true;
		}
		
     	//스마트에디터부분
		//Editor Setting
		nhn.husky.EZCreator.createInIFrame({
			oAppRef : oEditors, //전역변수 명과 동일해야 함.
			elPlaceHolder : "textareaEmail", //에디터가 그려질 textarea ID 값과 동일 해야 함.
			sSkinURI : "/SE2/SmartEditor2Skin.html", //Editor HTML
			fCreator : "createSEditor2", //SE2BasicCreator.js 메소드명이니 변경 금지 X
			htParams : {
				//툴바 사용 여부 (true:사용/false:사용하지 않음)
				bUseToolbar : true,
				//입력창 크기 조절바 사용 여부 (true:사용/false:사용하지 않음)
				bUseVerticalResizer : true,
				//모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/false:사용하지 않음)
				bUseModeChanger : true, 
			}
		});
     	
		//첨부파일 이벤트
		$("#attach").on("change", function(){
			//폼객체 얻어오기
			var createForm = $("#frm")[0];
			console.log(createForm);
			//폼객체 생성
			var formData = new FormData(createForm);
			
			//ajax로 form multipart/form-data 전송
			$.ajax({
				url : "${cp}/ajax/emailFileUpload",
				method : "post",
				data : formData, //폼 객체
				processData : false,
				contentType : false,
				dataType : "json",
				success : function(data){
					console.log(data);
					var arr = data;
					
					var filenameLabel = "";
					//파일 라벨 동적으로 생성
					for(var i=0; i<arr.length; i++){
						filenameLabel += "<label class='badge badge-light'>"+arr[i].eml_temp_fl_nm+"</label>"; //동적으로 파일명을 가지고 라벨 생성
						filenameLabel += "<button type='button' class='btn btn-link' data-emltempflsq='"+arr[i].eml_temp_fl_sq+"'>X</button> <br>"; //해당 파일의 x버튼
					}
					
					//파일명을 가지는 라벨을 동적으로 생성하기 위해 id값을 부여해서 jquery html 속성을 사용
					$("#divFile").html(filenameLabel);
				}
			});
		});
		
		//x버튼 이벤트
		//동적으로 생성된 버튼의 이벤트는 $(document).on("click", "해당태그의 id나 class 또는 name", function(){}); 형식으로 작성해야 동작함
		$(document).on("click", ".btn-link", function(){ //클래스명에서 btn btn-link는 btn-link로 적어야함
			console.log("x버튼의 이전요소: " + $(this).prev()); //해당 X버튼의 이전요소
			console.log("x버튼 index 순서" + $(".btn-link").index(this)); //모든 btn-link 요소중 내가 몇번째 index인지 찾을때
			
			//해당 파일번호
			var eml_temp_fl_sq = $(this).data("emltempflsq");
			console.log("x버튼의 해당하는 파일번호: " + eml_temp_fl_sq);
			
			//ajax로 파일을 삭제한후 리스트를 다시 받아오기
			var param = {"eml_temp_fl_sq" : eml_temp_fl_sq}; //넘겨줄 임시파일번호
			$.ajax({
				url : "${cp}/ajax/emailFileDelete",
				method : "post",
				data : JSON.stringify(param), //JSON.stringify(데이터)는 해당 데이터를 문자열로 변환해줌
				dataType : "json",	//server로부터 받으려는 데이터타입
				contentType : "application/json; charset=utf-8", //client가 전송하는 데이터타입 
				success : function(data){
					console.log(data);
					var arr = data;
					var filenameLabel = "";
					
					//파일 라벨 동적으로 생성
					for(var i=0; i<arr.length; i++){
						filenameLabel += "<label class='badge badge-light'>"+arr[i].eml_temp_fl_nm+"</label>"; //동적으로 파일명을 가지고 라벨 생성
						filenameLabel += "<button type='button' class='btn btn-link' data-emltempflsq='"+arr[i].eml_temp_fl_sq+"'>X</button> <br>"; //해당 파일의 x버튼
					}
					
					//파일명을 가지는 라벨을 동적으로 생성하기 위해 id값을 부여해서 jquery html 속성을 사용
					$("#divFile").html(filenameLabel);
				}
			});
		});
		
		//전송버튼 이벤트
		$("#btnSend").on("click", function() {
			if(confirm("전송하시겠습니까?")) {
				//id가 textareaEmail인 textarea에 에디터에서 대입
				oEditors.getById["textareaEmail"].exec("UPDATE_CONTENTS_FIELD", []);

				//이부분에 에디터 validation 검증
				if(validation()) {
					var email_content = $("#textareaEmail").val();
					$("#email_content").val(email_content);
					$("#email_to").val(toArr);
					
					console.log("받는사람 이메일 배열 사이즈: " + toArr.length);
					console.log($("#email_to").val());
					$("#frm").submit();
				}
			}
		});
		
		//주소록버튼 이벤트
		$("#btnEmail").on("click", function(){
			//체크박스 상태 초기화
			$(".divEmails").each(function(){
				$(this).children("input").prop("checked", false);
			});
			
			$("#myModal").show();
		});
		
		//주소록검색버튼 이벤트
		$("#btnSearch").on("click", function(){
			var search = $("#inputSearch").val();
			var param = {"email_search" : search};
			
			$.ajax({
				url : "${cp}/ajax/emailSearch",
				method : "post",
				data : JSON.stringify(param), //JSON.stringify(데이터)는 해당 데이터를 문자열로 변환해줌
				dataType : "json",	//server로부터 받으려는 데이터타입
				contentType : "application/json; charset=utf-8", //client가 전송하는 데이터타입 
				success : function(data){
					console.log(data);
					var arr = data;
					
					//검색결과 리스트로 다시 태그 생성
					var html = "";
					for(var i=0; i<arr.length; i++){
						html += "<div class='divEmails'>";
						html += 	"<input type='checkbox' name='inputCheck' value='"+ arr[i].emp_com_email +"'/> &nbsp;";
						html += 	"<label style='width: 150px;'><strong>"+ arr[i].emp_nm +"</strong></label>";
						html += 	"<label>&lt;"+ arr[i].emp_com_email +"&gt;</label> <br>";
						html += "</div>";
					}
					
					//생성한 태그를 붙이기
					$("#divEmail").html(html);
				}
			});
		});
		
		//체크박스와 이름, 이메일이 포함된 div를 클릭시 체크박스 이벤트 주기
		$(document).on("click", ".divEmails", function(){
			//체크박스의 checked 상태 확인
			var flag = $(this).children("input[name=inputCheck]").is(":checked"); //:checked가 핵심
			console.log(flag);

			//체크박스의 상태 반대로 변경
			$(this).children("input[name=inputCheck]").prop("checked", !flag);
		});
		
		
		//모달창 x버튼
		$("#btnCancelX").on("click", function(){
			$("#myModal").hide();
		});
		
		//모달창 취소버튼
		$("#btnCancel").on("click", function(){
			$("#myModal").hide();
		});
		
		//모달창 확인버튼
		$("#btnConfirm").on("click", function(){
			$("#myModal").hide();
			
			//체크박스 값
			var html = "";
			$(".divEmails").each(function(){
				//체크박스 상태를 확인하여 체크되어 있으면 value값을 얻어서 받는사람에 추가
				var flag = $(this).children("input[name=inputCheck]").is(":checked");
				if(flag){
					var content = $(this).children("input[name=inputCheck]").val();
					console.log("체크박스 값: " + content);
					
					//유효한 이메일인지 체크해서 테두리색상 변경
 					if(emailCheck(content)){
 						//글자타입을 체크해서 width 설정
 						html += "<div style='width: "+ charSize(content) +"px; height: 30px; background-color: white; border: 1px solid green;'>";
 					}else{
 						html += "<div style='width: "+ charSize(content) +"px; height: 30px; background-color: white; border: 1px solid red;'>";
 					}
 						html += 	"<span class='spanFrom'>";
	                    html += 		content;
	                    html += 	"</span>";
	                    html += 	"<a class='a-delete'>X</a>";
	                    html += "</div>";
		            
		          //배열에 이메일 주소값을 맨앞에 추가
	              toArr.unshift(content);
				}
			});
			
			$("#divFrom").prepend(html);
			
			console.log("이메일 주소들: " + toArr);
		});
		
	});
	
</script>