package edu.slcc.asdv.pojos;

import bl.singleton.dao.generic.Connect;
import bl.singleton.dao.generic.Item;
import bl.singleton.dao.generic.Keyable;
import bl.singleton.dao.generic.ProductsForSale;
import bl.singleton.dao.generic.WarehouseSingleton;
import edu.slcc.asdv.beans.InitializationBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedMap;
import static javax.management.remote.JMXConnectorFactory.connect;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

public class Products extends ProductsForSale
{

    public static WarehouseSingleton ws = InitializationBean.getWarehouse();
    public static ProductsForSale<String, Keyable> pfs = InitializationBean.getPoducts();

    public static ProductsForSale<String, Keyable> getPfs()
    {
        return pfs;
    }

    //<editor-fold defaultstate="collapsed" desc="Products Database">
    /**
     * Obtains all Products inside the database
     *
     * @param searchInput
     * @return
     * @throws SQLException
     */
    public static Result getAllProduct() throws SQLException
    {
        Connection con = Connect.connection();
        if ( con == null ) {
            System.out.println("Cannot connect to DB");
            return null;
        }
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM PRODUCT");
            return ResultSupport.toResult(result);
        }
        finally {
            con.close();
        }
    }

    /**
     * Gets the item number of the product thats searched
     *
     * @param searchInput
     * @return
     * @throws SQLException
     */
    public static int getItemNumber(String searchInput) throws SQLException
    {
        Connection con = Connect.connection();
        if ( con == null ) {
            System.out.println("Cannot connect to DB");
            return -1;
        }
        try {
            int item = 0;
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT itemNumber FROM PRODUCT WHERE NAME LIKE '%" + searchInput + "%'");
            System.out.println(result.toString());
            while ( result.next() ) {
                item = Integer.parseInt(result.getString(1));
            }
            return item;
        }
        finally {
            con.close();
        }
    }

    /**
     * Retrieves individual item based on the items key
     *
     * @param itemKey obtained when an item is searched through menu
     * @return the result set of the item from the key
     * @throws SQLException
     */
    public static Result getItem(int itemKey) throws SQLException
    {
        Connection con = Connect.connection();
        if ( con == null ) {
            System.out.println("Cannot connect to DB");
            return null;
        }
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM PRODUCT WHERE itemNumber =  '" + itemKey + "'");
            System.out.println(result.toString());
            return ResultSupport.toResult(result);
        }
        finally {
            con.close();
        }
    }

    /**
     * User enters product being searched for in the product database
     *
     * @param searchInput string user enters
     * @return products with matching characters
     * @throws SQLException
     */
    public static Result searchInfo(String searchInput) throws SQLException
    {
        Connection con = Connect.connection();
        if ( con == null ) {
            System.out.println("Cannot connect to DB");
            return null;
        }
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM PRODUCT WHERE NAME LIKE '%" + searchInput + "%'");
            System.out.println(result.toString());
            return ResultSupport.toResult(result);
        }
        finally {
            con.close();
        }
    }

    public static ProductsForSale<String, Keyable> getAllProductMap() throws SQLException
    {
        Connection con = Connect.connection();
        if ( con == null ) {
            System.out.println("Cannot connect to DB");
            return null;
        }
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM PRODUCT");
            SortedMap[] sm = ResultSupport.toResult(result).getRows();
            for ( SortedMap sm2 : sm ) {
                Item t = new Item();
                t.setType(sm2.get("type").toString());
                t.setPrice(sm2.get("price").toString());
                t.setName(sm2.get("name").toString());
                t.setItemNumber(sm2.get("itemNumber").toString());
                t.setPic_ref(sm2.get("pic_ref").toString());
                t.setQuantity(sm2.get("quantity").toString());
                t.setDescription(sm2.get("description").toString());
                pfs.create(t);
            }
            return pfs;
        }
        finally {
            con.close();
        }
    }

    public static Item getSingleProductMap(String item) throws SQLException
    {
        return pfs.findAllArg2(item);
    }

    public static Item getItemPurchased(String item)
    {
        return pfs.findAllArg2(item);
    }

    public static ProductsForSale<String, Keyable> updateQty(String quantity) throws SQLException
    {
        Connection con = Connect.connection();
        if ( con == null ) {
            System.out.println("Cannot connect to DB");
            return null;
        }
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("");
            SortedMap[] sm = ResultSupport.toResult(result).getRows();
            for ( SortedMap sm2 : sm ) {
                Item t = new Item();
                t.setType(sm2.get("type").toString());
                t.setPrice(sm2.get("price").toString());
                t.setName(sm2.get("name").toString());
                t.setItemNumber(sm2.get("itemNumber").toString());
                t.setPic_ref(sm2.get("pic_ref").toString());
                t.setQuantity(sm2.get("quantity").toString());
                t.setDescription(sm2.get("description").toString());
                pfs.create(t);
            }
            return pfs;
        }
        finally {
            con.close();
        }
    }

    public static ProductsForSale<String, Keyable> returnPfs()
    {
        return pfs;
    }

    public static int insertProduct(String type, int price, String name, int itemNumber,
         String pic_ref, String picture, int quantity, String description) throws SQLException
    {
        Connection con = Connect.connection();
        if ( con == null ) {
            System.out.println("Cannot connect to DB");
            return 0;
        }
        try {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate("INSERT INTO PRODUCT VALUES"
                 + "('" + type + "', " + price + ", '" + name + "', " + itemNumber
                 + ", '" + pic_ref + "', '" + picture + "', " + quantity + ", '" + description + "')");
            return result;
        }
        finally {
            con.close();
        }
    }

    public static String fixDesc(String desc)
    {
        if ( desc.contains("'") ) {
            desc = desc.replaceAll("'", "`");
        }
        return desc;
    }

    public static void updateDBAdd(Item it) throws SQLException
    {
        Connection con = Connect.connection();
        if ( con == null ) {
            System.out.println("Cannot connect to DB");
        }

        try {
            Statement stmt = con.createStatement();
            System.out.println("update DB started");
            int result = stmt.executeUpdate(
                 "INSERT INTO product (type, price, name, itemNumber,"
                 + " pic_ref, quantity, description) VALUES ('"
                 + it.getType() + "', "
                 + Double.valueOf(it.getPrice()) + ", '"
                 + it.getName() + "', "
                 + Integer.valueOf(it.getItemNumber()) + ", '"
                 + it.getPic_ref() + "', "
                 + Integer.valueOf(it.getQuantity()) + ", '"
                 + fixDesc(it.getDescription()) + "')");

        }
        finally {
            con.close();
        }
    }

    public static void updateDBSingle(Item it) throws SQLException
    {
        Connection con = Connect.connection();
        if ( con == null ) {
            System.out.println("Cannot connect to DB");
        }

        try {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(
                 "UPDATE product SET type = '" + it.getType()
                 + "', price = " + Double.valueOf(it.getPrice()) + ", "
                 + "name = '" + it.getName() + "', pic_ref = '" + it.getPic_ref()
                 + "', quantity = " + Integer.valueOf(it.getQuantity()) + ", "
                 + "description = '" + fixDesc(it.getDescription()) + "' "
                 + "WHERE itemNumber = " + Integer.valueOf(it.getItemNumber()));

        }
        finally {
            con.close();
        }
    }

    public static void updateDBDelete(Item it) throws SQLException
    {
        Connection con = Connect.connection();
        if ( con == null ) {
            System.out.println("Cannot connect to DB");
        }

        try {
            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate(
                 "DELETE FROM product WHERE itemNumber = "
                 + Integer.valueOf(it.getItemNumber()));

        }
        finally {
            con.close();
        }
    }

    public static boolean uploadPNG(String title, String filename, String location) throws SQLException
    {
        Connection con = Connect.connection();
        try {
            Statement st = con.createStatement();
            File imgfile = new File("C:/School/Semester4/WebApp/mp04/web/resources/files/" + filename);

            FileInputStream fin = new FileInputStream(imgfile);

            PreparedStatement pre
                 = con.prepareStatement("insert into pngholder values(?,?,?)");

            pre.setString(1, title);
            pre.setBinaryStream(2, ( InputStream ) fin, ( int ) imgfile.length());
            pre.setString(3, location);
            pre.executeUpdate();
            System.out.println("Successfully inserted the file into the database!");

            pre.close();

        }
        catch ( Exception e1 ) {
            System.out.println(e1.getMessage());
        }

        return false;
    }

    public static boolean daySale(String day, String option) throws SQLException
    {
        Connection con = Connect.connection();
        if ( con == null ) {
            System.out.println("Cannot connect to DB");
            return false;
        }

        try { //'20200324' day format
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select "
                 + option + " from sales WHERE day = " + day);

            while ( rs.next() ) {
                System.out.println("in while loop");
                //pull & convert from DB
                InputStream in = rs.getBinaryStream(1);
                //download locally
                OutputStream f = new FileOutputStream(new File("C:/School/Semester4/WebApp/mp04/web/resources/files/" + day + ".pdf"));
                int c = 0;
                while ( (c = in.read()) > -1 ) {
                    f.write(c);
                }
                f.close();
                in.close();
                System.out.println("COMPLETE");
                return true;
            }
        }
        catch ( Exception ex ) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    //</editor-fold>
}
