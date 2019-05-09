<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- table css 새로 작성 --%>
<style>
.table td, .myTable th{
	padding: 5px;
	padding-bottom : 10px;
	vertical-align: top;
}

.table th {
	text-align: center;
}
</style>

<div class="breadcrumbs">
	<div class="col-sm-4">
		<div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>받은 메일함</strong></h1>
			</div>
		</div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<form id="frm" action="${cp}/receiveMailboxDetail" method="get">
			<table class="table text-center">
				<thead>
					<tr>
						<th style="width: 30px;"><input type="checkbox" id="inputCheckAll"></th>
						<th style="width: 50px;"><button type="button" id="btnDel" class="btn btn-warning btn-sm"><i class="fa fa-trash-o"></i> 삭제</button></th>
						<th style="width: 50px;"></th>
						<th style="width: 50px;"></th>
						<th style="width: 50px;"></th>
						<th style="width: 600px;"></th>
						<th style="width: 100px;"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${emlReceiveList}" var="emlReceive">
						<c:if test="${emlReceive.eml_rec_del == 'N'}">
							<tr>
								<td><input type="checkbox" class="inputCheck" value="${emlReceive.eml_rec_sq}"></td>
								<%-- 받은메일 중요표시 구분하여 아이콘 출력 --%>
								<td class="tdStar" data-emlrecsq="${emlReceive.eml_rec_sq}">
									<c:if test="${emlReceive.eml_rec_ipt == 'N'}">
										<i class="fa fa-star-o"></i>
									</c:if>
									<c:if test="${emlReceive.eml_rec_ipt == 'Y'}">
										<i class="fa fa-star"></i>
									</c:if>
								</td>
								<%-- 받은메일 읽은표시 구분하여 아이콘 출력 --%>
								<td class="tdOpen" data-emlrecsq="${emlReceive.eml_rec_sq}">
									<c:if test="${emlReceive.eml_rec_chk == 'N'}">
										<i class="fa fa-envelope"></i>
									</c:if>
									<c:if test="${emlReceive.eml_rec_chk == 'Y'}">
										<i class="fa fa-envelope-o"></i>
									</c:if>
								</td>
								<%-- 휴지통 아이콘 처음상태는 display none으로 숨기기 --%>
								<td class="tdTrash" data-emlrecsq="${emlReceive.eml_rec_sq}"><i class="fa fa-trash-o" style="display: none;"></i></td>
								<%-- 읽지않았으면 진하게 표시하고, 읽었으면 연하게 표시 --%>
								<c:if test="${emlReceive.eml_rec_chk == 'N'}">
									<td style="text-align: left;">
										<strong>${sessionScope.employeeVo.emp_nm}</strong>
									</td>
									<td class="tdEmail" data-emlrecsq="${emlReceive.eml_rec_sq}" data-emlsendsq="${emlReceive.eml_send_sq}" style="text-align: left;">
										<strong>${emlReceive.eml_rec_nm}</strong>
									</td>
									<td style="text-align: left;">
										<strong><fmt:formatDate value="${emlReceive.eml_rec_dt}" pattern="yy.MM.dd HH:mm"/></strong>
									</td>
								</c:if>
								<c:if test="${emlReceive.eml_rec_chk == 'Y'}">
									<td style="text-align: left;">
										${sessionScope.employeeVo.emp_nm}
									</td>
									<td class="tdEmail" data-emlrecsq="${emlReceive.eml_rec_sq}" data-emlsendsq="${emlReceive.eml_send_sq}" style="text-align: left;">
										${emlReceive.eml_rec_nm}
									</td>
									<td style="text-align: left;">
										<fmt:formatDate value="${emlReceive.eml_rec_dt}" pattern="yy.MM.dd HH:mm"/>
									</td>
								</c:if>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
			
			<input type="hidden" name="eml_rec_sq">
			<input type="hidden" name="eml_send_sq">
		</form>
	</div>
</div>

