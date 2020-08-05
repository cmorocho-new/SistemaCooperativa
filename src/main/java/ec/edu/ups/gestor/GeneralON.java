package ec.edu.ups.gestor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;
import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dao.CatalogoDAO;
import ec.edu.ups.dao.ClienteDAO;
import ec.edu.ups.dao.CuentaDAO;
import ec.edu.ups.dto.CatalogoDTO;
import ec.edu.ups.dto.ClienteDTO;
import ec.edu.ups.dto.CuentaDTO;
import ec.edu.ups.dto.administracion.UsuarioDTO;
import ec.edu.ups.pojo.InfoCliente;
import ec.edu.ups.pojo.InfoCuenta;
import ec.edu.ups.utils.MailUtil;
import ec.edu.ups.utils.RandomUtil;


@Stateless
public class GeneralON {

	@Inject
	private ClienteDAO clienteDAO;

	@Inject
	private CuentaDAO cuentaDAO;

	@Inject
	private CatalogoDAO catalogoDAO;

	@Inject
	private AdministracionON administracionON;

	/**
	 * Guardar los datos de un cliente y crea una cuenta con su respectivo usuario.
	 *
	 * @throws GeneralException
	 */
	public void guardarCliente(ClienteDTO clienteDTO, long idUsuario) throws GeneralException {
		// Guardar datos del cliente
		clienteDAO.guardarCliente(clienteDTO, idUsuario);
		// Creacion del usario cliente
		UsuarioDTO usuarioClienteDTO = new UsuarioDTO();
		usuarioClienteDTO.setNombre(clienteDTO.nombreCompleto());
		usuarioClienteDTO.setCorreo(clienteDTO.getCorreo());
		administracionON.guardarUsuario(usuarioClienteDTO, true, idUsuario);
		// Creacion de la cuenta cliente
		CuentaDTO cuentaClienteDTO = new CuentaDTO();
		String numeroCuenta = RandomUtil.generarNumeroCuenta(clienteDTO.getIdentificacion());
		cuentaClienteDTO.setNumero(numeroCuenta);
		cuentaClienteDTO.setSaldoActual(0.0);
		cuentaClienteDTO.setCliente(clienteDTO);
		cuentaClienteDTO.setUsuario(usuarioClienteDTO);
		cuentaDAO.guardarCuenta(cuentaClienteDTO, idUsuario);
		// Envio del correo notificacion
		enviarCorreoAccesoBancaVirtual(usuarioClienteDTO, numeroCuenta);
	}

	/**
	 * Arma y envia el mensaje de acceso a la banca virtual del cliente.
	 *
	 * @param usuarioCliente
	 * @param numeroCuenta
	 */
	public void enviarCorreoAccesoBancaVirtual(UsuarioDTO usuarioCliente, String numeroCuenta) {
		String asuntoMensaje = "Acceso a la Banca Virtual";
		StringBuilder cuerpoMensaje = new StringBuilder("Estimado(a): <strong>")
				.append(usuarioCliente.getNombre()).append("</strong><br>")
				.append("SISTEMA FINANCIERO notifica a Ud. la siguiente informacion <br>")
				.append("========================================================== <br>")
				.append("<strong> Num. CUENTA: </strong> ").append(numeroCuenta).append("<br>")
				.append("<strong> USUARIO: </strong> ").append(usuarioCliente.getCorreo()).append("<br>")
				.append("<strong> CONTRASEÑA: </strong> ").append(usuarioCliente.getContresena()).append("<br>")
				.append("========================================================== <br>");
		CompletableFuture.runAsync(() -> {
			try {
				MailUtil.enviarCorreo(usuarioCliente.getCorreo(), asuntoMensaje, cuerpoMensaje.toString());
			} catch (MessagingException ex) {
				ex.printStackTrace();
			}
		});
	}

	public long obtenerIdCliente(String identificacion) throws GeneralException {
		return clienteDAO.obtenerIdCliente(identificacion);
	}

	public long obtenerIdCliente(long idUsuario) throws GeneralException {
		return clienteDAO.obtenerIdCliente(idUsuario);
	}

	public InfoCliente obtenerInfoCliente(long idCliente) throws GeneralException {
		return clienteDAO.obtenerInfoCliente(idCliente);
	}

	/**
	 * Obtener la informacion de una cuenta cliente segun el numero de cuenta
	 *
	 * @param numeroCuenta
	 * @return
	 */
	public InfoCuenta obtenerInfoCuenta(String numeroCuenta) throws GeneralException {
		return cuentaDAO.obtenerInfoCuenta(numeroCuenta);
	}

	/**
	 * Obtiene la informacion de una cuenta cliente segun el id de usuario
	 *
	 * @param idUsuario
	 * @return
	 */
	public InfoCuenta obtenerInfoCuenta(long idUsuario) throws GeneralException {
		return cuentaDAO.obtenerInfoCuenta(idUsuario);
	}

	/**
	 * Obtiene la lista de catalogos tipo Estados Civiles
	 */
	public long obtenerIdCatalogoPorCodigo(String codigo) throws GeneralException {
		return catalogoDAO.obtenerIdCatalogoPorCodigo(codigo);
	}

	/**
	 * Obtiene la lista de catalogos tipo Estados Civiles
	 */
	public List<CatalogoDTO> obtenerCatalogosEstadoCivil() throws GeneralException {
		return catalogoDAO.obtenerCatalogosPorTipo("CAT-EST-CIV");
	}

	/**
	 * Obtiene la lista de catalogos tipo Generos
	 */
	public List<CatalogoDTO> obtenerCatalogosGenero() throws GeneralException {
		return catalogoDAO.obtenerCatalogosPorTipo("CAT-GEN");
	}

	/**
	 * Obtiene la lista de catalogos tipo Identificaciones
	 */
	public List<CatalogoDTO> obtenerCatalogosIdentificacion() throws GeneralException {
		return catalogoDAO.obtenerCatalogosPorTipo("CAT-IDEN");
	}

	/**
	 * Obtiene la lista de catalogos tipo Ingreso-Egreso
	 *
	 */
	public List<CatalogoDTO> obtenerCatalogosFormaIE() throws GeneralException {
		return catalogoDAO.obtenerCatalogosValorPorTipo("TIP-FIE");

	}

	/**
	 * Método que retorna una lista de tipo de Vivienda
	 */

	public List<CatalogoDTO> obtenerCatalogoTipoVivienda() throws GeneralException {
		return catalogoDAO.obtenerCatalogosValorPorTipo("CAT-TIP-VIV");
	}

	/**
	 * Método que retorna el proposito del Credito
	 */
	public List<CatalogoDTO> obtenerCatalogoPropositoCredito() throws GeneralException {
		return catalogoDAO.obtenerCatalogosValorPorTipo("CAT-TIP-PRO-CRE");
	}

	/**
     * Método que retorna el tipo de Empleo que tiene el cliente
     */
	public List<CatalogoDTO> obtenerCatalogoTipoEmpleo() throws GeneralException{
		return catalogoDAO.obtenerCatalogosValorPorTipo("CAT-TIP-EMP");
	}

	/**
	 * Método que retorna los estados de la solicitud de credito
	 */
	public List<CatalogoDTO> obtenerCatalogoEstadoSolicitud() throws GeneralException{
		return catalogoDAO.obtenerCatalogosPorTipo("CAT-TIP-ESL");
	}

}
