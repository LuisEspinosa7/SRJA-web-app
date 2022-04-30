<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div ng-cloak>
	<div id="dialogContainer" data-ng-controller="AsignaturaController">
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
		<table id="tblAsignaturas" class="display responsive nowrap"
			cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Codigo</th>
					<th>Nombre</th>
					<th>Nombre Corto</th>
					<th>Nombre Impresión</th>
					<th>UAA</th>
					<th>NBC</th>
					<th>Caracter</th>
					<th>Núcleo</th>
					<th>Trabajo Presencial</th>
					<th>Trabajo Independiente</th>
					<th>Estado</th>
					<th>Publicar</th>
					<th>tipoUaa</th>
					<th>codigoUaa</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>Codigo</th>
					<th>Nombre</th>
					<th>Nombre Corto</th>
					<th>Nombre Impresión</th>
					<th>UAA</th>
					<th>NBC</th>
					<th>Caracter</th>
					<th>Núcleo</th>
					<th>Trabajo Presencial</th>
					<th>Trabajo Independiente</th>
					<th>Estado</th>
					<th>Publicar</th>
					<th>tipoUaa</th>
					<th>codigoUaa</th>
				</tr>
			</tfoot>
		</table>

		</md-content>
	</div>

</div>
