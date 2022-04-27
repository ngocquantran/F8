$(function () {
    let headone = document.getElementById("first-id");
    console.log(headone);

    let heads = document.getElementsByClassName("first-class");
    console.log(heads);

    let head2 = document.querySelector(".box .heading-2");
    console.log(head2);

    console.log(document.forms);
    console.log(document.forms[0]);
    console.log(document.forms["form-2"]);
    console.log(document.forms.testForm);

    console.log(document.anchors);

    console.log(document.querySelector('.box-1 ul'))

    let boxNode = document.querySelector('.box-1');
    console.log(boxNode.querySelectorAll('li'));

    console.log(boxNode.querySelector('p'));

    let title = document.querySelector(".title");
    console.log(title);

    //setter : gán thuộc tính cho thẻ

    title.id = "til";
    title.className = "title black-ground";
    title.style = "color: green";


    console.log(title.getAttribute('class'));
    console.log(title.className);
    console.log(title.getAttribute("id"));
    console.log(title.id);

    let textDiv = document.querySelector(".text-example");
    console.log(textDiv.innerText);

    textDiv.innerText = "Tôi sửa lại nhé";
    console.log(textDiv.innerText);
    console.log(textDiv.textContent);

    let boxE = document.querySelector('.outer-box');
    console.log(boxE);

    boxE.innerHTML = "<h1>heading</h1>";

    console.log(document.querySelector(".outer-box h1").innerText);



})

