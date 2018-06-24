<c:choose>
	<c:when test="${match.status eq 'CLOSED'}">
		<h3>Match is closed.</h3>
	</c:when>
	<c:when test="${match.status eq 'SCORING'}">
		<h3>Scoring has not ended. Statistics not shown.</h3>
	</c:when>
	<c:otherwise>
		<div class="table-responsive">
			<table class="table table-striped table-bordered" id="statisticsTable">
				<thead>
					<tr>
						<th>
							Div Pos
						</th>
						<th>
							Name
						</th>
						<th>
							CompNr
						</th>
						<th>
							Total Pts
						</th>
						<th>
							Div%
						</th>
						<th>
							Time
						</th>
						<th >
							A
						</th>
						<th>
							A%
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
							Proc
						</th>
						<c:if test="${additionalPenaltiesColumn == true }">
							<th>
								Add. pen.
							</th>
						</c:if>
						<th>
							Sum of Pts
						</th>
						<th>
							Div
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="line" items="${statistics}">
							<tr>
								<td align="right">
									<c:choose>
										<c:when test="${line.competitor.disqualified == true }">
											DQ
										</c:when>
										<c:otherwise>
											${line.divisionRank}	
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:url var="competitorResultsUrl" value="/competitorResults">
										<c:param name="matchId" value="${match.practiScoreId}" />
										<c:param name="competitorId" value="${line.competitor.practiScoreId}" />
									</c:url>
									<a href="${competitorResultsUrl}">${line.competitor.firstName} ${line.competitor.lastName} </a>
								</td>
								<td align="right">
									${line.competitor.shooterNumber }
								</td>
								<td align="right">
									<fmt:formatNumber type="number" minFractionDigits="4" maxFractionDigits="4" value="${line.divisionPoints }" />
								</td>
								<td align="right">
									<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${line.divisionScorePercentage }" />
								</td>
								<td align="right">
									<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${line.matchTime }" />
								</td>
								<td align="right">
									${line.aHits}
								</td>
								<td align="right">
									<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${line.aHitPercentage }" />
								</td>
								<td align="right">
									${line.cHits}						
								</td>
								<td align="right">
									${line.dHits}
								</td>
								<td align="right">
									${line.misses}
								</td>
								<td align="right">
									${line.noShootHits }
								</td>
								<td align="right">
									${line.proceduralPenalties}
								</td>
								<c:if test="${additionalPenaltiesColumn == true }">
									<td align="right">
										${line.additionalPenalties}
									</td>
								</c:if>
								<td align="right">
									${line.sumOfPoints }
								</td>
								<td align="center">
									<c:if test="${line.competitor.powerFactor eq  'MINOR'}">
										<c:set var="pf" value="-" />
									</c:if>
									<c:if test="${line.competitor.powerFactor eq  'MAJOR'}">
										<c:set var="pf" value="+" />
									</c:if>
									<c:url var="matchResultsUrl" value="matchResults">
										<c:param name="matchId" value="${match.practiScoreId}" />
										<c:param name="division" value="${line.competitor.division}" />
									</c:url> 
									<%-- <a href="${url}">${fn:substring(line.competitor.division, 0, 1)}${pf} </a> --%>		
									<a href="${matchResultsUrl}">${line.competitor.division}${pf} </a>		
									</td>
								</tr> 
							</c:forEach>
						</tbody>
				</table> 
			</div>
	</c:otherwise>
</c:choose>