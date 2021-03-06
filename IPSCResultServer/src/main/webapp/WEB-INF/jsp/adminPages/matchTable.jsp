<h3>Match List</h3>
<br>
<div class="table-responsive">
	<table class="table table-striped table-bordered" id="adminMatchListingTable">
		<thead>
			<tr>
				<th>
					#
				</th>
				<th>
					Match name
				</th>
				<th>
					Date
				</th>
				<th>
					Status
				</th>
				<th>
					Results shown
				</th>
				<th>
					Change status
				</th>
				<th>
					Uploaded by
				</th>
				<th>
					Delete match
				</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="matchCounter" value="1" />
			<c:forEach items="${matchList}" var="match">
				<tr>
					<td align="right">
						${matchCounter }
						<c:set var="matchCounter" value="${matchCounter + 1 }" />
					</td>
					<td>
						${match.name }
					</td>
					<td>
						<fmt:formatDate value="${match.date.time}" pattern="dd.MM.yyyy"></fmt:formatDate>
					</td>
					<td>
						<h4 class="adminTableLabel">
						<c:if test="${match.status eq 'CLOSED' }">
							<span class="label label-danger">
							<c:set var="visibility" value="None" />
						</c:if>
						<c:if test="${match.status eq 'SCORING' }">
							<span class="label label-success">
							<c:set var="visibility" value="Verify, stage results" />
						</c:if>
						<c:if test="${match.status eq 'SCORING_ENDED' }">
							<span class="label label-default">
							<c:set var="visibility" value="All" />
						</c:if>
						<c:out value="${match.status }" />
						</span>
						</h4>
					</td>
					<td>
						${visibility }
					</td>
					<td>
						<form action="<c:url value='/admin/setMatchStatus' />" method="POST">
							
							<select name="status">
								<c:forEach items="${matchStatusList }" var="status">
									<c:if test="${match.status eq  status}">
										<option value="${status }" selected><c:out value="${status }" /></option>
									</c:if>
									<c:if test="${match.status ne  status}">
										<option value="${status }"><c:out value="${status }" /></option>
									</c:if>
								</c:forEach>
							</select>
							<input type="hidden" name="matchId" value="${match.id }">
							<button class="btn btn-default adminTableStatusButton" type="submit">Set</button>	
						</form>
						
					</td>
					<td>
						<c:choose>
							<c:when test="${match.uploadedByAdmin == true }">
								admin
							</c:when>
							<c:otherwise>
								${match.user.username }
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<button class="btn btn-danger" onclick="deleteMatch('${match.id}')" type="button">Delete</button>
					</td>
				</tr> 
			</c:forEach>
		</tbody>
	</table>
</div>
<script>
	function deleteMatch(matchId) {
		bootbox.dialog({
		  	  message: "Delete match?",
		  	  title: "Confirm",
		  	  buttons: {
		  		cancel: {
		    	      label: "Cancel",
		    	      className: "btn-primary",
		    	      callback: function() {
		    	      }
		    	    },
		    	  confirm: {
		    	      label: "Delete",
		    	      className: "btn-danger",
		    	      callback: function() {
		    	    	  $.post("${baseUrl}admin/deleteMatch", { matchId: matchId}, function() {
		    	    		  window.location.href = "${baseUrl}admin";
		    	    	  });
		    	    	  }
		    	      }
		    	    },
		  	  });
	}
	function statusChange(matchId) {
		var status = document.getElementById(matchId).value;	
		window.location.href = "${baseUrl}admin/match/" + matchId + "/setStatus/" + status;
 	}

</script>