$(function () {
  

  chooseWords();
});



// MP3 handle-------------------------------------------------------

function playWordSound() {
  const $sound = $(".play-sound");
  $sound.each(function (index, sound) {
    const $btn = $(sound).find("i");
    const $mp3 = $(sound).find("audio");
    $btn.on("click", function () {
      playASound($mp3);
    });
  });
}

function playASound($sound) {
  $sound[0].load();
  $sound[0].onloadeddata = function () {
    $sound[0].play();
  };
}

// Choose word to study after filter----------------------------


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
