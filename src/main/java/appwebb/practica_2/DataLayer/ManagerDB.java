package appwebb.practica_2.DataLayer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Daniel Garcia Estevez
 */
public class ManagerDB {
    
    /*************************************************************************/
    /**************************Private atributtes*****************************/
    /*************************************************************************/
    
    /**
     * Atributo donde guardamos la unica instancia del ManagerDB.
     */
    private static ManagerDB singletonObject;
    
    /**
     * 
     */
    private ManagerUser managerUser;
    
    /**
     * 
     */
    private ManagerImage managerImage;
    
    /**
     * Guardamos la conexion a la base de datos.
     */
    private Connection connection;
    
    /**
     * Id de la base de datos a controlar.
     */
    private String idDB;
    
    /**
     * Usuario para acceder a la base de datos.
     */
    private String userDB;
    
    /**
     * Contraseña para acceder a la base de datos.
     */
    private String passDB;
    
    /*************************************************************************/
    /****************************Private methods******************************/
    /*************************************************************************/
    
    /**
     * Inicializa las estructuras relacionadas con el control de las bases
     * de datos.
     */
    private void inicializeManagerDB()
    {
        this.createDirImages();
        this.managerUser = ManagerUser.getInstance();
        this.managerImage = ManagerImage.getInstance();   
        this.managerUser.inicializeManagerUser();
        this.managerImage.inicializeManagerImage();
    }
    
    /**
     * 
     */
    private void createDirImages()
    {
        File dir = new File("/var/webapp/practica2/images");
        if (!dir.exists()) {
            if (dir.mkdirs()) System.out.println("[InfoLog][ManagerDB][createDirImages()] ## Directorios creados correctamente.");
            else System.out.println("[Error][ManagerDB][createDirImages()] ## Directorios no se han creado correctamente.");
        }
        else System.out.println("[InfoLog][ManagerDB][createDirImages()] ## Los directorios ya existen.");
    }
    
    /*************************************************************************/
    /***************************Static Methods********************************/
    /*************************************************************************/
    
    /**
     * Retorna la unica instancia de la clase ManagerDB.
     * @return La instancia de ManagerDB
     */
    public static ManagerDB getInstance()
    {
            if(singletonObject == null) singletonObject = new ManagerDB();
            return singletonObject;
    }
    
    /*************************************************************************/
    /****************************Constructors*********************************/
    /*************************************************************************/
    
    /**
     * La constructora por defecto de la clase ManagerDB.
     */
    private ManagerDB() 
    {
        this.idDB = null;
        this.passDB = null;
        this.userDB = null;
        this.connection = null;
    }
    
    /*************************************************************************/
    /*******************************Actions***********************************/
    /*************************************************************************/
    
    /**
     * Se conecta a la base de datos especifica en this.idDB, en casa de que la
     * connexion no se haya realizado con exito devuelve True, false en caso 
     * contrario.
     * @return Devuelve cierto si la connection a sido exitosa, falso en caso 
     * contrario.
     */
    public final Boolean conectDB()
    {
        System.out.println("[InfoLog][ManagerDB][conectDB()] ## Nos conectamos a la base de datos (" + this.idDB + ").");
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            this.connection = DriverManager.getConnection("jdbc:derby://localhost:1527/" + this.idDB + ";user=" + this.userDB + ";password=" + this.passDB);
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                if (this.connection == null) this.connection.close();
            } catch(SQLException ex) {
                System.err.println("[Error][ManagerDB][conectDB()] ## " + ex.getMessage());
            }
        }
        return (this.connection != null);
    }
    
    /*************************************************************************/
    /*******************************Getters***********************************/
    /*************************************************************************/
    
    /**
     * Devuelve la conection a la base de datos, este valor puede ser null.
     * @return Devuelve la conection a la base de datos.
     */
    public Connection getConnection()
    {
        return this.connection;
    }
    
    /**
     * 
     * @param table
     * @param options
     * @return 
     */
    public ArrayList<String> getData(String table, ArrayList<String> options)
    {
        System.out.println("[InfoLog][ManagerDB][getData()] ## Nueva solicitud de datos (" + table + ") con las opciones de: " + options.toString());
        ArrayList<String> l = new ArrayList<>();
        switch(table) {
            case "Users":
                l = this.managerUser.getData(options.get(0));
                break;
            case "Images":
                l = this.managerImage.getData(options.get(0));
                break;
            case "ImagesAdvance":
                l = this.managerImage.getDataAdvance(options);
                break;
            default:
                System.err.println("[Error][ManagerDB][getData()] ## El tipo de dato solicitado no existe.");
                break;
        }
        System.out.println("[InfoLog][ManagerDB][getData()] ## Solicitud de datos procesada: " + l.toString());
        return l;
    }
    
    /*************************************************************************/
    /*******************************Setters***********************************/
    /*************************************************************************/
    
    /**
     * Define la base de datos del cual sera Manager y las credenciales nece
     * sarias para conectarse. Acto seguido se conecta a la base de datos e
     * inicializa las estructuras de datos.
     * @param idDB Nombre de la base de datos. Diferente de null.
     * @param user Usuario de conection a la base de datos. Diferente de null.
     * @param password Contraseña de conection a la base de datos.Diferente de null.
     */
    public void setIdDBAndCredentials(String idDB, String user, String password)
    {
        this.idDB = idDB;
        this.userDB = user;
        this.passDB = password;
        int count = 0;
        while (count < 5 && !this.conectDB()) {
            ++count;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                System.err.println("[Error][ManagerDB][setIdDBAndCredentials()] ## " + ex.getMessage());
            }
        }
        this.inicializeManagerDB();
    }
    
    /**
     * 
     * @param table
     * @param data 
     * @return  
     */
    public Boolean setData(String table, ArrayList<String> data)
    {
        Boolean res = false;
        switch(table) {
            case "Users":
                res = this.managerUser.setData(data.get(0), data.get(1), data.get(2));
                break;
            case "Images":
                res = this.managerImage.setData(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4), data.get(5), data.get(6), data.get(7));
                break;
            default:
                System.err.println("[Error][ManagerDB][setData()] ## No se ha podido guardar correctamente porque el tipo de datos no existe.");
                break;
        }
        
        return res;
    }
    
    /**
     * 
     * @param table
     * @param data
     * @return 
     */
    public Boolean updateData(String table, ArrayList<String> data) 
    {
        Boolean res = false;
        switch(table) {
            case "Images":
                res = this.managerImage.updateData(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4), data.get(5), data.get(6), data.get(7), data.get(8));
                break;
            default:
                System.err.println("[Error][ManagerDB][updateData()] ## No se ha podido actualizar correctamente porque el tipo de datos no existe.");
                break;
        }
        
        return res;
    }
    
    /**
     * 
     * @param table
     * @param options
     * @return 
     */
    public Boolean deleteData(String table, ArrayList<String> options)
    {
        Boolean res = false;
        switch(table) {
            case "Images":
                res = this.managerImage.deleteData(options.get(0));
                break;
            default:
                System.err.println("[Error][ManagerDB][deleteData()] ## No se ha podido borrar correctamente porque el tipo de datos no existe.");
                break;
        }
        return res;
    }
}
