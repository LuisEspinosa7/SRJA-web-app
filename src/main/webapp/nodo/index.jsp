<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div ng-cloak>
	<div id="dialogContainer" data-ng-controller="NodosController">
		<md-content class="fondoTabla" flex layout-padding>
		<div layout-gt-xs="row">
			<span flex></span>
			<md-button class="md-raised options" ng-click="editar('Adicionar')">
			<i class="fa fa-plus fa-option" aria-hidden="true"></i>
			Adicionar </md-button>
			<md-button class="md-raised options" ng-click="editar('Modificar')">
			<i class="fa fa-pencil fa-option" aria-hidden="true"></i>
			Modificar </md-button>
			<md-button class="md-raised options" ng-click="dialogEliminar($event)"> 
			<i class="fa fa-trash fa-option" aria-hidden="true"></i>
			Eliminar </md-button>
		</div>
		<table id="tblNodos" class="display responsive nowrap"
			cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Id</th>
					<th>Codigo</th>
					<th>Nombre</th>					
					<th>Estado</th>					
					<th>Tipo Nodo</th>
					<th>Espacio</th>				
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Id</th>
					<th>Codigo</th>
					<th>Nombre</th>					
					<th>Estado</th>					
					<th>Tipo Nodo</th>
					<th>Espacio</th>	
				</tr>
			</tfoot>
		</table>

		</md-content>
	</div>

</div>
