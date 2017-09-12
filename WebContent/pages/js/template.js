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
