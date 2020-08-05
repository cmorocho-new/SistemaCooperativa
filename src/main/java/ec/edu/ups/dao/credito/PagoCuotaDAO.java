package ec.edu.ups.dao.credito;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.credito.PagoCuotaDTO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Stateless
public class PagoCuotaDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Guarda el detalle del pago credito
     * @param pagoCuotaDTO
     * @param idUsuario
     * @throws GeneralException
     */
    public void guardarPagoCredito(PagoCuotaDTO pagoCuotaDTO, long idUsuario) throws GeneralException {
        try {
            pagoCuotaDTO.setFechaRegistro(new Date());
            pagoCuotaDTO.setUsuarioRegistro(idUsuario);
            em.persist(pagoCuotaDTO);
        } catch (Exception ex) {
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }
}
