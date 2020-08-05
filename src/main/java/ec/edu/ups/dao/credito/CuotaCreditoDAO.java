package ec.edu.ups.dao.credito;

import ec.edu.ups.common.GeneralException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CuotaCreditoDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Actualiza el saldo de la cuota
     * @param idCuota
     * @param saldoActual
     * @throws GeneralException
     */
    public void actualizarSaldoCuota(long idCuota, double saldoActual) throws GeneralException {
        try {
             em.createQuery("UPDATE CuotaCreditoDTO c SET c.saldo = :saldoActual WHERE c.id = :idCuota")
                    .setParameter("idCuota", idCuota)
                    .setParameter("saldoActual", saldoActual)
                    .executeUpdate();
        } catch (Exception ex) {
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }

    /**
     * Actualiza el saldo de la cuota con su estado
     * @param idCuota
     * @param saldoActual
     * @param idEstado
     * @throws GeneralException
     */
    public void actualizarSaldoCuota(long idCuota, double saldoActual, long idEstado) throws GeneralException {
        try {
            em.createQuery("UPDATE CuotaCreditoDTO c SET c.saldo = :saldoActual, c.estadoCredito.id = :idEstado " +
                    "WHERE c.id = :idCuota")
                    .setParameter("idCuota", idCuota)
                    .setParameter("idEstado", idEstado)
                    .setParameter("saldoActual", saldoActual)
                    .executeUpdate();
        } catch (Exception ex) {
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }
}
