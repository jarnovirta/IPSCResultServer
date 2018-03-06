<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />

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
			<br><br>
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
		</div>
	</div>
	<script>
		function statusChange(matchId) {
			window.location.href = "${baseUrl}admin/match/" + matchId + "/setStatus/" + $("select#" + matchId).val();
		};
		function deleteMatch(matchId) {
			window.location.href = "${baseUrl}admin/deleteMatch/" + matchId;
		}
	</script>
	<%@include file="/WEB-INF/jsp/include/loginLogoutScripts.jsp" %>
	
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
