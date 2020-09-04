<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page isELIgnored="false"%>


<html>
<head>
<title>上传视频</title>
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
			<h3 style="text-align:center">视频上传</h3>
			  <div class="form-group">
			    <label for="staticEmail" class="col-sm-2 col-form-label">标题</label>
			    <div class="col-sm-12">
			      <input type="text" class="form-control" id="title" value="未命名">
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="inputPassword" class="col-sm-2 col-form-label">简介</label>
			    <div class="col-sm-12">
			      <input type="text" class="form-control" id="introduction" value="无">
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
							
			  <div class="form-group">
			    <label for="staticEmail" class="col-sm-2 col-form-label">视频</label>
			    <div class="col-sm-12">
			    	<input type="file" id="doc1" />
			
			    </div>
			  </div>
			  
			  
			  <div class="form-group">
			    <label for="inputPassword" class="col-sm-2 col-form-label">封面</label>
			    <div class="col-sm-12">
			    	<input type="file" id="doc2"/>
			
			    </div>
			  </div>
			
			  <div class="form-group">
			    <label for="inputPassword" class="col-sm-2 col-form-label"></label>
			    <div class="col-sm-12">
					
					<button type="submit" class="btn btn-primary" id="submit">提交</button>
					<small id="errSpan" class="form-text text-muted"></small>
					
			    </div>
			  </div>
			</div>
			
			<div class="col-sm-4"></div>
		</div>
	</div>
	
	








<script type="text/javascript">
$(document).ready(function(){
	errSpan = document.getElementById("errSpan")
	$("#submit").click(function(){	
		select = document.getElementById("category");
		ca_id = select[select.selectedIndex].value;
		
		file1 = document.getElementById("doc1").files[0];
		file2 = document.getElementById("doc2").files[0];

		if(typeof file1=="undefined"||typeof file2=="undefined"){
			errSpan.innerHTML = "视频和封面不能为空";
			return;
		}
		form = new FormData();
		form.append("ca_id",ca_id)
		form.append("video.title",$("#title").val());
		form.append("video.introduction",$("#introduction").val());
		form.append("doc",file1);
		form.append("doc",file2);
		$.ajax({
			url:'video/upload',
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
	            	id = data.id;
	            	window.location.href = "updateVideo?id="+id;
	            }else{
	            	errSpan.innerHTML = data.errmsg;
	            }
	            
			},
			error:function(returndata){
				console.log(returndata)
			}
		});
	});
});


//submit.onclick = function(){
//SendPost("POST","uploadVapi",form,function(res){},true);
//}


</script>

</body>
</html>