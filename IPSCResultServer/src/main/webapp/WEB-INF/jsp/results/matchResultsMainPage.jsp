<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="/WEB-INF/jsp/include/headTagWithDataTablesLinks.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 

<body>
	<c:url var="baseUrl" value="/" />

	<div id="wrap">
		<div class="container">
			<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
		    <ol class="breadcrumb breadcrumb-arrow">
				<li><a href="${baseUrl }">Home</a></li>
				<li class="active"><span>Match</span></li>
			</ol>
			<div class="page-header">
				<h1>${match.name}</h1>
			</div>
			<br>
			<c:choose>
				<c:when test="${match.status eq 'CLOSED'}">
					<h3>Match is closed.</h3>
				</c:when>
			<c:otherwise>
				<c:if test="${match.status eq 'SCORING_ENDED' }">
					<a href="${baseUrl}match/${match.id }/statistics" style="text-decoration: none;">
						<button class="btn btn-large btn-primary" type="button">Competitor Statistics</button>
					</a>
					<br><br><br>
					<h4><b>Match Results:</b></h4>
					
					<c:forEach var="division" items="${match.divisionsWithResults }">
						<a href="${baseUrl}match/${match.id }/division/${division}">
							${division }
						</a>
					</c:forEach>
					<hr />
				</c:if>
				<h4><b>Stage Results:</b></h4>
				<div style="max-width: 750px">
					<table class="table table-striped table-bordered" id="stageTable">
						<thead>
							<tr>
								<th>
									Stage
								</th>
								<th>
									Division
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="stage" items="${match.stages}">
								
								<c:if test="${stage.deleted ne true }">
										<tr>
											<td style="width: 50%">
												${stage.name}
											</td>
											<td style="width: 50%">
												<c:forEach var="division" items="${match.divisionsWithResults }">
													<a href="${baseUrl }match/${match.id}/stage/${stage.id}/division/${division }">
														${division } 
													</a>
												</c:forEach>
											</td>
										</tr>
								</c:if>		
							</c:forEach>
						</tbody>
					</table>
				</div>
				<hr />

				<h4><b>Verify List:</b></h4>
				<div style="max-width: 470px">
					<table class="table table-striped table-bordered" id="competitorTable">
						<thead>
							<tr>
								<th>
									Competitor
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="competitor" items="${competitors}">
								<tr>
									<td style="width: 50%">
										
										<a href="${baseUrl}match/${match.id }/competitor/${competitor.id}">
											${competitor.lastName }, ${competitor.firstName} 
										</a>
									</td>
								</tr>		
							</c:forEach>
						</tbody>
					</table>
				</div>
				<hr />
			
			</c:otherwise>
		</c:choose>
	</div>
</div>
	
<script>
	$(document).ready(function() {
		$('#stageTable').DataTable( {
			paging: false,
			searching: false,
			sort: false,
			info: false
		});
		$('#competitorTable').DataTable( {
			paging: false,
			searching: true,
			sort: false,
			info: false
		});
	} );
</script>

<%@include file="/WEB-INF/jsp/include/loginLogoutScripts.jsp" %>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />