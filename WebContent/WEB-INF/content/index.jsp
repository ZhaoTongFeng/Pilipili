<%@page import="bean.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@page isELIgnored="false"%>

<html>
<head>
<title>首页</title>
<script type="text/javascript" src="./js/ZTFJS.js"></script>


<link rel="stylesheet" href="./js/bootstrap-4.4.1-dist/css/bootstrap.min.css" type="text/css"></link>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-4.4.1-dist/js/bootstrap.bundle.min.js"></script>

</head>


<body class="data-spy="scroll" data-target="#navbar-example" style=" position: relative;">
	<%@include file="/WEB-INF/content/jsp/public/header.jsp"%>
	<!-- 顶部类型：取20个，两排，一排10个 -->
	<div class="container-fluid">
	<div style="height:50px"></div>
		<div class="row">
			<!-- 右边空列 -->
			<div class="col-sm-1"></div>
			
			
			<!-- 中间列：视频网格 -->
			<div class="col-sm-10">
			
				<!-- 类型 -->
				<div class="row">
					<div class="col-sm-2"></div>
					<div class="col-sm-8">
						<div class="row">
						
							<s:iterator value="categories" var="c">
							<div class="col-sm-1" style="padding-bottom:16px;">
								<a style="padding:4px 8px" class="badge badge-light" href="category?id=${c.id }">${c.name}</a>
							</div>
							</s:iterator>
							
						</div>
					</div>
	
					<div class="col-sm-2"></div>
				</div>
				


				
				<!-- 推荐 -->
				<nav aria-label="breadcrumb">
					<ol class="breadcrumb shadow-sm p-3 mb-5 bg-white rounded">
						<li class="breadcrumb-item active" aria-current="page" >最新</li>
					</ol>
				</nav>
				<div class="row " style="margin:0px">
				<s:iterator value="videos" var="v" status="status">
					<div class="col-sm-2 ">
						<div class="card "  style="width:100%;margin-bottom:16px;border:none" >
						
							<div class="position-relative" style="width:100%;height:100%;">
								<a href="video?id=${v.id}">
									<img class="card-img-top" src="${v.img }" video_id="${v.id }" video_src="${v.src }" style="height:120px;"/>
								</a>
						  		<div class="position-absolute" style="bottom:0px;width:100%;background-color:rgba(0,0,0,0)">
							  		<small style="color:white;padding:4px 0px 4px 4px">${v.num_view }</small>
							  		<small style="color:white;">播放</small>
							  		<small style="color:white;padding:4px 0px 4px 4px">${v.num_good }</small>
							  		<small style="color:white;">赞</small>						  		
					  			</div>
							</div>
							
						  	<ul class="list-group list-group-flush" >
						  		<li class="list-group-item" style="padding:0px;border:none">
						  		<s:if test="videos[#status.index].isVip == true">[VIP]</s:if>
						  		${v.title }
						  		</li>
						  	</ul>
						</div>
					</div>
				</s:iterator>
				</div>


				<!-- 分类 -->

				<s:iterator value="videos_" id="lv" status="st">
				
					<s:subset source="categories" var="cs" count="1" start="#st.index">
					<s:iterator value="#attr.cs" var="c">
						<nav aria-label="breadcrumb" id="c-${c.id }" class="">
							<ol class="breadcrumb shadow-sm p-3 mb-5 bg-white rounded">
								<li class="breadcrumb-item active " aria-current="page" ><b>${c.name }</b></li>
							</ol>
						</nav>
					</s:iterator>
					</s:subset>
					
					
					<!-- 内容 -->
					<div class="row" style="margin:0px">
					<s:iterator value="lv" var="v" status="status">
						<div class="col-sm-2">
							<div class="card" style="width:100%;margin-bottom:16px;border:none" >
								<div class="position-relative" style="width:100%;height:100%;">
									<a href="video?id=${v.id}">
										<img class="card-img-top" src="${v.img }" video_id="${v.id }" video_src="${v.src }" style="height:120px;"/>
									</a>
							  		<div class="position-absolute" style="bottom:0px;width:100%;background-color:rgba(0,0,0,0)">
								  		<small style="color:white;padding:4px 0px 4px 4px">${v.num_view }</small>
								  		<small style="color:white;">播放</small>
								  		<small style="color:white;padding:4px 0px 4px 4px">${v.num_good }</small>
								  		<small style="color:white;">赞</small>						  		
						  			</div>
								</div>
							  	
							  	<ul class="list-group list-group-flush" >
							  		<li class="list-group-item" style="padding:0px;border:none">
							  		<s:if test="videos[#status.index].isVip == true">[VIP]</s:if>
							  		${v.title }
							  		</li>

							    	<li class="list-group-item" style="padding:0px;border:none">
							    		<img class="rounded-circle" src="${v.user.img }" style="width:24px;height:24px;margin:4px" >
							   			<small style="color:#999">${v.user.name }</small>
							   		</li>
							  	</ul>
							  	
							</div>
						</div>
					</s:iterator>
					</div>
					
					
					<div style="height:50px"></div>
				</s:iterator>

			</div>
			

			<!-- 侧边导航+回到顶部 -->
			<div class="col-sm-1" style="left: 0px;">
			
				<nav id="navbar-example" class="list-group" style="position:fixed;top:100px">
				<s:iterator value="categories" var="c">
					<a style="padding:4px 8px;font-size:12px;" class="list-group-item list-group-item-action" href="#c-${c.id }">${c.name}</a>
				</s:iterator>
				</nav>
				
			</div>
			
			
		</div>
	</div>
	<%@include file="/WEB-INF/content/jsp/public/footer.jsp"%>
	
	

				






</body>
<script type="text/javascript" src="./js/QuickLook.js"></script>
</html>