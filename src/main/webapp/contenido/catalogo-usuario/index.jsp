<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Gestionar Usuarios</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-usuario.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlAgregar" value="/catalogo-usuario/new"
		includeContext="true" />
	<h1>Gestionar Usuario</h1>
	<s:actionmessage id="algo" theme="jquery" />
	<table id="tblUsuario">
		<thead>
			<tr>
				<th>Login</th>
				<th>Nombre</th>
				<th>Apellido Paterno</th>
				<th>Apellido Materno</th>
				<th>Perfil</th>
				<th>Dirección</th>
				<th>Activo</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="list">
				<tr>
					<td>${login}</td>
					<td>${nombre}</td>
					<td>${apPat}</td>
					<td>${apMat}</td>
					<td>${perfilUsuario.nombre}</td>
					<td>${area.nombre}</td>
					<td><s:if test="activado">Activado</s:if> <s:else>Desactivado</s:else></td>
					<td><a
						href="${pageContext.request.contextPath}/catalogo-usuario/${idUsuario}/edit"><img
							height="20" width="20" src="images/buttons/editar.png" /></a> <a
						href="${pageContext.request.contextPath}/catalogo-usuario/${idUsuario}/deleteConfirm"><img
							height="20" width="20" src="images/buttons/eliminar.png" /></a> <a
						href="${pageContext.request.contextPath}/catalogo-usuario/${idUsuario}/view"><img
							height="20" width="20" src="images/buttons/ver.png" /></a></td>
				</tr>
			</s:iterator>
		</tbody>
		<label id="selium"></label>
	</table>
	<s:if
		test="%{
		(#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario==@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@DIRECTORGENERAL 
		and list.isEmpty) 
		or 
		#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario!=@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@DIRECTORGENERAL}">
		<p>
			<sj:a id="btnAgregar" button="true" href="#"
				onclick="location.href='%{urlAgregar}'">Agregar</sj:a>
		</p>
	</s:if>
</body>
	</html>
</jsp:root>
