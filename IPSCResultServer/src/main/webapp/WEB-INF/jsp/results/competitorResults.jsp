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
			<ol class="breadcrumb breadcrumb-arrow">
				<li><a href="${baseUrl }">Home</a></li>
				<li><a href="${baseUrl }/match/${resultData.match.id}">Match Main Page</a></li>
				<li class="active"><span>Verify List</span></li>
			</ol>
			<div class="page-header">
				<h1>Results for ${resultData.competitor.firstName } ${resultData.competitor.lastName }</h1>
			</div>
			<br><br>
			<div class="panel panel-info">
			  <div class="panel-heading">Competitor verification list / ${resultData.match.name } - <fmt:formatDate value="${resultData.match.date.time}" pattern="dd.MM.yyyy" /></div>
			  	<div class="panel-body">
			  	  	<div class="verifyPageInfoTable">
					    <div class="verifyPageInfoTableLeft">
					        <table>
					        	<tr>
					        		<td>
					        			<p>Competitor #:</p>
					        		</td>
					        		<td>
					        			<p>${resultData.competitor.shooterNumber }</p>
					        		</td>
					        	</tr>
					        	<tr>
					        		<td>
					        			<p>Name:</p>
					        		</td>
					        		<td>
					        			<p>${resultData.competitor.firstName } ${resultData.competitor.lastName }</p>
					        		</td>
					        	</tr>
								<tr>
					        		<td>
					        			<p>Alias:</p>
					        		</td>
					        		<td>
					        			<p>${resultData.competitor.ipscAlias }</p>
					        		</td>
					        	</tr>	
					        	<tr>
					        		<td>
					        			<p>Division:</p>
					        		</td>
					        		<td>
					        			<p><c:out value="${resultData.competitor.division }" /></p>
					        		</td>
					        	</tr>
					        	<tr>
					        		<td>
					        			<p>Team:</p>
					        		</td>
					        		<td>
					        			<p>${resultData.competitor.team }</p>
					        		</td>
					        	</tr>					    			        	
					        </table> 
					    </div>
					    <div class="verifyPageInfoTableRight">   
					       	<table>
					        	<tr>
					        		<td>
					        			<p>Power Factor:</p>
					        		</td>
					        		<td>
					        			<p><c:out value="${resultData.competitor.powerFactor }" /></p>
					        		</td>
					        	</tr>
					        	<tr>
					        		<td>
					        			<p>Category:</p>
					        		</td>
					        		<td>
					        			<c:forEach items="${resultData.competitor.categories }" var="category">
					        				<p><c:out value="${category}" /></p> 
					        			</c:forEach>
					        		</td>
					        	</tr>
								<tr>
					        		<td>
					        			<p>Region:</p>
					        		</td>
					        		<td>
					        			<p>${resultData.competitor.country }</p>
					        		</td>
					        	</tr>	
					        	<tr>
					        		<td>
					        			<p>Squad:</p>
					        		</td>
					        		<td>
					        			<p>${resultData.competitor.squad }</p>
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
						<p>Stage</p>
					</th>
					<th>
						<p>Time</p>
					</th>
					<th>
						<p>A</p>
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
						<p>Proc.</p>
					</th>
					<th>
						<p>Last updated</p>
					</th>
					<th>
						<p>Rank</p>
					</th>
				</tr>
				<c:forEach var="stage" items="${resultData.match.stages}">
					<c:set var="scoreCard" value="${resultData.scoreCards[stage.id] }" />
						<tr>
							<td>
								<p>${scoreCard.stage.stageNumber }</p>
							</td>
							<td>
								<c:url var="url" value="/match/${resultData.match.id}/stage/${scoreCard.stage.id}/division/${scoreCard.competitor.division }" />
								<p><a href="${url}">${scoreCard.stage.name}</a></p>
							</td>
							<td>
								<p><fmt:formatNumber type = "number" minFractionDigits = "2" value="${scoreCard.time }" /></p>
							</td>
							<td>
								<p>${scoreCard.aHits}</p>
							</td>
							<td>
								<p>${scoreCard.cHits}</p>
							</td>
							<td>
								<p>${scoreCard.dHits}</p>						
							</td>
							<td>
								<p>${scoreCard.misses}</p>
							</td>
							<td>
								<p>${scoreCard.noshootHits}</p>
							</td>
							<td>
								<p>${scoreCard.proceduralPenalties }</p>
							</td>
							<td>
								<p><fmt:formatDate value="${scoreCard.modified.time }" pattern="dd.MM.yyyy 'at' HH:mm:ss" /></p>
							</td>
							<td>
								<p>${scoreCard.stageRank}</p>
							</td>
						</tr> 
				</c:forEach>
			</table>
		</div>
	</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />