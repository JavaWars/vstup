<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>


<c:set var="title" value="Registration new User" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<script type="text/javascript">
	function validateEmail(email) {
		console.log("checking email "+email);
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	}

	function continueOrNot() {
		if ((validateEmail(document.getElementById('email').value))
				&&(checkTextField(document.getElementById('email')),'emailValidation')
				&&(passwordValidation(document.getElementById('password')),'passwordValidation')
				&&(checkTextField(document.getElementById('name')),'nameValidation')
				&&(checkTextField(document.getElementById('secondName')),'secondNameValidation'))
						{

			console.log("data form is valid ");
			return true;
		} else {
			$("#validationResult").text("form is not valid :(");
			$("#validationResult").css("color", "red");

			console.log("data form is not valid ");
			return false;
		}
	}


	function passwordValidation(field, validationResult) {
		console.log("checking password");
		if ((field.value == "") || (field.value.length > 30)
				|| ((field.value.length < 6))) {
			document.getElementById(validationResult).style.color = "red";
		} else {
			document.getElementById(validationResult).style.color = "rgb(100,100,200)";
			return true;
		}
		return false;
	}

	function checkEmail(field, validationResult) {
		console.log("checking email");
		if (!validateEmail(field.value) || (field.value == "")
				|| (field.value.length > 30)) {
			document.getElementById(validationResult).style.color = "red";
		} else {
			document.getElementById(validationResult).style.color = "rgb(100,100,200)";
			return true;
		}
		return false;
	}

	function checkTextField(field, validationResult) {
		console.log("checking fild");
		if ((field.value == "") || (field.value.length > 30)) {
			document.getElementById(validationResult).style.color = "red";
		} else {
			document.getElementById(validationResult).style.color = "rgb(100,100,200)";
			return true;
		}
		return false;
	}
</script>
<body>
	<div class="container theme-showcase" role="main">

	<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
	<div class="jumbotron row">

		<div class="col-md-3 col-md-offset-4">

			<fieldset>
				<legend>Welcome, new User! Please write information about
					yourself.</legend>

				<form action="${context}/registration" method="post"
					onsubmit="return continueOrNot()">
					<div>
						<p id="nameValidation">input your name</p>
						<input type="text" name="name"
							onblur="checkTextField(this,'nameValidation');" />
					</div>
					<div>
						<p id="secondNameValidation">input your second name</p>
						<input type="text" name="secondName" id="secondName"
							onblur="checkTextField(this,'secondNameValidation');" />
					</div>
					<div>
						<p id="emailValidation">email</p>

						<input type="text" name="email" id="email"
							onblur="checkEmail(this,'emailValidation');" />
					</div>

					<div>
						<p id="passwordValidation">password</p>

						<input type="password" name="password" id=""
							onblur="passwordValidation(this,'passwordValidation');" />
					</div>
					<div>
						<p>city</p>
						<input type="text" name="city" />
					</div>
					<div>
						<p>area</p>
						<input type="text" name="cityArea" />
					</div>
					<div>
						<input type="submit" value="create" /> <input type="reset"
							value="clear" /><!-- //continueOrNot -->
					</div>
					<p id="validationResult"></p>
				</form>
				<a href="${context}/login">already registered</a>

			</fieldset>
		</div>
	</div>
</div>

</body>
</html>