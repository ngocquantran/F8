$(function () {
  async function init() {
    await getTestResultOfNowStage();
    rotateVocabCard();
  }
  init();
  
});

const URL_API = "http://localhost:8898/api/v1";
let params = new URLSearchParams(window.location.search);
let topicId = params.get("id");
let userId = "1";
let testResults = [];
let known = 0,
  unknown = 0,
  totalTime = 0;

// RENDER PAGE INFORMATION--------------------------------------

async function getTestResultOfNowStage() {
  try {
    let res = await axios.get(
      `${URL_API}/test/${topicId}/vocabs/test-result/now`
    );
    testResults = res.data;
    console.log(res.data);
    renderCircleGrahp();
    renderVocabSummary();
  } catch (error) {
    console.log(error);
  }
}

function renderCircleGrahp() {
  const $graphContainer = $(".result-evaluate-circle-right-wrapper");
  $graphContainer.html("");
  let html = "";

  testResults.forEach((result) => {
    totalTime += result.testTime;
    if (result.status) {
      known++;
    } else {
      unknown++;
    }
  });
  html = ` <span class="result-evaluate-circle-percent">${known/(known+unknown)*100}%</span>
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

  $(".result-evaluate-circle-desc span").text(totalTime);
  $(".result-evaluate-circle-know span").text(known);
  $(".result-evaluate-circle-un-know span").text(unknown);

}

function renderVocabSummary() {
  $(".vocab-group.forget .vocab-group-header span").text(unknown);
  $(".vocab-group.remember .vocab-group-header span").text(known);
  const $forgetContainer = $(".vocab-group.forget .vocab-list .row");
  const $rememberContainer = $(".vocab-group.remember .vocab-list .row");
  $forgetContainer.html("");
  $rememberContainer.html("");
  
  testResults.forEach(result => {
    let html = `  <div class="col l-2-4">
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
  })

}

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
