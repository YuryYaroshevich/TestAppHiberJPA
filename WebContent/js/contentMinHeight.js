$(document).ready(function() {
	var screenHeight = $(window).height();
	var headerHeight = $("#header").height()+2*parseInt($("#header").css('border-top-width'));
	var footerHeight = $("#footer").height()+2*parseInt($("#footer").css('border-top-width'));
	
	var tableBorderWidth;
	if ($("table td").css('border-bottom-width') == undefined) {
		// there are no tables on a page
		tableBorderWidth = 0;
	} else {
		// there is some table
		tableBorderWidth = $("table > tr").length * parseInt($("table td").css('border-bottom-width'));
	}
	
	var contentMinHeight = 
		screenHeight - headerHeight - 
		2*parseInt($("#content").css('margin-top')) -
		2*parseInt($("#content").css('padding-top')) -
		2*parseInt($("#content").css('border-top-width')) -
		2*parseInt($("body").css('border-top-width')) -
		footerHeight - tableBorderWidth;
	$("#content").css('min-height',contentMinHeight+'px');
});
