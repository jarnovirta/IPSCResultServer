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
			<!-- Revive Adserver Asynchronous JS Tag - Generated with Revive Adserver v4.1.4 -->
			<ins data-revive-zoneid="7" data-revive-id="da7ca4f967037b13cceb87f6e31d69cc"></ins>
			<script async src="//ads.hitfactor.fi/www/delivery/asyncjs.php"></script>
		</td>
	</tr>
</table>