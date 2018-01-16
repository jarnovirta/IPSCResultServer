<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<body>
	<c:url var="baseUrl" value="/" />
	<div id="wrap">
		<div class="container">
			<ol class="breadcrumb breadcrumb-arrow">
				<li><a href="${baseUrl }">Home</a></li>
				<li><a href="${baseUrl }match/${statistics.match.id}">Match Main Page</a></li>
				<li class="active"><span>Competitor Statistics</span></li>
			</ol>
			<div class="page-header">
				<h1>Competitor Statistics</h1>
			</div>
			<br><br>

			<div class="panel panel-info">
			  <div class="panel-heading">Competitor Statistics for ${statistics.match.name} / <c:out value="${statistics.division }"/></div>
			  	<div class="panel-body">
			  	  	<div class="verifyPageInfoTable">
					    <div class="verifyPageInfoTableLeft">
					        <table>
					        	<tr>
					        		<td>
					        			<p>Division:</p>
					        		</td>
					        		<td>
										<p>
											<select style="width: auto; max-width: 100%" id="division" name="division"
												class="form-control">
												<c:forEach var="division" items="${statistics.match.divisionsWithResults}">
													<c:if test="${selectedDivision eq division}">
														<option value="${division}" selected><c:out value="${division}" /></option>
													</c:if>
													<c:if test="${selectedDivision ne division}">
														<option value="${division}"><c:out value="${division}" /></option>
													</c:if>
												</c:forEach>
											</select>
										</p>							        			
					        		</td>
					        	</tr>
					        	<tr>
					        		<td></td>
					        		<td>
					        			<button class="btn btn-large btn-primary" onclick="submitDivisionChange()" type="button">Ok</button>
					        		</td>
					        	</tr>
								    			        	
					        </table> 
					    </div>
					</div>
				</div>
			</div>
			<br>
			<table class="table table-striped">
				<tr>
					<th>
						<p>CompNr</p>
					</th>
					<th>
						<p>Name</p>
					</th>
					<th>
						<p>Div Rank</p>
					</th>
					<th>
						<p>Div%</p>
					</th>
					<th>
						<p>Div pts</p>
					</th>
					<th>
						<p>Time</p>
					</th>
					<th>
						<p>A</p>
					</th>
					<th>
						<p>A%</p>
					</th>
					<th>
						<p>C</p>
					</th>
					<th>
						<p>D</p>
					</th>
					<th>
						<p>Miss</p>
					</th>
					<th>
						<p>NS</p>
					</th>
					<th>
						<p>Proc</p>
					</th>
					<th>
						<p>Sum of pts</p>
					</th>
					<th>
						<p>Div</p>
					</th>
				</tr>
				
				<c:forEach var="line" items="${statistics.statisticsLines}">
						<tr>
							<td>
								<p>${line.competitor.shooterNumber }</p>
							</td>
							<td>
								<c:url var="url" value="/match/${statistics.match.id}/competitor/${line.competitor.id}" />
								<p><a href="${url}">${line.competitor.firstName} ${line.competitor.lastName} </a></p>
							</td>
							<td>
								<p>${line.divisionRank}</p>
							</td>
							<td>
								<p><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${line.divisionScorePercentage }" /></p>
							</td>
							<td>
								<p><fmt:formatNumber type="number" minFractionDigits="4" maxFractionDigits="4" value="${line.divisionPoints }" /></p>
							</td>
							<td>
								<p><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${line.matchTime }" /></p>
							</td>
							<td>
								<p>${line.aHits}</p>
							</td>
							<td>
								<p><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${line.aHitPercentage }" /></p>
							</td>
							<td>
								<p>${line.cHits}</p>						
							</td>
							<td>
								<p>${line.dHits}</p>
							</td>
							<td>
								<p>${line.misses}</p>
							</td>
							<td>
								<p>${line.noShootHits }</p>
							</td>
							<td>
								<p>${line.proceduralPenalties}</p>
							</td>
							<td>
								<p>${line.sumOfPoints }</p>
							</td>
							<td>
								<c:if test="${line.competitor.powerFactor eq  'MINOR'}">
									<c:set var="pf" value="-" />
								</c:if>
								<c:if test="${line.competitor.powerFactor eq  'MAJOR'}">
									<c:set var="pf" value="+" />
								</c:if>
								<c:url var="url" value="/match/${statistics.match.id}/division/${line.competitor.division}" />
								<p><a href="${url}">${fn:substring(line.competitor.division, 0, 1)}${pf} </a></p>		
							</td>

						</tr> 
				</c:forEach>
			</table>
		</div>
	</div>
	<script>
			function submitDivisionChange() {
			location.replace("${baseUrl}match/${statistics.match.id }/statistics/division/"+ $("select#division").val());
		} 
	</script>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />