package ec.edu.ups.bean.virtual;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import ec.edu.ups.common.GeneralException;
import ec.edu.ups.gestor.CreditoON;
import ec.edu.ups.pojo.InfoCredito;
import ec.edu.ups.utils.MessagesUtil;
import ec.edu.ups.utils.SessionUtil;

@Named("listaCreditos")
@RequestScoped
@URLMapping(id="listado-creditos",
		pattern = "/virtual/creditos/",
		viewId = "/templates/banca-virtual/lista-creditos.xhtml"
)
public class ListadoCreditosBean {
	
	@Inject
	private CreditoON creditoON;
	
	private List<InfoCredito> listarCreditos;

	@PostConstruct
	public void inicializar () {
		cargarListaCredito();
	}

	private void cargarListaCredito(){
		long idUsuario = SessionUtil.getIdUsuarioLogeado();
		try {
			setListarCreditos(creditoON.obtenerListaCreditoPorCliente(idUsuario));
		} catch (GeneralException ex) {
			MessagesUtil.agregarMensajeError(ex.getMessage());
		}
	}

	public List<InfoCredito> getListarCreditos() {
		return listarCreditos;
	}

	public void setListarCreditos(List<InfoCredito> listarCreditos) {
		this.listarCreditos = listarCreditos;
	}

	public CreditoON getCreditoON() {
		return creditoON;
	}

	public void setCreditoON(CreditoON creditoON) {
		this.creditoON = creditoON;
	}

}