<script>
	$(document).ready(function(){
		//전체체크박스 이벤트
		$("#inputCheckAll").on("click", function(){
			//이메일 체크박스의 상태도 전체체크박스의 상태와 동일하게 변경
			$(".inputCheck").prop("checked", $("#inputCheckAll").prop("checked"));
		});
	
		//체크박스 값 배열
		var checkArr = [];
		//삭제버튼 이벤트
		$("#btnDel").on("click", function(){
			$(".inputCheck").each(function(){
				var flag = $(this).is(":checked");
				var emp_rec_sq = $(this).val();
				console.log(flag);
				console.log(emp_rec_sq);
				
				//체크박스 체크되고 배열에 값이 존재하지 않을때만 배열에 넣기
				if(flag && !checkArr.includes(emp_rec_sq)){
					checkArr.unshift(emp_rec_sq);
				}
				
				//체크박스가 해제되고 배열에 값이 존재할때만 배열에서 제거
				if(!flag && checkArr.includes(emp_rec_sq)){
					checkArr.splice($.inArray(emp_rec_sq, checkArr), 1);
				}
			});
			
			//체크박스 배열
			console.log(checkArr);
			
			//체크박스 배열 사이즈가 0보다 클때만 삭제함
			if(checkArr.length > 0){
				$("input[name=eml_rec_sq]").val(checkArr);
				
				$("#frm").attr("action", "${cp}/receiveMailDelete");
				$("#frm").submit();
			}
		});
		
		//별 아이콘 클릭 이벤트
		$(".tdStar").on("click", function(){
			//받은메일 중요표시 변수
			var eml_rec_ipt = "";
			
			//해당 아이콘에 fa-star-o 클래스 정보가 존재하면 fa fa-star로 변경, 그렇지 않으면 fa fa-star-o로 변경
			if($(this).find(".fa-star-o").hasClass("fa fa-star-o")){
				$(this).find(".fa-star-o").attr("class", "fa fa-star");
				eml_rec_ipt = "Y"; //중요표시 체크되면 Y
			}else{
				$(this).find(".fa-star").attr("class", "fa fa-star-o");
				eml_rec_ipt = "N"; //중요표시 체크되지않으면 N
			}
			
			//받은메일 번호
			console.log($(this).data("emlrecsq").toString());
			var eml_rec_sq = $(this).data("emlrecsq").toString(); //data가 자체적으로 number형이라서 string으로 변경하여 넘겨줌
			
			//받은메일 중요표시와 받은메일번호를 넘겨서 update
			var param = {"eml_rec_ipt" : eml_rec_ipt, "eml_rec_sq" : eml_rec_sq};
			$.ajax({
				url : "${cp}/ajax/receiveMailUpdate",
				method : "post",
				async:false, //ajax는 비동기 방식으로 async의 값을 주지 않으면 기본값으로 true가 셋팅이되어 비동기방식으로 ajax가 수행
						     //여기서 async의 값을 false로 주면 동기방식으로 전역변수에 셋팅 할 수 있게 됨
				data : JSON.stringify(param), //JSON.stringify(데이터)는 해당 데이터를 문자열로 변환해줌
				dataType : "json",	//server로부터 받으려는 데이터타입
				contentType : "application/json; charset=utf-8", //client가 전송하는 데이터타입 
				success : function(data){
					console.log(data);
				}
			});
			
			//페이지 새로고침
			location.reload();
		});
		
		//읽은표시 아이콘 클릭 이벤트
		$(".tdOpen").on("click", function(){
			//받은메일 읽은표시 변수
			var eml_rec_chk = "";
			
			//해당 아이콘에 fa-envelope-o 클래스 정보가 존재하면 fa fa-envelope로 변경, 그렇지 않으면 fa fa-envelope-o로 변경
			if($(this).find(".fa-envelope-o").hasClass("fa fa-envelope-o")){
				$(this).find(".fa-envelope-o").attr("class", "fa fa-envelope");
				eml_rec_chk = "N"; //읽은표시 체크되지않으면 N
			}else{
				$(this).find(".fa-envelope").attr("class", "fa fa-envelope-o");
				eml_rec_chk = "Y"; //읽은표시 체크되면 Y
			}
			
			//받은메일 번호
			console.log($(this).data("emlrecsq").toString());
			var eml_rec_sq = $(this).data("emlrecsq").toString(); //data가 자체적으로 number형이라서 string으로 변경하여 넘겨줌
			
			//받은메일 읽은표시와 받은메일번호를 넘겨서 update
			var param = {"eml_rec_chk" : eml_rec_chk, "eml_rec_sq" : eml_rec_sq};
			$.ajax({
				url : "${cp}/ajax/receiveMailUpdate",
				method : "post",
				async:false, //ajax는 비동기 방식으로 async의 값을 주지 않으면 기본값으로 true가 셋팅이되어 비동기방식으로 ajax가 수행
						     //여기서 async의 값을 false로 주면 동기방식으로 전역변수에 셋팅 할 수 있게 됨
				data : JSON.stringify(param), //JSON.stringify(데이터)는 해당 데이터를 문자열로 변환해줌
				dataType : "json",	//server로부터 받으려는 데이터타입
				contentType : "application/json; charset=utf-8", //client가 전송하는 데이터타입 
				success : function(data){
					console.log(data);
				}
			});
			
			//페이지 새로고침
			window.location.reload();
		});
		
		//마우스오버 이벤트
		$(".tdTrash").mouseenter(function(){
			//진행바에 마우스가 올라가면 휴지통 아이콘 보이기
			$(this).find(".fa-trash-o").show();
			console.log("마우스 들어옴");
		});
		
		//마우스리브 이벤트
		$(".tdTrash").mouseleave(function(){
			//진행바에 마우스가 벗어나면 휴지통 아이콘 숨기기
			$(this).find(".fa-trash-o").hide();
			console.log("마우스 나감");
		});
		
		//쓰레기 아이콘 클릭 이벤트
		$(".tdTrash").on("click", function(){
			console.log("data-emlrecsq : " + $(this).data("emlrecsq"));
			var eml_rec_sq = $(this).data("emlrecsq");
			
			$("input[name=eml_rec_sq]").val(eml_rec_sq);
			$("#frm").attr("action", "${cp}/receiveMailDelete");
			$("#frm").submit();
		});
		
		//해당 이메일 클릭시 메일쓰기로 넘어감
		$(".tdEmail").on("click", function() {
			//data 속성에 저장된 값을 hidden 태그에 세팅하여 전송
			console.log("data-emlrecsq : " + $(this).data("emlrecsq"));
			var eml_rec_sq = $(this).data("emlrecsq");
			
			console.log("data-emlsendsq : " + $(this).data("emlsendsq"));
			var eml_send_sq = $(this).data("emlsendsq");

			$("input[name=eml_rec_sq]").val(eml_rec_sq);
			$("input[name=eml_send_sq]").val(eml_send_sq);
			
			$("#frm").submit();
		});
	});
</script>