$(function () {
  changeSlider2();
  mobileMenu();
});

function changeSlider2() {
  const $sliders = $(".my-slider");
  setInterval(function () {
    const $currentSlider = $(".my-slider.active");
    let $nextSlider = $currentSlider.next();
    if ($nextSlider.length == 0) {
      $nextSlider = $currentSlider.prevAll().last();
    }
    $currentSlider.removeClass("active");
    $nextSlider.addClass("active");
  }, 4000);
}

function changeSlider() {
  const $sliders = $(".my-slider");
  let index = 0;
  $sliders.each(function (index, slider) {
    $(slider).addClass(`slider-${index}`);
    index++;
  });

  let number = 1;
  setInterval(function () {
    const $currentSlider = $(".my-slider.active");
    $currentSlider.removeClass("active");

    const $nextSlider = $(`.my-slider.slider-${number}`);
    $nextSlider.addClass("active");
    number++;
    if (number >= $sliders.length) {
      number = 0;
    }
  }, 4000);
}

function mobileMenu() {
  const $mobileMenu = $(".mobile-menu-btn");
  const $header = $(".header");
  let headerHeight = $header.height();

  //Đóng mở mobile menu

  $mobileMenu.on("click", function () {
    let isClose = $header.height() === headerHeight;
    if (isClose) {
      $header.css({
        height: "auto",
      });
    } else {
      $header.css({
        height: `${headerHeight}`,
      });
    }
  });

  //Tự động đóng khi chọn menu

  const $menuItems = $(".nav>li");
  for (let i = 1; i < $menuItems.length; i++) {
    let menuItem = $($menuItems[i]);
    menuItem.on("click", function (item) {
      $header.css({
        height: `${headerHeight}`,
      });
    });
  }
}
