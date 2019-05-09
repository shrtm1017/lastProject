<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- table css 새로 작성 --%>
<style>
.myTable {
	width: 100%;
	margin-bottom: 5px;
}

.myTable td, .myTable th{
	padding: 5px;
	vertical-align: top;
}

.myTable th {
	text-align: center;
}
</style>

<div class="breadcrumbs">
	<div class="col-sm-4">
		<div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>보낸 메일함</strong></h1>
			</div>
		</div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<form id="frm" action="${cp}/sendMailDelete" method="get">
			<div class="card">
				<div class="card-body card-block">
					<table class="myTable">
						<thead>
							<tr>
								<th style="width: 70px;"><button type="button" id="btnDel" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 삭제</button></th>
								<th style="width: 850px;"></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${emlSendList}" var="emlSend">
								<input type="hidden" name="eml_send_sq" value="${emlSend.eml_send_sq}">
								<tr>
									<th class="thStar">
										<c:if test="${emlSend.eml_send_ipt == 'N'}">
											<i class="fa fa-star-o"></i>
										</c:if>
										<c:if test="${emlSend.eml_send_ipt == 'Y'}">
											<i class="fa fa-star"></i>
										</c:if>
									</th>
									<td>
										<strong style="float: left; font-size: 20px;">${emlSend.eml_send_nm}</strong>
										<label style="color: #5D5D5D; float: right;"><strong><fmt:formatDate value="${emlSend.eml_send_dt}" pattern="yy.MM.dd HH:mm"/></strong></label><br>
										<br>
										받는 사람&nbsp;&nbsp;
										<%-- ,로 구분되어 한번에 넘어오는 값을 ,로 자르고 해당 배열의 길이만큼 반복하여 1개씩 주소출력 --%>
										<c:forEach begin="0" end="${fn:length(fn:split(emlSend.eml_send_rec, ','))-1}" varStatus="i">
											<strong>&lt;${fn:split(emlSend.eml_send_rec, ',')[i.index]}&gt;</strong>
										</c:forEach>
										<hr>
									</td>
								</tr>
								<tr>
									<td></td>
									<td>${emlSend.eml_send_con}</td>
								</tr>
							</c:forEach>
							
							<tr>
								<td style="text-align: right;">
									<button type="button" class="btn btn-outline-secondary btn-sm">
										<i class="fa fa-download"></i> 첨부파일
									</button>
								</td>
								<td>
									<c:forEach items="${emlFileList}" var="emlFile">
										<a href="${cp}/sendMailboxFile?eml_fl_sq=${emlFile.eml_fl_sq}" class="btn btn-link" style="padding: 0px;">${emlFile.eml_fl_nm}</a> <br>
									</c:forEach>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</form>
	</div>
</div>

<script>
	$(document).ready(function(){
		//삭제버튼 이벤트
		$("#btnDel").on("click", function(){
			$("#frm").submit();
		});
		
		//별 아이콘 클릭 이벤트
		$(".thStar").on("click", function(){
			//보낸메일 중요표시 변수
			var eml_send_ipt = "";
			
			//해당 아이콘에 fa-star-o 클래스 정보가 존재하면 fa fa-star로 변경, 그렇지 않으면 fa fa-star-o로 변경
			if($(this).find(".fa-star-o").hasClass("fa fa-star-o")){
				$(this).find(".fa-star-o").attr("class", "fa fa-star");
				eml_send_ipt = "Y"; //중요표시 체크되면 Y
			}else{
				$(this).find(".fa-star").attr("class", "fa fa-star-o");
				eml_send_ipt = "N"; //중요표시 체크되지않으면 N
			}
			
			//보낸메일 번호
			var eml_send_sq = $("input[name=eml_send_sq]").val();
			
			//보낸메일 중요표시와 보낸메일번호를 넘겨서 update
			var param = {"eml_send_ipt" : eml_send_ipt, "eml_send_sq" : eml_send_sq};
			$.ajax({
				url : "${cp}/ajax/sendMailUpdate",
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
		});
	});
</script>