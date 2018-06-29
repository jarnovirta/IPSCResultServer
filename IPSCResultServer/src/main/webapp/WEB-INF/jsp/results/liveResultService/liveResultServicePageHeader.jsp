<table>
	<tr>
		<td style="width: 680px">
			<div class="panel panel-info liveResultServiceInfoPanel">
				 <div class="panel-heading">Live Result Service</div>
				  	<div class="panel-body liveResultServiceInfoPanelBody">
				  		<div class="liveResultServicePageInfo">
				  			<div class="liveResultServiceTableLeft">
							        <table>
							        	<tr>
							        		<td>
							        			<b>Match:</b>
							        		</td>
							        		<td style="padding-left: 15px">
							        			<b>${stageResultData.stage.match.name}</b>
						        		</td>
						        	</tr>
						        	<tr>
						        		<td>
						        			<b>Stage:</b>
						        		</td>
						        		<td style="padding-left: 15px">
						        			<c:choose>
						        				<c:when test="${empty stageResultData.stage.name}">
						        					--
						        				</c:when>
						        				<c:otherwise>
						        					${stageResultData.stage.stageNumber } - ${stageResultData.stage.name}
						        				</c:otherwise>
						        			</c:choose>
						        		</td>
						        	</tr>					        	
						        </table>
							</div>
							<div class="class="liveResultServiceTableRight">
								<table>
									<tr>
						        		<td>
						        			<b>Division:</b>
						        		</td>
						        		<td style="padding-left: 15px">
						        			<c:choose>
						        				<c:when test="${empty stageResultData.division}">
						        					--
						        				</c:when>
						        				<c:otherwise>
						        					${stageResultData.division}
						        				</c:otherwise>
						        			</c:choose>
										</td>
						        	</tr>								
								</table>
							</div>
						</div>
					</div>
				</div>
		</td>
		<td class="liveResultServiceAdZone">
			<%@ include file="/WEB-INF/jsp/adZones/liveResultServicePageAdZone.jsp" %>
		</td>
	</tr>
</table>