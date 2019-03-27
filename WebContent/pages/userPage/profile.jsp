<%@ page import="java.io.PrintWriter"%>
<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<c:set var="title" value="Users" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>
<script type="text/javascript">
	$(document).ready(function() {
		checking();
	});

	function checking() {

		$('input:file').filter(function() {
			console.log("checking!");
			$('#sendFile').prop('disabled', (this.files.length == 0))

		});
	}
	
	function updateUserMainData(){
		console.log("update user main data"+$("#userPersonalFio").val()+"|"+$("#userPersonalDiplom").val());
	
		var datamodel = {
	            fio : $("#userPersonalFio").val(),
	            diplom : $("#userPersonalDiplom").val()
	         };
	         $.ajax({
	            type : "POST",
	            url : "UserUpdateBaseInfo",
	            data : datamodel,
	            success : function(data) {
	               alert("ok");
	            },
	            error : function(xhr, ajaxOptions, thrownErro) {
	            	alert("error "+xhr.responseText);
	            }
	         });
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
						</tr>
					</c:forEach>

				</tbody>
			</table>

		</div>

		<div>
			<div>
				<tags:lang text="userPersonalInfo"></tags:lang>
				<tags:lang text="fio"></tags:lang>
				<input id="userPersonalFio" value="${profile.fio}">
				<tags:lang text="diplom"></tags:lang>
				<input id="userPersonalDiplom" value="${profile.diplom}" pattern="[a-zA-Z]{2}[0-9]{8}">
				<button onclick="updateUserMainData()" class ="button btn-warning">
					<tags:lang text="update"></tags:lang>
				</button>
			</div>
			<div>
				<tags:lang text="selectFile"></tags:lang>
			</div>
			<form action="userImgCatcher" method="post"
				enctype="multipart/form-data">
				<input type="file" name="file" size="50" onchange="checking()" /> <br />
				<input type="submit" id="sendFile" class ="button btn-warning"
					value='<tags:lang text="uploadFile"></tags:lang>' />
			</form>

			<c:if test="${file!=null}">
				<img alt="img" src="${file}" height="300" width="300"
					accept="image/x-png,image/gif,image/jpeg" />
			</c:if>
		</div>

	</div>


	<%@ include file="/pages/jspf/directive/footer.jspf"%>

</body>
</html>