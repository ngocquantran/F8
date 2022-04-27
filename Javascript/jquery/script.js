$(function () {
    
    $('.single').click(function () {
        $(this).hide();
    })

    $('.double').dblclick(function () {
        $(this).hide();
    })

    $('.mouseenter').mouseenter(function () {
        // $('.single').hide();
        $('.single').text("Hehehehe");
        
    })

    $('.mouseleave').mouseleave(function () {
        // $('.double').hide();
        $('.double').text("Surprised!");
    })

    $('.hover').hover(
        function () {
            alert('Hello');
        },
        function () {
            alert("Goodbye");
        }
    )

    $('input').focus(function () {
        $(this).css({
            // backgroundImage: `linear-gradient(to right, #333, #336)`,
            "background-color": "yellow",
        });
    })

    $("input").blur(function () {
  
      $(this).css({
        "background-color": "orange",
      });
    });


    const $click = $('.click');

    $click.on({
        mouseenter: function () {
            $click.css({ 'color': 'yellow' });
        },
        mouseleave: function () {
            $click.css({ 'color': 'pink' });
        }, 
        click: function () {
            $click.css({ 'color': 'green' });
        }
    })

    $('.hide').click(function () {
        $('.appearance').hide();
    })

    $(".show").click(function () {
      $(".appearance").show();
    });

    $(".div1").hide();
     $(".div2").hide();
     $(".div3").hide();

    $('.toggle').click(function () {
        $(".appearance").toggle();
    })

    $('.fadeIn').click(function () {
        $('.div1').fadeIn();
        $('.div2').fadeIn('slow');
        $('.div3').fadeIn(4000);
    })

    $('.fadeOut').click(function () {
        $('.div1').fadeOut();
        $('.div2').fadeOut("slow");
        $('.div3').fadeOut(4000);
    })

    $(".fadeToggle").click(function () {
      $(".div1").fadeToggle();
      $(".div2").fadeToggle("slow");
      $(".div3").fadeToggle(4000);
    });

    $(".fadeTo").click(function () {
      $(".div1").fadeTo("slow",0.15);
      $(".div2").fadeTo("slow",0.4);
      $(".div3").fadeTo("slow",0.7);
    });

    $('.flip').click(function () {
        $('.panel-1').slideDown("slow");
    })

    $(".slide-up").click(function () {
      $(".panel-1").slideUp("slow");
    });

    $(".slide-toggle").click(function () {
      $(".panel-3").slideToggle("slow");
    });

    $('.animation').click(function () {
        $('.box').animate({
            left: "300px",
            height: "+=100px",
            width: "+=100px",
        opacity:"0.5"});
    })

    $(".animation").click(function () {
        const $box2 = $('.box2');
        $box2.animate({ height: "+=200px", opacity: "0.5" }, 2000);
        $box2.animate({ width: "+=200px", opacity: "0.8" }, 2000);
        $box2.animate({ height: "-=100px", opacity: "0.25" }, 2000);
        $box2.animate(
          {
            width: "-=100px",
            opacity: "0.5",
            "font-size": "30px",
            "text-align": "center",
          },
          2000
        );
    });

    $('.stop').click(function () {
        $('.box2').stop();
    })

    $('.chaining').click(function () {
        $('.chain').css({ "color": "red" }).slideUp("slow").slideDown("slow");
    })





})