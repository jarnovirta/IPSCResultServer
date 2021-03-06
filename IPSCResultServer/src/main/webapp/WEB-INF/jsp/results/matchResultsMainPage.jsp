<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- *** REMOVE AFTER AD TESTS: -->
<!-- *** 						--> 
<%@ page session="true" %>

<html>
	<head>
		<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
		<jsp:include page="/WEB-INF/jsp/include/dataTablesHeadTagLinks.jsp" />
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
					<li class="active"><span>Match</span></li>
				</ol>
				<div class="page-header">
					<h1>${match.name}</h1>
				</div>

				<br>
				<c:choose>
					<c:when test="${match.status eq 'CLOSED'}">
						<h3>Match is closed.</h3>
					</c:when>
					<c:otherwise>
						<c:if test="${match.status eq 'SCORING_ENDED' }">
							<c:url var="statisticsUrl" value="/statistics">
								<c:param name="matchId" value="${match.practiScoreId }" />
							</c:url>
							<a href="${statisticsUrl }">
								<button class="btn btn-large btn-primary" type="button">Statistics</button>
							</a>
							<br><br><br>
							<h4><b>Match Results:</b></h4>
							<c:set var="divisionsSize" value="${fn:length(match.divisionsWithResults) }" />
							<c:forEach var="division" items="${match.divisionsWithResults }" varStatus="status">
								<c:url var="divisionResultsUrl" value="/divisionResults">
									<c:param name="matchId" value="${match.practiScoreId }" />
									<c:param name="division" value="${division }" />
								</c:url>
								<a href="${divisionResultsUrl}">
									${division }</a>
								<c:if test="${status.count < divisionsSize }">
									|
								</c:if>
							</c:forEach>
							<hr />
						</c:if>
						<h4><b>Stage Results:</b></h4>
						<div style="max-width: 750px">
							<table class="table table-striped table-bordered" id="stageTable">
								<thead>
									<tr>
										<th>
											Stage
										</th>
										<th>
											Division
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="stage" items="${match.stages}">
										<tr>
											<td style="width: 50%">
												${stage.stageNumber }. ${stage.name}
											</td>
											<td style="width: 50%">
												<c:set var="divisionsSize" value="${fn:length(match.divisionsWithResults) }" />
												<c:forEach var="division" items="${match.divisionsWithResults }" varStatus="status">
													<c:url var="stageResultsUrl" value="/stageResults">
														<c:param name="matchId" value="${match.practiScoreId }" />
														<c:param name="stageId" value="${stage.practiScoreId }" />
														<c:param name="division" value="${division }" />
													</c:url>
													<a href="${stageResultsUrl}">
														${division}</a>
													<c:if test="${status.count < divisionsSize }">
														|
													</c:if>
												</c:forEach>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<hr />
		
						<h4><b>Verify List:</b></h4>
						<div style="max-width: 750px">
							<table class="table table-striped table-bordered" id="competitorTable">
								<thead>
									<tr>
										<th>
											Competitor (${fn:length(match.competitors)})
										</th>
										<th>
											Division
										</th>
										<th>
											Category
										</th>
										<th>
											Country
										</th>
										<th>
											Team
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="competitor" items="${match.competitors}">
										<tr>
											<td style="width: 50%">
												<c:url var="competitorResultsUrl" value="/competitorResults">
													<c:param name="matchId" value="${match.practiScoreId}" />
													<c:param name="competitorId" value="${competitor.practiScoreId}" />
												</c:url>
												<a href="${competitorResultsUrl}">
													${competitor.lastName }, ${competitor.firstName} 
												</a>
											</td>
											<td align="center">
												<c:if test="${competitor.powerFactor eq  'MINOR'}">
													<c:set var="pf" value="-" />
												</c:if>
												<c:if test="${competitor.powerFactor eq  'MAJOR'}">
													<c:set var="pf" value="+" />
												</c:if>
												${competitor.division }${pf }
											</td>
											<td align="center">
												<c:forEach items = "${competitor.categories}" var = "category">
													${category}
												</c:forEach>
											</td>
											<td>
		        								${competitor.country }
		        							</td>
											<td>
		        								${competitor.team }
		        							</td>
										</tr>		
									</c:forEach>
								</tbody>
							</table>
						</div>
						<hr />
					</c:otherwise>
				</c:choose>
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<%@ include file="/WEB-INF/jsp/adZones/pageBottomAdZone.jsp" %>
				</c:if>
			</div>
		</div>
		
		<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
		<script>
			$(document).ready(function() {
				$('#stageTable').DataTable( {
					paging: false,
					searching: false,
					sort: false,
					info: false
				});
				$('#competitorTable').DataTable( {
					paging: false,
					searching: true,
					sort: true,
					info: false
				});
			} );
		</script>
	</body>
</html>
	

