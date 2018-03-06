<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<body>
	<c:url var="baseUrl" value="/" />
	
	<div id="wrap">
		<div class="container">
			<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
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
		
	<script>
		function submit() {
			var matchId = $("select#seletcMatch").val();
			if (matchId != null && matchId != '') {
				window.location.href = "${baseUrl}match/" + matchId;
			}
		}

	</script>
	<%@include file="/WEB-INF/jsp/include/loginLogoutScripts.jsp" %>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />