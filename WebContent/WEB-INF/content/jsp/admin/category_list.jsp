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


<table>
    <tr>
        <td>id</td>
        <td>name</td>
        <td>修改</td>
        <td>删除</td>
    </tr>
                 
<s:iterator value="categories" var="ca">
    <tr>
        <td>${ca.id}</td>
        <td>${ca.name}</td>
        <td><a href="updateCategory?id=${ca.id}">修改</a></td>
        <td><a href="deleteCategory?id=${ca.id}">删除</a></td>
    </tr>
</s:iterator>
</table>




<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
	<s:if test='page >5'>

		<li class="page-item"><a class="page-link" href="categoryList&page=0">首页</a></li>
	</s:if>
	<s:if test='prevpage>=0'>
		<li class="page-item"><a class="page-link" href="categoryList&page=${prevpage}">上一页</a></li>
	</s:if>
	<s:iterator value="pages" var="p">
		<a href=""></a>
		<li class="page-item"><a class="page-link" href="categoryList&page=${p}">${p+1}</a></li>
	</s:iterator>
	<s:if test='nextpage >0&&nextpage<maxPage'>
		
		<li class="page-item"><a class="page-link" href="categoryList&page=${nextpage}">下一页</a></li>
	</s:if>
	<s:if test='maxPage >10'>
		<li class="page-item"><a class="page-link" href="categoryList&page=${maxPage }">总共${maxPage }页</a></li>
	</s:if> 
  </ul>
</nav>



<div>
<form action="addCategory"></form>
<input value="${category.id }" name="category.id" type="text" hidden/>
<s:if test="category!=null">
修改：
</s:if>
<s:else>
新建：
</s:else>
<input value="${category.name }" name="category.name" type="text"/>
<a href="updateCategory">提交</a>
</div>
</body>
</html>