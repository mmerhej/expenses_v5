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
import java.io.File;
import java.io.PrintWriter;
import java.util.Map;
import java.sql.*;
 
import javax.servlet.ServletContext;
 
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
 
public class ReportConfigUtil {
 
    /**
     * PRIVATE METHODS
     */
    private static void setCompileTempDir(ServletContext context, String uri) {
        System.setProperty("jasper.reports.compile.temp", context.getRealPath(uri));
    }
 
    /**
     * PUBLIC METHODS
     */
    public static boolean compileReport(ServletContext context, String compileDir, String filename) throws JRException {
        String jasperFileName = context.getRealPath(compileDir + filename + ".jasper");
        File jasperFile = new File(jasperFileName);
 
        if (jasperFile.exists()) {
            return true; // jasper file already exists, do not compile again
        }
        try {
            // jasper file has not been constructed yet, so compile the xml file
            setCompileTempDir(context, compileDir);
 
            String xmlFileName = jasperFileName.substring(0, jasperFileName.indexOf(".jasper")) + ".jrxml";
            JasperCompileManager.compileReportToFile(xmlFileName);
 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
 
    public static JasperPrint fillReport(File reportFile, Map<String, Object> parameters, Connection conn) throws JRException {
        parameters.put("/home/mmerhej/Desktop", reportFile.getParentFile());
 
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), parameters, conn);
 
        return jasperPrint;
    }
 
    public static String getJasperFilePath(ServletContext context, String compileDir, String jasperFile) {
        return context.getRealPath(compileDir + jasperFile);
    }
 
    private static void exportReport(JRAbstractExporter exporter, JasperPrint jasperPrint, PrintWriter out) throws JRException {
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
 
        exporter.exportReport();
    }
 
    
}