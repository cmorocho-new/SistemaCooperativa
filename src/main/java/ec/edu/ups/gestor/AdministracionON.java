package ec.edu.ups.gestor;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dao.administracion.RolDAO;
import ec.edu.ups.dao.administracion.UsuarioAccesoDAO;
import ec.edu.ups.dao.administracion.UsuarioDAO;
import ec.edu.ups.dto.administracion.RolDTO;
import ec.edu.ups.dto.administracion.UsuarioAccesoDTO;
import ec.edu.ups.dto.administracion.UsuarioDTO;
import ec.edu.ups.pojo.InfoUsuario;
import ec.edu.ups.utils.MailUtil;
import ec.edu.ups.utils.RandomUtil;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Stateless
public class AdministracionON {
	
	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private RolDAO rolDAO;
	
	@Inject
	private UsuarioAccesoDAO usuarioAccesoDAO;

	/**
	 * Valida que los datos ingresados sean de un usuario administrador
	 * @param correo
	 * @param contrasenia
	 * @return
	 * @throws GeneralException
	 */
	public InfoUsuario validarUsuarioAdministrativo(String correo, String contrasenia) throws GeneralException {
		InfoUsuario usuarioAdministrativo = usuarioDAO.obtenerUsuarioAdministrativo(correo);
		if (usuarioAdministrativo.getContrasenia().equals(contrasenia)) {
			usuarioAdministrativo.setContrasenia("");
			usuarioAdministrativo.setCorreo(correo);
			usuarioAdministrativo.setEsCliente(false);
			return usuarioAdministrativo;
		}else{
			throw new GeneralException(201, "La contraseña es incorrecta");
		}
	}

	/**
	 * Valida que los datos ingresados sean de un usuario cliente
	 * @param correo
	 * @param contrasenia
	 * @param dispositivo
	 * @param ubicacion
	 * @return
	 * @throws GeneralException
	 */
	public InfoUsuario validarUsuarioCliente(String correo, String contrasenia, String dispositivo, String ubicacion)
			throws GeneralException {
		InfoUsuario usuarioCliente = usuarioDAO.obtenerUsuarioCliente(correo);
		// Crea el acceso a la banca virtual del cliente
		UsuarioAccesoDTO usuarioAcceso = new UsuarioAccesoDTO();
		usuarioAcceso.setUsuarioDTO(new UsuarioDTO(usuarioCliente.getUsarioId()));
		usuarioAcceso.setUbicacion(ubicacion);
		usuarioAcceso.setDispositivo(dispositivo);
		// Verifica si la contraseña es correcta
		boolean contraseniaCorrecta = usuarioCliente.getContrasenia().equals(contrasenia);
		usuarioAcceso.setAcceso(contraseniaCorrecta);
		usuarioAccesoDAO.guardarUsuarioAcceso(usuarioAcceso, usuarioCliente.getUsarioId());
		// Eniva el correo de notificacion de acceso
		enviarCorreoIntentoAccesoBnacaVirtual(usuarioCliente, dispositivo, ubicacion, contraseniaCorrecta);
		if (contraseniaCorrecta) {
			usuarioCliente.setContrasenia("");
			usuarioCliente.setCorreo(correo);
			usuarioCliente.setEsCliente(true);
			return usuarioCliente;
		}else{
			throw new GeneralException(201, "La contraseña es incorrecta");
		}
	}

	/**
	 * Arma y envia el mensaje de notificacion de acceso a la banca virtual del cliente.
	 * @param usuarioCliente
	 */
	public void enviarCorreoIntentoAccesoBnacaVirtual(InfoUsuario usuarioCliente, String dispositivo, String ubicacion,
													  boolean correcto) {
		String asuntoMensaje = "Intento de acceso a la Banca Virtual";
		StringBuilder cuerpoMensaje = new StringBuilder("Estimado(a): <strong>");
		cuerpoMensaje.append(usuarioCliente.getNombre()).append("</strong><br>")
				.append("SISTEMA FINANCIERO notifica a Ud. la siguiente informacion <br>")
				.append( "========================================================== <br>");
		if (correcto)
			cuerpoMensaje.append("<strong> INTENTO DE ACCESO SATISFACTORIO </strong><br>");
		else
			cuerpoMensaje.append("<strong> INTENTO DE ACCESO FALLIDO </strong><br>");
		cuerpoMensaje.append("<strong> IP ADDRES: </strong> ").append(ubicacion).append("<br>")
				.append("<strong> DISPOSITIVO: </strong> ").append(dispositivo).append( "<br>")
				.append("========================================================== <br>");
		// Envia el correo
		CompletableFuture.runAsync(() -> {
			try {
				MailUtil.enviarCorreo(usuarioCliente.getCorreo(), asuntoMensaje, cuerpoMensaje.toString());
			}catch (MessagingException ex){ ex.printStackTrace(); }
		});
	}

	/**
     * Método para guardar un usuario
     */
    public void guardarUsuario(UsuarioDTO usuarioDTO, boolean esCliente, long idUsuario) throws GeneralException {
		usuarioDTO.setEsCliente(esCliente);
    	if (esCliente)
    		usuarioDTO.setContresena(RandomUtil.generarContrasenia());
    	usuarioDAO.guardarUsuario(usuarioDTO, idUsuario);
    }

    /**
     * Obtiene la lista de los tipos de roles
     */
    public List<RolDTO> obtenerInfoRoles() throws GeneralException {
        return rolDAO.listarRoles();
    }
    
    /**
     * Obtiene la lista deel numero de accesos que haya tenido un usuario en la banca virtual
     */
    public List<UsuarioAccesoDTO> obtenerInfoAccesos(long idUsuairo) throws GeneralException{
    	return 	usuarioAccesoDAO.obtenerUsuarioAcceso(idUsuairo);
    }

}
