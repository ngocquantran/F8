$(function () {
  countdown(15);

  updateTestProgress();
  jumpQuestion();
});

function countdown(time) {
  const $time = $(".test-countdown-number");
  $time.text(`${time}`);
  const timeCount = setInterval(function () {
    time--;
    if (time < 10) {
      $time.text("0" + `${time}`);
    } else {
      $time.text(`${time}`);
    }

    if (time === 0) {
      clearInterval(timeCount);
    }
  }, 1000);
}

function updateTestProgress() {
  const $exercise = $(".test-exercise");
  const n = $exercise.length;
  $(".progress-title span").text(n);
  const $cur = $(".test-exercise.exercise-current");
  let index = parseInt($cur.attr("index")) + 1;
  $(".progress-title strong").text(index);
  $(".test-progress-bar").css({
    width: (index / n) * 100 + "%",
  });
}

function jumpQuestion() {
  const jump = setInterval(function () {
    const $cur = $(".test-exercise.exercise-current");
    const $next = $cur.next();
    if ($next.length == 0) {
      clearInterval(jump);
    } else {
      $cur.removeClass("exercise-current");
      $next.addClass("exercise-current");
      countdown(15);
      updateTestProgress();
    }
  }, 18000);
}
