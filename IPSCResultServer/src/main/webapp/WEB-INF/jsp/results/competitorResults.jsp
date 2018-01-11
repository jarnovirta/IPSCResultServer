<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 

<body>
	<div id="wrap">
		<div class="container">
			<div class="page-header">
				<h1>Results for ${resultData.competitor.firstName } ${resultData.competitor.lastName }</h1>
			</div>
			<br><br>
			<div class="panel panel-info">
			  <div class="panel-heading">Competitor verification list - {resultData.match.name } (${resultData.match.date })</div>
			  	<div class="panel-body">
				  	<div class="verifyPageInfoTable">
					    <div class="verifyPageInfoTableLeft">
					        <table>
					        	<tr>
					        		<td>
					        		
					        		</td>
					        		<td>
					        		
					        		</td>
					        	</tr>
								<tr>
					        		<td>
					        		
					        		</td>
					        		<td>
					        		
					        		</td>
					        	</tr>						        	
					        </table> 
					    </div>
					    <div class="verifyPageInfoTableRight">   
					       Content of the second column. 
					    </div>        
					</div>
				</div>
			</div>

			<br>
			<table class="table table-striped">
				<tr>
					<th>#</th>
					<th>
						Stage
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
				</tr>
				<c:forEach var="stageScore" items="${resultData.stageScores}">
						<tr>
							<td>
								${stageScore.stage.stageNumber }
							</td>
							<td>
								${stageScore.stage.name}
							</td>
							<td>
								${stageScore.scoreCards[0].aHits}
							</td>
							<td>
								${stageScore.scoreCards[0].cHits}
							</td>
							<td>
								${stageScore.scoreCards[0].dHits}						
							</td>
							<td>
								${stageScore.scoreCards[0].misses}
							</td>
							<td>
								${stageScore.scoreCards[0].noshootHits}
							</td>
						</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>