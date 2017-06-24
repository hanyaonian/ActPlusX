$(function() {

	var password = $("#password");
    var password_again = $("#password-again");


	var isAllInfoRight = false;


    password.bind("blur", function(event) {
	    $("#passwordTip").hide();
	    if (password.val().length >= 6) return;
	    passwordAlert();
	    isAllInfoRight = false;
	    return;
    });

    password_again.bind("blur", function(event) {
        $("#password-againTip").hide();
        if (password_again.val() == password.val()) return;
        password_againAlert();
        isAllInfoRight = false;
        return;
    });



	//  提交按钮点击，进行检测，检测通过则post提交
    $("#submitButton").bind("click", function(event) {
        event.preventDefault();
        isAllInfoRight = true;
        password.blur();
        password_again.blur();


        if (!isAllInfoRight) {
            return;
        }

        $("form").submit();
    });


    function passwordAlert() {
        $("#passwordTip").show();
    }

    function password_againAlert() {
        $("#password-againTip").show();
    }
});