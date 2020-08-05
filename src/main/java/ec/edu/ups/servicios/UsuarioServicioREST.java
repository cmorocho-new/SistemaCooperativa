package ec.edu.ups.servicios;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.gestor.AdministracionON;
import ec.edu.ups.pojo.InfoUsuario;
import ec.edu.ups.trasient.ResultadoTrasient;
import ec.edu.ups.trasient.UsuarioTrasient;
import ec.edu.ups.utils.SessionUtil;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;

@Path("/usuarios")
public class UsuarioServicioREST {

    @Inject
    private AdministracionON administracionON;

    @Inject
    HttpServletRequest request;

    @POST
    @Path("/login")
    @Consumes("appication/json")
    @Produces("appication/json")
    public ResultadoTrasient loginCliente (UsuarioTrasient usuarioTrasient) {
        try{
            String dispositivo = request.getRemoteHost();
            String ipAddress = request.getRemoteAddr();
            InfoUsuario usuario = administracionON.validarUsuarioCliente(usuarioTrasient.getCorreo(),
                    usuarioTrasient.getContrasenia(), dispositivo, ipAddress);
            SessionUtil.setInfoUsuarioLogeado(usuario);
            return new ResultadoTrasient("200", usuario);
        }catch (GeneralException ex){
            return new ResultadoTrasient("100", ex.getMessage());
        }
    }
}
