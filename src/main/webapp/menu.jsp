<%-- 
    Document   : menu
    Created on : 02-oct-2022, 17:05:55
    Author     : Daniel Garcia Estevez
--%>

<%@page import="java.util.Enumeration"%>
<%@page import="appwebb.practica_2.Modelos.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession sesion = request.getSession();
    User user = null;
    if (sesion.getAttribute("user") == null) response.sendRedirect("login.jsp");
    else user = (User) sesion.getAttribute("user");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link rel="stylesheet" href="CSS/MenuStyle.css" type="text/css">
        <script>
            $(document).ready(function() {
                $("#RegisterImage").click(function() {
                    $(".wrapper-view").load("registrarImagen.jsp");
                });
                $("#ListImage").click(function() {
                    $(".wrapper-view").load("list.jsp");
                });
                $("#SearchImage").click(function() {
                    $(".wrapper-view").load("buscarImagen.jsp");
                });
            });
        </script>
    </head>
    <body>
        <div class="wrapper-menu">
            <nav>
                <ul>
                    <li><a id="RegisterImage">Registrar Imagen</a></li>
                    <li><a id="ListImage">Listar Imagenes</a></li>
                    <li><a id="SearchImage">Buscar Imagen</a></li>
                    <li><a href="logout?">Salir</a></li>
                </ul>
            </nav>
        </div>
        <div class="wrapper-view">
            <div class="titleWelcome">
                <% out.println("<h1> Bienvenido de nuevo " + user.getUserName() + "!</h1>");%>
            </div>
        </div>
        
    </body>
    
</html>
