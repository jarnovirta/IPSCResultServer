<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />

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
			<ol class="breadcrumb breadcrumb-arrow">
				<li><a href="${baseUrl }">Home</a></li>
				<li><a href="${baseUrl }/match/${stageResultData.stage.match.id}">Match Main Page</a></li>
				<li class="active"><span>Stage Results</span></li>
			</ol>
		
			<div class="page-header">
				<h1>Results for Stage in ${stageResultData.stage.match.name}</h1>
			</div>
			<br><br>
			<div class="panel panel-info">
			  <div class="panel-heading">Stage: ${stageResultData.stage.name} / <c:out value="${stageResultData.division }"/></div>
			  	<div class="panel-body">
			  	  	<div class="verifyPageInfoTable">
					    <div class="verifyPageInfoTableLeft">
					        <table>
					        	<tr>
					        		<td>
					        			<p>Stage:</p>
					        		</td>
					        		<td>
										<p>
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
										</p>					        			
					        		</td>
					        	</tr>
					        	<tr>
					        		<td>
					        			<p>Division:</p>
					        		</td>
					        		<td>
										<p>
											<select style="width: auto; max-width: 100%" id="division" name="division"
												class="form-control">
												<c:forEach var="division" items="${stageResultData.stage.match.divisionsWithResults}">
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
					        			<button class="btn btn-large btn-primary" onclick="submitStageListingChange()" type="button">Ok</button>
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
					<th><p>#</p></th>
					<th>
						<p>Points</p>
					</th>
					<th>
						<p>Time</p>
					</th>
					<th>
						<p>Hit Factor</p>
					</th>
					<th>
						<p>Stage Points</p>
					</th>
					<th>
						<p>%</p>
					</th>
					<th>
						<p>CompNr</p>
					</th>
					<th>
						<p>Name</p>
					</th>
					<th>
						<p>Cat</p>
					</th>
					<th>
						<p>Reg</p>
					</th>
					<th>
						<p>Div</p>
					</th>
				</tr>
				<c:forEach var="dataline" items="${stageResultData.dataLines}">
					<tr>
						<td>
							<p>${dataline.stageRank}.</p>
						</td>
						<td>
							<p>${dataline.scoreCard.points}</p>
						</td>
						<td>
							<p><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${dataline.scoreCard.time }" /></p>
						</td>
						<td>
							<p><fmt:formatNumber type = "number" minFractionDigits = "4" maxFractionDigits = "4" value="${dataline.scoreCard.hitFactor }" /></p>
						</td>
						<td>
							<p><fmt:formatNumber type = "number" minFractionDigits = "4" maxFractionDigits = "4" value="${dataline.stagePoints }" /></p>
						</td>
						<td>
							<p><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${dataline.stageScorePercentage }" /></p>			
						</td>
						<td>
							<p>${dataline.competitor.shooterNumber }</p>
						</td>
						<td>
							<c:url var="url" value="/match/${dataline.stageResultData.stage.match.id}/competitor/${dataline.competitor.id}" />
							<p><a href="${url }">${dataline.competitor.lastName }, ${dataline.competitor.firstName }</a></p>
						</td>
						<td>
							<c:forEach items = "${dataline.competitor.categories}" var = "category">
								<c:if test="${category eq  'SUPER_SENIOR'}">
									<p>SS</p>
								</c:if> 
								<c:if test="${category ne  'SUPER_SENIOR'}">
									<p>${fn:substring(category, 0, 1)}</p>
								</c:if>
							</c:forEach>
						</td>
						<td>
							<p>${dataline.competitor.country }</p>
						</td>
						<td>
							<c:if test="${dataline.competitor.powerFactor eq  'MINOR'}">
								<c:set var="pf" value="-" />
							</c:if>
							<c:if test="${dataline.competitor.powerFactor eq  'MAJOR'}">
								<c:set var="pf" value="+" />
							</c:if>
							<p>${fn:substring(dataline.competitor.division, 0, 1)}${pf}</p>
						</td>
					</tr> 
				</c:forEach>
			</table>
		</div>
	</div>
	
	<script>
		function submitStageListingChange() {
			location.replace("${baseUrl}match/${stageResultData.stage.match.id }/stage/" 
					+ $("select#stage").val() + "/division/"+ $("select#division").val());
		} 
	</script>
	
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />