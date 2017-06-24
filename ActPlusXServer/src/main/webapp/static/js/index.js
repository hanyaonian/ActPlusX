$(function() {
	$(".mask").each(function() {
		this.resizeScreen = function() {
			var width = $(this).siblings("img").width();
			var height = $(this).siblings("img").height();
			$(this).width(width).height(height);
		}
		this.resizeScreen();
	});
	$(window).resize(function() {
		$(".mask").each(function() {
			this.resizeScreen();
		});
	});
});