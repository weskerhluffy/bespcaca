<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>Inicio de Sesion</title>
	<jsp:text>
		<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/login.js" type="text/javascript"></script>
		 ]]>
	</jsp:text>
	</head>
<body>
	<!-- div id="pageContent" align="left"-->
		<center>
			<h1>Acceso al sistema</h1>
			<s:form id="doLoginFrm" name="doLoginFrm"
				action="%{#request.contextPath}/login" method="POST">
                <s:actionmessage id="algo" theme="jquery"/>
				<table>
					<div class="title">Acceso al sistema</div>
					<tr>
						<td><s:textfield name="userId" label="Usuario registrado" />
						</td>
					</tr>

					<tr>
						<td><s:password name="password" label="Contraseña" />
						</td>
					</tr>
					<tr>
						<td><a href="recuperar-password">¿Ha olvidado su
								contraseña?</a></td>
						<td><s:submit value="Aceptar" /></td>
					</tr>
				</table>
				<s:fielderror />
				<s:actionerror />
			</s:form>
		</center>
	
</body>

</html>
</jsp:root>