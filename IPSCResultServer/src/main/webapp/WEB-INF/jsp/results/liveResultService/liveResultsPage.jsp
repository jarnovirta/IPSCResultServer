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
	<body class="liveResultService">
	<div id="wrap">
		<div class="container">
			<%@ include file="/WEB-INF/jsp/results/liveResultService/liveResultServicePageHeader.jsp" %>
			<div class="liveResultServiceResultsTable">
				<%@ include file="/WEB-INF/jsp/results/stageResults/stageResultsTable.jsp" %>
			</div>
		</div>
	</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />

<c:url var="nextPageUrl" value="/live/nextPage">
	<c:param name="matchId" value="${stageResultData.stage.match.practiScoreId }" />
	<c:param name="previousStagePractiScoreId" value="${stageResultData.stage.practiScoreId }" />
	<c:param name="previousDivision" value="${stageResultData.division }" />
	
</c:url>

<script>
	$(document).ready(function() {
		$('#stageResultTable').dataTable( {
			paging: true,
			searching: false,
			info: false,
			pageLength: Number('${config.resultTableRows}'),
			lengthChange: false
		});
			
			var tablePage = 0;
			setInterval(function(){ 
				var resultTable = $('#stageResultTable').DataTable();
				tablePage++;
				
				if (tablePage + 1 > Number(resultTable.page.info().pages)) {
					location.replace('${nextPageUrl}');
				}
				else 
				{
					resultTable.page(tablePage).draw('page');
				}
			}, 1000 * Number('${config.pageChangeInterval}'));
				
	} );

</script>  


</body>
</html>