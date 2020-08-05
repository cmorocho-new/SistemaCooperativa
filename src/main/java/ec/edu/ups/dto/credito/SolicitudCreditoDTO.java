package ec.edu.ups.dto.credito;

import ec.edu.ups.dto.AuditoriaDTO;
import ec.edu.ups.dto.CatalogoDTO;
import ec.edu.ups.dto.ClienteDTO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "crt_solicitudes_creditos")
@SuppressWarnings("serial")
public class SolicitudCreditoDTO extends AuditoriaDTO implements Serializable {

	@Column(name = "descripcion_motivo", length = 500)
	private String descripcionMotivo;

	@Column(name = "plazo_meses", nullable = false, precision = 10)
	private int plazoMeses;

	@Column(name = "monto_credito", nullable = false, precision = 10, scale = 2)
	private double montoCredito;

	@Column(name = "avaluo_vivienda", nullable = false, precision = 10, scale = 2)
	private double avaluoVivienda;

	@Column(name = "tasa_pago", nullable = false, precision = 10, scale = 2)
	private double tasaPago;

	@Column(name = "interez", nullable = false, precision = 10, scale = 2)
	private double interez;

	@Column(name = "tiempo_empleo", nullable = false, precision = 4, scale = 2)
	private double tiempoEmpleo;

	@Column(name = "total_ingresos", nullable = false, precision = 10, scale = 2)
	private double totalIngresos;

	@Column(name = "total_egresos", nullable = false, precision = 10, scale = 2)
	private double totalEgresos;

	@Column(name = "saldo_actual", nullable = false, precision = 10, scale = 2)
	private double saldoActual;

	@Column(name = "descripcion_activo", length = 500)
	private String descripcionActivo;

	@Column(name = "edad", precision = 4)
	private int edad;

	@Column(name = "cantidad_creditos", precision = 4)
	private int cantidadCreditos;

	@Column(name = "trabajador_extrangero", nullable = false)
	private boolean trabajadorExtrangero;

	@Column(name = "observacion", length = 500)
	private String observacion;
	
	@Lob
	@Column(name = "pdf_cedula", length = 16777215)
	private byte[] pdfCedula;  

	@Lob
	@Column(name = "pdf_rol", length = 16777215)
	private byte[] pdfRol; 
	
	@Lob
	@Column(name = "pdf_planilla", length = 16777215)
	private byte[] pdfPlanilla;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
	private ClienteDTO cliente;

	@ManyToOne
	@JoinColumn(name = "garante_principal_id", referencedColumnName = "id", nullable = false)
	private ClienteDTO garantePrincipal;

	@ManyToOne
	@JoinColumn(name = "garante_secundario_id", referencedColumnName = "id")
	private ClienteDTO garanteSecundario;

	@ManyToOne
	@JoinColumn(name = "proposito_credito_id", referencedColumnName = "id", nullable = false)
	private CatalogoDTO propositoCredito;
	
	@ManyToOne
	@JoinColumn(name = "tipo_vivienda_id", referencedColumnName = "id", nullable = false)
	private CatalogoDTO tipoVienda;

	@ManyToOne
	@JoinColumn(name = "tipo_empleo_id", referencedColumnName = "id", nullable = false)
	private CatalogoDTO tipoEmpleo;

	@ManyToOne
	@JoinColumn(name = "estado_id", referencedColumnName = "id", nullable = false)
	private CatalogoDTO estado;

	@ManyToOne
	@JoinColumn(name = "tipo_garante_id", referencedColumnName = "id")
	private CatalogoDTO tipoGarante;

	@ManyToOne
	@JoinColumn(name = "historial_credito_id", referencedColumnName = "id")
	private CatalogoDTO historialCredito;

	@ManyToOne
	@JoinColumn(name = "tipo_cliente_id", referencedColumnName = "id")
	private CatalogoDTO tipoCliente;

	public SolicitudCreditoDTO(){

	}

	public SolicitudCreditoDTO(long id){
		super.setId(id);
	}

	public String getDescripcionMotivo() {
		return descripcionMotivo;
	}

	public void setDescripcionMotivo(String descripcionMotivo) {
		this.descripcionMotivo = descripcionMotivo;
	}

	public int getPlazoMeses() {
		return plazoMeses;
	}

	public void setPlazoMeses(int plazoMeses) {
		this.plazoMeses = plazoMeses;
	}

