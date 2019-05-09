<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" uri="http://tiles.apache.org/tags-tiles"%>
<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>명함 관리</strong></h1>
	        </div>
	    </div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<div class="form-group">
			<div class="col col-md-2">
				<div class="input-group">
					<form id="deletefrm" action="${cp}/cardDelete" method="post">
						<button type="submit" class="btn btn-secondary" id="del_btn">
							<i class="fa fa-trash-o"></i> 삭제
						</button>
						<input type="hidden" id="del_cal_sq" name="del_cal_sq" value="0" />
					</form>
					&nbsp;
					<form action="${cp}/cardForm">
						<button type="submit" class="btn btn-secondary">명함등록</button>
					</form>
				</div>
			</div>
			<div class="col col-md-8">
				<div class="input-group">
					<select name="selector" id="selectbox" class="form-control col-sm-2">
						<option value="1">이름</option>
						<option value="2">회사</option>
						<option value="3">직급</option>
					</select>
					<input type="text" id="inputText" class="form-control" style="margin-left: 10px;">
					<button id="btnSearch" class="btn btn-info" style="margin-left: 10px;">
						<i class="fa fa-search"></i>
					</button>
				</div>
			</div>
		</div>
		<br><br>
		<form id="frm" action="${cp}/cardModify" method="get">
			<input type="hidden" id="cal_sq" name="cal_sq" />
			<table class="table text-center">
				<thead class="thead-dark">
					<tr role="row">
						<th style="width: 30px;">
							<input type="checkbox" name="checkAll" onclick="javascript:CheckAll()" />
						</th>
						<th style="width: 180px;">이름</th>
						<th style="width: 180px;">직책(직급)</th>
						<th style="width: 180px;">회사명</th>
						<th style="width: 180px;">휴대폰번호</th>
						<th style="width: 180px;">회사번호</th>
						<th style="width: 180px;">이메일</th>
						<th style="width: 180px;">위치</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${cardpaginglist}" var="callingcard">
						<tr role="row">
							<td>
								<a><input type="checkbox" name="check_card" value="${callingcard.cal_sq}" /></a>
							</td>
							<td class="cardTd" data-calsq="${callingcard.cal_sq}">${callingcard.cal_nm}</td>
							<td>${callingcard.cal_grade}</td>
							<td>${callingcard.cal_com}</td>
							<td>${callingcard.cal_phone}</td>
							<td>${callingcard.cal_com_phone}</td>
							<td>${callingcard.cal_mail}</td>
							<td>
								<button type="button" class="btn btn-link" data-toggle="modal" data-calcom="${callingcard.cal_com}" data-caladdr="${callingcard.cal_addr}" data-target="#mapModal"><i class="fa fa-map-marker"></i></button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>

		<div class="dataTables_paginate paging_simple_numbers"
			id="bootstrap-data-table_paginate">
			<ul class="pagination">
				<!-- 첫번째 페이지 -->
				<c:choose>
					<c:when test="${page == '1'}">
						<li class="paginate_button page-item previous disabled"
							id="bootstrap-data-table_previous"><a
							aria-controls="bootstrap-data-table" tabindex="0"
							class="page-link"><i class="fa fa-angle-double-left"></i></a></li>
					</c:when>
					<c:otherwise>
						<li id="bootstrap-data-table_previous"><a
							href="${cp}/cardList?page=1&selCode=${selCode}&selText=${selText}"
							aria-controls="bootstrap-data-table" tabindex="0"
							class="page-link"><i class="fa fa-angle-double-left"></i></a></li>
					</c:otherwise>
				</c:choose>

				<!-- 페이지 -->
				<c:choose>
					<c:when test="${startPage == lastPageStartPage}">
						<%-- 마지막페이지가 해당되는 페이지의 시작페이지이면 다음로직 실행 --%>
						<c:forEach begin="${lastPageStartPage}" end="${lastPage}" var="i">
							<c:set var="active" value="" />
							<c:if test="${i == page}">
								<c:set var="active" value="paginate_button page-item active" />
							</c:if>
							<li class="${active}"><a
								href="${cp}/cardList?page=${i}&selCode=${selCode}&selText=${selText}"
								aria-controls="bootstrap-data-table" data-dt-idx="1"
								tabindex="0" class="page-link">${i}</a></li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach begin="${startPage}" end="${endPage}" var="i">
							<c:set var="active" value="" />
							<c:if test="${i == page}">
								<c:set var="active" value="paginate_button page-item active" />
							</c:if>
							<li class="${active}"><a
								href="${cp}/cardList?page=${i}&selCode=${selCode}&selText=${selText}"
								aria-controls="bootstrap-data-table" data-dt-idx="1"
								tabindex="0" class="page-link">${i}</a></li>
						</c:forEach>
					</c:otherwise>
				</c:choose>

				<!-- 마지막페이지 -->
				<c:choose>
					<c:when test="${page == (lastPage)}">
						<li class="paginate_button page-item next"
							id="bootstrap-data-table_next"><a
							aria-controls="bootstrap-data-table" tabindex="0"
							class="page-link"><i class="fa fa-angle-double-right"></i></a></li>
					</c:when>
					<c:otherwise>
						<li id="bootstrap-data-table_next"><a
							href="${cp}/cardList?page=${lastPage}&selCode=${selCode}&selText=${selText}"
							aria-controls="bootstrap-data-table" tabindex="0"
							class="page-link"><i class="fa fa-angle-double-right"></i></a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</div>
