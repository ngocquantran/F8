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
  $(".header-nav__collection-quick-menu").on("click", openQuickMenu);
  $(".quick-menu-exit-btn").on("click", exitQuickMenu);

  // Slider range Price
  priceSlider();

  // Product showcase galerry
  productGalery();

  // Product detail handle
  updateChosedsize();
  updateChosedColor();
  removeCurrentChoice();
  updateQuantity();
  addToCart();

  // Size guide window
  $(".container-product__info-size-guide").on("click", openSizeGuide);
  $(".size-guide-exit").on("click", exitSizeGuide);

  // Moving lable for input when focused and filled
  movingInputLabel();

  // Cart Page: change address

  showUpdateAddressForm();
  stuckCartSummaryTable();
});

// COMMON ACTION----------------------------------------------------------------------------------------

//Homepage slider------------------------------
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

// Header scroll appearance--------------------
function headerAppearance() {
  const $headerNav = $(".header-nav");
  $(window).scroll(function () {
    if ($(document).scrollTop() > 40) {
      $headerNav.addClass("fixed");
      $(".header-nav::after").css({
        display: "none",
      });
      $(".header-nav-spacer").css({
        display: "block",
      });
    } else {
      $headerNav.removeClass("fixed");
      $(".header-nav::after").css({
        display: "block",
      });
      $(".header-nav-spacer").css({
        display: "none",
      });
    }
  });
}

// Search Form Action--------------------------
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

// Quick cart action---------------------------
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

// Authen Form action -------------------------
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

// Quick Menu action --------------------------
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
  $(".quick-menu").animate({ left: "-600" }, 300);
  setTimeout(function () {
    $(".modal").addClass("hidden");
    $("body").removeClass("noscroll");
  }, 300);
}

// Handle quantity ----------------------------
function updateQuantity() {
  const $qtyWrapper = $(".qty-wrapper");

  $qtyWrapper.each(function (index, qtywrapper) {
    let $plus = $(qtywrapper).find(".plus");
    let $minus = $(qtywrapper).find(".minus");
    $minus.on("click", function () {
      const $curQty = $(qtywrapper).find("span");
      let qty = parseInt($curQty.text());
      if (qty > 1) {
        qty--;
      }
      $curQty.text(qty);
    });

    $plus.on("click", function () {
      const $curQty = $(qtywrapper).find("span");
      let qty = parseInt($curQty.text());
      qty++;
      $curQty.text(qty);
    });
  });
}

// Moving lable for input form when focused and filled
function movingInputLabel() {
  const $input = $(".input-moving-lable input");
  const $label = $(".input-moving-lable input + label");
  $input.each(function (index, input) {
    $(input).focus(function () {
      $(input).addClass("focused");
    });
    $(input).focusout(function () {
      if (!$(input).val()) {
        $(input).removeClass("focused");
      }
    });
  });
}

// HOME PAGE ACTION----------------------------------------------------------------------------------------

// Testimonial slider--------------------------
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

// PRODUCT PAGE ACTION-------------------------------------------------------------------------------------

// Size-guide action---------------------------
function openSizeGuide() {
  $(".modal").removeClass("hidden");
  $(".size-guide").removeClass("hidden");
  $("body").addClass("noscroll");
}

function exitSizeGuide() {
  $(".size-guide").addClass("hidden");
  $(".modal").addClass("hidden");
  $("body").removeClass("noscroll");
}

// Price range slider--------------------------

function priceSlider() {
  const rangeInput = document.querySelectorAll(".range-input input");
  const progress = document.querySelector(".price-slider .progress");
  const minResult = document.querySelector(".min-price-tag");
  const maxResult = document.querySelector(".max-price-tag");
  let priceGap = 100;

  rangeInput.forEach(function (input) {
    input.addEventListener("input", (e) => {
      let minVal = parseInt(rangeInput[0].value);
      let maxVal = parseInt(rangeInput[1].value);

      if (maxVal - minVal < priceGap) {
        if (e.target.className === "range-min") {
          rangeInput[0].value = maxVal - priceGap;
        } else {
          rangeInput[1].value = minVal + priceGap;
        }
      } else {
        minResult.innerText = minVal;
        maxResult.innerText = maxVal;
        progress.style.left =
          ((minVal - $(".range-min").attr("min")) /
            ($(".range-min").attr("max") - $(".range-min").attr("min"))) *
            100 +
          "%";
        progress.style.right =
          (($(".range-max").attr("max") - maxVal) /
            ($(".range-max").attr("max") - $(".range-max").attr("min"))) *
            100 +
          "%";
      }
    });
  });
}

// Product showcase gallery--------------------
function productGalery() {
  $(".container-product__gallery .vertical").lightSlider({
    gallery: true,
    loop: true,
    item: 1,

    verticalHeight: 945,
    vThumbWidth: 50,
    // thumbItem: 5,
    thumbMargin: 4,
    slideMargin: 0,
  });
}

// Product detail select-----------------------
function updateChosedsize() {
  const $shoeSizes = $(".container-product__info-size-item label");
  const $chosedSize = $(".container-product__info-size-chosed span");

  $shoeSizes.each(function (index, size) {
    $(size).on("click", function () {
      $chosedSize.text($(size).prev().val());
    });
  });
}

function updateChosedColor() {
  const $shoeColors = $(".container-product__info-color-item");
  const $chosedColor = $(".container-product__info-color-chosed span");

  $shoeColors.each(function (index, color) {
    $(color).on("click", function () {
      $chosedColor.text($(color).find("input").val());
    });
  });
}

function removeCurrentChoice() {
  const $removeChoice = $(".container-product__info-choice-remove");
  $removeChoice.on("click", function () {
    const $checkedSize = $("input[name=size-option]:checked");
    const $checkedColor = $("input[name=color-option]:checked");
    $checkedColor.prop("checked", false);
    $checkedSize.prop("checked", false);

    $(".container-product__info-size-chosed span").text("");
    $(".container-product__info-color-chosed span").text("");
  });
}

function addToCart() {
  const $addToCart = $(".container-product__info-handle-addcart");
  $addToCart.on("click", function () {
    const $curSize = $(".container-product__info-size-chosed span");
    const $curColor = $(".container-product__info-color-chosed span");
    if ($curColor.text() === "" && $curSize.text() === "") {
      alert(
        "Please select some product options before adding this product to your cart."
      );
    } else {
      openQuickCart();
    }
  });
}

// CART PAGE ACTION-------------------------------------------------------------------------------------

// Change address-----------------------------
function showUpdateAddressForm() {
  const $changeBtn = $(".container-cart__summary-ship-address-change");
  const $addressForm = $(".container-cart__summary-ship-address-input");
  $changeBtn.on("click", function () {
    $addressForm.slideToggle("slow");
  });
}

// Fix Cart Summary when scroll---------------
function stuckCartSummaryTable() {
  const $cartSummary = $(".container-cart__summary");
  let originalTop = $cartSummary.position().top - 100;
  let originalWidth = $cartSummary.width();
  const $cartSummaryInner = $(".container-cart__summary-wrapper");

  $(window).scroll(function () {
    if ($(document).scrollTop() > originalTop) {
      $cartSummaryInner.css({
        position: "fixed",
        top: "100px",
        width: originalWidth,
      });
    } else {
      $cartSummaryInner.css({
        position: "relative",
        top: "0",
      });
    }
  });
}
