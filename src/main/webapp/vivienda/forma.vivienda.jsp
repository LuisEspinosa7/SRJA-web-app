<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
md-radio-button {
	display: -webkit-inline-box;
	display: -moz-box;
}
</style>
<md-dialog aria-label="Formulario Vivienda" ng-cloak flex="90">
<form name="frmVivienda" novalidate>
	<h1 class="template-title">Formulario Vivienda</h1>
	<md-dialog-content> 
	<md-subheader class="md-warn hint" ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content" style="z-index:1;">	
		
		
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="25">
			<label class="md-warm">Codigo</label> 
			<input data-ng-model="vivienda.codigo" name="codigo" id="codigo" required
				ng-minlength="6" ng-maxlength="10">
			<div data-ng-messages="frmVivienda.codigo.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 6 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 10 caracteres.</div>
			</div>
			</md-input-container>			
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Direccion</label> 
			<input data-ng-model="vivienda.direccion" name="direccion" id="direccion" 
				required="" ng-minlength="5" ng-maxlength="50">
			<div data-ng-messages="frmVivienda.direccion.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 5 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 50 caracteres.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Barrio</label> 
			<input data-ng-model="vivienda.barrio" name="barrio" id="barrio" 
				required="" ng-min-length="6" ng-maxlength="45">
			<div data-ng-messages="frmVivienda.ancho.$error" role="alert">
				<div data-ng-message-exp="['required', 'minlength', 'maxlength']">
            		La direccion debe ser valida.
          		</div>
			</div>
			</md-input-container>			
								
		</div>
				
		<div layout-gt-sm="row">
		
			<md-input-container class="md-block" flex-gt-sm="30">
			<label>Pais</label> <md-select
				data-ng-model="vivienda.pais.id" id="idPais"
				name="idPais" data-ng-change="obtenerDepartamentos()" required>
			<md-option data-ng-repeat="paisesLista in JSONPaises"
				value="{{paisesLista.id}}">{{paisesLista.nombre}}</md-option> </md-select>
			<div data-ng-messages="frmVivienda.idPais.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="30">
			<label>Departamento</label> <md-select data-ng-model="vivienda.departamento.id"
				data-ng-change="obtenerCiudades()" 
				data-ng-disabled="!JSONPaises"
				id="idDepartamento" name="idDepartamento" required> <md-option
				data-ng-repeat="departamentosList in JSONDepartamentos" value="{{departamentosList.id}}">{{departamentosList.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmVivienda.idDepartamento.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="35">
			<label>Ciudad</label> <md-select data-ng-model="vivienda.ciudad.id" 
				data-ng-disabled="!JSONDepartamentos"
				id="idCiudad" name="idCiudad" required> <md-option
				data-ng-repeat="ciudadesList in JSONCiudades" value="{{ciudadesList.id}}">{{ciudadesList.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmVivienda.idCiudad.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>		
					
		</div>
		
	
		<div ng-show="activated" style="z-index:2;" layout="row" layout-sm="column" layout-align="space-around">
   			<md-progress-circular md-diameter="70" md-mode="indeterminate"  ></md-progress-circular>
  		</div>	
			
		
		<div layout-gt-sm="row">			
						
			<md-input-container class="md-block" flex-gt-sm="25">
			<label class="md-warm">Latitud</label> 
			<input data-ng-model="vivienda.coordenada.latitud" name="latitud" id="latitud" required
				ng-minlength="1" ng-maxlength="8">
			<div data-ng-messages="frmVivienda.latitud.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 1 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 8 caracteres.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label class="md-warm">Longitud</label> 
			<input data-ng-model="vivienda.coordenada.longitud" name="longitud" id="longitud" required
				ng-minlength="5" ng-maxlength="10">
			<div data-ng-messages="frmVivienda.longitud.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 5 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 10 caracteres.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label class="md-warm">Altitud</label> 
			<input data-ng-model="vivienda.coordenada.altitud" name="altitud" id="altitud" required
				ng-minlength="1" ng-maxlength="8">
			<div data-ng-messages="frmVivienda.altitud.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 1 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 8 caracteres.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Estado</label> <md-select data-ng-model="vivienda.estado.id"
				id="idEstado" name="idEstado" required> <md-option
				data-ng-repeat="estadoLista in JSONEstados" value="{{estadoLista.id}}">{{estadoLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmVivienda.idEstado.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>			
				
		</div>				
		
	</md-dialog-content>
	
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button class="md-raised options" ng-click="answer('ok')"
		ng-disabled="(frmVivienda.$invalid && (accion=='Adicionar')) || isProcessing ">
	{{ accion }} </md-button> 
	<md-button class="md-raised options" ng-click="cancel()">
	Cancelar </md-button> </md-dialog-actions>
</form>
</md-dialog>