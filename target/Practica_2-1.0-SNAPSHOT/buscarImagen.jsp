<%-- 
    Document   : buscarImagen
    Created on : 02-oct-2022, 17:17:10
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
        <link rel="StyleSheet" type="text/css" href="CSS/SearchStyle.css"/>
        <title>AppWeb Imagenes</title>
    </head>
    <body>
        <div class="content">
            <div class="form">
                <h1 class="h1_page">Buscar imagen</h1>
                <form class="simple-search" action="buscarImagen" method="POST">
                    <label for="title">Titulo</label><br>
                    <input id="title" type="text" name="title" placeholder="Titulo"/>
                    <input type="submit" value="Buscar"/>
                    <p class="message"><a href='#'> Busqueda avanzada</a></p>
                </form>
                <form class="avance-search" action="buscarImagen" method="POST">
                    <label for="title">Titulo</label><br>
                    <input id="title" type="text" name="title" placeholder="Titulo"/>
                    <label for="author">Autor</label><br>
                    <input id="author" type="text" name="author" placeholder="Autor"/>
                    <label for="creationDate">Fecha de creación</label><br>
                    <input id="creationDate" type="date" name="creationDate" placeholder="Fecha de creación"/>
                    <input type="submit" value="Buscar"/>
                    <p class="message"><a href='#'> Busqueda simple</a></p>
                </form>
            </div>
        </div>
        <script>
            $(document).ready(function() {
                $('.simple-search').submit(function(event) {
                    event.preventDefault();
                    var title = $(this).find('input[name=title]').val();
                    $.post( "buscarImagen", { title: title }, function(data) {
                        $(".wrapper-view").html(data);
                    });
                });
                
                $('.avance-search').submit(function(event) {
                    event.preventDefault();
                    var title = $(this).find('input[name=title]').val();
                    var author = $(this).find('input[name=author]').val();
                    var storage_date = $(this).find('input[name=creationDate]').val();
                    $.post( "buscarImagen", { title: title, author: author, creationDate: storage_date}, function(data) {
                        $(".wrapper-view").html(data);
                    });
                }); 
            });
            
            $('.message a').click(function(){
                $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
            });
        </script>
    </body>
</html>
