$(document).ready(function(){

    $("#main-table").height($(window).height() - 255)

    $(window).on("resize",function(){
        $("#main-table").height($(window).height() - 255)
    })

    // if($("#side-nav dl.active dd").length){
    //     $("#side-nav dl.active dd").show();
    // }else{
    //     $("#side-nav dl.active dt a").addClass("font-imt");
    // }
    $("#side-nav dt a").on("click",function(){
        var dl = $(this).parents("dl");
        if(dl.find("dd").length){
            if(dl.hasClass("active")){
                dl.find("dd").slideUp(300,function(){
                    dl.removeClass("active")
                });

            }else{
                dl.find("dd").slideDown();
                dl.addClass("active");

            }
        }
    })
    $('input.icheck').iCheck({
        checkboxClass: 'icheckbox_minimal-orange',
        radioClass: 'iradio_minimal-orange',
        increaseArea: '20%' // optional
    });
    $(".tabs-theme .btn").on("click",function(){
        var target = $(this).parent().data("target");
        $(this).addClass("btn-primary").siblings().removeClass("btn-primary");
        $(target).find(".panel-theme").hide().eq($(this).index()).show();
    })
});

$.success_layer = function(text,timer){
    var layer = $("<div>",{
        class : "global-layer",
        html : text
    })
    $("body").append(layer);
    setTimeout(function(){
        layer.remove();
    },timer || 2000)
}

$.data_picker_locale = {
    "direction": "ltr",
    "format": "YYYY-MM-DD",
    "separator": " ~ ",
    "applyLabel": "确定",
    "cancelLabel": "取消",
    "fromLabel": "从",
    "toLabel": "到",
    "customRangeLabel": "自定义",
    "daysOfWeek": [
        "日",
        "一",
        "二",
        "三",
        "四",
        "五",
        "六"
    ],
    "monthNames": [
        "一月",
        "二月",
        "三月",
        "四月",
        "五月",
        "六月",
        "七月",
        "八月",
        "九月",
        "十月月",
        "十一月",
        "十二月"
    ],
    "firstDay": 1
}