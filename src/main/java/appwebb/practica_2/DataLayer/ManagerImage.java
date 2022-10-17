package appwebb.practica_2.DataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Daniel Garcia Estevez
 */
public class ManagerImage {
    
    /*************************************************************************/
    /**************************Private atributtes*****************************/
    /*************************************************************************/
    
    /**
     * Atributo donde guardamos la unica instancia del ManagerUser.
     */
    private static ManagerImage singletonObject;
    
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
            query = "SELECT * FROM image";
            statement = connectionDB.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println("id: " + rs.getString("id"));
                System.out.println("title: " + rs.getString("title"));
                System.out.println("description: " + rs.getString("description"));
                System.out.println("keywords: " + rs.getString("keywords"));
                System.out.println("author: " + rs.getString("author"));
                System.out.println("creator: " + rs.getString("creator"));
                System.out.println("capture_date: " + rs.getString("capture_date"));
                System.out.println("storage_date: " + rs.getString("storage_date"));
                System.out.println("filename: " + rs.getString("filename"));
                System.out.println();
            }
        } catch (SQLException ex) {
            System.err.println("[Error][ManagerImage][getInfoTable()] ## " + ex.getMessage());
        }
    }
    
    /*************************************************************************/
    /***************************Static Methods********************************/
    /*************************************************************************/
    
    /**
     * Retorna la unica instancia de la clase ManagerUser.
     * @return La instancia de ManagerUser
     */
    public static ManagerImage getInstance()
    {
            if(singletonObject == null) singletonObject = new ManagerImage();
            return singletonObject;
    }
    
    /*************************************************************************/
    /****************************Constructors*********************************/
    /*************************************************************************/
    
    /**
     * La constructora por defecto de la clase ManagerUser.
     */
    private ManagerImage() 
    {
        
    }
    
    /**
     * 
     */
    public void inicializeManagerImage()
    {

    }
    
    /*************************************************************************/
    /*******************************Getters***********************************/
    /*************************************************************************/
    
    /**
     * 
     * @param idImage
     * @return 
     */
    public ArrayList<String> getData(String idImage)
    {
        System.out.println("[InfoLog][ManagerImage][getData()] ## Peticion de datos de imagen con id: " + idImage);
        ArrayList<String> l = new ArrayList<>();
        String query;
        PreparedStatement statement;
        ManagerDB db = ManagerDB.getInstance();
        Connection connectionDB = db.getConnection();
        
        try {
            ResultSet rs;
            if ("0".equals(idImage)) {
                query = "SELECT * FROM image";
                statement = connectionDB.prepareStatement(query);
                rs = statement.executeQuery();
            }
            else {
                query = "SELECT * FROM image WHERE id=?";
                statement = connectionDB.prepareStatement(query);
                statement.setString(1, idImage);
                rs = statement.executeQuery();
            }
            while (rs.next()) {
                l.add(rs.getString("id"));
                l.add(rs.getString("title"));
                l.add(rs.getString("description"));
                l.add(rs.getString("keywords"));
                l.add(rs.getString("author"));
                l.add(rs.getString("creator"));
                l.add(rs.getString("capture_date"));
                l.add(rs.getString("storage_date"));
                l.add(rs.getString("filename"));
            }
            
        } catch (SQLException ex) {
            System.err.println("[Error][ManagerImage][getData()] ## " + ex.getMessage());
        }
        System.out.println("[InfoLog][ManagerImage][getData()] ## Devolvemos los siguientes datos: " + l.toString());
        
        return l;
    }
    
    /**
     * 
     * @param options
     * @return 
     */
    public ArrayList<String> getDataAdvance(ArrayList<String> options)
    {
        System.out.println("[InfoLog][ManagerImage][getDataAdvance()] ## Peticion de datos de imagen con los siguientes criterios: " + options.toString());
        ArrayList<String> l = new ArrayList<>();
        String query;
        PreparedStatement statement;
        ManagerDB db = ManagerDB.getInstance();
        Connection connectionDB = db.getConnection();
        
        try {
            ResultSet rs;
            query = "SELECT * FROM image WHERE " + options.get(0) + " = ?";
            for (int i = 2; i < options.size(); i += 2) query = query + " AND " + options.get(i) + " = ?";
            statement = connectionDB.prepareStatement(query);
            for (int i = 1; i < (options.size() / 2) + 1; ++i) {
                if (i % 2 == 0) statement.setString(i, options.get(i + 1));
                else statement.setString(i, options.get(i));
            }
            rs = statement.executeQuery();
            while (rs.next()) {
                l.add(rs.getString("id"));
                l.add(rs.getString("title"));
                l.add(rs.getString("description"));
                l.add(rs.getString("keywords"));
                l.add(rs.getString("author"));
                l.add(rs.getString("creator"));
                l.add(rs.getString("capture_date"));
                l.add(rs.getString("storage_date"));
                l.add(rs.getString("filename"));
            }
            
        } catch (SQLException ex) {
            System.err.println("[Error][ManagerImage][getDataAdvance()] ## " + ex.getMessage());
        }
        System.out.println("[InfoLog][ManagerImage][getDataAdvance()] ## Devolvemos los siguientes datos: " + l.toString());
        
        return l;
    }
    
    /*************************************************************************/
    /*******************************Setters***********************************/
    /*************************************************************************/
    
    /**
     * 
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param creator
     * @param capture_date
     * @param storage_date
     * @param filename
     * @return 
     */
    public Boolean setData(String title, String description, 
                            String keywords, String author, String creator, 
                            String capture_date, String storage_date, String filename)
    {
        System.out.println("[InfoLog][ManagerImage][setData()] ## Peticion de guardado para la nuva imagen con titulo: " + title);
        
        Boolean res = true;
        ArrayList<String> l = new ArrayList<>();
        String query;
        PreparedStatement statement;
        ManagerDB db = ManagerDB.getInstance();
        Connection connectionDB = db.getConnection();
        try { 
            query = "INSERT INTO image (title, description, keywords, author, creator, capture_date, storage_date, filename) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connectionDB.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, keywords);
            statement.setString(4, author);
            statement.setString(5, creator);
            statement.setString(6, capture_date);
            statement.setString(7, storage_date);
            statement.setString(8, filename);
            statement.executeUpdate();
        } catch (SQLException ex) {
            res = false;
            System.err.println("[Error][ManagerImage][setData()] ## " + ex.getMessage());
        }
        System.out.println("[InfoLog][ManagerImage][setData()] ## Estado de la base de datos: ");
        this.getInfoTable();
        return res;
    }
    
    /**
     * 
     * @param idImage
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param creator
     * @param capture_date
     * @param storage_date
     * @param filename
     * @return 
     */
    public Boolean updateData(String idImage, String title, String description, 
                            String keywords, String author, String creator, 
                            String capture_date, String storage_date, String filename)
    {
        System.out.println("[InfoLog][ManagerImage][updateData()] ## Peticion de actualizacion para la imagen con id: " + idImage);
        
        Boolean res = true;
        String query;
        PreparedStatement statement;
        ManagerDB db = ManagerDB.getInstance();
        Connection connectionDB = db.getConnection();
        try { 
            query = "UPDATE image SET title = ?, description = ?, keywords = ?, author = ?, creator = ?, capture_date = ?, storage_date = ?, filename = ? WHERE id = ?";
            statement = connectionDB.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, keywords);
            statement.setString(4, author);
            statement.setString(5, creator);
            statement.setString(6, capture_date);
            statement.setString(7, storage_date);
            statement.setString(8, filename);
            statement.setString(9, idImage);
            statement.executeUpdate();
        } catch (SQLException ex) {
            res = false;
            System.err.println("[Error][ManagerImage][updateData()] ## " + ex.getMessage());
        }
        System.out.println("[InfoLog][ManagerImage][updateData()] ## Estado de la base de datos: ");
        this.getInfoTable();
        return res;
    }
    
    /**
     * 
     * @param idImage
     * @return 
     */
    public Boolean deleteData(String idImage)
    {
        System.out.println("[InfoLog][ManagerImage][deleteData()] ## Peticion de borrado para la imagen con id: " + idImage);
        
        Boolean res = true;
        String query;
        PreparedStatement statement;
        ManagerDB db = ManagerDB.getInstance();
        Connection connectionDB = db.getConnection();
        try { 
            query = "DELETE FROM image WHERE id = ?";
            statement = connectionDB.prepareStatement(query);
            statement.setString(1, idImage);
            statement.executeUpdate();
        } catch (SQLException ex) {
            res = false;
            System.err.println("[Error][ManagerImage][deleteData()] ## " + ex.getMessage());
        }
        System.out.println("[InfoLog][ManagerImage][deleteData()] ## Estado de la base de datos: ");
        this.getInfoTable();
        return res;
    }
}
