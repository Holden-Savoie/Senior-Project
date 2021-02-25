package edu.slcc.asdv.pojos;

import bl.singleton.dao.generic.Connect;
import edu.slcc.asdv.utilities.DESUtil;
import edu.slcc.asdv.utilities.UserKey;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User
{
   
    //<editor-fold defaultstate="collapsed" desc="User Database">
    /**
     * *
     * Takes in user entered username and password entered and encrypts data.
     * Sent to database for validation
     *
     * @param username entered by user
     * @param password entered by user
     * @return integer value whether user is logged in
     * @throws SQLException
     */
    public static int validateUser(String username, String password) throws SQLException
    {
        Connection con = Connect.connection();
        if ( con == null ) 
        {
            System.out.println("Cannot connect to DB");
            return 0;
        }
        //encrypt user input
        UserKey usr = new UserKey(username, password);
        byte[] ar = usr.StringToKey(usr.keyToString());
        password = DESUtil.encrypt(usr.getPassword(), ar);
        try 
        {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM login WHERE username "
                 + "= '" + username + "' AND password = '" + password + "'");
            if ( result.next() == false ) 
            {return -1;}
            else 
            {return 1;}
        }
        finally {con.close();}
    }
    /**
     * Gets user information to save in the database
     *
     * @param username obtained by user input
     * @param password obtained by user input
     * @return nothing
     * @throws SQLException
     */
    public static int insertUser(String username, String password) throws SQLException
    {
        Connection con = Connect.connection();
        if ( con == null ) 
        {
            System.out.println("Cannot connect to DB");
            return 0;
        }
        //encrypt user input
        UserKey usr = new UserKey(username, password);
        byte[] ar = usr.StringToKey(usr.keyToString());
        password = DESUtil.encrypt(usr.getPassword(), ar);
        try 
        {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate("INSERT INTO LOGIN VALUES"
                 + "('" + username + "', '" + password + "')");
            return result;
        }
        finally {con.close();}
    }
    //</editor-fold>
}
