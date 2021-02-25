package edu.slcc.asdv.beans;

import bl.singleton.dao.generic.Keyable;
import bl.singleton.dao.generic.ProductsForSale;
import bl.singleton.dao.generic.WarehouseSingleton;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

@Named(value = "initializationBean")
@ApplicationScoped
public class InitializationBean
{
    private static WarehouseSingleton ws;
    private static ProductsForSale<String, Keyable> pfs;

    public static ProductsForSale<String, Keyable> getPoducts(){return pfs;}
    
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init)
    {
        System.out.println("initialize singleton");
        ws = WarehouseSingleton.instantiateWarehouse();
        pfs = ws.getProducts();
    }
    public static WarehouseSingleton getWarehouse(){return ws;}
    
    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init){}//cleanup and save
    
}