<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.springframework.security.core.GrantedAuthority"%>
<%@ page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="com.sevensoftware.domotica.entities.Usuario"%>
<%@ page import="org.springframework.security.core.userdetails.User"%>
<%@ page import="com.sevensoftware.domotica.entities.CustomUserDetail"%>
<%
	CustomUserDetail userDetails = (CustomUserDetail) request.getAttribute("userDetails");
%>
<!DOCTYPE html>
<html lang="es" ng-app="administrador">
<head>
<title>Administrador Riego</title>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description"
	content="Administrador Domotica">
<meta name="viewport" content="initial-scale=1" />

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


<link rel='stylesheet'
	href='http://fonts.googleapis.com/css?family=Roboto:400,500,700,400italic'>
<link rel="stylesheet"
	href="app/assets/libs/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="app/assets/libs/angular-material/angular-material.css" />
<link rel="stylesheet" href="app/assets/css/general.css" />
<link rel="stylesheet" href="app/assets/css/administrador.css" />
<link rel="stylesheet"
	href="app/assets/libs/datatables/media/css/jquery.dataTables.min.css">
<link rel="stylesheet"
	href="app/assets/libs/datatables/media/css/responsive.dataTables.min.css">
<link rel="stylesheet"
	href="app/assets/libs/angular-material-datetimepicker/material-datetimepicker.css">
<!-- <link rel="stylesheet" href="app/assets/libs/full-calendar/css/fullcalendar.css"> -->
<link rel="stylesheet"
	href="app/assets/libs/full-calendar/css/fullcalendar.css">




<script src="app/assets/libs/angular/angular.js"></script>
<script src="app/assets/libs/ion.sound-3.0.7/ion.sound.min.js"></script>

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<script src="https://code.highcharts.com/highcharts-more.js"></script>
<script src="https://code.highcharts.com/modules/solid-gauge.js"></script>


<script type="text/javascript">
	angular
			.element(document.getElementsByTagName('head'))
			.append(
					angular
							.element('<base href="' + window.location.pathname + '" />'));
</script>

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
		
		
		.connectingClass {
			position: fixed;
			left: 0px;
			top: 0px;
			width: 100%;
			height: 100%;
			z-index: 9999;
			background: url('app/assets/img/admin/conectando.gif') 50% 50% no-repeat rgb(249,249,249);
		}
    
    
    </style>
    
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    
    <script type="text/javascript">
		$(window).load(function() {
			$(".loader").fadeOut("slow");
		})
	</script>
	
	
	

</head>

