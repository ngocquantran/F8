$(function () {
  courseInfoSlide();
  courseTopicBtn();
  filterVocab();
  topicProgress();
  playWordSound();
  // resultFilterChart();
  goToChooseWord();
  chooseWords();
});

function courseInfoSlide() {
  const slider = $(".course__info-list.lightSlider").lightSlider({
    item: 1,
    loop: true,
    easing: "cubic-bezier(0.25, 0, 0.25, 1)",
    speed: 600,
    pager: false,
    controls: false,
    adaptiveHeight: true,
    responsive: [
      {
        breakpoint: 800,
        settings: {
          item: 3,
          slideMove: 1,
          slideMargin: 6,
        },
      },
      {
        breakpoint: 480,
        settings: {
          item: 2,
          slideMove: 1,
        },
      },
    ],
  });
  $(".course__info-next-btn").on("click", function () {
    slider.goToNextSlide();
  });
}

// COURSE PAGE ACTION-------------------------------------------
function courseTopicBtn() {
  const topics = $(".course-content-item-thumb");

  $(document).click((event) => {
    if (!$(event.target).closest(".course-content-item-thumb").length) {
      const $curTopicBtn = $(".course-content-item-btn.button-active");
      $curTopicBtn.removeClass("button-active");
    }
  });

  topics.each(function (index, topic) {
    $(topic).on("click", function (e) {
      const $curTopicBtn = $(".course-content-item-btn.button-active");
      $curTopicBtn.removeClass("button-active");
      $(topic).siblings(".course-content-item-btn").addClass("button-active");
    });
  });
}

function topicProgress() {
  const $progressRange = $(".item-active .course-content-item-progress-range");
  $progressRange.each(function (index, progess) {
    const $progressValue = $(progess).find(
      ".course-content-item-progress-value"
    );

    const total = parseInt($(progess).find("span:last-child").text());
    const cur = parseInt($(progess).find("span:first-child").text());
    const $star = $(progess).find("i");
    $progressValue.css({
      width: (cur / total) * 100 + "%",
    });

    if (cur / total >= 0.9) {
      $progressValue.css({
        border: "6px solid #f1b522",
        background: "var(--color-orange)",
      });
      $star.css({
        color: "var(--color-orange)",
      });
    } else if (cur / total > 0) {
      $progressValue.css({
        background: " #8ee000",
        border: "6px solid #7AC70C",
      });
      $star.css({
        color: "#bdbbbb",
      });
    }
  });
}

// Filter word choice---------------------------------------------------

function filterVocab() {
  let index = 0;
  const $cards = $(".filter-word-layer.right");
  setCurrentFilterCart($cards.eq(0));

  
  const n = $cards.length;

  $(".filter-word-progress-title span").text(`${n}`);

  updateFilterVocabProgress(
    n,
    index,
    $(".filter-word-progress-range span"),
    $(".filter-word-progress-title strong")
  );

  // const $progressValue = $(".filter-word-progress-range span");
  // $progressValue.css({
  //   width: ((index + 1) / n) * 100 + "%",
  // });

  const btn = $(".filter-word-bottom-btn");
  btn.on("click", function () {
    if (index < n - 1) {
      $(".filter-word-layer.right.hide").removeClass("hide");

      $cards.eq(index).addClass("hide");
      $cards.eq(index).removeClass("show");
      $cards.eq(index).removeClass("layer-current");
      index++;
      updateFilterVocabProgress(
        n,
        index,
        $(".filter-word-progress-range span"),
        $(".filter-word-progress-title strong")
      );

      setCurrentFilterCart($cards.eq(index));
      // $cards.eq(index).find(".filter-word-item-sound audio").get(0).play();
    }
  });
}

function setCurrentFilterCart($card) {
  $card.addClass("show");
  $card.addClass("layer-current");
  const $sound = $card.find(".filter-word-item-sound audio");
  playASound($sound);
}

function updateFilterVocabProgress(
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

function playWordSound() {
  const sound = document.querySelectorAll(".play-sound");
  sound.forEach(function (sound) {
    const btn = sound.childNodes[1];
    const mp3 = sound.childNodes[3];
    btn.addEventListener("click", function () {
      mp3.play();
    });
  });
}

function playASound($sound) {
  $sound[0].load();
  $sound[0].onloadeddata = function () {
    $sound[0].play();
  };
}

// Result filter circle--------------------------------------------

// function resultFilterChart() {
//   var xValues = ["Italy", "France", "Spain", "USA", "Argentina"];
//   var yValues = [55, 49, 44, 24, 15];
//   var barColors = ["#b91d47", "#00aba9", "#2b5797", "#e8c3b9", "#1e7145"];

//   new Chart("myChart", {
//     type: "doughnut",
//     data: {
//       labels: xValues,
//       datasets: [
//         {
//           backgroundColor: barColors,
//           data: yValues,
//         },
//       ],
//     },
//     options: {
//       title: {
//         display: true,
//         text: "World Wide Wine Production 2018",
//       },
//     },
//   });
// }

// Choose word to study after filter----------------------------
function goToChooseWord() {
  $(".button-ready-group .button-start-study").on("click", function () {
    $(".modal").removeClass("hidden");
    $(".choose-study-word").removeClass("hidden");
  });
}

function chooseWords() {
  const $btnAllWords = $(".choose-study-word-btn.all-word");
  const $btnUnKnownWords = $(".choose-study-word-btn.unknown-word");

  $btnAllWords.on("click", function () {
    chooseAllWords();
    updateStudyBtn();
  });

  $btnUnKnownWords.on("click", function () {
    chooseUnKnownWords();
    updateStudyBtn();
  });

  const $words = $(".choose-study-word-item label");
  $words.each(function (index, word) {
    $(word).on("click", function () {
      updateStudyBtn();
    });
  });
}

function chooseAllWords() {
  const $wordInputs = $(".choose-study-word-item label input");
  $wordInputs.each(function (index, input) {
    $(input).prop("checked", true);
  });
}

function chooseUnKnownWords() {
  const $checkedInput = $(".choose-study-word-item label input:checked");
  $checkedInput.each(function (index, input) {
    $(input).prop("checked", false);
  });

  const $unknownWords = $(".choose-study-word-item.item-unknown label input");
  $unknownWords.each(function (index, input) {
    $(input).prop("checked", true);
  });
}

function updateStudyBtn() {
  const $wordInputs = $(".choose-study-word-item label input:checked");
  const $activeBtn = $(
    ".choose-study-word-content-footer .choose-word-btn.btn-choose-active"
  );
  const $inactiveBtn = $(
    ".choose-study-word-content-footer .choose-word-btn:not(.btn-choose-active)"
  );
  if ($wordInputs.length == 0) {
    $activeBtn.removeClass("btn-choose-active");
  } else {
    $inactiveBtn.addClass("btn-choose-active");
  }
}
