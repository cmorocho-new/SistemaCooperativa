package ec.edu.ups.utils;

import ec.edu.ups.pojo.InfoUsuario;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static String INFO_USER = "infoUsuario";

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) FacesContext.getCurrentInstance()
                .getExternalContext().getResponse();
    }

    public static void setInfoUsuarioLogeado(InfoUsuario infoUusario){
        getSession().setAttribute(INFO_USER, infoUusario);
    }

    public static InfoUsuario getInfoUsuarioLogeado(){
        return (InfoUsuario) getSession().getAttribute(INFO_USER);
    }

    public static long getIdUsuarioLogeado() {
       return ((InfoUsuario) getSession().getAttribute(INFO_USER)).getUsarioId();
    }

}
