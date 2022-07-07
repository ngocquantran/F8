$(function () {
  async function init() {
    await getCurCourse();
    await getTopicsByCourse();
    await getUserTopics();

    courseTopicBtn();
    topicProgress();
  }

  init();
  courseInfoSlide();
});

let params = new URLSearchParams(window.location.search);
let courseId = params.get("id");
let topics = [];
let userTopics = [];
let courseCategory;
const URL = "http://localhost:8898/api/v1";

let userId = "1";

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
    let res = await axios.get(`${URL}/course/${courseId}`);
    renderCourseInfo(res.data);
    console.log(res.data);
    courseCategory = res.data.category.id;
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
    let res = await axios.get(`${URL}/course/${courseId}/topics`);
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

  for (let i = 0; i < arr.length; i++) {
    html +=
      ` <div class="col l-3">
                  <div class="course-content-item" topic-id="${arr[i].id}">
                 
                    <div class="course-content-item-thumb">
                      <img
                        src="${arr[i].img}"
                        alt=""
                      />
                    </div>

                    <h4 class="course-content-item-name">
                      ${arr[i].title}
                    </h4>

                    <div class="course-content-item-progress">
                      <div class="course-content-item-progress-range">
                        <div class="course-content-item-progress-value"></div>
                        <p><span>0</span>/<span>${
                          arr[i].numberOfVocabs + arr[i].numberOfSens
                        }</span></p>
                        <i class="fa-solid fa-star"></i>
                      </div>
                    </div>

                       <div class="course-content-item-btn ${
                         i == 0 ? "" : "btn-disabled"
                       }">
                          <div class="course-content-item-btn-content">  ` +
      (i == 0
        ? ` <h5>${arr[i].title}</h5>
                        <h6>Từ đã thuộc: <span>0</span>/<span>${
                          arr[i].numberOfVocabs + arr[i].numberOfSens
                        }</span></h6>
                        <a href="${
                          courseCategory == 1
                            ? "/filter.html"
                            : "/sen_learn.html"
                        }?id=${
            arr[i].id
          }" class="course-btn-longer">HỌC NGAY</a>`
        : `<p>
                              Bài học mới sẽ được mở sau khi bạn hoàn thành bài học
                              hiện tại. Hãy hoàn thành bài học hiện tại theo đúng lộ
                              trình học bạn nhé!
                            </p>`) +
      `
                            
                          </div>
                       </div>

               

                     
                  </div>
                </div>`;
  }

  $container.append(html);
}

// Render topic by User

async function getUserTopics() {
  try {
    let res = await axios.get(
      `${URL}/course/user-topic?courseId=${courseId}&userId=${userId}`
    );
    console.log(res.data);
    renderTopicStatus(res.data);
  } catch (error) {
    console.log(error);
  }
}


function renderTopicStatus(userTopicArr) {
  const $topics = $(".course-content-item");

  if (userTopicArr.length > 0) {
    //Render thông báo theo từng trạng thái của UserTopic: peding, lock, wait, pass
    userTopicArr.forEach((userTopic) => {
      const $topic = $(
        `.course-content-item[topic-id="${userTopic.topic.id}"]`
      );
     
      const title = $topic.find(".course-content-item-name").text();
      let html = "";
      switch (userTopic.status) {
        case "PENDING":
          html = `<div class="course-content-item-btn btn-pending">
                        <div class="course-content-item-btn-content">
                          <h5>${title}</h5>
                          <h6>
                            Từ đã thuộc: <span>${
                              userTopic.passedElement
                            }</span> / <span>${userTopic.totalElement}</span>
                          </h6>
                          <a href="${
                            courseCategory == 1
                              ? "/learning.html"
                              : "/sen_learn.html"
                          }?id=${
            userTopic.topic.id
          }" class="course-btn-longer"> HỌC TIẾP</a>
                          
                        </div>
                      </div>`;
          break;
        case "PASS":
          $topic.addClass("item-active");
          html = `<div class="course-content-item-btn btn-review">
                        <div class="course-content-item-btn-content">
                          <h5>${title}</h5>
                          <h6>
                            Từ đã thuộc: <span>${
                              userTopic.passedElement
                            }</span> / <span>${userTopic.totalElement}</span>
                          </h6>
                          <a href="${
                            courseCategory == 1
                              ? "/learning.html"
                              : "/sen_learn.html"
                          }?id=${
            userTopic.topic.id
          }" class="course-btn-longer"> ÔN TẬP LẠI</a>
                          <a href="${
                            courseCategory == 1
                              ? "/test.html"
                              : "/sen_test.html"
                          }?id=${
            userTopic.topic.id
          }" class="course-btn-longer btn-result"
                            >XEM KẾT QUẢ</a
                          >
                          
                        </div>
                      </div>`;
          break;

        case "CONTINUE":
          $topic.addClass("item-active");
          html = `<div class="course-content-item-btn btn-review">
                        <div class="course-content-item-btn-content">
                          <h5>${title}</h5>
                          <h6>
                            Từ đã thuộc: <span>${
                              userTopic.passedElement
                            }</span> / <span>${userTopic.totalElement}</span>
                          </h6>
                          <a href="${
                            courseCategory == 1
                              ? "/learning.html"
                              : "/sen_learn.html"
                          }?id=${
            userTopic.topic.id
          }" class="course-btn-longer"> ÔN TẬP LẠI</a>
                          <a href="${
                            courseCategory == 1
                              ? "/test.html"
                              : "/sen_test.html"
                          }?id=${
            userTopic.topic.id
          }" class="course-btn-longer btn-result"
                            >XEM KẾT QUẢ</a
                          >
                          
                        </div>
                      </div>`;
          break;

        case "NOW":
          $topic.addClass("item-current");
          html = `<div class="course-content-item-btn">
                        <div class="course-content-item-btn-content">
                          <h5>${title}</h5>
                          <h6>
                            Từ đã thuộc: <span>0</span> / <span>${
                              userTopic.totalElement
                            }</span>
                          </h6>
                           <a href="${
                             courseCategory == 1
                               ? "/filter.html"
                               : "/sen_learn.html"
                           }?id=${
            userTopic.topic.id
          }" class="course-btn-longer">HỌC NGAY</a>
                          
                        </div>
                      </div>`;
          break;
        case "WAITING":
          html = ` <div class="course-content-item-btn btn-disabled">
                      <div class="course-content-item-btn-content">
                        <p>
                          Bài học mới sẽ được mở sau khi bạn hoàn thành bài học
                          hiện tại. Hãy hoàn thành bài học hiện tại theo đúng lộ
                          trình học bạn nhé!
                        </p>
                      </div>
                    </div>`;
          break;
        case "LOCKING":
          html = ` <div class="course-content-item-btn btn-disabled">
                      <div class="course-content-item-btn-content">
                        <p>
                          Bài học này có tính phí, bạn cần mua gói học để tiếp tục.
                        </p>
                      </div>
                    </div>`;
          break;
      }
      $topic.append(html);
    });
  }
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
