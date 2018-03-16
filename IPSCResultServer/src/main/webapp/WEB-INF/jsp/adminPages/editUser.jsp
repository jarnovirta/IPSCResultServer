<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTagWithDataTablesLinks.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<body>
	<c:url var="baseUrl" value="/" />
	<div id="wrap">
		<div class="container">
			<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
			<ol class="breadcrumb breadcrumb-arrow">
				<li><a href="${baseUrl }">Home</a></li>
				<li><a href="${baseUrl }admin">Admin</a></li>
				<li class="active"><span>Add user</span></li>
			</ol>
			<div class="page-header">
				<h1>Add a User</h1>
			</div>
			<br>

			<sf:form id="editUserForm" modelAttribute="user" method="post">

				<label for="username">First name: </label>
				<br>
				<sf:input type="text" autocomplete="off" style="width: 15em; max-width: 100%" id="firstName" path="firstName" value="${user.firstName}"/> 
				<br><br>
								
				<label for="lastName">Last name: </label>
				<br>
				<sf:input type="text" autocomplete="off" style="width: 15em; max-width: 100%" id="lastName" path="lastName" value="${user.lastName}"/> 
				<br><br>
				
				<label for="email">E-mail: </label>
				<br>
				<sf:input type="text" autocomplete="off" style="width: 15em; max-width: 100%" id="email" path="email" value="${user.email}"/> 
				<br><br>
				
				<label for="email">Telephone: </label>
				<br>
				<sf:input type="text" autocomplete="off" style="width: 15em; max-width: 100%" id="phone" path="phone" value="${user.phone}"/> 
				<br><br>
				
				<label for="userName">User name: </label>
				<br>
				*<sf:input type="text" autocomplete="off" style="width: 15em; max-width: 100%" id="userName" path="username" value="${user.username}" /> 
				<br><br>
				
				<label for="password">Password: </label>
				<br>
				*<sf:input type="text" autocomplete="off" style="width: 15em; max-width: 100%" id="password" path="password" value="${user.password}" />
				<br><br>
				
				<button class="btn btn-primary" type="submit">Save user</button>
			</sf:form>
		</div>
	</div>
	
<%@include file="/WEB-INF/jsp/include/loginLogoutScripts.jsp" %>
	
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
	
				
				