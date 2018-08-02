$(function(){
	FastClick.attach(document.body);
	$(".search-md").on("click",function(){
		window.location.href = 'search'
	})
})
$.modalLeave = function(type){
	if(type == "show"){
		$(".modal-leave-fixed").show();
		setTimeout(function(){
			$(".modal-leave-fixed").addClass("modal-leave-fixed-active")
		},10)
		$(".modal-leave-fixed .bg").off("click").on("click",function(){
			$.modalLeave("hide")
		})
	}
	if(type =="hide"){
		$(".modal-leave-fixed").removeClass("modal-leave-fixed-active")
		setTimeout(function(){
			$(".modal-leave-fixed").hide()
		},300)
	}
}

$.modalFilter = function(type){
	if(type == "show"){
		$(".side-search-fixed").show();
		setTimeout(function(){
			$(".side-search-fixed").addClass("side-search-fixed-active")
		},10)
		$(".side-search-fixed .bg").off("click").on("click",function(){
			$.modalFilter("hide")
		})
	}
	if(type =="hide"){
		$(".side-search-fixed").removeClass("side-search-fixed-active")
		setTimeout(function(){
			$(".side-search-fixed").hide()
		},300)
	}
}
