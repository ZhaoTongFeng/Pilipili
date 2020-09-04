<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page isELIgnored="false"%>

<html>
	<head>
		<title>积分中心</title>
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
			<h3>硬币充值</h3>
			<div>
				<span >硬币余额：</span>
				<span id="currentBalance" style="margin-right:16px">${user.balance }</span>
			</div>

			<div class="row">	
			<s:iterator value="balances" var="b">
			<div class="col-sm-4" style="text-center">				
					<button style="width:100%;margin:8px 0px;" class="btn btn-primary" onclick="SetTarget(${b})">
						<p style="padding:8px;margin:0px">${b}</p>
					</button>
				</div>
			</s:iterator>
			
			<s:iterator value="vipPrices" id="map"> 
				<s:iterator value="map" id="column"> 
				<div class="col-sm-4" style="text-center">
					<button style="width:100%;margin:8px 0px;" class="btn btn-primary" onclick="SetMonth(${key})">
						<p style="padding:8px;margin:0px">${key }个月/${value }积分</p>
					</button>
				</div>
				</s:iterator>   
			</s:iterator>
			</div>
			
			<div style="text-center">

			
			</div>
			
			<div style="margin-top:32px">
					<button style="display:inline" style="" class="btn btn-light" id="submit">充值：<span id="balance">0</span></button>
					<span style="" id="errSpan"></span>

			</div>


		</div>
		<div class="col-sm-4"></div>
		
	</div>
</div>











<script type="text/javascript">
currentBalance = document.getElementById("currentBalance")
errSpan = document.getElementById("errSpan");

balanceSpan = document.getElementById("balance")

function SetTarget(num){
	balanceSpan.innerHTML=num;

}
$("#submit").click(function(){
	
	balance = balanceSpan.innerHTML;
	console.log(balance);
	if(balance==0){
		errSpan.innerHTML = "请选择充值金额";
		return;
	}
	form = new FormData();
	form.append("balance",balance);
	
	$.ajax({
		url:'recharge',
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
            	errSpan.innerHTML = "";
            	currentBalance.innerHTML = data.balance;
            	//payimg.hidden=false;
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