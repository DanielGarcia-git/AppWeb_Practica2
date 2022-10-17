package appwebb.practica_2.Servlets;

import appwebb.practica_2.DataLayer.ManagerDB;
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
@WebServlet(urlPatterns = {"/sigin"})
public class sigin extends HttpServlet {

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
            String email = request.getParameter("email");
            String pass = request.getParameter("password");
            String name = request.getParameter("name");
            System.out.println("Email: " + email);
            System.out.println("Name: " + name);
            System.out.println("Pass: " + pass);
            if (sesion.getAttribute("user") == null && (email == null || pass == null || name == null)) response.sendRedirect("login.jsp");
            else {
                if (email.equals("") && pass.equals("") && name.equals("")) response.sendRedirect("error.jsp");
                else {
                    ManagerDB db = ManagerDB.getInstance();
                    db.setIdDBAndCredentials("AppWebImages", "Daniel", "1234");
                    ArrayList<String> data = new ArrayList<>();
                    data.add(email);
                    data.add(pass);
                    data.add(name);
                    Boolean res = db.setData("Users", data);
                    System.out.print("[InfoLog][sigin][processRequest()] ## Resultado del guardado: " + res.toString());
                    if (!res) response.sendRedirect("error.jsp");
                    else {
                        sesion.setAttribute("user", email);
                        response.sendRedirect("login.jsp");
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("[Error][sigin][processRequest()] ## " + ex.getMessage());
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
