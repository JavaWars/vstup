<%@ include file="/pages/jspf/directive/page.jspf"%>

<html>


<c:set var="title" value="Registration new User" scope="page" />
<%@ include file="/pages/jspf/directive/head.jspf"%>

<!-- autocomplite -->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!--end autocomplite -->

<!-- validations for email, fields... -->
<script type="text/javascript" src="pages/js/template.js"></script>

<script type="text/javascript">

	function continueOrNot() {
		if ((validateEmail(document.getElementById('email').value))
				&&(checkTextField(document.getElementById('email'),'emailValidation'))
				&&(checkTextField(document.getElementById('name'),'nameValidation'))
				&&(checkTextField(document.getElementById('secondName'),'secondNameValidation'))
				&&(passwordValidation(document.getElementById('password'),'passwordValidation')))
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

	  $(function() {
		    $( "#autocompleteCity" ).autocomplete({
		      source: function( request, response ) {
		        $.ajax({
		          url: "autocomplete/city",
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

	<div class="container theme-showcase" role="main">

		<div class="jumbotron row">

			<div class="col-md-3 col-md-offset-4">

				<fieldset>
					<legend>Welcome, new User! Please write information about
						yourself.</legend>

					<form action="registration" method="post"
						onsubmit="return continueOrNot()" >
						<div>
							<p id="nameValidation">input your name</p>
							<input type="text" name="name" id ="name"
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

							<input type="password" name="password" id="password"
								onblur="passwordValidation(this,'passwordValidation');" />
						</div>
						<div>
							<p>city</p>
							<div class="ui-widget">
								<input id="autocompleteCity" type="text" name="city">
							</div>

						</div>
						<div>
							<p>area</p>
							<input type="text" name="cityArea" />
						</div>
						<div>
							<input type="submit" value="create" /> <input type="reset"
								value="clear" />
						</div>
						<p id="validationResult"></p>
					</form>
					<a href="login">already registered</a>

				</fieldset>
			</div>
		</div>
	</div>

</body>
</html>