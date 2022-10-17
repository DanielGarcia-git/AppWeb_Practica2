package appwebb.practica_2.Servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import appwebb.practica_2.DataLayer.ManagerDB;
import appwebb.practica_2.Modelos.User;
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
@WebServlet(urlPatterns = {"/login"})
public class login extends HttpServlet {

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
        HttpSession sesion = request.getSession();
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        System.out.println("Email: " + email);
        System.out.println("Pass: " + pass);
        if (sesion.getAttribute("user") == null && (email == null || pass == null)) response.sendRedirect("login.jsp");
        else {
            if (email.equals("") && pass.equals("")) response.sendRedirect("error.jsp");
            else {
                ManagerDB db = ManagerDB.getInstance();
                db.setIdDBAndCredentials("AppWebImages", "Daniel", "1234");
                // Definimos los parametros de busqueda
                ArrayList<String> options = new ArrayList<>();
                options.add(email);

                // Enviamos la solicitud y recogemos los datos
                ArrayList<String> data = db.getData("Users", options);
                User user = new User(data);
                System.out.println("[InfoLog][login][processRequest()] ## \n" + user.toString());
                if (user.getUserPassword().equals(pass) && user.getUserEmail().equals(email)) {
                    System.out.println("[InfoLog][login][processRequest()] ## Nos hemos logeado correctamente (" + user.getUserEmail() + ").");
                    sesion.setAttribute("user", user);
                    response.sendRedirect("menu.jsp");
                }
                else {
                    System.out.println("[InfoLog][login][processRequest()] ## No nos hemos logeado correctamente (" + user.getUserEmail() + ").");
                    response.sendRedirect("error.jsp");
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
