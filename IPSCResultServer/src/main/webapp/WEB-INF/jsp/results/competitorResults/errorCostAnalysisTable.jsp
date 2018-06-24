<h3>Error Cost Analysis</h3>
<div class="table-responsive">
	<table class="table table-striped table-bordered" id="competitorResultTable">
		<thead>
			<tr>
				<th>
					Stage #
				</th>
				<th>
					Stage Name
				</th>
				
				<th>
					Value
				</th>
				<th>
					Time
				</th>
				<th>
					A-Time
				</th>
				<th>
					Error cost
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="line" items="${errorCostDataLines}">
				<tr>
					<td align="right">
						${line.scoreCard.stage.stageNumber }
					</td>
					<td>
						<c:url var="stageResultsUrl" value="/stageResults" >
							<c:param name="matchId" value="${line.scoreCard.stage.match.practiScoreId}" />
							<c:param name="stageId" value="${line.scoreCard.stage.practiScoreId}" />
							<c:param name="division" value="${line.scoreCard.competitor.division }" />
						</c:url>
						<a href="${stageResultsUrl}">${line.scoreCard.stage.name }</a>
					</td>
					<td align="right">
						${line.scoreCard.stage.maxPoints } (${line.stageValuePercentage}%)
					</td>
					<td align="right">
						<fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${line.scoreCard.time }" />
					</td>
					<c:choose>
						<c:when test="${line.scoreCard.hitFactor gt 0}">
							<td align="right">
								${line.aTime }
							</td>
							<td>
								C=${line.cCost }, 
								D=${line.dCost },
								NS=${line.proceduralPenaltyAndNoShootCost },
								Miss=${line.missCost }
							</td>
						</c:when>
						<c:otherwise>
							<td align="right">
								-
							</td>
							<td>
								-
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>