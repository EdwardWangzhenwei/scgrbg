/**
 * Created by Magee on 2017-08-02.
 */
$(function(){
        $(".leftsidebar_box dd").hide();
        $(".leftsidebar_box dt").click(function(){
            $(this).addClass('active').parent().siblings().find('dt').removeClass('active');
            $(this).parent().find('dd').removeClass("menu_chioce");
            $(".leftsidebar_box dt img").attr("src","images/select_xl01.png");
            $(this).parent().find('img').attr("src","images/select_xl.png");
            $(".menu_chioce").slideUp();
            $(this).parent().find('dd').slideToggle();
            $(this).parent().find('dd').addClass("menu_chioce");
            $(this).siblings('dd').removeClass('active');
        });
    $('.leftsidebar_box dd').click(function(){
        $(this).addClass('active').siblings().removeClass('active');
        $(this).parent().siblings().find('dd').removeClass('active');
        $(this).siblings('dt').addClass('active');
    })

    $('.fir_nav li a').click(function(){
        $(this).parent().addClass('active').siblings().removeClass('active');
    })

    if($('.fir_nav').css('display')=='block'){
        $('.main_title').css({top:'-15px'});
        $('.second_title').css({top:'31px'});
    }
})