<body layout="column" ng-controller="MenuController as ul" ng-cloak>

	<div class="loader"></div>
	
	<div class="connectingClass" ng-show="ul.connecting"></div>

	<!--[if lt IE 9]>
      <p class="browse-happy">Estas usando un explorador <strong>desactualizado y sin soporte</strong>. Por favor <a href="http://browsehappy.com/">Actualiza tu explorador</a> para mejorar tu experiencia.</p>
    <![endif]-->

	<md-toolbar id="toolbar-admin" layout="row" class="md-whiteframe-1dp"
		class="toolbar-admin"
		layout-gt-sm="myStyle={'background-color':'white'}">

	<div class="logo-admin" flex hide-sm hide-xs></div>

	<img class="logo-mobile"
		src="app/assets/img/admin/logo.png"
		alt="Riego IoT" title="Riego IoT"
		hide-gt-sm /> <span flex></span>

	<h3 class="md-accent role" hide-xs hide-sm>	
	
		<md-fab-speed-dial md-open="ul.fabIsOpen" md-direction="{{ul.fabSelectedDirection}}"
                         ng-class="ul.fabSelectedMode" ng-class="{ 'md-hover-full': ul.hover }"
                       ng-mouseenter="ul.fabIsOpen=true" ng-mouseleave="ul.fabIsOpen=false">
                       
        <md-fab-trigger>
          <md-button aria-label="perfiles" class="md-fab md-accent">
          <md-tooltip md-direction="top" md-visible="tooltipVisible">Perfiles</md-tooltip>
            <md-icon md-svg-src="app/assets/img/admin/perfiles.svg"></md-icon>
          </md-button>
        </md-fab-trigger>
             

        <md-fab-actions>
        
        	<%
			//List<String> roles = new ArrayList();
        	String HTML = "";
			for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
				String authorityTemp = grantedAuthority.getAuthority();
				authorityTemp = authorityTemp.replace("_", " ");
				String authority = authorityTemp.replace("ROLE", "").trim();
				
				switch(authority){					
					case "ADMIN":
						HTML += "<md-button aria-label='Administrador' class='md-fab md-raised md-mini'> " 
								+ "<md-tooltip md-direction='top' md-visible='tooltipVisible' md-autohide='false'> Administrador </md-tooltip>"
								+ "<md-icon md-svg-src='app/assets/img/admin/administrador.svg' aria-label='Administrador'></md-icon> </md-button> </br>";
						break;
					case "PROPIETARIO":
						HTML += "<md-button aria-label='Propietario' class='md-fab md-raised md-mini'> " 
								+ "<md-tooltip md-direction='top' md-visible='tooltipVisible' md-autohide='false'> Propietario </md-tooltip>"
								+ "<md-icon md-svg-src='app/assets/img/admin/propietario.svg' aria-label='Propietario'></md-icon> </md-button> </br>";
						break;					
					case "INTEGRANTE":
						HTML += "<md-button aria-label='Integrante' class='md-fab md-raised md-mini'> " 
								+ "<md-tooltip md-direction='top' md-visible='tooltipVisible' md-autohide='false'> Integrante </md-tooltip>"
								+ "<md-icon md-svg-src='app/assets/img/admin/integrante.svg' aria-label='Integrante'></md-icon> </md-button> </br>";
				}			
				//roles.add(authority);				
			}			
			%>
        	<%=HTML%>              
        </md-fab-actions>       
      </md-fab-speed-dial>
	
	
		
		
	</h3>

	<md-button class="menu" ng-click="ul.toggleMenu()"
		aria-label="Show Menu List"> <md-icon md-svg-icon="app/assets/img/admin/menu.svg"></md-icon>
	</md-button>
     
	</md-toolbar>

	<div flex layout="row">
		 <md-sidenav id="sidenav-usco" md-component-id="left" md-component-id="left" 
		 	class="md-whiteframe-1dp sidenav-usco" layout="column" flex>
				
		<div layout="row">
			<div class="sidenav-imagen">
				<md-button class="md-whiteframe-1dp close-sidenav"
					aria-label="cerrar" ng-transclude
					ng-click="$mdSidenav('right').close();ul.toggleMenu();">
				<i class="fa fa-arrow-left" aria-hidden="true"></i> 
				</md-button>
				
				<%
					String genero = userDetails.getUser().getGenero().getNombre();
					String logo = "";
					if(genero.equalsIgnoreCase("MASCULINO")){
						logo = "<img src='app/assets/img/admin/user/usuario-m.png' class='avatar'>";	
					}else{
						logo = "<img src='app/assets/img/admin/user/usuario-f.png' class='avatar'>";
					}				
				%>
				<%=logo%>			
				
			</div>
		</div>
		<a ng-href="#perfil"
			ng-click="ul.toggleMenu();ul.selected.name='Perfil del Usuario'"
			class="profile-buttom" data-ma-action="profile-menu-toggle">
			<div class="profile-menu">
					<%= userDetails.getUser().getNombre() + " " + userDetails.getUser().getApellido()%> <i class="zmdi zmdi-caret-down"></i> <small
					class="small-role"><i class="fa fa-cog" aria-hidden="true"></i></small>
			</div>
		</a> 
		<md-list> 
			<md-list-item ng-repeat="it in ul.menu" layout="column"> 
					 
				<sec:authorize access="hasAnyRole('ROLE_ADMIN') and isAuthenticated()">
			<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Gestion Usuarios'"
				ng-href="#usuario" class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-list"
				aria-hidden="true"></i> Gestion de Usuarios </md-button>
		</sec:authorize>
		
		<sec:authorize access="hasAnyRole('ROLE_ADMIN') and isAuthenticated()">
			<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Gestion Viviendas'"
				ng-href="#vivienda" class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-list"
				aria-hidden="true"></i> Gestion de Viviendas </md-button>
		</sec:authorize>
		
		<sec:authorize access="hasAnyRole('ROLE_ADMIN') and isAuthenticated()">
			<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Gestion Espacios'"
				ng-href="#espacio" class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-list"
				aria-hidden="true"></i> Gestion de Espacios </md-button>
		</sec:authorize>
		
		<sec:authorize access="hasAnyRole('ROLE_ADMIN') and isAuthenticated()">
			<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Gestion Nodos'"
				ng-href="#nodo" class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-list"
				aria-hidden="true"></i> Gestion de Nodos </md-button>
		</sec:authorize>
		
		<sec:authorize access="hasAnyRole('ROLE_ADMIN') and isAuthenticated()">
			<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Gestion Dispositivos'"
				ng-href="#dispositivo" class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-list"
				aria-hidden="true"></i> Gestion de Dispositivos </md-button>
		</sec:authorize>
		
		<sec:authorize access="hasAnyRole('ROLE_ADMIN') and isAuthenticated()">
			<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Gestion Dispositivos Items'"
				ng-href="#dispositivoItem" class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-list"
				aria-hidden="true"></i> Gestion de Dispositivos Items </md-button>
		</sec:authorize>
		
		
		
		<!-- -------------------------------------------------------------------- -->
		
		
		
		<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'INTEGRANTE', 'PROPIETARIO') and isAuthenticated()">
			<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Monitoreo y Control Espacios'"
				ng-href="#listaEspacios" class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-list"
				aria-hidden="true"></i> Lista de Espacios </md-button>
		</sec:authorize>
		
		
		
		<!-- -------------------------------------------------------------------- -->
		<!-- 
		<sec:authorize access="hasAnyRole('ROLE_ADMINISTRADOR') and isAuthenticated()">
			<md-button
				ng-click="it.show?it.show=false:it.show=true;"
				class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'toggle'">
				<i class="fa fa-list"
				aria-hidden="true"></i> Medicos <i
				class="fa fa-plus-circle more-links" aria-hidden="true"
				ng-if="it.type == 'toggle' && it.show== false || it.show == undefined"></i>
				<i class="fa fa-minus-circle more-links" aria-hidden="true"
				ng-show="it.show"></i> </md-button>				
		</sec:authorize>
		
		<md-list id="sub-menu" class="sub-menu" ng-if="it.type == 'toggle'"
			ng-show="it.show" ng-animate="animate">
			<sec:authorize
				access="hasAnyRole('ROLE_ADMINISTRADOR') and isAuthenticated()">
				<md-list-item id="subItem" flex> 
				<md-button
					ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Inscripcion Medico'"
					ng-href="#inscripcionMedico" class="menu-button"
					ng-class="{'selected' : subIt === ul.selected }"> <i
					class="fa fa-pied-piper" aria-hidden="true"></i> Inscripcion
				</md-button> 
				</md-list-item>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_ADMINISTRADOR') and isAuthenticated()">
				<md-list-item id="subItem" flex> 
				<md-button
					ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Gestion Medicos'"
					ng-href="#medicos" class="menu-button"
					ng-class="{'selected' : subIt === ul.selected }"> <i
					class="fa fa-list" aria-hidden="true"></i> Gestion de Medicos 
				</md-button> 
				</md-list-item>
			</sec:authorize>			
		</md-list>
		 -->
		<!-- -------------------------------------------------------------------- -->
		<!--  
		<sec:authorize
			access="hasAnyRole('ROLE_ADMINISTRADOR') and isAuthenticated()">
			<md-button ng-click="it.showv?it.showv=false:it.showv=true;"
				class="menu-button" ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'toggle'"> <i class="fa fa-list"
				aria-hidden="true"></i> Administradores <i
				class="fa fa-plus-circle more-links" aria-hidden="true"
				ng-if="it.type == 'toggle' && it.showv== false || it.showv == undefined"></i>
			<i class="fa fa-minus-circle more-links" aria-hidden="true"
				ng-show="it.showv"></i> </md-button>
		</sec:authorize> 
		<md-list id="sub-menu" class="sub-menu" ng-if="it.type == 'toggle'"
			ng-show="it.showv" ng-animate="animate"> <sec:authorize
			access="hasAnyRole('ROLE_ADMINISTRADOR') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Inscripcion Administrador'"
				ng-href="#inscripcionAdministrador" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-list" aria-hidden="true"></i> Inscripcion
			</md-button> </md-list-item>
		</sec:authorize> <sec:authorize
			access="hasAnyRole('ROLE_ADMINISTRADOR') and isAuthenticated()">
			<md-list-item id="subItem" flex> <md-button
				ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Gestion Administradores'"
				ng-href="#administradores" class="menu-button"
				ng-class="{'selected' : subIt === ul.selected }"> <i
				class="fa fa-list" aria-hidden="true"></i> Gestion Administradores </md-button> </md-list-item>
		</sec:authorize> 
				
		</md-list> 	
		-->
		<!-- -------------------------------------------------------------------- -->
		
		
		<!-- ------------------------------------------------------------------------------------- -->
		<!--  
		<sec:authorize access="hasAnyRole('ROLE_ADMINISTRADOR') and isAuthenticated()">
			<md-button
				ng-click="it.showaf?it.showaf=false:it.showaf=true;"
				class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'toggle'">
				<i class="fa fa-list"
				aria-hidden="true"></i> Afiliaciones <i
				class="fa fa-plus-circle more-links" aria-hidden="true"
				ng-if="it.type == 'toggle' && it.showaf== false || it.showaf == undefined"></i>
				<i class="fa fa-minus-circle more-links" aria-hidden="true"
				ng-show="it.showaf"></i> </md-button>				
		</sec:authorize>
		
		<md-list id="sub-menu" class="sub-menu" ng-if="it.type == 'toggle'"
			ng-show="it.showaf" ng-animate="animate">
			<sec:authorize
				access="hasAnyRole('ROLE_ADMINISTRADOR') and isAuthenticated()">
				<md-list-item id="subItem" flex> 
				<md-button
					ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Adicionar Afiliacion'"
					ng-href="#adicionAfiliacion" class="menu-button"
					ng-class="{'selected' : subIt === ul.selected }"> <i
					class="fa fa-pied-piper" aria-hidden="true"></i> Adicionar
				</md-button> 
				</md-list-item>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_ADMINISTRADOR') and isAuthenticated()">
				<md-list-item id="subItem" flex> 
				<md-button
					ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Gestion Afiliaciones'"
					ng-href="#afiliaciones" class="menu-button"
					ng-class="{'selected' : subIt === ul.selected }"> <i
					class="fa fa-list" aria-hidden="true"></i> Gestion de Afiliaciones 
				</md-button> 
				</md-list-item>
			</sec:authorize>			
		</md-list>
		-->
		<!-- ------------------------------------------------------------------------------------- -->
		
		<!--  
		<sec:authorize access="hasAnyRole('ROLE_ADMINISTRADOR') and isAuthenticated()">
			<md-button
				ng-click="it.showsus?it.showsus=false:it.showsus=true;"
				class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'toggle'">
				<i class="fa fa-list"
				aria-hidden="true"></i> Suscripciones <i
				class="fa fa-plus-circle more-links" aria-hidden="true"
				ng-if="it.type == 'toggle' && it.showsus== false || it.showsus == undefined"></i>
				<i class="fa fa-minus-circle more-links" aria-hidden="true"
				ng-show="it.showsus"></i> </md-button>				
		</sec:authorize>
		
		<md-list id="sub-menu" class="sub-menu" ng-if="it.type == 'toggle'"
			ng-show="it.showsus" ng-animate="animate">
			<sec:authorize
				access="hasAnyRole('ROLE_ADMINISTRADOR') and isAuthenticated()">
				<md-list-item id="subItem" flex> 
				<md-button
					ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Adicionar Suscripcion'"
					ng-href="#adicionSuscripcion" class="menu-button"
					ng-class="{'selected' : subIt === ul.selected }"> <i
					class="fa fa-pied-piper" aria-hidden="true"></i> Adicionar
				</md-button> 
				</md-list-item>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_ADMINISTRADOR') and isAuthenticated()">
				<md-list-item id="subItem" flex> 
				<md-button
					ng-click="ul.selectMenu(subIt);ul.toggleMenu();ul.selected.name='Gestion Suscripciones'"
					ng-href="#suscripciones" class="menu-button"
					ng-class="{'selected' : subIt === ul.selected }"> <i
					class="fa fa-list" aria-hidden="true"></i> Gestion de Suscripciones 
				</md-button> 
				</md-list-item>
			</sec:authorize>			
		</md-list>
		-->
		<!-- ------------------------------------------------------------------------------------- -->
				
		<md-button
				ng-click="ul.selectMenu(it);ul.toggleMenu();ul.selected.name='Acerca de'"
				ng-href="#acerca" class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-list"
				aria-hidden="true"></i> Acerca de </md-button>
				
		<md-button
				ng-click="ul.portal()"
				ng-href="#salir" class="menu-button"
				ng-class="{'selected' : it === ul.selected }"
				ng-if="it.type == 'link'"> <i class="fa fa-list"
				aria-hidden="true"></i> Salir </md-button>		
			 
			
			 
			</md-list-item>		 
		
		</md-list> 
		</md-sidenav>

		<md-content flex id="content">

		<div class="card">
			<h1 class="template-title" ng-if="ul.selected.name == null">Bienvenido a la plataforma
			 de Riego IoT para tus Jardines</h1>
			<h1 class="template-title" ng-if="ul.selected.name">{{ul.selected.name}}</h1>
			<div class="template-content" ng-view></div>
		</div>
		</md-content>
	</div>
	
	
	<!--  
	<audio id="audio">
		<source src="app/assets/ring.ogg" type="audio/ogg">
		Your browser does not support the audio element.
	</audio>
	-->

	<script src="app/assets/libs/angular-animate/angular-animate.js"></script>
	<script src="app/assets/libs/angular-aria/angular-aria.js"></script>
	<script src="app/assets/libs/angular-messages/angular-messages.min.js"></script>
	<script src="app/assets/libs/angular-material/angular-material.js"></script>
	<script src="app/assets/libs/angular-route/angular-route.min.js"></script>
	<script src="app/assets/libs/angular-resource/angular-resource.js"></script>
	<script src="app/assets/libs/angular-tinymce/tinymce-dist/tinymce.js"></script>
	<script
		src="app/assets/libs/angular-tinymce/angular-ui-tinymce/src/tinymce.js"></script>
	<script
		src="app/assets/libs/angular-material-datetimepicker/moment.min.js"></script>
	<script
		src="app/assets/libs/angular-material-datetimepicker/angular-material-datetimepicker.js"></script>
	<script src="app/assets/libs/jquery/dist/jquery.min.js"></script>
	<script
		src="app/assets/libs/datatables/media/js/jquery.dataTables.min.js"></script>
	
	<!--  
	<script
		src="app/assets/libs/datatables/media/js/dataTables.responsive.min.js"></script>
	<script src="app/assets/libs/full-calendar/js/fullcalendar.js"></script>
	<script src="app/assets/libs/full-calendar/js/gcal.js"></script>
	<script src="app/assets/libs/full-calendar/js/lang-all.js"></script>
	<script src="app/assets/libs/full-calendar/js/calendar.js"></script>
	<script src="app/assets/libs/full-calendar/js/angular-locale_es-co.js"></script>
	<script src="app/assets/libs/alertify/js/alertify.min.js"></script>
	-->
	
	
	<script src="app/app.modules.js"></script>
	<script src="app/app.routes.js"></script>
	<script src="app/services/menu/MenuService.js"></script>
	<script src="app/controllers/menu/MenuController.js"></script>
	<script src="app/directives/password/PasswordConfirmationDirective.js"></script>
	<script src="app/assets/libs/sockjs-client/dist/sockjs.min.js"></script>
	<script src="app/assets/libs/stomp-websocket/lib/stomp.min.js"></script>
	
	
	<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'INTEGRANTE', 'PROPIETARIO') and isAuthenticated()">
	
		<script src="app/controllers/listaEspacios/ListaEspaciosController.js"></script>
		<script src="app/services/vivienda/ViviendaSocketService.js"></script>
		<script src="app/controllers/jardin/JardinController.js"></script>
		
		
	</sec:authorize>
	
	

	<sec:authorize access="hasAnyRole('ROLE_ADMIN') and isAuthenticated()">	
		
		
		<!--  
		<script src="app/controllers/perfil/PerfilController.js"></script>
    	<script src="app/controllers/medicos/MedicosController.js"></script>
    	<script src="app/controllers/medicos/inscripcion/MedicosInscripcionController.js"></script>
    	<script src="app/controllers/administradores/AdministradoresController.js"></script>
    	<script src="app/controllers/administradores/inscripcion/AdministradoresInscripcionController.js"></script>
    	<script src="app/controllers/familiares/FamiliaresController.js"></script>
    	<script src="app/controllers/familiares/inscripcion/FamiliaresInscripcionController.js"></script>
    	<script src="app/controllers/pacientes/PacientesController.js"></script>
    	<script src="app/controllers/pacientes/inscripcion/PacientesInscripcionController.js"></script>  	
    	<script src="app/controllers/eps/EpsController.js"></script>
    	<script src="app/controllers/contratos/ContratosController.js"></script>
    	<script src="app/controllers/afiliaciones/AfiliacionesController.js"></script>
    	<script src="app/controllers/afiliaciones/adicion/AfiliacionesAdicionController.js"></script>
    	   	
    	<script src="app/controllers/suscripciones/SuscripcionesController.js"></script>
    	<script src="app/controllers/suscripciones/adicion/SuscripcionesAdicionController.js"></script>
    	-->    	
    	<script src="app/controllers/usuario/UsuarioController.js"></script>
    	<script src="app/controllers/vivienda/ViviendaController.js"></script>
    	<script src="app/controllers/espacios/EspaciosController.js"></script>
    	<script src="app/controllers/nodos/NodosController.js"></script>
    	<script src="app/controllers/dispositivos/DispositivosController.js"></script>
    	<script src="app/controllers/dispositivosItem/DispositivosItemsController.js"></script>
    	
    			
	</sec:authorize>

	<script type="text/javascript">
		angular
				.element(document.getElementsByTagName('head'))
				.append(
						angular
								.element('<base href="' + window.location.pathname + '" />'));
	</script>
</body>
</html>