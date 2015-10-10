package imported_from_db;

import imported_from_db.util.JsfUtil;
import imported_from_db.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@ManagedBean(name = "operationsController")
@SessionScoped

public class OperationsController implements Serializable {
 
    @EJB
    private imported_from_db.OperationsFacade ejbFacade;
    private List<Operations> items = null;
    private Operations selected;

    public OperationsController() {
    }

    public Operations getSelected() {
        return selected;
    }

    public void setSelected(Operations selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OperationsFacade getFacade() {
        return ejbFacade;
    }

    public Operations prepareCreate() {
        selected = new Operations();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/expBundle").getString("OperationsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/expBundle").getString("OperationsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/expBundle").getString("OperationsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Operations> getItems1() {
        if (items == null) {         
            items = getFacade().findAll();
            }
        return items;
    }
    
    public List<Operations> getItems() {
        if (items == null) {         
            
            FacesContext context = FacesContext.getCurrentInstance();
            String test = (String) context.getExternalContext().getSessionMap().get("key");
            int user_id = (int) context.getExternalContext().getSessionMap().get("session_userid");
            switch (test) {
                case "0":
                    items = getFacade().findByUserId(user_id);
                    break;
                case "1":
                    items = getFacade().findAll();
                    break;
            }
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/expBundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/expBundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Operations> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Operations> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Operations.class)
    public static class OperationsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OperationsController controller = (OperationsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "operationsController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Operations) {
                Operations o = (Operations) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Operations.class.getName()});
                return null;
            }
        }

    }

}
