$(function() {
    var name = $("#name");
    var position = $("#position");
    var people = $("#people");

    var time = $("#time");
    var sponsor = $("#sponsor");
    var info = $("#info");
    var detail = $("#detail");
    // var award = $("#award");
    // var require = $("#require");
    var date = $("#date");
    var signup = $("#signup");
    var email = $("#email");
    // var checkbox = $(".check-box");

    var isAllInfoRight = false;
    var isAnyCheckboxChecked = false;

    // checkbox.bind("blur", function(event) {
    //     if ()
    // });

    //  所有input失去焦点后进行数据检测，当未输入时不出现提醒框
    name.bind("blur", function(event) {
        $("#nameTip").hide();
        if (name.val().length != 0) return;
        nameAlert();
        isAllInfoRight = false;
        return;
    });

    position.bind("blur", function(event) {
        $("#positionTip").hide();
        if (position.val().length != 0) return;
        positionAlert();
        isAllInfoRight = false;
        return;
    });

    people.bind("blur", function(event) {
        $("#peopleTip").hide();
        if (people.val().length != 0) return;
        peopleAlert();
        isAllInfoRight = false;
        return;
    });

    time.bind("blur", function(event) {
        $("#timeTip").hide();
        if (time.val().length != 0) return;
        timeAlert();
        isAllInfoRight = false;
        return;
    });

    sponsor.bind("blur", function(event) {
        $("#sponsorTip").hide();
        if (sponsor.val().length != 0) return;
        sponsorAlert();
        isAllInfoRight = false;
        return;
    });

    info.bind("blur", function(event) {
        $("#infoTip").hide();
        if (info.val().length != 0) return;
        infoAlert();
        isAllInfoRight = false;
        return;
    });

    detail.bind("blur", function(event) {
        $("#detailTip").hide();
        if (detail.val().length != 0) return;
        detailAlert();
        isAllInfoRight = false;
        return;
    });


    signup.bind("blur", function(event) {
        $("#signupTip").hide();
        if (signup.val().length != 0) return;
        signupAlert();
        isAllInfoRight = false;
        return;
    });

    email.bind("blur", function(event) {
        $("#emailTip").hide();
        var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        if (reg.test(email.val())) return;
        emailAlert();
        isAllInfoRight = false;
        return;
    });

    //  截止日期

    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
    Date.prototype.Format = function(fmt)
    { //author: meizz
      var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
      };
      if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
      for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
      return fmt;
    }

    function checkDate() {
        $("#dateTip").hide();
        var a = (new Date()).Format("yyyy年MM月dd日 hh:mm");
        //  如果日期填写正确则退出，否则弹出提示
        if (a <= date.val()) return;
        dateAlert();
        isAllInfoRight = false;
        return;
    }

    //  提交按钮点击，进行检测，检测通过则post提交
    $("#submitButton").bind("click", function(event) {
        event.preventDefault();
        // isSubmitNow = true;
        isAllInfoRight = true;
        isAnyCheckboxChecked = false;

        name.blur();
        position.blur();
        people.blur();
        time.blur();
        sponsor.blur();
        info.blur();
        detail.blur();
        // award.blur();
        require.blur();
        signup.blur();
        email.blur();
        checkDate();
        $(".check-box").each(function(){
            if(this.checked == true){
                isAnyCheckboxChecked = true;
                return;
            }
        });

        if (!isAnyCheckboxChecked) {
            schoolAlert();
            $("#myModal2").modal("show");
            return;
        }


        if (!isAllInfoRight) {
            $("#myModal2").modal("show");
            // isSubmitNow = false;
            return;
        }
        $("form").submit();
    });

    function nameAlert() {
        $("#nameTip").show();
    }
    function positionAlert() {
        $("#positionTip").show();
    }
    function peopleAlert() {
        $("#peopleTip").show();
    }
    function timeAlert() {
        $("#timeTip").show();
    }
    function sponsorAlert() {
        $("#sponsorTip").show();
    }
    function infoAlert() {
        $("#infoTip").show();
    }
    function detailAlert() {
        $("#detailTip").show();
    }

    function dateAlert() {
        $("#dateTip").show();
    }
    function signupAlert() {
        $("#signupTip").show();
    }
    function emailAlert() {
        $("#emailTip").show();
    }
    function schoolAlert() {
        $("#schoolTip").show();
    }
});
