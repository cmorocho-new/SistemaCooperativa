package ec.edu.ups.gestor;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dao.CatalogoDAO;
import ec.edu.ups.dao.CuentaDAO;
import ec.edu.ups.dao.credito.CreditoDAO;
import ec.edu.ups.dao.credito.CuotaCreditoDAO;
import ec.edu.ups.dao.credito.PagoCuotaDAO;
import ec.edu.ups.dao.transaccion.TransaccionDAO;
import ec.edu.ups.dto.CuentaDTO;
import ec.edu.ups.dto.credito.CuotaCreditoDTO;
import ec.edu.ups.dto.credito.PagoCuotaDTO;
import ec.edu.ups.dto.transaccion.ComprobanteDTO;
import ec.edu.ups.dto.transaccion.TransaccionDTO;
import ec.edu.ups.pojo.DetalleTransaccion;
import ec.edu.ups.pojo.InfoCuenta;
import ec.edu.ups.pojo.InfoCuota;
import ec.edu.ups.pojo.InfoTransacion;
import ec.edu.ups.utils.MathUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Stateless
public class TransaccionesON {

	@Inject
	private TransaccionDAO transaccionDAO;

	@Inject
	private CuentaDAO cuentaDAO;

	@Inject
	private CreditoDAO creditoDAO;

	@Inject
	private CatalogoDAO catalogoDAO;

	@Inject
	private CuotaCreditoDAO cuotaCreditoDAO;

	@Inject
	private PagoCuotaDAO pagoCuotaDAO;

	/**
	 * Obtierne la informacion de una transacion
	 *
	 * @param codigoTrans
	 * @return
	 * @throws GeneralException
	 */
	public InfoTransacion obtenerInfoTransaccion(String codigoTrans) throws GeneralException {
		InfoTransacion infoTrans = transaccionDAO.obtenerInfoTransaccion(codigoTrans);
		infoTrans.setFechaTransacion(new Date());
		infoTrans.setCodigoTransacion(codigoTrans);
		return infoTrans;
	}

	/**
	 * Genera un transaccion de transferencia
	 *
	 * @param numeroCuentaOrigen
	 * @param numeroCuentaDestino
	 * @param cantidad
	 * @throws GeneralException
	 */
	public void transaccionAutomaticaTransaferencia(String numeroCuentaOrigen, String numeroCuentaDestino, double cantidad)
			throws GeneralException {
		InfoCuenta cuentaOrigen;
		InfoCuenta cuentaDestino;
		// Valida que las cuentas existan
		try {
			cuentaOrigen = cuentaDAO.obtenerInfoBasicaCuenta(numeroCuentaOrigen);
		} catch (GeneralException ex) {
			throw new GeneralException("La cuenta origen no existe");
		}
		try {
			cuentaDestino = cuentaDAO.obtenerInfoBasicaCuenta(numeroCuentaDestino);
		} catch (GeneralException ex) {
			throw new GeneralException("La cuenta destino no existe");
		}
		// Crea el comprobante de transaccion origen
		ComprobanteDTO comprobanteO = new ComprobanteDTO();
		comprobanteO.setHaber(cantidad);
		guardarComprobanteTransaferencia(comprobanteO, cuentaOrigen.getIdCuenta(), "TRASF", false);
		// Crea el comprobante de transaccion destino
		ComprobanteDTO comprobanteD = new ComprobanteDTO();
		comprobanteD.setDebe(cantidad);
		guardarComprobanteTransaferencia(comprobanteD, cuentaDestino.getIdCuenta(), "TRASF", true);
	}

