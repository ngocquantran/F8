//***************************************************************** */
// // JSON: Number, Boolean, Null, Array, Object
// Mã hóa / Giải Mã
// Encode / decode
// Stringify: Từ Javascript types -> JSON
// Parse: từ JSON -> Javascript types
//***************************************************************** */

// let json = '["Java","JS"]';
// let json = '{"name": "NgocQuan","age":"18"}';






$(function () {
  let a = "1";
  console.log(JSON.parse(a));

  let b = '{"name": "NgocQuan","age":18}';
  console.log(JSON.parse(b));
console.log(typeof JSON.parse(b));
    

    let c = '["Java","JS"]';
    console.log(JSON.parse(c));
    console.log(typeof JSON.parse(c));

    console.log(typeof JSON.stringify(true));

    console.log(JSON.stringify({
        name: "Quan",
        age:29,
    }))


    let postApi = "https://jsonplaceholder.typicode.com/comments";
    fetch(postApi)
      .then(function (response) {
        return response.json();
        // JSON.parse: JSON -> Javascript type
      })
      .then(function (comments) {
        console.log(comments);
        let htmls = comments.map(function (post) {
          return `<li>
            <h2>${post.name}</h2>
            <p>${post.body}</p>
            </li>`;
        });
          let html = htmls.join("");
          const $comment = $(".comment");
          $comment.html(html)
      })
      .catch(function (err) {
        console.log(err);
      });
    
})

//***************************************************************** */
// PROMISE
// Sync / async

// setTimeout, setInterval, fetch,
// XMLHttpRequest, file reading
// request animation frame

//sleep

// Callback hell
// Pyramid

let promise = new Promise(
    //Exucutor
    function (resolve, reject) {
        //Logic
        // Thành công
        // Thất bại
        resolve();
    }
);

promise
    .then(function () {
        return 1;
    })
    .then(function (data) {
        console.log(data);
        return 2;
    })
    .then(function (data) {
        console.log(data);
        return 3;
    })
    .then(function (data) {
        console.log(data);
        return 4;
    })
    .catch(function () {
        console.log("Failure!");
    })
    .finally(function () {
        console.log("Done!");
    })











