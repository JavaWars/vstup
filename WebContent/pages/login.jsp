<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>

<%@ include file="/pages/jspf/directive/head.jspf"%>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<script type="text/javascript">
	function validateEmail(email) {
		console.log("checking email " + email);
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	}

	function continueOrNot() {
		if (validateEmail(document.getElementById('email').value)) {
			return true;
		} else {
			$("#validationResult").text("email is not valid :(");
			$("#validationResult").css("color", "red");
			return false;
		}
	}
</script>

<body>
	<div class="container theme-showcase" role="main">


		<div class="jumbotron row">

			<div class="col-md-2 col-md-offset-5">

				<c:set var="context" value="${pageContext.request.contextPath}" />

				<form action="${context}/login" method="post"
					onsubmit="return continueOrNot()">
					<div>
						<tags:lang text="registration.email"></tags:lang>
						<input type="text" name="email" id='email' />
					</div>
					<div>
						<tags:lang text="registration.password"></tags:lang>
						<input type="password" name="password" />
					</div>
					<div>
						<button type="submit" id='validate' class="btn btn-success">
							<tags:lang text="registration.login"></tags:lang>
						</button>
					</div>
					<a href="${context}/registration"><tags:lang
							text="registration.i_am_new_user"></tags:lang></a>

				</form>
			</div>

			<h2 id='validationResult'></h2>
		</div>
	</div>

</body>
</html>