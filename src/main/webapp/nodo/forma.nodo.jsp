<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
md-radio-button {
	display: -webkit-inline-box;
	display: -moz-box;
}
</style>
<md-dialog aria-label="Formulario Nodo" ng-cloak flex="90">
<form name="frmNodo" novalidate>
	<h1 class="template-title">Formulario Nodo</h1>
	<md-dialog-content> 
	<md-subheader class="md-warn hint" ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content" style="z-index:1;">
		
		
		
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="25">
			<label class="md-warm">Codigo</label> 
			<input data-ng-model="nodo.codigo" name="codigo" id="codigo" required
				ng-minlength="6" ng-maxlength="10">
			<div data-ng-messages="frmNodo.codigo.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 6 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 10 caracteres.</div>
			</div>
			</md-input-container>	
			<md-input-container class="md-block" flex-gt-sm="50">
			<label class="md-warm">Nombre</label> 
			<input data-ng-model="nodo.nombre" name="nombre" id="nombre" required
				ng-minlength="4" ng-maxlength="50">
			<div data-ng-messages="frmNodo.nombre.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 4 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 50 caracteres.</div>
			</div>
			</md-input-container>			
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Estado</label> <md-select data-ng-model="nodo.estado.id"
				id="idEstado" name="idEstado" required> <md-option
				data-ng-repeat="estadoLista in JSONEstados" value="{{estadoLista.id}}">{{estadoLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmNodo.idEstado.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>			
								
		</div>
		
		<div ng-show="activated" style="z-index:2;" layout="row" layout-sm="column" layout-align="space-around">
   			<md-progress-circular md-diameter="70" md-mode="indeterminate"  ></md-progress-circular>
  		</div>
				
		<div layout-gt-sm="row">
			<md-input-container class="md-block" flex-gt-sm="40">
			<label>Tipo Nodo</label> <md-select
				data-ng-model="nodo.tipoNodo.id" id="idTipoNodo"
				name="idTipoNodo" required> <md-option
				data-ng-repeat="tipoNodLista in JSONTiposNodo"
				value="{{tipoNodLista.id}}">{{tipoNodLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmNodo.idTipoNodo.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			
			<md-input-container class="md-block" flex-gt-sm="60">
			<label>Espacio</label> <md-select
				data-ng-model="nodo.espacio.id" id="idEspacio"
				name="idEspacio" required> <md-option
				data-ng-repeat="espacioLista in JSONEspacios"
				value="{{espacioLista.id}}">{{espacioLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmNodo.idEspacio.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>				
			 		
		</div>
		
	</md-dialog-content>
	
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button class="md-raised options" ng-click="answer('ok')"
		ng-disabled="(frmNodo.$invalid && (accion=='Adicionar')) || isProcessing ">
	{{ accion }} </md-button> 
	<md-button class="md-raised options" ng-click="cancel()">
	Cancelar </md-button> </md-dialog-actions>
</form>
</md-dialog>