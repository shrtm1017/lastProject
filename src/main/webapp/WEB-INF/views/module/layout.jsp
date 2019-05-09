<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
  
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>NK Office</title>
    <meta name="description" content="Sufee Admin - HTML5 Admin Template">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	
    <link rel="stylesheet" href="${cp}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${cp}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${cp}/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="${cp}/css/style.css">

    <link rel='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>
 	<script src="${cp}/js/jquery-3.3.1.min.js"></script>
    <script src="${cp}/js/popper.min.js"></script>
    <script src="${cp}/js/bootstrap.min.js"></script>
    <script src="${cp}/js/main.js"></script>
    
    <!-- jquery cdn 추가 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    
    <!-- 데이트 피커 css, js 추가 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">
    <script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
    <script src="${cp}/js/datepicker/bootstrap-datepicker.kr.js" charset="UTF-8"></script>
    
    <!-- 스마트에디터 부분 js 추가 -->
    <script src="${cp}/SE2/js/HuskyEZCreator.js"></script>
	
    <!-- chosen select css, js 추가 -->
    <link rel="stylesheet" href="${cp}/css/chosen/chosen.min.css">
	<script src="${cp}/js/chosen/chosen.jquery.min.js" type="text/javascript"></script>
    
    <!-- calendar -->
    <link href='${cp}/css/calendar/fullcalendar.css' rel='stylesheet' />
	<link href='${cp}/css/calendar/fullcalendar.print.css' rel='stylesheet' media='print' />
	<script src='${cp}/js/calendar/jquery-ui.custom.min.js' type="text/javascript"></script>
	<script src='${cp}/js/calendar/fullcalendar.js' type="text/javascript"></script>
	
	<style>
		#page-title-style { width: 50%; text-align: center; }
		#page-title-style:after {
			content: ' ';
			display: block;
			width: 100%;
			height: 2px;
			margin-top: 10px;
			background: -moz-linear-gradient(left, rgba(140,140,140,0) 0%, rgba(140,140,140,0.8) 20%, rgba(140,140,140,1) 53%, rgba(140,140,140,0.8) 79%, rgba(140,140,140,0) 100%); 
			background: -webkit-gradient(linear, left top, right top, color-stop(0%,rgba(140,140,140,0)), color-stop(20%,rgba(140,140,140,0.8)), color-stop(53%,rgba(140,140,140,1)), color-stop(79%,rgba(140,140,140,0.8)), color-stop(100%,rgba(140,140,140,0))); 
			background: -webkit-linear-gradient(left, rgba(140,140,140,0) 0%,rgba(140,140,140,0.8) 20%,rgba(140,140,140,1) 53%,rgba(140,140,140,0.8) 79%,rgba(140,140,140,0) 100%); 
			background: -o-linear-gradient(left, rgba(140,140,140,0) 0%,rgba(140,140,140,0.8) 20%,rgba(140,140,140,1) 53%,rgba(140,140,140,0.8) 79%,rgba(140,140,140,0) 100%); 
			background: -ms-linear-gradient(left, rgba(140,140,140,0) 0%,rgba(140,140,140,0.8) 20%,rgba(140,140,140,1) 53%,rgba(140,140,140,0.8) 79%,rgba(140,140,140,0) 100%); 
			background: linear-gradient(left, rgba(140,140,140,0) 0%,rgba(140,140,140,0.8) 20%,rgba(140,140,140,1) 53%,rgba(140,140,140,0.8) 79%,rgba(140,140,140,0) 100%); 
		}
	</style>
</head>
<body style="width: 1683px; background: white;">
	<!-- Left Panel -->
	<tiles:insertAttribute name="left"/>
	<!-- Left Panel -->

    <!-- Right Panel -->
    <div id="right-panel" class="right-panel">
		<tiles:insertAttribute name="header"/>


        <div class="content">
        	<tiles:insertAttribute name="content"/>
        </div>
        
         <tiles:insertAttribute name="footer"/>
    </div>
    <!-- Right Panel -->
</body>
</html>