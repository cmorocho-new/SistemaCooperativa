let dimHeadFooter = 0;
let heightMainContent = 0;
let widthMainContent = 0;

$(document).ready(() => {
    // Obtiene la dimension extra
    dimHeadFooter = $('#cm-header').height() + $('#cm-footer').height();
    // Actualiza el content
    actualizar_height_content();

    $('nav .dropdown-trigger').dropdown();
    $('.collapsible').collapsible();
    $('.sidenav').sidenav();
    $('select').formSelect();
    $('.chips').chips();

    M.updateTextFields();

 });

$(window).resize(() => {
    actualizar_height_content();
});


function actualizar_height_content() {
    heightMainContent = $(window).height() - dimHeadFooter;
    widthMainContent = $(window).width();
    // Agrega el tamaño al contenido principal
    $('#cm-content').css('height', heightMainContent + 'px');
}

function mostrarMensajes(){
    let errorMesage = $("#cnt-panel-mensajes\\:errors-mensaje");
    if (errorMesage.val() !== ''){
        showToast.error(errorMesage.val());
        errorMesage.val("");
    }
    let sussesMesage = $("#cnt-panel-mensajes\\:susses-mensaje");
    if (sussesMesage.val() !== ''){
        showToast.success(sussesMesage.val());
        sussesMesage.val("");
    }
}
