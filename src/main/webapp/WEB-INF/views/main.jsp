<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<br>
<div>
	<div class="col-lg-12 col-sm-4"> <br>
		<div class="form-group">
			<div class="card col-md-4" style="left: 150px; right: 50px; height: 574px;">
				<div style="position: static;">
					<div class="form-group text-center">
						<img class="user-avatar rounded-circle"
							style="width: 140px; height: 140px; margin-top: 50px;"
							src="${cp }/userImg?emp_sq=${employeeVo.emp_sq }">
						<label style="font-size: 22px; vertical-align: middle; margin-left: 20px;">
							<br><br>
							안녕하세요! 
							<br>
							<strong>${employeeVo.emp_nm }</strong> 님
						</label>
					</div>
					<br>
					<div class="text-center">
						<a href="${cp}/userPassCheck" style="font-size: 16px; color: #4C4C4C;">
							<i class="fa fa-unlock-alt">
								내 정보
							</i>
						</a>
						&nbsp;&nbsp;&nbsp;
						<a href="${cp}/receiveMailbox" style="font-size: 16px; color: #4C4C4C;">
							<%-- 메일 --%>
							<i class="fa fa-envelope-o">
								메일 <label style="color: #008299;"><strong>${fn:length(emlReceiveList)}</strong></label>
							</i>
						</a>
						
						<div style="margin-top: 20px;">
							<c:choose>
								<c:when test="${commuteCheck == null }">
									<button type="button" id="Gotowork_Btn"
										class="btn btn-info btn-sm col-sm-4 active">출근</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-info btn-sm col-sm-4 active">출근</button>
								</c:otherwise>
							</c:choose>
							<button type="button" id="GotoHome_Btn"
								class="btn btn-secondary btn-sm col-sm-4 active">퇴근</button>
						</div>
						<hr style="width: 425px; background-color: gray;">
						<div class="card" style="height: 210px; padding-left: 50px; padding-right: 50px; background-color: #EBF0FF;">
                   	 		<div class="card-body pb-0">
		                        <h4 class="mb-0" id="city" style="float: left;">
		                        </h4>
		                        <br><br>
		                        <div class="chart-wrapper px-0" style="vertical-align: middle;">
	                        		<img id="weather" src="" style="height: 90px; float: left;">
									<label id="temp" style="float: right;"></label>
		                        </div>
		                    </div>
		            	</div>
					</div>
				</div>
			</div>
			<div class="card col-md-5" style="left: 180px; height: 300px;">
		        <div class="card-body pb-0">
		            <div class="dropdown float-right">
		                <button class="btn bg-transparent dropdown-toggle theme-toggle" type="button" id="dropdownMenuButton3" data-toggle="dropdown">
		                    <i class="fa fa-cog"></i>
		                </button>
		                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton3">
		                    <div class="dropdown-menu-content">
		                        <a class="dropdown-item" href="${cp}/apvReceive">
		                        	<i class="fa fa-caret-right"></i>
		                        	&nbsp;내가 받은 결재
		                        </a>
		                        <a class="dropdown-item" href="${cp}/apvSend">
		                        	<i class="fa fa-caret-right"></i>
		                        	&nbsp;내가 올린 결재
		                        </a>
		                        <a class="dropdown-item" href="${cp}/apvReference">
		                        	<i class="fa fa-caret-right"></i>
		                        	&nbsp;참조문서함
		                        </a>
		                    </div>
		                </div>
		            </div>
		            <h4>
		               	<strong>결재현황</strong>
		            </h4>
		            <div class="col-sm-12" style="height: 220px;">
		            	<canvas id="pieChart"></canvas>
		            </div>
		        </div>
		    </div>
			<div class="card col-md-5" style="left: 180px; height: 250px;">
		        <div class="card-body pb-0">
		            <div class="dropdown float-right">
		                <button class="btn bg-transparent dropdown-toggle theme-toggle" type="button" id="dropdownMenuButton3" data-toggle="dropdown">
		                    <i class="fa fa-cog"></i>
		                </button>
		                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton3">
		                    <div class="dropdown-menu-content">
		                        <a class="dropdown-item" href="${cp}/CmtLookup">
		                        	<i class="fa fa-caret-right"></i>
		                        	&nbsp;근태 관리
		                        </a>
		                    </div>
		                </div>
		            </div>
		            <h4>
		               	<strong>연차현황</strong>
		            </h4>
					<div style="text-align: center; margin-top: 30px;">
						<div style="float: left; margin-left: 100px;">
							<a style="font-size: 60px;"><i class="fa fa-meh-o"></i></a>
							<div class="location text-sm-center">
								<strong style="font-size: 17px;">총일수</strong>
							</div>
							<div class="location text-sm-center">
								<span class="badge badge-primary">15</span>
							</div>
						</div>
						<div style="float: left; margin-left: 20px;">
							<a style="font-size: 60px;"><i style="font-size: 30px;" class="fa fa-minus"></i></a>
						</div>
						<div style="float: left; margin-left: 20px;">
							<a style="font-size: 60px;">
								<i class="fa fa-frown-o"></i>
							</a>
							<div class="location text-sm-center">
								<strong style="font-size: 17px;">사용일수</strong>
							</div>
							<div class="location text-sm-center">
								<span class="badge badge-danger">${useDay }</span>
							</div>
						</div>
						<div style="float: left; margin-left: 20px;">
							<a style="font-size: 60px;"><i style="font-size: 30px;" class="fa fa-arrow-right"></i></a>
						</div>
						<div style="float: left; margin-left: 20px;">
							<a style="font-size: 60px;"><i class="fa fa-smile-o"></i></a>
							<div class="location text-sm-center">
								<strong style="font-size: 17px;">남은일수</strong>
							</div>
							<div class="location text-sm-center">
								<span class="badge badge-warning">${remainsDay }</span>
							</div>
						</div>
					</div>
		        </div>
		    </div>
		</div>
	    <div class="card col-md-4" style="left: 150px; right: 50px; height: 350px;">
	        <div class="card-body pb-0">
	            <div class="dropdown float-right">
	                <button class="btn bg-transparent dropdown-toggle theme-toggle" type="button" id="dropdownMenuButton3" data-toggle="dropdown">
	                    <i class="fa fa-cog"></i>
	                </button>
	                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton3">
	                    <a class="dropdown-item" href="${cp}/noticeBoard?ntc_div=1">
	                       	<i class="fa fa-caret-right"></i>
	                       	&nbsp;공지사항
	                       </a>
	                </div>
	            </div>
	            <h4>
	               	<strong>공지사항</strong>
	            </h4>
	            <hr style="border: dashed 1px #476600;">
				<table class="table">
					<!-- 최신 글 5개만 출력 -->
					<!-- 글 번호, 제목 클릭 시 상세 페이지로 이동 -->
					<c:forEach items="${noticeList }" var="list">
						<tr>
							<th style="width: 100px;">
								<a href="${cp}/detail?ntc_sq=${list.ntc_sq }&ntc_div=1" style="color: black;">
									${list.ntc_sq }
								</a>
							</th>
							<td>
								<a href="${cp}/detail?ntc_sq=${list.ntc_sq }&ntc_div=1" style="color: black;">
									${list.ntc_nm }
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>
	    	</div>
	    </div>
	    <div class="card col-md-5" style="left: 180px; right: 50px; height: 350px;">
	        <div class="card-body pb-0">
	        	<div class="dropdown float-right">
	                <button class="btn bg-transparent dropdown-toggle theme-toggle" type="button" id="dropdownMenuButton3" data-toggle="dropdown">
	                    <i class="fa fa-cog"></i>
	                </button>
	                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton3">
	                    <a class="dropdown-item" href="${cp}/researchList">
	                       	<i class="fa fa-caret-right"></i>
	                       	&nbsp;설문조사
	                       </a>
	                </div>
	            </div>
	            <h4>
	               	<strong>진행 중인 설문</strong>
	            </h4>
	            <hr style="border: dashed 1px #002266;">
	            <table class="table">
					<c:forEach items="${resList }" var="res">
						<tr>
							<th style="width: 100px;">
								<a href="${cp}/researchDetail?res_sq=${res.res_sq }" style="color: black;">${res.res_sq }</a>
							</th>
							<td>
								<a href="${cp}/researchDetail?res_sq=${res.res_sq }" style="color: black;">${res.res_nm }</a>
							</td>
							<td style="text-align: right;">
								<small style="color: #5D5D5D;"><strong><fmt:formatDate value="${res.res_end_dt }" pattern="yyyy-MM-dd" /></strong></small>
							</td>
						</tr>
					</c:forEach>
				</table>
	        </div>
	    </div>
	    <div class="card col-md-9" style="left: 165px; right: 50px; height: 400px;">
	        <div class="card-body pb-0">
	            <h4>
	               	<strong>NK Office 찾아오는 길</strong>
	            </h4>
	            <hr>
				<div id="map" style="width:940px; height:300px;"></div>
	        </div>
	    </div>
    </div>
</div>
<form id="frm" action="${cp }/GotoworkCmt"></form>

<input type="hidden" id="waitCnt" value="${waitCnt }" />
<input type="hidden" id="endCnt" value="${endCnt }" />
<input type="hidden" id="returnCnt" value="${returnCnt }" />
 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.1/Chart.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8490ff1d393b934759812aa4e72644a9&libraries=services"></script>
<script>
	$(document).ready(function() {
		<c:if test="${msg !=null}">
			alert("${msg}");
		</c:if>
		
		$("#GotoHome_Btn").on("click", function() {
			confirm("퇴근을 하시겠습니까?");
			$("#frm").attr("action", "${cp}/GotoHomeCmt");
			$("#frm").submit();
		});
		
		$("#Gotowork_Btn").on("click", function() {
			confirm("출근을 하시겠습니까?");
			$("#frm").submit();
		});
		
		var endCnt = $("#endCnt").val();
		var returnCnt = $("#returnCnt").val();
		var waitCnt = $("#waitCnt").val();
		var chartDiv = $("#pieChart");
		var myChart = new Chart(chartDiv, {
			type: 'pie',
		    data: {
		        labels: ["결재완료", "결재반려", "결재대기"],
		        datasets: [
		        {
		            data: [endCnt, returnCnt, waitCnt],
		            backgroundColor: [
			            "#4BC0C0",
			            "#FF6666",
			            "#FFCE56"
		            ]
		        }]
		    },
		    options: {
		        title: {
		            display: false,
		        },
				responsive: true,
				maintainAspectRatio: false,
		    }
		});
		
		// 회사 위치
		var mapContainer = document.getElementById("map"), // 지도를 표시할 div 
		mapOption = {
		    center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		    level: 3 // 지도의 확대 레벨
		};  
		
		var map = new daum.maps.Map(mapContainer, mapOption); 
		
		var geocoder = new daum.maps.services.Geocoder();
			
		// 주소로 좌표를 검색합니다
		geocoder.addressSearch('대전 중구 중앙로 76', function(result, status) {
			
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
		            content: '대전 중구 중앙로 76'
		        });
		        infowindow.open(map, marker);
			
		        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
		        map.setCenter(coords);
		    }  
		});
		
		var apiURI = "http://api.openweathermap.org/data/2.5/weather?q="+"Daejeon"+"&appid="+"f46074a2fbdc367b7f632d7e13beae43";
        $.ajax({
            url: apiURI,
            dataType: "json",
            type: "GET",
            async: "false",
            success: function(resp) {
                var imgURL = "http://openweathermap.org/img/w/" + resp.weather[0].icon + ".png";
                var weatherStr = resp.weather[0].icon;
                var weatherImg = weatherStr.substring(0, 2);
                
                $("#city").html("<strong>대전광역시</strong>");
                
                var info = "<strong style='font-size: 25px;'>" + parseInt(resp.main.temp- 273.15) + " ˚C</strong><br>";
                info += "<label style='font-size: 16px;'><i style='color: gray;' class='fa fa-tint'></i>&nbsp;&nbsp;습도 <strong>" + resp.main.humidity + "%</strong></label><br>";
                info += "<label style='font-size: 16px;'><i style='color: gray;' class='fa fa-cloud'></i>&nbsp;&nbsp;구름 <strong>" + resp.clouds.all + "%</strong></label>";
                
                $("#temp").html(info);

                $("#weather").attr("src", "images/weather/" + weatherImg + ".png");
            }
        });
	});
</script>