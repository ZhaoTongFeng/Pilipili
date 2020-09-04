<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page isELIgnored="false"%>

<html>
	<head>
		<title>管理员首页</title>
		<script type="text/javascript" src="./js/ZTFJS.js"></script>
<link rel="stylesheet" href="./js/bootstrap-4.4.1-dist/css/bootstrap.min.css" type="text/css"></link>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-4.4.1-dist/js/bootstrap.bundle.min.js"></script>
		
	</head>
	<body>
	<%@include file="/WEB-INF/content/jsp/public/header.jsp" %>	
		<a href="list?type=u">用户管理</a>
		<a href="list?type=v">视频管理</a>
		<a href="list?type=c">评论管理</a>
		<a href="list?type=ca">分类管理</a>
	</body>
</html>