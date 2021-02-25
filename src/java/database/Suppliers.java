package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

public class Suppliers
{

    public static ResultSet getAllSuppliers() throws SQLException
    {
        Connection con = connection();
        if (con == null)
          {
            System.out.println("cannot connect to database");
            return null;
          }
        try
          {
            PreparedStatement ps = null;
            ResultSet rs = null;
            String sqlStr = "SELECT  *  FROM supplier";

            //prepare statement
            ps = con.prepareStatement(sqlStr);
            //execute
            rs = ps.executeQuery();
            return rs; //ResultSupport.toResult(result);
          }
        finally
          {
            //con.close();
          }
    }

    private static Connection connection() //throws InstantiationException, IllegalAccessException
    {
        String databaseName = "microcenter";
        String userName = "root";
        String password = "";
        String URL2 = "com.mysql.jdbc.Driver";
        Connection con = null;
        try
          {// Load Sun's jdbc driver
            Class.forName(URL2).newInstance();
            System.out.println("JDBC Driver loaded!");
          }
        catch (Exception e) // driver not found
          {
            System.err.println("Unable to load database driver");
            System.err.println("Details : " + e);
            return null;
          }
        String ip = "localhost"; //internet connection
        String url = "jdbc:mysql://" + ip + ":3306/" + databaseName;
        try
          {
            con = DriverManager.getConnection(url, userName, password);
            con.setReadOnly(false);
          }
        catch (Exception e)
          {
            System.err.println(e.toString());
            return null;
          }
        System.out.println("connection successfull");
        return con;
    }

    public static void main(String[] args)
            throws SQLException
    {
//        ResultSet results = Suppliers.getAllSuppliers();
//
//        while (results.next())
//          {
//
//            String s = results.getString("snumber");
//            System.out.println(s);
//            results.getString("sname");
//            results.getInt("status");
//            results.getString("city");
//          }
//        results.close();
                Connection con = connection();
        try {
            Statement st = con.createStatement();
            File imgfile = new File("./web/resources/files/demoInvoice.pdf");
            File imgfile2 = new File("./web/resources/files/demoTotal.pdf");

            FileInputStream fin = new FileInputStream(imgfile);
            FileInputStream fin2 = new FileInputStream(imgfile2);

            PreparedStatement pre
                    = con.prepareStatement("insert into sales values(?,?,?)");

            Timestamp x = Timestamp.valueOf(LocalDateTime.now());
            pre.setTimestamp(1, x);
            pre.setBinaryStream(2, (InputStream) fin, (int) imgfile.length());
            pre.setBinaryStream(3, (InputStream) fin2, (int) imgfile2.length());
            pre.executeUpdate();
            System.out.println("Successfully inserted the file into the database!");

            pre.close();

        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }
}
