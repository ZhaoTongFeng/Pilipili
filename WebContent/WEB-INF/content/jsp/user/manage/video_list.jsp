<%@page import="bean.Video"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<%@page isELIgnored="false"%>

<html>
<head>
<title>视频管理</title>

<script type="text/javascript" src="./js/ZTFJS.js"></script>
<link rel="stylesheet"
	href="./js/bootstrap-4.4.1-dist/css/bootstrap.min.css" type="text/css"></link>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript"
	src="./js/bootstrap-4.4.1-dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
	<%@include file="/WEB-INF/content/jsp/public/header.jsp"%>


	<h3 style="text-align: center">我的视频</h3>

	
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8">
			
				<table class="table">
					<thead>
						<tr>
							<th scope="col">#</th>
							<th scope="col">时间</th>
							<th scope="col">标题</th>
							<th scope="col">类型</th>

							<th scope="col">付费</th>
							<th scope="col">状态</th>
							

							<th scope="col">播放</th>
							<th scope="col">收益</th>
							<th scope="col">收藏</th>
							<th scope="col">点赞</th>


							<th scope="col">修改</th>
							<th scope="col">删除</th>
						</tr>

					</thead>
					<tbody>
						<s:iterator value="videos" var="v" status="status">
							<tr>
								<th><s:property value="#status.index" /></th>
								<th>${v.time_create }</th>

								<th><a href="video?id=${v.id }">${v.title }</a></th>
								<th>${v.category.name }</th>

								<s:if test="isVip==true">
									<th>VIP</th>
								</s:if>
								<s:else>
									<th>免费</th>
								</s:else>
								
								<s:if test="videos[#status.index].level == 0"><th>私有</th></s:if>
								<s:elseif test="videos[#status.index].level == 1"><th>审核中</th></s:elseif>
								<s:elseif test="videos[#status.index].level == 2"><th>公开</th></s:elseif>
								<s:elseif test="videos[#status.index].level == 3"><th>审核失败</th></s:elseif>
								<s:else><th>${v.level }</th></s:else>
								
								<th>${v.num_view }</th>
								<th>${v.balance }</th>
								<th>${v.num_mark }</th>
								<th>${v.num_good }</th>

								<th><a href="updateVideo?id=${v.id }">修改</a></th>
								<th><a href="deleteVideo?id=${v.id }">删除</a></th>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				

				<nav aria-label="Page navigation example">
					<ul class="pagination justify-content-center">
						<s:if test='page >0'>

							<li class="page-item"><a class="page-link"
								href="videoList?page=0">首页</a></li>
						</s:if>
						<s:if test='prevpage>=0'>
							<li class="page-item"><a class="page-link"
								href="videoList?page=${prevpage}">上一页</a></li>
						</s:if>
						<s:iterator value="pages" var="p">

							<li class="page-item"><a class="page-link"
								href="videoList?page=${p}">${p+1}</a></li>
						</s:iterator>
						<s:if test='nextpage >0&&nextpage<maxPage'>

							<li class="page-item"><a class="page-link"
								href="videoList?page=${nextpage}">下一页</a></li>
						</s:if>
						<s:if test='maxPage >10'>
							<li class="page-item"><a class="page-link"
								href="videoList?page=${maxPage }">总共${maxPage+1 }页</a></li>
						</s:if>
					</ul>
				</nav>
				
			</div>
			<div class="col-sm-2"></div>
		</div>
	</div>





</body>
</html>
