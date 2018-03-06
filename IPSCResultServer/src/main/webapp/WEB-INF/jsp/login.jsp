<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 

<body>
	<div id="wrap">
		<div class="container">
			<c:url var="baseUrl" value="/" />
			<ol class="breadcrumb breadcrumb-arrow">
				<li><a href="${baseUrl }">Home</a></li>
				<li class="active"><span>Login</span></li>
			</ol>

			<div class="page-header">
				<h1>Login</h1>
			</div>
			<br><br>
			
			<form action="${pageContext.request.contextPath}/authenticate">
				<label for="userName">User name: </label>
				<br>
				<input name="j_username" type="text" id="j_username" style="width: 15em; max-width: 100%" />
				<br><br>
				<label for="password">Password: </label>
				<br>
				<input name="j_password" type="password" style="width: 15em; max-width: 100%" />
				<br><br><br>
				<input class="btn btn-large btn-primary" type="submit" value="Login" />
			</form>
		</div>
	</div>

<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />