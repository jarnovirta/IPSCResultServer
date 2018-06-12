<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
		<jsp:include page="/WEB-INF/jsp/include/dataTablesHeadTagLinks.jsp" />
	</head>

	<body>
		<div id="wrap">
			<div class="container">
				<%@ include file="/WEB-INF/jsp/include/pageTopAdZone.jsp" %>
				
				<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
				<ol class="breadcrumb breadcrumb-arrow">
					<li class="active"><span>Home</span></li>
				</ol>
				
				<div class="page-header">
					<h1>Result Service</h1>
				</div>
				<br>
				<br>
					
					<div style="max-width: 750px;">
						<table class="table table-striped table-bordered" id="matchTable">
							<thead>
								<tr>
									<th>
										Match
									</th>
									<th>
										Level
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
												<c:url var="matchMainPageUrl" value="/matchMainPage" >
													<c:param name="matchId" value="${ match.practiScoreId}" />
												</c:url>
												<a href="${matchMainPageUrl}">${match.name}</a>
											</td>
											<td>
												${match.level }
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
		<%@ include file="/WEB-INF/jsp/include/pageBottomAdZone.jsp" %>					
		<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
		
	</body>

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
	
</html>
<%@include file="/WEB-INF/jsp/include/loginLogoutScripts.jsp" %>