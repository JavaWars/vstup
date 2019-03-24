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
				if (xhr.readyState === 4 && this.status == 500) {
					alert("oops");
				}
			};
			xhttp.open("POST", url, true);
			xhttp.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			var data = "id=" + id;
			xhttp.send(data);
			reload();
		}
	</script>

	<div class="jumbotron container theme-showcasejumbotron" role="main">
		<c:if test="${ROLE=='ADMIN' && isPageForBlocking==true}">
			<div class="jumbotron row">
				<c:if test="${ROLE=='ADMIN'}">
				Set Marks for group for group
				mark type
				key kolumn
				value kolumn
				submit
				filter
			</c:if>
			</div>
		</c:if>
		<div class="jumbotron row">
			<div class="jumbotron bs-example" data-example-id="simple-table">
				<table class="table">
					<thead>
						<tr>
							<th>#</th>
							<th><tags:lang text="id"></tags:lang></th>
							<th><tags:lang text="fio"></tags:lang></th>
							<th><tags:lang text="userName"></tags:lang></th>
							<th><tags:lang text="user2Name"></tags:lang></th>
							<th><tags:lang text="email"></tags:lang></th>
							<%--
							<c:if test="${isPageForBlocking==true}">
								<th><tags:lang text="count"></tags:lang></th>
							</c:if>
							 --%>
							<th><tags:lang text="diplom"></tags:lang></th>
							<th><tags:lang text="operation"></tags:lang></th>
							<c:if test="${ROLE=='ADMIN' && isPageForBlocking==true}">
								<th>Set Mark</th>
							</c:if>
						</tr>
					</thead>
					<tbody>

						<c:forEach var="user" items="${users}">
							<tr>
								<th scope="row"></th>
								<td>${user.id}</td>
								<td>fio todo</td>
								<td>${user.name}</td>
								<td>${user.secondName}</td>
								<td>${user.email}</td>
								<td>todo diplom</td>
								<%--
								<c:if test="${isPageForBlocking==true}">
									<td>${user.departmentCount}</td>
								</c:if>
								 --%>
								<td><c:choose>
										<c:when test="${isPageForBlocking==true}">
											<button type="button" class="btn btn-danger"
												onclick="banUser('blockUser','${user.id}')">
												<tags:lang text="banUser"></tags:lang>
											</button>

										</c:when>
										<c:otherwise>
											<button type="button" class="btn btn-danger"
												onclick="banUser('unblockUser','${user.id}')">
												<tags:lang text="unBlockUser"></tags:lang>
											</button>
										</c:otherwise>
									</c:choose></td>
								<c:if test="${ROLE=='ADMIN' && isPageForBlocking==true}">
									<th>
										<ul>
											<li>1</li>
											<li>1</li>
										</ul>
									</th>
								</c:if>
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