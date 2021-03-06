<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
	</head>
		
	<body>
		<div id="wrap">
			<div class="container">
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<%@ include file="/WEB-INF/jsp/adZones/pageTopAdZone.jsp" %>
				</c:if>
				<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
				<ol class="breadcrumb breadcrumb-arrow">
					<li><a href="<c:url value='/' />">Home</a></li>
					<c:url var="matchPageUrl" value="/matchMainPage">
						<c:param name="matchId" value="${resultData.match.practiScoreId }" />
					</c:url>
					<li><a href="${matchPageUrl}">Match</a></li>
					<li class="active"><span>Verify List</span></li>
				</ol>
				<div class="page-header">
					<h1>Competitor Results</h1>
				</div>
				<br><br>

				<%@ include file="/WEB-INF/jsp/results/competitorResults/competitorResultsPageHeader.jsp" %>	
				<br>
				<c:url var="matchAnalysisUrl" value='/matchAnalysis' >
					<c:param name="matchId" value="${resultData.match.practiScoreId }" />
					<c:param name="competitorId" value="${resultData.competitor.practiScoreId }" />
				</c:url>
				<c:choose>
					<c:when test="${resultData.match.status eq 'CLOSED'}">
						<h3>Match is closed.</h3>
					</c:when>
					<c:otherwise>
						<%@ include file="/WEB-INF/jsp/results/competitorResults/competitorResultsTable.jsp" %>	
						<br>
						<a href="${matchAnalysisUrl }">
							<button class="btn btn-large btn-primary" type="button">Match Analysis</button>
						</a>
					</c:otherwise>
				</c:choose>
				<br><br><br><br>
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<%@ include file="/WEB-INF/jsp/adZones/pageBottomAdZone.jsp" %>
				</c:if>
			</div>
		</div>
		
		<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
	</body>
</html>