	public double getMontoCredito() {
		return montoCredito;
	}

	public void setMontoCredito(double montoCredito) {
		this.montoCredito = montoCredito;
	}

	public double getAvaluoVivienda() {
		return avaluoVivienda;
	}

	public void setAvaluoVivienda(double avaluoVivienda) {
		this.avaluoVivienda = avaluoVivienda;
	}

	public double getTasaPago() {
		return tasaPago;
	}

	public double getInterez() {
		return interez;
	}

	public void setInterez(double interez) {
		this.interez = interez;
	}

	public void setTasaPago(double tasaPago) {
		this.tasaPago = tasaPago;
	}

	public double getTiempoEmpleo() {
		return tiempoEmpleo;
	}

	public void setTiempoEmpleo(double tiempoEmpleo) {
		this.tiempoEmpleo = tiempoEmpleo;
	}

	public double getTotalIngresos() {
		return totalIngresos;
	}

	public void setTotalIngresos(double totalIngresos) {
		this.totalIngresos = totalIngresos;
	}

	public double getTotalEgresos() {
		return totalEgresos;
	}

	public void setTotalEgresos(double totalEgresos) {
		this.totalEgresos = totalEgresos;
	}

	public double getSaldoActual() {
		return saldoActual;
	}

	public void setSaldoActual(double saldoActual) {
		this.saldoActual = saldoActual;
	}

	public String getDescripcionActivo() {
		return descripcionActivo;
	}

	public void setDescripcionActivo(String descripcionActivo) {
		this.descripcionActivo = descripcionActivo;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getCantidadCreditos() {
		return cantidadCreditos;
	}

	public void setCantidadCreditos(int cantidadCreditos) {
		this.cantidadCreditos = cantidadCreditos;
	}

	public boolean isTrabajadorExtrangero() {
		return trabajadorExtrangero;
	}

	public void setTrabajadorExtrangero(boolean trabajadorExtrangero) {
		this.trabajadorExtrangero = trabajadorExtrangero;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public byte[] getPdfCedula() {
		return pdfCedula;
	}

	public void setPdfCedula(byte[] pdfCedula) {
		this.pdfCedula = pdfCedula;
	}

	public byte[] getPdfRol() {
		return pdfRol;
	}

	public void setPdfRol(byte[] pdfRol) {
		this.pdfRol = pdfRol;
	}

	public byte[] getPdfPlanilla() {
		return pdfPlanilla;
	}

	public void setPdfPlanilla(byte[] pdfPlanilla) {
		this.pdfPlanilla = pdfPlanilla;
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public ClienteDTO getGarantePrincipal() {
		return garantePrincipal;
	}

	public void setGarantePrincipal(ClienteDTO garantePrincipal) {
		this.garantePrincipal = garantePrincipal;
	}

	public ClienteDTO getGaranteSecundario() {
		return garanteSecundario;
	}

	public void setGaranteSecundario(ClienteDTO garanteSecundario) {
		this.garanteSecundario = garanteSecundario;
	}

	public CatalogoDTO getPropositoCredito() {
		return propositoCredito;
	}

	public void setPropositoCredito(CatalogoDTO propositoCredito) {
		this.propositoCredito = propositoCredito;
	}

	public CatalogoDTO getTipoVienda() {
		return tipoVienda;
	}

	public void setTipoVienda(CatalogoDTO tipoVienda) {
		this.tipoVienda = tipoVienda;
	}

	public CatalogoDTO getTipoEmpleo() {
		return tipoEmpleo;
	}

	public void setTipoEmpleo(CatalogoDTO tipoEmpleo) {
		this.tipoEmpleo = tipoEmpleo;
	}

	public CatalogoDTO getEstado() {
		return estado;
	}

	public void setEstado(CatalogoDTO estado) {
		this.estado = estado;
	}

	public CatalogoDTO getTipoGarante() {
		return tipoGarante;
	}

	public void setTipoGarante(CatalogoDTO tipoGarante) {
		this.tipoGarante = tipoGarante;
	}

	public CatalogoDTO getHistorialCredito() {
		return historialCredito;
	}

	public void setHistorialCredito(CatalogoDTO historialCredito) {
		this.historialCredito = historialCredito;
	}

	public CatalogoDTO getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(CatalogoDTO tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
}
