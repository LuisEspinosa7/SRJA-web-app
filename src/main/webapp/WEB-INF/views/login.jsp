<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Bienvenido</title>

<link rel="stylesheet" 	href="app/assets/libs/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="login/login.css">
<link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>

<link rel="apple-touch-icon" sizes="57x57" href="app/assets/img/favicon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60" href="app/assets/img/favicon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72" href="app/assets/img/favicon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76" href="app/assets/img/favicon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114" href="app/assets/img/favicon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120" href="app/assets/img/favicon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144" href="app/assets/img/favicon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152" href="app/assets/img/favicon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180" href="app/assets/img/favicon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192"  href="app/assets/img/favicon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="app/assets/img/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="app/assets/img/favicon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="app/assets/img/favicon/favicon-16x16.png">
<link rel="manifest" href="app/assets/img/favicon/manifest.json">
<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage" content="app/assets/img/favicon/ms-icon-144x144.png">
<meta name="theme-color" content="#ffffff">

<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

</style>

<style type="text/css">
    
    	.loader {
			position: fixed;
			left: 0px;
			top: 0px;
			width: 100%;
			height: 100%;
			z-index: 9999;
			background: url('app/assets/img/admin/page-loader.gif') 50% 50% no-repeat rgb(249,249,249);
		}
    
    
    </style>
    
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    
    <script type="text/javascript">
		$(window).load(function() {
			$(".loader").fadeOut("slow");
		})
	</script>



</head>

<body>
	
	<div class="loader"></div>
	
	<div class="logo"></div>
	<div class="login-block">
		<form action="j_spring_security_check" method="post">
		    <h1>Login</h1>
		    <c:if test="${not empty error}">
				<div class="error">${error}</div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="msg">${msg}</div>
			</c:if>

			<input type="hidden" id="${_csrf.parameterName}"
			name="${_csrf.parameterName}" value="${_csrf.token}" /> 
		    
		    <input type="text"  name="j_username" minlength="4" maxlength="17" placeholder="Username" id="username" />
		    <input type="password" name="j_password" minlength="6" maxlength="17" placeholder="Password" id="password" />
		    <button>Ingresar</button>
		</form>
	</div>

	<!-- /form -->
	<script src='app/assets/libs/jquery/dist/jquery.min.js'></script>
	<!-- Bootstrap -->
	<script src="app/assets/libs/bootstrap/js/bootstrap.min.js"></script>
	
	<script src="app/assets/libs/angular/angular.min.js"></script>
	<script src="app/assets/libs/angular-resource/angular-resource.js"></script>
	<script src="app/assets/libs/angular-messages/angular-messages.js"></script>

</body>
</html>
