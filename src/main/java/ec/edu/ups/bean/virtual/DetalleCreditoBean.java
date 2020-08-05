package ec.edu.ups.bean.virtual;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.credito.CuotaCreditoDTO;
import ec.edu.ups.gestor.CreditoON;
import ec.edu.ups.utils.MessagesUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("detalleCredito")
@RequestScoped
@URLMapping(id="detalle-credito", pattern = "/virtual/creditos/#{detalleCredito.idCredito}/detalle/",
        viewId = "/templates/banca-virtual/detalle-credito.xhtml")
public class DetalleCreditoBean {

    @Inject
    private CreditoON creditoON;

    private long idCredito;
    private List<CuotaCreditoDTO> listaCuotas;

    @URLAction(onPostback=false)
    public void cargarDetalleCredito(){
        try {
            setListaCuotas(creditoON.obtenerCuotasCredito(idCredito));
        }catch (GeneralException ex){
            MessagesUtil.agregarMensajeError(ex.getMessage());
        }
    }

    public long getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(long idCredito) {
        this.idCredito = idCredito;
    }

    public List<CuotaCreditoDTO> getListaCuotas() {
        return listaCuotas;
    }

    public void setListaCuotas(List<CuotaCreditoDTO> listaCuotas) {
        this.listaCuotas = listaCuotas;
    }
}
