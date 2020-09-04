<%@page import="bean.Video"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page isELIgnored="false"%>


<html>
<head>
<title>视频编辑</title>

<script type="text/javascript" src="./js/ZTFJS.js"></script>
<link rel="stylesheet" href="./js/bootstrap-4.4.1-dist/css/bootstrap.min.css" type="text/css"></link>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-4.4.1-dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
<%@include file="/WEB-INF/content/jsp/public/header.jsp" %>	
  

  
	<div class="container-fluid">
	
  
  	<span id="video_id" hidden>${video.id }</span>
  	<span id="category_id" hidden>${video.category.id }</span>
  	
<div class="row">
	<div class="col-sm-4"></div>
	
	<div class="col-sm-4 shadow-sm p-3 mb-5 bg-white rounded">

		<h3 style="text-align:center">视频编辑</h3>
		
		
	  <div class="form-group">
	    <label for="staticEmail" class="col-sm-2 col-form-label">视频</label>
	    <div class="col-sm-12">
	    	<input type="file" id="doc1" />
			<video id="videoPlayer" autoplay="autoplay" style="margin-top:16px;width:100%;max-height:300px;background-color: black">
				<source src="${video.src }" type="video/ogg" />
				<source src="${video.src }" type="video/mp4" />
				<source src="${video.src }" type="video/webm" />
			</video>
	    </div>
	  </div>

		

	


	  
	  
	  <div class="form-group">
	    <label for="inputPassword" class="col-sm-2 col-form-label">封面</label>
	    <div class="col-sm-12">
	    	<input type="file" id="doc2"/>
	    	<img id="video_img" src="${video.img }" style="width:100%;max-height:300px;"/>
	    </div>
	  </div>
	
	  <div class="form-group">
	    <label for="staticEmail" class="col-sm-2 col-form-label">标题</label>
	    <div class="col-sm-12">
	      <input type="text" class="form-control" id="title" value="${video.title }">

	    </div>
	  </div>
	  
	  <div class="form-group">
	    <label for="inputPassword" class="col-sm-2 col-form-label">简介</label>
	    <div class="col-sm-12">
	      <input type="text" class="form-control" id="introduction" value="${video.introduction }">
	    </div>
	  </div>
	
	
	  <div class="form-group">
	    <label for="inputPassword" class="col-sm-2 col-form-label">类型</label>
	    <div class="col-sm-12">
		    <select class="form-control" id="category">
				<s:iterator value="categories" var="c">
					<option value="${c.id}">${c.name}</option>
				</s:iterator>
		    </select>
	    </div>
	  </div>
					
	  
	  <div class="form-group form-check">
	  
		  <div class="col-sm-12">

			<s:if test="video.isVip == true"><input id="isVipCheckBox" type="checkbox" checked class="form-check-input"/></s:if>
			<s:else><input id="isVipCheckBox" type="checkbox"  class="form-check-input"/></s:else>
		    <label class="form-check-label" for="exampleCheck1">VIP视频</label>
		  </div>
	  </div>
	  
	  
	  <div class="form-group">
	    <label for="inputPassword" class="col-sm-2 col-form-label"></label>
	    <div class="col-sm-12">
	    
				
				<button type="submit" class="btn btn-primary" id="submit">保存</button>
				<button onclick="updateLevel(this)" class="btn btn-primary">
					<s:if test="video.level==0">发布</s:if>
					<s:elseif test="video.level==1">取消审核</s:elseif>
					<s:elseif test="video.level==2">私有</s:elseif>
					<s:elseif test="video.level==3">重新审核</s:elseif>
				</button>
				<small id="errSpan" class="form-text text-muted" style="display:inline"></small>
	    </div>
	  </div>
	  
		  
	</div>
	<div class="col-sm-4"></div>
</div>

</div>


	

	
	

	

	
	
	

	
	




<script type="text/javascript">



	video_id = document.getElementById("video_id");
	video_img = document.getElementById("video_img");
	videoPlayer = document.getElementById("videoPlayer")
	
	isVipCheckBox = document.getElementById("isVipCheckBox");
	
	select = document.getElementById("category");
	category_id = document.getElementById("category_id");
	select[category_id.innerHTML-1].selected=true;
	
	errSpan = document.getElementById("errSpan")
	
	
	
	//权限变更
	function updateLevel(ele){
		id = video_id.innerHTML
		form = new FormData();
		form.append("id",id)
		$.ajax({
			url:'video/updateLevel',
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
	            	if(data.oldLevel==0||data.oldLevel==3){
	            		errSpan.innerHTML = "已提交审核，请耐心等待";
	            	}else if(data.oldLevel==1){
	            		errSpan.innerHTML = "已取消审核";
	            	}else if(data.oldLevel==2){
	            		errSpan.innerHTML = "设置成功";
	            	}
	            	ele.innerHTML = data.str;
	            }else{
	            	errSpan.innerHTML = data.errmsg;
	            }
	            
			},
			error:function(returndata){
				console.log(returndata)
			}
		});
	}
	
	//提交修改
	$("#submit").click(function(){	
		id = video_id.innerHTML
		ca_id = select[select.selectedIndex].value;
		title = $("#title").val()
		introduction = $("#introduction").val()
		isVip = isVipCheckBox.checked;
		file1 = document.getElementById("doc1").files[0];
		file2 = document.getElementById("doc2").files[0];
		
		form = new FormData();
		form.append("video.id",id)
		form.append("ca_id",ca_id)
		form.append("isVip",isVip)
		form.append("video.title",title);
		form.append("video.introduction",introduction);
		
		
		if(typeof file1!="undefined"){
			form.append("doc",file1);
			form.append("hasVideo",true);
		}
		if(typeof file2!="undefined"){
			form.append("doc",file2);
			form.append("hasImage",true);
		}
		
		$.ajax({
			url:'video/update',
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
	            	errSpan.innerHTML = "保存成功";
	            	video_img.src = data.video.img;
	            	for(var i=0;i<videoPlayer.children.length;i++){
	            		videoPlayer.children[i].src=data.src;
	            	}
	            	videoPlayer.currentTime = videoPlayer.duration;
	            	videoPlayer.play();
	            	console.log(videoPlayer)
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