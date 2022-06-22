$(function () {
  $(".test-intro .button-ready-content").on("click", async function () {
    await getTestContent(1);
    $(".test-intro").addClass("hidden");
    $(".test-container").removeClass("hidden");
    $(".test-exercise").eq(0).addClass("exercise-current");
    // countdown(15);
    runTest();
  });

});

// Get Test data---------------------------------------------------------------------------------------------------
let vocabs = [];
const URL_API = "http://localhost:8080/api/v1";

async function getTestContent(id) {
  try {
    let res = await axios.get(`${URL_API}/test/${id}`);
    vocabs = res.data;
    console.log(vocabs);
    renderQuestions2(vocabs);
  } catch (error) {
    console.log(error);
  }
}

// Có tối thiểu 12 loại câu hỏi, nếu số lượng nhiều hơn 12 sẽ load loại câu lại từ type 1

function renderQuestions2(arr) {
  const $testContent = $(".test-body-wrapper");
  $testContent.html("");
  let html = "";
  arr.forEach((question, no) => {
    let type = no < 12 ? no + 1 : no - 12 + 1;
    // Tách từ trong câu tiếng anh để render
    let enSentence = question.enSentence.split("_");
    html +=
      ` <div
                  class="test-exercise"
                  index="${no + 1}"
                  typequestion="${type}"
                  typeanswer="${type <= 7 ? "choose" : "write"}"
                  answer="${question.word}"
                  answer-index="${question.answerIndex}"
                >
                  <div class="test-exercise-question">
                    <div class="test-exercise-question-viewport">
                      <div class="test-exercise-question-view-front">
                        <div class="test-exercise-content">
                          <p class="test-exercise-title">
                            ${
                              type == 1 || type == 3
                                ? "Chọn từ phù hợp theo các gợi ý sau:"
                                : type == 2
                                ? "Chọn nghĩa đúng với từ vựng sau:"
                                : type == 4
                                ? "Chọn từ đúng với định nghĩa sau:"
                                : type == 5
                                ? "Chọn từ đúng với âm thanh sau:"
                                : type == 6
                                ? "Chọn từ đúng điền vào câu sau:"
                                : type == 7
                                ? "Chọn câu tiếng anh ứng với nội dung sau:"
                                : type == 8
                                ? "Gõ từ đúng với phiên âm sau:"
                                : type == 9 || type == 10
                                ? "Gõ từ phù hợp theo các gợi ý sau:"
                                : "Gõ từ đúng với định nghĩa sau:"
                            }
                          </p>
                        </div>` +
      (type == 1
        ? `<div class="test-exercise-question">
                          <div class="play-sound">
                            <i class="fa-solid fa-volume-high"></i>
                            <audio
                              src="${question.audio}"
                            ></audio>
                          </div>
                          <p class="test-exercise-question-phonetic">${question.phonetic}</p>
                        </div>`
        : type == 2
        ? `<div class="test-exercise-question">
                          <p class="test-exercise-question-word">
                            ${question.word} <span>${question.phonetic}</span>
                          </p>
                        </div>`
        : type == 3
        ? `<div class="test-exercise-question">
                          <div class="question-hint">
                            <img
                              class="hint-image"
                              src="${question.img}"
                              alt="img"
                            />
                            <p class="hint-word">
                              ${question.vnMeaning}
                              <span>${question.type}</span>
                            </p>
                          </div>
                        </div>`
        : type == 4
        ? `<div class="test-exercise-question">
                          <p class="question-definition">
                            ${question.enMeaning}
                          </p>
                          <span class="question-word-type">${question.type}</span>
                        </div>`
        : type == 5
        ? `<div class="test-exercise-question">
                          <div class="test-exercise-question-sound play-sound">
                            <i class="fa-solid fa-volume-high"></i>
                            <audio
                              src="${question.audio}"
                            ></audio>
                          </div>
                        </div>`
        : type == 6
        ? `<div class="test-exercise-question">
                          <p class="question-missing-sentence">
                            <span>${enSentence[0]}</span>
                            <span>_ _ _ _ _</span>
                            <span>${enSentence[2]}</span>
                          </p>
                        </div>`
        : type == 7
        ? `<div class="test-exercise-question">
                          <p class="question-vi-sentence">
                            ${question.vnSentence}
                          </p>
                        </div>`
        : type == 8
        ? `<div class="test-exercise-question">
                          <div class="test-exercise-question-sound play-sound">
                            <i class="fa-solid fa-volume-high"></i>
                            <audio
                              src="${question.audio}"
                            ></audio>
                          </div>
                          <p class="question-phonetic">${question.phonetic}</p>
                        </div>`
        : type == 9 || type == 10
        ? `<div class="test-exercise-question">
                          <div class="question-hint">
                            <img
                              class="hint-image"
                              src="${question.img}"
                              alt="img"
                            />
                            <p class="hint-word">
                             ${question.vnMeaning}
                              <span>${question.type}</span>
                            </p>
                          </div>
                        </div>`
        : `<div class="test-exercise-question">
                          <p class="question-definition">
                            ${question.enMeaning}
                          </p>
                          <span class="question-word-type">${question.type}</span>
                        </div>`) +
      `
                        
                      </div>

                      <div class="test-exercise-question-view-back">` +
      (type < 6 || type > 7
        ? `<div class="test-exercise-question-view-back-img">
                            <img
                              src="${question.img}"
                              alt=""
                            />
                          </div>

                          <div class="test-exercise-question-view-back-content">
                            <p class="word-content">
                              ${question.word} <span class="word-content-type">${question.type}</span>
                            </p>

                            <p class="word-content-phonetic">
                              <span class="play-sound">
                                <i class="fa-solid fa-volume-high"></i>
                                <audio
                                  src="${question.audio}"
                                ></audio>
                              </span>
                              <span class="word-content-phonetic-result"
                                >${question.phonetic}</span
                              >
                            </p>

                            <div class="text-definition-vi">${question.vnMeaning}</div>
                          </div>`
        : `<div class="testing-exercise-result-cover">
                          <div class="play-sound">
                            <i class="fa-solid fa-volume-high"></i>
                            <audio
                              src="${question.senAudio}"
                            ></audio>
                          </div>
                          <p class="result-content-example">
                            <span>${enSentence[0]}</span><span>${enSentence[1]}</span><span>${enSentence[2]}</span>
                          </p>
                          <p class="result-content-example-translate">
                            ${question.vnSentence}
                          </p>
                        </div>`) +
      `</div>
                    </div>
                  </div>` +
      (type <= 7
        ? `

                  <div class="test-exercise-answer">
                    <div class="answer-wrapper">
                      <div class="answer-choice" value="1">
                        <span>${
                          type == 1 || type == 3 || type == 4 || type == 6
                            ? question.vocabs[0]
                            : type == 2 || type == 5
                            ? question.vnMeanings[0]
                            : question.enSentences[0].replaceAll("_", "")
                        }</span>
                      </div>
                      <div class="answer-choice" value="2">
                        <span>${
                          type == 1 || type == 3 || type == 4 || type == 6
                            ? question.vocabs[1]
                            : type == 2 || type == 5
                            ? question.vnMeanings[1]
                            : question.enSentences[1].replaceAll("_", "")
                        }</span>
                      </div>
                      <div class="answer-choice" value="3">
                        <span>${
                          type == 1 || type == 3 || type == 4 || type == 6
                            ? question.vocabs[2]
                            : type == 2 || type == 5
                            ? question.vnMeanings[2]
                            : question.enSentences[2].replaceAll("_", "")
                        }</span>
                      </div>
                      <div class="answer-choice" value="4">
                        <span>${
                          type == 1 || type == 3 || type == 4 || type == 6
                            ? question.vocabs[3]
                            : type == 2 || type == 5
                            ? question.vnMeanings[3]
                            : question.enSentences[3].replaceAll("_", "")
                        }</span>
                      </div>
                    </div>
                  </div>
                </div>`
        : `<div class="test-exercise-answer answer-write">
                    <div class="answer-cover">
                      <input
                        class="answer-writing"
                        placeholder="Gõ đáp án của bạn tại đây"
                        name=""
                      />
                      <div class="answer-check">Kiểm tra</div>
                    </div>
                  </div>
                </div>`);
  });
  $testContent.append(html);

}


