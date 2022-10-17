package appwebb.practica_2.DataLayer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Daniel Garcia Estevez
 */
public class ManagerUser {
    
    /*************************************************************************/
    /**************************Private atributtes*****************************/
    /*************************************************************************/
    
    /**
     * Atributo donde guardamos la unica instancia del ManagerUser.
     */
    private static ManagerUser singletonObject;
    
    /*************************************************************************/
    /*****************************Private methods*****************************/
    /*************************************************************************/
    
    private void getInfoTable()
    {
        String query;
        PreparedStatement statement;
        ManagerDB db = ManagerDB.getInstance();
        Connection connectionDB = db.getConnection();
        try {
            query = "SELECT * FROM users";
            statement = connectionDB.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println("id_user: " + rs.getString("id_user"));
                System.out.println("password: " + rs.getString("password"));
                System.out.println("name: " + rs.getString("name"));
                System.out.println();
            }
        } catch (SQLException ex) {
            System.err.println("[Error][ManagerUser][getInfoTable()] ## " + ex.getMessage());
        }
    }
    
    /*************************************************************************/
    /***************************Static Methods********************************/
    /*************************************************************************/
    
    /**
     * Retorna la unica instancia de la clase ManagerUser.
     * @return La instancia de ManagerUser
     */
    public static ManagerUser getInstance()
    {
            if(singletonObject == null) singletonObject = new ManagerUser();
            return singletonObject;
    }
    
    /*************************************************************************/
    /****************************Constructors*********************************/
    /*************************************************************************/
    
    /**
     * La constructora por defecto de la clase ManagerUser.
     */
    private ManagerUser() 
    {
        
    }
    
    /**
     * 
     */
    public void inicializeManagerUser()
    {
        
    }
    
    /*************************************************************************/
    /*******************************Getters***********************************/
    /*************************************************************************/
    
    /**
     * 
     * @param idUser
     * @return 
     */
    public ArrayList<String> getData(String idUser)
    {
        System.out.println("[InfoLog][ManagerUser][getData()] ## Peticion de datos de usuario con id: " + idUser);
        ArrayList<String> l = new ArrayList<>();
        String query;
        PreparedStatement statement;
        ManagerDB db = ManagerDB.getInstance();
        Connection connectionDB = db.getConnection();
        try {
            query = "SELECT * FROM users WHERE id_user=?";
            statement = connectionDB.prepareStatement(query);
            statement.setString(1, idUser);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                l.add(rs.getString("id_user"));
                l.add(rs.getString("password"));
                l.add(rs.getString("name"));
            }
        } catch (SQLException ex) {
            System.err.println("[Error][ManagerUser][getData()] ## " + ex.getMessage());
        }
        System.out.println("[InfoLog][ManagerUser][getData()] ## Devolvemos los siguientes datos: " + l.toString());

        return l;
    }
    
    /*************************************************************************/
    /*******************************Setters***********************************/
    /*************************************************************************/
    
    /**
     * 
     * @param idUser
     * @param passUser
     * @param nameUser 
     * @return  
     */
    public Boolean setData(String idUser, String passUser, String nameUser)
    {
        System.out.println("[InfoLog][ManagerUser][getData()] ## Peticion de guardado para el nuevo usuario con id: " + idUser);
        
        Boolean res = true;
        ArrayList<String> l = new ArrayList<>();
        String query;
        PreparedStatement statement;
        ManagerDB db = ManagerDB.getInstance();
        Connection connectionDB = db.getConnection();
        try { 
            query = "INSERT INTO users (id_user, password, name) VALUES(?, ?, ?)";
            statement = connectionDB.prepareStatement(query);
            statement.setString(1, idUser);
            statement.setString(2, passUser);
            statement.setString(3, nameUser);
            statement.executeUpdate();
        } catch (SQLException ex) {
            res = false;
            System.err.println("[Error][ManagerUser][setData()] ## " + ex.getMessage());
        }
        System.out.println("[InfoLog][ManagerUser][getData()] ## Estado de la base de datos: ");
        this.getInfoTable();
        return res;
    }
}
