package ec.edu.ups.bean;

import ec.edu.ups.pojo.InfoUsuario;
import ec.edu.ups.utils.SessionUtil;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class PrincipalBean {

    private InfoUsuario usuarioLogeado;

    @PostConstruct
    public void inicializar() {
        usuarioLogeado = SessionUtil.getInfoUsuarioLogeado();
    }

    public InfoUsuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(InfoUsuario usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }
}
