<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"  %>

<html>
	<head>
		<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
		<jsp:include page="/WEB-INF/jsp/include/dataTablesHeadTagLinks.jsp" />
	</head>
	<body>
		<c:url var="baseUrl" value="/" />
		<div id="wrap">
			<div class="container">
				<%@ include file="/WEB-INF/jsp/include/pageTopAdZone.jsp" %>
				<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
				<ol class="breadcrumb breadcrumb-arrow">
					<li><a href="<c:url value='/' />">Home</a></li>
					<c:url var="matchPageUrl" value="/matchMainPage">
						<c:param name="matchId" value="${match.id }" />
					</c:url>
					<li><a href="${matchPageUrl}">Match</a></li>
					<li class="active"><span>Competitor Statistics</span></li>
				</ol>
				<div class="page-header">
					<h1>Competitor Statistics</h1>
				</div>
				<br><br>
				<div class="panel panel-info">
				  <div class="panel-heading">Match Info</div>
				  <div class="panel-body">
						    <div class="pageInfoTable">
						    	<div class="pageInfoTableLeft">
						        <table>
						        	<tr>
						        		<td>
						        			<b>Match:</b>
						        		</td>
						        		<td>
						        			${match.name}
						        		</td>
						        	</tr>
						        	<tr>
						        		<td>
						        			<b>Date:</b>
						        		</td>
						        		<td>
						        			<fmt:formatDate value="${match.date.time}" pattern="dd.MM.yyyy" />
						        		</td>
						        	</tr>
								</table>
								</div>
								<div class="pageInfoTableRight">   
						       	<table>
						       		<tr>
						        		<td>
						        			<b>Showing statistics for division:</b>
						        		</td>
						        		<td>
						        			<c:choose>
						        				<c:when test="${match.status eq 'SCORING_ENDED' }">
					        						<c:url var="matchResultsUrl" value="/matchResults" >
						        						<c:param name="matchId" value="${match.id}" />
						        						<c:param name="division" value="${division}" />
						        					</c:url>
									        		<a href="${matchResultsUrl}"><c:out value="${division}" /></a>
									        	</c:when>
						        				<c:otherwise>
						        					<c:out value="${division}" />
						        				</c:otherwise>
							        		</c:choose>
						        		</td>
						        	</tr>
						        </table>
						       	</div>
						    </div>
						  </div>
					</div>
					<c:choose>
						<c:when test="${match.status eq 'CLOSED'}">
							<h3>Match is closed.</h3>
						</c:when>
						<c:when test="${match.status eq 'SCORING'}">
							<h3>Scoring has not ended. Statistics not shown.</h3>
						</c:when>
						<c:otherwise>
							<table style="width:100%">
								<tr>
									<td>
							
								        <table class="resultsPageDropDownTable">
								        	<tr>
								        		<td>
								        			<b>Show statistics for division:</b>
								        		</td>
								        		<td>
								        			<select style="width: auto; max-width: 100%" id="division" name="division"
														class="form-control">
														<c:forEach var="division" items="${match.divisionsWithResults}">
															<c:if test="${selectedDivision eq division}">
																<option value="${division}" selected><c:out value="${division}" /></option>
															</c:if>
															<c:if test="${selectedDivision ne division}">
																<option value="${division}"><c:out value="${division}" /></option>
															</c:if>
														</c:forEach>
													</select>
								        		</td>
								        		<td>
								        			<button class="btn btn-large btn-primary" onclick="submitDivisionChange()" type="button">Show</button>
								        		</td>
								        	
								        		<td>
								        		
								        		</td>
								        	</tr>
								        </table>
					        		</td>
					        		<td align="right" style="vertical-align:bottom">
										<div class="sortTableHint"><p><i>(Hold shift to sort by several columns)</i></p></div>
									</td>
								</tr>
							</table>
							
							<table class="table table-striped table-bordered" id="statisticsTable">
								<thead>
									<tr>
										<th>
											Div Pos
										</th>
										<th>
											Name
										</th>
										<th>
											CompNr
										</th>
										<th>
											Total Pts
										</th>
										<th>
											Div%
										</th>
										<th>
											Time
										</th>
										<th >
											A
										</th>
										<th>
											A%
										</th>
										<th>
											C
										</th>
										<th>
											D
										</th>
										<th>
											Miss
										</th>
										<th>
											NS
										</th>
										<th>
											Proc
										</th>
										<c:if test="${additionalPenaltiesColumn == true }">
											<th>
												Add. pen.
											</th>
										</c:if>
										<th>
											Sum of Pts
										</th>
										<th>
											Div
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="line" items="${statistics}">
											<tr>
												<td align="right">
													<c:choose>
														<c:when test="${line.competitor.disqualified == true }">
															DQ
														</c:when>
														<c:otherwise>
															${line.divisionRank}	
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:url var="competitorResultsUrl" value="/competitorResults" >
														<c:param name="matchId" value="${match.id }" />
														<c:param name="competitorId" value="${line.competitor.id }" />
													</c:url>
													<a href="${competitorResultsUrl}">${line.competitor.firstName} ${line.competitor.lastName} </a>
												</td>
												<td align="right">
													${line.competitor.shooterNumber }
												</td>
												<td align="right">
													<fmt:formatNumber type="number" minFractionDigits="4" maxFractionDigits="4" value="${line.divisionPoints }" />
												</td>
												<td align="right">
													<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${line.divisionScorePercentage }" />
												</td>
												<td align="right">
													<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${line.matchTime }" />
												</td>
												<td align="right">
													${line.aHits}
												</td>
												<td align="right">
													<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${line.aHitPercentage }" />
												</td>
												<td align="right">
													${line.cHits}						
												</td>
												<td align="right">
													${line.dHits}
												</td>
												<td align="right">
													${line.misses}
												</td>
												<td align="right">
													${line.noShootHits }
												</td>
												<td align="right">
													${line.proceduralPenalties}
												</td>
												<c:if test="${additionalPenaltiesColumn == true }">
													<td align="right">
														${line.additionalPenalties}
													</td>
												</c:if>
												<td align="right">
													${line.sumOfPoints }
												</td>
												<td align="center">
													<c:if test="${line.competitor.powerFactor eq  'MINOR'}">
														<c:set var="pf" value="-" />
													</c:if>
													<c:if test="${line.competitor.powerFactor eq  'MAJOR'}">
														<c:set var="pf" value="+" />
													</c:if>
													<c:url var="matchResultsUrl" value="matchResults">
														<c:param name="matchId" value="${match.id}" />
														<c:param name="division" value="${line.competitor.division}" />
													</c:url> 
													<%-- <a href="${url}">${fn:substring(line.competitor.division, 0, 1)}${pf} </a> --%>		
													<a href="${matchResultsUrl}">${line.competitor.division}${pf} </a>		
												</td>
											</tr> 
										</c:forEach>
									</tbody>
							</table> 
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<%@ include file="/WEB-INF/jsp/include/pageBottomAdZone.jsp" %>
	</body>
	<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
	
	<script>
		$(document).ready(function() {
			$('#statisticsTable').DataTable( {
				paging: false,
				searching: true,
				info: false
			});
		} );
		function submitDivisionChange() {
			var url = "${baseUrl}statistics?matchId=${match.id }&division=" + $("select#division").val();
			location.replace(url);
		}
	</script>
	
	<%@include file="/WEB-INF/jsp/include/loginLogoutScripts.jsp" %>
</html>

