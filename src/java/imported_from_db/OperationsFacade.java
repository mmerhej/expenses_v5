/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imported_from_db;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mmerhej
 */
@Stateless
public class OperationsFacade extends AbstractFacade<Operations> {
    @PersistenceContext(unitName = "expenses_v5PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperationsFacade() {
        super(Operations.class);
    }
    
}
