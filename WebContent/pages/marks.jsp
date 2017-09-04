<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Marks" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>

	<!-- 
		select mark and number -->


	<!-- ======================================
	MARKS for department
	====================================== -->
	<div class="container theme-showcase" role="main">

		<div class="jumbotron row">

			<div class="col-md-4 col-md-offset-3">

				<div class="bs-example" data-example-id="simple-table">
					<table class="table">
						<thead>
							<tr>
								<th>#</th>
								<th>Mark name</th>
								<th>mark scale</th>
								<th>Operations</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="row">1</th>
								<td>math</td>
								<td>0.3</td>
								<td>
									<!-- 
					user want give documents
					 -->
									<button type="button" class="btn btn-danger">Remove</button> <!-- Indicates a successful or positive action -->

								</td>
							</tr>

							<!-- new mark -->

							<tr>
								<th scope="row"></th>
								<td><input type="text" id="newMark" name="newMark" /></td>
								<td><input type="number" id="newMark" name="newMark" /></td>
								<td>
									<!-- 
					user want give documents
					 -->
									<button type="button" class="btn btn-primary">ADD</button> <!-- Indicates a successful or positive action -->

								</td>
							</tr>


						</tbody>
					</table>
				</div>

			</div>
		</div>
			<%@ include file="/pages/jspf/directive/footer.jspf"%>
		
	</div>

</body>
</html>