// Countdown Timer-------------------------------------------------------------------------------------------------
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
      timeOut();

      // clearInterval(timeCount);
    }
  }, 1000);
}

async function timeOut() {
  await stopCountDown();
  await disableChoosing();
  await showAnswer();
  await nextQuestion();
}

function stopCountDown() {
  window.clearInterval(timeCount);
}

// Run test ---------------------------------------------------------------------------------------------------------------------------

function updateTestProgress() {
  const $exercise = $(".test-exercise");
  const n = $exercise.length;
  $(".progress-title span").text(n);
  const $cur = $(".test-exercise.exercise-current");
  let index = parseInt($cur.attr("index"));
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
  const $input = $currentExercise.find(
    ".test-exercise-answer.answer-write input"
  );
  $input.focus();
  const $audio = $currentExercise.find(
    ".test-exercise-question-view-front audio"
  );
  if ($audio.length > 0) {
    playASound($audio);
  }
}

// Check answer-------------------------------------------------------------------------------------------------------

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
    disableChoosing();
    nextQuestion();
  });

  $wrongChoice.each(function (index, choice) {
    $(choice).on("click", function () {
      stopCountDown();
      $rightChoice.addClass("choose-right");
      $(choice).addClass("choose-wrong");
      playSoundWrong();
      showAnswer();
      disableChoosing();
      nextQuestion();
    });
  });
}

