<c:choose>
	<c:when test="${stageResultData.stage.match.status eq 'CLOSED'}">
			<h3>Match is closed.</h3>
	</c:when>
	<c:otherwise>
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
	</c:otherwise>
</c:choose>