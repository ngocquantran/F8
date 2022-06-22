$(function () {
  init();

  async function init() {
    // await getStudiedContent(1);

    learningViewportSlideBox();

    learningWords();
    updateLearningVocabProgress(
      $(".learning-word-layer").length,
      0,
      $(".learning-word-progress-range span"),
      $(".learning-word-progress-title strong")
    );
    playWordSound();
    playEachContext();
  }
  playSentenceByWords();

  $(".context-content .context-title audio")[0].ontimeupdate = function () {
    showContextPlaying(this);
  };
});

let studiedVocabs = [];
const URL_API = "http://localhost:8080/api/v1";

// Get Learning List Vocabs ------------------------------------------------------------------------------------------------
async function getStudiedContent(id) {
  try {
    let res = await axios.get(`${URL_API}/learning/${id}`);
    studiedVocabs = res.data;
    renderStudiedVocabs();
  } catch (error) {
    console.log(error);
  }
}

function createVocabTemplate(vocab) {
  const $template = $(
    document.querySelector(".vocab-template").content.firstElementChild
  ).clone();

  $template.attr({
    words: vocab.word,
    "words-id": vocab.id,
  });
  $template.find(".card-img img").attr("src", vocab.img);
  $template.find(".card-img img").attr("src", vocab.img);
  $template.find(".card-content-text").text(vocab.enMeaning);
  $template.find(".card-content-type").text(vocab.type);
  $template.find(".card-content-text-main").text(vocab.word);
  $template.find(".card-content-phonetic audio").attr("src", vocab.audio);
  $template.find(".card-content-phonetic span:last-child").text(vocab.phonetic);
  $template.find(".text-definition-vi").text(vocab.vnMeaning);
  $template.find(".card-content-example audio").attr("src", vocab.senAudio);
  $template.find(".example-vi").text(vocab.vnSentence);
  // Tách câu ví dụ tiếng anh
  let enExample = vocab.enSentence.split("_");
  $template.find(".example-en span").eq(0).text(enExample[0]);
  $template.find(".example-en span").eq(1).text(enExample[1]);
  $template.find(".example-en span").eq(2).text(enExample[2]);
  console.log($template.find(".example-en"));

  return $template;
}

function createVocabList() {
  const list = studiedVocabs.map(function (vocab) {
    return createVocabTemplate(vocab);
  });
  return list;
}

function renderStudiedVocabs() {
  const $studiedContent = $(".learning-word-content");
  $studiedContent.html("");
  const list = createVocabList();
  $studiedContent.append(list);
}

// Learning page------------------------------------------------------------------------------------------------

// Xoay flashcard từ vựng để học------------------------------------------------------------------
function learningViewportSlideBox() {
  const $cards = $(".learning-word-viewport-container");
  $cards.each(function (index, card) {
    const $btn = $(card).find(".card-turn");
    const $word = $(card).find(".learning-word-viewport-slide.slide2 audio");
    const $sentence = $(card).find(
      ".learning-word-viewport-slide.slide3 audio"
    );
    // let pos = 0;
    let pos = parseInt($(card).attr("rotate-data"));
    let cur = 1;
    $btn.on("click", function () {
      pos -= 120;
      $(card).attr("rotate-data", `${pos}`);
      cur++;
      if (cur > 3) {
        cur = 1;
      }
      $(card).css({
        transform: `rotateX(${pos}deg)`,
      });
      $(card).find(".learning-word-viewport-slide").removeClass("on");
      if (cur == 2) {
        // playASound($word);
        $(card).find(".learning-word-viewport-slide.slide2").addClass("on");
      } else if (cur == 3) {
        // playASound($sentence);
        $(card).find(".learning-word-viewport-slide.slide3").addClass("on");
      } else {
        $(card).find(".learning-word-viewport-slide.slide1").addClass("on");
      }
    });
  });
}

// Chuyển từ học tiếp/quay lại------------------------------------------------------------------

function learningWords() {
  const $cards = $(".learning-word-layer");
  const n = $cards.length;
  $(".learning-word-progress-title span").text(`${n}`);

  let index = 0;
  setCurrentLearningCart($cards.eq(0));

  const $btnNext = $(".learning-word-bottom-btn.btn-next");
  $btnNext.on("click", function () {
    $cards.each(function (index, card) {
      $(card).removeClass("left");
      $(card).addClass("right");
    });
    if (index < n - 1) {
      $(".learning-word-layer.hide").removeClass("hide");

      $cards.eq(index).addClass("hide");
      $cards.eq(index).removeClass("show");
      $cards.eq(index).removeClass("layer-current");
      index++;
      updateLearningVocabProgress(
        n,
        index,
        $(".learning-word-progress-range span"),
        $(".learning-word-progress-title strong")
      );

      setCurrentLearningCart($cards.eq(index));
    }
  });

  const $btnBack = $(".learning-word-bottom-btn.btn-back");
  $btnBack.on("click", function () {
    $cards.each(function (index, card) {
      $(card).removeClass("right");
      $(card).addClass("left");
    });
    if (index > 0) {
      $(".learning-word-layer.hide").removeClass("hide");
      $cards.eq(index).removeClass("show");
      $cards.eq(index).removeClass("layer-current");
      $cards.eq(index).addClass("hide");
      index--;
      setCurrentLearningCart($cards.eq(index));
      updateLearningVocabProgress(
        n,
        index,
        $(".learning-word-progress-range span"),
        $(".learning-word-progress-title strong")
      );
    }
  });
}

