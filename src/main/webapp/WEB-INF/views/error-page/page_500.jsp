<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Error en Servidor</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Page" />
<script type="application/x-javascript"> 

	addEventListener("load", function() { 
		setTimeout(hideURLbar, 0); }, false);

	function hideURLbar(){ 
		window.scrollTo(0,1); 
	} 
</script>

<link rel="stylesheet" 	href="app/assets/libs/bootstrap/css/bootstrap.css">

<!-- Custom Theme files -->
<link href="login/other-pages.css" rel="stylesheet" type="text/css" media="all" />
<!-- //Custom Theme files -->
<!-- web font -->
<link href="//fonts.googleapis.com/css?family=Josefin+Sans" rel="stylesheet">
<link href='//fonts.googleapis.com/css?family=Roboto+Condensed:400,300,300italic,400italic,700,700italic' rel='stylesheet' type='text/css'>
<!-- //web font -->


</head>

<body>
	
	<!--mian-content-->
	<h1>Error en Servidor!</h1>
		<div class="main-wthree">
			<h2>500</h2>
			
			<c:choose>
				<c:when test="${empty username}">
				  <p><span class="sub-agileinfo">Disculpas! </span>Error en el Servidor!....</p>				  
				</c:when>
				<c:otherwise>
				  <p><span class="sub-agileinfo"> Disculpanos! ${username}</span>Error en el Servidor!!....</p>				  
				</c:otherwise>
			</c:choose>
			
			<h5><a href="/monitoreo/">Salir</a></h5>
			</br>
			<h5><a href="/monitoreo/logout">Log Out</a></h5>
				
			
		</div>
	<!--//mian-content-->	

	</div>
	<!-- /form -->	

</body>
</html>
