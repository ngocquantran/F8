$(function () {
  getCourseGroupByCategory("Từ vựng");
  getCoursesByCategory("từ vựng");
});

const URL = "http://localhost:8898/api/v1";
let groups = [];
let courses = [];
let categories = [];

async function getCourseGroupByCategory(category) {
  try {
    let res = await axios.get(`${URL}/courses/group/${category}`);

    groups = res.data;
    renderGroups(groups);
  } catch (error) {
    console.log(error);
  }
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

async function getCoursesByCategory(category) {
  try {
    let res = await axios.get(`${URL}/courses/${category}`);

    courses = res.data;
    renderCourses(courses);
  } catch (error) {
    console.log(error);
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
