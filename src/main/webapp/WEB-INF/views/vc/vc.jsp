<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="breadcrumbs">
	<div class="col-sm-4">
		<div class="page-header float-left" id="page-title-style">
			<div class="page-title">
				<h1 style="padding-bottom: 0px;">
					<strong>화상회의</strong>
				</h1>
			</div>
		</div>
	</div>
</div>

<div class="container"><br>
	<div class="card">
		<div class="card-body">
			<div class="row">
				<div class="col-sm-6">
					<h5>
						<img class="user-avatar rounded-circle" src="/images/noimg.png"
							style="width: 30px; height: 30px;">&nbsp;
						<strong>상대방</strong>
					</h5>
					<hr style="margin-top: 10px;">
					<video controls id="receiver-video" width="100%" height="400px"></video>
					<p>
						<button id="start" class="btn btn-info btn-sm" style="width: 80px;">
							<i class="fa fa-share"></i>&nbsp;
							발신
						</button>
					</p>
					<textarea id="offer" class="form-control"></textarea>
				</div>
				<div class="col-sm-6">
					<h5>
						<img class="user-avatar rounded-circle" src="/userImg?emp_sq=${employeeVo.emp_sq }"
							style="width: 30px; height: 30px;">&nbsp;
						<strong>나</strong>
					</h5>
					<hr style="margin-top: 10px;">
					<video controls id="emitter-video" width="100%" height="400px"></video>
					<p>
						<button id="receive" class="btn btn-secondary btn-sm" style="width: 80px;">
							<i class="fa fa-refresh"></i>&nbsp;
							수신
						</button>
					</p>
					
					<form id="incoming">
						<div class="input-group">
							<textarea class="form-control"></textarea>
							<button type="submit" class="btn btn-primary">등록</button>
						</div>
						<p>
						</p>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="${cp}/js/webrtc/simplePeer.js"></script>
<script type="text/javascript">
	navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia;
	
	function bindEvents(p){
		p.on('error', function(err){
			console.log('error', err);
		});
		
		p.on('signal', function(data){
			document.querySelector('#offer').textContent = JSON.stringify(data);
		});
		
		p.on('stream', function(stream){
			let video = document.querySelector('#receiver-video');
			video.volume = 0;
			video.srcObject = stream;
			video.play();
		});
		
		document.querySelector('#incoming').addEventListener('submit', function(e){
			e.preventDefault();
			
			console.log("아아아아아");
			console.log(p);
			console.log(e.target.querySelector('textarea').value);
			console.log(JSON.parse(e.target.querySelector('textarea').value));
			console.log("아아아아아");
			p.signal(JSON.parse(e.target.querySelector('textarea').value));
		});
	}
	
	function startPeer(initiator){
		navigator.getUserMedia({
			video: true,
			audio: true
		}, function(stream){
			let p = new SimplePeer({
				initiator: initiator,
				stream: stream,
				trickle: false
			});
			
			bindEvents(p);
			
			let emitterVideo = document.querySelector('#emitter-video');
			emitterVideo.volume = 0;
			emitterVideo.srcObject = stream;
			emitterVideo.play();
		}, function(){})
	}
	
	document.querySelector('#start').addEventListener('click', function(e){
		startPeer(true);
	});
	
	document.querySelector('#receive').addEventListener('click', function(e){
		startPeer(false);
	});
</script>