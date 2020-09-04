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











	<h3 style="text-align: center">我的消息</h3>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8">
			
	
						<s:iterator value="messages" var="m" status="status">
							<div class="media shadow-sm p-3 mb-5 bg-white rounded" style="margin-bottom:16px">
							<s:if test="m.fromUser!=null"><img src="${m.fromUser.img }" style="width:50px;height:50px;margin-right:16px;" class="rounded-circle"/></s:if>
							
							  
							  <div class="media-body">
							  
							    <h5 class="mt-0" >${m.fromUser.name }</h5>
							    
							    <p><s:if test="messages[#status.index].fromUser == null"><span>【系统】</span></s:if>${m.content }</p>
							    <span>${m.time_create }</span>
							    
							  </div>
							</div>
							
						</s:iterator>

				

				<nav aria-label="Page navigation example">
					<ul class="pagination justify-content-center">
						<s:if test='page >0'>

							<li class="page-item"><a class="page-link"
								href="messageList?page=0">首页</a></li>
						</s:if>
						<s:if test='prevpage>=0'>
							<li class="page-item"><a class="page-link"
								href="messageList?page=${prevpage}">上一页</a></li>
						</s:if>
						<s:iterator value="pages" var="p">

							<li class="page-item"><a class="page-link"
								href="messageList?page=${p}">${p+1}</a></li>
						</s:iterator>
						<s:if test='nextpage >0&&nextpage<maxPage'>

							<li class="page-item"><a class="page-link"
								href="messageList?page=${nextpage}">下一页</a></li>
						</s:if>
						<s:if test='maxPage >10'>
							<li class="page-item"><a class="page-link"
								href="messageList?page=${maxPage }">总共${maxPage+1 }页</a></li>
						</s:if>
					</ul>
				</nav>
				
			</div>
			<div class="col-sm-2"></div>
		</div>
	</div>



</body>
</html>