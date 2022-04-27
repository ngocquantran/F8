// let a = 1;
// let b = 2;
// let c = 3;
// console.log(a);
// console.log(typeof (a));

// let fullName = "Ngọc \"Quân";
// console.log(fullName);
// console.log(typeof fullName);

// //Boolean
// let isSuccess = true;

// //Function
// let myFunction = function () {
//     console.log("Xin chào bạn");
// }

// myFunction();
// console.log(typeof myFunction);

// //Object types

// let myObject = {
//     name: "Quân",
//     age: 28,
//     address: "Hà Nam",
//     myFunction: function () {
//         console.log("buồn cười nhỉ");
//     }
// };

// console.log("myObject", myObject);
// console.log(myObject.myFunction);

// //Arrays

// let myArray = [
//     "JS",
//     "HTML"
// ];

// console.log(myArray);

// let a = 1;
// let b = "1";

// console.log(a !== b);

// console.log(Boolean("Quân"));
// console.log(!"Quân");
// console.log(!!"Quân");

// let a = 1;
// let b = 2;
// let result = 0 && "B" && NaN;
// console.log(result);

// function showDialog() {
//     console.log("Chào mọi người");
// }

// showDialog();
// function writeLog() {
//     for (let param of arguments) {
//         console.log(param);
//     }
// }
// writeLog("log 1", "log 2");

// function writeLog2() {
//     let myString = "";
//   for (let param of arguments) {
//       myString += param+" - ";
//     }
//     console.log(myString);
// }
// writeLog2("log 1", "log 2", "log 3");

//********************************************************* */
//Return trong hàm

// let isConfirm = confirm("Message");
// console.log(isConfirm);

// function plus(a, b) {
//     return a.toString() + b.toString();
// }
// let result = plus(2, 8);
// console.log(result);

// function showMessage() {
    
// }

// let showMessage2 = function () {
    
// }

//********************************************************* */
//CÁC CÁCH TẠO CHUỖI
// let myName = "Ngọc Quân";
// let lala = new String("hay nhỉ");

// let firstName = "Quân";
// let lastName = "Trần";
// console.log(`Tôi là: ${firstName} ${lastName}`);


// let myString = "Học JS tại  JS JS F8";

// // 1. LENGTH
// console.log(myString.length);

// // 2. FIND INDEX
// console.log(myString.indexOf("JS"));
// console.log(myString.indexOf("JS", 7));
// console.log(myString.lastIndexOf("JS"));
// console.log(myString.indexOf("abc"));

// console.log(myString.search("JS"));

// // 3. CUT STRING

// console.log(myString.slice(4, 6))
// console.log(myString.slice(4));


// // 4. REPLACE

// console.log(myString.replace("JS", "Javascript"));
// console.log(myString.replace(/JS/g, "Javascript"));

// // 5. CONVERT TO UPPER CASE

// // 8. SPLIT

// let language = "Javascrip, PHP, Ruby";
// console.log(language.split(", "));
// console.log(language.split(""));

//********************************************************* */
// let million = 1000000;
// let billion = 1e9;

// let age = 18;
// let result = 20 / "ABC";
// console.log(result);
// console.log(isNaN(result));

// let PI = 3.1256;
// console.log(PI.toFixed());
// console.log(PI.toFixed(1));
// console.log(PI.toFixed(2));

let language = ["JS", "PHP", "Java", "SQL"]; 
console.log(language.join(" - "));

let print = language.join(" - ");
console.log(print);

console.log(language.pop());
console.log(language);

language.push("Python");
console.log(language);

console.log(language.shift());
console.log(language);

console.log(language.unshift("HTML", "CSS"));
console.log(language);

language.splice(1, 1);
console.log(language);

language.splice(2, 0, "SQL");
console.log(language);

language.splice(4, 1, "C++");
console.log(language);

console.log(language.slice(2, 4));

let copy = language.slice(0);
console.log(copy);



