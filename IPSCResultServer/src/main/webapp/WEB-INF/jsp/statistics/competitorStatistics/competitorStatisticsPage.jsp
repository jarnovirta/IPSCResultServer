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
		<c:url var="baseUrl" value="/" />
		<div id="wrap">
			<div class="container">
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<%@ include file="/WEB-INF/jsp/adZones/pageTopAdZone.jsp" %>
				</c:if>
				<%@ include file="/WEB-INF/jsp/include/loginLogoutButtons.jsp" %>
				<ol class="breadcrumb breadcrumb-arrow">
					<li><a href="<c:url value='/' />">Home</a></li>
					<c:url var="matchPageUrl" value="/matchMainPage">
						<c:param name="matchId" value="${match.practiScoreId }" />
					</c:url>
					<li><a href="${matchPageUrl}">Match</a></li>
					<li class="active"><span>Competitor Statistics</span></li>
				</ol>
				<%@ include file="/WEB-INF/jsp/statistics/competitorStatistics/competitorStatisticsPageHeader.jsp" %>
				
				<%@ include file="/WEB-INF/jsp/statistics/competitorStatistics/competitorStatisticsTable.jsp" %>
				<c:if test="${sessionScope.adtest.adTest eq true}">
					<%@ include file="/WEB-INF/jsp/adZones/pageBottomAdZone.jsp" %>
				</c:if>
			</div>
		</div>

		<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
		
		<script>
			jQuery.extend( jQuery.fn.dataTableExt.oSort, {
			    "div-rank-asc": function ( a, b ) {
			    	if (a == 'DQ' || b == 'DQ') {
			    		return (a != 'DQ') ? -1 : (b != 'DQ') ? 1 : 0;
			    	}
			    	var aNum = new Number(a);
			    	var bNum = new Number(b);
			        return ((aNum < bNum) ? -1 : ((aNum > bNum) ? 1 : 0));
			    },
			 
			    "div-rank-desc": function ( a, b ) {
			    	if (a == 'DQ' || b == 'DQ') {
			    		return (a != 'DQ') ? 1 : (b != 'DQ') ? -1 : 0;
			    	}
			    	var aNum = new Number(a);
			    	var bNum = new Number(b);
			        return ((aNum < bNum) ? 1 : ((aNum > bNum) ? -1 : 0));
			    },
			    
			    "comp-name-pre": function (a) {
			    	var stripped = a.match(/<a[^>]*>([^<]+)<\/a>/)[1].trim();
			    	var splitName = stripped.split(" ");
			    	return splitName[splitName.length - 1];
			    },
			    "comp-name-asc": function ( a, b ) {
			    	return ((a < b) ? -1 : ((a> b) ? 1 : 0));
			    },
			    "comp-name-desc": function ( a, b ) {
			    	return ((a < b) ? 1 : ((a> b) ? -1 : 0));
			    }
			} );
			
			$(document).ready(function() {
				$('#statisticsTable').DataTable( {
					paging: false,
					searching: true,
					info: false, 
					  "columnDefs": [
						    { "type": "div-rank", "targets": 0 },
						    { "type": "comp-name", "targets": 1 },
		
						  ]
				});
			} );
			function submitDivisionChange() {
				var url = "${baseUrl}statistics?matchId=${match.practiScoreId }&division=" + $("select#division").val();
				location.replace(url);
			}
	</script>
	</body>
</html>
