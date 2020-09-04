<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" isELIgnored="false"%>
 
<%@ taglib prefix="s" uri="/struts-tags" %>
 
<%@page isELIgnored="false"%>

<html>
<head>
<title>收藏夹</title>

<script type="text/javascript" src="./js/ZTFJS.js"></script>
<link rel="stylesheet" href="./js/bootstrap-4.4.1-dist/css/bootstrap.min.css" type="text/css"></link>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-4.4.1-dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
<%@include file="/WEB-INF/content/jsp/public/header.jsp" %>	
 
 	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-1"></div>
			<div class="col-sm-10">
				<!-- 收藏夹 -->
				<nav aria-label="breadcrumb">
					<ol class="breadcrumb shadow-sm p-3 mb-5 bg-white rounded">
						<li class="breadcrumb-item active" aria-current="page" >收藏夹</li>
					</ol>
				</nav>
				<!-- 内容 -->
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
						   			<a style="font-size:12px" href="deleteFromBookmark?id=${v.id }">移除</a>
						   		</li>
						  	</ul>
						  	
						</div>
					</div>
				</s:iterator>
				</div>
			</div>
			<div class="col-sm-1"></div>
		</div>
	</div>

	
	


<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
	<s:if test='page >5'>

		<li class="page-item"><a class="page-link" href="videoList&page=0">首页</a></li>
	</s:if>
	<s:if test='prevpage>=0'>
		<li class="page-item"><a class="page-link" href="videoList&page=${prevpage}">上一页</a></li>
	</s:if>
	<s:iterator value="pages" var="p">
		<a href=""></a>
		<li class="page-item"><a class="page-link" href="videoList&page=${p}">${p+1}</a></li>
	</s:iterator>
	<s:if test='nextpage >0&&nextpage<maxPage'>
		
		<li class="page-item"><a class="page-link" href="videoList&page=${nextpage}">下一页</a></li>
	</s:if>
	<s:if test='maxPage >10'>
		<li class="page-item"><a class="page-link" href="videoList&page=${maxPage }">总共${maxPage }页</a></li>
	</s:if> 
  </ul>
</nav>


</body>
</html>
