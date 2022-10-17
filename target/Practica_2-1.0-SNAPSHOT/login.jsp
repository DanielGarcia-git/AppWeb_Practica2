<%-- 
    Document   : login
    Created on : 02-oct-2022, 17:05:38
    Author     : Daniel Garcia Estevez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null) response.sendRedirect("menu.jsp");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href='CSS/LoginStyle.css' type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="JS/md5.js"></script>
        <title>AppWeb Imagenes</title>
    </head>
    <body>
        <div class="login-page">
            <div class="form">
                <h1>Practica 2</h1>
                <form class="sign-in">
                    <input name="email" type="email" placeholder="Correo electronico" required/>
                    <input name="name" type="text" placeholder="Nombre" required/>
                    <input name="password" type="password" placeholder="Introduce la nueva contraseña" required/>
                    <input name="passwordR" type="password" placeholder="Repita la contraseña" required/>
                    <input type="submit" value="Crear"/>
                    <p class="message">¿Ya registrado? <a href='#'> Inicia sesion</a></p>
                </form>
                <form class="log-in">
                    <input name="email" type="email" placeholder="Correo electronico" required/>
                    <input name="password" type="password" placeholder="Contraseña" required/>
                    <input type="submit" value="Iniciar sesion"/>
                    <p class="message">¿No estas registrado? <a href='#'> Crear una cuenta</a></p>
                </form>
            </div>
        </div>
        <script>
            var data = [];
            $(document).ready(function() {
                $('.log-in').submit(function(event) {
                    event.preventDefault();
                    var pass = CryptoJS.MD5($(this).find('input[name=password]').val());
                    var pass = pass + "";
                    var user = $(this).find('input[name=email]').val();
                    $.post( "login", { password: pass, email: user }, function() {
                        window.location.replace("menu.jsp");
                    });
                });
                
                $('.sign-in').submit(function(event) {
                    event.preventDefault();
                    if ($(this).find('input[name=password]').val() !== $(this).find('input[name=passwordR]').val()) {
                        alert("Las contraseñas no coinciden.");
                    }
                    else {
                        var pass = CryptoJS.MD5($(this).find('input[name=password]').val());
                        var pass = pass + "";
                        var user = $(this).find('input[name=email]').val();
                        var name = $(this).find('input[name=name]').val();
                        $.post( "sigin", { password: pass, email: user, name: name}, function() {
                            window.location.replace("menu.jsp");
                        });
                    }
                }); 
            });
            
            $('.message a').click(function(){
                $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
            });
        </script>
    </body>
</html>
