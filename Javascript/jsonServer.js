/*
CRUD
- Create: Tạo mới  --> POST
- Read: Lấy dữ liệu  --> GET
- Update: Chỉnh sửa  --> PUT / PATCH
- Delete: Xóa  --> DELETE

Postman


*/
let users = [];

let userApi = "http://localhost:3000/users";


function getUsers(callback) {
    fetch(userApi)
      .then(function (response) {
        return response.json();
      })
      .then(callback); 
}



function renderUsers(users) {
    const $userList = $(".user-list");
    let htmls = users.map(function (user) {
        return `<tr class="user-id-${user.id}">
                        <td>${user.id}</td>
                        <td>${user.fullName}</td>
                        <td>${user.gender}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.phone}</td>
                        <td><button class="clear-btn clear-${user.id}">Xóa</button></td>
                        
                    </tr> `;
    })

    let html = htmls.join("");
    $userList.html(html);
        
}

function getNewUserInfo() {
    let fullName = $(".name-input").val();
    let gender = $(".gender-input").val();
    let age = $(".age-input").val();
    let email = $(".email-input").val();
    let phone = $(".phone-input").val();

    const newUser = {
        fullName: fullName,
        gender: gender,
        age: age,
        email: email,
        phone: phone,
    }
    return newUser;

}

function createNewUser() {
    let newUser = getNewUserInfo();
    let options = {
      method: "POST",
      body: JSON.stringify(newUser),
      headers: {
        "Content-Type": "application/json",
      },
    };
    fetch(userApi, options)
        .then(function (response) {
            return response.json();
        })
    
}

function handleAddform() {
    createNewUser();
}




$(function () {
    

    $(".add-btn").on('click', createNewUser);
    getUsers(renderUsers);
    



})


