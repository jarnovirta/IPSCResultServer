<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 

<body>
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<h1>${match.name}</h1>
			</div>
			<br><br>
				<label for="verify">Verify list: </label>
				<br>
				<select style="width: auto; max-width: 100%" id="verify" name="verify"
					class="form-control">
					<c:forEach var="competitor" items="${competitors}">
						<option value="${competitor.id}">${competitor.lastName} ${competitor.firstName} </option>
					</c:forEach>
				</select>
				<br>
				<button class="btn btn-large btn-primary" onclick="submit()" type="button">Ok</button>
		</div>
	</div>
	<c:url var="url" value="/" />
	
	<script>
		function submit() {
				window.location.href = "${url}match/${match.id }/competitor/" + $("select#verify").val();
		}
	</script>
</body>
</html>