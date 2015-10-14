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
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

@ManagedBean(name = "Export")
@SessionScoped
    public class Export {

    private Object response;
    private HashMap hash;
    private String select;
    
    JasperPrint jasperprint;
    JasperPrint jasperprint2;
    Connection conn;

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
    
    public void init2() throws JRException, ClassNotFoundException, SQLException{
    Class.forName("com.mysql.jdbc.Driver");    
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expenses","root","123");
    hash = new HashMap();
    hash.put("usr",select);
    jasperprint= JasperFillManager.fillReport("/home/mmerhej/NetBeansProjects/expenses_v5/web/reports/report1.jasper", new HashMap(),conn);
    jasperprint2= JasperFillManager.fillReport("/home/mmerhej/NetBeansProjects/expenses_v5/web/reports/report2.jasper", hash,conn);    
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
    
        public void exportPdf2(ActionEvent actionevent) throws JRException, IOException, ClassNotFoundException, SQLException {
    init2();
    
    HttpServletResponse httpServletResponse= (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    httpServletResponse.addHeader("Content-disposition",  "inline; filename=report.pdf");
        try (ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperprint2, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();
            servletOutputStream.flush();
        }
    }
     
 
    public void exportXLS2(ActionEvent actionevent) throws JRException, IOException, ClassNotFoundException, SQLException {
    init2();
    
    JRXlsxExporter exporter = new JRXlsxExporter();
    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint2);
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
