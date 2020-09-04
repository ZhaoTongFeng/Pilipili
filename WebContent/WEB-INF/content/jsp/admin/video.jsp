<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page isELIgnored="false"%>


<html>
<head>
<title>审核：${video.title }</title>
<script type="text/javascript" src="./js/ZTFJS.js"></script>
<link rel="stylesheet" href="./js/bootstrap-4.4.1-dist/css/bootstrap.min.css" type="text/css"></link>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-4.4.1-dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
	<%@include file="/WEB-INF/content/jsp/public/header.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-1"></div>
			<!-- 左边 -->
			<div class="col-sm-8">
				<!-- 视频抬头 ：标题，播放量，弹幕，时间，作者昵称，类型-->
				<div>
					<div>
						<h4>${video.title }</h4>
					</div>
					<div>
						<small>${video.category.name }</small><small>${video.time_create }</small>
					</div>
					<div>
						<small>${video.num_view }播放</small>
					</div>
				</div>

				<!-- 视频播放器 -->
				<input id="video_id" hidden value="${video.id }"/>
				<video width="100%" height="auto" controls="controls"
					autoplay="autoplay"
					style="max-height: 700px; background-color: black">
					<source src="${video.src }" type="video/ogg" />
					<source src="${video.src }" type="video/mp4" />
					<source src="${video.src }" type="video/webm" />
				</video>


				<!-- 弹幕发送区 -->
				<div style="width:100%;height:50px;">
					
				</div>

				<!-- 视频数据，点赞，投币，收藏，转载 -->
				<div>
					<button id="${video.id }" opt="true" onclick="Check(this)">通过</button>
					<button id="${video.id }" opt="false" onclick="Check(this)">驳回</button>
				</div>
			
			</div>

			<!-- 右边 -->
			<div class="col-sm-2">
				<!-- 用户介绍：头像，简介关注 -->
				<div>
					<div class="row">
						<div class="col-sm-4">
							<img src="${video.user.img }" class="img-fluid rounded-circle" style="max-height:48px"/>
						</div>
						<div class="col-sm-8">
							<span>${video.user.name }</span>
						</div>
					</div>

				</div>
				<h5>相关推荐</h5>
				<div class="row">
					<!-- 视频： 封面，播放量，弹幕，作者昵称，标题 -->
					<s:iterator value="videos" var="v">
						<div class="col-sm-8" style="margin-bottom: 16px">
							<a href="video?id=${v.id}">
								<img src="${v.img }" class="img-fluid" style="width:100%;height:100px"/>
							</a>
						</div>
						<div class="col-sm-4" style="padding:0px;margin:0px">
						
							<span>${v.title }</span> 
							<div style="postion:absolute;button:0px;">
								<span>${v.user.name }</span>
								<br>
								<span>${v.num_view }播放</span> 
							</div>
						</div>
					</s:iterator>
				</div>
			</div>

			<div class="col-sm-1"></div>

		</div>
	</div>
</body>
</html>



<script type="text/javascript">
function Check(ele){
	id = ele.attributes["id"].value;
	opt = ele.attributes["opt"].value;
	form = new FormData();
	form.append("id",id);
	form.append("opt",opt);
	
	
	$.ajax({
		url:'video/audit',
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
            	if(data.id==0){
            		//没有待审核的视频了
            		window.location.href = "auditList";
            	}else{
            		//跳转下个视频
            		window.location.href = "audit?id="+data.id;
            	}
            }else{
            	err2.html(data.errmsg);
            }
		},
		error:function(returndata){
			console.log(returndata)
		}
	});
}
</script>
