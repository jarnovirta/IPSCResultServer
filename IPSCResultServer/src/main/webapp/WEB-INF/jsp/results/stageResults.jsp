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
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<h1>Results for Stage in ${stageResultData.stage.match.name}</h1>
			</div>
			<br><br>
			<div class="panel panel-info">
			  <div class="panel-heading">Stage Results</div>
			  	<div class="panel-body">
			  	  	<div class="verifyPageInfoTable">
					    <div class="verifyPageInfoTableLeft">
					        <table>
					        	<tr>
					        		<td>
					        			<p>Stage name:</p>
					        		</td>
					        		<td>
					        			<p>${stageResultData.stage.name}</p>
					        		</td>
					        	</tr>
					        	<tr>
					        		<td>
					        			<p>Division:</p>
					        		</td>
					        		<td>
					        			<p>[Division dropdown]</p>
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
							<p><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${dataline.scoreCard.roundedTime }" /></p>
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
							<c:if test = "${resultData.competitor.shooterNumber != -1} "> 
					        	<p>${resultData.competitor.shooterNumber }</p>
					        </c:if>
						</td>
						<td>
							<p>${dataline.competitor.lastName }, ${dataline.competitor.firstName }</p>
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
</body>
</html>