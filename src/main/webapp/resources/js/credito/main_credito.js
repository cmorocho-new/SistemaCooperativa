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
    $('#tbl-lista-solicitudes').scrollTable({
        'height': heightMainContent - 20,
    });
}