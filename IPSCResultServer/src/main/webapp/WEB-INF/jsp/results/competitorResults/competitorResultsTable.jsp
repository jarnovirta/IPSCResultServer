<h3>Verify List</h3>

<div class="table-responsive">
	<table class="table table-striped table-bordered" id="competitorResultTable">
		<thead>
			<tr>
				<th>Stage #</th>
				<th>
					Stage Name
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
				<th>
					Proc.
				</th>
				<c:if test="${additionalPenaltiesColumn == true }">
					<th>
						Add. penalties
					</th>
				</c:if>
				<th>
					Points
				</th>
				<th>
					Time
				</th>
				<th>
					HF
				</th>
				<th>
					Last updated
				</th>
				<!-- REMOVED, NEEDS TO BE FIXED. SHOWS RANK IN COMBINED, NOT IN DIVISION -->
				<!-- <th>
					Rank
				</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="stage" items="${resultData.match.stages}">
					<c:set var="scoreCard" value="${resultData.scoreCards[stage.id] }" />
						<c:if test="${not empty scoreCard  }">
							<tr>
								<td align="right">
									${scoreCard.stage.stageNumber }
								</td>
								<td>
									<c:choose>
										<c:when test="${resultData.match.status eq 'SCORING_ENDED' }">
											<c:url var="stageResultsUrl" value="/stageResults" >
												<c:param name="matchId" value="${resultData.match.practiScoreId}" />
												<c:param name="stageId" value="${scoreCard.stage.practiScoreId}" />
												<c:param name="division" value="${scoreCard.competitor.division }" />
											</c:url>
											<a href="${stageResultsUrl}">${scoreCard.stage.name}</a>
										</c:when>
										<c:otherwise>
											${scoreCard.stage.name}
										</c:otherwise>
									</c:choose>
								</td>

								<td align="right">
									${scoreCard.aHits}
								</td>
								<td align="right">
									${scoreCard.cHits}
								</td>
								<td align="right">
									${scoreCard.dHits}						
								</td>
								<td align="right">
									${scoreCard.misses}
								</td>
								<td align="right">
									${scoreCard.noshootHits}
								</td>
								<td align="right">
									${scoreCard.proceduralPenalties }
								</td>
								<c:if test="${additionalPenaltiesColumn == true }">
									<td align="right">
										${scoreCard.additionalPenalties }
									</td>
								</c:if>
								<td align="right">
									${scoreCard.points }
								</td>
								<td align="right">
									<fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${scoreCard.time }" />
								</td>
								<td align="right">
									<fmt:formatNumber type = "number" minFractionDigits = "4" maxFractionDigits = "4" value="${scoreCard.hitFactor }" />
								</td>
								
								<td align="right">
									<fmt:formatDate value="${scoreCard.modified.time }" pattern="dd.MM.yyyy 'at' HH:mm:ss" />
								</td>
								<!-- <td align="right">
									${scoreCard.stageRank}
								</td>-->
							</tr>
					</c:if>
			</c:forEach>
 			<tr>
				<td />
				<td align="right" style="font-weight:bold">
					Total:
				</td>
				<td align="right">
					${resultData.aHitsSum}
				</td>
					
				<td align="right">
					${resultData.cHitsSum}
				</td>
				<td align="right">
					${resultData.dHitsSum}
				</td>
				<td align="right">
					${resultData.missSum}
				</td>
				<td align="right">
					${resultData.noshootHitsSum}
				</td>
				<td align="right">
					${resultData.proceduralPenaltiesSum}
				</td>
				<td align="right">
					${resultData.pointsSum}
				</td>									
				<td align="right">
					<fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${resultData.totalTime}" />
				</td>
				<td align="right">
					<fmt:formatNumber type = "number" minFractionDigits = "4" maxFractionDigits = "4" value="${resultData.hitFactorSum}" />
				</td>
				<td />
			</tr>
			<tr>
				<td />
				<td />
				<td align="right">
					<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${resultData.aHitsPercentage }" />%
				</td>
		 		<td align="right">
					<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${resultData.cHitsPercentage }" />%
				</td>
				<td align="right">
					<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${resultData.dHitsPercentage }" />%
				</td>
				<td align="right">
					<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${resultData.missPercentage }" />%
				</td>
				<td />
				<td />
				<td />
				<td />
				<td align="right">
					Avg: <fmt:formatNumber type="number" minFractionDigits="4" maxFractionDigits="4" value="${resultData.hitFactorAverage }" />
				</td>
				<td />
			</tr>												
		</tbody>
	</table>
</div>