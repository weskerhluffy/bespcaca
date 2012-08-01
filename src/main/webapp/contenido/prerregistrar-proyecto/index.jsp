<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>Gestión de Proyectos</title>
	<jsp:text>
		<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-proyecto.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/Coordinador.js" type="text/javascript"></script>
		 ]]>
	</jsp:text>
	</head>
	<body>
	<h1>Gestionar Proyecto Preregistrado</h1>
	<table id="tblProyecto">
		<thead>
			<tr>
				<th>Siglas</th>
				<th>Nombre</th>
				<th>Resumen</th>
				<th>Responsable</th>
				<th>Objetivo General</th>
				<th>Datos Pre-registro</th>
				<th>Duración</th>
				<th>Inicio</th>
				<th>Fin</th>
				<th>Estado</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="list">
				<tr>
					<td>${siglas}</td>
					<td>${nombre}</td>
					<td>${resumen}</td>
					<td>${responsable.nombre} ${responsable.apPat} ${responsable.apMat}</td>
					<td>${objetivoGeneral}</td>
					<td>${datosPreregistro}</td>
					<td>${periodo.duracion} días</td>
					<td>${periodo.fechaInicio}</td>
					<td>${periodo.fechaFin}</td>
					<td>${estado.nombre}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<a href="${pageContext.request.contextPath}/catalogo-proyecto/new"
		id="lnkNuevoProyecto">Nuevo Proyecto</a>
	</body>
	</html>
</jsp:root>
