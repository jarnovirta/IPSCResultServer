<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />



<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 

<body>
	<div id="wrap">
		<div class="container">
			<div style="float:right">
				<security:authorize var="loggedIn" access="isAuthenticated()" />
				<c:choose>
					<c:when test="${loggedIn}">
					
						<form:form action="${pageContext.request.contextPath}/logout" method="POST" id="logoutForm">
						    
						</form:form>
						 <security:authentication property="principal.username" />  <button class="btn btn-default" style="margin-left: 10px" onclick="logout()" type="button">Logout</button>
					</c:when>
					<c:otherwise>
				       <button class="btn btn-default" onclick="login()" type="button">Login</button>
				    </c:otherwise>
				</c:choose>
			</div>
			
			<ol class="breadcrumb breadcrumb-arrow">
				<li class="active"><span>Home</span></li>
			</ol>

			<div class="page-header">
				<h1>Result Service</h1>
			</div>
			<br><br>
				<label for="seletcMatch">Choose a match: </label>
				<br>
				<select style="width: auto; max-width: 100%" id="seletcMatch" class="form-control">
					<c:forEach var="match" items="${matches}">
						<option value="${match.id}">${match.name}</option>
					</c:forEach>
				</select>
				<br>
				<button class="btn btn-large btn-primary" onclick="submit()" type="button">Show</button>
			
		</div>
	</div>
	<c:url var="url" value="/" />
	<script>
		function submit() {
			var matchId = $("select#seletcMatch").val();
			if (matchId != null && matchId != '') {
				window.location.href = "${url}match/" + matchId;
			}
		}
		function login() {
			window.location.href = "${url}login";
		}
		function logout() {
			document.getElementById("logoutForm").submit();
		}
	</script>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />