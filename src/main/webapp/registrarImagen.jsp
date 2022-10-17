<%-- 
    Document   : registrarImagen
    Created on : 02-oct-2022, 17:06:10
    Author     : Daniel Garcia Estevez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") == null) response.sendRedirect("login.jsp");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="form">
           <form action="registrarImagen" method="POST" enctype="multipart/form-data">
                <h1 class="h1_page">Registrar Imagen</h1>
                <label for="title">Titulo</label><br>
                <input id="title" type="text" name="title" placeholder="Titulo" required/>
                <label for="description">Descripción</label><br>
                <input id="description" type="text" name="description" placeholder="Descripción"/>
                <label for="author">Autor</label><br>
                <input id="author" type="text" name="author" placeholder="Autor" />
                <label for="keyWords">Palabras clave</label><br>
                <input id="keyWords" type="text" name="keyWords" placeholder="Palabras clave" />
                <label for="creationDate">Fecha de creación</label><br>
                <input id="creationDate" type="date" name="creationDate" placeholder="Fecha de creación" required/>
                <label for="uploadImage">Subir imagen</label><br>
                <input id="uploadImage" type="file" name="imageFile" required/>
                <input type="submit" value="Registrar">
            </form> 
        </div>
    </body>
</html>
