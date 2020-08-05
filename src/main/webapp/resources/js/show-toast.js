/**
 * Retorna la plantilal de mensajes
 * @param {*} valor
 * @param {*} parametro
 */
function plantilla(valor, parametro){
    switch(valor){
        case 1:
        return '<div class="done-msg"><p>'+parametro+'</p></div>';
        case 2:
        return '<div class="error-msg"><p>'+parametro+'</p></div>';
        case 3:
        return '<div class="info-msg"><p>'+parametro+'</p></div>';
    }
}

/**
 * Muestra los mensajes de error
 */
var showToast = (function() {
    return {
        success: function(msg) {
            showToast.show_toast(plantilla(1, msg));
        },
        error: function(msg) {
           showToast.show_toast(plantilla(2, msg));
        },
        info: function(msg){
            showToast.show_toast(plantilla(3, msg));
        },
        show_toast: function (html) {
            M.toast({html: html});
        }
    }
})();