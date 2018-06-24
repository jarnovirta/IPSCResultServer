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