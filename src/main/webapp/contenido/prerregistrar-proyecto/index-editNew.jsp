<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Agregar Proyecto</title>
<jsp:text>
	<![CDATA[ 		
			<script src="${pageContext.request.contextPath}/scripts/Coordinador.js" type="text/javascript"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/prerregistrar-proyecto.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-core.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-more.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>
<style type="text/css" >
#pageMain, pageHMenu{
	display: block;	
	}
</style>
<h1>Preregistrar Proyecto</h1>
	<s:url id="urlCancelar" value="/prerregistrar-proyecto"
		includeContext="true" />
	<s:actionerror id="aerProyecto" theme="jquery" />
	<s:actionmessage id="algo" theme="jquery"/>
	<s:fielderror id="ferProyecto" theme="jquery" />
	<s:form action="%{#request.contextPath}/prerregistrar-proyecto"
		method="post" theme="simple" acceptcharset="UTF-8" id="frmProyecto">
		<div>
			<h1>Datos del responsable</h1>
			
			<table>
				<tr>
					<td><label>Nombre:</label></td>
					<td><s:textfield id="txtNombre" name="datosPrerregistro.nombre" /></td>
					<td><label>Apellido paterno:</label></td>
					<td><s:textfield id="txtApPat" name="datosPrerregistro.apPat" /></td>
				</tr>
				<tr>
					<td><label>Apellido materno:</label></td>
					<td><s:textfield id="txtApMat" name="datosPrerregistro.apMat" /></td>
					<td><label>RFC:</label></td>
					<td><s:textfield id="txtRFC" name="datosPrerregistro.rfc" /></td>
				</tr>

				<tr>
					<td><label>Cargo:</label></td>
					<td><s:textfield id="txtCargo" name="datosPrerregistro.cargo" /></td>
					<td><label>Dirección:</label></td>
					<td><s:select list="listAreas" listKey="idArea" listValue="nombre"
				name="datosPrerregistro.idArea" /></td>
				</tr>

				<tr>
					<td><label>Correo:</label></td>
					<td><s:textfield id="txtCorreo" name="datosPrerregistro.correo" /></td>
					<td><label>Telefono:</label></td>
					<td><s:textfield id="txtTelefono" name="datosPrerregistro.telefono" /></td>
				</tr>
			</table>
			

			<h1>Datos del proyecto</h1>
			<table>
				<tr>
					<td><label>Nombre:</label></td> 
					<td><s:textfield id="txtNombre"
							name="model.nombre" />
					</td>
					<td>
					<label>Siglas:</label>
					</td>
					<td>
					 <s:textfield id="txtSiglas"
							name="model.siglas" />
					</td>
				</tr>
				<tr>
					<td><label>Resumen:</label> 
					</td>
					<td>
					<s:textarea id="txtResumen" cols="30"
							name="model.resumen" />
					</td>
					<td><label>Objetivo General:</label> 
					</td>
					<td>
					<s:textarea cols="30"
							id="txtObjetivoGeneral" name="model.objetivoGeneral" />
					</td>
				</tr>
			</table>

			<h1>Periodo</h1>
			<table>
				<tr>
					<td>Por duracion <input type="radio" id="duracion"
						name="tipoPeriodo" value="duracion" /> Por fechas<input
						type="radio" id="fechas" name="tipoPeriodo" value="fechas" />
						Indefinido<input type="radio" id="indefinido" name="tipoPeriodo"
						value="indefinido" />
					</td>
				</tr>
			</table>

			<div id="periodoConDuracion" style="display: none">
				<table>
					<tr>
						<td>Duración</td>
						<td><s:textfield name="periodo.duracion" id="txtDuracion"
								cssClass="campoDuracion" /> <select id="comboTipoPeriodo">
								<option value="day">dias</option>
								<option value="week">semanas</option>
								<option value="month">meses</option>
								<option value="year">años</option>
						</select>
						</td>
					</tr>

					
				</table>
			</div>


			<div id="periodoSinDuracion" style="display: none">
				<table>
					<tr>
						<td>Inicio</td>
						<td><sj:datepicker name="periodo.fechaInicio"
								displayFormat="yy-mm-dd" id="dtpFechaInicio"
								cssClass="campoFecha fecha" readonly="true" /></td>


					</tr>
					<tr>
						<td>Fin</td>
						<td><sj:datepicker name="periodo.fechaFin"
								displayFormat="yy-mm-dd" id="dtpFechaFin"
								cssClass="campoFecha fecha" readonly="true"/></td>
					</tr>

				</table>
			</div>
			<div id="periodoIndefinido" style="display: none"></div>
		</div>
		<s:hidden id="banderaTipoPeriodo" name="banderaTipoPeriodo" />
		<br />

		<table align="center">
			<tr>
				<td><img id="captcha"
			src="${pageContext.request.contextPath}/simpleCaptcha.jpg"
			width="150" /></td>
			</tr>
			<tr>
				<td><s:textfield id="captchaCode" name="answer" /></td>

			</tr>
		</table>

		
<sj:a id="btnAgregar" button="true" class="botonOperacion" href="#" value="%{'Agregar'+nombreNivelSel}" onclick="capturaSubmit();">Aceptar</sj:a>
		
<!--  		<input type="button" value="Agregar ${nombreNivelSel}" id="btnAgregar" class="botonOperacion" onclick="capturaSubmit();" />-->

		<sj:a button="true" href="#"
			onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
</body>
	</html>
</jsp:root>