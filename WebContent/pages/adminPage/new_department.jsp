<%@ page import="java.io.PrintWriter"%>
<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Department" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<c:if test="${isEditPage}">
	<script type="text/javascript">
		var result;
		window.onload = function() {
			$.get("department?id=${id}", function(responseText) {
				result = JSON.parse(responseText);
				setDepartment(result.departmentName, result.totalPlaces,
						result.govPlace);
				depId = result.depId;

				for (var i = 0; i < result.marks.length; i++) {
					var mark = result.marks[i];
					insertMarkToList(mark.markName, mark.markScale, i);
				}

			});
		}
		function setDepartment(name, placesTotal, placesGov) {
			$("#departmentName").val(name);
			$("#placesTotal").val(placesTotal);
			$("#placesGov").val(placesGov);
		}
		function update(depId) {
			if (validateDep()) {
				var xhr = new XMLHttpRequest();
				var url = "editDepartment";
				xhr.open("POST", url, true);
				xhr.setRequestHeader("Content-type", "application/json");
				xhr.onreadystatechange = function() {
					if (xhr.readyState === 4 && xhr.status === 200) {
						window.location.href="departments";
					}
				};
				var myJsonString = JSON.stringify(list);
				var data = {
					json : list,
					departmentName : $("#departmentName").val(),
					placesTotal : $("#placesTotal").val(),
					placesGov : $("#placesGov").val(),
					id : depId
				};
				console.log(data);
				var res = JSON.stringify(data);
				xhr.send(res);
			}
		}
	</script>
</c:if>

<script type="text/javascript">
	var list = [];
	var nextId = 0;

	function clearMark() {
		$("#markName").val('');
		$("#markScale").val('');
	}

	function addRow() {
		if (validateNewMark()) {

			var markName = $("#markName").val();
			var markScale = $("#markScale").val();
			clearMark();

			nextId++;
			insertMarkToList(markName, markScale, nextId);
		}
	}

	function insertMarkToList(markName, markScale, idNext) {
		var newMark = {
			name : markName,
			scale : markScale,
			id : idNext
		};

		list.push({
			mark : newMark
		});

		$('#myTable')
				.append(
						'<tr id="'+idNext+'">'
								+ '<td>'
								+ idNext
								+ '</td>'
								+ '<td>'
								+ markName
								+ '</td>'
								+ '<td>'
								+ markScale
								+ '</td>'
								+ '<td><button class="btn btn-danger" onclick="removeRow('
								+ idNext + ')">remove</td>' + '</tr>');

	}

	function removeRow(id) {
		console.log("removed row with id=" + id);
		$('table#myTable tr#' + id).remove();

		for (var i = 0; i < list.length; i++) {
			var mark = list[i].mark;
			if (mark.id == id) {
				list.splice(i, 1);
			}

		}
	}

	function create() {
		if (validateDep()) {
			var xhr = new XMLHttpRequest();
			var url = "/vstup/newDepartment";
			xhr.open("POST", url, true);
			xhr.setRequestHeader("Content-type", "application/json");
			xhr.onreadystatechange = function() {
				if (xhr.readyState === 4 && xhr.status === 200) {
					console.log("ok");
					window.location.href = "departments";
				}
			};
			var myJsonString = JSON.stringify(list);
			var data = {
				json : list,
				departmentName : $("#departmentName").val(),
				placesTotal : $("#placesTotal").val(),
				placesGov : $("#placesGov").val()
			};
			var res = JSON.stringify(data);
			console.log("sending data");
			xhr.send(res);
		}

	}

	function validateDep() {
		if ((checkTextField(document.getElementById('departmentName'),
				'departmentNameValidation'))
				&& (checkTextField(document.getElementById('placesTotal'),
						'placesTotalValidation'))
				&& (checkTextField(document.getElementById('placesGov'),
						'placesGovValidation'))) {

			console.log("data form is valid ");
			return true;
		} else {

			console.log("data form is not valid ");
			return false;
		}
	}
	function validateNewMark() {
		if ((checkTextField(document.getElementById('markName'),
				'markValidation'))
				&& (checkTextField(document.getElementById('markScale'),
						'markValidation'))) {

			console.log("data for mark is valid ");
			return true;
		} else {

			console.log("data for mark is not valid ");
			return false;
		}
	}
	function checkTextField(field, validationResult) {

		if ((field.value == "") || (field.value.length > 30)) {

			document.getElementById(validationResult).style.color = "red";
			console.log("is not valid " + validationResult);
		} else {
			document.getElementById(validationResult).style.color = "rgb(100,100,200)";
			console.log("is valid " + validationResult);
			return true;
		}
		return false;
	}
</script>


<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>

	<!-- <script type="text/javascript" src="/pages/js/template.js"></script>
 -->

	<div class="container theme-showcase" role="main">
		<c:set var="context" value="${pageContext.request.contextPath}" />

		<div class="jumbotron row">

			<div class="jumbotron col-md-4 col-md-offset-4">
				<p>DEPARTMENT</p>
				<%-- 
				<form method="post" action="${context}/departament">
 --%>
				<div>
					<p id="departmentNameValidation">Department Name</p>
					<input type="text" id="departmentName" name="departmentName"
						onblur="checkTextField(this,'departmentNameValidation');" /></input>
				</div>
				<div>
					<p id="placesTotalValidation">Places total</p>
					<input type="number" id="placesTotal" name="placesTotal"
						onblur="checkTextField(this,'placesTotalValidation');" /></input>
				</div>
				<div>
					<p id="placesGovValidation">Places Gov</p>
					<input type="number" id="placesGov" name="placesGov"
						onblur="checkTextField(this,'placesGovValidation');" /></input>
				</div>

				<!-- </form> -->

			</div>
			<div>
				<!-- ======================================
	MARKS for department
	====================================== -->

				<div class="bs-example" data-example-id="simple-table">
					<table class="table" id="myTable">
						<tr>
							<th>#</th>
							<th>Mark name</th>
							<th>mark scale</th>
							<th>Operations</th>
						</tr>
						<tbody></tbody>
						<!--rows will be generated here (using jquery, see function add row)-->
						<!-- new mark -->

						<tr>
							<th scope="row"></th>
							<td><input type="text" id="markName" /></td>
							<td><input type="number" id="markScale" /></td>
							<td>
								<!-- 
					admin want add this mark to table
					 -->
								<button type="button" class="btn btn-primary" onclick="addRow()">ADD</button>
								<!-- Indicates a successful or positive action -->
								<p id="markValidation"></p>

							</td>
						</tr>
					</table>
				</div>
				<!-- END PLACES FOR MARKS -->
				<div>
					<!--MAIN BTN (admin want create department or update) -->

					<c:choose>
						<c:when test="${isEditPage==true}">
							<button value="yes" class="btn btn-success" onclick="update(${id})">update</button>
						</c:when>
						<c:otherwise>
							<button value="yes" class="btn btn-success" onclick="create()">create</button>
						</c:otherwise>
					</c:choose>

				</div>
			</div>
		</div>
	</div>

	<%@ include file="/pages/jspf/directive/footer.jspf"%>
</body>
</html>