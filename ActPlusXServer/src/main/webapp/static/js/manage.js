$(function() {
	var canBeEdit = false;
	var editButton = $('.edit-button');
	var activityItem = $('.activity-item > div');
	editButton.bind("click", function() {
		if (canBeEdit == false) {
			canBeEdit = true;
			editButton.html('确认');
			$('.activity-item > div').removeClass('dispear');
			$(".header").attr("contenteditable", "true");
			$("section").attr("contenteditable", "true");
		} else {
			canBeEdit = false;
			editButton.html('编辑');
			$('.activity-item > div').addClass('dispear');
			$(".header").attr("contenteditable", "false");
			$("section").attr("contenteditable", "false");
			//  传送整个页面或者修改的活动数据给后端
		}
	})
	// var editbutton = $('.edit');
	var deletebutton = $('.delete');
	var upmovebutton = $('.upmove');
	var downmovebutton = $('.downmove');

	for (var i = 0; i < deletebutton.length; i++) {
		// editbutton[i].addEventListener("click", editFunction);
		deletebutton[i].addEventListener("click", deleteFunction);
		upmovebutton[i].addEventListener("click", upmoveFunction);
		downmovebutton[i].addEventListener("click", downmoveFunction);
	}

	// function editFunction() {
	// 	//  每一个编辑都需要一个canbeEdit用以确认状态
	// 	var thisActivity = $(this).parents("section")[0];
	// 	$(thisActivity).attr("contenteditable", "true");
	// }

	function deleteFunction() {
		$(this).parents("section")[0].remove();
	}

	function upmoveFunction() {
		var thisActivity = $(this).parents("section")[0];
		$(thisActivity).prev().before(thisActivity.outerHTML);
		var nowActivity = $(thisActivity).prev().prev();
		// var nowEditButton = nowActivity.find(".edit")[0];
		var nowDeleteButton = nowActivity.find(".delete")[0];
		var nowUpMoveButton = nowActivity.find(".upmove")[0];
		var nowDownMoveButton = nowActivity.find(".downmove")[0];
		// nowEditButton.addEventListener("click", editFunction);
		nowDeleteButton.addEventListener("click", deleteFunction);
		nowUpMoveButton.addEventListener("click", upmoveFunction);
		nowDownMoveButton.addEventListener("click", downmoveFunction);
		thisActivity.remove();
	}

	function downmoveFunction() {
		var thisActivity = $(this).parents("section")[0];
		$(thisActivity).next().after(thisActivity.outerHTML);
		var nowActivity = $(thisActivity).next().next();
		// var nowEditButton = nowActivity.find(".edit")[0];
		var nowDeleteButton = nowActivity.find(".delete")[0];
		var nowUpMoveButton = nowActivity.find(".upmove")[0];
		var nowDownMoveButton = nowActivity.find(".downmove")[0];
		// nowEditButton.addEventListener("click", editFunction);
		nowDeleteButton.addEventListener("click", deleteFunction);
		nowUpMoveButton.addEventListener("click", upmoveFunction);
		nowDownMoveButton.addEventListener("click", downmoveFunction);
		thisActivity.remove();
	}
});