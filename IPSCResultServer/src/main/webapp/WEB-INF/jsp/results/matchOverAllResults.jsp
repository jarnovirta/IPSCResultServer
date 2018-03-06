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
			<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
			<ol class="breadcrumb breadcrumb-arrow">
				<li><a href="${baseUrl }">Home</a></li>
				<li><a href="${baseUrl }match/${matchResultData.match.id}">Match</a></li>
				<li class="active"><span>Match Results</span></li>
			</ol>
			<div class="page-header">
				<h1>Match Results</h1>
			</div>
			<br><br>
			<div class="panel panel-info">
			  <div class="panel-heading">Match Information</div>
			  	<div class="panel-body">
					    <div class="pageInfoTable">
					    	<div class="pageInfoTableLeft">
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
							</table>
							</div>
							
							<div class="pageInfoTableRight"> 
						        <table>
						        	<tr>
						        		<td>
						        			<b>Showing results for division:</b>
						        		</td>
						        		<td>
						        			<c:out value="${matchResultData.division}" />
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
		        			<b>Show results for:</b>
		        		</td>
		        		<td>
		        			<select style="width: auto; max-width: 100%" id="division" name="division"
								class="form-control">
								<c:forEach var="division" items="${matchResultData.match.divisionsWithResults}">
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
								<c:url var="url" value="/match/${dataline.matchResultData.match.id}/competitor/${dataline.competitor.id}" />
								<a href="${url }">${dataline.competitor.firstName }</a> 
							</td>
							<td>
								<a href="${url }">${dataline.competitor.lastName }</a> 
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
								${fn:substring(dataline.competitor.division, 0, 1)}${pf}			
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
			<br>
		</div>
	</div>
	
	<script>
		$(document).ready(function() {
			$('#matchResultTable').DataTable( {
				paging: false,
				searching: false,
				info: false
			});
		} );
		function submitDivisionChange() {
				location.replace("${baseUrl}match/${matchResultData.match.id }/division/"+ $("select#division").val());
		}

	</script>
	<%@include file="/WEB-INF/jsp/include/loginLogoutScripts.jsp" %>
	<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
    