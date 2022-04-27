$(function () {
    openMobileNav();
})

function openMobileNav() {
     const $navBtn = $(".nav-btn");
    const $navMobile = $(".nav__mobile");
    const $overLay = $(".nav-overlay");
    const $navMobileClose = $(".nav__mobile-close");
    $navBtn.on("click", function () {
         $navMobile.animate({width:'toggle'});
         $navMobile.removeClass("hidden");
         $overLay.removeClass('hidden')
     });
    
    $overLay.on('click', function () {
        $navMobile.animate({ width: "toggle" });
        $navMobile.addClass('hidden');
        $overLay.addClass("hidden");
    });
    $navMobileClose.on("click", function () {
        $navMobile.animate({ width: "toggle" });
       $navMobile.addClass("hidden");
       $overLay.addClass("hidden");
     });
}