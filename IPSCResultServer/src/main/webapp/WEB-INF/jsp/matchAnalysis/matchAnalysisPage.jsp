<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"  %>

<html>
	<head>
		<!-- Common page head tag contents -->
		<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
		<jsp:include page="/WEB-INF/jsp/include/dataTablesHeadTagLinks.jsp" />
		
		<!-- Google charts scripts -->
		<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
		<script>
			<%@ include file="/resources/js/chartLoadScript.js" %>
		</script>
	</head>
	<body>
		<c:url var="baseUrl" value="/" />
		<div id="wrap">
			<div class="container">
			
				<!-- Ad zone -->
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<%@ include file="/WEB-INF/jsp/adZones/pageTopAdZone.jsp" %>
				</c:if>
				
				<!-- Page top bar -->
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
				
				<%@ include file="/WEB-INF/jsp/matchAnalysis/matchAnalysisPageHeader.jsp" %>
							
				<div id="competitorAccuracyChart"></div>
				
				<%@ include file="/WEB-INF/jsp/matchAnalysis/hitsTable.jsp" %>
				<br><br>
				
				<h3>Stage Results</h3>
				<div class="table-responsive">
					<table id="stageResultsTable" class="table table-striped table-bordered" width="100%"></table>
				</div>
				<br><br>
				
				<%@ include file="/WEB-INF/jsp/results/competitorResults/errorCostAnalysisTable.jsp" %>
				<br><br>
				
				<div id="percentByStageChart"></div>
				<br><br><br>
				<div id="timeByStageChart"></div>
				
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

