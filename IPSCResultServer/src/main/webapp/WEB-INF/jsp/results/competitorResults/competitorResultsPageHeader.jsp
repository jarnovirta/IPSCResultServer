<div class="panel panel-info">
  <div class="panel-heading">Competitor Information</div>
  	<div class="panel-body">
		    <div class="twoColumnPageInfo">
		    	<div class="pageInfoTableLeft">
			        <table>
			        	<tr>
			        		<td>
			        			<b>Name:</b>
			        		</td>
			        		<td>
			        			<c:if test="${resultData.competitor.disqualified eq true }">
			        				<c:set var="dq" value="<span class='label label-danger'>&nbsp&nbspDQ&nbsp&nbsp</span>" />
			        			</c:if>
			        			<c:if test="${resultData.competitor.dnf eq true }">
			        				<c:set var="dnf" value="<span class='label label-info'>&nbsp&nbspDNF&nbsp&nbsp</span>" />
			        			</c:if>
			        			<b>${resultData.competitor.firstName } ${resultData.competitor.lastName } &nbsp ${dnf }${dq } </b>
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
		        					<c:url var="divisionResultsUrl" value="/divisionResults">
		        						<c:param name="matchId" value="${resultData.match.practiScoreId}" />
		        						<c:param name="division" value="${resultData.competitor.division }" />
		        					</c:url> 
		        					<a href="${divisionResultsUrl}"><c:out value="${resultData.competitor.division }" /></a>
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