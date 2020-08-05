package ec.edu.ups.pojo;

import java.util.Date;

public class InfoCredito {

	
	private long idCredito;
	private long numeroCredito;
	private String proposito;
	private double valorCredito;
	private double saldoCredito;
	private Date FechaVencimiento;
	public InfoCredito(long idCredito, long numeroCredito, String proposito, double valorCredito, double saldoCredito,
			Date fechaVencimiento) {
		super();
		this.idCredito = idCredito;
		this.numeroCredito = numeroCredito;
		this.proposito = proposito;
		this.valorCredito = valorCredito;
		this.saldoCredito = saldoCredito;
		FechaVencimiento = fechaVencimiento;
	}
	public long getIdCredito() {
		return idCredito;
	}
	public void setIdCredito(long idCredito) {
		this.idCredito = idCredito;
	}
	public long getNumeroCredito() {
		return numeroCredito;
	}
	public void setNumeroCredito(long numeroCredito) {
		this.numeroCredito = numeroCredito;
	}
	public String getProposito() {
		return proposito;
	}
	public void setProposito(String proposito) {
		this.proposito = proposito;
	}
	public double getValorCredito() {
		return valorCredito;
	}
	public void setValorCredito(double valorCredito) {
		this.valorCredito = valorCredito;
	}
	public double getSaldoCredito() {
		return saldoCredito;
	}
	public void setSaldoCredito(double saldoCredito) {
		this.saldoCredito = saldoCredito;
	}
	public Date getFechaVencimiento() {
		return FechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		FechaVencimiento = fechaVencimiento;
	}
	
	
}
