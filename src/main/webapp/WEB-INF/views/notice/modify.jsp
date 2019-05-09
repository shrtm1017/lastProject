<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script src="${cp}/SE2/js/HuskyEZCreator.js"></script>
<style type="text/css">
.filebox input[type="file"] { /* 파일 필드 숨기기 */
	position: absolute;
	width: 1px;
	height: 1px;
	padding: 0;
	margin: -1px;
	/* 	overflow: hidden; */
	clip: rect(0, 0, 0, 0);
	border: 0;
}

.filebox label {
	display: inline-block;
	padding: .5em .75em;
	color: #ffffff;
	font-size: inherit;
	line-height: normal;
	vertical-align: middle;
	background-color: #5cb85c;
	border-color: #4cae4c;
	cursor: pointer;
	border: 1px solid #ebebeb;
	border-bottom-color: #e2e2e2;
	border-radius: .25em;
	border-color: #4cae4c;
}

.filebox .upload-name {
	display: inline-block;
	padding: .5em .75em; /* label의 패딩값과 일치 */
	font-size: inherit;
	font-family: inherit;
	line-height: normal;
	vertical-align: middle;
	background-color: #f5f5f5;
	border: 1px solid #ebebeb;
	border-bottom-color: #e2e2e2;
	border-radius: .25em;
	-webkit-appearance: none; /* 네이티브 외형 감추기 */
	-moz-appearance: none;
	appearance: none;
}
</style>

</head>
<body>
	<!-- 화면 출력 로직 작성 -->
	<div class="breadcrumbs">
		<div class="col-sm-4">
		    <div class="page-header float-left" id="page-title-style">
		        <div class="page-title">
		            <h1 style="padding-bottom: 0px;"><strong>게시글 수정</strong></h1>
		        </div>
		    </div>
		</div>
	</div>

	<form id="frm_modify" action="${cp}/modify" method="post"
		enctype="multipart/form-data">
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<input type="text" name="ntc_nm" id="ntc_nm" placeholder="제묙을 입력하세요"
				class="form-control" style="width: 1000px;"
				value="${noticeModify.ntc_nm }">
			<textarea name="ntc_con" id="smarteditor" rows="10" cols="100"
				style="width: 1000px; height: 500px;">${noticeModify.ntc_con }
				</textarea>

			<input type="hidden" name="ntc_sq" value="${noticeModify.ntc_sq}">
			<input type="hidden" name="ntc_fl_path" value="${noticeModify.ntc_fl_path }" >
			<div class="box1" style="float: left">
				<div id="file_add_div">
					<!-- 					<button type="button" id="file_add" style="width: 80px"  >파일첨부</button> -->
				</div>
				<div class="filebox">
					<input class="upload-name" value="파일선택" disabled="disabled">
					<label for="file_name">업로드</label> <input id="file_name"
						type="file" name="notice_file" style="float: left;" width="80px"
						class="upload-hidden" />
				</div>
				<div class='file_list_set' style='padding-top: 10px; clear: both;'>
					&nbsp;<label>${noticeModify.ntc_fl_nm}</label>
					<c:if test="${noticeModify.ntc_fl_nm !=null }">
						<button id="file_delete" class='remove_btn' type='button'
							style='float: left; background: #444; color: #fff; border: 1px solid #333;'>첨부파일
							삭제</button>
					</c:if>
				</div>
				<div class="sys-container sys1">
					<button type="button" id="modify_btn" class="btn btn-secondary">수정</button>
					<button type="button" id="cancle_btn" class="btn btn-secondary">취소</button>
				</div>
			</div>
		</div>
	</form>
<%-- <form id="file_delete_frm" action="${cp }/modify" method="post"> --%>
	
<!-- </form> -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

	<script>
		$("#cancle_btn").click(function() {
			history.go(-1);
		});
		
// 		$("#file_delete").click(function() {
// 			$("#file_delete_frm").submit();
// 		});

	</script>
	<script type="text/javascript">
		var max_file = 5;

		var oEditors = []; // 개발되어 있는 소스에 맞추느라, 전역변수로 사용하였지만, 지역변수로 사용해도 전혀 무관 함.

		$(document).ready(function() {
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

			$(".remove_btn")
					.on(
							"click",
							function() {
								var get_fileNo = $(this)
										.closest(
												".file_list_set");
								$(this).closest(
										".file_list_set")
										.remove();
								$("#	")
										.append(
												"<input type='hidden' name='removeFile' value='"+get_fileNo+"'>")
							});

			// 전송버튼 클릭이벤트
			$("#modify_btn")
					.click(
							function() {
								if (confirm("저장하시겠습니까?")) {
									// id가 smarteditor인 textarea에 에디터에서 대입
									oEditors.getById["smarteditor"]
											.exec(
													"UPDATE_CONTENTS_FIELD",
													[]);

									// 이부분에 에디터 validation 검증
									$("#frm_modify").submit();
								}
							});
			$("#file_add_div").on("click", function() {
				//사용중지
				file_add();
			});

			function file_add() {
				$("#file_container")
						.append(
								"<div class='file_list' style='padding-top:5px; clear:both;'><button class='btn_remove' type='button' style='float:left; background:#444; color:#fff; border:1px solid #333;'>-</button></div>");
				remove_init();
			}
			function remove_init() {
				$(".file_list input").off();
				$(".remove_btn").off();

				// 								// 파일첨부 추가하기 보여주기
				// 								$("#file_add_div").css("display", "none");
				// 								if ($(".btn_remove").length == 0) {
				// 									$("#file_add_div").css("display", "block");
				// 								}

				// 파일첨부 제거하기
				$(".remove_btn").on("click", function() {
					$(this).closest(".file_list").remove();
					remove_init();
				});

			}
			var fileTarget = $('.filebox .upload-hidden');
			fileTarget.on('change', function() { // 값이 변경되면
				if (window.FileReader) { // modern browser 
					var filename = $(this)[0].files[0].name;
				} else { // old IE var 
					filename = $(this).val().split('/').pop()
							.split('\\').pop(); // 파일명만 추출 } // 추출한 파일명 삽입
				}
				$(this).siblings('.upload-name').val(filename);
			});
		});

		// 필수값 Check
		function validation() {
			var contents = $.trim(oEditors[0].getContents());
			if (contents === '<p>&nbsp;</p>' || contents === '') { // 기본적으로 아무것도 입력하지 않아도 <p>&nbsp;</p> 값이 입력되어 있음. 
				alert("내용을 입력하세요.");
				oEditors.getById['smarteditor'].exec('FOCUS');
				return false;
			}

			return true;
		}
	</script>
</body>
</html>