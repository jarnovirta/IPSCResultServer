<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
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
						<c:param name="matchId" value="${matchId }" />
					</c:url>
					<li><a href="${matchPageUrl}">Match</a></li>
					<li class="active"><span>Analysis</span></li>
				</ol>
				
				<div id="loader"></div>
				
				<div id="contentDiv" class="animate-bottom" style="display:none">
					<%@ include file="/WEB-INF/jsp/matchAnalysis/matchAnalysisPageHeader.jsp" %>
					
	<!-- MONITOR / TABLET LAYOUT -->
					<div class="hidden-xs">
							<div class="row">
								<div class="col-xs-6">
									<h3 align='center'><span id="analysisColumnCompetitorName"></span></h3>
									<br>
									<h4>Accuracy</h4>
									<div id="competitorAccuracyChart-large" align='center'></div>
									<hr>
									<h4>Hits</h4>
									<div class="table-responsive">
										<table id="competitorHitsTable-large" class="table table-striped table-bordered hitsTable competitorHitsTable" width="100%"></table>
									</div>
									<hr>
									<h4>Stage Results</h4>
									<div class="table-responsive">
										<table id="competitorStageResultsTable-large" class="table table-striped table-bordered stageResultsTable competitorStageResultsTable" width="100%"></table>
									</div>
									<hr>
									<h4>Error Cost Analysis</h4>
									<div class="table-responsive">
										<table id="competitorErrorCostAnalysisTable-large" class="table table-striped table-bordered errorCostAnalysisTable competitorErrorCostAnalysisTable" width="100%"></table>
									</div>
									
								</div>
								<div class="col-xs-6" style="border-left: 1px solid #ccc">
									<h3 align='center'><span id="analysisColumnCompareToCompetitorName"></span></h3>
									<br>
									<h4>Accuracy</h4>
									<div id="compareToCompetitorAccuracyChart-large" align='center'></div>
									<hr>
									<h4>Hits</h4>
									<div class="table-responsive">
										<table id="compareToCompetitorHitsTable-large" class="table table-striped table-bordered hitsTable compareToCompetitorHitsTable" width="100%"></table>
									</div>
									<hr>									
									<h4>Stage Results</h4>
									<div class="table-responsive">
										<table id="compareToCompetitorStageResultsTable-large" class="table table-striped table-bordered stageResultsTable compareToCompetitorStageResultsTable" width="100%"></table>
									</div>
									<hr>									
									<h4>Error Cost Analysis</h4>
									<div class="table-responsive">
										<table id="compareToCompetitorErrorCostAnalysisTable-large" class="table table-striped table-bordered errorCostAnalysisTable compareToCompetitorErrorCostAnalysisTable" width="100%"></table>
									</div>
								</div>
							</div>
							<br>
							<hr>
							<div class="row">
								<div class="col-xs-6">
									<h4 style="text-align:center">Percent by Stage <span id="percentByStageDivision"></span></h4>
									<div id="percentByStageChart-large" align='center'></div>
								</div>
								<div class="col-xs-6" style="border-left: 1px solid #ccc">
									<h4 style="text-align:center">Time by Stage</h4>
									<div id="timeByStageChart-large" align='center'></div>
								</div>
							</div>
						</div>
						<br><br><br>
	<!-- SMART PHONE LAYOUT -->					
						<div class="hidden-sm hidden-md hidden-lg">
							  <ul class="nav nav-tabs">
							    <li class="active"><a data-toggle="tab" href="#accuracyTab">Accuracy</a></li>
							    <li><a data-toggle="tab" href="#hitsTab">Hits</a></li>
							    <li><a data-toggle="tab" href="#stageResultsTab">Stage Results</a></li>
							    <li><a data-toggle="tab" href="#errorCostTab">Error Cost</a></li>
							    <li><a data-toggle="tab" href="#stagePercentTab">Stage %</a></li>
							    <li><a data-toggle="tab" href="#stageTimesTab">Stage Times</a></li>
							  </ul>	
							<div class="tab-content">
								
							<!-- ACCURACY CHART TAB -->
   							<div id="accuracyTab" class="tab-pane fade in active">
    							<ul class="nav nav-pills">
								  <li class="active"><a href="#competitorAccuracyPill" data-toggle="pill"><span class="competitorPillName"></span></a></li>
								  <li><a href="#compareToCompetitorAccuracyPill" data-toggle="pill"><span class="compareToCompetitorPillName"></span></a></li>
								</ul>
								<div class="tab-content">
									<div id="competitorAccuracyPill" class="tab-pane active">
	    								<div id="competitorAccuracyChart-small" align='center'></div>
	    							</div>
	    							<div id="compareToCompetitorAccuracyPill" class="tab-pane">
	    								<div id="compareToCompetitorAccuracyChart-small" align='center'></div>
	    							</div>
								</div>
   							</div>
							
							<!-- HITS TABLE TAB -->
   							<div id="hitsTab" class="tab-pane fade in">
    							<ul class="nav nav-pills">
								  <li class="active"><a href="#competitorHitsPill" data-toggle="pill"><span class="competitorPillName"></span></a></li>
								  <li><a href="#compareToCompetitorHitsPill" data-toggle="pill"><span class="compareToCompetitorPillName"></span></a></li>
								</ul>
								<div class="tab-content">
									<div id="competitorHitsPill" class="tab-pane active">
	    								<div class="table-responsive">
											<table id="competitorHitsTable-small" class="table table-striped table-bordered hitsTable competitorHitsTable" width="100%"></table>
										</div>
	    							</div>
	    							<div id="compareToCompetitorHitsPill" class="tab-pane">
	    								<div class="table-responsive">
											<table id="compareToCompetitorHitsTable-small" class="table table-striped table-bordered hitsTable compareToCompetitorHitsTable" width="100%"></table>
										</div>
	    							</div>
								</div>   							
   							</div>
   							
   							<!-- STAGE RESULTS TAB -->
   							<div id="stageResultsTab" class="tab-pane fade in">
    							<ul class="nav nav-pills">
								  <li class="active"><a href="#competitorStageResultsPill" data-toggle="pill"><span class="competitorPillName"></span></a></li>
								  <li><a href="#compareToCompetitorStageResultsPill" data-toggle="pill"><span class="compareToCompetitorPillName"></span></a></li>
								</ul>
								<div class="tab-content">
									<div id="competitorStageResultsPill" class="tab-pane active">
		   								<div class="table-responsive">
											<table id="competitorStageResultsTable-small" class="table table-striped table-bordered stageResultsTable competitorStageResultsTable" width="100%"></table>
										</div>
	    							</div>
	    							<div id="compareToCompetitorStageResultsPill" class="tab-pane">
		   								<div class="table-responsive">
											<table id="compareToCompetitorStageResultsTable-small" class="table table-striped table-bordered stageResultsTable compareToCompetitorStageResultsTable" width="100%"></table>
										</div>
	    							</div>
								</div>    							
   							</div>
   							
   							<!-- ERROR COST TAB -->
   							<div id="errorCostTab" class="tab-pane fade in">
    							<ul class="nav nav-pills">
								  <li class="active"><a href="#competitorErrorCostPill" data-toggle="pill"><span class="competitorPillName"></span></a></li>
								  <li><a href="#compareToCompetitorErrorCostPill" data-toggle="pill"><span class="compareToCompetitorPillName"></span></a></li>
								</ul>
								<div class="tab-content">
									<div id="competitorErrorCostPill" class="tab-pane active">
		   								<div class="table-responsive">
											<table id="competitorErrorCostAnalysisTable-small" class="table table-striped table-bordered errorCostAnalysisTable competitorErrorCostAnalysisTable" width="100%"></table>
										</div>
			    							</div>
	    							<div id="compareToCompetitorErrorCostPill" class="tab-pane">
		   								<div class="table-responsive">
											<table id="compareToCompetitorErrorCostAnalysisTable-small" class="table table-striped table-bordered errorCostAnalysisTable compareToCompetitorErrorCostAnalysisTable" width="100%"></table>
										</div>
	    							</div>
								</div>     							
   							</div>
   							
   							<!-- STAGE PERCENT CHART TAB -->
   							<div id="stagePercentTab" class="tab-pane fade in">
   								<br>
   								<div id="percentByStageChart-small" align='center'></div>
   							</div>
   							
   							<!-- STAGE TIMES CHART TAB -->
     						<div id="stageTimesTab" class="tab-pane fade in">
     							<br>
     							<div id="testWrapper">
     								<div id="timeByStageChart-small" align='center'></div>
     							</div>
    						</div>
    					</div>
					</div>
				</div>
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<%@ include file="/WEB-INF/jsp/adZones/pageBottomAdZone.jsp" %>
				</c:if>
			</div>
		</div>
	
		<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
	</body>
</html>

