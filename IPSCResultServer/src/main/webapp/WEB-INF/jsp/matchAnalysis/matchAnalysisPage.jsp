<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"  %>

<html>
	<head>
		<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
		<%-- <jsp:include page="/WEB-INF/jsp/include/dataTablesHeadTagLinks.jsp" /> --%>
		
		<jsp:include page="/WEB-INF/jsp/matchAnalysis/chartLoadScript.jsp" />
		
	</head>
	<body>
		<c:url var="baseUrl" value="/" />
		<div id="wrap">
			<div class="container">
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<%@ include file="/WEB-INF/jsp/adZones/pageTopAdZone.jsp" %>
				</c:if>
				<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
				<ol class="breadcrumb breadcrumb-arrow">
					<li><a href="<c:url value='/' />">Home</a></li>
					<c:url var="matchPageUrl" value="/matchMainPage">
						<%-- <c:param name="matchId" value="${match.practiScoreId }" /> --%>
					</c:url>
					<li><a href="${matchPageUrl}">Match</a></li>
					<li class="active"><span>Analysis</span></li>
				</ol>
				<div class="page-header">
					<h1>Match Analysis</h1>
				</div>
				
				<div id="competitorAccuracyChart" style="width: 700px; height: 389px; display: block; margin: 0 auto"></div>
				
				<%@ include file="/WEB-INF/jsp/matchAnalysis/hitsTable.jsp" %>
				<br><br><br><br>
				
				<div id="percentByStageChart" style="width: 700px; height: 500px; display: block; margin: 0 auto"></div>
				<br><br><br>
				<div id="timeByStageChart" style="width: 700px; height: 500px; display: block; margin: 0 auto"></div>
				
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<%@ include file="/WEB-INF/jsp/adZones/pageBottomAdZone.jsp" %>
				</c:if>
			</div>
		</div>
	</body>
	<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
	
	<script>
		/* $(document).ready(function() {
			$('#statisticsTable').DataTable( {
				paging: false,
				searching: true,
				info: false, 
			});
		} );
		function submitDivisionChange() {
			var url = "${baseUrl}statistics?matchId=${match.practiScoreId }&division=" + $("select#division").val();
			location.replace(url);
		} */
	</script>

</html>

