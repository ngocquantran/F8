$(function () {
  async function init() {
    await getTestResultOfNowStage();
    await getCourseIdFromTopic();
    await getTopicRecords();
    await getTopRankTopic();
    await getUserTopicState();
    await getUserRank();

    rotateVocabCard();
  }
  init();
  $(".btn-learning").on("click", initChooseWord);
  $(".btn-testing").attr("href", `/test.html?id=${topicId}`);
});

const URL_API = "http://localhost:8898/api/v1";
let params = new URLSearchParams(window.location.search);
let topicId = params.get("id");
let userId = "1";
let testResults = [];
let known = 0,
  unknown = 0,
  totalTime = 0;
let userTopicRecords = [];

// RENDER PAGE INFORMATION-----------------------------------------------------------------------------------------------

//render back button

async function getCourseIdFromTopic() {
  try {
    let res = await axios.get(`${URL_API}/topic/${topicId}/to-course`);
    console.log(res.data);
    $("a.header-menu-item").attr(
      "href",
      `course.html?id=${res.data.course.id}`
    );
  } catch (error) {
    console.log(error);
  }
}

//Lấy dữ liệu userTopicVocab từ database để render kết quả test
async function getTestResultOfNowStage() {
  try {
    let res = await axios.get(`${URL_API}/test/${topicId}/vocabs/test-result`);
    testResults = res.data;
    console.log(testResults);
    renderCircleGrahp();
    renderVocabSummary();
  } catch (error) {
    console.log(error);
  }
}

//Get User rank
async function getUserRank() {
  try {
    let res = await axios.get(
      `${URL_API}/test/${topicId}/vocabs/test-result/user-rank`
    );
    console.log(res.data);
    renderUserRank(res.data);
  } catch (error) {
    console.log(error);
  }
}

function renderUserRank(rank) {
  $(".user-rank-item .rank").text(rank.rank);
  $(".user-rank-item .name").text(rank.userName);
  $(".user-rank-item .right").text(`${rank.rightAnswers} từ đúng`);
  $(".user-rank-item .time").text(`Thời gian ${rank.testTime}s`);
}

// Render biểu đồ hình tròn: thuộc-chưa thuộc
function renderCircleGrahp() {
  const $graphContainer = $(".result-evaluate-circle-right-wrapper");
  $graphContainer.html("");
  let html = "";

  testResults.forEach((result) => {
    if (result.status) {
      known++;
    } else {
      unknown++;
    }
  });
  html = ` <span class="result-evaluate-circle-percent">${Math.floor(
    (known / (known + unknown)) * 100
  )}%</span>
                    <canvas
                      id="result-evaluate-circle-chart"
                      style="width: 120px; height: 120px"
                    ></canvas>
                    <script>
                      var xValues = ["Thuộc", "Chưa thuộc"];
                      var yValues = [${known}, ${unknown}];
                      var barColors = ["#58CC02", "rgb(255, 1, 78)"];

                      new Chart("result-evaluate-circle-chart", {
                        type: "doughnut",
                        data: {
                          labels: xValues,
                          datasets: [
                            {
                              backgroundColor: barColors,
                              data: yValues,
                            },
                          ],
                        },
                        options: {
                          plugins: {
                            legend: {
                              display: false,
                            },
                          },
                        },
                      });
                    </script>`;
  $graphContainer.append(html);

  $(".result-evaluate-circle-know span").text(known);
  $(".result-evaluate-circle-un-know span").text(unknown);
}

function renderTestTime(arrRecord) {
  arrRecord.forEach((record) => {
    if (record.stage === "NOW") {
      $(".result-evaluate-circle-desc span").text(record.testTime);
    }
  });
}

// Render list từ vựng đã thuộc và chưa thuộc
function renderVocabSummary() {
  $(".vocab-group.forget .vocab-group-header span").text(unknown);
  $(".vocab-group.remember .vocab-group-header span").text(known);
  const $forgetContainer = $(".vocab-group.forget .vocab-list .row");
  const $rememberContainer = $(".vocab-group.remember .vocab-list .row");
  $forgetContainer.html("");
  $rememberContainer.html("");

  testResults.forEach((result) => {
    let html = `  <div class="col ">
                    <div class="vocab-item">
                      <div class="vocab-item-viewport">
                        <div class="item-front">
                          <img
                            class="item-image"
                            src="${result.vocab.img}"/>
                          <p class="item-text">${result.vocab.word}</p>
                          <p class="item-pronounce">${result.vocab.phonetic}</p>

                          <span class="rotate"
                            ><i class="fa-solid fa-rotate"></i
                          ></span>
                        </div>
                        <div class="item-back">
                          <div class="item-back-cover">
                            <audio
                              src="${result.vocab.audio}"
                            ></audio>
                            <p class="item-type">${result.vocab.type}</p>
                            <p class="item-text-vi">${result.vocab.vnMeaning}</p>
                            <span class="rotate"
                              ><i class="fa-solid fa-rotate"></i
                            ></span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>`;
    if (result.status) {
      $rememberContainer.append(html);
    } else {
      $forgetContainer.append(html);
    }
  });
}

// HIển thị thông báo

async function getUserTopicState() {
  try {
    let res = await axios.get(
      `${URL_API}/test/${topicId}/vocabs/test-result/topic-state`
    );
    console.log(res.data);
    let state = res.data;
    renderNotification(state);
  } catch (error) {
    console.log(error);
  }
}

