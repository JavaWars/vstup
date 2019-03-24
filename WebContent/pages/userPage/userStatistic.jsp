<%@ page import="java.io.PrintWriter"%>
<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Users" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>
<script type="text/javascript">
function go(path,id){
	console.log(path);
	console.log(id);
	window.location.href = path+'?id='+id;
}
</script>
<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>

	<div class="jumbotron container theme-showcasejumbotron" role="main">
		<div class="jumbotron row">

			<table class="table">
				<thead>
					<tr>
						<th>#</th>
						<th><tags:lang text="department"></tags:lang></th>
						<th><tags:lang text="placesGov"></tags:lang></th>
						<th><tags:lang text="placesTot"></tags:lang></th>
						<th><tags:lang text="positionMy"></tags:lang></th>
						<th><tags:lang text="totalUserPass"></tags:lang></th>
						<th><tags:lang text="operation"></tags:lang></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${userPositionList}" var="userPos">
						<tr>
							<th scope="row"></th>
							<td>${userPos.name}</td>
							<td>${userPos.placeGov}</td>
							<td>${userPos.totaPlace}</td>
							<td>${userPos.myPlace}</td>
							<td>${userPos.totalPeople}</td>
							<td><c:choose>
									<c:when test="${ROLE=='USER'}">
										<button type="button" class="btn btn-primary "
											onclick="go('departmentRating',${userPos.id})">
											<tags:lang text="showList"></tags:lang>
										</button>
									</c:when>
								</c:choose></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>

		</div>
	</div>



</body>
</html>