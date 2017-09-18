<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="home" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<script type="text/javascript">
	function pdf(dep) {
		window.location.href = "pdf"+'?id='+dep;
	}
</script>

<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>
	<div class="container theme-showcase" role="main">
		<div class="jumbotron">

			<div class="jumbotron bs-example" data-example-id="simple-table">
				<table class="table">
					<thead>
						<tr>
							<th>#</th>
							<th><tags:lang text="userName"></tags:lang></th>
							<th><tags:lang text="user2Name"></tags:lang></th>
							<th><tags:lang text="ball"></tags:lang></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${depRating}" var="position">
							<tr>
								<th scope="row"></th>
								<td>${position.name}</td>
								<td>${position.secondName}</td>
								<td>${position.mark}</td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
			<button type="button" class="btn btn-primary "
				onclick="pdf(${id})">
				<tags:lang text="pdf"></tags:lang>
			</button>


		</div>
	</div>
	<%@include file="/pages/jspf/directive/footer.jspf"%>

</body>
</html>