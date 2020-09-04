<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" isELIgnored="false"%>
 
<%@ taglib prefix="s" uri="/struts-tags" %>
 
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











	<h3 style="text-align: center">我的评论</h3>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8">
			
				<table class="table">
					<thead>
						<tr>
							<th scope="col">#</th>
							<th scope="col">视频</th>
							<th scope="col">内容</th>
							<th scope="col">赞</th>
							<th scope="col">回复</th>
							<th scope="col">时间</th>
							<th scope="col">删除</th>
						</tr>

					</thead>
					<tbody>
						<s:iterator value="comments" var="c" status="status">
							<tr>
								<th><s:property value="#status.index" /></th>
								<th><a href="video?id=${c.video.id }">${c.video.title }</a></th>
								<th>${c.content }</th>
								<th>${c.num_good }</th>
								<th>${c.num_reply }</th>
								<th>${c.time_create }</th>
								<td><a href="deleteComment?id=${c.id }">删除</a></td>
																
							</tr>
						</s:iterator>
					</tbody>
				</table>
				

				<nav aria-label="Page navigation example">
					<ul class="pagination justify-content-center">
						<s:if test='page >0'>

							<li class="page-item"><a class="page-link"
								href="commentList?page=0">首页</a></li>
						</s:if>
						<s:if test='prevpage>=0'>
							<li class="page-item"><a class="page-link"
								href="commentList?page=${prevpage}">上一页</a></li>
						</s:if>
						<s:iterator value="pages" var="p">

							<li class="page-item"><a class="page-link"
								href="commentList?page=${p}">${p+1}</a></li>
						</s:iterator>
						<s:if test='nextpage >0&&nextpage<maxPage'>

							<li class="page-item"><a class="page-link"
								href="commentList?page=${nextpage}">下一页</a></li>
						</s:if>
						<s:if test='maxPage >10'>
							<li class="page-item"><a class="page-link"
								href="commentList?page=${maxPage }">总共${maxPage+1 }页</a></li>
						</s:if>
					</ul>
				</nav>
				
			</div>
			<div class="col-sm-2"></div>
		</div>
	</div>
























</body>
</html>