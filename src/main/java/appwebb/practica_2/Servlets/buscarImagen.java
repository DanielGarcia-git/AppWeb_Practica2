/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appwebb.practica_2.Servlets;

import appwebb.practica_2.DataLayer.ManagerDB;
import appwebb.practica_2.Modelos.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "buscarImagen", urlPatterns = {"/buscarImagen"})
public class buscarImagen extends HttpServlet {

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
            HttpSession sesion = request.getSession();
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String storage_date = request.getParameter("creationDate");

            if (sesion.getAttribute("user") == null) response.sendRedirect("login.jsp");
            else {
                if ((title == null && author == null && storage_date == null) ||
                        ("".equals(title) && author == null && storage_date == null) ||
                        ("".equals(title) && "".equals(author) && "".equals(storage_date)))
                    response.sendRedirect("menu.jsp");
                else {
                    ManagerDB db = ManagerDB.getInstance();
                    ArrayList<String> options = new ArrayList<>();
                    User actualUser = (User) sesion.getAttribute("user");

                    if (!"".equals(title)) {
                        options.add("title");
                        options.add(title);
                    }
                    if (!"".equals(author) && author != null) {
                        options.add("author");
                        options.add(author);
                    }
                    if (!"".equals(storage_date) && storage_date != null) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date d = new Date();
                        try {
                            d = format.parse(storage_date);
                        } catch (ParseException ex) {
                            System.out.println("[Error][buscarImagen][processRequest()] ## " + ex.getMessage());
                        }
                        format.applyPattern("dd/MM/yyyy");
                        String newCreationDate = format.format(d);
                        options.add("capture_date");
                        options.add(newCreationDate);
                    }

                    ArrayList<String> data = db.getData("ImagesAdvance", options);
                    
                    out.print("<html>");
                    out.print("<head>");
                    out.print("<link rel=\"stylesheet\" href='CSS/TableStyle.css' type=\"text/css\">");
                    out.print("<script>");
                    out.print("$('.ModImage').click(function() {");
                    out.print("var actualImage = $(this).attr('id');");
                    out.print("$('.wrapper-view').load('modificarImagen.jsp?image=' + actualImage);");
                    out.print("});");
                    out.print("$('.DelImage').click(function() {");
                    out.print("var actualImage = $(this).attr('id');");
                    out.print("$('.wrapper-view').load('eliminarImagen.jsp?image=' + actualImage);");
                    out.print("});");
                    out.print("$('.ButtonSee').click(function() {");
                    out.print("var actualImage = $(this).attr('id');");
                    out.print("$.post('visualizarImagen', { idImage: actualImage }, function(data) {");
                    out.print("window.open(data);");
                    out.print("});");
                    out.print("});");
                    out.print("</script>");
                    out.print("</head>");
                    
                    out.print("<body>");
                    out.print("<div class=\"content-table\">");
                    out.print("<table class=\"table\">");
                    out.print("<tr>");
                    out.print("<th>Titulo</th>");
                    out.print("<th>Descripción</th>");
                    out.print("<th>Palabras clave</th>");
                    out.print("<th>Autor</th>");
                    out.print("<th>Creador</th>");
                    out.print("<th>Fecha de creación</th>");
                    out.print("<th>Fecha de alta</th>");
                    out.print("<th>Modificar</th>");
                    out.print("<th>Eliminar</th>");
                    out.print("<th>Visualizar</th>");
                    out.print("</tr>");

                    for (int i = 0; i < data.size(); i += 9) {
                        out.print("<tr>");
                        out.print("<td>" + data.get(i + 1) + "</td>"); // Titulo
                        out.print("<td>" + data.get(i + 2) + "</td>"); // Descripcion
                        out.print("<td>" + data.get(i + 3) + "</td>"); // Palabras clave
                        out.print("<td>" + data.get(i + 4) + "</td>"); // Autor
                        out.print("<td>" + data.get(i + 5) + "</td>"); // Creador
                        out.print("<td>" + data.get(i + 6) + "</td>"); // Fecha de creacion
                        out.print("<td>" + data.get(i + 7) + "</td>"); // Fecha de alta
                        if (data.get(i + 5).equals(actualUser.getUserEmail())) {
                            out.print("<td><a class='ModImage' id='" + data.get(i) + "' href='#'>Modificar</a></td>");
                            out.print("<td><a class='DelImage' id='" + data.get(i) + "' href='#'>Eliminar</a></td>");
                        }
                        else {
                            out.print("<td><a>Modificar</a></td>");
                            out.print("<td><a>Eliminar</a></td>");
                        }
                        out.print("<td><button class='ButtonSee' id='" + data.get(i) + "' >Visualizar</button></td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</body>");
                    out.print("</html>");
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