	/**
	 * Crea una tranacion de tipo retiro
	 * @param numeroCuenta
	 * @param cantidad
	 * @throws GeneralException
	 */
    public void transaccionAutomaticaRetiro(String numeroCuenta, double cantidad) throws GeneralException {
    	InfoCuenta cuenta;
        try {
            cuenta = cuentaDAO.obtenerInfoBasicaCuenta(numeroCuenta);
        }catch (GeneralException ex){
            throw new GeneralException("La cuenta no existe");
        }
        // Crea el comprobante retiro
        ComprobanteDTO comprobante = new ComprobanteDTO();
        comprobante.setHaber(cantidad);
        comprobante.setSaldo(cuenta.getSaldoActual() - cantidad);
		// Guarda el comprobante retiro
        guardarComprobanteRetiro(comprobante, cuenta.getIdCuenta(), "RETI");
    }

	/**
	 * Genera un transaccion de deposito
	 *
	 * @param numeroCuenta
	 * @param cantidad
	 * @throws GeneralException
	 */
	public void transaccionAutomaticaDeposito(String numeroCuenta, double cantidad) throws GeneralException {
		InfoCuenta cuenta;
		try {
			cuenta = cuentaDAO.obtenerInfoBasicaCuenta(numeroCuenta);
		} catch (GeneralException ex) {
			throw new GeneralException("La cuenta origen no existe");
		}
		// Crea el comprobante deposito
		ComprobanteDTO comprobante = new ComprobanteDTO();
		comprobante.setDebe(cantidad);
		// Guarda el comprobante deposito
		guardarComprobanteDeposito(comprobante, cuenta.getIdCuenta(), "DEPO");
	}

	/**
	 * Genera un transaccion de credito
	 * @param identificacionCliente
	 * @param valorCredito
	 * @return
	 * @throws GeneralException
	 */
	public long transaccionAutomaticaCredito(String identificacionCliente, double valorCredito) throws GeneralException {
		InfoCuenta cuenta;
		try {
			cuenta = cuentaDAO.obtenerInfoBasicaCuentaPorIdentificacion(identificacionCliente);
		} catch (GeneralException ex) {
			throw new GeneralException("La cuenta origen no existe");
		}
		// Obtiene la info transaccion de credito
		InfoTransacion infoTrans = transaccionDAO.obtenerInfoTransaccion("TRA-CRE");
		// Agrega la cuenta y la transacion
		ComprobanteDTO comprobante = new ComprobanteDTO();
		comprobante.setFecha(new Date());
		comprobante.setCuenta(new CuentaDTO(cuenta.getIdCuenta()));
		comprobante.setTransaccion(new TransaccionDTO(infoTrans.getIdTransacion()));
		comprobante.setNumero(infoTrans.getNumeroTransacion());
		comprobante.setDebe(valorCredito); // El valor del credito
		comprobante.setSaldo(cuenta.getSaldoActual() + valorCredito);
		// Guarda el comprobante
		transaccionDAO.guardarComprobante(comprobante);
		// Actualiza el saldo de la cuenta
		cuentaDAO.actualizarSaldoCuentaDeposito(cuenta.getIdCuenta(), valorCredito);
		// Actualiza el numero de la tranasacion
		transaccionDAO.actualizarNumeroTransaccion(infoTrans.getIdTransacion());
		return comprobante.getId();
	}

	/**
	 * Genera un transacion tipo pago cuota credito
	 * @param idCuotaCredito
	 * @param valorPago
	 * @throws GeneralException
	 */
	public void transaccionPagoCuota(long idCuotaCredito, double valorPago, long idUsuario) throws GeneralException {
		// Verifica que el valor tenga saldo
		InfoCuota infoCuota = creditoDAO.obtenerInfoCuota(idCuotaCredito);
		if (infoCuota.getSaldoCuota() == 0){
			throw new GeneralException("La cuota no tiene saldo pendiente");
		}
		if (valorPago == 0){
			throw new GeneralException("El valor a pagar debe ser mayor a cero");
		}
		if (valorPago > infoCuota.getSaldoCuota()){
			throw new GeneralException("El valor a pagar deber ser menor o igual al saldo pendiente");
		}
		if (valorPago > infoCuota.getSaldoCuenta()){
			throw new GeneralException("La cuenta no tiene saldo suficiente");
		}
		// Obtiene la info transaccion de credito
		InfoTransacion infoTrans = transaccionDAO.obtenerInfoTransaccion("TRA-PAG");
		// Agrega la cuenta y la transacion
		ComprobanteDTO comprobante = new ComprobanteDTO();
		comprobante.setFecha(new Date());
		comprobante.setCuenta(new CuentaDTO(infoCuota.getIdCuenta()));
		comprobante.setTransaccion(new TransaccionDTO(infoTrans.getIdTransacion()));
		comprobante.setNumero(infoTrans.getNumeroTransacion());
		comprobante.setHaber(valorPago); // El valor del pago
		comprobante.setSaldo(infoCuota.getSaldoCuenta() - valorPago);
		// Guarda el comprobante
		transaccionDAO.guardarComprobante(comprobante);
		// Actualiza el estdo y saldo de la cuota
		double saldoCuota = MathUtil.roundAvoid(infoCuota.getSaldoCuota() - valorPago, 2);
		if (saldoCuota == 0){
			// Actualiza el estado de la solicitud
			long idEstado = catalogoDAO.obtenerIdCatalogoPorCodigo("TIP-PAG-CUO-CAN");
			cuotaCreditoDAO.actualizarSaldoCuota(infoCuota.getIdCuota(), saldoCuota, idEstado);
		}else{
			cuotaCreditoDAO.actualizarSaldoCuota(infoCuota.getIdCuota(), saldoCuota);
		}
		// Crea el detalle pago credito
		PagoCuotaDTO pagoCuotaDTO = new PagoCuotaDTO();
		pagoCuotaDTO.setComprobante(comprobante);
		pagoCuotaDTO.setCutaCredito(new CuotaCreditoDTO(idCuotaCredito));
		pagoCuotaDTO.setSaldo(saldoCuota);
		pagoCuotaDAO.guardarPagoCredito(pagoCuotaDTO, idUsuario);
		// Actualiza el saldo de la cuenta
		cuentaDAO.actualizarSaldoCuentaRetiro(infoCuota.getIdCuenta(), valorPago);
		// Actualiza el numero de la tranasacion
		transaccionDAO.actualizarNumeroTransaccion(infoTrans.getIdTransacion());
	}

	/**
	 * Guarda un comprobante de la transacion transferencia
	 *
	 * @param idCuenta
	 * @param codigoTrans
	 * @throws GeneralException
	 */
	public void guardarComprobanteTransaferencia(ComprobanteDTO comprobante, Long idCuenta, String codigoTrans,
			boolean ingreso) throws GeneralException {
		// Obtiene el saldo actual de la cuenta
		double saldoActual = cuentaDAO.obtenerSaldoCuenta(idCuenta);
		if (!ingreso){
			if (comprobante.getDebe() > saldoActual) {
				throw new GeneralException("La cuenta no tiene saldo suficiente.");
			}
		}
		// Obtiene la info transacion
		InfoTransacion infoTrans = transaccionDAO.obtenerInfoTransaccion(codigoTrans);
		// Agrega la cuenta y la ransacion
		comprobante.setFecha(new Date());
		comprobante.setCuenta(new CuentaDTO(idCuenta));
		comprobante.setTransaccion(new TransaccionDTO(infoTrans.getIdTransacion()));
		comprobante.setNumero(infoTrans.getNumeroTransacion());
		if (ingreso){
			comprobante.setSaldo(saldoActual + comprobante.getDebe());
		}else{
			comprobante.setSaldo(saldoActual - comprobante.getHaber());
		}
		// guarda el comprobante transaccion
		transaccionDAO.guardarComprobante(comprobante);
		if (ingreso) {
			// Actualiza el saldo de la cuenta
			cuentaDAO.actualizarSaldoCuentaDeposito(idCuenta, comprobante.getDebe());
		} else {
			// Actualiza el saldo de la cuenta
			cuentaDAO.actualizarSaldoCuentaRetiro(idCuenta, comprobante.getHaber());
		}
		// Actualiza el numero de la tranasacion
		transaccionDAO.actualizarNumeroTransaccion(infoTrans.getIdTransacion());
	}

