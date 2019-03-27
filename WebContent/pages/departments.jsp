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
			
			if (this.readyState == 4 && this.status == 500) {
				alert("oops, server error");
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
					<p>
						<tags:lang text="filter_sorter"></tags:lang>
					</p>
				</div>
				<p>
					<tags:lang text="departmentName"></tags:lang>
				</p>
				<input type="text" name="departmentName" /> <select name="sort">
					<option value="name a-z">name a-z</option>
					<option value="total>">places total max to min</option>
					<option value="gov>">places gov max to min</option>
					<option value="name z-a">name z-a</option>
					<option value="total<">places total min to max</option>
					<option value="gov<">places gov min to max</option>
				</select> <input type="submit" class="btn btn-primary "
					value="<tags:lang text="filter"></tags:lang>" />
			</form>

			<!-- FILTER AND SORTER FIN -->
			<table class="table">
				<thead>
					<tr>
						<th>#</th>
						<th><tags:lang text="department"></tags:lang></th>
						<th><tags:lang text="placesGov"></tags:lang></th>
						<th><tags:lang text="placesTot"></tags:lang></th>
						<th><tags:lang text="operation"></tags:lang></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${departmens}" var="dep">
						<tr>
							<th scope="row"></th>
							<td>${dep.name}</td>
							<td>${dep.placeGov}</td>
							<td>${dep.totaPlace}</td>
							<td><c:choose>
									<c:when test="${ROLE=='SUPERADMIN'}">
										<button type="button" class="btn btn-danger"
											onclick="deleteDepartment('delDepartment',${dep.id})">
											<tags:lang text="delete"></tags:lang>
										</button>
										<button type="button" class="btn btn-warning"
											onclick="go('editDepartment',${dep.id})">
											<tags:lang text="edit"></tags:lang>
										</button>

									</c:when>
									<c:when test="${ROLE=='USER'}">
										<button type="button" class="btn btn-success"
											onclick="go('enter',${dep.id})">
											<tags:lang text="enter"></tags:lang>
										</button>
									</c:when>
								</c:choose> <!-- for all user -->
								<button type="button" class="btn btn-primary "
									onclick="go('departmentRating',${dep.id})">
									<tags:lang text="rating"></tags:lang>
								</button></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</div>
	<%@include file="/pages/jspf/directive/footer.jspf"%>

</body>
</html>