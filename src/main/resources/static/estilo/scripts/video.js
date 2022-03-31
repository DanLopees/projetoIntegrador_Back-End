
var myVideo = document.getElementById("Play_Video");
function makeBig() {
    myVideo.width = 900;
}
function makeSmall() {
    myVideo.width = 300;
}

function makeNormal() {
    myVideo.width = 600;
}

function telaCheia() {

    myVideo.requestFullscreen();
}

function play_pause() {
    if (myVideo.paused)
        myVideo.play();
    else
        myVideo.pause();
}
function aumentar_volume() {
    if (myVideo.volume < 1) myVideo.volume += 0.1;
}

function diminuir_volume() {
    if (myVideo.volume > 0) myVideo.volume -= 0.1;
}

function mute() {
    if (myVideo.muted) {
        myVideo.muted = false;
    } else {
        myVideo.muted = true;
    }
}

$('.carousel').slick({
  dots: true,
  infinite: true,
  slidesToShow: 4,
  slidesToScroll: 4
});