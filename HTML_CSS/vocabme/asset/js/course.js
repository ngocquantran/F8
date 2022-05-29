$(function () {
  courseInfoSlide();
  courseTopicBtn();
  topicProgress();

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

