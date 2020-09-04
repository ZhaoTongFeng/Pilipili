<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page isELIgnored="false"%>

<html>
<head>
	<title>注册</title>
<script type="text/javascript" src="./js/ZTFJS.js"></script>
<link rel="stylesheet" href="./js/bootstrap-4.4.1-dist/css/bootstrap.min.css" type="text/css"></link>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-4.4.1-dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
	<%@include file="/WEB-INF/content/jsp/public/header.jsp"%>
	
	<div class="container">
		<div class="row">
			<div class="col"></div>
			<div class="col shadow-sm p-3 mb-5 bg-white rounded">
				<h3 style="text-align:center">注册</h3>
				<div class="form-group">
					<label for="exampleInputEmail1">昵称</label> <input id="name" value="Name" type="text" class="form-control">

				</div>
				<div class="form-group">
					<label for="exampleInputEmail1">邮箱</label> <input
						onFocus="clearErr()" type="email" class="form-control" id="email"
						value="xxx@qq.com" aria-describedby="emailHelp">

				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">密码</label> <input
						onFocus="clearErr()" type="password" class="form-control"
						id="passwd" value="passwd">
				</div>

				<button id="submit" class="btn btn-primary">注册</button>
				<button class="btn" onclick="GoSignIn()">登录</button>
				<small id="errSpan" class="form-text text-muted"></small>
			</div>
			<div class="col"></div>
		</div>
	</div>
	
	
	
	
	<script>
	function GoSignIn() {
		a = document.createElement("a");
		a.href = "sign_in"
		a.click();
	}
		const submit = document.getElementById("submit");
		const errSpan = document.getElementById("errSpan");
		const nameInput = document.getElementById("name");
		const emailInput = document.getElementById("email");
		const passwdInput = document.getElementById("passwd");

		submit.onclick = function() {
			content = "name=" + nameInput.value + "&email=" + emailInput.value
					+ "&passwd=" + passwdInput.value;
			SendPost("register", content, function(res) {
				if (res.code == 0) {
					//注册成功
					errSpan.innerHTML = "";
					window.location.href = "home";
				} else {
					errSpan.innerHTML = res.errmsg;
				}
			});
		}
	</script>

</body>
</html>


