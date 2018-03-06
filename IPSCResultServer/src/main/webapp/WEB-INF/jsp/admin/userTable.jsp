<h3>User List</h3>
<br>
<table class="table table-striped table-bordered" id="adminUserListingTable">
	<thead>
		<tr>
			<th>
				#
			</th>
			<th>
				Match name
			</th>
			<th>
				Status
			</th>
			<th>
				Change status
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
					<h4 class="adminTableLabel">
					<c:if test="${match.status eq 'CLOSED' }">
						<span class="label label-warning">
					</c:if>
					<c:if test="${match.status eq 'SCORING' }">
						<span class="label label-success">
					</c:if>
					<c:if test="${match.status eq 'SCORING_ENDED' }">
						<span class="label label-default">
					</c:if>
					<c:out value="${match.status }" />
					</span>
					</h4>
				</td>
				<td>
					<select id="${match.id }">
						<c:forEach items="${matchStatusList }" var="status">
							<c:if test="${match.status eq  status}">
								<option value="${status }" selected><c:out value="${status }" /></option>
							</c:if>
							<c:if test="${match.status ne  status}">
								<option value="${status }"><c:out value="${status }" /></option>
							</c:if>
						</c:forEach>
					</select>
					<button class="btn btn-default adminTableStatusButton" onclick="statusChange('${match.id}')" type="button">Set</button>
				</td>
				<td>
					<button class="btn btn-danger" onclick="deleteMatch('${match.id}')" type="button">Delete</button>
				</td>
			</tr> 
		</c:forEach>
	</tbody>
</table>