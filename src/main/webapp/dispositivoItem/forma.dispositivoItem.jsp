<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
md-radio-button {
	display: -webkit-inline-box;
	display: -moz-box;
}
</style>
<md-dialog aria-label="Formulario Dispositivo Item" ng-cloak flex="90">
<form name="frmDispositivoItem" novalidate>
	<h1 class="template-title">Formulario Dispositivo Item</h1>
	<md-dialog-content> 
	<md-subheader class="md-warn hint" ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content" style="z-index:1;">
		
		
		
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="50">
			<label class="md-warm">Codigo</label> 
			<input data-ng-model="dispositivoItem.codigo" name="codigo" id="codigo" required
				ng-minlength="4" ng-maxlength="50">
			<div data-ng-messages="frmDispositivoItem.codigo.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 4 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 50 caracteres.</div>
			</div>
			</md-input-container>			
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Estado</label> <md-select data-ng-model="dispositivoItem.estado.id"
				id="idEstado" name="idEstado" required> <md-option
				data-ng-repeat="estadoLista in JSONEstados" value="{{estadoLista.id}}">{{estadoLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmDispositivoItem.idEstado.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>			
								
		</div>
		
		<div ng-show="activated" style="z-index:2;" layout="row" layout-sm="column" layout-align="space-around">
   			<md-progress-circular md-diameter="70" md-mode="indeterminate"  ></md-progress-circular>
  		</div>
				
		<div layout-gt-sm="row">
			<md-input-container class="md-block" flex-gt-sm="40">
			<label>Dispositivo</label> <md-select
				data-ng-model="dispositivoItem.dispositivo.id" id="idDispositivo"
				name="idDispositivo" required> <md-option
				data-ng-repeat="dispLista in JSONDispositivos"
				value="{{dispLista.id}}">{{dispLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmDispositivoItem.idDispositivo.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			
			<md-input-container class="md-block" flex-gt-sm="60">
			<label>Nodo</label> <md-select
				data-ng-model="dispositivoItem.nodo.id" id="idNodo"
				name="idNodo" required> <md-option
				data-ng-repeat="nodoLista in JSONNodos"
				value="{{nodoLista.id}}">{{nodoLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmDispositivoItem.idNodo.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>				
			 		
		</div>
		
	</md-dialog-content>
	
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button class="md-raised options" ng-click="answer('ok')"
		ng-disabled="(frmDispositivoItem.$invalid && (accion=='Adicionar')) || isProcessing ">
	{{ accion }} </md-button> 
	<md-button class="md-raised options" ng-click="cancel()">
	Cancelar </md-button> </md-dialog-actions>
</form>
</md-dialog>