<div class="modal fade" id="mapModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-body">
			<div id="map" style="width:800px; height:500px;"></div>
		</div>
	</div>		
</div>

<form id="searchfrm" action="${cp}/cardSelect">
	<input type="hidden" id="selCode" name="selCode" />
	<input type="hidden" id="selText" name="selText" />
</form>

<script> 
	$(document).ready(function () {
		console.log("준비");
		//input 포커스
		$("#inputText").focus();
			
		var selCode = 1;
		$("#selCode").val(selCode);
			
		//select박스
		$("#selectbox").on("change", function() {
			//input 포커스
			$("#inputText").focus();
			
			//선택코드(이름, 회사명, 직급명)
			console.log($("select[name=selector]").val());
			selCode = $("select[name=selector]").val();
			$("#selCode").val(selCode);
		});
			
		//검색버튼 클릭시
		$("#btnSearch").on("click", function() {
			//input값
			console.log($("#inputText").val());
			var selText = $("#inputText").val();
			$("#selText").val(selText);
			
			
			$("#searchfrm").submit();
		});
		
		$(".cardTd").on("click",function(){
			var cal_sq = $(this).data("calsq");
			var tdArr = new Array();
			var td = $(this);
				
			$("#cal_sq").val(cal_sq);
			$("#frm").submit();
			
		});
		
		$("#del_btn").on("click", function () {
			var arr_del = new Array();
			$("input[name=check_card]:checked").each(function() {
				console.log($(this).val());
		
				arr_del.push($(this).val());
				$("#del_cal_sq").val(arr_del);
		
				$("#deletefrm").submit();
			});
		});
		
	   
		var check = false;
		function CheckAll() {
			var chk = document.getElementsByName("check_card");
			
			if (check == false) {
				check = true;
			
				for (var i = 0; i < chk.length; i++) {
					chk[i].checked = true; //모두 체크
				}
			} else {
				check = false;
			
				for (var i = 0; i < chk.length; i++) {
					chk[i].checked = false; //모두 해제
				}
			}
		}
		
		function checkdelete() {
			if (confirm("정말 삭제하시겠습니까??") == true) { //확인
			
			} else { //취소
				return false;
			}
		}
	});	
	
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>	
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8490ff1d393b934759812aa4e72644a9&libraries=services"></script>
<script type="text/javascript">
	var jquery = $.noConflict(true);
		
	jquery("#mapModal").on("shown.bs.modal", function(event){
		var button = jquery(event.relatedTarget);
		var cal_addr = button.data("caladdr");
		var cal_com = button.data("calcom");
		console.log(cal_addr);
		
		var mapContainer = document.getElementById("map"), // 지도를 표시할 div 
		mapOption = {
		    center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		    level: 3 // 지도의 확대 레벨
		};  
			
		// 지도를 생성합니다    
		var map = new daum.maps.Map(mapContainer, mapOption); 
		
		var geocoder = new daum.maps.services.Geocoder();
			
		// 주소로 좌표를 검색합니다
		geocoder.addressSearch(cal_addr, function(result, status) {
			
		    // 정상적으로 검색이 완료됐으면 
		     if (status === daum.maps.services.Status.OK) {
			
		        var coords = new daum.maps.LatLng(result[0].y, result[0].x);
				
		        console.log(result[0].y);
		        console.log(result[0].x);
		        	
		        // 결과값으로 받은 위치를 마커로 표시합니다
		        var marker = new daum.maps.Marker({
		            map: map,
		            position: coords
		        });
			
		        // 인포윈도우로 장소에 대한 설명을 표시합니다
		        var infowindow = new daum.maps.InfoWindow({
		            content: '<div style="width:150px;text-align:center;padding:6px 0;">"'+cal_com+'"</div>'
		        });
		        infowindow.open(map, marker);
			
		        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
		        map.setCenter(coords);
		    }  
		});    
	});
</script>