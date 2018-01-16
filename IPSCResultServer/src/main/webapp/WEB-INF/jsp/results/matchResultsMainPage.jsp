<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 

<body>
	<c:url var="baseUrl" value="/" />

	<div id="wrap">
		<div class="container">
		    <ol class="breadcrumb breadcrumb-arrow">
				<li><a href="${baseUrl }">Home</a></li>
				<li class="active"><span>Match Main Page</span></li>
			</ol>
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
				<button class="btn btn-large btn-primary" onclick="submitCompetitor()" type="button">Ok</button>
			<br><br>
			<label for="stageList">Match results: </label>
				<br>
				<select style="width: auto; max-width: 100%" id="division" name="division"
					class="form-control">
					<c:forEach var="division" items="${match.divisionsWithResults}">
						<option value="${division}"><c:out value="${division}" /></option>
					</c:forEach>
				</select>
				<br>
				<button class="btn btn-large btn-primary" onclick="submitDivision()" type="button">Ok</button>
				
			<br><br>
			<label for="stageList">Stage results: </label>
				<br>
				<select style="width: auto; max-width: 100%" id="stage" name="stage"
					class="form-control">
					<c:forEach var="stage" items="${match.stages}">
						<option value="${stage.id}">${stage.name}</option>
					</c:forEach>
				</select>
				<br>
				<button class="btn btn-large btn-primary" onclick="submitStage()" type="button">Ok</button>
				
				<br><br>
				<button class="btn btn-large btn-primary" onclick="showStatistics()" type="button">Statistics</button>
		</div>
	</div>
	
	<script>
		function submitCompetitor() {
			window.location.href = "${baseUrl}match/${match.id }/competitor/" + $("select#verify").val();
		}
		function submitDivision() {
			window.location.href = "${baseUrl}match/${match.id }/division/" + $("select#division").val();
		}
		function submitStage() {
			window.location.href = "${baseUrl}match/${match.id }/stage/" + $("select#stage").val();
		}
		function showStatistics() {
			window.location.href = "${baseUrl}match/${match.id }/statistics";
		}
	</script>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />