<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 

<html>
	<head>
		<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
		<jsp:include page="/WEB-INF/jsp/include/dataTablesHeadTagLinks.jsp" />
	</head>
	
	<body>
		<c:url var="baseUrl" value="/" />
		<div id="wrap">
			<div class="container">
				<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
				<ol class="breadcrumb breadcrumb-arrow">
					<li><a href="<c:url value='/' />">Home</a></li>
					<li class="active"><span>Admin</span></li>
				</ol>
				<div class="page-header">
					<h1>Admin</h1>
				</div>
				<br>
				<%@ include file="/WEB-INF/jsp/adminPages/matchTable.jsp" %>
				<security:authentication var="authorities" property="principal.authorities" />
				<c:if test="${authorities == '[ROLE_ADMIN]' }">
					<hr />
					<%@ include file="/WEB-INF/jsp/adminPages/userTable.jsp" %>
				</c:if>
			</div>
		</div>
			
	<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
	</body>
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
	</script>
</html>
