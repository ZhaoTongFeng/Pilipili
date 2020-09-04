<%@page import="action.MyConfig"%>
<%@page import="bean.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<%@page isELIgnored="false"%>


 
<div hidden>
	<b>公开页面：</b> <a href="index">首页</a> 
	<a href="sign_up">注册</a> 
	<a href="sign_in">登录</a> 
	<a href="video">视频播放页</a> 
	<a href="category">分类页</a>
	<a href="search">视频搜索页</a> 
	<br> 
	<a href="home"><b>用户主页</b></a> 
	<a href="uploadVideo">视频上传页</a> 
	<a href="videoList">视频管理页</a> 
	<a href="commentList">评论管理页</a> 
	<a href="balance">积分中心</a> 
	<a href="vip">会员中心</a>
	<a href="bookmark">收藏夹</a> 
	<br> 
	<a href="home_admin"><b>管理员主页</b></a>
	<a href="userList">用户管理页</a> 
	<a href="categoryList">类型管理页</a> 
	<a href="auditList">审核</a> <br> <b>当前权限：</b>

	<%
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			if (user.getLevel() == MyConfig.LEVEL_USER) {
				out.println("<span>用户</span>");
			} else if (user.getLevel() == MyConfig.LEVEL_VIP) {
				out.println("<span>VIP</span>");
			} else if (user.getLevel() == MyConfig.LEVEL_ADMIN) {
				out.println("<span>管理员</span>");
			}
		} else {
			out.println("未登录");
		}
		out.println("<b>权限变更：</b>");
		out.println("<a href='setLevel?level=" + MyConfig.LEVEL_USER + "'>用户</a>");
		out.println("<a href='setLevel?level=" + MyConfig.LEVEL_VIP + "'>VIP</a>");
		out.println("<a href='setLevel?level=" + MyConfig.LEVEL_ADMIN + "'>管理员</a>");
	%>
	<br>
</div>









<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm p-3 mb-5 bg-white rounded">
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	
	<a class="navbar-brand" href="index">PILPIL</a>
	<div class="collapse navbar-collapse" id="navbarTogglerDemo03">

		<!-- 左侧 -->
		<ul class="navbar-nav  mt-2 mt-lg-0">
			<li class="nav-item active"><a class="nav-link" href="index">首页 <span class="sr-only">(current)</span></a></li>
			<li class="nav-item active"><a class="nav-link" href="category">分类</a></li>
		</ul>
		<!-- 搜索 -->
		<form class="form-inline my-2 my-lg-0" action="search">
			<input
				class="form-control mr-sm-2" type="search" name="content"
				placeholder="Search" aria-label="Search" >
			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
		</form>

		<!-- 右侧 -->
		<ul class="navbar-nav  mt-2 mt-lg-0" style="margin-left: auto;">
		<s:if test="user != null">
			<!-- 登录 -->
			<li class='nav-item dropdown'>
				<!-- 图片 -->
				<a class='nav-link ' href='#' id='navbarDropdown' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>
				
				
			<s:if test="user.img!=null">
				<img src='${user.img }' width='24' height='24' class='rounded-circle' alt=''>
			</s:if>
			<s:else>${user.name }</s:else>
				</a>
				
				<!-- 下拉菜单 -->
				<div class='dropdown-menu' aria-labelledby='navbarDropdown'>
					<a class='dropdown-item' href='home'>${user.name }</a>
					<a class='dropdown-item' href='home'>${user.email }</a>
	
					<a class='dropdown-item' href='home'>个人中心</a> 
					<div class='dropdown-divider'></div>
					<a class='dropdown-item' href='commentList'>评论管理</a>
					<a class='dropdown-item' href='videoList'>视频管理</a>
					<!-- 管理员多两个 -->
				<s:if test="user.level ==2">
					<a class='dropdown-item' href='userList'>用户管理</a>
					<a class='dropdown-item' href='auditList'>审核管理</a>
				</s:if>
					<div class='dropdown-divider'></div>
					<a class='dropdown-item' href='sign_out'>注销</a>
				</div>
			</li>
			<li class='nav-item active'><a class='nav-link' href='bookmark'>收藏<span class='sr-only'>(current)</span></a></li>
			<li class='nav-item active'><a class='nav-link' href='messageList'>消息<span class='sr-only'>(current)</span></a></li>
			
			<li class='nav-item active'><a class='nav-link' href='uploadVideo'>上传<span class='sr-only'>(current)</span></a></li>
		</s:if>
		
		
		<s:else>
			<!-- 未登录 -->
			<li class='nav-item active'><a class='nav-link' href='sign_up'>注册<span class='sr-only'>(current)</span></a></li>
			<li class='nav-item active'><a class='nav-link' href='sign_in'>登录<span class='sr-only'>(current)</span></a></li>
		</s:else>
		</ul>
	</div>
</nav>