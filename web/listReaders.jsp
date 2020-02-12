<%-- 
    Document   : listReaders
    Created on : Sep 19, 2019, 10:54:35 AM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <h1>Список читателей</h1>
        <p>${info}</p>
        
        <a href="index">Главная страница</a>
        <ul>
            <c:forEach var="reader" items="${listReaders}">
                <li>
                    <div class="container" style="max-width:30rem">
                        <div class="card border-primary mb-5" style="">
                        <div class="card-header">${reader.name} ${reader.surname}</div>
                        <div class="card-body">
                        <h4 class="card-title">Номер телефона: ${reader.phone}</h4>
                        <p class="card-text">Денег в кошельке: ${reader.money}$</p>
                        <c:if test="${userRole eq 'MANAGER' || userRole eq 'ADMIN'}">
                        <a href="editReader?id=${reader.id}">Изменить</a>
                        </c:if>

                        </div>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>

