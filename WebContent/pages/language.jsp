<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="home" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<script type="text/javascript">
	
</script>

<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>
	<div class="container theme-showcase" role="main">
		<div class="jumbotron">
			<form action="language" method="post" class="jumbotron">
				<p><tags:lang text="selectLang"></tags:lang></p>
				<select name="language">
					<option value="ru"><tags:lang text="russian"></tags:lang></option>
					<option value="en"><tags:lang text="english"></tags:lang></option>
				</select> <input type="submit" value="ok" />
			</form>
		</div>
	</div>

</body>
</html>