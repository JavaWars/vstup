<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="home" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<script type="text/javascript">
		function go(path,departmentId) {
			console.log(path+' '+departmentId);
			window.location.href = path+'?id='+departmentId;
		}
		
		function deleteDepartment(path,departmentId){
			console.log(path+' '+departmentId);
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				window.location.href="departments";
			}
			};
			xhttp.open("POST", path, true);
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			var data="id="+departmentId;
			xhttp.send(data); 
		}
</script>

<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>
	<div class="container theme-showcase" role="main">
		<div class="jumbotron">

			<!-- FILTER AND SORTER START -->

			<form action="departments" method="get" class="jumbotron">
				<div>
					<p>FILTER and SORTER</p>
				</div>
				<p>department name</p>
				<input type="text" name="departmentName" /> <select name="sort">
					<option value="name a-z">name a-z</option>
					<option value="total>">places total max to min</option>
					<option value="gov>">places gov max to min</option>
					<option value="name z-a">name z-a</option>
					<option value="total<">places total min to max </option>
					<option value="gov<">places gov min to max</option>
				</select> <input type="submit" />
			</form>

			<!-- FILTER AND SORTER FIN -->
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
								<th scope="row"></th>
								<td>${dep.name}</td>
								<td>${dep.placeDov}</td>
								<td>${dep.totaPlace}</td>
								<td><c:choose>
										<c:when test="${ROLE=='ADMIN'}">
											<button type="button" class="btn btn-primary "
												onclick="deleteDepartment('delDepartment',${dep.id})">delete</button>
											<button type="button" class="btn btn-primary "
												onclick="go('editDepartment',${dep.id})">edit</button>
										</c:when>

										<c:when test="${ROLE=='USER'}">
											<button type="button" class="btn btn-primary "
												onclick="go('enter',${dep.id})">enter</button>
										</c:when>
									</c:choose>
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