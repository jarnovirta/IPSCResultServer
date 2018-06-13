<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<div style="float:right">
	<security:authorize var="loggedIn" access="isAuthenticated()" />
	<c:choose>
		<c:when test="${loggedIn}">
			
			 <security:authentication property="principal.username"/>  
			 <div class="btn-group">
			 	<a style="text-decoration: none" href="<c:url value='/logout' />" method="POST" ><button class="btn btn-default" type="button">Logout</button></a>
				<!-- <button class="btn btn-default" style="margin-left: 10px" onclick="logout()" type="button">Logout</button> -->
				<a style="text-decoration: none" href="<c:url value='/' />admin"><button class="btn btn-primary" type="button">Admin page</button></a>
			 </div>
		</c:when>
		<c:otherwise>
	       <a href="<c:url value='/login' />" class="btn btn-default">Login</a>
	    </c:otherwise>
	</c:choose>
</div>