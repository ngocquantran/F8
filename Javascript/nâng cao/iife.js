(function myFunction() {
    console.log("Hi");
})();

let i = 0;
 
(function myFunction() {
    i++;
    console.log(i);

    if (i < 10) {
        myFunction();
    }
})();

const app1 = {
    cars: [],
    add(car) {
        this.cars.push(car);
    },
    edit(index, car) {
        this.cars[index] = car;
    },
    delete(index) {
        this.cars.splice(index, 1);
    }  
}

const app2 = (function () {
    //Private
    const cars = [];
    return {

      add(car) {
        cars.push(car);
      },
      edit(index, car) {
        cars[index] = car;
      },
      delete(index) {
        cars.splice(index, 1);
      },
    };
})