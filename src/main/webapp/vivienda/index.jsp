<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div ng-cloak>
	<div id="dialogContainer" data-ng-controller="ViviendaController">
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
		<table id="tblViviendas" class="display responsive nowrap"
			cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>ID</th>
					<th>CODIGO</th>
					<th>DIRECCION</th>					
					<th>COORDENADA</th>
					<th>BARRIO</th>
					<th>CIUDAD</th>					
					<th>ESTADO</th>					
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>ID</th>
					<th>CODIGO</th>
					<th>DIRECCION</th>					
					<th>COORDENADA</th>
					<th>BARRIO</th>
					<th>CIUDAD</th>					
					<th>ESTADO</th>
				</tr>
			</tfoot>
		</table>

		</md-content>
	</div>

</div>