<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!--   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"> -->
  <link rel="stylesheet" type="text/css" href="https://uicdn.toast.com/tui.time-picker/latest/tui-time-picker.css">
  <link rel="stylesheet" type="text/css" href="https://uicdn.toast.com/tui.date-picker/latest/tui-date-picker.css">
  <link rel="stylesheet" type="text/css" href="${cp}/css/calendar/dist/tui-calendar.css" />
  <link rel="stylesheet" type="text/css" href="${cp}/css/calendar/examples/css/default.css"></link>
  <link rel="stylesheet" type="text/css" href="${cp}/css/calendar/examples/css/icons.css"></link>

  <div class="col col-md-12">
    <div id="menu">
      <span id="menu-navi">
        <button type="button" class="btn btn-default btn-sm move-today" data-action="move-today">Today</button>
        <button type="button" class="btn btn-default btn-sm move-day" data-action="move-prev">
          <i class="calendar-icon ic-arrow-line-left" data-action="move-prev"></i>
        </button>
        <button type="button" class="btn btn-default btn-sm move-day" data-action="move-next">
          <i class="calendar-icon ic-arrow-line-right" data-action="move-next"></i>
        </button>
      </span>
      <span id="renderRange" class="render-range"></span>
    </div>
    
    <div id="calendar" style="height: 750px" >          
    </div>
  </div>

  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
      crossorigin="anonymous"></script>
<!--   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script> -->
  <script type="text/javascript" src="https://uicdn.toast.com/tui.code-snippet/latest/tui-code-snippet.min.js"></script>
  <script type="text/javascript" src="https://uicdn.toast.com/tui.time-picker/latest/tui-time-picker.min.js"></script>
  <script type="text/javascript" src="https://uicdn.toast.com/tui.date-picker/latest/tui-date-picker.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.20.1/moment.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/chance/1.0.13/chance.min.js"></script>
  <script src="${cp}/css/calendar/dist/tui-calendar.js"></script>
  <script src="${cp}/css/calendar/examples/js/data/calendars.js"></script>
  <script src="${cp}/css/calendar/examples/js/data/schedules.js"></script>
  
  <script type="text/javascript" class="code-js">
  
  var chk = true;
  var privateChk = $("#tui-full-calendar-schedule-private").on("click",function(){
	  console.log("체크체크");
	  chk = chk*false;
  }); // 기본값 true
  
  $(document).ready(function(){
	  console.log("document.ready");
	  $(".tui-full-calendar-popup-save").on("click",function(){
		  console.log("Test!!!!");
		  
		  var allday = $("#tui-full-calendar-schedule-allday").is(":checked"); 	// 모든날짜 체크 true false로 반환
		  var state = $("#tui-full-calendar-schedule-state").html(); 			// 상태 busy free
		  var selectChk = $("#tui-full-calendar-schedule-calendar").html(); 	// 항목
		  var title = $("#tui-full-calendar-schedule-title").val();				// 제목
		  var location = $("#tui-full-calendar-schedule-location").val(); 		// 위치
		  var start = $("#tui-full-calendar-schedule-start-date").val(); 		// 시작날짜
		  var end = $("#tui-full-calendar-schedule-end-date").val();			// 종료날짜
		  
		  console.log(selectChk);
		  console.log(title);
		  console.log(privateChk);
		  console.log(location);
		  console.log(start);
		  console.log(end);
		  console.log(allday);
		  console.log(state);
	  });
  });
  
  // register templates
  const templates = {
	
		  clickDaynamecalendar : function(){
			  console.log("clickDaynamecalendar");
		  },
	beforeUpdateSchedule : function(){
		console.log("beforeUpdateSchedule");
	},
// 	beforeCreateSchedule : function(){
// 		console.log("beforeCreateSchedule");		  
// 	},
	popupIsAllDay: function() {
      return 'All Day';
    },
    popupStateFree: function() {
      return 'Free';
    },
    popupStateBusy: function() {
      return 'Busy';
    },
    titlePlaceholder: function() {
      return 'Subject';
    },
    locationPlaceholder: function() {
      return 'Location';
    },
    startDatePlaceholder: function() {
      return 'Start date';
    },
    endDatePlaceholder: function() {
    	console.log("test!");
      return 'End date';
    },
    popupSave: function() {
    	console.log("test!!!");
      return 'Save';
    },
    popupUpdate: function() {
    	console.log("update!!!");
      return 'Update';
    },
    popupDetailDate: function(isAllDay, start, end) {
      var isSameDate = moment(start).isSame(end);
      var endFormat = (isSameDate ? '' : 'YYYY.MM.DD ') + 'hh:mm a';

      if (isAllDay) {
        return moment(start).format('YYYY.MM.DD') + (isSameDate ? '' : ' - ' + moment(end).format('YYYY.MM.DD'));
      }

      return (moment(start).format('YYYY.MM.DD hh:mm a') + ' - ' + moment(end).format(endFormat));
    },
    popupDetailLocation: function(schedule) {
      return 'Location : ' + schedule.location;
    },
    popupDetailUser: function(schedule) {
      return 'User : ' + (schedule.attendees || []).join(', ');
    },
    popupDetailState: function(schedule) {
      return 'State : ' + schedule.state || 'Busy';
    },
    popupDetailRepeat: function(schedule) {
      return 'Repeat : ' + schedule.recurrenceRule;
    },
    popupDetailBody: function(schedule) {
      return 'Body : ' + schedule.body;
    },
    popupEdit: function() {
      return 'Edit';
    },
    popupDelete: function() {
      return 'Delete';
    }
  };
  
  var cal = new tui.Calendar('#calendar', {
    defaultView: 'month',
    template: templates,
    useCreationPopup: true,
    useDetailPopup: true
	
  });
  
  
  
  
  cal.on({
	    'clickSchedule': function(e) {
	        console.log('clickSchedule', e);
	    },
	    'beforeCreateSchedule': function(e) {
	        console.log('beforeCreateSchedule', e);
	        // open a creation popup

	        // If you dont' want to show any popup, just use `e.guide.clearGuideElement()`

	        // then close guide element(blue box from dragging or clicking days)
	        e.guide.clearGuideElement();
	    },
	    'beforeUpdateSchedule': function(e) {
	        console.log('beforeUpdateSchedule', e);
	        e.schedule.start = e.start;
	        e.schedule.end = e.end;
	        cal.updateSchedule(e.schedule.id, e.schedule.calendarId, e.schedule);
	    },
	    'beforeDeleteSchedule': function(e) {
	        console.log('beforeDeleteSchedule', e);
	        cal.deleteSchedule(e.schedule.id, e.schedule.calendarId);
	    }
	});
  
  console.log("/script");
  
  
  
  </script>
  <script src="${cp}/js/calendar/default.js"></script>
