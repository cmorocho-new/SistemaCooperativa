package ec.edu.ups.bean.credito;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.CatalogoDTO;
import ec.edu.ups.gestor.CreditoON;
import ec.edu.ups.gestor.GeneralON;
import ec.edu.ups.pojo.InfoSolisitudCredito;
import ec.edu.ups.utils.MessagesUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("listaSolicitudCredito")
@RequestScoped
@URLMapping(id="lista-solisitudes-creditos",
        pattern = "/administrativo/creditos/",
        viewId = "/templates/credito/lista-solicutud-credito.xhtml"
)
public class ListaSolisitudCreditoBean {

    @Inject
    private CreditoON creditoON;
    @Inject
    private GeneralON generalON;

    private List<InfoSolisitudCredito> listaSolicitudCredito;
    private List<CatalogoDTO> listaEstadoSolicitud;
    private CatalogoDTO estadoSolicitud;

    /**
     * Método de inicialización de un bean
     */
    @PostConstruct
    public void inicializar() {
        caragrDataInicial();
    }

    private void caragrDataInicial(){
        try{
            setListaEstadoSolicitud(generalON.obtenerCatalogoEstadoSolicitud());
            // Obtiene el id del estado pendiente
            long idEstadoSolicitud = generalON.obtenerIdCatalogoPorCodigo("TIP-ESL-PEN");
            setListaSolicitudCredito(creditoON.obtenerSolicitudesCredito(idEstadoSolicitud));
        }catch (GeneralException ex){
            MessagesUtil.agregarMensajeError(ex.getMessage());
        }
    }

    public List<InfoSolisitudCredito> getListaSolicitudCredito() {
        return listaSolicitudCredito;
    }

    public void setListaSolicitudCredito(List<InfoSolisitudCredito> listaSolicitudCredito) {
        this.listaSolicitudCredito = listaSolicitudCredito;
    }

    public List<CatalogoDTO> getListaEstadoSolicitud() {
        return listaEstadoSolicitud;
    }

    public void setListaEstadoSolicitud(List<CatalogoDTO> listaEstadoSolicitud) {
        this.listaEstadoSolicitud = listaEstadoSolicitud;
    }

    public CatalogoDTO getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(CatalogoDTO estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }
}
