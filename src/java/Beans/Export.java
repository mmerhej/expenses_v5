/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;


/**
 *
 * @author mmerhej
 */


import java.io.ByteArrayOutputStream;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

@ManagedBean(name = "Export")
@SessionScoped
public class Export {

    JasperPrint jasperprint;
    Connection conn;
    private Object response;

    public void init2() throws JRException, ClassNotFoundException, SQLException{
    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expenses","root","123");    
    jasperprint= JasperFillManager.fillReport("/home/mmerhej/NetBeansProjects/expenses_v5/web/reports/report1.jasper", new HashMap(),conn);
        
    }
    
    public void exportPdf(ActionEvent actionevent) throws JRException, IOException, ClassNotFoundException, SQLException {
    init2();
    
    HttpServletResponse httpServletResponse= (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    httpServletResponse.addHeader("Content-disposition",  "inline; filename=report.pdf");
        try (ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperprint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();
            servletOutputStream.flush();
        }
    }

        public void exportXML(ActionEvent actionevent) throws JRException, IOException, ClassNotFoundException, SQLException {
    init2();
    
    HttpServletResponse httpServletResponse= (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    httpServletResponse.addHeader("Content-disposition", "inline; filename=report.xml");
        try (ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {
            JasperExportManager.exportReportToXmlStream(jasperprint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();
            servletOutputStream.flush();
        }
    }
 
    public void exportXLS(ActionEvent actionevent) throws JRException, IOException, ClassNotFoundException, SQLException {
    init2();
    
    JRXlsxExporter exporter = new JRXlsxExporter();
    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    HttpServletResponse httpServletResponse= (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.xlsx");
    ServletOutputStream servletOutputStream;
    servletOutputStream = httpServletResponse.getOutputStream();
    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
    httpServletResponse.getOutputStream().write(os.toByteArray());
    httpServletResponse.flushBuffer();
    exporter.exportReport();
        }
    }
