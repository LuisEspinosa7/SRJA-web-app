<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
md-radio-button {
	display: -webkit-inline-box;
	display: -moz-box;
}
</style>
<md-dialog aria-label="Formulario Asignatura" ng-cloak flex="90">
<form name="frmAsignatura" novalidate>
	<h1 class="template-title">Formulario Asignatura</h1>
	<md-dialog-content> 
	<md-subheader class="md-warn hint" ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content">
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="50">
			<label class="md-warm">Nombre</label> 
			<input data-ng-model="asignatura.nombre" name="nombre" id="nombre" required
				ng-maxlength="100">
			<div data-ng-messages="frmAsignatura.nombre.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="maxlength">Máximo 100 caracteres.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Nombre Impresión</label> <input
				data-ng-model="asignatura.nombreImpresion" name="nombreImpresion"
				id="nombreImpresion" required="" ng-maxlength="12">
			<div data-ng-messages="frmAsignatura.nombreImpresion.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="maxlength">Máximo 12 caracteres.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Acronimo</label> <input data-ng-model="asignatura.nombreCorto"
				name="nombreCorto" id="nombreCorto" disabled required="">
			<div data-ng-messages="frmAsignatura.nombreCorto.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
		</div>
		<div layout-gt-sm="row">
			<md-input-container class="md-block" flex-gt-sm="20">
			<label><b>Unidad Responsable</b></label> </md-input-container>
			<md-input-container class="md-block" flex-gt-sm="30">
			<label>Tipo UAA</label> <md-select
				data-ng-model="asignatura.uaaTipo.codigo" id="codigoTipoUaa"
				name="codigoTipoUaa" data-ng-change="obtenerUaa()" required>
			<md-option data-ng-repeat="uaaTipos in JSONUaaTipo"
				value="{{uaaTipos.codigo}}">{{uaaTipos.nombre}}</md-option> </md-select>
			<div data-ng-messages="frmAsignatura.codigoTipoUaa.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="50">
			<label>UAA</label> <md-select data-ng-model="asignatura.uaa.codigo"
				data-ng-change="acronimo()" data-ng-disabled="!JSONUaa"
				id="codigoUaa" name="codigoUaa" required> <md-option
				data-ng-repeat="uaaList in JSONUaa" value="{{uaaList.codigo}}">{{uaaList.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmAsignatura.codigoUaa.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
		</div>
		<div layout-gt-sm="row">
			<md-input-container class="md-block" flex-gt-sm="">
			<label>Caracter</label> <md-select
				data-ng-model="asignatura.caracter.codigo" id="codigoCaracter"
				name="codigoCaracter"> <md-option
				data-ng-repeat="caracterLista in JSONCaracteres"
				value="{{caracterLista.codigo}}">{{caracterLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmAsignatura.codigoCaracter.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="">
			<label>NBC</label> <md-select data-ng-model="asignatura.nbc.codigo"
				id="codigoNbc" name="codigoNbc"> <md-option
				data-ng-repeat="nbcLista in JSONNbc" value="{{nbcLista.codigo}}">{{nbcLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmAsignatura.codigoNbc.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="">
			<label>Componente</label> <md-select
				data-ng-model="asignatura.componente.codigoComponente"
				data-ng-change="acronimo()" id="codigoComponente" name="codigo">
			<md-option data-ng-repeat="componenteLista in JSONComponente"
				value="{{componenteLista.codigoComponente}}">{{componenteLista.nombreComponente}}</md-option>
			</md-select>
			<div data-ng-messages="frmAsignatura.codigoComponente.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="">
			<label>Nucleo</label> <md-select
				data-ng-model="asignatura.nucleo.codigo" data-ng-change="acronimo()"
				id="codigoNucleo" name="codigoNucleo"> <md-option
				data-ng-repeat="nucleoLista in JSONNucleo"
				value="{{nucleoLista.codigo}}">{{nucleoLista.nombre}}</md-option> </md-select>
			<div data-ng-messages="frmAsignatura.codigoNucleo.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
		</div>
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Trabajo Presencial(Horas)</label> <input type="number"
				data-ng-model="asignatura.trabajoPresencial"
				name="trabajoPresencial" id="trabajoPresencial" required=""
				data-ng-pattern="/^[0-9]{1,3}$/">
			<div data-ng-messages="frmAsignatura.trabajoPresencial.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="number">Solo números.</div>
				<div data-ng-message="pattern">Rango de número no permitido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Trabajo Independiente(Horas)</label> <input type="number"
				data-ng-model="asignatura.trabajoIndependiente"
				name="trabajoIndependiente" id="trabajoIndependiente" required=""
				ng-pattern="/^[0-9]{1,3}$/">
			<div data-ng-messages="frmAsignatura.trabajoIndependiente.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="number">Solo números.</div>
				<div data-ng-message="pattern">Rango de número no permitido.</div>
			</div>
			</md-input-container>
			<!-- <md-radio-group ng-model="asignatura.publicar" class="md-block"
				flex-gt-sm="" required> <label>Publicar</label> <md-radio-button
				value="1">Si</md-radio-button> <md-radio-button value="0">No</md-radio-button>
			<div data-ng-messages="frmAsignatura.publicar.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-radio-group>
			<md-radio-group ng-model="asignatura.estado" class="md-block"
				flex-gt-sm="" required> <label>Estado</label> <md-radio-button
				value="1">Si</md-radio-button> <md-radio-button value="0">No</md-radio-button>
			<div data-ng-messages="frmAsignatura.publicar.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-radio-group> -->
		</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button class="md-raised options" ng-click="answer('ok')"
		ng-disabled="frmAsignatura.$invalid && (accion=='Adicionar')">
	{{ accion }} </md-button> <md-button class="md-raised options" ng-click="cancel()">
	Cancelar </md-button> </md-dialog-actions>
</form>
</md-dialog>