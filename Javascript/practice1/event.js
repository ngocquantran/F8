
//  h1Element.onclick = function (e) {
//    console.log(e.target);
//  };

// for (let i = 0; i < h1Element.length; i++){
//     h1Element[i].onclick = function (e) {
//         console.log(e.target);
//     }
// }

// let inputElement = document.querySelector(".input");

// inputElement.onchange = function (e) {
//     console.log(e.target.value);
// }
// // inputElement.oninput = function (e) {
// //   console.log(e.target.value);
// // };

// let checkElement = document.querySelector(".check");

// checkElement.onchange = function (e) {
//     console.log(e.target.checked);
// }

// let selectElement = document.querySelector(".select");
// selectElement.onchange = function (e) {
//     console.log(e.target.value);
// }

// let element = document.querySelector(".input2");

// element.onkeyup = function (e) {
//     console.log(e.which);
// }

// // element.onkeydown = function (e) {
// //   console.log(e);
// // };


// document.onkeydown = function (e) {
//     switch (e.which) {
//         case 27:
//             console.log('EXIT');
//             break;
//     }
// }


// let link = document.querySelectorAll('a');
// console.log(link);

// for (let i = 0; i < link.length; i++){
//     link[i].onclick = function(e){
//         if (!e.target.href.startsWith("https://fullstack.edu.vn")) {
//           e.preventDefault();
//         }
         
//     }
// }

// let btn = document.querySelector('.btn');

// // btn.onclick = function () {
// //     console.log('Việc 1');
// //     console.log('Việc 2');
// //     alert('Việc 3');
// // }

// // setTimeout(function () {
// //     btn.onclick = function () { };
// // }, 3000);

// btn.addEventListener('click', function (e) {
//     console.log('Hahaha');
// })
// btn.addEventListener("click", function (e) {
//   console.log("Hehehe");
// });

// function viec3() {
//     console.log('Hihihihi')
// }
// btn.addEventListener("click", viec3);

// setTimeout(function () {
//     btn.removeEventListener('click', viec3)
// }, 2000);


//1. Định nghĩ key: value cho object








let name = 'Javascript';
let price = 1000;

let course = {
    name,
    price
}

console.log(course);

let arr = ['JS', 'Java', 'CSS'];

let [a, b, c] = arr;
let [h, ...rest] = arr;

console.log(a, b, c);
console.log(h);
console.log(rest);


let array1 = ['JS', 'Java', 'HTML'];
let array2 = ['CSS', 'Python'];
let array3 = [...array2, ...array1];
console.log(array3)


let obj1 = {
    name: 'Javascript',
};
let obj2 = {
    price: '1000000'
};
let obj3 = {
    ...obj1,
    ...obj2,
};
console.log(obj3);

function logger(a,b,c) {
    console.log(a, b, c);
}

function logger2(...rest) {
    for (let i = 0; i < rest.length; i++) { 
        console.log(rest[i]);
    }
}

logger(...array1);
logger(...array2);
logger(...array3);

logger2(...array1);
logger2(...array2);
logger2(...array3);


//11. Tagged template literals

// function hightlight([first,...strings],...values) {
//     console.log('first: ', first);
//     console.log('strings: ', strings);
//     console.log('values: ',values)
// }

function hightlight([first, ...strings], ...values) {
    return values.reduce(
        (acc, curr) => [],
        [first]
    );
}

let brand = 'F8';
let classes = 'Javascript';

hightlight`Học lập trình ${classes} tại ${brand}!`;



