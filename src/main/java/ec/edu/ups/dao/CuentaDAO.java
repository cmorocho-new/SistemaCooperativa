package ec.edu.ups.dao;

import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.CuentaDTO;
import ec.edu.ups.pojo.InfoCuenta;

@Stateless
public class CuentaDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Guarda los datos de una cuenta
	 * @throws GeneralException
	 */
	public void guardarCuenta(CuentaDTO cuentaDTO, long idUsuario) throws GeneralException {
		try {
			cuentaDTO.setFechaRegistro(new Date());
			cuentaDTO.setUsuarioRegistro(idUsuario);
			em.persist(cuentaDTO);
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Reduce el saldo de una cuenta segun su id.
	 * @throws GeneralException
	 */
	public void actualizarSaldoCuentaRetiro(long idCuenta, double valorTrans) throws GeneralException {
		try {
			em.createQuery("UPDATE CuentaDTO c SET c.saldoActual = c.saldoActual - :valorTrans, " +
					"c.ultimaTransaccion = :ultimaTransaccion " +
					"WHERE c.id = :idCuenta")
					.setParameter("valorTrans", valorTrans)
					.setParameter("ultimaTransaccion", new Date())
					.setParameter("idCuenta", idCuenta)
					.executeUpdate();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Aumenta el saldo de una cuenta segun su id.
	 * @throws GeneralException
	 */
	public void actualizarSaldoCuentaDeposito(long idCuenta, double valorTrans) throws GeneralException {
		try {
			em.createQuery("UPDATE CuentaDTO c SET c.saldoActual = c.saldoActual + :valorTrans, " +
					"c.ultimaTransaccion = :ultimaTransaccion " +
					"WHERE c.id = :idCuenta")
					.setParameter("valorTrans", valorTrans)
					.setParameter("ultimaTransaccion", new Date())
					.setParameter("idCuenta", idCuenta)
					.executeUpdate();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Obtiene el saldo de una cuenta segun su id.
	 * @throws GeneralException
	 */
	public double obtenerSaldoCuenta(long idCuenta) throws GeneralException {
		try {
			return em.createQuery("SELECT c.saldoActual FROM CuentaDTO c " +
					"WHERE c.id = :idCuenta", Double.class)
					.setParameter("idCuenta", idCuenta)
					.getSingleResult();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}


	/**
	 * Obtiene la informacion basica de una cuenta segun su numero
	 */
	public InfoCuenta obtenerInfoBasicaCuentaPorIdentificacion(String identificacion) throws GeneralException {
		try{
			return em.createQuery("SELECT NEW ec.edu.ups.pojo.InfoCuenta(c.id, c.numero, c.saldoActual) " +
					"FROM CuentaDTO c WHERE c.cliente.identificacion = :identificacion", InfoCuenta.class)
					.setParameter("identificacion", identificacion)
					.getSingleResult();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}


	/**
	 * Obtiene la informacion basica de una cuenta segun su numero
	 */
	public InfoCuenta obtenerInfoBasicaCuenta(String numeroCuenta) throws GeneralException {
		try{
			return em.createQuery("SELECT NEW ec.edu.ups.pojo.InfoCuenta(c.id, c.numero, c.saldoActual) " +
					"FROM CuentaDTO c WHERE c.numero = :numeroCuenta", InfoCuenta.class)
					.setParameter("numeroCuenta", numeroCuenta)
					.getSingleResult();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Obtiene la informacion basica de una cuenta segun el id usuario
	 */
	public InfoCuenta obtenerInfoBasicaCuenta(long idUsuario) throws GeneralException {
		try{
			return em.createQuery("SELECT NEW ec.edu.ups.pojo.InfoCuenta(c.id, c.numero, c.saldoActual) " +
					"FROM CuentaDTO c WHERE c.usuario.id = :idUsuario", InfoCuenta.class)
					.setParameter("idUsuario", idUsuario)
					.getSingleResult();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Obtiene la informacion de una cuenta segun su numero
	 */
	public InfoCuenta obtenerInfoCuenta(String numeroCuenta) throws GeneralException {
		try{
			return em.createQuery("SELECT NEW ec.edu.ups.pojo.InfoCuenta(c.id, c.numero, c.cliente.identificacion, " +
					"CONCAT(c.cliente.nombres, ' ', c.cliente.apellidos), c.cliente.correo, c.ultimaTransaccion) " +
					"FROM CuentaDTO c WHERE c.numero = :numeroCuenta", InfoCuenta.class)
					.setParameter("numeroCuenta", numeroCuenta)
					.getSingleResult();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * Obtiene la informacion de una cuenta con su saldo segun el id de usuario
	 */
	public InfoCuenta obtenerInfoCuenta(long idUsuario) throws GeneralException {
		try{
			return em.createQuery("SELECT NEW ec.edu.ups.pojo.InfoCuenta(c.numero, " +
					"CONCAT(c.cliente.nombres, ' ', c.cliente.apellidos), c.ultimaTransaccion, c.saldoActual) " +
					"FROM CuentaDTO c WHERE c.usuario.id = :idUsuario", InfoCuenta.class)
					.setParameter("idUsuario", idUsuario)
					.getSingleResult();
		} catch (Exception ex) {
			throw new GeneralException("ERROR: " + ex.getMessage());
		}
	}

}
