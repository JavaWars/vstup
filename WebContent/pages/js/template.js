function validateEmail(email) {
	console.log("checking email " + email);
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
}

function passwordValidation(field, validationResult) {
	console.log("checking password");
	if ((field.value == "") || (field.value.length > 30)
			|| ((field.value.length < 6))) {
		document.getElementById(validationResult).style.color = "red";
		console.log("not valid"+field);
	} else {
		document.getElementById(validationResult).style.color = "rgb(100,100,200)";
		console.log("valid"+field);
		return true;
	}
	return false;
}

function checkEmail(field, validationResult) {
	console.log("checking email");
	if (!validateEmail(field.value) || (field.value == "")
			|| (field.value.length > 30)) {
		document.getElementById(validationResult).style.color = "red";
		console.log("not valid"+field);
	} else {
		document.getElementById(validationResult).style.color = "rgb(100,100,200)";
		console.log("valid"+field);
		return true;
	}
	return false;
}

function checkTextField(field, validationResult) {
	console.log("checking fild");
	if ((field.value == "") || (field.value.length > 60)) {
		document.getElementById(validationResult).style.color = "red";
		console.log("not valid"+field);
	} else {
		document.getElementById(validationResult).style.color = "rgb(100,100,200)";
		console.log("valid"+field);
		return true;
	}
	return false;
}
