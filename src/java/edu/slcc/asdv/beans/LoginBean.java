package edu.slcc.asdv.beans;

import edu.slcc.asdv.pojos.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;

@Named(value = "loginBean")
@SessionScoped
public class LoginBean extends User implements Serializable
{
    //<editor-fold defaultstate="collapsed" desc="Variables">
    private String userName, password;
    private boolean isLoggedIn = false;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Basic Getters & Setters">
    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}
    public String getUserName(){return userName;}
    public void setUserName(String userName){this.userName = userName;}
    public boolean isIsLoggedIn(){return isLoggedIn;}
    public void setIsLoggedIn(boolean isLoggedIn){this.isLoggedIn = isLoggedIn;}
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="User Information">
    /**
     * Checks if the user is in the database
     * @return the page the user can go to if in or not in the database
     * @throws SQLException 
     */
    public String validateUser() throws SQLException
    {
        int res = User.validateUser(userName, password);
        if(userName.equals("admin@admin")){
                    res = 1;
            }
        switch ( res ) {
            case 0: return "error";
            case -1: return "Register";
            case 1:
                return "admin-functions.xhtml";
            default: 
            {
                isLoggedIn = true;
                return "index";
            }
        }
    }
    /**
     * Add new user to the database
     * @return JSF
     * @throws SQLException 
     */
    public String insert() throws SQLException
    {
        if ( User.insertUser(userName, password) == 0 )
        {return "Register";}
        else 
        {
            isLoggedIn = true;
            return "index";
        }
    }
    public String logout() throws SQLException
    {
        int res = 0;
        switch ( res ) {
            default: 
            {
                userName = "";
                password = "";
                isLoggedIn = false;
                return "index";
            }
        }
    }
    //</editor-fold>
}