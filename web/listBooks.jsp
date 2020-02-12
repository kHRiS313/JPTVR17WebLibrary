<%-- 
    Document   : listBooks
    Created on : Sep 19, 2019, 10:54:00 AM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <h1>Список книг библиотеки</h1>
        <p>${info}</p>
        
        <a href="index">Главная страница</a>
        <ul>
                            <c:forEach var="book" items="${listBooks}">
                                <div id="content" class="form-horizontal">
                <div class="card border-primary mb-3" style="max-width: 20rem;">
                <div class="card-header">${book.name}</div>
                <div class="card-body">
                <h4 class="card-title">${book.author} ${book.publishedYear} г.</h4>
                <p class="card-text">Количество книг в наличии: ${book.countInLibrary}</p>
                <c:if test="${userRole eq 'MANAGER' || userRole eq 'ADMIN'}">
                <a href="editBook?id=${book.id}">Изменить</a><br>
                </c:if>
                <c:if test="${userRole eq 'USER' || userRole eq 'MANAGER' || userRole eq 'ADMIN'}">
                <a href="doTakeBook?bookId=${book.id}">Почитать (за деньги)</a>
                </c:if>
                </div>
                </div>
                </div>
            </c:forEach>
        </ul>
