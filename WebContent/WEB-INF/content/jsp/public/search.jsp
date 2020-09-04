<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page isELIgnored="false"%>

<!-- 分类的基础上，增加搜索功能 -->

<html>
	<head>
		<title>搜索</title>
<script type="text/javascript" src="./js/ZTFJS.js"></script>
<link rel="stylesheet" href="./js/bootstrap-4.4.1-dist/css/bootstrap.min.css" type="text/css"></link>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-4.4.1-dist/js/bootstrap.bundle.min.js"></script>

	</head>
	<body>
	<%@include file="/WEB-INF/content/jsp/public/header.jsp" %>	
	
	

		
		

		
		

		
	<div class="container-fluid">
		<div class="row">
			<!-- 视频网格 -->
			<div class="col-sm-1"></div>
			<div class="col-sm-10">

				
				<!-- 类型导航 -->
				<div>
					<ul class="nav">
					<s:iterator value="categories" var="c">
						<li class="nav-item"><a class="nav-link" href="search?content=${content }&id=${c.id}&page=${page}">${c.name}</a></li>
					</s:iterator>
					</ul>
				</div>
				
					<div class="row" style="margin:0px">
					<s:iterator value="videos" var="v" status="status">
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
			
			<!-- 侧边导航+回到顶部 -->
			<div class="col-sm-1"></div>
		</div>
	</div>




	<!-- 翻页组件 -->
	<nav aria-label="Page navigation example">
	  <ul class="pagination justify-content-center">
		<s:if test='page >5'>
	
			<li class="page-item"><a class="page-link" href="search?content=${content }&id=${id}&page=0">首页</a></li>
		</s:if>
		<s:if test='prevpage>=0'>
			<li class="page-item"><a class="page-link" href="search?content=${content }&id=${id}&page=${prevpage}">上一页</a></li>
		</s:if>
		<s:iterator value="pages" var="p">
			<a href=""></a>
			<li class="page-item"><a class="page-link" href="search?content=${content }&id=${id}&page=${p}">${p+1}</a></li>
		</s:iterator>
		<s:if test='nextpage >0'>
			
			<li class="page-item"><a class="page-link" href="search?content=${content }&id=${id}&page=${nextpage}">下一页</a></li>
		</s:if>
		<s:if test='maxPage >10'>
			<li class="page-item"><a class="page-link" href="search?content=${content }&id=${id}&page=${maxPage }">总共${maxPage }页</a></li>
		</s:if> 
	  </ul>
	</nav>




	</body>
	<script type="text/javascript" src="./js/QuickLook.js"></script>
</html>