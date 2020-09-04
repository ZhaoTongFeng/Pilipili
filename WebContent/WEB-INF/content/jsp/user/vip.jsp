<%@page import="org.apache.struts2.components.If"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page isELIgnored="false"%>

<html>
<head>
<title>会员中心</title>

<script type="text/javascript" src="./js/ZTFJS.js"></script>
<link rel="stylesheet" href="./js/bootstrap-4.4.1-dist/css/bootstrap.min.css" type="text/css"></link>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-4.4.1-dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
<%@include file="/WEB-INF/content/jsp/public/header.jsp" %>	


	


<div class="container-fluid">
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4 shadow-sm p-3 mb-5 bg-white rounded">
		
			<s:if test="user.isVip == true"><h3 style='text-align:center'>会员续费</h3></s:if>
			<s:else><h3 style='text-align:center'>会员开通</h3></s:else>
			<div>
				<span >硬币余额：</span><span style="margin-right:16px" id="currentBalance">${user.balance }</span><a href="balance">充值硬币</a>
			</div>
			
			<div>
				<span>当前状态：</span>
				<span id="state">
					<s:if test="user.level == 0">用户</s:if>
					<s:elseif test="user.level == 1">VIP</s:elseif>
					<s:elseif test="user.level == 2">管理员</s:elseif>
				</span>
			</div>

			<div>
				<span>到期时间：</span>
			<s:if test="user.isVip == true"><span id='vipTime'>${user.vipTime }</span></s:if>
			<s:else><span>已到期</span><span id='vipTime'></span></s:else>

			</div>

			<div class="row">	
			<s:iterator value="vipPrices" id="map"> 
				<s:iterator value="map" id="column"> 
				<div class="col-sm-4" style="text-center">
					<button style="width:100%;margin:8px 0px;" class="btn btn-primary" onclick="SetMonth(${key})">
						<p style="padding:8px;margin:0px">${value }积分/${key }月</p>
					</button>
				</div>
				</s:iterator>   
			</s:iterator>
			</div>
			

			<div>
				<button style="margin:8px 0px;" class="btn btn-light" id="submit">硬币兑换：<span id="month">0</span>个月</button>
				<span style="margin-left:16px" id="errSpan"></span>
			</div>
		</div>
		<div class="col-sm-4"></div>
		
	</div>
</div>



<!-- 模态框提示 -->
<!-- Button trigger modal -->
<button hidden id="ModalBtn" type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
  Launch demo modal
</button>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">开通成功</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div id="ModalMessageDiv" class="modal-body">
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="GoHome()">返回个人中心</button>
      </div>
    </div>
  </div>
</div>
	
	
	
	
<script>
currentBalance = document.getElementById("currentBalance")
state = document.getElementById("state");
vipTime = document.getElementById("vipTime");
monthSpan = document.getElementById("month");

function GoHome(){
	tempa = document.createElement("a");
	tempa.href="home";
	tempa.click();
}

function SetMonth(num){
	monthSpan.innerHTML=num;
	errSpan.innerHTML = "";
	
}

$("#submit").click(function(){
	month = monthSpan.innerHTML;
	console.log(month);
	form = new FormData();
	form.append("month",month);
	$.ajax({
		url:'addVipTime',
		type:'POST',
		data:form,
		async:false,
		cache:false,
		dataType:"json",
		contentType:false,
		processData:false,
		success:function(data){
            console.log(data)
            if(data.code==0){
            	errSpan.innerHTML = "充值成功";
            	vipTime.innerHTML = data.vipTime;
            	state.innerHTML = "VIP"
           		currentBalance.innerHTML = data.balance;
            	ModalMessageDiv.innerHTML = month+"个月会员已开通，到期时间："+data.vipTime;
            	ModalBtn.click();
            }else{
            	errSpan.innerHTML = data.errmsg;
            }
		},
		error:function(returndata){
			console.log(returndata)
		}
	});
});


</script>



	</body>
</html>