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
		<div id="wrap">
			<div class="container">
				<%-- <%@ include file="/WEB-INF/jsp/include/pageTopAdZone.jsp" %> --%>
				<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
				<ol class="breadcrumb breadcrumb-arrow">
					<li><a href="<c:url value='/' />">Home</a></li>
					<c:url var="matchPageUrl" value="/matchMainPage">
						<c:param name="matchId" value="${stageResultData.stage.match.practiScoreId }" />
					</c:url>
					<li><a href="${matchPageUrl}">Match</a></li>
					<li class="active"><span>Stage Results</span></li>
				</ol>
			
				<div class="page-header">
					<h1>Stage Results</h1>
				</div>
				<br><br>
				
				<div class="panel panel-info">
				  <div class="panel-heading">Stage Information</div>
				  	<div class="panel-body">
						        <table class="oneColumnPageInfo">
						        	<tr>
						        		<td>
						        			<b>Match:</b>
						        		</td>
						        		<td>
						        			<b>${stageResultData.stage.match.name}</b>
						        		</td>
						        	</tr>
						        	<tr>
						        		<td>
						        			<b>Stage:</b>
						        		</td>
						        		<td>
						        			<select id="stage" name="stage" class="form-control">
												<c:forEach var="stage" items="${stageResultData.stage.match.stages}">
													<c:set var="stageListItem" value="${stage.stageNumber } - ${stage.name }" />
													<c:if test="${stageResultData.stage.name eq stage.name}">
														<option value="${stage.practiScoreId}" selected>${stageListItem}</option>
													</c:if>
													<c:if test="${stageResultData.stage.name ne stage.name}">
														<option value="${stage.practiScoreId}">${stageListItem}</option>
													</c:if>
												</c:forEach>
											</select>
						        		</td>
						        	</tr>
						        	<tr>
						        		<td>
						        			<b>Division:</b>
						        		</td>
						        		<td>
						        			<div class="form-inline">
								        		<select id="division" name="division" class="form-control">
													<c:forEach var="division" items="${stageResultData.stage.match.divisionsWithResults}">
														<c:if test="${param.division eq division}">
															<option value="${division}" selected><c:out value="${division}" /></option>
														</c:if>
														<c:if test="${param.division ne division}">
															<option value="${division}"><c:out value="${division}" /></option>
														</c:if>
													</c:forEach>
												</select>
												<button class="btn btn-large btn-default" onclick="submitStageListingChange()" type="button">Show</button>
											</div>
										</td>
						        	</tr>					        	
						        </table>
						</div>
					</div>
					<!-- <div class="container row"> -->
				        <c:choose>
							<c:when test="${stageResultData.stage.match.status eq 'CLOSED'}">
									<h3>Match is closed.</h3>
							</c:when>
							<c:otherwise>
							
								<!-- <div class="leftColumn" style="position: relative;"> -->
								
									<div class="table-responsive">
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
															<c:url var="competitorResultsUrl" value="/competitorResults">
																<c:param name="matchId" value="${dataline.stageResultData.stage.match.practiScoreId}" />
																<c:param name="competitorId" value="${dataline.competitor.practiScoreId}" />
															</c:url>
															<a href="${competitorResultsUrl }">${dataline.competitor.firstName }</a>
														</td>
														<td align="left">
															<a href="${competitorResultsUrl }">${dataline.competitor.lastName }</a>
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
															${dataline.competitor.division }${pf }
															<%-- ${fn:substring(dataline.competitor.division, 0, 1)}${pf} --%>
														</td>
													</tr> 
												</c:forEach>
											</tbody>
										</table>
									</div>
								<!-- 	</div> -->
							</c:otherwise>
					</c:choose>
<%-- 			<div class="rightColumn">
				<%@ include file="/WEB-INF/jsp/include/rightAdZone.jsp" %>
			</div> --%>
		</div>
<%-- 		<div class="container row">
			<div class="leftColumn">
						<%@ include file="/WEB-INF/jsp/include/pageBottomAdZone.jsp" %> 
			</div>
			
		</div> --%>
	<!-- </div> -->
</div>


<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />

<script>
	$(document).ready(function() {
		$('#stageResultTable').DataTable( {
			paging: false,
			searching: true,
			info: false
		});
	} );
	function submitStageListingChange() {
		
		var url = "${baseUrl}stageResults?matchId=${stageResultData.stage.match.practiScoreId }&stageId=" 
				+ $("select#stage").val() + "&division="+ $("select#division").val();
		location.replace(url);

	}
</script>


</body>
</html>
	

