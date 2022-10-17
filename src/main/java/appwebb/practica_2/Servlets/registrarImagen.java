package appwebb.practica_2.Servlets;

import appwebb.practica_2.DataLayer.ManagerDB;
import appwebb.practica_2.Modelos.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Daniel Garcia Estevez
 */
@WebServlet(name = "registrarImagen", urlPatterns = {"/registrarImagen"})
@MultipartConfig
public class registrarImagen extends HttpServlet {

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
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String author = request.getParameter("author");
        String keyWords = request.getParameter("keyWords");
        String creationDate = request.getParameter("creationDate");
        
        System.out.println("Title: " + title);
        System.out.println("description: " + description);
        System.out.println("author: " + author);
        System.out.println("keyWords: " + keyWords);
        System.out.println("creationDate: " + creationDate);
        
        
        // Solicitamos el archivo y guardamos
        Part filePart = request.getPart("imageFile");
        String path = request.getServletContext().getInitParameter("imagePath");
        String imageFile = getFileName(filePart);
        System.out.println("imageFile: " + imageFile);

        OutputStream writer = null;
        InputStream reader = null;

        try {
            File file = new File(path + File.separator + imageFile); 
            writer = new FileOutputStream(file);
            reader = filePart.getInputStream();

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = reader.read(bytes)) != -1) writer.write(bytes, 0, read);
            System.err.println("[InfoLog][registrarImagen][processRequest()] ## El archivo " + imageFile + " ha sido creado correctamente.");
        } catch (FileNotFoundException ex) {
            System.err.println("[Error][registrarImagen][processRequest()] ## " + ex.getMessage());
        } finally {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
        }

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
                User actualUser = (User) sesion.getAttribute("user");

                // Cogemos la fecha actual
                DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                // Cambiamos el formato de la fecha
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date d = new Date();
                try {
                    d = format.parse(creationDate);
                } catch (ParseException ex) {
                    System.out.println("[Error][resgistrarImagen][processRequest()] ## " + ex.getMessage());
                }
                format.applyPattern("dd/MM/yyyy");
                String newCreationDate = format.format(d);

                // Definimos los datos a guardar
                ArrayList<String> options = new ArrayList<>();
                options.add(title);
                options.add(description);
                options.add(keyWords);
                options.add(author);
                options.add(actualUser.getUserEmail());
                options.add(newCreationDate);
                options.add(dft.format(LocalDateTime.now()));
                options.add(imageFile);

                System.out.println("[InfoLog][resgistrarImagen][processRequest()] ## Resgistrando la nueva imagen con titulo: " + title);
                Boolean res = db.setData("Images", options);
                if (res) {
                    response.sendRedirect("menu.jsp");
                    System.out.println("[InfoLog][resgistrarImagen][processRequest()] ## La imagen (" + title + ") se ha registrado correctamente");
                }
                else {
                    response.sendRedirect("error.jsp");
                    System.out.println("[Error][resgistrarImagen][processRequest()] ## La imagen (" + title + ") no se ha registrado correctamente");
                }
            }
        } 
    }
    
    private String getFileName(Part part)
    {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
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
