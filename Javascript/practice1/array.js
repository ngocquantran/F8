const arr = [
  {
    name: "Quan",
    age: 22,
  },
  {
    name: "Nam",
    age: 23,
  },
  {
    name: "Nga",
    age: 24,
  },
  {
    name: "Van",
    age: 25,
  },
];

// arr.forEach(function (arr,index) {
//     console.log(index,arr);
// })

// let check = arr.every(function (arr) {
//     return arr.age > 18;
// })
// console.log(check);

// let check2 = arr.every(function (arr) {
//     return arr.age > 23;
// })
// console.log(check2);

// let search = arr.find(function (arr) {
//     return arr.name === "Quan";
// })
// console.log(search);

// let newarr = arr.map(function (arr) {
//     return {
//         name: arr.name+ " chào bạn",
//         age: arr.age+10,
//     }
// })

// console.log(newarr);

// let totalAge = arr.reduce(function (accumulator, currentValue, currentIndex, originalArray) {
//     return accumulator + currentValue.age;
// }, 0)

// console.log(totalAge);

// let sumAge = arr.reduce(function (total, person) {
//   return total + person.age;
// }, 0);

// console.log(sumAge)


let deepArray = [1, 2, [3, 4], 5, 6, [7, 8]];
let flatArray = deepArray.reduce(function (flatOutput, deepItem) {
  return flatOutput.concat(deepItem);
}, [])
console.log(flatArray);

let numbers = [1, 2, 3, 4, 5];
let sumNum = numbers.reduce(function (total, num) {
  return total + num;
}, 10);

console.log(sumNum);


let course = [
  "Javascript",
  "PHP",
  "Ruby"
];

course.forEach(function (course,index,array) {
  console.log(course, array);
});

console.log(course.filter(function (course, index, array) {
  return course === 'PHP'
}));

console.log(course.some(function (course) {
  return course === "script";
}));





