/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appwebb.practica_2.Servlets;

import appwebb.practica_2.DataLayer.ManagerDB;
import appwebb.practica_2.Modelos.Image;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dani_
 */
@WebServlet(name = "modificarImagen", urlPatterns = {"/modificarImagen"})
public class modificarImagen extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String author = request.getParameter("author");
            String keyWords = request.getParameter("keyWords");
            String creationDate = request.getParameter("creationDate");
            String imageFile = request.getParameter("imageFile");
            System.out.println("Title: " + title);
            System.out.println("description: " + description);
            System.out.println("author: " + author);
            System.out.println("keyWords: " + keyWords);
            System.out.println("creationDate: " + creationDate);
            System.out.println("imageFile: " + imageFile);
            
            // Consultamos cual es el usuario actual  
            HttpSession sesion = request.getSession();
            
            if (sesion.getAttribute("user") == null && 
                    (title == null || description == null || author == null 
                    || keyWords == null || creationDate == null || imageFile == null))
                response.sendRedirect("menu.jsp");
            else {
                if ("".equals(title) && "".equals(imageFile)) response.sendRedirect("error.jsp");
                else {
                    ManagerDB db = ManagerDB.getInstance();
                    String idImage = (String) sesion.getAttribute("idImage");
                    // Cogemos los datos anteriores de la antigua imagen
                    ArrayList<String> options = new ArrayList<>();
                    options.add(idImage);
                    ArrayList<String> oldData = db.getData("Images", options);
                    Image oldImage = new Image(oldData);
                    
                    // Definimos los datos a guardar
                    options.clear();
                    options.add(idImage);
                    options.add(title);
                    options.add(description);
                    options.add(keyWords);
                    options.add(author);
                    options.add(oldImage.getCreator());
                    options.add(creationDate);
                    options.add(oldImage.getRegisterDate());
                    options.add(imageFile);
                    
                    System.out.println("[InfoLog][modificarImagen][processRequest()] ## Modificando la nueva imagen con id: " + idImage);
                    Boolean res = db.updateData("Images", options);
                    if (res) {
                        response.sendRedirect("menu.jsp");
                        System.out.println("[InfoLog][modificarImagen][processRequest()] ## La imagen (" + idImage + ") se ha modificado correctamente");
                    }
                    else {
                        response.sendRedirect("error.jsp");
                        System.out.println("[Error][modificarImagen][processRequest()] ## La imagen (" + idImage + ") no se ha modificado correctamente");
                    }
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
