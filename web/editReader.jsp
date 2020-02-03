<%-- 
    Document   : newReader
    Created on : Sep 19, 2019, 10:53:23 AM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Изменения читателя</title>
    </head>
    <body>
        <h1>Изменение читателя</h1>
        <a href="index">Главная страница</a>
        <p>${info}</p>
        <form action="changeReader" method="POST">
            <input type="hidden" name="id" value="${reader.id}">
            Имя: <input type="text" name="name" value="${reader.name}"><br>
            Фамилия: <input type="text" name="surname" value="${reader.surname}"><br>
            Телефон: <input type="text" name="phone" value="${reader.phone}"><br>
            Деньги: <input type="text" name="money" value="${reader.money}"><br>
            Логин: <input type="text" name="login" value="${changeUser.login}"><br>
            Пароль: <input type="password" name="password1" value=""><br>
            Повторите пароль: <input type="password" name="password2" value=""><br>
            <input type="submit" value="Изменить данные читателя">
        </form>
                
    </body>
</html>
