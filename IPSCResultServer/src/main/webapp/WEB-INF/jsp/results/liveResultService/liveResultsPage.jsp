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
	<div id="wrap">
		<div class="container">
			<div class="panel panel-info">
				 <div class="panel-heading">Live Result Service</div>
				  	<div class="panel-body">
						        <table class="oneColumnPageInfo">
						        	<tr>
						        		<td>
						        			<b>Match:</b>
						        		</td>
						        		<td>
						        			<b>${stageResultData.stage.match.name}</b>
					        		</td>
					        	</tr>
					        	<tr>
					        		<td>
					        			<b>Division:</b>
					        		</td>
					        		<td>
					        			<c:choose>
					        				<c:when test="${empty stageResultData.division}">
					        					--
					        				</c:when>
					        				<c:otherwise>
					        					${stageResultData.division}
					        				</c:otherwise>
					        			</c:choose>
									</td>
					        	</tr>
					        	<tr>
					        		<td>
					        			<b>Stage:</b>
					        		</td>
					        		<td>
					        			
					        			<c:choose>
					        				<c:when test="${empty stageResultData.stage.name}">
					        					--
					        				</c:when>
					        				<c:otherwise>
					        					${stageResultData.stage.stageNumber } - ${stageResultData.stage.name}
					        				</c:otherwise>
					        			</c:choose>
					        		</td>
					        	</tr>					        	
					        </table>
					</div>
				</div>
			<%@ include file="/WEB-INF/jsp/results/stageResults/stageResultsTable.jsp" %>
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