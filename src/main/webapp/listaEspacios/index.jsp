<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div data-ng-controller="ListaEspaciosController" flex ng-cloak>
	
	<md-grid-list md-cols="1" md-cols-sm="2" md-cols-md="3"
		md-cols-gt-md="6" md-row-height-gt-md="1:1" md-row-height="4:3"
		md-gutter="8px" md-gutter-gt-sm="4px"> 
		
		
		<md-grid-tile ng-repeat="espacio in JSONEspacios" md-rowspan="1"
			md-colspan="1" md-colspan-sm="1" md-colspan-xs="1"
			ng-style = "{ background: colors[$index]}">	
			<!-- #/{{espacio.nombre } -->		
			<a href="" ng-href="#/{{espacio.nombre.split(' ').join('') | lowercase}} ">			
				<md-icon md-svg-src="app/assets/img/admin/vivienda/{{espacio.tipoEspacio.nombre | lowercase}}.svg" class="size-100" ></md-icon>
				<md-grid-tile-footer><h3>{{espacio.nombre}}</h3></md-grid-tile-footer> 		
			 </a> 
		</md-grid-tile>
		
		
		
	</md-grid-list>
	
	
	
	
	
	
	<!-- 
	<md-grid-list md-cols="1" md-cols-sm="2" md-cols-md="3"
		md-cols-gt-md="6" md-row-height-gt-md="1:1" md-row-height="4:3"
		md-gutter="8px" md-gutter-gt-sm="4px"> 
				
		
		<md-grid-tile md-rowspan="1"
			md-colspan="1" md-colspan-sm="1" md-colspan-xs="1"
			style="background: #A60D0D">			
			<a href="http://www.google.com.co">			
				<md-icon md-svg-src="app/assets/img/admin/vivienda/oven.svg" class="size-100" ></md-icon>
				<md-grid-tile-footer><h3>COCINA</h3></md-grid-tile-footer> 		
			 </a> 
		</md-grid-tile>
		
		<md-grid-tile md-rowspan="1"
			md-colspan="1" md-colspan-sm="1" md-colspan-xs="1"
			style="background: #593D08">			
			<a href="http://www.google.com.co">			
				<md-icon md-svg-src="app/assets/img/admin/vivienda/bathroom.svg" class="size-100" ></md-icon>
				<md-grid-tile-footer><h3>BAÑO</h3></md-grid-tile-footer> 		
			 </a> 
		</md-grid-tile>
		
		<md-grid-tile md-rowspan="1"
			md-colspan="1" md-colspan-sm="1" md-colspan-xs="1"
			style="background: #BFAF8F">			
			<a href="http://www.google.com.co">			
				<md-icon md-svg-src="app/assets/img/admin/vivienda/dining-room.svg" class="size-100" ></md-icon>
				<md-grid-tile-footer><h3>COMEDOR</h3></md-grid-tile-footer> 		
			 </a> 
		</md-grid-tile>
		
		<md-grid-tile md-rowspan="1"
			md-colspan="1" md-colspan-sm="1" md-colspan-xs="1"
			style="background: #2345A6">			
			<a href="http://www.google.com.co">			
				<md-icon md-svg-src="app/assets/img/admin/vivienda/living room.svg" class="size-100" ></md-icon>
				<md-grid-tile-footer><h3>SALA</h3></md-grid-tile-footer> 		
			 </a> 
		</md-grid-tile>
		
		<md-grid-tile md-rowspan="1"
			md-colspan="1" md-colspan-sm="1" md-colspan-xs="1"
			style="background: #AED3F2">			
			<a href="#habitacion1">			
				<md-icon md-svg-src="app/assets/img/admin/vivienda/bedroom.svg" class="size-100" ></md-icon> 
				<md-grid-tile-footer><h3>HABITACION 1</h3></md-grid-tile-footer> 		
			 </a> 
		</md-grid-tile>
		
		<md-grid-tile md-rowspan="1"
			md-colspan="1" md-colspan-sm="1" md-colspan-xs="1"
			style="background: #A60D0D">			
			<a href="http://www.google.com.co">			
				<md-icon md-svg-src="app/assets/img/admin/vivienda/bedroom.svg" class="size-100" ></md-icon>
				<md-grid-tile-footer><h3>HABITACION 2</h3></md-grid-tile-footer> 		
			 </a> 
		</md-grid-tile>
		
		<md-grid-tile md-rowspan="1"
			md-colspan="1" md-colspan-sm="1" md-colspan-xs="1"
			style="background: #A60D0D">			
			<a href="http://www.google.com.co">			
				<md-icon md-svg-src="app/assets/img/admin/vivienda/garden.svg" class="size-100" ></md-icon>
				<md-grid-tile-footer><h3>JARDIN</h3></md-grid-tile-footer> 		
			 </a> 
		</md-grid-tile>
		
		
		
		
		<md-grid-tile md-rowspan="1"
			md-colspan="1" md-colspan-sm="1" md-colspan-xs="1"
			style="background: #ff8a80">			
			<a href="http://www.google.com.co">			
				<md-icon md-svg-src="app/assets/img/admin/vivienda/washing-machine.svg" class="size-100" ></md-icon> 
				<md-grid-tile-footer><h3>LAVADERO</h3></md-grid-tile-footer> 		
			 </a> 
		</md-grid-tile>
		
		<md-grid-tile md-rowspan="1"
			md-colspan="1" md-colspan-sm="1" md-colspan-xs="1"
			style="background: #ff80ab">			
			<a href="http://www.google.com.co">			
				<md-icon md-svg-src="app/assets/img/admin/vivienda/house.svg" class="size-100" ></md-icon> 
				<md-grid-tile-footer><h3>OTRO ESPACIO</h3></md-grid-tile-footer> 		
			 </a> 
		</md-grid-tile>
		
		<md-grid-tile md-rowspan="1"
			md-colspan="1" md-colspan-sm="1" md-colspan-xs="1"
			style="background: #b9f6ca">			
			<a href="http://www.google.com.co">			
				<md-icon md-svg-src="app/assets/img/admin/vivienda/house.svg" class="size-100" ></md-icon>
				<md-grid-tile-footer><h3>OTRO ESPACIO</h3></md-grid-tile-footer> 		
			 </a> 
		</md-grid-tile>
				 
		<md-grid-tile md-rowspan="1"
			md-colspan="1" md-colspan-sm="1" md-colspan-xs="1"
			style="background: #ffff8d">			
			<a href="http://www.google.com.co">		
				<md-icon md-svg-src="app/assets/img/admin/vivienda/garage.svg" class="size-100" ></md-icon> 
				<md-grid-tile-footer><h3>GARAGE</h3></md-grid-tile-footer> 		
			 </a> 
		</md-grid-tile>	 
		 
		 
	</md-grid-list>
	-->
</div>

