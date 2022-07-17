$(function () {
  async function init() {
    await getAllCategory();
    await getAllLevels();
    renderPageByCategory();

    chooseCategory();
  }
  init();
});

const URL = "http://localhost:8898/api/v1";
let params = new URLSearchParams(window.location.search);
let categoryId = params.get("category");
let groups = [];
let courses = [];
let categories = [];
let levels = [];

//Lấy dữ liệu tất cả các category

async function getAllCategory() {
  try {
    let res = await axios.get(`${URL}/courses/category`);
    // console.log(res.data);
    categories = res.data;
    renderCategories();
  } catch (error) {
    console.log(error);
  }
}

function renderCategories() {
  const $container = $(".container__selection__list");
  $container.html("");
  let html = "";
  categories.forEach((category) => {
    html += ` <li class="container__selection__item" id-category="${
      category.id
    }">
                  <label>
                    <input
                      type="radio" 
                      name="container__selection__item" 
                     ${
                       !categoryId && category.id == 1
                         ? "checked"
                         : categoryId == category.id
                         ? "checked"
                         : ""
                     }
                      id-category="${category.id}"
                    />
                    <img
                      src="${category.img}"
                      alt=""
                    />
                  </label>
                </li>`;
  });
  $container.append(html);
}

// Lấy các nhóm khóa học theo category để render vào nút filter và nội dung container

async function getCourseGroupByCategory(categoryId) {
  try {
    let res = await axios.get(`${URL}/courses/group/${categoryId}`);
    groups = res.data;
    // console.log(groups);
    renderFilterGroupBtn();
    renderGroups(groups);
  } catch (error) {
    console.log(error);
  }
}

function renderFilterGroupBtn() {
  const $container = $(".filter-group.dropdown ul");
  $container.html("");
  let html = ` <li class="dropdown-item" id-group="0">Tất cả khóa học</li>`;
  groups.forEach((group) => {
    html += ` <li class="dropdown-item" id-group="${group.id}">${group.title}</li>`;
  });
  $container.append(html);
}

// Lấy tất cả các levels và render vào nút filter level
async function getAllLevels() {
  try {
    let res = await axios.get(`${URL}/courses/levels`);
    // console.log(res.data);
    levels = res.data;
    renderLevelsFilterBtn(levels);
  } catch (error) {
    console.log(error);
  }
}

function renderLevelsFilterBtn() {
  const $btnContainer = $(".filter-level.dropdown ul");
  $btnContainer.html("");
  let html = "";
  levels.forEach((level) => {
    html += ` <li class="dropdown-item" id-level="${level.id}">
                      <img src="${level.img}" alt="">
                      <span>${level.description} (${level.title})</span>
                    </li>`;
  });
  $btnContainer.append(html);
}

// Switch category

function chooseCategory() {
  $("input[type=radio][name=container__selection__item]").change(function () {
    categoryId = $(this).attr("id-category");

    window.history.replaceState(null, null, `?category=${categoryId}`);
    console.log(categoryId);
    renderPageByCategory(categoryId);
  });
}

async function renderPageByCategory() {
  if (!categoryId) {
    await getCourseGroupByCategory(1);
    await getCoursesByCategory(1);
  } else {
    await getCourseGroupByCategory(categoryId);
    await getCoursesByCategory(categoryId);
  }
  filterByGroup();
  filterByLevels();
}

//  Lọc Khóa học theo Nhóm
function filterByGroup() {
  const $groupItems = $(".filter-group.dropdown ul .dropdown-item");
  $groupItems.each(function (index, item) {
    $(item).on("click", function () {
      let itemId = $(this).attr("id-group");

      if (itemId == 0) {
        renderGroups(groups);
        renderCourses(courses);
      } else {
        let filteredGroups = groups.filter((group) => {
          return group.id == itemId;
        });

        let filteredCourses = courses.filter((course) => {
          return course.group.id == itemId;
        });

        renderGroups(filteredGroups);
        renderCourses(filteredCourses);
      }
    });
  });
}

// Lọc khóa học theo level
function filterByLevels() {
  const $levelItems = $(".filter-level.dropdown ul .dropdown-item");
  $levelItems.each(function (index, item) {
    $(item).on("click", function () {
      let levelId = parseInt($(this).attr("id-level"));
      let filteredCourses = courses.filter((course) => {
        let itemLevels = course.levels.map((level) => {
          return level.id;
        });
        return itemLevels.includes(levelId);
      });
      let filteredGroups = Array.from(
        new Set(filteredCourses.map((course) => course.group.id))
      ).map((id) => {
        return {
          id: id,
          title: groups.find((group) => group.id === id).title,
        };
      });

      renderGroups(filteredGroups);
      renderCourses(filteredCourses);
    });
  });
}

// Render nhóm chủ đề học----------------------------------------------------------------

function createGroupTemplate(group) {
  const $template = $(
    document.querySelector(".course-group-template").content.firstElementChild
  ).clone();
  $template.attr("id-group", group.id);
  $template.find(".container__courses-group-header-name").text(group.title);
  return $template;
}

function createGroupList(groupsArr) {
  const list = groupsArr.map(function (group) {
    return createGroupTemplate(group);
  });
  return list;
}

function renderGroups(groupsArr) {
  const $groupContent = $(".container__courses-content");
  $groupContent.html("");
  const list = createGroupList(groupsArr);
  $groupContent.append(list);
}

// Render khóa học theo nhóm từ---------------------------------------------------------

async function getCoursesByCategory(categoryId) {
  try {
    let res = await axios.get(`${URL}/courses/${categoryId}`);
    courses = res.data;

    renderCourses(courses);
  } catch (error) {
    console.log(error);0
  }
}

function createCourseTemplate(course) {
  const $template = $(
    document.querySelector(".course-template").content.firstElementChild
  ).clone();
  $template.find(".container__courses-group-item").attr("id-course", course.id);
  $template
    .find(".container__courses-group-item")
    .attr("id-group", course.group.id);

  $template
    .find(".container__courses-group-item>a")
    .attr("href", `/course.html?id=${course.id}`);

  $template.find("img").attr("src", course.thumbnail);
  $template.find("h4").text(course.title);
  $template
    .find(".container__courses-group-item-lesson span")
    .text(course.numberOfTopics);
  $template
    .find(".container__courses-group-item-desc")
    .text(course.description);

  course.levels.forEach((level) => {
    let className = level.title.toLowerCase();
    $template.find(`.${className}`).addClass("active");
  });

  return $template;
}

function createCourseList(courseArr) {
  const list = courseArr.map(function (course) {
    return createCourseTemplate(course);
  });
  return list;
}

function renderCourses(courseArr) {
  const list = createCourseList(courseArr);

  const $groups = $(".container__courses-group");
  $groups.each(function (index, group) {
    const $container = $(group).find(".container__courses-group-wrapper .row");
    const idGroup = $(group).attr("id-group");
    $container.html("");
    const listByGroup = list.filter(function (course) {
      return course[0].children[0].attributes[2].value == idGroup;
    });
    $container.append(listByGroup);
  });
}
