$(function () {
  getCurCourse();
  getTopicsByCourse();
  courseInfoSlide();
  courseTopicBtn();
  topicProgress();
});

let params = new URLSearchParams(window.location.search);
let id = params.get("id");
let topics = [];
const URL = "http://localhost:8898/api/v1";

// CSS page-------------------------------------------------------------------

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

// RENDER PAGE -------------------------------------------------------------------

// Render Course Info

async function getCurCourse() {
  try {
    let res = await axios.get(`${URL}/course/${id}`);
    renderCourseInfo(res.data);
  } catch (error) {
    console.log(error);
  }
}

function renderCourseInfo(course) {
  $(".container .course__header").text(course.title);

  course.levels.forEach((level) => {
    let className = level.title.toLowerCase();
    $(`.${className}`).addClass("active");
  });

  let goals = course.goal.split("_");
  renderCourseInfoItem(
    goals,
    $(".course__info-item.goal .course__info-item-content")
  );

  let contents = course.content.split("_");
  renderCourseInfoItem(
    contents,
    $(".course__info-item.content .course__info-item-content")
  );

  let students = course.targetLearner.split("_");
  renderCourseInfoItem(
    students,
    $(".course__info-item.student .course__info-item-content")
  );
}

function renderCourseInfoItem(arr, $container) {
  $container.html("");
  let html = "";
  arr.forEach((element) => {
    html += ` <p>
                    <span
                      >${element}</span
                    >
                  </p>`;
  });
  $container.append(html);
}

// Render Course Topics

async function getTopicsByCourse() {
  try {
    let res = await axios.get(`${URL}/course/${id}/topics`);
    console.log(res.data);
    renderTopicInfo(res.data);
  } catch (error) {
    console.log(error);
  }
}

function renderTopicInfo(arr) {
  $(".course-content-header span").text(arr.length);
  const $container = $(".course-content-list .row");
  $container.html("");
  let html = "";
  arr.forEach((element) => {
    html += ` <div class="col l-3">
                  <div class="course-content-item item-active">
                  <a href="${element.vocabs.length>0?"/filter.html":"/sen_learn.html"}?id=${element.id}">
                    <div class="course-content-item-thumb">
                      <img
                        src="${element.img}"
                        alt=""
                      />
                    </div>

                    <h4 class="course-content-item-name">
                      ${element.title}
                    </h4>

                    <div class="course-content-item-progress">
                      <div class="course-content-item-progress-range">
                        <div class="course-content-item-progress-value"></div>
                        <p><span>0</span>/<span>${
                          element.vocabs.length + element.sentences.length
                        }</span></p>
                        <i class="fa-solid fa-star"></i>
                      </div>
                    </div>

                    <div class="course-content-item-btn btn-review">
                      <div class="course-content-item-btn-content">
                        <h5>WHY MONKEY HAS NO HOME</h5>
                        <h6>Từ đã thuộc: <span>19</span> / <span>19</span></h6>
                        <a href="" class="course-btn-longer"> ÔN TẬP LẠI</a>
                        <a href="" class="course-btn-longer btn-result"
                          >XEM KẾT QUẢ</a
                        >
                      </div>
                    </div>
                     </a>
                  </div>
                </div>`;
  });
  $container.append(html);
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
