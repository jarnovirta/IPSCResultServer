<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
	</head>
	<body>
		<div id="wrap">
			<div class="container">
				<c:url var="baseUrl" value="/" />
				<ol class="breadcrumb breadcrumb-arrow">
					<li><a href="/">Home</a></li>
					<li class="active"><span>Login</span></li>
				</ol>
				<c:if test="${loginError == true }">
					<div class="alert alert-danger" role="alert">
  						Login failed!
					</div>
				</c:if>
				<div class="page-header">
					<h1>Login</h1>
				</div>
				<br><br>
				<form action="${pageContext.request.contextPath}/login" method="POST">
					<label for="userName">User name: </label>
					<br>
					<input name="username" type="text" id="username" style="width: 15em; max-width: 100%" />
					<br><br>
					<label for="password">Password: </label>
					<br>
					<input name="password" type="password" id="password" style="width: 15em; max-width: 100%" />
					<br><br><br>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input class="btn btn-large btn-primary" type="submit" value="Login" />
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
	</body>
</html>