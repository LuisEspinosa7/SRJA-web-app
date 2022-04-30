<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
	
<div data-ng-controller="JardinController" flex ng-cloak>

	<h3>ACTUADORES</h3>
	
	<md-grid-list md-cols="1" md-cols-sm="2" md-cols-md="3"
		md-cols-gt-md="6" md-row-height-gt-md="1:1" md-row-height="4:3"
		md-gutter="8px" md-gutter-gt-sm="4px"> 
		
		<!--  md-rowspan="{{dispositivoItemActuador.dispositivo.nombre === 'ILUMINACION RGB' ? '2' : '1'}}" -->
		<md-grid-tile ng-repeat="dispositivoItemActuador in actuadoresLista" 			
			md-rowspan="1"
			md-colspan="1" 
			md-colspan-sm="1" 
			md-colspan-xs="1"
			ng-style = "{ background: colors[$index]}">	
			<!-- #/{{espacio.nombre } -->		
				
			<md-grid-tile-header>
   				 
   				 <md-menu>
			      <md-button aria-label="Menu de Opciones" class="md-icon-button" ng-click="openMenu($mdOpenMenu, $event)">
			        <md-icon md-menu-origin md-svg-src="app/assets/img/admin/configuracion.svg"></md-icon>
			      </md-button>
			      <md-menu-content width="4">
			        
			        
			        <md-menu-item>
			          <md-button ng-click="toggledispositivoItemActuadorEncendido(dispositivoItemActuador)">
			            <md-icon md-svg-src="app/assets/img/admin/vivienda/{{dispositivoItemActuadorEncendido(dispositivoItemActuador) ? 'on' : 'off'}}.svg"></md-icon>
			             {{dispositivoItemActuadorEncendido(dispositivoItemActuador) ? 'APAGAR' : 'ENCENDER'}} 
			          </md-button>
			        </md-menu-item>
			        <!--  
			        <md-menu-item>
			          <md-button ng-click="toggledispositivoItemActuadorEstado()">
			            <md-icon md-svg-src="app/assets/img/admin/vivienda/{{dispositivoItemActuadorEstado ? 'on' : 'off'}}.svg"></md-icon>
			             {{dispositivoItemActuadorEstado ? 'APAGAR' : 'ENCENDER'}} 
			          </md-button>
			        </md-menu-item>
			        -->		
			        	        
			        <md-menu-divider></md-menu-divider>
			        
			       	<md-menu-item ng-if="dispositivoItemActuador.dispositivo.nombre == 'ILUMINACION RGB'">
			          <md-button ng-click="changeRGB(dispositivoItemActuador, $event)">
			            <md-icon md-svg-src="app/assets/img/admin/vivienda/{{dispositivoItemActuador.dispositivo.nombre | lowercase}}.svg"></md-icon>
			            LUZ RGB
			          </md-button>
			        </md-menu-item>
			      	
			      </md-menu-content>
			    </md-menu>
   				 
 		 	</md-grid-tile-header>
			<md-icon md-svg-src="app/assets/img/admin/vivienda/{{dispositivoItemActuador.dispositivo.nombre | lowercase}}.svg" class="size-100" ></md-icon>
			
			<md-grid-tile-footer>	
				<h3>{{dispositivoItemActuador.dispositivo.nombre }} - {{ dispositivoItemActuador.dispositivo.categoria.nombre  }}</h3>
	        </md-grid-tile-footer> 		
			 
		</md-grid-tile>		
	</md-grid-list>
	
	<h3>SENSORES</h3>
	
		
	<div id="container1"></div>
	</br>
	<div id="container2"></div>
	</br>
	<div id="containerSoilTemperature"></div>	
	
	</br>
	
	<div style="width: 600px; height: 400px; margin: 0 auto">
		<div id="container-sh1a" style="width: 300px; height: 200px; float: left"></div>
	</div>
	</br>
	<div style="width: 600px; height: 400px; margin: 0 auto">
		<div id="container-sh1b" style="width: 300px; height: 200px; float: left"></div>
	</div>
	</br>
	
	<div style="width: 600px; height: 400px; margin: 0 auto">	
		<div id="container-flow" style="width: 300px; height: 200px; float: left"></div>
	</div>
	
	</br>
	
	<div style="width: 600px; height: 400px; margin: 0 auto">	
		<div id="container-rain" style="width: 300px; height: 200px; float: left"></div>
	</div>
	
	
	

</div>
	
	
	
	
	
	


