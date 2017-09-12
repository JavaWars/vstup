<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Users" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>

	<script type="text/javascript">	
		function banUser(url, id) {
			
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
			    location.reload();
			}
			};
			xhttp.open("POST", url, true);
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			var data="id="+id;
			xhttp.send(data); 
			reload();
		}
	</script>

	<div class="jumbotron container theme-showcasejumbotron" role="main">
		<div class="jumbotron row">
			<div class="jumbotron bs-example" data-example-id="simple-table">
				<table class="table">
					<thead>
						<tr>
							<th>#</th>
							<th>Id</th>
							<th>User name</th>
							<th>User second name</th>
							<th>Email</th>
							<th>Area</th>
							<th>Options</th>

						</tr>
					</thead>
					<tbody>

						<c:forEach var="user" items="${users}">
							<tr>
								<th scope="row"></th>
								<td>${user.id}</td>
								<td>${user.name}</td>
								<td>${user.secondName}</td>
								<td>${user.email}</td>
								<td>${user.cityArea}</td>
								<td>
									<!--==================== 
					admins commands
					======================== --> <!-- Standard button -->
									<button type="button" class="btn btn-danger"
										onclick="banUser('${href}','${user.id}')">${action}</button> <!-- Deemphasize a button by making it look like a link while maintaining button behavior -->
									<!--==================== 
					admins commands end
					======================== -->
								</td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="/pages/jspf/directive/footer.jspf"%>
</body>
</html>