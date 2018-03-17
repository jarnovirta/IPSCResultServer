<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<body>
	<c:url var="baseUrl" value="/" />
	<div id="wrap">
		<div class="container">
			<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
			<ol class="breadcrumb breadcrumb-arrow">
				<li><a href="${baseUrl }">Home</a></li>
				<li><a href="${baseUrl }match/${resultData.match.id}">Match</a></li>
				<li class="active"><span>Verify List</span></li>
			</ol>
			<div class="page-header">
				<h1>Verify List</h1>
			</div>
			<br><br>
			
			<div class="panel panel-info">
			  <div class="panel-heading">Competitor Information</div>
			  	<div class="panel-body">
					    <div class="pageInfoTable">
					    	<div class="pageInfoTableLeft">
						        <table>
						        	<tr>
						        		<td>
						        			<b>Name:</b>
						        		</td>
						        		<td>
						        			<b>${resultData.competitor.firstName } ${resultData.competitor.lastName }</b>
						        		</td>
						        	</tr>
						        	<tr>
						        		<td>
						        			<b>Competitor #:</b>
						        		</td>
						        		<td>
						        			${resultData.competitor.shooterNumber }
						        		</td>
						        	</tr>
									<tr>
						        		<td>
						        			<b>Alias:</b>
						        		</td>
						        		<td>
						        			${resultData.competitor.ipscAlias }
						        		</td>
						        	</tr>	
						        	<tr>
						        		<td>
						        			<b>Match:</b>
						        		</td>
						        		<td>
						        			${resultData.match.name }
						        		</td>
						        	</tr>
						        	<tr>
						        		<td>
						        			<b>Match date:</b>
						        		</td>
						        		<td>
						        			<fmt:formatDate value="${resultData.match.date.time}" pattern="dd.MM.yyyy" />
						        		</td>
						        	</tr>
						        </table> 
						    </div>
					    <div class="pageInfoTableRight">   
					       	<table>
					       		<tr>
					        		<td>
					        			<b>Division:</b>
					        		</td>
					        		<td>
					        			<c:choose>
											<c:when test="${resultData.match.status eq 'SCORING_ENDED' }">
					        					<c:url var="url" value="/match/${resultData.match.id}/division/${resultData.competitor.division}" />
												<a href="${url}"><c:out value="${resultData.competitor.division }" /></a>
											</c:when>
											<c:otherwise>
												${resultData.competitor.division }
											</c:otherwise>
										</c:choose>
					        		</td>
					        	</tr>
					        	<tr>
					        		<td>
					        			<b>Power Factor:</b>
					        		</td>
					        		<td>
					        			<c:out value="${resultData.competitor.powerFactor }" />
					        		</td>
					        	</tr>
					        	<tr>
					        		<td>
					        			<b>Category:</b>
					        		</td>
					        		<td>
					        			<c:forEach items="${resultData.competitor.categories }" var="category">
					        				${category} 
					        			</c:forEach>
					        		</td>
					        	</tr>
								<tr>
					        		<td>
					        			<b>Region:</b>
					        		</td>
					        		<td>
					        			${resultData.competitor.country }
					        		</td>
					        	</tr>	
					        	<tr>
					        		<td>
					        			<b>Squad:</b>
					        		</td>
					        		<td>
					        			${resultData.competitor.squad }
					        		</td>
					        	</tr>
					        	<tr>
						        	<td>
						        		<b>Team:</b>
						        	</td>
						        	<td>
						        		${resultData.competitor.team }
						        	</td>
						        </tr>			        	
					        </table> 
					    </div>
					  </div>
				</div>
			</div>

			<br>
			<table class="table table-striped table-bordered" id="competitorResultTable">
				<thead>
					<tr>
						<th>Stage #</th>
						<th>
							Stage Name
						</th>
						<th>
							Time
						</th>
						<th>
							A
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
							Proc.
						</th>
						<th>
							Last updated
						</th>
						<c:if test="${resultData.match.status eq 'SCORING_ENDED' }">
							<th>
								Rank
							</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="stage" items="${resultData.match.stages}">
						<c:set var="scoreCard" value="${resultData.scoreCards[stage.id] }" />
							<tr>
								<td align="right">
									${scoreCard.stage.stageNumber }
								</td>
								<td>
									<c:choose>
										<c:when test="${resultData.match.status eq 'SCORING_ENDED' }">
											<c:url var="url" value="/match/${resultData.match.id}/stage/${scoreCard.stage.id}/division/${scoreCard.competitor.division }" />
											<a href="${url}">${scoreCard.stage.name}</a>
										</c:when>
										<c:otherwise>
											${scoreCard.stage.name}
										</c:otherwise>
									</c:choose>
								</td>
								<td align="right">
									<fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${scoreCard.time }" />
								</td>
								<td align="right">
									${scoreCard.aHits}
								</td>
								<td align="right">
									${scoreCard.cHits}
								</td>
								<td align="right">
									${scoreCard.dHits}						
								</td>
								<td align="right">
									${scoreCard.misses}
								</td>
								<td align="right">
									${scoreCard.noshootHits}
								</td>
								<td align="right">
									${scoreCard.proceduralPenalties }
								</td>
								<td align="right">
									<fmt:formatDate value="${scoreCard.modified.time }" pattern="dd.MM.yyyy 'at' HH:mm:ss" />
								</td>
								<c:if test="${resultData.match.status eq 'SCORING_ENDED' }">
									<td align="right">
										${scoreCard.stageRank}
									</td>
								</c:if>
							</tr> 
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<%@include file="/WEB-INF/jsp/include/loginLogoutScripts.jsp" %>

<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />