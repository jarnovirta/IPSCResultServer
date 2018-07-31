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
			<%@ include file="/resources/js/matchAnalysis.js" %>
		</script>
	</head>
	<body>
		<c:url var="baseUrl" value="/" />
		<div id="wrap">
			<div class="container" style="width: 1500px">
			
				<!-- Ad zone -->
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<%@ include file="/WEB-INF/jsp/adZones/pageTopAdZone.jsp" %>
				</c:if>
				
				<!-- Page top bar -->
				<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
				<ol class="breadcrumb breadcrumb-arrow">
					<li><a href="<c:url value='/' />">Home</a></li>
					<c:url var="matchPageUrl" value="/matchMainPage">
						<c:param name="matchId" value="${matchId }" />
					</c:url>
					<li><a href="${matchPageUrl}">Match</a></li>
					<li class="active"><span>Analysis</span></li>
				</ol>
				
				<div id="loader"></div>
				
				<div id="contentDiv" class="animate-bottom">
					<%@ include file="/WEB-INF/jsp/matchAnalysis/matchAnalysisPageHeader.jsp" %>
					<div class="row">
						<div class="col-xs-6">
						
							<h3 align='center'><span id="analysisColumnCompetitorName"></span></h3>
							<br>
							<h4>Accuracy</h4>
							<div id="competitorAccuracyChart"></div>
							
							<h4>Hits</h4>
							<div class="table-responsive">
								<table id="competitorHitsTable" class="table table-striped table-bordered" width="99%"></table>
							</div>
							<br><br>
							
							<h4>Stage Results</h4>
							<div class="table-responsive">
								<table id="competitorStageResultsTable" class="table table-striped table-bordered" width="99%"></table>
							</div>
							<br><br>
							
							<h4>Error Cost Analysis</h4>
							<div class="table-responsive">
								<table id="competitorErrorCostAnalysisTable" class="table table-striped table-bordered" width="99%"></table>
							</div>
						</div>
						<div class="col-xs-6" style="border-left: 1px solid #ccc">
							<h3 align='center'><span id="analysisColumnCompareToCompetitorName"></span></h3>
							<br>
							<h4>Accuracy</h4>
							<div id="compareToCompetitorAccuracyChart"></div>
							
							<h4>Hits</h4>
							<div class="table-responsive">
								<table id="compareToCompetitorHitsTable" class="table table-striped table-bordered" width="99%"></table>
							</div>
							<br><br>
							
							<h4>Stage Results</h4>
							<div class="table-responsive">
								<table id="compareToCompetitorStageResultsTable" class="table table-striped table-bordered" width="99%"></table>
							</div>
							<br><br>
							
							<h4>Error Cost Analysis</h4>
							<div class="table-responsive">
								<table id="compareToCompetitorErrorCostAnalysisTable" class="table table-striped table-bordered" width="99%"></table>
							</div>
						</div>
					</div>
					<br>
					<hr>
					<br>
					<div class="col-xs-6">
						<h4>Percent by Stage <span id="percentByStageDivision"</span></h4>
						<br>
						<div id="percentByStageChart" align='center'></div>
					</div>
					<div class="col-xs-6">
						<h4>Time by Stage</h4>
						<br>
						<div id="timeByStageChart" align='center'></div>
						<br><br><br><br>
					</div>
				</div>
				
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<%@ include file="/WEB-INF/jsp/adZones/pageBottomAdZone.jsp" %>
				</c:if>
			</div>
		</div>
	</body>
	<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
</html>

