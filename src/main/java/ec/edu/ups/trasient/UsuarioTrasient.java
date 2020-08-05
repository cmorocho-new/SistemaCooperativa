package ec.edu.ups.trasient;

import java.io.Serializable;

public class UsuarioTrasient implements Serializable {

    private String correo;
    private String contrasenia;

    public UsuarioTrasient(){

    }

    public UsuarioTrasient(String correo, String contrasenia) {
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
