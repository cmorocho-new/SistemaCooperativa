package ec.edu.ups.bean.virtual;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.model.file.UploadedFile;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.CatalogoDTO;
import ec.edu.ups.dto.credito.SolicitudCreditoDTO;
import ec.edu.ups.gestor.CreditoON;
import ec.edu.ups.gestor.GeneralON;
import ec.edu.ups.pojo.ExtraInfoSolicitudCredito;
import ec.edu.ups.utils.MessagesUtil;
import ec.edu.ups.utils.SessionUtil;

public class SolicitudCreditoBean {

	@Inject
	private GeneralON generalON;

	@Inject
	private CreditoON creditoON;

	private ExtraInfoSolicitudCredito extraInfo;
	private SolicitudCreditoDTO creditoDTO;
	private List<CatalogoDTO> listaPropositoCredito;
	private List<CatalogoDTO> listaTipoEmpleo;
	private List<CatalogoDTO> listaTipoVivienda;

	private UploadedFile file1;
	private UploadedFile file2;
	private UploadedFile file3;

	@PostConstruct
	public void inicializar() {
		creditoDTO = new SolicitudCreditoDTO();
		extraInfo = new ExtraInfoSolicitudCredito(4);
		creditoDTO.setMontoCredito(1000);
		creditoDTO.setPlazoMeses(12);
		cargarCatalogos();
	}

	public void cargarCatalogos() {
		try {
			setListaPropositoCredito(generalON.obtenerCatalogoPropositoCredito());
			setListaTipoVivienda(generalON.obtenerCatalogoTipoVivienda());
			setListaTipoEmpleo(generalON.obtenerCatalogoTipoEmpleo());
		} catch (GeneralException ex) {
			MessagesUtil.agregarMensajeError(ex.getMessage());
		}
	}

	public String guardarSolicitud() throws IOException {
		try {
			long idUsuario = SessionUtil.getIdUsuarioLogeado();
			creditoDTO.setPdfCedula(creditoON.convertirArchivo(file1.getInputStream()));
			creditoDTO.setPdfRol(creditoON.convertirArchivo(file2.getInputStream()));
			creditoDTO.setPdfPlanilla(creditoON.convertirArchivo(file3.getInputStream()));
			creditoON.guardarSolicitud(creditoDTO, extraInfo, idUsuario);

			MessagesUtil.agregarMensajeDone("DONE: La solicitud de credito ha sido procesada exitosamente!");
			inicializar();
		} catch (GeneralException ex) {
			MessagesUtil.agregarMensajeError(ex.getMessage());
		}
		return null;
	}


	public SolicitudCreditoDTO getCreditoDTO() {
		return creditoDTO;
	}

	public void setCreditoDTO(SolicitudCreditoDTO creditoDTO) {
		this.creditoDTO = creditoDTO;
	}

	public List<CatalogoDTO> getListaPropositoCredito() {
		return listaPropositoCredito;
	}

	public void setListaPropositoCredito(List<CatalogoDTO> listaPropositoCredito) {
		this.listaPropositoCredito = listaPropositoCredito;
	}

	public List<CatalogoDTO> getListaTipoEmpleo() {
		return listaTipoEmpleo;
	}

	public void setListaTipoEmpleo(List<CatalogoDTO> listaTipoEmpleo) {
		this.listaTipoEmpleo = listaTipoEmpleo;
	}

	public List<CatalogoDTO> getListaTipoVivienda() {
		return listaTipoVivienda;
	}

	public void setListaTipoVivienda(List<CatalogoDTO> listaTipoVivienda) {
		this.listaTipoVivienda = listaTipoVivienda;
	}

	public ExtraInfoSolicitudCredito getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(ExtraInfoSolicitudCredito extraInfo) {
		this.extraInfo = extraInfo;
	}

	public UploadedFile getFile1() {
		return file1;
	}

	public void setFile1(UploadedFile file1) {
		this.file1 = file1;
	}

	public UploadedFile getFile2() {
		return file2;
	}

	public void setFile2(UploadedFile file2) {
		this.file2 = file2;
	}

	public UploadedFile getFile3() {
		return file3;
	}

	public void setFile3(UploadedFile file3) {
		this.file3 = file3;
	}
	

}
