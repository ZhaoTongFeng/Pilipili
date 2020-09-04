<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page isELIgnored="false"%>

<html>
	<head>
		<title>个人中心</title>
<script type="text/javascript" src="./js/ZTFJS.js"></script>
<link rel="stylesheet" href="./js/bootstrap-4.4.1-dist/css/bootstrap.min.css" type="text/css"></link>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-4.4.1-dist/js/bootstrap.bundle.min.js"></script>

	</head>
	<body>
	<%@include file="/WEB-INF/content/jsp/public/header.jsp" %>	
	
	


	<div class="container-fluid">
	
		<div class="row">
			<div class="col-sm-3"></div>
			<div class="col-sm-6" style="padding:0px;">
			
			
				<div class="row shadow-sm  bg-white rounded" style="padding:0px">
					<!-- 中间左边 -->
					<div class="col-sm-2" style="padding:0px;">
						<ul class="list-group  " >
							  <a href="home" class="list-group-item list-group-item-action active">个人中心</a>
							  <a href="vip" class="list-group-item list-group-item-action">会员充值</a>
							  <a href="balance" class="list-group-item list-group-item-action">硬币充值</a>
							  <a href="bookmark" class="list-group-item list-group-item-action">收藏夹</a>
							  <a href="uploadVideo" class="list-group-item list-group-item-action">视频上传</a>
							  <li class="list-group-item active">管理</li>
							  
							  <a href="videoList" class="list-group-item list-group-item-action">视频管理</a>
							  <a href="commentList" class="list-group-item list-group-item-action">评论管理</a>
							
								<s:if test="user.level == 2">
								<a href='userList' class='list-group-item list-group-item-action'>用户管理</a>
								<a href='auditList' class='list-group-item list-group-item-action'>审核管理</a>
								</s:if>

							  
							  
							  
						</ul>
					</div>
					<!-- 中间右边START -->
					<div class="col-sm-10" style="margin:16px 0px;">
					
					
						<div class="media" style="margin-bottom:64px">
						<s:if test="user.img!=null"><img src="${user.img }" style="width:50px;height:50px;margin-right:16px;" class="rounded-circle"/></s:if>
						  
						  <div class="media-body">
						  
						    <h5 class="mt-0" >${user.name }</h5>
							<!-- 经验条 -->
							
							<div class="progress">LV:<span id="grade">${user.grade }</span>
							  <div id="progress" class="progress-bar" role="progressbar" style="width: 0%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">0%</div>
								<span id="exp">${user.exp }</span>
								<span>/1000</span>
							</div>
							
							<div style="margin-top:16px;">
								<span>硬币：</span><span id="balance">${user.balance }</span>
								<button id="checkIn" class="btn btn-light">签到领硬币</button>
								<span id="checkInErrSpan"></span>
						  	</div>
						  	
						  
						  </div>
						</div>
						<hr >
						
	
						
						<div style="margin-bottom:64px">	
							<div style="margin-bottom:16px">
								<span style="margin-bottom:16px"><b>会员状态</b></span><br>
							</div>
							
								
							当前状态：
							<s:if test="user.level ==2"><span>管理员</span></s:if>
							<s:elseif test="user.level == 1"><span>VIP</span><a href='vip'>续费</a><br><span>到期时间：${user.vipTime }</span></s:elseif>
							<s:else><span>普通用户</span><a href='vip'>开通</a></s:else>


	
						</div>
						<hr >
						
						<div style="margin-bottom:64px">	
							<div style="margin-bottom:16px">
								<span><b>修改头像</b></span>	
							</div>
							

							<div style="margin-bottom:16px">
								<s:if test="user.img!=null"><img id="userImage" src="${user.img }" style="height:128px;width:128px;"/></s:if>
								
							</div>
							
							<div >
								<button id="changeImg" class="btn btn-light">提交</button>
								<input id="uploadfile" type="file" value="选择图片" class="btn btn-light">
							</div>

							<div>
								<span id="errSpan1"></span>
							</div>
						</div>
						<hr >
						
						<div style="margin-bottom:64px">	
							
							
							
							
							<div style="margin-bottom:16px;">
								<b>修改基本信息</b>
							</div>
							
							<div>
								<div style="margin-bottom:16px;">
									<input id="name" value="${user.name }" type="text" placeholder="昵称" class="form-control form-control-lg"/>
								</div>
								<div style="margin-bottom:16px;">
									<input id="passwd" value="${user.passwd }" type="password" placeholder="密码" class="form-control form-control-lg"/>
								</div>
								<div>
									<span id="errSpan"></span>
								</div>
								<div>
									<button id="submit" class="btn btn-light">提交</button>
								</div>
							</div>
						</div>

						
						
					</div>
					<!-- 中间右边END -->
				</div>
			
			</div>
			
			<div class="col-sm-3"></div>
		</div>
	</div>












<script type="text/javascript">

var count = 0;
var end = document.getElementById("exp").innerHTML;
function UpdateProgress(){
	timer = setInterval(function(){
		count++;		
		progress = document.getElementById("progress");
		rate = count;

		progress.style.width=rate+"%";
		progress.innerHTML = rate+"%";
		if(count>=end/10){
			clearInterval(timer)
		}
	},16)
}
window.onload = function() {


	UpdateProgress()
};


//签到
$("#checkIn").click(function(){	
	checkInErrSpan = document.getElementById("checkInErrSpan");
	$.ajax({
		url:'checkIn',
		type:'POST',
		async:false,
		cache:false,
		dataType:"json",
		contentType:false,
		processData:false,
		success:function(data){
            console.log(data)
            if(data.code==0){
            	checkInErrSpan.innerHTML = "签到成功";
            	exp.innerHTML = data.exp;
            	grade.innerHTML = data.grade
            	balance.innerHTML = data.balance
            	
            	
            	end = data.exp;
            	UpdateProgress()
            }else{
            	checkInErrSpan.innerHTML = data.errmsg;
            }
		},
		error:function(returndata){
			console.log(returndata)
		}
	});
});

</script>
<script>

//换头像
	$("#changeImg").click(function(){
		image = document.getElementById("userImage");	
		errSpan = document.getElementById("errSpan1");
		file = document.getElementById("uploadfile").files[0];
		console.log(file);
		if(file=="undefined"){
			errSpan.innerHTML="请选择图片";
			return;
		}
		form = new FormData();
		form.append("doc",file);
		
		$.ajax({
			url:'uploadImg',
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
	            	image.src=data.src;
	            }else{
	            	errSpan.innerHTML = data.errmsg;
	            }
			},
			error:function(returndata){
				console.log(returndata)
			}
		});
	});
	
//该资料
	$("#submit").click(function(){	
		errSpan = document.getElementById("errSpan");
		form = new FormData();
		form.append("name",$("#name").val())
		form.append("passwd",$("#passwd").val())
		
		$.ajax({
			url:'updateInfo',
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