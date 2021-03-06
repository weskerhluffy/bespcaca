<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Nueva Dirección</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
	<h1>Agregar Dirección</h1>
	<s:url id="urlCancelar" value="/catalogo-area" includeContext="true" />
	<s:actionerror id="saeArea" theme="jquery" />
	<s:fielderror id="sfeArea" theme="jquery" />

	<s:form action="%{#request.contextPath}/catalogo-area" method="post"
		theme="simple" acceptcharset="UTF-8">
		<table>
			<tr>
				<td><label>Nombre:</label></td>
				<td><s:textfield id="Nombre" name="model.nombre" /></td>
			</tr>
			<tr>
				<td><label>Siglas:</label></td>
				<td><s:textfield id="Siglas" name="model.siglas" maxlength="10" /></td>
			</tr>
			<tr>
				<td><label>Descripción:</label></td>
				<td><s:textarea rows="3" cols="70" id="Descripcion"
			name="model.descripcion" /></td>
			</tr>
		</table>
		<sj:submit id="btnAceptar" value="Aceptar" button="true"
		/>
		<sj:a id="btnCancelar" button="true" 
			href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
	<center></center>
</body>
	</html>
</jsp:root>