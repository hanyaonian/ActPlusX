$(function() {
	var name = $("#name");
	var password = $("#password");

	var isAllInfoRight = false;

    name.bind("blur", function(event) {
	    $("#nameTip").hide();
	    if (name.val().length != 0) return;
	    nameAlert();
	    isAllInfoRight = false;
	    return;
    });

    password.bind("blur", function(event) {
	    $("#passwordTip").hide();
	    if (password.val().length >= 6) return;
	    passwordAlert();
	    isAllInfoRight = false;
	    return;
    });

	//  提交按钮点击，进行检测，检测通过则post提交
    $("#submitButton").bind("click", function(event) {
        event.preventDefault();
        isAllInfoRight = true;
        name.blur();
        password.blur();


        if (!isAllInfoRight) {
            $("#myModal").modal("show");
            return;
        }

        $("form").submit();

    });

    function nameAlert() {
        name.attr("placeholder", "请输入用户名");
    }

    function passwordAlert() {
        password.attr("placeholder", "请输入密码");
    }
});