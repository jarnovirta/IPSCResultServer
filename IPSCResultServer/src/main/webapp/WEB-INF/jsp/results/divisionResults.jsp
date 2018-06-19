<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<html>
	<head>
		<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
		<jsp:include page="/WEB-INF/jsp/include/dataTablesHeadTagLinks.jsp" />
	</head>
	
	
	<body>
			<c:url var="baseUrl" value="/" />
			<div id="wrap">
				<div class="container">
					<%-- <%@ include file="/WEB-INF/jsp/include/pageTopAdZone.jsp" %> --%>
					<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
					<ol class="breadcrumb breadcrumb-arrow">
						<li><a href="<c:url value='/' />">Home</a></li>
						<c:url var="matchPageUrl" value="/matchMainPage">
							<c:param name="matchId" value="${matchResultData.match.practiScoreId}" />
						</c:url>
						<li><a href="${matchPageUrl}">Match</a></li>
						
						<li class="active"><span>Match Results</span></li>
					</ol>
					<div class="page-header">
						<h1>Match Results</h1>
					</div>
					<br><br>
					<div class="panel panel-info">
					  <div class="panel-heading">Match Information</div>
					  	<div class="panel-body">
							    <div class="oneColumnPageInfo">
							    	<table>
							        	<tr>
							        		<td>
							        			<b>Match:</b>
							        		</td>
							        		<td>
							        			<b>${matchResultData.match.name}</b>
							        		</td>
							        	</tr>
							        	<tr>
							        		<td>
							        			<b>Date:</b>
							        		</td>
							        		<td>
							        			<fmt:formatDate value="${matchResultData.match.date.time}" pattern="dd.MM.yyyy" />
							        		</td>
							        	</tr>
							        	<tr>
							        		<td>
							        			<b>Division:</b>
							        		</td>
							        		<td>
							        			<div class="form-inline">
								        			<select id="division" name="division"
															class="form-control">
															<c:forEach var="division" items="${matchResultData.match.divisionsWithResults}">
																<c:if test="${param.division eq division}">
																	<option value="${division}" selected><c:out value="${division}" /></option>
																</c:if>
																<c:if test="${param.division ne division}">
																	<option value="${division}"><c:out value="${division}" /></option>
																</c:if>
															</c:forEach>
													</select>
													<button class="btn btn-large btn-default" onclick="submitDivisionChange()" type="button">Show</button>
												</div>
							        		</td>
							        	</tr>
							        	<tr>
							        		<td />
							        		<td>
							        			
							        		</td>
							        	</tr>
									</table>
									</div>
							    </div>
							  </div>
						
						<c:choose>
						<c:when test="${matchResultData.match.status eq 'CLOSED'}">
							<h3>Match is closed.</h3>
						</c:when>
						<c:when test="${matchResultData.match.status ne 'SCORING_ENDED'}">
							<h3>Scoring has not ended. Results not shown.</h3>
						</c:when>
						<c:otherwise>
							<div class="table-responsive">
								<table class="table table-striped table-bordered" id="matchResultTable">
									<thead>
										<tr>
											<th>
												Pos.
											</th>
											<th>
												First Name
											</th>
											<th>
												Last Name
											</th>
											<th>
												CompNr
											</th>
											<th>
												Points
											</th>
											<th>
												%
											</th>
											<th>
												Division
											</th>
											<th>
												Cat
											</th>
											<th>
												Region
											</th>
											<th>
												Team
											</th>
											<th>
												Scored
											</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="dataline" items="${matchResultData.dataLines}">
											<tr>
												<td align="right">
													${dataline.rank}
												</td>
												<td>
													<c:url var="competitorResultsUrl" value="/competitorResults">
														<c:param name="matchId" value="${dataline.matchResultData.match.practiScoreId}" />
														<c:param name="competitorId" value="${dataline.competitor.practiScoreId}" />
													</c:url>
	
													<a href="${competitorResultsUrl }">${dataline.competitor.firstName }</a> 
												</td>
												<td>
													<a href="${competitorResultsUrl }">${dataline.competitor.lastName }</a> 
												</td>
												<td align="right">
													${dataline.competitor.shooterNumber}
												</td>
												<td align="right">
													<fmt:formatNumber type = "number" minFractionDigits = "4" maxFractionDigits = "4" value="${dataline.points }" />
												</td>
												<td align="right">
													<fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${dataline.scorePercentage }" />
												</td>
												<td align="center">
													<c:if test="${dataline.competitor.powerFactor eq  'MINOR'}">
														<c:set var="pf" value="-" />
													</c:if>
													<c:if test="${dataline.competitor.powerFactor eq  'MAJOR'}">
														<c:set var="pf" value="+" />
													</c:if>
													${dataline.competitor.division}${pf }
													<%-- ${fn:substring(dataline.competitor.division, 0, 1)}${pf} --%>			
												</td>
												<td align="center">
													<c:forEach items = "${dataline.competitor.categories}" var = "category">
														${category }
													</c:forEach>
												</td>
												<td>
													${dataline.competitor.country }
												</td>
												<td>
													<c:set var="team" value="${dataline.competitor.team }" />
													<c:if test="${fn:length(team) > 15 }">
														<c:set var="team" value="${fn:substring(team, 0, 15)}..." />
													</c:if>
													${team }
												</td>
												<td align="right">
													${dataline.scoredStages }
												</td>
											</tr> 
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:otherwise>
					</c:choose>
				<br>
			</div>
		</div>
		<%-- <%@ include file="/WEB-INF/jsp/include/pageBottomAdZone.jsp" %> --%>
	</body>
	
	<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
	<script>
		$(document).ready(function() {
			$('#matchResultTable').DataTable( {
				paging: false,
				searching: true,
				info: false
			});
		} );
		function submitDivisionChange() {
			var url = "${baseUrl}divisionResults?matchId=${matchResultData.match.practiScoreId }&division=" + $("select#division").val();
			location.replace(url);
		}
	
	</script>
</html>    