$(function() {
    var username = $("#name");
    var stuID = $("#stuId");
    var phoneNum = $("#phone");
    var email = $("#email");
    var faculty = $("#faculty");
    var isAllInfoRight = false;

    //  所有input失去焦点后进行数据检测，当未输入时不出现提醒框
    username.bind("blur", function(event) {
        $("#nameTip").hide();
        if (username.val().length != 0) return;
        nameAlert();
        isAllInfoRight = false;
        return;
    });

    stuID.bind("blur", function(event) {
        $("#stuTip").hide();
        if (stuID.val().match(new RegExp("^[1-9][0-9]{7}$"))) return;
        isAllInfoRight = false;
        stuAlert();
        return;
    });

    phoneNum.bind("blur", function(event) {
        $("#phoneTip").hide();
        if (phoneNum.val().match(new RegExp("^[1-9][0-9]{10}$"))) return;
        isAllInfoRight = false;
        phoneAlert();
        return;
    });

    email.bind("blur", function(event) {
        $("#emailTip").hide();
        if (email.val().match(new RegExp("^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$"))) return;
        isAllInfoRight = false;
        emailAlert();
        return;
    });

    faculty.bind("blur", function(event) {
        $("#facultyTip").hide();
        if (faculty.val().length != 0) return;
        facultyAlert();
        isAllInfoRight = false;
        return;
    });

    //  提交按钮点击，进行检测，检测通过则post提交
    $("#submitButton").bind("click", function(event) {
        event.preventDefault();
        // isSubmitNow = true;
        isAllInfoRight = true;

        username.blur();
        stuID.blur();
        phoneNum.blur();
        email.blur();
        faculty.blur();

        if (!isAllInfoRight) {
            // isSubmitNow = false;
            $("#myModal2").modal("show");
            return;
        }

        $("form").submit();
    }); 
    
    function nameAlert() {
        $("#nameTip").show();
    }
    function stuAlert() {
        $("#stuTip").show();
    }
    function phoneAlert() {
        $("#phoneTip").show();
    }
    function emailAlert() {
        $("#emailTip").show();
    }
    function facultyAlert() {
        $("#facultyTip").show();
    }

});
