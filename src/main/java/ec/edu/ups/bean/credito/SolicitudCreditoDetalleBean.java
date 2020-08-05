package ec.edu.ups.bean.credito;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.credito.SolicitudCreditoDTO;
import ec.edu.ups.gestor.CreditoON;
import ec.edu.ups.pojo.InfoSolisitudCredito;
import ec.edu.ups.utils.MessagesUtil;
import ec.edu.ups.utils.SessionUtil;
import javax.inject.Inject;


@URLMapping(id="solisitud-credito-detalle",
        pattern = "/administrativo/creditos/#{solicitudCreditoDetalle.idSolicitud}/",
        viewId = "/templates/credito/detalle-solicutud-credito.xhtml"
)
public class SolicitudCreditoDetalleBean {

    @Inject
    private CreditoON creditoON;

    private long idSolicitud;
    private SolicitudCreditoDTO infoSolisitudCredito;

    @URLAction(onPostback=false)
    public void cargarSolicitud(){
        try {
            setInfoSolisitudCredito(creditoON.obtenerInfoSolicitudCredito(idSolicitud));
        }catch (GeneralException ex){
            MessagesUtil.agregarMensajeError(ex.getMessage());
        }
    }

    /**
     * Aprueba o rezhaza una solicitud
     * @param aprobado
     * @return
     */
    public String aprobarRechazarSolicitud(boolean aprobado){
        try {
            InfoSolisitudCredito infoSolisitud = new InfoSolisitudCredito(infoSolisitudCredito.getId());
            infoSolisitud.setIdentificacion(infoSolisitudCredito.getCliente().getIdentificacion());
            infoSolisitud.setSolicitante(infoSolisitudCredito.getCliente().getNombreCompleto());
            infoSolisitud.setCorreo(infoSolisitudCredito.getCliente().getCorreo());
            infoSolisitud.setMontoCredito(infoSolisitudCredito.getMontoCredito());
            infoSolisitud.setPlazoCredito(infoSolisitudCredito.getPlazoMeses());
            infoSolisitud.setObservacion(infoSolisitudCredito.getObservacion());
            infoSolisitud.setInteres(infoSolisitudCredito.getInterez());
            if (aprobado){
                long idUsuario = SessionUtil.getIdUsuarioLogeado();
                creditoON.aprobarSolicitudCredito(infoSolisitud, idUsuario);
                MessagesUtil.agregarMensajeDoneSiguiente("DONE: Solicitud de credito aprobada satisfactoriamente");
            }else{
                creditoON.rechazarSolicitudCredito(infoSolisitud);
                MessagesUtil.agregarMensajeDoneSiguiente("DONE: Solicitud de credito rechzada satisfactoriamente");
            }
        }catch (GeneralException ex){
            MessagesUtil.agregarMensajeError(ex.getMessage());
            return null;
        }
        return "pretty:lista-solisitudes-creditos";
    }

    public boolean mostrarBotonesPrincipales(){
        return infoSolisitudCredito.getEstado().getCodigo().equals("TIP-ESL-PEN");
    }

    public SolicitudCreditoDTO getInfoSolisitudCredito() {
        return infoSolisitudCredito;
    }

    public void setInfoSolisitudCredito(SolicitudCreditoDTO infoSolisitudCredito) {
        this.infoSolisitudCredito = infoSolisitudCredito;
    }

    public long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
}
