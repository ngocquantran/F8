$(function () {
  rotateVocabCard();
});

function rotateVocabCard() {
  const $cards = $(".vocab-item");
  $cards.each(function (index, card) {
    const $cardView = $(card).find(".vocab-item-viewport");
    const $frontBtn = $(card).find(".item-front");
    const $backBtn = $(card).find(".item-back");
    $frontBtn.on("click", function () {
      $cardView.css({
        transform: "rotateY(-180deg)",
      });
      playASound($(card).find(".item-back audio"));
    });
    $backBtn.on("click", function () {
      $cardView.css({
        transform: "rotateY(0deg)",
      });
    });
  });
}

function playASound($sound) {
  $sound[0].load();
  $sound[0].onloadeddata = function () {
    $sound[0].play();
  };
}
