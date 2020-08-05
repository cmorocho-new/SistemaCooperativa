package ec.edu.ups.bean.virtual;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import ec.edu.ups.common.GeneralException;
import ec.edu.ups.gestor.CreditoON;
import ec.edu.ups.gestor.TransaccionesON;
import ec.edu.ups.pojo.InfoCuota;
import ec.edu.ups.pojo.InfoPagoCredito;
import ec.edu.ups.utils.MessagesUtil;
import ec.edu.ups.utils.SessionUtil;

import javax.inject.Inject;
import java.util.List;

@URLMapping(id="pago-cuota-credito",
        pattern = "/virtual/creditos/#{pagoCuota.idCredito}/detalle/#{pagoCuota.idCuota}/pago/",
        viewId = "/templates/banca-virtual/pago-cuota.xhtml"
)
public class PagoCuotaBean {

    @Inject
    private CreditoON creditoON;
    @Inject
    private TransaccionesON transaccionesON;

    private long idCredito;
    private long idCuota;
    private List<InfoPagoCredito> listaPagos;
    private InfoCuota infoCuota;

    @URLAction(onPostback=false)
    public void cargarPagosCuotas(){
        try {
            setInfoCuota(creditoON.obtenerInfoCuota(idCuota));
            setListaPagos(creditoON.obtenerPagosCuota(idCuota));
        }catch (GeneralException ex){
            MessagesUtil.agregarMensajeError(ex.getMessage());
        }
    }

    public String guardarPagoCuota(){
        try {
            long idUsuario = SessionUtil.getIdUsuarioLogeado();
            transaccionesON.transaccionPagoCuota(infoCuota.getIdCuota(), infoCuota.getValorPago(), idUsuario);
            cargarPagosCuotas();
            MessagesUtil.agregarMensajeDone("DONE: Pago realizado exitosamente");
        }catch (GeneralException ex){
            MessagesUtil.agregarMensajeError(ex.getMessage());
        }
        return null;
    }

    public long getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(long idCredito) {
        this.idCredito = idCredito;
    }

    public long getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(long idCuota) {
        this.idCuota = idCuota;
    }

    public List<InfoPagoCredito> getListaPagos() {
        return listaPagos;
    }

    public void setListaPagos(List<InfoPagoCredito> listaPagos) {
        this.listaPagos = listaPagos;
    }

    public InfoCuota getInfoCuota() {
        return infoCuota;
    }

    public void setInfoCuota(InfoCuota infoCuota) {
        this.infoCuota = infoCuota;
    }
}
