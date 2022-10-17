package appwebb.practica_2.Modelos;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Daniel Garcia Estevez
 */
public class Image {
    
    /*************************************************************************/
    /**************************Private atributtes*****************************/
    /*************************************************************************/
    
    /**
     * Identificador que identifica a la imagen.
     */
    private final String IdImagen;
    
    /**
     * Titulo de la imagen.
     */
    private String Title;
    
    /**
     * Descripcion de la imagen.
     */
    private String Description;
    
    /**
     * 
     */
    private String Keywords;
    
    /**
     * 
     */
    private String Author;
    
    /**
     * 
     */
    private final String Creator;
    
    /**
     * 
     */
    private String CreationDate;
    
    /**
     * 
     */
    private final String RegisterDate;
    
    /**
     * 
     */
    private String FileName;

    
    /*************************************************************************/
    /****************************Constructors*********************************/
    /*************************************************************************/
    
    /**
     * 
     */
    public Image()
    {
        this.Author = null;
        this.Creator = null;
        this.FileName = null;
        this.RegisterDate = null;
        this.CreationDate = null;
        this.Keywords = null;
        this.Description = null;
        this.Title = null;
        this.IdImagen = null;
    }
    
    /**
     * 
     * @param data 
     */
    public Image(ArrayList<String> data)
    {
        this.Author = data.get(4);
        this.Creator = data.get(5);
        this.FileName = data.get(8);
        this.RegisterDate = data.get(7);
        this.CreationDate = data.get(6);
        this.Keywords = data.get(3);
        this.Description = data.get(2);
        this.Title = data.get(1);
        this.IdImagen = data.get(0);
    }
    
    /*************************************************************************/
    /*******************************Getters***********************************/
    /*************************************************************************/
    
    /**
     * 
     * @return 
     */
    public String getAuthor()
    {
        return this.Author;
    }
    
    /**
     * 
     * @return 
     */
    public String getCreationDate()
    {
        return this.CreationDate;
    }
    
    /**
     * 
     * @return 
     */
    public String getCreator()
    {
        return this.Creator;
    }
    
    /**
     * 
     * @return 
     */
    public String getDescription()
    {
        return this.Description;
    }
    
    /**
     * 
     * @return 
     */
    public String getFileName()
    {
        return this.FileName;
    }
    
    /**
     * 
     * @return 
     */
    public String getID()
    {
        return this.IdImagen;
    }
  
    /**
     * 
     * @return 
     */
    public String getKeywords()
    {
        return this.Keywords;
    }
    
    /**
     * 
     * @return 
     */
    public String getRegisterDate()
    {
        return this.RegisterDate;
    }
    
    /**
     * 
     * @return 
     */
    public String getTitle()
    {
        return this.Title;
    }
    
    /*************************************************************************/
    /*******************************Setters***********************************/
    /*************************************************************************/
    
    /**
     * 
     * @param newAuthor 
     */
    public void setAuthor(String newAuthor)
    {
        this.Author = newAuthor;
    }
    
    /**
     * 
     * @param newCreationDate 
     */
    public void setCreationDate(String newCreationDate)
    {
        this.CreationDate = newCreationDate;
    }
    
    /**
     * 
     * @param newDescription 
     */
    public void setDescription(String newDescription)
    {
        this.Description = newDescription;
    }
    
    /**
     * 
     * @param newFileName 
     */
    public void setFileName(String newFileName)
    {
        this.FileName = newFileName;
    }
    
    /**
     * 
     * @param newKeywords 
     */
    public void setKeywords(String newKeywords)
    {
        this.Keywords = newKeywords;
    }
    
    /**
     * 
     * @param newTitle 
     */
    public void setTitle(String newTitle)
    {
        this.Title = newTitle;
    }
}
