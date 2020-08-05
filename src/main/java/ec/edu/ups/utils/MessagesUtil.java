package ec.edu.ups.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessagesUtil {

    public static void agregarMensajeDone(String mensaje){
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje,  null));
    }

    public static void agregarMensajeDoneSiguiente(String mensaje){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje,  null));
        context.getExternalContext().getFlash().setKeepMessages(true);
    }

    public static void agregarMensajeError(String mensaje){
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje,  null));
    }

    public static void agregarMensajeError(String mensaje, String detalle){
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje,  detalle));
    }

    public static void agregarMensajeError(String idComponente, String mensaje, String detalle){
        FacesContext.getCurrentInstance()
                .addMessage(idComponente, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje,  detalle));
    }
}
