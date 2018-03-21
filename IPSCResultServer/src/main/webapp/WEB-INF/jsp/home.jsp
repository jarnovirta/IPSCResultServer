<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTagWithDataTablesLinks.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<body>
	<c:url var="baseUrl" value="/" />
	
	<div id="wrap">
		<div class="container">
			<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
			<ol class="breadcrumb breadcrumb-arrow">
				<li class="active"><span>Home</span></li>
			</ol>
			<div class="page-header">
				<h1>Result Service</h1>
			</div>
			<br>
			<br>
			<div style="max-width: 750px">
				<table class="table table-striped table-bordered" id="matchTable">
					<thead>
						<tr>
							<th>
								Match
							</th>
							<th>
								Date
							</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="match" items="${matches}">
							<c:if test="${match.status ne 'CLOSED' }">
								<tr>
									<td>
										<a href="${baseUrl}match/${ match.id}">${match.name}</a>
									</td>
									<td>
										<fmt:formatDate value="${match.date.time}" pattern="dd.MM.yyyy" />
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
		
	<script>

		$(document).ready(function() {
			$('#matchTable').DataTable( {
				paging: true,
				searching: true,
				sort: false,
				info: false
			});
		} );

	</script>
	<%@include file="/WEB-INF/jsp/include/loginLogoutScripts.jsp" %>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />