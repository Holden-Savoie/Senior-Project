package bl.singleton.dao.generic;

import java.util.Collection;

public class WarehouseSingleton<K, V extends Keyable>
{

    private static ProductsForSale<String, Keyable> productsForSale;
    private static WarehouseSingleton warehouse;

    private WarehouseSingleton()
    {
        productsForSale = new ProductsForSale();
    }

    public static WarehouseSingleton instantiateWarehouse()
    {
        if ( warehouse == null ) {
            warehouse = new WarehouseSingleton();
        }
        return warehouse;
    }

    public ProductsForSale<String, Keyable> getProducts()
    {
        return productsForSale;
    }

    public static void main(String[] args)
    {
        WarehouseSingleton ws = WarehouseSingleton.instantiateWarehouse();
        ProductsForSale<String, Keyable> pfs = ws.getProducts();
        Collection<Keyable> c = pfs.findAll();
        //System.out.println(c);
        //System.out.println(pfs.findAll());
    }
}
