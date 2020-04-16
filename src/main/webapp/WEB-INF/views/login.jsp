<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>RegistrationAdminWeb</title>
	<link rel="shortcut icon" sizes="196x196" href="${pageContext.request.contextPath}/resources/assets/images/round1.PNG">
	<title>RegistrationAdminWeb</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
	<meta name="description" content="Admin, Dashboard, Bootstrap" />

	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/libs/bower/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/libs/bower/material-design-iconic-font/dist/css/material-design-iconic-font.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/libs/bower/animate.css/animate.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/core.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/misc-pages.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway:400,500,600,700,800,900,300">
</head>
<body class="simple-page" style="background-color: rgb(231,0,0);">

	<div class="simple-page-wrap">
		<div class="simple-page-logo-kids animated swing">
			<a href="">
				<img src="${pageContext.request.contextPath}/resources/assets/images/round11.png"/>

			</a>
		</div><!-- logo -->

		<div class="simple-page-logo animated swing">
			<a href="">
				<img src="${pageContext.request.contextPath}/resources/assets/images/kids.png"/>
			</a>
		</div><!-- logo -->

		<div class="simple-page-form animated flipInY" id="login-form">
<!-- 	<h4 class="form-title m-b-xl text-center">Sign In With Your Admin Account</h4> -->
	<form action="loginPost" method="POST">
		<div class="form-group">
			<input name="id" id="sign-in-id" type="text" class="form-control" placeholder="UserID" autocomplete="off">
		</div>

		<div class="form-group">
			<input name="pw" id="sign-in-password" type="password" class="form-control"  placeholder="Password">
		</div>
		<input type="submit" class="btn btn-danger" value="SIGN IN">
	</form>
</div><!-- #login-form -->

	</div><!-- .simple-page-wrap -->
</body>
</html>