	/**
	 * Guarda un comprobante de la transacion retiro
	 *
	 * @param comprobante
	 * @param idCuenta
	 * @param codigoTrans
	 * @throws GeneralException
	 */
	public void guardarComprobanteRetiro(ComprobanteDTO comprobante, Long idCuenta, String codigoTrans)
			throws GeneralException {
		// Verifica que la cuenta tenga saldo suciciente
		double saldoActual = cuentaDAO.obtenerSaldoCuenta(idCuenta);
		if (comprobante.getDebe() > saldoActual) {
			throw new GeneralException("La cuenta no tiene saldo suficiente.");
		}
		// Obtiene la info transacion
		InfoTransacion infoTrans = transaccionDAO.obtenerInfoTransaccion(codigoTrans);
		// Agrega la cuenta y la ransacion
		comprobante.setFecha(new Date());
		comprobante.setCuenta(new CuentaDTO(idCuenta));
		comprobante.setTransaccion(new TransaccionDTO(infoTrans.getIdTransacion()));
		comprobante.setNumero(infoTrans.getNumeroTransacion());
		comprobante.setSaldo(saldoActual - comprobante.getHaber());
		// Guarda el comprobante
		transaccionDAO.guardarComprobanteRetiro(comprobante);
		// Actualiza el saldo de la cuenta
		cuentaDAO.actualizarSaldoCuentaRetiro(idCuenta, comprobante.getHaber());
		// Actualiza el numero de la tranasacion
		transaccionDAO.actualizarNumeroTransaccion(infoTrans.getIdTransacion());
	}

	/**
	 * Guarda un comprobante de la transacion deposito
	 *
	 * @param comprobante
	 * @param idCuenta
	 * @param codigoTrans
	 * @throws GeneralException
	 */
	public void guardarComprobanteDeposito(ComprobanteDTO comprobante, Long idCuenta, String codigoTrans)
			throws GeneralException {
		double saldoActual = cuentaDAO.obtenerSaldoCuenta(idCuenta);
		// Obtiene la info transacion
		InfoTransacion infoTrans = transaccionDAO.obtenerInfoTransaccion(codigoTrans);
		// Agrega la cuenta y la ransacion
		comprobante.setFecha(new Date());
		comprobante.setCuenta(new CuentaDTO(idCuenta));
		comprobante.setTransaccion(new TransaccionDTO(infoTrans.getIdTransacion()));
		comprobante.setNumero(infoTrans.getNumeroTransacion());
		comprobante.setSaldo(saldoActual + comprobante.getDebe());
		// Guarda el comprobante
		transaccionDAO.guardarComprobanteDeposito(comprobante);
		// Actualiza el saldo de la cuenta
		cuentaDAO.actualizarSaldoCuentaDeposito(idCuenta, comprobante.getDebe());
		// Actualiza el numero de la tranasacion
		transaccionDAO.actualizarNumeroTransaccion(infoTrans.getIdTransacion());
	}

	/**
	 * Obtiene las transaciones de la cuenta de un usuairo
	 *
	 * @return
	 */
	public List<DetalleTransaccion> obtenerTransaccionesCuenta(long idUsuario, Date fechaInicio, Date fechaFin)
			throws GeneralException {
		return transaccionDAO.obtenerTransaccionesCuenta(idUsuario, fechaInicio, fechaFin);
	}

	/**
	 * Obtiene la iformacion de la transacion retiro
	 *
	 * @return
	 * @throws GeneralException
	 */
	public InfoTransacion obtenerInfoTransaccionRetiro() throws GeneralException {
		return obtenerInfoTransaccion("RETI");
	}

	/**
	 * Obtiene la iformacion de la transacion retiro
	 *
	 * @return
	 * @throws GeneralException
	 */
	public InfoTransacion obtenerInfoTransaccionDeposito() throws GeneralException {
		return obtenerInfoTransaccion("DEPO");
	}

}
