<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="home" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<body>
	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>

	<div class="container ">

		<div>
			<br>
			<div>
				<p>USER PAGE</p>
			</div>
			<br>

			<div>
				Sort: <select>
					<option>by department</option>
					<option>by gov money places</option>
					<option>by laces total</option>
				</select>
			</div>

			<div class="bs-example" data-example-id="simple-table">
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
						<tr>
							<th scope="row">1</th>
							<td>Mark</td>
							<td>Otto</td>
							<td>@mdo</td>
							<td>
								<!-- 
					user want give documents
					 -->
								<button type="button" class="btn btn-primary">Edit</button> <!-- Indicates a successful or positive action -->

							</td>
						</tr>

					</tbody>
				</table>
			</div>
		</div>
	</div>

	<%@include file="/pages/jspf/directive/footer.jspf"%>

</body>
</html>