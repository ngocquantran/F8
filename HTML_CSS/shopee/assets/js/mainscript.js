$(function () {
    showAuthenForm();
})


// Login and Register Function
function showAuthenForm() {
  const $modal = $(".modal");
  const $registerWindow = $(".register-form");
  const $loginWindow = $(".login-form");

  const $getRegisterWindow = $(".header__navbar-item__register");
  const $getLoginWindow = $(".header__navbar-item__login");

  const $btnSwitchToLogin = $(".register-form .auth-form__switch-btn");
    const $btnSwitchToRegister = $(".login-form .auth-form__switch-btn");

    const $exitLogin = $(".login-form .auth-form__controls-exit"); 
    const $exitRegister = $(".register-form .auth-form__controls-exit");

  //Open Register window
  $getRegisterWindow.on("click", function () {
    $modal.removeClass("hidden");
    $registerWindow.removeClass("hidden");
  });

  $btnSwitchToRegister.on("click", function () {
    $loginWindow.addClass("hidden");
    $registerWindow.removeClass("hidden");
  });

  //Open Login window
  $getLoginWindow.on("click", function () {
    $modal.removeClass("hidden");
    $loginWindow.removeClass("hidden");
  });

  $btnSwitchToLogin.on("click", function () {
    $registerWindow.addClass("hidden");
    $loginWindow.removeClass("hidden");
  });

  //Close Register and Login window
    $exitLogin.on('click', function () {
        $loginWindow.addClass("hidden");
        $modal.addClass("hidden");
    });

    $exitRegister.on('click', function () {
        $registerWindow.addClass("hidden");
        $modal.addClass("hidden");
    })
    
}