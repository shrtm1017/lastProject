<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>

var dayInfo;
var arrData = new Array();
var flagIndex = 0;

var chkDiv = "${scd_div_sq}";
var chkDpt = "${memDpts}";

Date.prototype.yyyymmdd = function() {
  var mm = this.getMonth() + 1; // getMonth() is zero-based
  var dd = this.getDate();

  return [this.getFullYear(),
          (mm>9 ? '' : '0') + mm,
          (dd>9 ? '' : '0') + dd
         ].join('');
};


$(document).ready(function() {
	scheduleView();
	console.log(arrData);
	
	$('.input-group.date').datepicker({
	    format: "yyyymmdd",
	    language: "kr"
	});
	$("#closebtn").on("click",function(){
		$('#myModal').hide();
	});
	$("#closebtn2").on("click",function(){
		$('#ModalDetail').hide();
	});
	$("#deletebtn").on("click",function(){
		var dataParam = {
				"scd_sq" : dayInfo.id,
				"scd_div_sq" : "${scd_div_sq}",
				"scd_emp_sq" : "${scd_emp_sq}"
		}
		
		$.ajax({
			url : "${cp}/deleteSchedule",
			type : "POST",
			dataType : "json",
			async : false,
			data : JSON.stringify(dataParam), // stringify는 post로 보내야만 하고 @RequestBody Map으로 한다
			contentType : "application/json; charset=UTF-8",
			
			success : function(data,text,request) {
				$('#ModalDetail').hide(); // 모달창을 가림
				
				$('#calendar').fullCalendar( "refetchEvents" ); // 데이터를 삭제할 경우 화면 재출력
			},
			error:function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	});

	$("#updatebtn").on("click",function(){
		var startDay;
		var endDay;

		if(flagIndex ==0){
			startDay = dayInfo.start.yyyymmdd();
			endDay = dayInfo.end;
			
			if(endDay == null){
				endDay = startDay;
			}else{
				endDay = dayInfo.end.yyyymmdd();
			}
		}else if(flagIndex ==1){
			startDay = $("#start_date").val();
			endDay = $("#end_date").val();
		}
		
		var dataParam = {
			"scd_title" : $("#scd_title_up").val(),
			"scd_con" : $("#scd_con_up").val(),
			"scd_sq" : dayInfo.id,
			"scd_div_sq" : "${scd_div_sq}",
			"scd_emp_sq" : "${scd_emp_sq}",
			"scd_str_dt" : startDay,
			"scd_end_dt" : endDay
		}
		
		$.ajax({
			url : "${cp}/updateSchedule",
			type : "POST",
			dataType : "json",
			async : false,
			data : JSON.stringify(dataParam), // stringify는 post로 보내야만 하고 @RequestBody Map으로 한다
			contentType : "application/json; charset=UTF-8",
			
			success : function(data,text,request) {
				$('#ModalDetail').hide(); // 모달창을 숨기는 기능
				
				$('#calendar').fullCalendar( "refetchEvents" ); // 데이터가 변경될시 화면에 다시 뿌려주는 함수
			},
			error:function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	});
	
    var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();
	
	var params = ${scd_div_sq};
	var motitle; 
	var mostart;
	var moend;
	var moallDay;
	var mocontent;
	var moselect;

	/*  className colors
	className: default(transparent), important(red), chill(pink), success(green), info(blue)
	*/

	/* initialize the external events */
	$('#external-events div.external-event').each(function() {
		// create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
		// it doesn't need to have a start or end
		var eventObject = {
			title: $.trim($(this).text()) // use the element's text as the event title
		};

		// store the Event Object in the DOM element so we can get to it later
		$(this).data('eventObject', eventObject);

		// make the event draggable using jQuery UI
		$(this).draggable({
			zIndex: 999,
			revert: true,      // will cause the event to go back to its
			revertDuration: 0  //  original position after the drag
		});

	});

	/* initialize the calendar
	-----------------------------------------------------------------*/
	var calendar =  $('#calendar').fullCalendar({
		height: 850,
		header: {
			left: 'title',
			center: 'month',
			right: 'prev,next today'
		},
		editable: true,
		firstDay: 0, //  1(Monday) this can be changed to 0(Sunday) for the USA system
		selectable: true,
		defaultView: 'month',
		
		axisFormat: 'h:mm',
		columnFormat: {
               month: 'ddd',    // Mon
               week: 'ddd d', // Mon 7
               day: 'dddd M/d',  // Monday 9/7
               agendaDay: 'dddd d'
           },
           titleFormat: {
               month: 'yyyy년 MMMM', // September 2009
               week: "MMMM yyyy", // September 2009
               day: 'MMMM yyyy'                  // Tuesday, Sep 8, 2009
           },
		allDaySlot: false,
		selectHelper: true,
		select: function(start, end, allDay) {
			if(chkDiv==3){
				if(chkDpt==1){
					
					// 모달창 생성
					$('#myModal').show();
					
					$("#savebtn").on("click",function(){
						
						if($("#scd_title").val() ==""){
							alert("일정 명을 입력해주세요");
							return;
						}
						
						motitle 	= $("#scd_title").val(); 
						mostart 	= start.yyyymmdd();
						moend 		= end.yyyymmdd();
						moallDay 	= allDay;
						mocontent 	= $("#scd_con").val();
						moselect	= $("#scd_select").val();
						
						$("#scd_str_dt").val(mostart);
						$("#scd_end_dt").val(moend);
						$("#scd_allday").val(moallDay);
						$("#scd_sel").val($("#scd_select").val());
						
						$("#modalInfo").submit();
											
					});
					
					calendar.fullCalendar('unselect');
				}
				else{
					console.log("관리자가 아님");
				}
			}else{
				// 모달창 생성
				$('#myModal').show();
				
				$("#savebtn").on("click",function(){
					
					if($("#scd_title").val() ==""){
						alert("일정 명을 입력해주세요");
						return;
					}
					
					motitle 	= $("#scd_title").val(); 
					mostart 	= start.yyyymmdd();
					moend 		= end.yyyymmdd();
					moallDay 	= allDay;
					mocontent 	= $("#scd_con").val();
					moselect	= $("#scd_select").val();
					
					$("#scd_str_dt").val(mostart);
					$("#scd_end_dt").val(moend);
					$("#scd_allday").val(moallDay);
					$("#scd_sel").val($("#scd_select").val());
					
					$("#modalInfo").submit();
										
				});
				
				calendar.fullCalendar('unselect');
			}
			
		},
		
		droppable: true, // this allows things to be dropped onto the calendar !!!
		drop: function(date, allDay) { // this function is called when something is dropped

			// retrieve the dropped element's stored Event Object
			var originalEventObject = $(this).data('eventObject');

			// we need to copy it, so that multiple events don't have a reference to the same object
			var copiedEventObject = $.extend({}, originalEventObject);

			// assign it the date that was reported
			copiedEventObject.start = date;
			copiedEventObject.allDay = allDay;

			// render the event on the calendar
			// the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
			$('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

			// is the "remove after drop" checkbox checked?
			if ($('#drop-remove').is(':checked')) {
				// if so, remove the element from the "Draggable Events" list
				$(this).remove();
			}

		},
		events:	function(start, end, callback) {
			$.ajax({
				url : "${cp}/selectSchedule",
				type : "POST",
				dataType : "json",
				async : false,
				data : JSON.stringify(params), // stringify는 post로 보내야만 하고 @RequestBody Map으로 한다
				contentType : "application/json; charset=UTF-8",
				
				success : function(data,text,request) {
					var events = [];
					
					arrData.length = 0; // 이전 자료 초기화
					var arr = data;
					for(var i=0;i<arr.length;i++){
						arrData.push(arr[i]);
					}
					events = arrData;
					callback(events);
				},
				error:function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
		},
		eventClick:function(event) {
			// 여기에 이벤트 클릭시 생기는 모달창 생성
			
			dayInfo=event;
			
			var param = {
					"id": dayInfo.id
			}
			
			if(chkDiv==3){
				if(chkDpt==1){
					$.ajax({
						url : "${cp}/readSchedule",
						type : "POST",
			 			dataType : "json",
			 			async : false,
						data : JSON.stringify(param), // stringify는 post로 보내야만 하고 @RequestBody Map으로 한다
						contentType : "application/json; charset=UTF-8",
						beforeSend : function() {
							$("#scd_con_up").empty();
							$("#scd_title_up").empty();
						},
						success : function(data) {
							var startDay = dayInfo.start;
							var endDay = dayInfo.end;
							
							if(endDay == null){
								endDay = startDay;
							}
							
							$("#scd_con_up").val(data.scd_con);
							$("#scd_title_up").val(dayInfo.title);
							
							$("#start_date").val(startDay.yyyymmdd());
							$("#end_date").val(endDay.yyyymmdd());
							
							$("#ModalDetail").show();
						},
						error:function(request, status, error){
							alert("에러발생");
						}
					});
				}
			}else{
				$.ajax({
					url : "${cp}/readSchedule",
					type : "POST",
		 			dataType : "json",
		 			async : false,
					data : JSON.stringify(param), // stringify는 post로 보내야만 하고 @RequestBody Map으로 한다
					contentType : "application/json; charset=UTF-8",
					beforeSend : function() {
						$("#scd_con_up").empty();
						$("#scd_title_up").empty();
					},
					success : function(data) {
						var startDay = dayInfo.start;
						var endDay = dayInfo.end;
						
						if(endDay == null){
							endDay = startDay;
						}
						
						$("#scd_con_up").val(data.scd_con);
						$("#scd_title_up").val(dayInfo.title);
						
						$("#start_date").val(startDay.yyyymmdd());
						$("#end_date").val(endDay.yyyymmdd());
						
						$("#ModalDetail").show();
					},
					error:function(request, status, error){
						alert("에러발생");
					}
				});
			}
			
			
			
		}
	});
});

var params=${scd_div_sq};

function scheduleView(){
	$.ajax({
		url : "${cp}/selectSchedule",
		type : "POST",
		dataType : "json",
		async : false,
		data : JSON.stringify(params), // stringify는 post로 보내야만 하고 @RequestBody Map으로 한다
		contentType : "application/json; charset=UTF-8",
		
		success : function(data,text,request) {
			
			arrData.length = 0; // 이전 자료 초기화
			var arr = data;
			for(var i=0;i<arr.length;i++){
				arrData.push(arr[i]);
			}
		},
		error:function(request, status, error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
} 


function flag(){
	flagIndex=1;
}

</script>

<style>
body {
/*  	margin-top: 40px;   */
/* 	text-align: center; */
	font-size: 14px;
	font-family: "Helvetica Nueue", Arial, Verdana, sans-serif;
	background-color: #DDDDDD;
}

#wrap {
	width: 1100px;
	margin: 0 auto;
}

#external-events {
	float: left;
	width: 150px;
	padding: 0 10px;
	text-align: left;
}

#external-events h4 {
	font-size: 16px;
	margin-top: 0;
	padding-top: 1em;
}

.external-event { /* try to mimick the look of a real event */
	margin: 10px 0;
	padding: 2px 4px;
	background: #3366CC;
	color: #fff;
	font-size: .85em;
	cursor: pointer;
}

#external-events p {
	margin: 1.5em 0;
	font-size: 11px;
	color: #666;
}

#external-events p input {
	margin: 0;
	vertical-align: middle;
}

#calendar {
	/* 		float: right; */
	margin: 0 auto;
	width: 1400px;
	background-color: #FFFFFF;
	border-radius: 6px;
	box-shadow: 0 1px 2px #C3C3C3;
}

.modal.modal-center {
	text-align: center;
}

@media screen and (min-width: 400px) {
	.modal.modal-center:before {
		display: inline-block;
		vertical-align: middle;
		content: " ";
		height: 100%;
	}
}

.modal-dialog.modal-center {
	display: inline-block;
	text-align: left;
	vertical-align: middle;
}

@media (max-width: 1368px)
.content {
    padding: 0 0px;
}

.content {
    float: left;
    padding: 0 0px;
    width: 100%;
}

.col, .col-1, .col-10, .col-11, .col-12, .col-2, .col-3, .col-4, .col-5, .col-6, .col-7, .col-8, .col-9, .col-auto, .col-lg, .col-lg-1, .col-lg-10, .col-lg-11, .col-lg-12, .col-lg-2, .col-lg-3, .col-lg-4, .col-lg-5, .col-lg-6, .col-lg-7, .col-lg-8, .col-lg-9, .col-lg-auto, .col-md, .col-md-1, .col-md-10, .col-md-11, .col-md-12, .col-md-2, .col-md-3, .col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9, .col-md-auto, .col-sm, .col-sm-1, .col-sm-10, .col-sm-11, .col-sm-12, .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-sm-auto, .col-xl, .col-xl-1, .col-xl-10, .col-xl-11, .col-xl-12, .col-xl-2, .col-xl-3, .col-xl-4, .col-xl-5, .col-xl-6, .col-xl-7, .col-xl-8, .col-xl-9, .col-xl-auto {
    position: relative;
    width: 100%;
    min-height: 1px;
    padding-right: 0px;
    padding-left: 0px;
}
</style>

<div id='wrap' class="col-lg-12">
	<div class="modal modal-center fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-center">
			<div class="modal-content" style="text-align: center;">
				<div class="modal-body">
					<form id="modalInfo" action="${cp }/insertSchedule">
						<input type="hidden" id="scd_str_dt" name="scd_str_dt"> <input
							type="hidden" id="scd_end_dt" name="scd_end_dt"> <input
							type="hidden" id="scd_allday" name="scd_allday"> <input
							type="hidden" id="scd_sel" name="scd_sel"> <input
							type="hidden" id="scd_div_sq" name="scd_div_sq"
							value="${scd_div_sq }">
						<div class="form-group">
							<input type="hidden" class="form-control" id="scd_sq"
								name="scd_sq" readonly="readonly" value="${scd_sq }">
						</div>
						<div class="form-group">
							<label for="title"><strong>일정 선택<strong></label>
							<select class="form-control" id="scd_select">
								<option value="important" style="background-color: #FFBEBE">출장</option>
								<option value="chill" style="background-color: #FFFF48">미팅</option>
								<option value="success" style="background-color: #BEFFBF">회의</option>
								<option value="info" style="background-color: #D1B2FF">행사</option>
							</select>
						</div>
						<div class="form-group">
							<label for="title"><strong>일정명</strong></label>
							<input type="text"
								class="form-control" id="scd_title" name="scd_title">
						</div>
						<div class="form-group">
							<label for="title"><strong>상세 내용</strong></label>
							<textarea class="form-control" id="scd_con" name="scd_con"></textarea>
						</div>

						<div class="form-group">
							<input type="button" id="savebtn" class="btn btn-info btn-sm col-sm-4" value="저장">
							<input type="button" id="closebtn" class="btn btn-secondary btn-sm col-sm-4" value="종료">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal modal-center fade" id="ModalDetail" role="dialog">
		<div class="modal-dialog modal-center">
			<div class="modal-content" style="text-align: center;">
				<div class="modal-body">
					<form id="modalInfo" action="${cp }/insertSchedule">
						
						<div class="form-group">
			               <div class="input-group date">
			                   <input id="start_date" type="text" class="form-control" onchange="flag()">
			                   <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
			               </div>
				        </div>
						<div class="form-group">
			               <div class="input-group date">
			                   <input id="end_date" type="text" class="form-control" onchange="flag()">
			                   <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
			               </div>
				        </div>
						
						<input type="hidden" id="scd_str_dt_up" name="scd_str_dt_up">
						<input type="hidden" id="scd_end_dt_up" name="scd_end_dt_up">
						<input type="hidden" id="scd_allday_up" name="scd_allday_up">
						<input type="hidden" id="scd_sel_up" name="scd_sel_up"> <input
							type="hidden" id="scd_div_sq_up" name="scd_div_sq_up">
						<div class="form-group">
							<input type="hidden" class="form-control" id="scd_sq_up"
								name="scd_sq_up" readonly="readonly" value="${scd_sq }">
						</div>
						<div class="form-group">
							<label for="title"><strong>일정명</strong></label>
							<input type="text"
								class="form-control" id="scd_title_up" name="scd_title_up">
						</div>
						<div class="form-group">
							<label for="title"><strong>상세 내용</strong></label>
							<textarea class="form-control" id="scd_con_up" name="scd_con_up" >
							</textarea>
						</div>

						<div class="form-group">
							<input type="button" id="updatebtn" class="btn btn-info btn-sm col-sm-3" value="변경"> 
							<input type="button" id="deletebtn" class="btn btn-danger btn-sm col-sm-3" value="삭제"> 
							<input type="button" id="closebtn2" class="btn btn-secondary btn-sm col-sm-3" value="종료">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div id='calendar'></div>
	<div style='clear: both'></div>
</div>