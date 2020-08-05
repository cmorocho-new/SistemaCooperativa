package ec.edu.ups.bean.virtual;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.gestor.GeneralON;
import ec.edu.ups.pojo.InfoCuenta;
import ec.edu.ups.utils.SessionUtil;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class InicioVitualBean {

    @Inject
    private GeneralON generalON;
    private InfoCuenta infoCuenta;

    @PostConstruct
    private void inicializar() {
        cargarInfoCuenta();
    }

    public void cargarInfoCuenta() {
        try {
            long idUsuario = SessionUtil.getIdUsuarioLogeado();
            infoCuenta = generalON.obtenerInfoCuenta(idUsuario);
        }catch (GeneralException ex){

        }
    }

    public InfoCuenta getInfoCuenta() {
        return infoCuenta;
    }

    public void setInfoCuenta(InfoCuenta infoCuenta) {
        this.infoCuenta = infoCuenta;
    }
}
