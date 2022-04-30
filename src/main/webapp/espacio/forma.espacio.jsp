<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
md-radio-button {
	display: -webkit-inline-box;
	display: -moz-box;
}
</style>
<md-dialog aria-label="Formulario Espacio" ng-cloak flex="90">
<form name="frmEspacio" novalidate>
	<h1 class="template-title">Formulario Espacio</h1>
	<md-dialog-content> 
	<md-subheader class="md-warn hint" ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content" style="z-index:1;">
		
		
		
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="25">
			<label class="md-warm">Nombre</label> 
			<input data-ng-model="espacio.nombre" name="nombre" id="nombre" required
				ng-minlength="4" ng-maxlength="50">
			<div data-ng-messages="frmEspacio.nombre.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 4 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 50 caracteres.</div>
			</div>
			</md-input-container>			
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Descripcion</label> 
			<input data-ng-model="espacio.descripcion" name="descripcion" id="descripcion" 
				required="" ng-minlength="4" ng-maxlength="100">
			<div data-ng-messages="frmEspacio.descripcion.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 4 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 100 caracteres.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Ancho</label> 
			<input data-ng-model="espacio.ancho" name="ancho" id="ancho" 
				required="" ng-min-length="10" ng-maxlength="5">
			<div data-ng-messages="frmEspacio.ancho.$error" role="alert">
				<div data-ng-message-exp="['required', 'minlength', 'maxlength']">
            		El valor debe ser proporcionado en metros, hasta 10 caracteres.
          		</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Largo</label> 
			<input data-ng-model="espacio.largo" name="largo" id="largo" 
				required="" ng-min-length="10" ng-maxlength="5">
			<div data-ng-messages="frmEspacio.largo.$error" role="alert">
				<div data-ng-message-exp="['required', 'minlength', 'maxlength']">
            		El valor debe ser proporcionado en metros, hasta 10 caracteres.
          		</div>
			</div>
			</md-input-container>
								
		</div>
				
		<div layout-gt-sm="row">
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Tipo Espacio</label> <md-select
				data-ng-model="espacio.tipoEspacio.id" id="idTipoEspacio"
				name="idTipoEspacio" required> <md-option
				data-ng-repeat="tipoEspLista in JSONTiposEspacio"
				value="{{tipoEspLista.id}}">{{tipoEspLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmEspacio.idTipoEspacio.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>		
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Estado</label> <md-select data-ng-model="espacio.estado.id"
				id="idEstado" name="idEstado" required> <md-option
				data-ng-repeat="estadoLista in JSONEstados" value="{{estadoLista.id}}">{{estadoLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmEspacio.idEstado.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
					
		</div>
		
	
		<div ng-show="activated" style="z-index:2;" layout="row" layout-sm="column" layout-align="space-around">
   			<md-progress-circular md-diameter="70" md-mode="indeterminate"  ></md-progress-circular>
  		</div>	
			
		
		<div layout-gt-sm="row">			
						
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Vivienda</label> <md-select data-ng-model="espacio.vivienda.id"
				id="idVivienda" name="idVivienda" required> <md-option
				data-ng-repeat="viviendaLista in JSONViviendas" value="{{viviendaLista.id}}">{{viviendaLista.direccion}}</md-option>
			</md-select>
			<div data-ng-messages="frmEspacio.idVivienda.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>			
				
		</div>				
		
	</md-dialog-content>
	
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button class="md-raised options" ng-click="answer('ok')"
		ng-disabled="(frmEspacio.$invalid && (accion=='Adicionar')) || isProcessing ">
	{{ accion }} </md-button> 
	<md-button class="md-raised options" ng-click="cancel()">
	Cancelar </md-button> </md-dialog-actions>
</form>
</md-dialog>