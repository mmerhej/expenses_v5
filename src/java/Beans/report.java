/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author mmerhej
 */
@ManagedBean
@RequestScoped
public class report {
    Connection con;
    Statement ps;
    ResultSet rs;
    String SQL_Str;
    int amount=0;
    int op_amount;
    
    public int dbData()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/expenses","root","123");
            ps = con.createStatement();
            SQL_Str="SELECT sum(amount) as amnt FROM operations" ;
            rs=ps.executeQuery(SQL_Str);
            rs.next();
            amount=rs.getInt(1);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Exception Occur :" + ex);
        }
        return amount;
    }
    /**
     * Creates a new instance of report
     */
    public report() {
    }
    
    
    
}