function disableChoosing() {
  const $allChoice = $(".test-exercise.exercise-current .answer-choice");
  $allChoice.each(function (index, option) {
    $(option).css({
      "pointer-events": "none",
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

// active submit button when input--------------------------------------------------------------------------------

function activeSubmit() {
  const $answer = $(".test-exercise.exercise-current .test-exercise-answer");
  const $input = $answer.find(".answer-writing");
  const $btn = $answer.find(".answer-check");
  $input.on("keyup", function (e) {
    if (e.keyCode == 13 && $input.val().length > 0) {
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

// Audio Play---------------------------------------------------------------------------------------------

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





// function renderQuestions(arr) {
//   const $questions = $(".test-exercise");
//   $questions.each(function (index, question) {
//     $(question).attr("answer", arr[index].word);
//     $(question).attr("answer-index", arr[index].answerIndex);
//     $(question)
//       .find(".test-exercise-question audio")
//       .attr("src", arr[index].audio);
//     $(question)
//       .find(".test-exercise-question .test-exercise-question-phonetic")
//       .text(arr[index].phonetic);
//     $(question)
//       .find(".test-exercise-question .question-phonetic")
//       .text(arr[index].phonetic);
//     $(question)
//       .find(".test-exercise-question-view-back img")
//       .attr("src", arr[index].img);
//     $(question)
//       .find(".test-exercise-question-view-back .word-content")
//       .text(arr[index].word);
//     $(question)
//       .find(".test-exercise-question-view-back .word-content-type")
//       .text(arr[index].type);
//     $(question)
//       .find(".test-exercise-question-view-back .word-content-phonetic audio")
//       .text(arr[index].audio);
//     $(question)
//       .find(".test-exercise-question-view-back .word-content-phonetic-result")
//       .text(arr[index].phonetic);
//     $(question)
//       .find(".test-exercise-question-view-back .text-definition-vi")
//       .text(arr[index].vnMeaning);
//     $(question).find(".test-exercise-question-word").text(arr[index].word);
//     $(question).find(".test-exercise-question-word span").text(arr[index].type);
//     $(question).find(".question-hint .hint-image").attr("src", arr[index].img);
//     $(question).find(".question-hint .hint-word").text(arr[index].vnMeaning);
//     $(question).find(".question-hint .hint-word span").text(arr[index].type);
//     $(question)
//       .find(".test-exercise-question .question-definition")
//       .text(arr[index].enMeaning);
//     $(question)
//       .find(".test-exercise-question .question-word-type")
//       .text(arr[index].type);
//     $(question)
//       .find(".result-content-example-translate")
//       .text(arr[index].vnSentence);
//     $(question)
//       .find(".test-exercise-question .question-vi-sentence")
//       .text(arr[index].vnSentence.replace("_", ""));
//     $(question)
//       .find(".result-content-example-translate")
//       .text(arr[index].vnSentence);

//     // Tách từ trong câu tiếng anh để render
//     let enSentence = arr[index].enSentence.split("_");
//     $(question)
//       .find(".question-missing-sentence span")
//       .eq(0)
//       .text(enSentence[0]);
//     $(question).find(".question-missing-sentence span").eq(1).text("_ _ _ _ ");
//     $(question)
//       .find(".question-missing-sentence span")
//       .eq(2)
//       .text(enSentence[2]);

//     $(question).find(".result-content-example span").eq(0).text(enSentence[0]);
//     $(question).find(".result-content-example span").eq(1).text(enSentence[1]);
//     $(question).find(".result-content-example span").eq(2).text(enSentence[2]);

//     // Answer render

//     if ($(question).attr("typeanswer") == "choose") {
//       renderAnswer($(question), $(question).attr("typequestion"), arr[index]);
//     }
//   });
// }

// function renderAnswer($question, typeQuestion, vocab) {
//   if (
//     typeQuestion == 1 ||
//     typeQuestion == 3 ||
//     typeQuestion == 4 ||
//     typeQuestion == 6
//   ) {
//     assignAnswer($question, vocab.vocabs);
//   } else if (typeQuestion == 2 || typeQuestion == 5) {
//     assignAnswer($question, vocab.vnMeanings);
//   } else {
//     let arr = vocab.enSentences.map(function (sen) {
//       return sen.replaceAll("_", "");
//     });
//     assignAnswer($question, arr);
//   }
// }

// function assignAnswer($question, arrAnswer) {
//   const $answers = $question.find(".answer-wrapper .answer-choice");
//   $answers.each(function (index, answer) {
//     $(answer).text(arrAnswer[index]);
//   });
// }