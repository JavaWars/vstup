<%@ page import="java.io.PrintWriter"%>
<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Marks" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<script type="text/javascript" src="pages/js/template.js"></script>

<script type="text/javascript">
	var list = [];
	function insertMarkToList(markNumber, idMark) {
		var newMark = {
			userMark : markNumber,
			id : idMark
		};

		list.push({
			mark : newMark
		});
	}

	function enter() {
		var ok = true;
		var collection = $(".studentMark");
		collection.each(function() {
			insertMarkToList($(this).val(), $(this).attr('id'));
			console.log($(this).attr('id'));//mark id
			console.log($(this).val());//value from user (user mark)
			if (!checkTextField($(this).get()[0], $(this).attr('id')
					+ 'Validation')) {
				ok = false;
			}
		});
		if (ok) {
			console.log("data is correct");
			sendMarks();
		} else {
			console.log("data is not correct");
			list = [];
		}
	}

	function sendMarks() {

		var xhr = new XMLHttpRequest();
		var url = "userMark";
		xhr.open("POST", "userMark", true);
		xhr.setRequestHeader("Content-type", "application/json");
		xhr.onreadystatechange = function() {
			if (xhr.readyState === 4 && xhr.status === 200) {
				console.log("ok");
				window.location.href = "home";
			}
			if (xhr.readyState === 4 && this.status == 500) {
				alert("oops, something wrong");
			}
		};
		var myJsonString = JSON.stringify(list);
		var data = {
			json : list,
			departmentId : ${requestScope.departmnetId}
		};
		var res = JSON.stringify(data);
		xhr.send(res);

	}
</script>

<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>

	<!-- 
		select mark and number -->

	<div class="container theme-showcase" role="main">

		<div class="jumbotron row">

			<div class="jumbotron col-md-4 col-md-offset-4">

				<div>
					<p id="departmentNameDescription">
						<tags:lang text="department"></tags:lang>
					</p>
					<p id="departmentName">${department.name}</p>
				</div>
				<div>
					<p id="departmentPlacesTotalDescription">
						<tags:lang text="placesTot"></tags:lang>
					</p>
					<p id="departmentPlaces">${department.totaPlace}</p>
				</div>
				<div>
					<p id="departmenPlacesGov">
						<tags:lang text="placesGov"></tags:lang>
					</p>
					<p id="departmentName">${department.placeGov}</p>
				</div>

			</div>
			<div>
				<!-- ======================================
	MARKS for department
	====================================== -->

				<div class="bs-example" data-example-id="simple-table">
					<table class="table" id="myTable">
						<tr>
							<th>#</th>
							<th><tags:lang text="subject"></tags:lang></th>
							<th><tags:lang text="markMy"></tags:lang></th>
						</tr>
						<tbody>
							<!--marks to set-->
							<c:forEach items="${marks}" var="mark">
								<tr>
									<td></td>
									<td><p id="${mark.id}Validation">${mark.name}</p></td>
									<td><input type="number" min="0" max="100" maxlength="3" class="studentMark"
										id="${mark.id}" /></td>
								</tr>
							</c:forEach>
						</tbody>
						<!--rows will be generated here (using jquery, see function add row)-->
						<!-- new mark -->

					</table>
				</div>
				<!-- END PLACES FOR MARKS -->
				<div>
					<!--MAIN BTN (user want set marks) -->

					<button value="enter my marks" class="btn btn-success"
						onclick="enter()">
						<tags:lang text="confirm"></tags:lang>
					</button>
				</div>
			</div>
		</div>
	</div>

</body>
</html>