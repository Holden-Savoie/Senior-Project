package bl.singleton.dao.generic;
import java.util.Objects;
public class Item
     implements Keyable<String>, Comparable<Item>, Categorable<String>
{
    //<editor-fold desc="Variables and Constructor">
    private String type, price, name, itemNumber, pic_ref, quantity, description;
    public Item(){}
    public Item(String itemNumber){this.itemNumber = itemNumber;}
    public Item(String type, String price, String name,
         String itemNumber, String pic_ref, String quantity,
         String description)
    {
        this.type = type;
        this.price = price;
        this.name = name;
        this.itemNumber = itemNumber;
        this.pic_ref = pic_ref;
        this.quantity = quantity;
        this.description = description;
    }
    //</editor-fold>
    
    //<editor-fold desc="Getters and Setters">
    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}
    public String getItemNumber(){return itemNumber;}
    public void setItemNumber(String itemNumber){this.itemNumber = itemNumber;}
    @Override
    public String getKey(){return getItemNumber();}
    @Override
    public void setKey(String key){setItemNumber(key);}
    @Override
    public String getType(){return type;}
    @Override
    public void setType(String t){this.type = t;}
    public String getPrice(){return price;}
    public void setPrice(String price){this.price = price;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String getPic_ref(){return pic_ref;}
    public void setPic_ref(String pic_ref){this.pic_ref = pic_ref;}
    public String getQuantity(){return quantity;}
    public void setQuantity(String quantity){this.quantity = quantity;}
    //</editor-fold>
    
    //<editor-fold desc="compareTo, equals, toString">
    @Override
    public int compareTo(Item o){return this.itemNumber.compareTo(o.itemNumber);}
    @Override
    public boolean equals(Object obj)
    {
        if ( this == obj )
        {return true;}
        if ( obj == null ) 
        {return false;}
        if ( getClass() != obj.getClass() ) 
        {return false;}
        final Item other = ( Item ) obj;
        if ( !Objects.equals(this.itemNumber, other.itemNumber) )
        {return false;}
        return true;
    }
    @Override
    public String toString()
    {
        return "Item{ " + "type = " + type + ", price = " + price +
             ", name = " + name + ", itemNumber = " + itemNumber +
             ", pic_ref = " + pic_ref + ", quantity = " + quantity +
             ", description = " + description + '}';
    }    
//</editor-fold>
    
}