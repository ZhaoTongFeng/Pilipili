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




	<h3 style="text-align: center">用户管理</h3>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8">
			
				<table class="table">
					<thead>
						<tr>
							<th scope="col">#</th>
							<th scope="col">昵称</th>
							<th scope="col">最近签到</th>
							<th scope="col">注册时间</th>
							
							<th scope="col">邮箱</th>
							<th scope="col">权限</th>
							<th scope="col">VIP到期时间</th>

							<th scope="col">被评论</th>
							<th scope="col">被观看</th>
							<th scope="col">被点赞</th>
							<th scope="col">被收藏</th>

							<th scope="col">封禁</th>
						</tr>

					</thead>
					<tbody>
						<s:iterator value="users" var="u" status="status">
							<tr>
								<th><s:property value="#status.index" /></th>
								<th>${u.name }</th>
								<th>${u.checkInTime }</th>
								<th>${u.registerTime }</th>
								<th>${u.email }</th>
								
								<s:if test="users[#status.index].level == 0"><th>普通用户</th></s:if>
								<s:elseif test="users[#status.index].level == 1"><th>VIP用户</th></s:elseif>
								<s:elseif test="users[#status.index].level == 2"><th>管理员</th></s:elseif>
								<s:elseif test="users[#status.index].level == -1"><th>永久封禁</th></s:elseif>
								<s:else><th>${u.level }</th></s:else>
								
								<th>${u.vipTime }</th>
								
								<th>${u.num_comment }</th>
								<th>${u.num_view }</th>
								<th>${u.num_good }</th>

								<th>${u.num_mark }</th>
								
								<s:if test="users[#status.index].level == -1"><th><a href="deleteUser?id=${u.id}">解封</a></th></s:if>
								<s:else><th><a href="deleteUser?id=${u.id}">封禁</a></th></s:else>
								
							</tr>
						</s:iterator>
					</tbody>
				</table>
				

				<nav aria-label="Page navigation example">
					<ul class="pagination justify-content-center">
						<s:if test='page >0'>

							<li class="page-item"><a class="page-link"
								href="userList?page=0">首页</a></li>
						</s:if>
						<s:if test='prevpage>=0'>
							<li class="page-item"><a class="page-link"
								href="userList?page=${prevpage}">上一页</a></li>
						</s:if>
						<s:iterator value="pages" var="p">

							<li class="page-item"><a class="page-link"
								href="userList?page=${p}">${p+1}</a></li>
						</s:iterator>
						<s:if test='nextpage >0&&nextpage<maxPage'>

							<li class="page-item"><a class="page-link"
								href="userList?page=${nextpage}">下一页</a></li>
						</s:if>
						<s:if test='maxPage >10'>
							<li class="page-item"><a class="page-link"
								href="userList?page=${maxPage }">总共${maxPage+1 }页</a></li>
						</s:if>
					</ul>
				</nav>
				
			</div>
			<div class="col-sm-2"></div>
		</div>
	</div>
	








</body>
</html>