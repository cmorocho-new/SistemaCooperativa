/**
 * Archivo main para pantalla retiros
 */

$(document).ready(() => {
    // Actualiza el height de la tabla y el panel
    actualizar_height_tabla();
});


$(window).resize(() => {
    // Actualiza el height de la tabla y el panel
    actualizar_height_tabla();
});

function actualizar_height_tabla(size= 32) {
    // Obtiene la dimension de hear y footer
    $('#tbl-lista-detalle').scrollTable({
        'height': heightMainContent - $('#cnt-panel-opciones').height() - size,
    });
}

function capturarErrorDetalle(data){
    if(data.status == "success") {
        mostrarMensajes();
        actualizar_height_tabla(40);
        $('select').formSelect();
    }else if(data.status == "begin") {
        $('#progress-tabla').addClass("indeterminate");
    }else if(data.status == "complete") {
        $('#progress-tabla').removeClass("indeterminate");
    }else if(data.status == "serverError") {
        showToast.error("ERROR: Ha ocurrido un error en el servidor.")
    }
}

function capturarErrorCuenta(data){
    if(data.status == "success") {
        mostrarMensajes();
        M.updateTextFields();
        $('select').formSelect();
    }else if(data.status == "begin") {
        $('#progress-cuenta').addClass("indeterminate");
    }else if(data.status == "complete") {
        $('#progress-cuenta').removeClass("indeterminate");
    }else if(data.status == "serverError") {
        showToast.error("ERROR: Ha ocurrido un error en el servidor.")
    }
}


function capturarErrorTransaccion(data){
    if (data.status == "success") {
        mostrarMensajes();
        M.updateTextFields();
        $('select').formSelect();
    }else if(data.status == "begin") {
        $('#progress-cuenta').addClass("indeterminate");
    }else if(data.status == "complete") {
        $('#progress-cuenta').removeClass("indeterminate");
    }else if(data.status == "serverError") {
        showToast.error("ERROR: Ha ocurrido un error en el servidor.")
    }
}
