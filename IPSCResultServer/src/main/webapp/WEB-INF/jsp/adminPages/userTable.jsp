<h3>User List</h3>
<br>
<table class="table table-striped table-bordered" id="adminUserListingTable">
	<thead>
		<tr>
			<th>
				#
			</th>
			<th>
				First name
			</th>
			<th>
				Last name
			</th>
			<th>
				Username
			</th>
			<th>
				Telephone
			</th>
			<th>
				Email
			</th>
			<th>
				Delete user
			</th>
		</tr>
	</thead>
	<tbody>
		<c:set var="userCounter" value="1" />
		<c:forEach items="${userList}" var="user">
			<tr>
				<td align="right">
					${userCounter }
					<c:set var="userCounter" value="${userCounter + 1 }" />
				</td>
				<td>
					${user.firstName}
				</td>
				<td>
					${user.lastName}
				</td>
				<td>
					${user.username}
				</td>
				<td>
					${user.phone}
				</td>
				<td>
					${user.email}
				</td>
				<td>
					<button class="btn btn-danger" onclick="deleteUser('${user.id}')" type="button">Delete</button>
				</td>
			</tr> 
		</c:forEach>
	</tbody>
</table>

<a style="text-decoration: none;" href="<c:url value='/' />admin/addUser"><button class="btn btn-primary" type="button">Add a user</button></a>

<script>
	function deleteUser(userId) {
		window.location.href = "${baseUrl}admin/deleteUser/" + userId;
	}

</script>