function renderNotification(state) {
  const $notifyTitle = $(".result-message-notify");
  const $notifyDesc = $(".result-message-desc");
  let bestRecord = userTopicRecords.filter((record) => {
    return record.stage === "BEST";
  });
  let nowRecord = userTopicRecords.filter((record) => {
    return record.stage === "NOW";
  });
  if (state === "PASS") {
    if (bestRecord.length == 0) {
      //Pass lần đầu
      $notifyTitle.text("Bạn đã vượt qua chủ đề từ vựng này");
      $notifyDesc.text(
        "Bài mới đã được mở, bạn có thể học ngay. Tuy nhiên, hãy kiểm tra lại để có kết quả tốt nhất"
      );
    } else {
      if (bestRecord.rightAnswers < testResults.length) {
        //Pass lần đầu do kết quả tốt nhất trước đó vẫn chưa pass
        $notifyTitle.text("Bạn đã vượt qua chủ đề từ vựng này");
        $notifyDesc.text(
          "Bài mới đã được mở, bạn có thể học ngay. Tuy nhiên, hãy kiểm tra lại để có kết quả tốt nhất"
        );
      } else {
        // Nếu đã từng pass trước đó
        if (nowRecord.rightAnswers < testResults.length) {
          //  lần test hiện tại không trả lời đúng 100%
          $notifyTitle.text("Bạn đã quên từ vựng");
          $notifyDesc.text("Hãy cải thiện để có kết quả cao hơn");
        } else if (nowRecord.testTime > bestRecord.testTime) {
          //  lần test hiện tại pass nhưng có thời gian chậm hơn
          $notifyTitle.text("Bạn đã vượt qua chủ đề từ vựng này");
          $notifyDesc.text(
            "Thành tích lần này của bạn chưa vượt qua thành tích cao nhất mà bạn đã từng đạt được, hãy cải thiện để có kết quả cao hơn!"
          );
        } else {
          //  lần test hiện tại có kết quả tốt nhất
          $notifyTitle.text("Bạn đã vượt qua chủ đề từ vựng này");
          $notifyDesc.text(
            "Bài mới đã được mở, bạn có thể học ngay. Tuy nhiên, hãy kiểm tra lại để có kết quả tốt nhất"
          );
        }
      }
    }
  } else {
    //Không pass chủ đề
    $notifyTitle.text("Bạn chưa vượt qua chủ đề này");
    $notifyDesc.text(
      "Để vượt qua, bạn cần đạt kết quả trả lời đúng 100%. Hãy học lại hoặc kiểm tra lại để có kết quả cao hơn."
    );
  }
}

// Lấy dữ liệu UserTopicRecord từ database để render Biểu đồ tiến độ học
async function getTopicRecords() {
  try {
    let res = await axios.get(
      `${URL_API}/test/${topicId}/vocabs/test-result/record`
    );
    console.log(res.data);
    userTopicRecords = res.data;
    renderTestTime(userTopicRecords);
    renderRecordGraph(userTopicRecords);
  } catch (error) {
    console.log(error);
  }
}

// Render biểu đồ tiến độ học
function renderRecordGraph(arrRecord) {
  let timeArr = getdataFromRecordData(arrRecord, "testTime");
  let answerArr = getdataFromRecordData(arrRecord, "rightAnswers");
  var ctx = document
    .getElementById("result-evaluate-graph-chart")
    .getContext("2d");
  var myChart = new Chart(ctx, {
    type: "line",
    data: {
      labels:
        answerArr.length == 2
          ? ["Trước khi học", "Hiện tại"]
          : ["Trước khi học", "Tốt nhất", "Gần đây nhất", "Hiện tại"],
      datasets: [
        {
          label: ["Số từ đã thuộc"], // Name the series
          data: answerArr, // Specify the data values array
          fill: false,
          borderColor: "#2196f3", // Add custom color border (Line)
          backgroundColor: "#2196f3", // Add custom color background (Points and Fill)
          borderWidth: 1, // Specify bar border width
        },
      ],
    },
    options: {
      plugins: {
        tooltip: {
          callbacks: {
            afterBody: function (context) {
              return `\nThời gian: ${timeArr[context[0].dataIndex]}s`;
            },
          },
        },
      },
      responsive: true, // Instruct chart js to respond nicely.
      maintainAspectRatio: false, // Add to prevent default behaviour of full-width/height'

      scales: {
        y: { beginAtZero: true, max: 12 },
      },
    },
  });
}

// Sắp xếp lại thứ tự record First-Best-Previous-Now để render biểu đồ tiến độ
function getdataFromRecordData(arrRecord, character) {
  let arr = [];
  arrRecord.forEach((record) => {
    if (record.stage === "FIRST") {
      arr.push(record[character]);
    }
  });
  arrRecord.forEach((record) => {
    if (record.stage === "BEST") {
      arr.push(record[character]);
    }
  });
  arrRecord.forEach((record) => {
    if (record.stage === "PREVIOUS") {
      arr.push(record[character]);
    }
  });
  arrRecord.forEach((record) => {
    if (record.stage === "NOW") {
      arr.push(record[character]);
    }
  });
  return arr;
}

// Lấy dữ liệu bảng xếp hạng kết quả test
async function getTopRankTopic() {
  try {
    let resp = await axios.get(
      `${URL_API}/test/${topicId}/vocabs/test-result/top-rank`
    );
    console.log(resp.data);
  } catch (error) {
    console.log(error);
  }
}

// Chức năng lật flashcard để xem thông tin từ vựng
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
