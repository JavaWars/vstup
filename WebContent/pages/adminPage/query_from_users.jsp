<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Phase" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>


<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>
	<script type="text/javascript">
		function getQueryForDepartment() {
			console.log("getQueryForDepartment"
					+ $("#findDepartmentPattern option:selected").text());
			window.location.href = ("enterQueries?dep=" + $(
					"#findDepartmentPattern option:selected").text());
		}

		function confirmUserQuery(queryId) {
			console.log("confirmUserQuery" + queryId);
			
			if (confirm('<tags:lang text="areYouShure"></tags:lang>')) {

				$.ajax({
					type : "DELETE",
					url : "enterQueries?queriesId=" + queryId,
					success : function(data) {
						console.log("deleted!");
						$('table#unconfirmedQueriesList tr#' + queryId).remove();
					},
					error : function(data) {
					}
				});
			} else {
				// Do nothing!
			}
		}
	</script>

	<div class="container theme-showcase" role="main">

		<div class="jumbotron row">

			<div class="jumbotron">

				<div>
					<select id="findDepartmentPattern">
						<c:forEach var="department" items="${departments}">
							<option value="${department.id}">${department.name}</option>
						</c:forEach>
					</select>
					<button onclick="getQueryForDepartment()">Search</button>

					<table class="table" id="unconfirmedQueriesList">
						<thead>
							<tr>
								<th>#</th>
								<th><tags:lang text="fio"></tags:lang></th>
								<th><tags:lang text="diplom"></tags:lang></th>

								<th><tags:lang text="query_user.markName"></tags:lang></th>
								<th><tags:lang text="subjectMaxValue"></tags:lang></th>
								<th><tags:lang text="query_user.mark"></tags:lang></th>
								<th><tags:lang text="dockScreen"></tags:lang></th>
								<th><tags:lang text="operation"></tags:lang></th>
							</tr>
						</thead>
						<tbody>

							<c:forEach var="user" items="${users}">
								<tr id="${user.queryId}">
									<td>${user.queryId}</td>
									<td>${user.fio}</td>
									<td>${user.diplom}</td>

									<td>${user.markOriginalName}</td>
									<td>${user.maxPosibleMark}</td>
									<td>${user.mark}</td>
									<td><img height="300" width="300" src="${user.diplomImg}" /></td>
									<td><button onclick="confirmUserQuery('${user.queryId}')">
											<tags:lang text="confirm"></tags:lang>
										</button></td>
								</tr>
							</c:forEach>

						</tbody>
					</table>

				</div>

			</div>
		</div>
	</div>

</body>
</html>