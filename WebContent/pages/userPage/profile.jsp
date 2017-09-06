<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Users" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>

	<div class="jumbotron container theme-showcasejumbotron" role="main">
		<div class="jumbotron row">
			PRO FILE
			<%@ include file="/pages/jspf/directive/footer.jspf"%>
		</div>
	</div>



	<%@ include file="/pages/jspf/directive/footer.jspf"%>

</body>
</html>