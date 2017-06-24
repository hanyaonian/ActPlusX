$(function() {
	var isAllInfoRight = true;
	var title = $("#title");
	var content = $("#content");
    var contact = $("#contact");

	title.bind("blur", function(event) {
	    $("#titleTip").addClass("invisible");
	    if (title.val().length >= 4) return;
        titleAlert();
	    isAllInfoRight = false;
	    return;
    });

    content.bind("blur", function(event) {
	    $("#contentTip").addClass("invisible");
	    if (content.val().length >= 15) return;
	    isAllInfoRight = false;
        contentAlert();
	    return;
    });

    contact.bind("blur", function(event) {
        $("#contactTip").addClass("invisible");
        if (contact.val().length >= 4) return;
        isAllInfoRight = false;
        contactAlert();
        return;
    });

	$("#submitButton").bind("click", function(event) {
        event.preventDefault();
        isAllInfoRight = true;
        title.blur();
        content.blur();
        contact.blur();

        if (!isAllInfoRight) {
        	$("#myModal2").modal("show");
            return;
        }
		$("#myModal").modal("show");
    });

    $(".group_success").bind("click", function() {
    	window.location.href = "/group_success.html";
    });

    function titleAlert() {
        $("#titleTip").removeClass("invisible");
    }
    function contentAlert() {
        $("#contentTip").removeClass("invisible");
    }
    function contactAlert() {
        $("#contactTip").removeClass("invisible");
    }

});