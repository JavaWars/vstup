<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Error" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<body>

	<div class="container theme-showcase" role="main">

		<c:if test="${not empty requestScope.errorMessage}">
			<h3>${requestScope.errorMessage}</h3>
		</c:if>

		<%-- this way we obtain an information about an exception (if it has been occurred) --%>
		<c:set var="code"
			value="${requestScope['javax.servlet.error.status_code']}" />
		<c:set var="message"
			value="${requestScope['javax.servlet.error.message']}" />
		<c:set var="exception"
			value="${requestScope['javax.servlet.error.exception']}" />
		<p class="bg-danger">

			<c:if test="${not empty code}">
				<p class="bg-danger">Error code: ${code}</p>
			</c:if>

			<c:if test="${not empty message}">
				<p class="bg-danger">Message: ${message}</p>
			</c:if>

			<c:if test="${not empty exception}">
				<p class="bg-danger">

					<%
						exception.printStackTrace(new PrintWriter(out));
					%>
				</p>
			</c:if>

		</p>


		<br>
		<a href="home">Home</a>

	</div>
</body>
</html>