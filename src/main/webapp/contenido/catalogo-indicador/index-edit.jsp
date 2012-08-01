<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Modificar Indicador Fisico</title>
</head>
<body>
	<H1>Modificar Indicador Fisico</H1>
	<s:actionerror id="caca" theme="jquery" />
	<s:fielderror id="mierda" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-indicador/%{idIndicador}"
		method="post" theme="simple" acceptcharset="UTF-8" id="caca">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<div class="title">Datos Generales</div>
		<table>
			<tr>
				<td><label>Descripción del indicador</label></td>
				<td><s:textfield id="txtDescripcion" name="model.descripcion"
						disabled="false" /></td>
			</tr>

			<tr>
				<td><label>Meta:</label></td>
				<td><s:textfield id="txtMeta" name="model.meta"
						disabled="false" /></td>
			</tr>


			<tr>
				<td><label>Tipo de unidad: </label></td>
				<td><s:select id="comboTipoUnidad" list="listTipoUnidad"
						listValue="nombre" listKey="idTipoUnidad" name="tipoUnidadSel"
						headerValue="- - Seleccione un tipo de unidad - -" headerKey="-1"
						onchange="$.publish('reloadsecondlist');"
						value="%{model.unidad.idUnidad}" />
				</td>
			</tr>

			<tr>
				<td><label>Unidad: </label>
				</td>
				<td><s:url id="urlUnidad"
						action="catalogo-indicador!traerUnidades" includeContext="true" />
					<sj:select href="%{urlUnidad}" id="comboUnidad" formIds="caca"
						reloadTopics="reloadsecondlist" name="model.idUnidad"
						list="listUnidad" listValue="nombre" listKey="idUnidad"
						emptyOption="true" headerKey="-1"
						headerValue="Seleccione una unidad" />
				</td>
			</tr>



			<tr>
				<td><label>Peso:</label></td>
				<td><s:textfield id="txtPeso" name="model.peso"
						disabled="false" /></td>
			</tr>
			<tr>
				<td colspan="2"><sj:submit id="btnAceptar" value="Aceptar"
						button="true" /> <sj:a id="btnRegresar"
						button="true"  href="#"
						onclick="history.go(-1)">Cancelar</sj:a></td>
			</tr>
		</table>
	</s:form>
</body>
	</html>
</jsp:root>