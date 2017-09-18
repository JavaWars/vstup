<%@ page import="java.io.PrintWriter"%>
<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Users" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

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
						<th><tags:lang text="subject"></tags:lang></th>
						<th><tags:lang text="markMy"></tags:lang></th>
						<!-- <th>operation</th> -->
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${subjects}" var="subject">
						<tr>
							<th scope="row"></th>
							<td>${subject.name}</td>
							<td>${subject.mark}</td>
							<%-- <td><c:choose>
									<c:when test="${ROLE=='USER'}">
										<button type="button" class="btn btn-primary "
											onclick="go('subjectEdit',${subject.id})">edit</button>
									</c:when>
								</c:choose></td> --%>
						</tr>
					</c:forEach>

				</tbody>
			</table>

		</div>
	</div>



	<%@ include file="/pages/jspf/directive/footer.jspf"%>

</body>
</html>