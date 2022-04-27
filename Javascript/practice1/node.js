
let boxElement = document.querySelector('.box');
console.log(boxElement);
console.log(boxElement.style);



Object.assign(boxElement.style, {
    width : "300px",
    height : "600px",
    backgroundColor: "green",
})

console.log(boxElement.style.backgroundColor);
console.log(boxElement.style.height);

console.log(boxElement.classList.length);
console.log(boxElement.classList[0]);
console.log(boxElement.classList[1]);
console.log(boxElement.classList.value);

let head1 = boxElement.firstElementChild;
console.log(head1);

// head1.classList.add("red");

console.log(boxElement.classList.contains("box-2"));
console.log(boxElement.classList.contains("box-template"));


setInterval(() => {
    head1.classList.toggle("red");
}, 1000);









    










