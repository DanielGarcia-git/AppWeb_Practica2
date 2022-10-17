package appwebb.practica_2.Modelos;

import java.util.ArrayList;

/**
 *
 * @author Daniel Garcia Estevez
 */
public class User {
    
    /*************************************************************************/
    /**************************Private atributtes*****************************/
    /*************************************************************************/
    
    /**
     * El email del usuario que le identifica en el sistema.
     */
    private final String UserEmail;
    
    /**
     * El nombre del usuario.
     */
    private String UserName;
    
    /**
     * La contraseña del usuario cifrada con MD5.
     */
    private String UserPassword;
    private ArrayList<String> data;
    
    /*************************************************************************/
    /****************************Constructors*********************************/
    /*************************************************************************/
    
    /**
     * 
     */
    public User()
    {
        this.UserEmail = null;
        this.UserName = null;
        this.UserPassword = null;
    }
    
    /**
     * 
     * @param data 
     */
    public User(ArrayList<String> data)
    {
        this.UserEmail = data.get(0);
        this.UserPassword = data.get(1);
        this.UserName = data.get(2);
    }
    
    /*************************************************************************/
    /*******************************Getters***********************************/
    /*************************************************************************/
    
    /**
     * 
     * @return 
     */
    public String getUserEmail()
    {
        return this.UserEmail;
    }
    
    /**
     * 
     * @return 
     */
    public String getUserName()
    {
        return this.UserName;
    }
    
    /**
     * 
     * @return 
     */
    public String getUserPassword()
    {
        return this.UserPassword;
    }
    
    /*************************************************************************/
    /*******************************Setters***********************************/
    /*************************************************************************/
    
    /**
     * 
     * @param newName 
     */
    public void setUserName(String newName)
    {
        this.UserName = newName;
    }
    
    /**
     * 
     * @param newPassword 
     */
    public void setUserPassword(String newPassword)
    {
        this.UserPassword = newPassword;
    }
    
    @Override
    public String toString()
    {
        String s = "#########################\n";
        s = s + "Id usuario: " + this.UserEmail;
        s = s + "\nContraseña del usuario (MD5): " + this.UserPassword;
        s = s + "\nNombre del usuario: " + this.UserName;
        s = s + "\n#########################\n";
        return s;
    }
}
