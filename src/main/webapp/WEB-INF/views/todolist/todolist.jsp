<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>	

<div class="breadcrumbs">
	<div class="col-sm-4">
	    <div class="page-header float-left" id="page-title-style">
	        <div class="page-title">
	            <h1 style="padding-bottom: 0px;"><strong>To Do List</strong></h1>
	        </div>
	    </div>
	</div>
</div>

<div>
	<div class="col-lg-12 col-sm-4">
		<div class="col col-md-2">
		</div>
		<div class="col col-md-8">
			<div style="position: relative; left: 10px; top: 20px; z-index: 2;">
				<img src="images/green.png" style="width: 150px;">
			</div>
			<div class="card" style="position: relative; z-index: 1;">
				<div class="card-body card-block">
					<form action="${cp}/createTodo">
						<div class="input-group">
							&nbsp;&nbsp;&nbsp;
							<input id="todoplus" name="todoplus" type="text" class="form-control cc-name valid col-sm-11">
							<button type="submit" id="createBtn" class="btn btn-secondary">
								<i class="fa fa-plus"></i>
							</button>
						</div>
					</form>
					
					<c:choose>
						<c:when test="${todosize == 0 }">
						</c:when>
						<c:otherwise>
							<c:forEach items="${todolist }" var="todo" varStatus="status">
								<form id="frm" action="${cp}/toDoList" >
									<div class="card-body">
						           		<input type="hidden" name="sqnum" class="sqnum" value="${todo.tdl_sq }"/>
						           		
										<c:choose>
											<c:when test="${todo.tdl_comp == 'y' }">
												<div class="checkbox">			
													<input type="checkbox" data-sq="${todo.tdl_sq }" name="checkboxT" class="form-check-input" checked="checked">
												</div>
											</c:when>
											<c:otherwise>
												<div class="checkbox">			
													<input type="checkbox" data-sq="${todo.tdl_sq }" name="checkboxT" class="form-check-input">
												</div>
											</c:otherwise>
										</c:choose>
										
										<div class="input-group">
											<input id="modihid" type="text" name="modihid" class="form-control cc-name valid" value="${todo.tdl_con }"/>
											<input data-sqnum="${todo.tdl_sq }" type="button" name="modify" class="btn btn-secondary btn-sm" value="편집"/>          
											<input type="button" name="delete" class="btn btn-danger btn-sm" value="삭제" onclick="sqNumd(${todo.tdl_sq  })"/>
							           	</div>
									</div>
									
									<!-- 추가 -->
									<input type="hidden" name="tdl_sq" id="tdl_sq" />
									<input type="hidden" name="tdl_con" id="tdl_con" />
								</form>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div style="position: relative; left: 700px; top: -50px; z-index: 2;">
				<img src="images/blue.png" style="width: 150px;">
			</div>
		</div>
	</div>
</div>

	
<script type="text/javascript">

	var sq_Num = "";
	
	// 삭제
	function sqNumd(n){
	   sq_Num = n;
	}
	
	$("input[name=modify]").on("click",function(){
		// 추가
		var sqnum = $(this).data("sqnum");
//		var id = $(this).parent().find("[name=sqnum]").val();
		var con = $(this).parent().find("[name=modihid]").val();
		console.log($("input[name=modihid]").val());
		
/* 		$(".sqnum").val(sqnum);
		$(".connum").val(con); */

		// 추가
		$("#tdl_sq").val(sqnum);
		$("#tdl_con").val(con);
		
		$("#frm").attr("action","${cp}/modiTodo");
		$("#frm").submit();
	});
	
	$("input[name=delete]").on("click",function(){
		$(".sqnum").val(sq_Num);		
		$("#frm").attr("action","${cp}/deleTodo");
		$("#frm").submit();
	});
	
	// 체크박스
	$("input[name=checkboxT]").on("click",function(){
       	var sq = $(this).data("sq");
       	var check = $(this).is(":checked");

        if(check == true){
        	$(".sqnum").val(sq);
            $("#frm").attr("action","${cp}/completeChk");
            $("#frm").submit();
        }else{
        	$(".sqnum").val(sq);
            $("#frm").attr("action","${cp}/uncompleteChk");
            $("#frm").submit();
        }
    });
	
</script>

    
    
    