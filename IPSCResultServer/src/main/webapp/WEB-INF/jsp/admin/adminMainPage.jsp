<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTagWithDataTablesLinks.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<body>
	<c:url var="baseUrl" value="/" />
	<div id="wrap">
		<div class="container">
			<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
			<ol class="breadcrumb breadcrumb-arrow">
				<li><a href="${baseUrl }">Home</a></li>
				<li class="active"><span>Admin</span></li>
			</ol>
			<div class="page-header">
				<h1>Admin</h1>
			</div>
			<br>
			<%@ include file="/WEB-INF/jsp/admin/matchTable.jsp" %>
			<hr />
			<%@ include file="/WEB-INF/jsp/admin/userTable.jsp" %>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			$('#adminMatchListingTable').DataTable( {
				paging: true,
				searching: true,
				info: true,
				ordering: false
			});
			$('#adminUserListingTable').DataTable( {
				paging: true,
				searching: true,
				info: true,
				ordering: false
			});
		} );
	
		
		function deleteMatch(matchId) {
			window.location.href = "${baseUrl}admin/deleteMatch/" + matchId;
		}
	</script>
	<%@include file="/WEB-INF/jsp/include/loginLogoutScripts.jsp" %>
	
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
