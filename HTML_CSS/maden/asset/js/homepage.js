$(function () {
  // Header slider
  slideFunction();
  changeSlideByDot();

  //Header change when scroll
  headerAppearance();
  // Search box action
  $(".header-nav__action-search").on("click", openSearchForm);
  $(".search-box-exit-btn").on("click", exitSearchForm);

  // Slide Testimonial
  lightSliderComment();

  // Quick cart action
  $(".header-nav__action-cart").on("click", openQuickCart);
  $(".quick-cart__header-exit-btn").on("click", exitQuickCart);

  // Authen form action
  $(".header-nav__action-login").on("click", openAuthenForm);
  $(".modal__authen-form__switch-btn").on("click", swithAuthenForm);
  $(".modal__authen-form-exit").on("click", exitAuthenForm);

  // Quick Menu action
  $(".header-nav__collection-quick-menu").on('click', openQuickMenu);
  $(".quick-menu-exit-btn").on('click',exitQuickMenu);

  // Slider range Price
  priceSlider();
});

//Homepage slider-----------------------------------------------------------
function showSlider(n) {
  const $sliders = $(".slider-list-item");
  const $dots = $(".slider-paginate li");
  const $curSlide = $(".slider-list-item.active");
  const $curDot = $(".slider-paginate li.active");

  if (n >= $sliders.length) n = 0;
  if (n < 0) n = $sliders.length - 1;

  $curSlide.removeClass("active");
  $curSlide.addClass("hidden");
  $curDot.removeClass("active");

  const $nextSlide = $sliders.eq(n);
  const $nextDot = $dots.eq(n);

  $nextSlide.removeClass("hidden");
  $nextSlide.addClass("active");
  $nextDot.addClass("active");
}

function slideFunction() {
  const $sliders = $(".slider-list-item");
  let slideIndex = 0;

  // Manual slide by next,prev
  const $next = $(".slider-click-next");
  const $prev = $(".slider-click-prev");
  $next.on("click", function () {
    slideIndex = setIndexInLength(++slideIndex, $sliders.length);
    showSlider(slideIndex);
  });
  $prev.on("click", function () {
    slideIndex = setIndexInLength(--slideIndex, $sliders.length);
    showSlider(slideIndex);
  });

  //Auto slide
  setInterval(function () {
    slideIndex = setIndexInLength(++slideIndex, $sliders.length);
    showSlider(slideIndex);
  }, 4000);
}

function changeSlideByDot() {
  //Manual slide by dot
  const $dots = $(".slider-paginate li");
  for (let i = 0; i < $dots.length; i++) {
    $dots.eq(i).on("click", function () {
      showSlider(i);
    });
  }
}

function setIndexInLength(index, length) {
  if (index < 0) index = length - 1;
  if (index >= length) index = 0;
  return index;
}

// Header scroll appearance--------------------------------------
function headerAppearance() {
  const $headerNav = $(".header-nav");
  $(window).scroll(function () {
    if ($(document).scrollTop() > 40) {
      $headerNav.css({
        "background-color": "#fff",
        height: "70px",
        top: 0,
        "box-shadow": "0px 1px 5px #999",
      });
    } else {
      $headerNav.css({
        "background-color": "transparent",
        height: 90,
        top: 40,
        "box-shadow": "none",
      });
    }
  });
}

// Search Form Action-------------------------------------------------------
function openSearchForm() {
  $(".modal").removeClass("hidden");
  $(".search-box").slideDown("300");
  $("body").addClass("noscroll");
}

function exitSearchForm() {
  $(".search-box").slideUp("300");
  setTimeout(function () {
    $(".modal").addClass("hidden");
    $("body").removeClass("noscroll");
  }, 300);
}

// Quick cart action--------------------------------------------
function openQuickCart() {
  $(".modal").removeClass("hidden");
  $(".quick-cart").animate(
    {
      right: 0,
    },
    300
  );
  $("body").addClass("noscroll");
}

function exitQuickCart() {
  $(".quick-cart").animate({ right: -460 }, 300);
  setTimeout(function () {
    $(".modal").addClass("hidden");
    $("body").removeClass("noscroll");
  }, 300);
}

// Authen Form action ------------------------------------------------------------------------------
function openAuthenForm() {
  $(".modal").removeClass("hidden");
  $(".modal__authen-form.login-form").removeClass("hidden");
  $("body").addClass("noscroll");
}

function swithAuthenForm() {
  const $curForm = $(".modal__authen-form:not(.hidden)");
  const $targetForm = $(".modal__authen-form.hidden");
  $curForm.addClass("hidden");
  $targetForm.removeClass("hidden");
}

function exitAuthenForm() {
  $(".modal__authen-form:not(.hidden)").addClass("hidden");
  $(".modal").addClass("hidden");
  $("body").removeClass("noscroll");
}

// Testimonial slider---------------------------------------
function lightSliderComment() {
  const slider = $(".container__comment-list.lightSlider").lightSlider({
    item: 1,
    loop: true,
    auto: true,
    pause: 2000,
    controls: false,
    pauseOnHover: true,
  });
  $(".comment-click-prev").on("click", function () {
    slider.goToPrevSlide();
  });
  $(".comment-click-next").on("click", function () {
    slider.goToNextSlide();
  });
}

// Quick Menu action -------------------------------------------
function openQuickMenu() {
  $(".modal").removeClass("hidden");
  $(".quick-menu").animate(
    {
      left: 0,
    },
    300
  );
  $("body").addClass("noscroll");
}

function exitQuickMenu() {
  $(".quick-menu").animate({ left: '-600' }, 300);
  setTimeout(function () {
    $(".modal").addClass("hidden");
    $("body").removeClass("noscroll");
  }, 300);
}


// Price range slider-----------------------------------------------------------
function priceSlider() {
  const $rangeInput = $('.range-input input');
const $progress = $(".price-slider .progress");

  $rangeInput.each(function (index,input) {
    $(input).change(function () {
      let minVal = parseInt($('.range-min').val());
      let maxVal = parseInt($('.range-max').val());
      let percentLeft =
        (minVal -
          $(".range-min").attr("min")) /
            ($(".range-min").attr("max") - $(".range-min").attr("min")) *
        100;
      let percentRight =
        ($(".range-max").attr("max")-maxVal) /
          ($(".range-max").attr("max") - $(".range-max").attr("min")) *
        100;
      $progress.css({
        left:percentLeft+"%",
      })
      $progress.css({
        right: percentRight + "%",
      });
    })
  })
}