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
						<c:forEach var="card" items="${stageResultData.scoreCards}">
							<tr>
								<td align="right">
									${card.stageRank}
								</td>
								<td align="left">
									<c:url var="competitorResultsUrl" value="/competitorResults">
										<c:param name="matchId" value="${stageResultData.stage.match.practiScoreId}" />
										<c:param name="competitorId" value="${card.competitor.practiScoreId}" />
									</c:url>
									<a href="${competitorResultsUrl }">${card.competitor.firstName }</a>
								</td>
								<td align="left">
									<a href="${competitorResultsUrl }">${card.competitor.lastName }</a>
								</td>
								<td align="right">
									${card.points}
								</td>
								<td align="right">
									<fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${card.time }" />
								</td>
								<td align="right">
									<fmt:formatNumber type = "number" minFractionDigits = "4" maxFractionDigits = "4" value="${card.hitFactor }" />
								</td>
								<td align="right">
									<fmt:formatNumber type = "number" minFractionDigits = "4" maxFractionDigits = "4" value="${card.inViewStagePoints }" />
								</td>
								<td align="right">
									<fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${card.scorePercentage }" />			
								</td>
								<td align="center">
									<c:forEach items = "${card.competitor.categories}" var = "category">
											${category}
									</c:forEach>
								</td>
								<td align="center">
									<c:if test="${card.competitor.powerFactor eq  'MINOR'}">
										<c:set var="pf" value="-" />
									</c:if>
									<c:if test="${card.competitor.powerFactor eq  'MAJOR'}">
										<c:set var="pf" value="+" />
									</c:if>
									${card.competitor.division }${pf }
									</td>
								</tr> 
							</c:forEach>
						</tbody>
						
					</table>
				</div>
	</c:otherwise>
</c:choose>