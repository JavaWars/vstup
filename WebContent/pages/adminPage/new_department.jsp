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
					insertMarkToList(mark.markName, mark.markMaxValue,mark.isUserEntered, i);
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
					if (xhr.readyState === 4 && this.status == 500) {
						alert("oops"+xhr.responseText);
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

<script type="text/javascript" src="pages/js/template.js"></script>

<script type="text/javascript">
	var list = [];
	var nextId = 0;

	function clearMark() {
		$("#markName").val('');
		$("#markMaxValue").val('');
		$("#markWillBeSetByUser").prop('checked', false);
	}

	function addRow() {
		if (validateNewMark()) {

			console.log($("#markWillBeSetByUser").prop("checked"));
			var markName = $("#markName").val();
			var markMaxValue = $("#markMaxValue").val();
			var markEnteredByUser=$("#markWillBeSetByUser").prop("checked");
			clearMark();

			nextId++;
			insertMarkToList(markName, markMaxValue,markEnteredByUser, nextId);
		}
	}

	function insertMarkToList(markName, markMaxValue,ismarkEnteredByUser, idNext) {
		var newMark = {
			name : markName,
			markMaxValue : markMaxValue,
			is_user_entered : ismarkEnteredByUser,
			id : idNext
		};

		list.push({
			mark : newMark
		});

		var isUserS;
		if (ismarkEnteredByUser) isUserS='<tags:lang text="enteredByUser"></tags:lang>';
		else isUserS='<tags:lang text="enteredByUniversity"></tags:lang>';
		
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
								+ markMaxValue
								+ ','+isUserS
								+ '</td>'
								+ '<td><button class="btn btn-danger" onclick="removeRow('
								+ idNext + ')"><tags:lang text="remove"></tags:lang></td>' + '</tr>');

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
			var url ="newDepartment";
			xhr.open("POST", url, true);
			xhr.setRequestHeader("Content-type", "application/json");
			xhr.onreadystatechange = function() {
				if (xhr.readyState === 4 && xhr.status === 200) {
					console.log("ok");
					window.location.href = "departments";
				}
				if (xhr.readyState === 4 && this.status == 500) {
					alert("oops,"+xhr.responseText);
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
				&& (checkTextField(document.getElementById('markMaxValue'),
						'markValidation'))) {

			console.log("data for mark is valid ");
			return true;
		} else {

			console.log("data for mark is not valid ");
			return false;
		}
	}
</script>


<!-- autocomplite -->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!--end autocomplite -->

<script type="text/javascript">
$(function() {
    $( "#markName" ).autocomplete({
      source: function( request, response ) {
        $.ajax({
          url: "autocomplete/subject",
          dataType: "json",
          data: {
            term: request.term
          },
          success: function( data ) {
            response( data );
            console.log(data);
          }
        });
      },
      minLength: 2,
      select: function( event, ui ) {
        console.log( ui.item ?
          "Selected: " + ui.item.label :
          "Nothing selected, input was " + this.value);
      },
    });
  });
</script>

<body>

	<%-- HEADER --%>
	<%@ include file="/pages/jspf/directive/header.jspf"%>
	<%-- HEADER --%>


	<div class="container theme-showcase" role="main">

		<div class="jumbotron row">

			<div class="jumbotron col-md-4 col-md-offset-4">
				<p>
					<tags:lang text="department"></tags:lang>
				</p>

				<div>
					<p id="departmentNameValidation">
						<tags:lang text="departmentName"></tags:lang>
					</p>
					<input type="text" id="departmentName" name="departmentName"
						onblur="checkTextField(this,'departmentNameValidation');" /></input>
				</div>
				<div>
					<p id="placesTotalValidation">
						<tags:lang text="placesTot"></tags:lang>
					</p>
					<input type="number" min="0" max="999" maxlength="3"
						id="placesTotal" name="placesTotal"
						onblur="checkTextField(this,'placesTotalValidation');" /></input>
				</div>
				<div>
					<p id="placesGovValidation">
						<tags:lang text="placesGov"></tags:lang>
					</p>
					<input type="number" min="0" max="999" maxlength="3" id="placesGov"
						name="placesGov"
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
							<th><tags:lang text="subject"></tags:lang></th>
							<th><tags:lang text="subjectMaxValue"></tags:lang></th>
							<th><tags:lang text="operation"></tags:lang></th>
						</tr>
						<tbody></tbody>
						<!--rows will be generated here (using jquery, see function add row)-->
						<!-- new mark -->

						<tr>
							<th scope="row"></th>
							<td>
								<div class="ui-widget">
									<input id="markName" type="text" name="markName">
								</div>
							</td>
							<td><input type="number" id="markMaxValue" min="0" max="200"
								maxlength="3" /></td>

							<td><input type="checkbox" id="markWillBeSetByUser">
							<tags:lang text="user_defined"></tags:lang>
								<button type="button" class="btn btn-primary" onclick="addRow()">
									<tags:lang text="add"></tags:lang>
								</button> <!-- Indicates a successful or positive action -->
								<p id="markValidation"></p></td>
						</tr>
					</table>
				</div>
				<!-- END PLACES FOR MARKS -->
				<div>
					<!--MAIN BTN (admin want create department or update) -->

					<c:choose>
						<c:when test="${isEditPage==true}">
							<button value="yes" class="btn btn-success"
								onclick="update(${id})">
								<tags:lang text="update"></tags:lang>
							</button>
						</c:when>
						<c:otherwise>
							<button value="yes" class="btn btn-success" onclick="create()">
								<tags:lang text="create"></tags:lang>
							</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="/pages/jspf/directive/footer.jspf"%>
</body>
</html>