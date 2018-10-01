<div class="table-responsive">
	<table class="table table-striped table-bordered" id="matchResultTable">
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
					Points
				</th>
				<th>
					%
				</th>
				<th>
					Division
				</th>
				<th>
					Cat
				</th>
				<th>
					Team
				</th>
				<th>
					Scored
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="dataline" items="${matchResultData.dataLines}">
				<tr>
					<td align="right">
						${dataline.rank}
					</td>
					<td>
						<c:url var="competitorResultsUrl" value="/competitorResults">
							<c:param name="matchId" value="${matchResultData.match.practiScoreId}" />
							<c:param name="competitorId" value="${dataline.competitor.practiScoreId}" />
						</c:url>

						<a href="${competitorResultsUrl }">${dataline.competitor.firstName }</a> 
					</td>
					<td>
						<a href="${competitorResultsUrl }">${dataline.competitor.lastName }</a> 
					</td>
					<td align="right">
						<fmt:formatNumber type = "number" minFractionDigits = "4" maxFractionDigits = "4" value="${dataline.points }" />
					</td>
					<td align="right">
						<fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${dataline.scorePercentage }" />
					</td>
					<td align="center">
						<c:if test="${dataline.competitor.powerFactor eq  'MINOR'}">
							<c:set var="pf" value="-" />
						</c:if>
						<c:if test="${dataline.competitor.powerFactor eq  'MAJOR'}">
							<c:set var="pf" value="+" />
						</c:if>
						${dataline.competitor.division}${pf }
						<%-- ${fn:substring(dataline.competitor.division, 0, 1)}${pf} --%>			
					</td>
					<td align="center">
						<c:forEach items = "${dataline.competitor.categories}" var = "category">
							${category }
						</c:forEach>
					</td>
					<td>
						<c:set var="team" value="${dataline.competitor.team }" />
						<c:if test="${fn:length(team) > 15 }">
							<c:set var="team" value="${fn:substring(team, 0, 15)}..." />
						</c:if>
						${team }
					</td>
					<td align="right">
						${dataline.scoredStages }
					</td>
				</tr> 
			</c:forEach>
		</tbody>
	</table>
</div>