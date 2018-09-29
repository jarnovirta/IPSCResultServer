<h3>User List</h3>
<br>
<div class="table-responsive">
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
					Edit/Delete
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
						
						<form action="admin/editUser" method="GET">
							<input type="hidden" name="userId" value="${user.id }" />
							<div class="btn-group">
								<button class="btn btn-default" type="submit">Edit</button>
								<button class="btn btn-danger" onclick="deleteUser('${user.id}')" type="button">Delete</button>
							</div>
						</form>
						
					</td>
				</tr> 
			</c:forEach>
		</tbody>
	</table>
</div>
<a style="text-decoration: none;" href="<c:url value='/' />admin/editUser"><button class="btn btn-primary" type="button">Add a user</button></a>

<script>
	function deleteUser(userId) {
		bootbox.dialog({
		  	  message: "Delete user?",
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
		    	    	  $.post("${baseUrl}admin/deleteUser", 
		    	    			  { userId: userId }, 
		    	    			  function() { 
		    	    				  window.location.href = "${baseUrl}admin";
		    	    			  }
		    	    			);
		    	      }
		    	    },
		  	  }
		  });
	}

</script>