
$(function(){
    const $tabs=$('.tab-item');
    const $panes=$('.tab-pane')
    
    const tabActive = document.querySelector('.tab-item.active');
    console.log(tabActive);

    const line = document.querySelector(".line");
    line.style.left = tabActive.offsetLeft + 'px';
    line.style.width = tabActive.offsetWidth + 'px';

    // console.log($tabs);
    // console.log($panes);
    $tabs.each(function (index, tab) {
        const pane = $panes[index];
        $(tab).on('click', function () { 
            // console.log(pane);
            $(".tab-item.active").removeClass('active');
            $(".tab-pane.active").removeClass('active');

            line.style.left = this.offsetLeft + "px";
            line.style.width = this.offsetWidth + "px";


            $(this).addClass('active');
            $(pane).addClass('active');
        })
    })

    

})
    

