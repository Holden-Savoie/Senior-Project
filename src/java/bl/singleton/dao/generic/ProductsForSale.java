package bl.singleton.dao.generic;

import edu.slcc.asdv.pojos.Products;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class ProductsForSale<K, V extends Keyable>
     implements Product<Keyable>
{

    private Map<K, V> map = new HashMap<>();

    @Override
    public void create(Keyable t)
    {
        map.put(( K ) t.getKey(), ( V ) t);
        {
            try {
                Products.updateDBAdd(( Item ) t);
            }
            catch ( SQLException ex ) {
                java.util.logging.Logger.getLogger(ProductsForSale.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // update database

    @Override
    public void delete(Keyable t)
    {
        map.remove(t.getKey());
        {
            try {
                Products.updateDBDelete(( Item ) t);
            }
            catch ( SQLException ex ) {
                java.util.logging.Logger.getLogger(ProductsForSale.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //update database 

    @Override
    public void update(Keyable t)
    {
        map.replace(( K ) t.getKey(), ( V ) t);
        {
            try {
                Products.updateDBSingle(( Item ) t);
            }
            catch ( SQLException ex ) {
                java.util.logging.Logger.getLogger(ProductsForSale.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //update database

    @Override
    public Keyable find(Keyable t)
    {
        return map.get(t.getKey());
    }

    @Override
    public Collection<Keyable> findAll()
    {
        return ( Collection<Keyable> ) map.values();
    }

    public Item find2(K t)
    {
        return ( Item ) map.get(t);
    }

    public Collection<Item> findAll2()
    {
        return ( Collection<Item> ) map.values();
    }

    public Item findAllArg2(String key)
    {
        return ( Item ) map.get(key);
    }

}
