<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTagWithDataTablesLinks.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"  %>


<body>
	<c:url var="baseUrl" value="/" />

	<div id="wrap">
		<div class="container">
			<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
			<ol class="breadcrumb breadcrumb-arrow">
				<li><a href="${baseUrl }">Home</a></li>
				<li><a href="${baseUrl }match/${stageResultData.stage.match.id}">Match</a></li>
				<li class="active"><span>Stage Results</span></li>
			</ol>
		
			<div class="page-header">
				<h1>Stage Results</h1>
			</div>
			<br><br>
			
			<div class="panel panel-info">
			  <div class="panel-heading">Stage Information</div>
			  	<div class="panel-body">
					    <div class="pageInfoTable">
					    	<div class="pageInfoTableLeft">
					        <table>
					        	<tr>
					        		<td>
					        			<b>Stage name:</b>
					        		</td>
					        		<td>
					        			${stageResultData.stage.name}
					        		</td>
					        	</tr>
					        	<tr>
					        		<td>
					        			<b>Stage number:</b>
					        		</td>
					        		<td>
					        			${stageResultData.stage.stageNumber}
					        		</td>
					        	</tr>					        	
					        	<tr>
					        		<td>
					        			<b>Match:</b>
					        		</td>
					        		<td>
					        			${stageResultData.stage.match.name}
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
						        			<c:url var="url" value="/match/${stageResultData.stage.match.id}/division/${stageResultData.division}" />
											<a href="${url}"><c:out value="${stageResultData.division}" /></a>
						        		</td>
						        	</tr>
								</table>
						    </div>
					  </div>
				</div>
				</div>
		        
		        <table style="width:100%">
		        <tr>
		        <td>
		        <table class="resultsPageDropDownTable">
		        	<tr>
		        		<td>
		        			<b>Show results for:</b>
		        		</td>
		        	</tr>
		        	<tr>
		        		<td>
		        			<select style="width: auto; max-width: 100%" id="stage" name="stage"
								class="form-control">
								<c:forEach var="stage" items="${stageResultData.stage.match.stages}">
									<c:if test="${stageResultData.stage.name eq stage.name}">
										<option value="${stage.id}" selected>${stage.name}</option>
									</c:if>
									<c:if test="${stageResultData.stage.name ne stage.name}">
										<option value="${stage.id}">${stage.name}</option>
									</c:if>
								</c:forEach>
							</select>
		        		</td>
		        		
		        		<td>
		        			<select style="width: auto; max-width: 100%" id="division" name="division" class="form-control">
								<c:forEach var="division" items="${stageResultData.stage.match.divisionsWithResults}">
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
		        			<button class="btn btn-large btn-primary" onclick="submitStageListingChange()" type="button">Show</button>
		        		</td>
		        	</tr>
		        </table>
		    </td>
		    <td align="right" style="vertical-align:bottom">
				<div class="sortTableHint"><p><i>(Hold shift to sort by several columns)</i></p></div>			
			</td> 
			</tr>
			</table>
			
			<table class="table table-striped table-bordered" id="stageResultTable">
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
							Time
						</th>
						<th>
							Hit Factor
						</th>
						<th>
							Stage Points
						</th>
						<th>
							%
						</th>
						<th>
							Cat
						</th>
						<th>
							Reg
						</th>
						<th>
							Div
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="dataline" items="${stageResultData.dataLines}">
						<tr>
							<td align="right">
								${dataline.stageRank}
							</td>
							<td align="left">
								<c:url var="url" value="/match/${dataline.stageResultData.stage.match.id}/competitor/${dataline.competitor.id}" />
								<a href="${url }">${dataline.competitor.firstName }</a>
							</td>
							<td align="left">
								<a href="${url }">${dataline.competitor.lastName }</a>
							</td>
							<td align="right">
								${dataline.competitor.shooterNumber }
							</td>
							<td align="right">
								${dataline.scoreCard.points}
							</td>
							<td align="right">
								<fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${dataline.scoreCard.time }" />
							</td>
							<td align="right">
								<fmt:formatNumber type = "number" minFractionDigits = "4" maxFractionDigits = "4" value="${dataline.scoreCard.hitFactor }" />
							</td>
							<td align="right">
								<fmt:formatNumber type = "number" minFractionDigits = "4" maxFractionDigits = "4" value="${dataline.stagePoints }" />
							</td>
							<td align="right">
								<fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${dataline.stageScorePercentage }" />			
							</td>
							<td align="center">
								<c:forEach items = "${dataline.competitor.categories}" var = "category">
										${category}
								</c:forEach>
							</td>
							<td align="left">
								${dataline.competitor.country }
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
						</tr> 
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	<script>
		$(document).ready(function() {
			$('#stageResultTable').DataTable( {
				paging: false,
				searching: false,
				info: false
			});
		} );
		function submitStageListingChange() {
			location.replace("${baseUrl}match/${stageResultData.stage.match.id }/stage/" 
					+ $("select#stage").val() + "/division/"+ $("select#division").val());
		}
	</script>
	<%@include file="/WEB-INF/jsp/include/loginLogoutScripts.jsp" %>
	
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />