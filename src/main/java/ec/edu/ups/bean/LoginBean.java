package ec.edu.ups.bean;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.gestor.AdministracionON;
import ec.edu.ups.pojo.InfoUsuario;
import ec.edu.ups.utils.MessagesUtil;
import ec.edu.ups.utils.SessionUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class LoginBean {

    @Inject
    HttpServletRequest request;

    @Inject
    private AdministracionON administracionON;

    private String correo;
    private String password;

    public String loginAdmin() {
        try {
            InfoUsuario usuario = administracionON.validarUsuarioAdministrativo(correo, password);
            SessionUtil.setInfoUsuarioLogeado(usuario);
            return "pretty:inicio-administrativo";
        }catch (GeneralException e) {
            password = "";
            MessagesUtil.agregarMensajeError("El correo o contraseña es incorrecto");
        }
        return null;
    }

    public String loginVirtual() {
        try {
            String dispositivo = request.getRemoteHost();
            String ipAddress = request.getRemoteAddr();
            InfoUsuario usuario = administracionON.validarUsuarioCliente(correo, password, dispositivo, ipAddress);
            SessionUtil.setInfoUsuarioLogeado(usuario);
            return "pretty:inicio-banca-virtual";
        }catch (GeneralException ex){
            password = "";
            MessagesUtil.agregarMensajeError("El correo o contraseña es incorrecto");
        }
        return null;
    }

    public String logout() {
        SessionUtil.getSession().invalidate();
        return "pretty:index";
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}