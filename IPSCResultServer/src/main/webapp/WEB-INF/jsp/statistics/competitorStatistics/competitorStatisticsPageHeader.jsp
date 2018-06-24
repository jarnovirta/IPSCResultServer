<div class="page-header">
	<h1>Competitor Statistics</h1>
</div>
<br><br>
<div class="panel panel-info">
  <div class="panel-heading">Match Info</div>
	  <div class="panel-body">
			    <div class="oneColumnPageInfo">
			        <table>
			        	<tr>
			        		<td>
			        			<b>Match:</b>
			        		</td>
			        		<td>
			        			<b>${match.name}</b>
			        		</td>
			        	</tr>
			        	<tr>
			        		<td>
			        			<b>Date:</b>
			        		</td>
			        		<td>
			        			<fmt:formatDate value="${match.date.time}" pattern="dd.MM.yyyy" />
			        		</td>
			        	</tr>
			        	<tr>
			        		<td>
			        			<b>Division:</b>
			        		</td>
			        		<td>
				        		<div class="form-inline">
					        		<select id="division" name="division" class="form-control">
										<c:forEach var="division" items="${match.divisionsWithResults}">
											<c:if test="${param.division eq division}">
												<option value="${division}" selected><c:out value="${division}" /></option>
											</c:if>
											<c:if test="${param.division ne division}">
												<option value="${division}"><c:out value="${division}" /></option>
											</c:if>
										</c:forEach>
									</select>
									<button class="btn btn-large btn-default" onclick="submitDivisionChange()" type="button">Show</button>
								</div>
							</td>
			        	</tr>
					</table>
			  </div>
			</div>
		</div>