package edu.slcc.asdv.beans;

import bl.singleton.dao.generic.Keyable;
import bl.singleton.dao.generic.ProductsForSale;
import bl.singleton.dao.generic.WarehouseSingleton;
import bl.singleton.dao.generic.Item;
import edu.slcc.asdv.pojos.Products;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.SortedMap;

@Named(value = "productFind")
@SessionScoped
public class ProductFind implements Serializable
{

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private boolean isSearching = false;
    private String searchInput;
    private SortedMap[] cubby;
    private String itemNumber;
    private String numChosen;
    private int itemKey;
    private String title = "";
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Basic Getters & Setters">
    public int getItemKey()
    {
        return itemKey;
    }

    public void setItemKey(int itemKey)
    {
        this.itemKey = itemKey;
    }

    public String getItemNumber()
    {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber)
    {
        this.itemNumber = itemNumber;
    }

    public boolean isIsSearching()
    {
        return isSearching;
    }

    public void setIsSearching(boolean isSearching)
    {
        this.isSearching = isSearching;
    }

    public SortedMap[] getCubby()
    {
        return cubby;
    }

    public void setCubby(SortedMap[] cubby)
    {
        this.cubby = cubby;
    }

    public String getSearchInput()
    {
        return searchInput;
    }

    public void setSearchInput(String searchInput)
    {
        this.searchInput = searchInput;
    }

    public String getNumChosen()
    {
        return numChosen;
    }

    public void setNumChosen(String numChosen)
    {
        isSearching = true;
        this.numChosen = numChosen;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Item Information">
    /**
     * Returns all of the products inside the database
     *
     * @throws SQLException
     */
    public Collection<Item> retrieveEverything() throws SQLException
    {

        return Products.getAllProductMap().findAll2();
//      this.cubby = Products.getAllProduct().getRows();
    }

    /**
     * Returns the products searched from the menu inside the database
     *
     * @throws SQLException
     */
    public void search() throws SQLException
    {
        this.isSearching = true;
        this.cubby = Products.searchInfo(searchInput).getRows();
        itemKey = Products.getItemNumber(searchInput);
    }

    /**
     * Returns the products searched from the search bar inside the database
     *
     * @throws SQLException
     */
    public void search(String item) throws SQLException
    {
        this.isSearching = true;
        this.cubby = Products.searchInfo(item).getRows();
        itemKey = Products.getItemNumber(item);
    }

    public void viewItem(String title) throws SQLException
    {
        this.isSearching = true;
        if(Products.pfs.find2(String.valueOf(title)) == null){
            return;
        }else{
            this.title = "Acer Predator";
        }
        search(this.title);
    }

    /**
     * Gets the previous items from the list when the next button is pressed
     *
     * @throws SQLException
     */
    public void roundaboutP() throws SQLException
    {
        if ( Products.pfs.find2(String.valueOf(Integer.valueOf(this.numChosen + 1))) != null ) {
            this.numChosen = String.valueOf(Integer.valueOf(this.numChosen + 1));
        }
        else {
            this.numChosen = "1";
        }
        search(this.numChosen);
    }

    /**
     * Gets the next items from the list when the next button is pressed
     *
     * @throws SQLException
     */
    public void roundaboutN() throws SQLException
    {
        if ( Products.pfs.find2(String.valueOf(Integer.valueOf(this.numChosen + 1))) != null ) {
            numChosen = String.valueOf(Integer.valueOf(this.numChosen + 1));
        }
        else {
            this.numChosen = "1";
        }
        search(numChosen);
    }

    public Item retrieveSingle(String item) throws SQLException
    {
        this.isSearching = true;
        return Products.getSingleProductMap(item);
    }

    public void placeOrder(String item) throws SQLException
    {
        this.isSearching = true;
        String itemQty = Products.getItemPurchased(item).getQuantity();
        int temp = Integer.parseInt(itemQty) - 1;
        String newQty = String.valueOf(temp);
        Products.updateQty(newQty);
    }

//    public ProductsForSale<String, Keyable> returnPfs(){}
    //</editor-fold>
    public ProductFind() throws SQLException
    {
        System.out.println("Warehouse in bean -> " + InitializationBean.getWarehouse());
        WarehouseSingleton ws = InitializationBean.getWarehouse();
        ProductsForSale<String, Keyable> p = ws.getProducts();
        System.out.println(p);
    }
}
