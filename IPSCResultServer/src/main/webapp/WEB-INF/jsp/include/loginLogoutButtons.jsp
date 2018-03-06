<div style="float:right">
	<security:authorize var="loggedIn" access="isAuthenticated()" />
	<sf:form action="${pageContext.request.contextPath}/logout" method="POST" id="logoutForm">
			</sf:form>
	<c:choose>
		<c:when test="${loggedIn}">
			
			 <security:authentication property="principal.username" />  <button class="btn btn-default" style="margin-left: 10px" onclick="logout()" type="button">Logout</button>
		</c:when>
		<c:otherwise>
	       <button class="btn btn-default" onclick="login()" type="button">Login</button>
	    </c:otherwise>
	</c:choose>
</div>