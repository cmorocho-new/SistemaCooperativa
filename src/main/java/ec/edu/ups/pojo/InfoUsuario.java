package ec.edu.ups.pojo;

public class InfoUsuario {

    private long usarioId;
    private String correo;
    private String contrasenia;
    private String nombre;
    private String rolId;
    private String rolNombre;
    private boolean esCliente;

    public InfoUsuario(long usarioId, String nombre, String contrasenia, String rolId, String rolNombre) {
        this.usarioId = usarioId;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.rolId = rolId;
        this.rolNombre = rolNombre;
    }

    public InfoUsuario(long usarioId, String nombre, String contrasenia) {
        this.usarioId = usarioId;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
    }

    public long getUsarioId() {
        return usarioId;
    }

    public void setUsarioId(long usarioId) {
        this.usarioId = usarioId;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRolId() {
        return rolId;
    }

    public void setRolId(String rolId) {
        this.rolId = rolId;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public boolean isEsCliente() {
        return esCliente;
    }

    public void setEsCliente(boolean esCliente) {
        this.esCliente = esCliente;
    }
}