function setCurrentLearningCart($card) {
  $card.addClass("show");
  $card.addClass("layer-current");
  // $card.find(".filter-word-item-sound audio").get(0).play();
}

function updateLearningVocabProgress(
  totalLength,
  curIndex,
  $progressValue,
  $progressValueText
) {
  $progressValue.css({
    width: ((curIndex + 1) / totalLength) * 100 + "%",
  });
  $progressValueText.text(curIndex + 1);
}

//MP3-------------------------------------------------------------------------------------------------------------------------------

function playSoundRight() {
  $(".sound-answer-right")[0].load();
  $(".sound-answer-right")[0].onloadeddata = function () {
    $(".sound-answer-right")[0].play();
  };
}

function playSoundWrong() {
  $(".sound-answer-wrong")[0].load();
  $(".sound-answer-wrong")[0].onloadeddata = function () {
    $(".sound-answer-wrong")[0].play();
  };
}

function playASound($sound) {
  $sound[0].load();
  $sound[0].onloadeddata = function () {
    pauseEverySound();
    $sound[0].play();
  };
}

function pauseEverySound() {
  $("audio").each(function (index, audio) {
    $(audio)[0].pause();
  });
}

function playWordSound() {
  const $sound = $(".play-sound");
  $sound.each(function (index, sound) {
    const $btn = $(sound).find("i");
    const $mp3 = $(sound).find("audio");
    $btn.on("click", function () {
      pauseEverySound();
      playASound($mp3);
    });
  });
}

function playASoundInTime($sound, start, end) {
  // return new Promise(function (resolve, reject) {
    $sound[0].load();
    let duration = $sound[0].duration;
    console.log(duration);
    if (start > duration || end > duration || start > end) {
      console.log("Kiểm tra lại timestamp");
      return;
    }
    $sound[0].onloadeddata = function () {
      $sound[0].currentTime = start / 1000;
      pauseEverySound();
      $sound[0].play();
      setTimeout(() => {
        $sound[0].pause();
        $sound[0].currentTime=0;
        // resolve();
      }, end - start);
    };
  // });
}

function playEachContext2() {
  const $contextContent = $(".context-group-content");
  const $sound = $contextContent.find(".context-title .play-sound audio");
  const $contexts = $(".context-item");

  $contexts.each(function (index, context) {
    $(context).on("click", async function () {
      $(context).toggleClass("context-active");
      $(context).find(".speaker, .speaker-active").toggleClass("hidden");

      playASoundInTime(
        $sound,
        $(context).attr("data-start"),
        $(context).attr("data-end")
      ).then(() => {
        $(context).toggleClass("context-active");
        $(context).find(".speaker, .speaker-active").toggleClass("hidden");
      });
    });
  });
}

function playEachContext() {
  const $contextContent = $(".context-group-content");
  const $sound = $contextContent.find(".context-title .play-sound audio");
  const $contexts = $(".context-item");

  $contexts.each(function (index, context) {
    $(context).on("click", function () {
      playASoundInTime(
        $sound,
        $(context).attr("data-start"),
        $(context).attr("data-end")
      );
    });
  });
}

function showContextPlaying(sound) {
  const $contexts = $(".context-content .context-item");
  // const sound = $(".context-content .context-title audio")[0];
  const timestamp = [];
  $contexts.each(function (index, context) {
    timestamp.push({
      start: parseInt($(context).attr("data-start")),
      end: parseInt($(context).attr("data-end")),
    });
  });

  timestamp.forEach(function (time, index) {
    if (
      sound.currentTime * 1000 < time.end &&
      sound.currentTime * 1000 > time.start
    ) {
      $contexts.eq(index).addClass("context-active");
      $contexts.eq(index).find(".speaker").addClass("hidden");
      $contexts.eq(index).find(".speaker-active").removeClass("hidden");
    } else {
      $contexts.eq(index).removeClass("context-active");
      $contexts.eq(index).find(".speaker").removeClass("hidden");
      $contexts.eq(index).find(".speaker-active").addClass("hidden");
    }
  });
}

function playSentenceByWords() {
  const $layer = $(".learning-word-layer.layer");
  $layer.each(function (index, layer) {
    const sound = $(layer).find(".sound .sound-word audio")[0];
    const $words = $(layer).find(".word .content span");
    console.log($words);
    const timestamp = [];
    $words.each(function (index, word) {
      timestamp.push({
        start: parseInt($(word).attr("data-start")),
        end: parseInt($(word).attr("data-end")),
      });
    });
    console.log(timestamp);
    const $phonetics = $(layer).find(".word .pronounce span");
    sound.ontimeupdate = function () {
      timestamp.forEach(function (time, index) {
        if (
          sound.currentTime * 1000 < time.end &&
          sound.currentTime * 1000 > time.start
        ) {
          $words.eq(index).addClass("color-active");
          $phonetics.eq(index).addClass("color-active");
        } else {
          $words.eq(index).removeClass("color-active");
          $phonetics.eq(index).removeClass("color-active");
        }
      });
    };
  });
}
