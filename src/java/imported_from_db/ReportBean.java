/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imported_from_db;


/**
 *
 * @author mmerhej
 */


import imported_from_db.Users;
import imported_from_db.UsersFacade;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean(name = "ReportBean")
@SessionScoped
public class ReportBean {
    @EJB
    UsersFacade Usr;
    public List<Users> list;
    JasperPrint jasperprint;
    Connection conn;
 
    public List<Users> getList() {
        list= Usr.findAll();
        return list;
    }

    public void setList(List<Users> list) {
        this.list = list;
    }
   
    
    public void init2() throws JRException, ClassNotFoundException, SQLException{
    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expenses","root","123");    
    jasperprint= JasperFillManager.fillReport("/home/mmerhej/NetBeansProjects/expenses_v5/web/operations/report1.jasper", new HashMap(),conn);
        
    }
    
    public void exportPdf(ActionEvent actionevent) throws JRException, IOException, ClassNotFoundException, SQLException {
    init2();
    
    HttpServletResponse httpServletResponse= (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
    ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
    JasperExportManager.exportReportToPdfStream(jasperprint, servletOutputStream);
    FacesContext.getCurrentInstance().responseComplete();
    servletOutputStream.flush();
    servletOutputStream.close();
    }


}
