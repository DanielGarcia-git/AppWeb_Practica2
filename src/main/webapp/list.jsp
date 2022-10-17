<%-- 
    Document   : list
    Created on : 02-oct-2022, 17:16:54
    Author     : Daniel Garcia Estevez
--%>

<%@page import="appwebb.practica_2.Modelos.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="appwebb.practica_2.DataLayer.ManagerDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession sesion = request.getSession();
    User actualUser = new User();
    if (sesion.getAttribute("user") == null) response.sendRedirect("login.jsp");
    else actualUser = (User) sesion.getAttribute("user");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href='CSS/TableStyle.css' type="text/css">
    </head>
    <body>
        <div class="content-table">
            <table class="table">
                <tr>
                    <th>Titulo</th>
                    <th>Descripción</th>
                    <th>Palabras clave</th>
                    <th>Autor</th>
                    <th>Creador</th>
                    <th>Fecha de creación</th>
                    <th>Fecha de alta</th>
                    <th>Modificar</th>
                    <th>Eliminar</th>
                    <th>Visualizar</th>
                </tr>
                <% 
                    ManagerDB db = ManagerDB.getInstance();
                    ArrayList options = new ArrayList();
                    options.add("0");
                    ArrayList<String> data = db.getData("Images", options);
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
                %>   
            </table>
        </div>
    </body>
</html>
