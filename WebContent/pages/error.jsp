<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Error" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<body>

	<div class="container theme-showcase" role="main">

		<%-- HEADER --%>
		<%@ include file="/pages/jspf/directive/header.jspf"%>
		<%-- HEADER --%>

		<c:if test="${not empty requestScope.errorMessage}">
			<h3>${requestScope.errorMessage}</h3>
		</c:if>

		<%@ include file="/pages/jspf/directive/footer.jspf"%>

	</div>
</body>
</html>