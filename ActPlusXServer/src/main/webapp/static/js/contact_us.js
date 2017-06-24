$(function() {
    var content = $("#content");


    var isAllInfoRight = false;

    //  所有input失去焦点后进行数据检测，当未输入时不出现提醒框
    content.bind("blur", function(event) {
        $("#contentTip").hide();
        if (content.val().length != 0) return;
        isAllInfoRight = false;
        return;
    });

    //  提交按钮点击，进行检测，检测通过则post提交
    $("#submitButton").bind("click", function(event) {
        event.preventDefault();
        // isSubmitNow = true;
        isAllInfoRight = true;

        content.blur();


        if (!isAllInfoRight) {
            $("#myModal").modal("show");
            // isSubmitNow = false;
            return;
        }
        $("form").submit();
    });

});
