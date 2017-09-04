<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="home" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>
<div class="container theme-showcase" role="main">

	<br>
	<div>
		<p>USER PAGE</p>
	</div>
	<br>

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
					<th scope="row">row number</th>
					<td>KIU</td>
					<td>101</td>
					<td>200</td>
					<td>
						<!--==================== 
					admins commands
					======================== --> <!-- Standard button -->
						<button type="button" class="btn btn-primary">Edit</button> <!-- Indicates a successful or positive action -->
						<button type="button" class="btn btn-danger">Delete</button> <!-- Deemphasize a button by making it look like a link while maintaining button behavior -->
						<!--==================== 
					admins commands end
					======================== -->
					</td>
				</tr>



			</tbody>
		</table>

		<button type="button" class="btn btn-default">Create new
			department</button>
		<!-- Provides extra visual weight and identifies the primary action in a set of buttons -->
	</div>
</div>
	<%@include file="/pages/jspf/directive/footer.jspf"%>

</body>
</html>