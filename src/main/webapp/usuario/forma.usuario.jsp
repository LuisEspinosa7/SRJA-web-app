<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
md-radio-button {
	display: -webkit-inline-box;
	display: -moz-box;
}
</style>
<md-dialog aria-label="Formulario Usuario" ng-cloak flex="90">
<form name="frmUsuario" novalidate>
	<h1 class="template-title">Formulario Usuario</h1>
	<md-dialog-content> 
	<md-subheader class="md-warn hint" ng-if="showHints">{{MsgError}} </md-subheader>
	<div class="md-dialog-content" style="z-index:1;">
		
		
		
		<div layout-gt-xs="row">
			<md-input-container class="md-block" flex-gt-sm="25">
			<label class="md-warm">Nombre</label> 
			<input data-ng-model="usuario.nombre" name="nombre" id="nombre" required
				ng-minlength="4" ng-maxlength="50">
			<div data-ng-messages="frmUsuario.nombre.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 4 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 100 caracteres.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Apellido</label> 
			<input data-ng-model="usuario.apellido" name="apellido" id="apellido" 
				required="" ng-minlength="4" ng-maxlength="45">
			<div data-ng-messages="frmUsuario.apellido.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 4 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 12 caracteres.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Email</label> 
			<input data-ng-model="usuario.email" name="email" type="email"
				id="email" required="" ng-min-length="10" ng-maxlength="80" ng-pattern="/^.+@.+\..+$/">
			<div data-ng-messages="frmUsuario.email.$error" role="alert">
				<div data-ng-message-exp="['required', 'minlength', 'maxlength', 'pattern']">
            		El email debe tener entre 10 y 80 caracteres y ser una direccion de correo electronico valida.
          		</div>
			</div>
			</md-input-container>
				
				
			<md-datepicker class="md-block" flex-gt-sm="50" required=""
				data-ng-model="usuario.fechaNacimiento" name="fechaNacimiento" id="fechaNacimiento" required
				md-placeholder="Fecha de Nacimiento" md-max-date="maxDate">
						
			<div data-ng-messages="frmUsuario.fechaNacimiento.$error">
				<div data-ng-message="valid">La fecha no es valida.</div>
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="maxlength">Máximo 100 caracteres.</div>
			</div>
						
		</div>
				
		<div layout-gt-sm="row">
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Tipo Identificacion</label> <md-select
				data-ng-model="usuario.tipoIdentificacion.id" id="idTipoIdentificacion"
				name="idTipoIdentificacion"> <md-option
				data-ng-repeat="tipoIdentiiLista in JSONTiposIdentificacion"
				value="{{tipoIdentiiLista.id}}">{{tipoIdentiiLista.acronimo}}</md-option>
			</md-select>
			<div data-ng-messages="frmUsuario.idTipoIdentificacion.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			
			<md-input-container class="md-block" flex-gt-sm="25">
			<label class="md-warm">Identificacion</label> 
			<input data-ng-model="usuario.identificacion" name="identificacion" id="identificacion" required
				ng-minlength="4" ng-maxlength="20">
			<div data-ng-messages="frmUsuario.identificacion.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 4 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 20 caracteres.</div>
			</div>
			</md-input-container>
			
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Estado</label> <md-select data-ng-model="usuario.estado.id"
				id="idEstado" name="idEstado"> <md-option
				data-ng-repeat="estadoLista in JSONEstados" value="{{estadoLista.id}}">{{estadoLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmUsuario.idEstado.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Genero</label> <md-select data-ng-model="usuario.genero.id"
				id="idGenero" name="idGenero"> <md-option
				data-ng-repeat="generoLista in JSONGeneros" value="{{generoLista.id}}">{{generoLista.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmUsuario.idGenero.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>		
		</div>
		
	
		<div ng-show="activated" style="z-index:2;" layout="row" layout-sm="column" layout-align="space-around">
   			<md-progress-circular md-diameter="70" md-mode="indeterminate"  ></md-progress-circular>
  		</div>	
			
		
		<div layout-gt-sm="row">			
			<md-input-container class="md-block" flex-gt-sm="20">
			<label>Pais</label> <md-select
				data-ng-model="usuario.pais.id" id="idPais"
				name="idPais" data-ng-change="obtenerDepartamentos()" required>
			<md-option data-ng-repeat="paisesLista in JSONPaises"
				value="{{paisesLista.id}}">{{paisesLista.nombre}}</md-option> </md-select>
			<div data-ng-messages="frmUsuario.idPais.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="20">
			<label>Departamento</label> <md-select data-ng-model="usuario.departamento.id"
				data-ng-change="obtenerCiudades()" 
				data-ng-disabled="!JSONPaises"
				id="idDepartamento" name="idDepartamento" required> <md-option
				data-ng-repeat="departamentosList in JSONDepartamentos" value="{{departamentosList.id}}">{{departamentosList.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmUsuario.idDepartamento.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			<md-input-container class="md-block" flex-gt-sm="20">
			<label>Ciudad</label> <md-select data-ng-model="usuario.ciudad.id" 
				data-ng-disabled="!JSONDepartamentos"
				id="idCiudad" name="idCiudad" required> <md-option
				data-ng-repeat="ciudadesList in JSONCiudades" value="{{ciudadesList.id}}">{{ciudadesList.nombre}}</md-option>
			</md-select>
			<div data-ng-messages="frmUsuario.idCiudad.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>
			
			<md-input-container class="md-block" flex-gt-sm="20">
			<label class="md-warm">Telefono</label> 
			<input data-ng-model="usuario.telefono" name="telefono" id="telefono" required
				ng-minlength="8" ng-maxlength="25">
			<div data-ng-messages="frmUsuario.telefono.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 8 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 25 caracteres.</div>
			</div>
			</md-input-container>
			
			<md-input-container class="md-block" flex-gt-sm="20">
			<label class="md-warm">Direccion</label> 
			<input data-ng-model="usuario.direccion" name="direccion" id="direccion" required
				ng-minlength="6" ng-maxlength="50">
			<div data-ng-messages="frmUsuario.direccion.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 6 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 50 caracteres.</div>
			</div>
			</md-input-container>		
		</div>
		
		
		<div layout-gt-xs="row">	
		
			<md-input-container class="md-block" flex-gt-sm="20">
			<label class="md-warm">Nombre de Usuario</label> 
			<input data-ng-model="usuario.username" name="username" id="username" required
				ng-minlength="6" ng-maxlength="40">
			<div data-ng-messages="frmUsuario.username.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 6 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 50 caracteres.</div>
			</div>
			</md-input-container>
			
			<md-input-container class="md-block" flex-gt-sm="20">
			<label class="md-warm">Contraseña</label> 
			<input data-ng-model="usuario.password" type="password" name="password" id="password" required
				ng-minlength="6" ng-maxlength="20">
			<div data-ng-messages="frmUsuario.password.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 6 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 20 caracteres.</div>
			</div>
			</md-input-container>
			
			<md-input-container class="md-block" flex-gt-sm="20">
			<label class="md-warm">Confirmar Contraseña</label> 
			<input data-ng-model="usuario.passwordConfirmation" type="password" name="passwordConfirmation" id="passwordConfirmation" required
				ng-minlength="6" ng-maxlength="20" confirm-pwd="usuario.password">
			<div data-ng-messages="frmUsuario.passwordConfirmation.$error">
				<div data-ng-message="required">Campo requerido.</div>
				<div data-ng-message="minlength">Minimo 6 caracteres.</div>
				<div data-ng-message="maxlength">Máximo 40 caracteres.</div>
				<div data-ng-message="password">Contraseñas No Coinciden.</div>
			</div>
			</md-input-container>
			
			<md-input-container class="md-block" flex-gt-sm="25">
			<label>Perfil</label> <md-select data-ng-model="usuario.perfil.id"
				id="idPerfil" name="idPerfil" required> <md-option
				data-ng-repeat="perfilLista in JSONPerfiles" value="{{perfilLista.id}}">{{perfilLista.perfil.split('_')[1]}}</md-option>
			</md-select>
			<div data-ng-messages="frmUsuario.idPerfil.$error">
				<div data-ng-message="required">Campo requerido.</div>
			</div>
			</md-input-container>					
			
			
			
		</div>
	</md-dialog-content>
	<md-dialog-actions layout="row"> <span flex></span>
	<md-button class="md-raised options" ng-click="answer('ok')"
		ng-disabled="(frmUsuario.$invalid && (accion=='Adicionar')) || isProcessing ">
	{{ accion }} </md-button> 
	<md-button class="md-raised options" ng-click="cancel()">
	Cancelar </md-button> </md-dialog-actions>
</form>
</md-dialog>