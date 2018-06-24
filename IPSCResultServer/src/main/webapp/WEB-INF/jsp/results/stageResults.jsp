<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"  %>

<html>
	<head>
		<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
		<jsp:include page="/WEB-INF/jsp/include/dataTablesHeadTagLinks.jsp" />
	</head>
	<body>
<%-- 		<c:if test="${sessionScope.adtest.adTest eq true}">
			<div class="advertis-right">
				<a href="https://www.youtube.com/watch?v=pm-sWFKlu7w">
					<img src="<c:url value='/resources/images/cesar_wide_skyscraper.png' />" />
				</a>
			</div>
		</c:if> --%>
		<div id="wrap">
			<%-- <c:set var="divClass" value="container sideAdZonePageLeftHeaderColumn" />
			<c:set var="divClass" value="container" /> --%>
			<%-- <div class="${divClass }"> --%>
			<div class="container">
				<div class="sideAdZonePageLeftHeaderColumn">
					<%@ include file="/WEB-INF/jsp/include/pageTopAdZone.jsp" %>
					<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
					<ol class="breadcrumb breadcrumb-arrow">
						<li><a href="<c:url value='/' />">Home</a></li>
						<c:url var="matchPageUrl" value="/matchMainPage">
							<c:param name="matchId" value="${stageResultData.stage.match.practiScoreId }" />
						</c:url>
						<li><a href="${matchPageUrl}">Match</a></li>
						<li class="active"><span>Stage Results</span></li>
					</ol>
					<%@ include file="/WEB-INF/jsp/results/stageResultsPageHeader.jsp" %>
				</div>
				<div class="advertis-right" style="float: right; width=100%; ">
	 					<img src="<c:url value='/resources/images/cesar_wide_skyscraper.png' />" />
	 			</div>
	 			<br>
	 				<div style="clear:both"
	 					<%@ include file="/WEB-INF/jsp/results/stageResultsTable.jsp" %>
	 				</div>
					<%@ include file="/WEB-INF/jsp/include/pageBottomAdZone.jsp" %>
			</div>
 		</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />

<script>
	$(document).ready(function() {
		$('#stageResultTable').DataTable( {
			paging: false,
			searching: true,
			info: false
		});
	} );
	function submitStageListingChange() {
		
		var url = "${baseUrl}stageResults?matchId=${stageResultData.stage.match.practiScoreId }&stageId=" 
				+ $("select#stage").val() + "&division="+ $("select#division").val();
		location.replace(url);

	}
</script>


</body>
</html>
	

