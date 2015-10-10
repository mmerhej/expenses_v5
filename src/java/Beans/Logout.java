/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author mmerhej
 */
@ManagedBean
@SessionScoped
public class Logout {

    /**
     * Creates a new instance of Logout
     */
    public Logout() {
        
    }
    public String reset() /*throws IOException*/ {
    /*ExternalContext session = FacesContext.getCurrentInstance().getExternalContext();
    session.invalidateSession();
    session.redirect(session.getRequestContextPath() + "/index.xhtml");*/
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }
}
