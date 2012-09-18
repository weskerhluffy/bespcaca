<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Editar Programa ${nombre}</title>

<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/mensaje-ingles.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/Gerente.js" type="text/javascript"></script>
			
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-core.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-more.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/catalogo-programa-editar.js" ></script>								
		 ]]>
</jsp:text>

</head>
<body>
	<h1>Editar Programa</h1>
	<s:url id="urlCancelar" value="/catalogo-programa"
		includeContext="true" />
	<s:actionerror id="saeTipoAviso" theme="jquery" />
	<s:fielderror id="sfeTipoAviso" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-programa/%{idPrograma}"
		method="post" theme="simple" acceptcharset="UTF-8" id="frmPrograma">

		<s:hidden id="hdnMethod" name="_method" value="put" />

		<div class="title">Editar Programa</div>
		<div class="section">Datos del Programa</div>
		<table>
			<s:if
				test="%{not #session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@HAY_SECTORIAL] or (#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@HAY_SECTORIAL]  and #session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@ES_SECTORIAL] )}">
				<tr>
					<td><label>Programa Sectorial</label></td>
					<td><s:checkbox id="stSectorial" name="model.sectorial" /></td>
				</tr>
			</s:if>
			<tr>
				<td><label>Siglas:</label></td>
				<td><s:textfield id="txtSiglas" name="model.siglas"
						disabled="true" /></td>
			</tr>
			<tr>
				<td><label>Nombre del Programa:</label></td>
				<td><s:textfield id="txtNombre" name="model.nombre"
						disabled="true" /></td>
			</tr>
			<tr>
				<td><label>Descripción:</label></td>
				<td><s:textarea id="txtResumen" name="model.resumen" cols="70"
						rows="5" maxlength="255" /></td>
			</tr>
			<tr>

				<td colspan="2"><label>Responsable:</label> <br /> <s:if
						test="model.estructuras.size lt 1">
						<s:select
							list="#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@GERENTES_ASIGNABLES]"
							listKey="idUsuario" listValue="nombre" name="model.idUsuario" />
					</s:if> <s:else>
						<s:property value="model.usuario.nombre" />
					</s:else></td>
			</tr>
		</table>

		<table border="0">
			<tr>
				<td>Período</td>
			</tr>
			<tr>
				<td>Por duración <input type="radio" id="duracion"
					name="tipoPeriodo" value="duracion" />Por fechas<input
					type="radio" id="fechas" name="tipoPeriodo" value="fechas" />
					Indefinido <input type="radio" id="indefinido" name="tipoPeriodo"
					value="indefinido" /></td>
			</tr>
		</table>
		<div id="periodoConDuracion" style="display: none">
			<table>
				<tr>
					<td>Duración</td>
					<td><s:textfield name="model.periodo.duracion"
							id="txtDuracion" size="5" cssClass="campoDuracion" /> <select
						id="comboTipoPeriodo">
							<option value="day">dias</option>
							<option value="week">semanas</option>
							<option value="month">meses</option>
							<option value="year">años</option>
					</select></td>
				</tr>

			</table>
		</div>
		<div id="periodoSinDuracion" style="display: none">
			<table>
				<tr>
					<td>Inicio</td>
					<td><sj:datepicker name="model.periodo.fechaInicio"
							displayFormat="yy-mm-dd" id="dtpFechaInicio"
							cssClass="campoFechas" readonly="true" /></td>
				</tr>
				<tr>
					<td>Fin</td>
					<td><sj:datepicker name="model.periodo.fechaFin"
							displayFormat="yy-mm-dd" id="dtpFechaFin" cssClass="campoFechas"
							readonly="true" /></td>
				</tr>
			</table>
		</div>
		<s:hidden id="banderaTipoPeriodo" name="banderaTipoPeriodo" />
		<sj:submit id="btnAceptar" value="Aceptar" button="true" />
		<sj:a id="btnCancelar" button="true" href="#"
			onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>

	</s:form>
</body>
	</html>
</jsp:root>