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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author mmerhej
 */
@ManagedBean
@SessionScoped
public class Privilege {
    private int privilege;
    private boolean status;
    Connection con;
    Statement ps;
    ResultSet rs;
    String SQL_Str;
    
    /**
     * Creates a new instance of Privilege
     */
    public Privilege() {
    }
    
    public boolean dbData()
    {
    FacesContext context = FacesContext.getCurrentInstance();
    int user_id = (int) context.getExternalContext().getSessionMap().get("session_userid");
    
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/expenses","root","123");
            ps = con.createStatement();
            SQL_Str="Select * from users where id = '" + user_id +"'";
            rs=ps.executeQuery(SQL_Str);
            rs.next();
            privilege=rs.getInt(7);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Exception Occur :" + ex);
        }
   if(privilege==1){
   status = true;
   }
   else{
   status = false;
   }
     
   return status;
    }
}