<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="home" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

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
							<th>Department</th>
							<th>Places for gov money</th>
							<th>Places total</th>
							<th>Operations</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${departmens}" var="dep">
							<tr>
								<th scope="row">row number</th>
								<td>${dep.name}</td>
								<td>${dep.placeDov}</td>
								<td>${dep.totaPlace}</td>
								<td>
									<!-- operations -->
									 <c:forEach items="${oper}" var="o">
										<button type="button" class="btn btn-primary ">${o.operation}${o.path}</button>
									</c:forEach> <!--  end operations-->
								</td>
							</tr>
						</c:forEach>


					</tbody>
				</table>


			</div>
		</div>
	</div>
	<%@include file="/pages/jspf/directive/footer.jspf"%>

</body>
</html>