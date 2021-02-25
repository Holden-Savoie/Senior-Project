package edu.slcc.asdv.beans;

import bl.singleton.dao.generic.Item;
import edu.slcc.asdv.pojos.Products;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.Part;

@Named(value = "adminBean")
@SessionScoped
public class AdminBean implements Serializable
{

        //<editor-fold desc="Variables and Constructor">
    private String type, price, name, itemNumber, pic_ref, quantity, description, pngTitle;
    private boolean isSuccess;

    public boolean isIsSuccess()
    {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess)
    {
        this.isSuccess = isSuccess;
    }


    public String getPngTitle()
    {
        return pngTitle;
    }

    public void setPngTitle(String pngTitle)
    {
        this.pngTitle = pngTitle;
    }

    private Part uploadedFile;

    public Part getUploadedFile()
    {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile)
    {
        this.uploadedFile = uploadedFile;
    }

    private String choice = "all";
    private Map<String, String> allItemNo = new HashMap<>();
    private Item currentItem = new Item();
    public AdminBean(){}
    public AdminBean(String itemNumber){this.itemNumber = itemNumber;}
    public AdminBean(String type, String price, String name,
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
    public String getKey(){return getItemNumber();}
    public void setKey(String key){setItemNumber(key);}
    public String getType(){return type;}
    public void setType(String t){this.type = t;}
    public String getPrice(){return price;}
    public void setPrice(String price){this.price = price;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String getPic_ref(){return pic_ref;}
    public void setPic_ref(String pic_ref){this.pic_ref = pic_ref;}
    public String getQuantity(){return quantity;}
    public void setQuantity(String quantity){this.quantity = quantity;}
    public Map<String, String> getAllItemNo(){return allItemNo;}
    public String getChoice(){return choice;}
    public void setChoice(String choice){this.choice = choice;}
    //</editor-fold>
    
    public Collection<Item> findAll() throws SQLException{
        Collection<Item> allItem = Products.pfs.findAll2();
        for(Item it: allItem){allItemNo.put(it.getItemNumber(), it.getItemNumber());}
        return allItem;
    }
    
    public void valueChangeMethod(ValueChangeEvent e) throws SQLException{
        if(itemNumber != null){
            currentItem = Products.pfs.findAllArg2(itemNumber);
            this.type = currentItem.getType();
            this.price = currentItem.getPrice();
            this.name = currentItem.getName();
            this.pic_ref = currentItem.getPic_ref();
            this.quantity = currentItem.getQuantity();
            this.description = currentItem.getDescription();            
        }
    }
    
    public void saveNewInfo(){
        currentItem.setType(type);
        currentItem.setPrice(price);
        currentItem.setName(name);
        currentItem.setPic_ref(pic_ref);
        currentItem.setQuantity(quantity);
        currentItem.setDescription(description);
        currentItem.setItemNumber(itemNumber);
    }
    
    public void clear(){
        this.type = "";
        this.price = "";
        this.name = "";
        this.pic_ref = "";
        this.quantity = "";
        this.description = "";
        allItemNo = new HashMap<>();
    }
    
    public void update() throws SQLException{
        saveNewInfo();
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            Products.pfs.update(currentItem);
            context.addMessage(null, new FacesMessage("Update item# " + itemNumber + " Successful!"));
        }catch(Exception e){
            context.addMessage(null, new FacesMessage("Insert item# " + itemNumber + " Failed! " + e.getLocalizedMessage()));
        }
        clear();
    }
    
    public void insert() throws SQLException{
        saveNewInfo();
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            Products.pfs.create(currentItem);
            context.addMessage(null, new FacesMessage("Insert item# " + itemNumber + " Successful!"));
        }catch(Exception e){
            context.addMessage(null, new FacesMessage("Insert item# " + itemNumber + " Failed! " + e.getLocalizedMessage()));
        }
        clear();
    }
    
    public void delete() throws SQLException{
        saveNewInfo();
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            Products.pfs.delete(currentItem);
            context.addMessage(null, new FacesMessage("Delete item# " + itemNumber + " Successful!"));
        }catch(Exception e){
            context.addMessage(null, new FacesMessage("Delete item# " + itemNumber + " Failed! " + e.getLocalizedMessage()));
        }
        clear();
    }
    
    public void saveFile() throws SQLException, InterruptedException{
        FacesContext context = FacesContext.getCurrentInstance();
        try(InputStream input = uploadedFile.getInputStream()){
            String fileName = uploadedFile.getSubmittedFileName();
            Files.copy(input, new File("C:/School/Semester4/WebApp/mp04/web/resources/files", fileName).toPath());
            Products.uploadPNG(pngTitle, fileName, "C:/School/Semester4/WebApp/mp04/web/resources/files");
            context.addMessage(null, new FacesMessage("Upload Successful"));
            Thread.sleep(5000);
            File myObj = new File ("C:/School/Semester4/WebApp/mp04/web/resources/files/" + fileName);
            if(myObj.delete()){
                System.out.println("Deleted the file: " + fileName + LocalDate.now());
            }else{
                System.out.println("Failed to delete the file.");
            }
        }
        catch ( IOException ex ) {
            isSuccess = false;
            context.addMessage(null, new FacesMessage("Upload failed..."));
        }
                 
    }
    
    
    
}