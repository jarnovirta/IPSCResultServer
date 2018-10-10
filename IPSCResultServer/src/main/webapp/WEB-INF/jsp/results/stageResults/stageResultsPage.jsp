<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"  %>

<html>
	<head>
		<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
		<jsp:include page="/WEB-INF/jsp/include/dataTablesHeadTagLinks.jsp" />
	</head>
	<body>
		<div id="wrap">
			<c:url var="baseUrl" value="/" />
			<c:if test="${sessionScope.adtest.adTest eq true}">
				<c:set var="divClass" value="container sideAdZonePageContainer" />
			</c:if>
			<c:if test="${sessionScope.adtest.adTest ne true}">
				<c:set var="divClass" value="container" />
			</c:if>
			
			<div class="${divClass }">
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<div class="sideAdZonePageLeftHeaderColumn">
				</c:if>
					<c:if test="${sessionScope.adtest.adTest eq true}">
						<%@ include file="/WEB-INF/jsp/adZones/pageTopAdZone.jsp" %>
					</c:if>
					<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
					<ol class="breadcrumb breadcrumb-arrow">
						<li><a href="<c:url value='/' />">Home</a></li>
						<c:url var="matchPageUrl" value="/matchMainPage">
							<c:param name="matchId" value="${stageResultData.stage.match.practiScoreId }" />
						</c:url>
						<li><a href="${matchPageUrl}">Match</a></li>
						<li class="active"><span>Stage Results</span></li>
					</ol>
					<%@ include file="/WEB-INF/jsp/results/stageResults/stageResultsPageHeader.jsp" %>
				<c:if test="${sessionScope.adtest.adTest eq true}">
					</div>
				</c:if>
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<div class="advertis-right" style="float: right; width=100%; ">
		 					<img src="<c:url value='/resources/images/cesar_wide_skyscraper.png' />" />
		 			</div>
		 		</c:if>
				
				<div style="clear:both">
					<%@ include file="/WEB-INF/jsp/results/stageResults/stageResultsTable.jsp" %>
				</div>
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<%@ include file="/WEB-INF/jsp/adZones/pageBottomAdZone.jsp" %>
				</c:if>
			</div>
		</div>
		<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
		
		<c:url var="nextPageUrl" value="/live">
			<c:param name="matchId" value="${stageResultData.stage.match.practiScoreId }" />
			<c:param name="previousStagePractiScoreId" value="${stageResultData.stage.practiScoreId }" />
			<c:param name="previousDivision" value="${stageResultData.division }" />
			
		</c:url>
		
		<script>
			$(document).ready(function() {
				$('#stageResultTable').dataTable( {
					paging: false,
					searching: true,
					info: false
				});
			} );
			
			function submitStageListingChange() {
				var url = "${baseUrl}stageResults?stageId=" + $("select#stage").val() + "&matchId=${stageResultData.stage.match.practiScoreId}&division=" + $("select#division").val();
				location.replace(url);
			}
		
		</script>
	</body>
</html>
	

