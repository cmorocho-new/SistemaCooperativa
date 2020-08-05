package ec.edu.ups.common;

public class GeneralException extends Exception {

    public int CODIGO_ERROR = 100;

    public GeneralException(int codigo, String mensaje) {
        super("ERROR [ " + codigo + " ]: " + mensaje);
        CODIGO_ERROR = codigo;
    }

    public GeneralException (String mensaje) {
        super(mensaje);
    }
}
