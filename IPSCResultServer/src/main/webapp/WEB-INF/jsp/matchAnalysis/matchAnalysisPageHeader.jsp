<div class="page-header">
	<h1>Stage Results</h1>
</div>
<br><br>

<div class="panel panel-info">
  <div class="panel-heading">Match Analysis for ${competitorResultData.competitor.firstName } ${competitorResultData.competitor.lastName }</div>
  	<div class="panel-body">
		        <table class="oneColumnPageInfo">
		        	<tr>
		        		<td>
		        			<b>Match:</b>
		        		</td>
		        		<td>
		        			<b>${competitorResultData.match.name}</b>
	        		</td>
	        	</tr>
	        	<tr>
	        		<td>
	        			<b>Competitor:</b>
	        		</td>
	        		<td>
	        			<select id="competitor" name="competitor" class="form-control">
							<c:forEach var="competitor" items="${competitorResultData.match.competitors}">
								<c:set var="competitorListItem" value="${competitor.firstName} ${competitor.lastName }" />
								<c:if test="${competitorResultData.competitor.practiScoreId eq competitor.practiScoreId}">
									<option value="${competitor.practiScoreId}" selected>${competitorListItem}</option>
								</c:if>
								<c:if test="${competitorResultData.competitor.practiScoreId ne competitor.practiScoreId}">
									<option value="${competitor.practiScoreId}">${competitorListItem}</option>
								</c:if>
							</c:forEach>
						</select>
	        		</td>
	        	</tr>
	        	<tr>
	        		<td>
	        			<b>Compare to:</b>
	        		</td>
	        		<td>
	        			<select id="competitor" name="competitor" class="form-control">
							<c:forEach var="competitor" items="${competitorResultData.match.competitors}">
								<c:set var="competitorListItem" value="${competitor.firstName} ${competitor.lastName }" />
								<c:if test="${competitorResultData.competitor.practiScoreId eq competitor.practiScoreId}">
									<option value="${competitor.practiScoreId}" selected>${competitorListItem}</option>
								</c:if>
								<c:if test="${competitorResultData.competitor.practiScoreId ne competitor.practiScoreId}">
									<option value="${competitor.practiScoreId}">${competitorListItem}</option>
								</c:if>
							</c:forEach>
						</select>
	        		</td>
	        	</tr>	        		 		        	
	        </table>
	</div>
</div>