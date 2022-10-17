<%-- 
    Document   : modificarImagen
    Created on : 02-oct-2022, 17:16:12
    Author     : Daniel Garcia Estevez
--%>

<%@page import="appwebb.practica_2.Modelos.Image"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="appwebb.practica_2.DataLayer.ManagerDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") == null) response.sendRedirect("login.jsp");
    
    String idImage = request.getParameter("image");
    sesion.setAttribute("idImage", idImage);
    ManagerDB db = ManagerDB.getInstance();
    ArrayList options = new ArrayList();
    options.add(idImage);
    ArrayList<String> data = db.getData("Images", options);
    Image image = new Image(data);
    String title = image.getTitle();
    String description = image.getDescription();
    String keywords = image.getKeywords();
    String author = image.getAuthor();
    String storage_date = image.getRegisterDate();
    
    // Ponemos el formato correcto de la fecha para que nos identifique el campo
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Date d = new Date();
    try {
        d = format.parse(storage_date);
    } catch (ParseException ex) {
        System.out.println("[Error][modificarImagen.jsp][jsp()] ## Error parseando la fecha.");
    }
    format.applyPattern("yyyy-MM-dd");
    String newStorage_date = format.format(d);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="form">
           <form action="modificarImagen" method="POST">
                <h1 class="h1_page">Modificar imagen</h1>
                <label for="title">Titulo</label><br>
                <input id="title" type="text" name="title" placeholder="Titulo" value="<%= title %>" required/>
                <label for="description">Descripción</label><br>
                <input id="description" type="text" name="description" placeholder="Descripción" value="<%= description %>"/>
                <label for="author">Autor</label><br>
                <input id="author" type="text" name="author" placeholder="Autor" value="<%= author %>"/>
                <label for="keyWords">Palabras clave</label><br>
                <input id="keyWords" type="text" name="keyWords" placeholder="Palabras clave" value="<%= keywords %>"/>
                <label for="creationDate">Fecha de creación</label><br>
                <input id="creationDate" type="date" name="creationDate" placeholder="Fecha de creación" value='<%= newStorage_date %>' required/>
                <label for="uploadImage">Subir imagen</label><br>
                <input id="uploadImage" type="file" name="imageFile" required/>
                <input type="submit" value="Modificar">
            </form> 
        </div>
    </body>
</html>
