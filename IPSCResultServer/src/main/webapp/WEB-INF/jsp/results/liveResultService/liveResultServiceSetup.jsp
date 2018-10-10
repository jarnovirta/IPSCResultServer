<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
		<jsp:include page="/WEB-INF/jsp/include/headTag.jsp" />
		<jsp:include page="/WEB-INF/jsp/include/dataTablesHeadTagLinks.jsp" />
	</head>
	<body>
		<div id="wrap">
			<div class="container">
				<ol class="breadcrumb breadcrumb-arrow">
					<li><a href="<c:url value='/' />">Home</a></li>
					<c:url var="matchPageUrl" value="/matchMainPage">
						<c:param name="matchId" value="${match.practiScoreId }" />
					</c:url>
					<li><a href="${matchPageUrl}">Match</a></li>
					<li class="active"><span>Live Results</span></li>
				</ol>
				<div class="page-header">
					<h1>Live Result Service</h1>
				</div>
				
				<form:form method="POST" modelAttribute="liveResultServiceConfig">
          			<table>
          				<tr>
          					<td>
          					<form:label path="matchPractiScoreId">Match</form:label>
          					</td>
          					<td>
			          			<form:select path="matchPractiScoreId">
			          				<c:forEach var="matchOption" items="${matches }">
			          					<form:option value="${matchOption.practiScoreId}">${matchOption.name }</form:option>
			          				</c:forEach>
			          			</form:select>		
          					</td>
          				</tr>
          				<tr>
          					<td>
          						<form:label path="resultTableRows">Rows per result table page</form:label>
          					</td>
          					<td>
          						<form:input path="resultTableRows" />
          					</td>
          					
          				</tr>
          				<tr>
          					<td>
          						<form:label path="pageChangeInterval">Result view change interval (secs.)</form:label>
          					</td>
          					<td>
          						<form:input path="pageChangeInterval" />
          					</td>
          					
          				</tr>
          				<tr>
          					<td>
          						<input type="submit" class="btn btn-primary" value="Start"/></td>
          					</td>
          				</tr>
          			</table>
          		</form:form>
				
			</div>
		</div>
		<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
	</body>
</html>