$(function () {
  $(".test-intro .button-ready-content").on("click", function () {
    $(".test-intro").addClass("hidden");
    $(".test-container").removeClass("hidden");
    // countdown(15);
    runTest();
  });
});

let timeCount;

function countdown(time) {
  const $time = $(".test-countdown-number");
  $time.text(`${time}`);
  timeCount = setInterval(function () {
    time--;
    if (time < 10) {
      $time.text("0" + `${time}`);
      playClockSound();
    } else {
      $time.text(`${time}`);
      playClockSound();
    }

    if (time == 0) {
      stopCountDown();
      showAnswer();
      nextQuestion();
      // clearInterval(timeCount);
    }
  }, 1000);
}

function stopCountDown() {

  window.clearInterval(timeCount);
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

function showAnswer() {
  const $cur = $(".test-exercise.exercise-current");
  const $view = $cur.find(".test-exercise-question-viewport");
  const $audio = $cur.find(".test-exercise-question-view-back audio");
  setTimeout(function () {
    $cur.addClass("finish");
    $view.addClass("show-answer");
    playASound($audio);
  }, 500);
}

function runTest() {
  getReadyToRun($(".test-exercise.exercise-current"));
  countdown(15);
  updateTestProgress();
  checkAnswerGuessing();
  activeSubmit();
  checkAnswerWriting();
}

function nextQuestion() {
  const $cur = $(".test-exercise.exercise-current");
  const $next = $cur.next();
  
  if (!$next.length == 0) {
    setTimeout(function () {
      $cur.removeClass("exercise-current");
      $next.addClass("exercise-current");
      runTest();
    }, 3000);
  }
}

function getReadyToRun($currentExercise) {
  const $input = $currentExercise.find(".test-exercise-answer.answer-write input");
  $input.focus();
  const $audio = $currentExercise.find(".test-exercise-question-view-front audio");
  if ($audio.length > 0) {
    playASound($audio);
  }
}

function checkAnswerGuessing() {
  const $cur = $(".test-exercise.exercise-current");
  const answerIndex = parseInt($cur.attr("answer-index"));

  const $allChoice = $cur.find(".answer-choice");
  const $rightChoice = $cur.find(`.answer-choice[value=${answerIndex}]`);
  const $wrongChoice = $cur.find(`.answer-choice:not([value=${answerIndex}])`);

  $rightChoice.on("click", function () {
    stopCountDown();
    $rightChoice.addClass("choose-right");
    playSoundRight();

    showAnswer();
    $allChoice.each(function (index, option) {
      $(option).css({
        "pointer-events": "none",
      });
    });
    nextQuestion();
  });

  $wrongChoice.each(function (index, choice) {
    $(choice).on("click", function () {
      stopCountDown();
      $rightChoice.addClass("choose-right");
      $(choice).addClass("choose-wrong");
      playSoundWrong();
      showAnswer();
      $allChoice.each(function (index, option) {
        $(option).css({
          "pointer-events": "none",
        });
      });
      nextQuestion();
    });
  });
}

function checkAnswerWriting() {
  const $cur = $(".test-exercise.exercise-current");
  const answer = $cur.attr("answer");
  const $input = $cur.find(".answer-writing");
  const $btn = $cur.find(".answer-check");
  $btn.on("click", function () {
    if ($input.val().toLowerCase().trim() == answer) {
      playSoundRight();
      $input.addClass("show-answer-right");
    } else {
      playSoundWrong();
      $input.addClass("show-answer-wrong");
    }
    stopCountDown();
    showAnswer();
    nextQuestion();
  });
}

function activeSubmit() {
  const $answer = $(".test-exercise.exercise-current .test-exercise-answer");
  const $input = $answer.find(".answer-writing");
  const $btn = $answer.find(".answer-check");
  $input.on("keyup", function (e) {
    if (e.keyCode == 13) {
      $input.blur();
      $btn.trigger("click");
      $btn.css({
        "pointer-events": "none",
      });
      $btn.removeClass("active-btn");
    } else {
      if ($input.val().length > 0) {
        $btn.addClass("active-btn");
      } else {
        $btn.removeClass("active-btn");
      }
    }
  });
}

function playASound($sound) {
  $sound[0].load();
  $sound[0].onloadeddata = function () {
    $sound[0].play();
  };
}

function playClockSound() {
  $(".clock-sound")[0].load();
  $(".clock-sound")[0].onloadeddata = function () {
    $(".clock-sound")[0].play();
  };
}

function playSoundRight() {
  $(".answer-right-sound")[0].load();
  $(".answer-right-sound")[0].onloadeddata = function () {
    $(".answer-right-sound")[0].play();
  };
}

function playSoundWrong() {
  $(".answer-wrong-sound")[0].load();
  $(".answer-wrong-sound")[0].onloadeddata = function () {
    $(".answer-wrong-sound")[0].play();
  };
}
