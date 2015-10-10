/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporting;


/**
 *
 * @author mmerhej
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import net.sf.jasperreports.engine.JRException;
 
@ManagedBean(name = "reportsBean")
@SessionScoped
 
public class ReportsBean extends AbstractReportBean {
 
    private final String COMPILE_FILE_NAME = "productlist";
 
    @Override
    protected String getCompileFileName() {
        return COMPILE_FILE_NAME;
    }
 
    @Override
    protected Map<String, Object> getReportParameters() {
        Map<String, Object> reportParameters = new HashMap<String, Object>();
 
        reportParameters.put("rtitle", "Hello JasperReports");
 
        return reportParameters;
    }
 
    public String execute() {
        try {
            super.prepareReport();
        } catch (JRException | IOException e) {
        }
 
        return null;
    }
}