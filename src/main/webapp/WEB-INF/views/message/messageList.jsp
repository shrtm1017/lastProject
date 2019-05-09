<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs">
   <div class="col-sm-4">
      <div class="page-header float-left" id="page-title-style">
           <div class="page-title">
               <h1 style="padding-bottom: 0px;"><strong>쪽지</strong></h1>
         </div>
      </div>
   </div>
</div>

<div>
   <div class="col-lg-12 col-sm-4">
      <br>
      <div class="form-group">

         <div class="col col-md-7">
            <div class="input-group">
               <c:set var="value" value="0">
               </c:set>
               <select class="form-control col-sm-3" id="selCondition">
                  <option value="0">받은쪽지함</option>
                  <option value="1">내게쓴쪽지함</option>
                  <option value="2">보낸쪽지함</option>
                  <option value="3">쪽지보관함</option>
               </select>
               
               <div class="col col-md-1" style="text-align: right;"></div>
               <button type="button" id="delBtn"
                  class="btn btn-secondary btn-sm col-sm-2">쪽지 삭제</button>
               &nbsp;
               <button type="button" id="boguanBtn"
                  class="btn btn-secondary btn-sm col-sm-2">쪽지 보관</button>
               &nbsp;
               <button type="button" id="insBtn"
                  class="btn btn-secondary btn-sm col-sm-2">쪽지 쓰기</button>
               </div>
         </div>


      </div>
      <br>
      <br>
      <table class="table text-center">
         <thead style="background-color: #002266; color: white;" id="headlist">
            <tr>
               <th scope="col" style="width: 50px; text-align: center;"></th>
               <th scope="col" style="width: 100px; text-align: center;">No.</th>
               <th scope="col"></th>
               <th scope="col" style="width: 200px; text-align: center;">보낸이</th>
               <th scope="col" style="width: 200px; text-align: center;">보낸날짜</th>
            </tr>

         </thead>
         <tbody id="bodylist">

            <c:forEach items="${recelist}" var="rec" varStatus="status">
               <tr>
                  <td style='text-align: center;'>
                     <div class='checkbox'>
                        <input type='checkbox' name='checkboxT' class='form-check-input'
                           data-sq='${rec.msg_rec_sq }'>
                     </div>
                  </td>
                  <td>${status.count}</td>
                  <td class='msgTd' data-ressq='${rec}'
                     data-ids='${rec.msg_emp_send}' 
                     data-con='${rec.msg_con}'
                     data-rec='${rec.msg_emp_rec}'
                     style="text-align: left;">
                     <c:choose>
                        <c:when test="${fn:length(rec.msg_con) > 90}">
                           <c:out value='${fn:substring(rec.msg_con,0,89)}' />... 
                        </c:when>
                        <c:otherwise>
                           <c:out value='${rec.msg_con }' />
                        </c:otherwise>
                     </c:choose>
                  </td>
                  <td>${rec.msg_emp_send }</td>
                  <td>
                     <fmt:formatDate value='${rec.msg_dt}' pattern='yy/MM/dd hh:mm' />
                  </td>
               </tr>
          	  </c:forEach>

        </tbody>
      	</table>
      <br>
   </div>
</div>

<style>

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
</style>

<div class="modal modal-center fade" id="myModal" role="dialog"
   tabindex="-1">
   <div class="modal-dialog modal-center">
      <div class="modal-content" style="text-align: center;">
         <div class="modal-body">
            <form id="modalInfo" action="${cp }/insertSchedule">
               <table style="margin: 10px">
                  <tr>
                     <td style="text-align: left;">보낸 사원 : <strong><label style="color: #008299;" id="receiE"></label></strong></td>
                  </tr>
                  <tr></tr>
                  <tr>
                     <td>
                        <textarea  id="receCon" cols="50" rows="10" style="background-color: transparent; padding: 10px;" readonly="readonly"></textarea>
                     </td>
                  </tr>
               </table>
         
               <div class="form-group">
                  <input type="button" id="recallbtn" class="btn btn-info btn-sm col-sm-3" value="답장"> 
                  <input type="button" id="closebtn" class="btn btn-secondary btn-sm col-sm-3" value="종료">
               </div>
            </form>
         </div>
      </div>
   </div>
</div>

<form id="insFrm" action="${cp }/messageInsert"></form>

<form id="insreFrm" action="${cp }/messageInsertrecall">
   <input type="hidden" id="recallins" name="recallins" />
</form>

<form id="searchFrm" action="${cp }/researchSearch" method="get"
   class="form-horizontal" role="form">
   <input type="hidden" id="condition" name="condition" /> <input
      type="hidden" id="keyword" name="keyword" />
</form>


<script>
   $(document).ready(function() {

      $("#closebtn").on("click",function(){
         $("#myModal").hide();
      });
      
      $("#recallbtn").on("click",function(){
         $("#insreFrm").submit();
      });
      
      $(".msgTd").on("click", function() {
         var res_sq1 = $(this).data("ids");
         var res_sq2 = $(this).data("con");
         var res_sq3 = $(this).data("rec");
         console.log(res_sq1);
         console.log(res_sq2);
         console.log(res_sq3);
         
         $("#receiE").text(res_sq1);
         $("#receiE").text();
         $("#receCon").val(res_sq2);
         
         $("#myModal").show();
      });
      
      // 동적으로 만든 녀석들을 이벤트 발생 시키는 방법
      $(document).on("click", ".msgTd" ,function(){ 
         var res_sq1 = $(this).data("ids");
         var res_sq2 = $(this).data("con");
         var res_sq3 = $(this).data("rec");
         console.log(res_sq1);
         console.log(res_sq2);
         console.log(res_sq3);
         
         $("#recallins").val(res_sq1);
         $("#receiE").text(res_sq1);
         $("#receiE").text();
         $("#receCon").val(res_sq2);
         
         $("#myModal").show();
      });
      
      $("#insBtn").on("click", function() {
         $("#insFrm").submit();
      });
      
      var selectR = '';
      
      $(document).on("click", "input[name='checkboxT']" ,function(){ 
         var output = '';
             $('input[name="checkboxT"]:checked').each(function(index,item){
             if(index!=0){
                 output += ', ';
             }
            output += $(this).data("sq");
             });
         
            selectR = output;
            console.log(output);
      });
      
       
      $("#delBtn").on("click",function(){
    	  
         var param = {
               params : selectR
            };
         
         var delmess = selectR;
         
         console.log(selectR);
         
         $.ajax({
            url : "/messageDelete",
            type : "POST",
            dataType : "json",
            data : JSON.stringify(param), // stringify는 post로 보내야만 하고 @RequestBody Map으로 한다
            contentType : "application/json; charset=UTF-8",
            success : function(data) {
            	for(let i=0;i<1000000;i++){
            		console.log(i);
            	}
            }
         });
         window.location.reload();
      });
      
      $("#boguanBtn").on("click",function(){
    	  
         var param = {
               params : selectR
            };
         
         var savemess = selectR;
         
         $.ajax({
            url : "/messageSave",
            type : "POST",
            dataType : "json",
            data : JSON.stringify(param), // stringify는 post로 보내야만 하고 @RequestBody Map으로 한다
            contentType : "application/json; charset=UTF-8",
            success : function(data) {
            	for(let i=0;i<1000000;i++){
            		console.log(i);
            	}
            }
         });
         window.location.reload();
      });
      
      $("#selCondition").on("change", function() {
         var check = $("#selCondition").val();
         
         console.log(check);
         
         $.ajax({
            url : "/messageLists",
            type : "POST",
            dataType : "json",
            data : JSON.stringify(check), // stringify는 post로 보내야만 하고 @RequestBody Map으로 한다
            contentType : "application/json; charset=UTF-8",
            success : function(data) {
                  
               var text = "";
               var headtext ="";
               
               if(check==0){
                  
                  <c:forEach items="${recelist}" var="rec" varStatus = "status">  
                     text += " <tr> ";
                     text += "    <td style='text-align: center;'> ";
                     text += "       <div class='checkbox'> ";      
                     text += "         <input type='checkbox' name='checkboxT' class='form-check-input' data-sq='${rec.msg_rec_sq }'> ";
                     text += "      </div> ";
                     text += "   </td> ";
                     text += "   <td style='text-align: center;'>${status.count}</td> ";
                     text += "    <td class='msgTd' data-ressq='${rec}' data-ids='${rec.msg_emp_send}' data-con='${rec.msg_con}' data-rec='${rec.msg_emp_rec}' style='text-align: left;'> ";
                     
                     if(${fn:length(rec.msg_con) > 90}){
                        text += "              <c:out value='${fn:substring(rec.msg_con,0,89)}'/>... ";
                     }else{
                        text += "               <c:out value='${rec.msg_con }'/> ";
                     }
                     
                     text += "    </td> ";
                     text += "    <td style='text-align: center;'>${rec.msg_emp_send }</td> ";
                     text += "    <td style='text-align: center;'><fmt:formatDate value = '${rec.msg_dt}' pattern = 'yy/MM/dd hh:mm' /></td> ";
                     text += " </tr> ";
                  </c:forEach>         
               }else if(check==1){
                  
                  <c:forEach items="${myrecelist}" var="rec" varStatus = "status">  
                     text += " <tr> ";
                     text += "    <td style='text-align: center;'> ";
                     text += "       <div class='checkbox'> ";      
                     text += "         <input type='checkbox' name='checkboxT' class='form-check-input' data-sq='${rec.msg_rec_sq }'> ";
                     text += "      </div> ";
                     text += "   </td> ";
                     text += "   <td style='text-align: center;'>${status.count}</td> ";
                     text += "    <td class='msgTd' data-ressq='${rec}' data-ids='${rec.msg_emp_send}' data-con='${rec.msg_con}' data-rec='${rec.msg_emp_rec}' style='text-align: left;'> ";
                     
                     if(${fn:length(rec.msg_con) > 90}){
                        text += "              <c:out value='${fn:substring(rec.msg_con,0,89)}'/>... ";
                     }else{
                        text += "            <c:out value='${rec.msg_con }'/> ";
                     }
                     
                     text += "    </td> ";
                     text += "    <td style='text-align: center;'>${rec.msg_emp_send }</td> ";
                     text += "    <td style='text-align: center;'><fmt:formatDate value = '${rec.msg_dt}' pattern = 'yy/MM/dd hh:mm' /></td> ";
                     text += " </tr> ";
                  </c:forEach>   
               }else if(check==2){
                  <c:forEach items="${sendMap}" var="rec" varStatus = "status">  
                     text += " <tr> ";
                     text += "    <td style='text-align: center;'> ";
                     text += "       <div class='checkbox'> ";      
                     text += "         <input type='checkbox' name='checkboxT' class='form-check-input' data-sq='${rec.MSG_REC_SQ }'> ";
                     text += "      </div> ";
                     text += "   </td> ";
                     text += "   <td style='text-align: center;'>${status.count}</td> ";
                     text += "    <td class='msgTd' data-ressq='${rec}' data-ids='${rec.MSG_EMP_SEND}' data-con='${rec.MSG_CON}' data-rec='${rec.MSG_EMP_REC}' style='text-align: left;'> ";
                  
                     if(${fn:length(rec.MSG_CON) > 90}){
                        text += "              <c:out value='${fn:substring(rec.MSG_CON,0,89)}'/>... ";
                     }else{
                        text += "            <c:out value='${rec.MSG_CON }'/> ";
                     }
                  
                     text += "    </td> ";
                     text += "    <td style='text-align: center;'>${rec.MN_EMP_REC_SQ }</td> ";
                     text += "    <td style='text-align: center;'><fmt:formatDate value = '${rec.MSG_SEND_DT}' pattern = 'yy/MM/dd hh:mm' /></td> ";
                     text += " </tr> ";
                  </c:forEach>
                  
               }else if(check==3){
                  
                  <c:forEach items="${receimplist}" var="rec" varStatus = "status">  
                     text += " <tr> ";
                     text += "    <td style='text-align: center;'> ";
                     text += "       <div class='checkbox'> ";      
                     text += "         <input type='checkbox' name='checkboxT' class='form-check-input' data-sq='${rec.msg_rec_sq }'> ";
                     text += "      </div> ";
                     text += "   </td> ";
                     text += "   <td style='text-align: center;'>${status.count}</td> ";
                     text += "    <td class='msgTd' data-ressq='${rec}' data-ids='${rec.msg_emp_send}' data-con='${rec.msg_con}' data-rec='${rec.msg_emp_rec}' style='text-align: left;'> ";
                     
                     if(${fn:length(rec.msg_con) > 90}){
                        text += "              <c:out value='${fn:substring(rec.msg_con,0,89)}'/>... ";
                     }else{
                        text += "            <c:out value='${rec.msg_con }'/> ";
                     }
                     
                     text += "    </td> ";
                     text += "    <td style='text-align: center;'>${rec.msg_emp_send }</td> ";
                     text += "    <td style='text-align: center;'><fmt:formatDate value = '${rec.msg_dt}' pattern = 'yy/MM/dd hh:mm' /></td> ";
                     text += " </tr> ";
                  </c:forEach>   
            }
               
               $("#bodylist").html(text);
               
            }
         });
         
      });
   });
</script>