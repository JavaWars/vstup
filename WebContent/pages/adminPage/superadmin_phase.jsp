<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Phase" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>


<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>


	<div class="container theme-showcase" role="main">

		<div class="jumbotron row">
			<!--to page-proofs-->
			<br> <br> <br>

			<div>
				<tags:lang text="phase.tutorial"></tags:lang>
			</div>

			<div>
				<h4>
					<tags:lang text="phase.current"></tags:lang>
					<label>${phase}</label>
				</h4>
			</div>

			<div>
				<form action="phase" method="post">
					<tags:lang text="phase.choose"></tags:lang>
					<select name="phase">
						<option value="PRE_REGISTRATION">PRE_REGISTRATION</option>
						<option value="DOCUMENT_SERVE">DOCUMENT_SERVE</option>
						<option value="RESULT_CHECKING">RESULT_CHECKING</option>
					</select> <input type="submit" value="ok" />
				</form>
			</div>

		</div>
	</div>

</body>
</html>