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
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<%@ include file="/WEB-INF/jsp/adZones/pageTopAdZone.jsp" %>
				</c:if>
				<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
				<ol class="breadcrumb breadcrumb-arrow">
					<li class="active"><span>Home</span></li>
				</ol>
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<div class="alert alert-info">
						Demo ads are visible. Go to hitfactor.fi/adtest/clear to remove.
					</div>
				</c:if>
				
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
					<c:if test="${sessionScope.adtest.adTest eq true}">
						<div style="align: center; margin-top: 80px; margin-left: auto; margin-right:auto">
							<%@ include file="/WEB-INF/jsp/adZones/pageBottomAdZone.jsp" %>
						</div>	
					</c:if>
			</div>
		
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
