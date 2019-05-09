<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
	body {
		font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
		font-size: 14px;
	}
	
	#calendar {
		max-width: 900px;
		margin: 0 auto;
	}
	
	<%-- 일요일 색상변경 --%>
	.fc-sun { 
		color: #e31b23
	}
	
	<%-- 토요일 색상변경 --%>
	.fc-sat {
		color: #007dc3
	}
</style>

<div id="calendar" onclick="calplus()"></div>

<script>
	var data = [{"title":"오늘일정", "start":"2019-03-01", "end":"2019-03-03",
		"color":"#FF0000", "textColor":"#FFFF00"}]; 
		
	$(document).ready(function() {
		$("#calendar").fullCalendar({
			header : {
				left : "prev,next today", //이전달, 다음달, 오늘로 이동하는 기능
				center : "title", //현재 출력된 월에 대한 정보
				right : "month,basicWeek,basicDay" //날짜를 보는 기간(월, 주, 일)을 지정하는 옵션
			},
			defaultDate : new Date(), //오늘날짜를 보여주도록 기본값 지정
			navLinks : true, //월*주별 달력에서 일자를 클릭하면 일별 보기로 전환하는 기능을 사용할지 여부를 선택하는 옵션
			editable : true, //달력에서 일정(event)을 표시한 바(bar)를 마우스로 이동할 수 있게 하는 옵션
			eventLimit : true, //하루에 보여줄 일정(event)을 3개만 보여주고 그 이상은 more로 처리할지 여부를 선택하는 옵션
			events : data
		});
	});
	
	function calplus(){
		
	}
	
</script>