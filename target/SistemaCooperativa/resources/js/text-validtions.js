$(document).ready(function () {
    $('input.txt').each(function () {
        $(this).attr('onkeypress', 'validacionInputs.validarTexto(event)');
    });
    $('input.num').each(function () {
        $(this).attr('onkeypress', 'validacionInputs.validarNumero(event)');
    });
    $('input.dec').each(function () {
        $(this).attr('onkeypress', 'validacionInputs.validarDecimal(value)');
    });
});

/**
 * Metodo qeu valida si la cedula es correcta
 * @param numero
 * @returns {boolean}
 */
function validarCedula(numero) {
    // Obtiene la suma de los numeros
    let suma = numero.split('').reduce((sum, num, i) => {
        num = parseInt(num);
        if (i == 9) return sum;
        num *= (i + 1) % 2 == 0 ? 1 : 2;
        num = num > 9 ? num - 9 : num;
        // Retorona la suma
        return sum + num
    }, 0);
    // Saca el modulo de 10
    suma = suma % 10;
    let verificador = parseInt(numero.charAt(9));
    // Retorna si es valido
    return suma == 0 ? suma == verificador : 10 - suma == verificador
}

function validarEmail(email) {
    // Obtiene el regex
    let patter = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    // Retorna si es valido
    return patter.test(email)
}

/**
 * Metodos para la validacion de inputs
 * @type {{validarValidar, refrescar, validarTexto, validarDecimal, validarNumero}}
 */
let validacionInputs = (function () {

    return {

        validarVacios: function (prefijo, mensaje= true) {
            let inputs = $('input.requerido'+ '-' + prefijo);
            let selects = $('select.requerido'+ '-' + prefijo);
            let vacios = 0;
            if (inputs.length) {
                inputs.each(function () {
                    $(this).removeClass('invalid');
                    if (!this.value.trim()) {
                        vacios++;
                        $(this).addClass('invalid')
                    }
                });
            }
            if (selects.length) {
                selects.each(function (select) {
                    let input = $(selects[select].parentNode.firstChild);
                    input.removeClass('invalid');
                    if (!$(selects[select]).val()) {
                        vacios++;
                        input.addClass('invalid')
                    }
                });
            }
            let validacion = vacios == 0;
            if(!validacion && mensaje) showToast.error("Asegurese de llenar todos los campos requeridos");
            return validacion;
        },
        refrescarInpust: function (prefijo) {
            $('input.requerido'+ '-' + prefijo).removeClass('invalid')
        },

        mostrarMensajes: function(inputs){
            let mensaje = '';
            inputs.forEach((input) => {
                mensaje += `El campo [ ${input} ] es requerido. <br>`
            });
            // muestra el mensaje
            showToast.error(mensaje)
        },

        /**
         * Metodo que valida que un field tenga solo letras
         * @param event
         */
        validarTexto: function (event) {
            if (event.key >= '0' && event.key <= '9') {
                event.preventDefault();
            }
        },

        /***
         * Metodo que valida que un field tenga solo decimales
         * @param
         */
        validarDecimal: function () {
            let value = event.target.value;
            if (!(event.key >= '0' && event.key <= '9')) {
                if (event.key === '.') {
                    value = (value).match(/\./g);
                    if ((value ? value : []).length > 0) {
                        event.preventDefault();
                    }
                } else {
                    event.preventDefault();
                }
            }
        },

        /**
         * Metodo que valida que un field tenga solo numeros
         */
        validarNumero: function () {
            if (!(event.key >= '0' && event.key <= '9')) {
                event.preventDefault();
            }
        },
        /**
         * Metodo que valida que un field tenga solo numeros
         */
        validarFormato: function () {
            let value = event.target.value;
            if(event.target.value){
                event.target.value = parseFloat(value).toFixed(2);
            }
        }
    }

})();
