<%-- 
    Document   : index
    Created on : Sep 19, 2019, 10:31:32 AM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Сетевая библиотека имени JPTVR17</title>
    </head>
    <body>
        <h1>Сетевая библиотека</h1>
        <p>${info}</p>
        
        <br>
        Для всех:<br>
        <c:if test="${userRole eq null}">
            <a href="showLogin">Войти</a><br>
        </c:if>
        <c:if test="${userRole ne null}">
            <a href="logout">Выйти</a><br>
            <a href="editReader">Редактировать профиль</a><br>
        </c:if>
        <a href="newReader">Регистрация</a><br>
        <br>
        <a href="listBooks">Список книг</a><br>
        
        
        <c:if test="${userRole eq 'MANAGER' || userRole eq 'ADMIN'}">
            <br>
            Для администратора:<br>
            <a href="newBook">Добавить новую книгу</a><br>
            <a href="listReaders">Список читателей</a><br>
            <a href="listAllBooks">Список всех книг</a><br>
            <a href="takeOnBooks">Список выданных книг</a><br>
            
        </c:if>
        <c:if test="${userRole eq 'ADMIN'}">
            <a href="showAdmin">Изменить роль пользователя</a><br>
        </c:if>
    </body>
</html>
