<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />



<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 

<body>
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<h1>Result service</h1>
			</div>
			<br><br>
			
			<form action="results/overall/test" method="get">
				<label for="selectedMatch">Choose a match: </label>
				<br>
				<select style="width: auto; max-width: 100%" id="selectedMatch" name="match"
					class="form-control">
					<c:forEach var="match" items="${matches}">
						<option value="${match.id}">${match.name}</option>
					</c:forEach>
				</select>
				<br>
				<button class="btn btn-large btn-primary" type="submit">Ok</button>
			</form>
		</div>
	</div>
</body>
</html>


