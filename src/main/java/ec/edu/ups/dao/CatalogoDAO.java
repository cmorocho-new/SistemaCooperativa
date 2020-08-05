package ec.edu.ups.dao;

import ec.edu.ups.common.GeneralException;
import ec.edu.ups.dto.CatalogoDTO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CatalogoDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Obtiene los catalogos de acuerdo al tipo
     * @param codigoTipo
     * @return
     * @throws GeneralException
     */
    public List<CatalogoDTO> obtenerCatalogosPorTipo(String codigoTipo) throws GeneralException {
        try{
            return em.createQuery("SELECT NEW ec.edu.ups.dto.CatalogoDTO(c.id, c.codigo, c.nombre) " +
                            "FROM CatalogoDTO c WHERE c.tipoCatalogo.codigo = :codigoTipo", CatalogoDTO.class)
                    .setParameter("codigoTipo",  codigoTipo)
                    .getResultList();
        }catch (Exception ex){
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }

    /**
     * Obtiene los catalogos con sus valores de acuerdo al tipo
     * @param codigoTipo
     * @return
     * @throws GeneralException
     */
    public List<CatalogoDTO> obtenerCatalogosValorPorTipo(String codigoTipo) throws GeneralException {
        try{
            return em.createQuery("SELECT NEW ec.edu.ups.dto.CatalogoDTO(c.id, c.codigo, c.nombre, c.valor) " +
                    "FROM CatalogoDTO c WHERE c.tipoCatalogo.codigo = :codigoTipo", CatalogoDTO.class)
                    .setParameter("codigoTipo",  codigoTipo)
                    .getResultList();
        }catch (Exception ex){
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }

    /**
     * Obtiene el id del catalogo por su codigo
     * @param codigoCatalogo
     * @return
     * @throws GeneralException
     */
    public long obtenerIdCatalogoPorCodigo(String codigoCatalogo) throws GeneralException {
        try{
            return em.createQuery("SELECT c.id " +
                    "FROM CatalogoDTO c WHERE c.codigo = :codigoCatalogo", Long.class)
                    .setParameter("codigoCatalogo",  codigoCatalogo)
                    .getSingleResult();
        }catch (Exception ex){
            throw new GeneralException("ERROR: " + ex.getMessage());
        }
    }

}
