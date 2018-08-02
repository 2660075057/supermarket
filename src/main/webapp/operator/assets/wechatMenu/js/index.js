
function countChar(textareaName,spanName)
{  
 document.getElementById(spanName).innerHTML = 140 - document.getElementById(textareaName).value.length;
}  

jQuery("document").ready(function($) {	
			
				$("<a id='toTop' class='icons_c icons_c_top go_top'>top</a>").appendTo('#chat_box');

				if($(this).scrollTop()==0){
						$("#toTop").hide();
					}
				$(window).scroll(function(event) {
					/* Act on the event */
					if($(this).scrollTop()==0){
						$("#toTop").hide();
					}
					if($(this).scrollTop()!=0){
						$("#toTop").show();
					}
				});	
					$("#toTop").click(function(event) {
								/* Act on the event */
								$("html,body").animate({
									scrollTop:"0px"},
									666
									)
							});
		});



// Checkboxes and Radio buttons
$('[data-toggle="checkbox"]').radiocheck();
$('[data-toggle="radio"]').radiocheck();

// Custom Selects
if ($('[data-toggle="select"]').length) {
  $('[data-toggle="select"]').select2();
}
