<div class="page-header">
	<h1>Match Analysis</h1>
</div>
<br><br>

<div class="panel panel-info">
  <div class="panel-heading">Match Analysis for <span id="pageHeadingCompetitorName"></span></div>
  	<div class="panel-body">
		        <table class="oneColumnPageInfo">
		        	<tr>
		        		<td>
		        			<b>Match:</b>
		        		</td>
		        		<td>
		        			<b>${competitorResultData.match.name}</b>
	        		</td>
	        	</tr>
	        	<tr>
	        		<td>
	        			<b>Competitor:</b>
	        		</td>
	        		<td>
	        			<select id="competitor" name="competitorId" class="form-control"></select>

        			</td>
	        	</tr>
	        	<tr>
	        		<td>
	        			<b>Compare to:</b>
	        		</td>
	        		<td>
	        			<div class="form-inline">
		        			<select id="compareToCompetitor" name="compareToCompetitorId" class="form-control" ></select>
							<button class="btn btn-large btn-default" onclick="submitCompetitorsChange()" type="button">Show</button>
						</div>
	        		</td>
	        	</tr>	        		 		        	
	        </table>
	</div>
</div>