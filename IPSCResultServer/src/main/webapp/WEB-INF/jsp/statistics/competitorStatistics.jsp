<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTagWithDataTablesLinks.jsp" />

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
			<div style="float:right">
				<button class="btn btn-default" onclick="login()" type="button">Login</button>
			</div>
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
					        			${statistics.match.name}
					        		</td>
					        	</tr>
					        	<tr>
					        		<td>
					        			<b>Date:</b>
					        		</td>
					        		<td>
					        			<fmt:formatDate value="${statistics.match.date.time}" pattern="dd.MM.yyyy" />
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
					        			<c:out value="${statistics.division}" />
					        		</td>
					        	</tr>
					        </table>
					       	</div>
							
					    </div>
					  </div>
				</div>
		        <table class="resultsPageDropDownTable">
		        	<tr>
		        		<td>
		        			<b>Show statistics for division:</b>
		        		</td>
		        		<td>
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
		        		</td>
		        		<td>
		        			<button class="btn btn-large btn-primary" onclick="submitDivisionChange()" type="button">Show</button>
		        		</td>
		        	</tr>
		        </table>
			        
				<div class="sortTableHint"><p><i>(Hold shift to sort by several columns)</i></p></div>
			<br>
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
						<th>
							Sum of Pts
						</th>
						<th>
							Div
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="line" items="${statistics.statisticsLines}">
							<tr>
								<td align="right">
									${line.divisionRank}
								</td>
								<td>
									<c:url var="url" value="/match/${statistics.match.id}/competitor/${line.competitor.id}" />
									<a href="${url}">${line.competitor.firstName} ${line.competitor.lastName} </a>
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
									<c:url var="url" value="/match/${statistics.match.id}/division/${line.competitor.division}" />
									<a href="${url}">${fn:substring(line.competitor.division, 0, 1)}${pf} </a>		
								</td>
							</tr> 
						</c:forEach>
					</tbody>
			</table>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			$('#statisticsTable').DataTable( {
				paging: false,
				searching: false,
				info: false
			});
		} );
		function submitDivisionChange() {
			location.replace("${baseUrl}match/${statistics.match.id }/statistics/division/"+ $("select#division").val());
		}
		function login() {
			window.location.href = "${baseUrl}login";
		}
	</script>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />