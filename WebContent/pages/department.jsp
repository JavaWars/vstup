<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Department" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>

	<div class="container theme-showcase" role="main">
	<c:set var="context" value="${pageContext.request.contextPath}" />

		<div class="jumbotron row">

			<div class="col-md-2 col-md-offset-5">
				<p>DEPARTMENT</p>

				<form method="post" action="${context}/departament">
					<div>
						<p>Paces total</p>
						<input type="text" id="placesTotal" name="placesTotal" /></input>
					</div>
					<div>
						<p>Places Dov</p>
						<input type="text" id="placesDov" name="placesDov" /></input>
					</div>
					<div>
						<p>Department Name</p>
						<input type="text" id="departmentName" name="departmentName" /></input>
					</div>
					<div>

						<input type="submit" value="yes" class="btn btn-success" /> <input
							type="reset" value="clean" class="btn btn-primary"></input>
					</div>

				</form>

			</div>

		</div>

		<!-- ======================================
	MARKS for department
	====================================== -->

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
		<%@ include file="/pages/jspf/directive/footer.jspf"%>

	</div>


</body>
</html>