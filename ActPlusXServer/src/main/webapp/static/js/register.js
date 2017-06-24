$(function() {
	var name = $("#name");
    var nickname = $("#nickname");
	var password = $("#password");
    var password_again = $("#password-again");
    var email = $("#email");

	var isAllInfoRight = false;

    name.bind("blur", function(event) {
	    $("#nameTip").hide();
	    if (name.val().length != 0) return;
	    nameAlert();
	    isAllInfoRight = false;
	    return;
    });

    nickname.bind("blur", function(event) {
        $("#nicknameTip").hide();
        if (nickname.val().length != 0) return;
        nicknameAlert();
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

    password_again.bind("blur", function(event) {
        $("#password-againTip").hide();
        if (password_again.val() == password.val()) return;
        password_againAlert();
        isAllInfoRight = false;
        return;
    });

//    email.bind("blur", function() {
//         if($("#email1").val()=="") {
//            alert("邮箱不能为空");
//            return false;
//         }
//         var email=$("#email1").val();
//         if(!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
//            alert("格式不正确！请重新输入");
//         }
//    })


	//  提交按钮点击，进行检测，检测通过则post提交
    $("#submitButton").bind("click", function(event) {
        event.preventDefault();
        isAllInfoRight = true;
        name.blur();
        nickname.blur();
        password.blur();
        password_again.blur();


        if (!isAllInfoRight) {
            return;
        }

        $("form").submit();
    });

    function nameAlert() {
        $("#nameTip").show();
    }

    function nicknameAlert() {
        $("#nicknameTip").show();
    }

    function passwordAlert() {
        $("#passwordTip").show();
    }

    function password_againAlert() {
        $("#password-againTip").show();
    }
});