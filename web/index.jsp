<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <div id="content"  >
                <div class="card border-primary mb-3" style="max-width: 10rem;">
                  <div class="card-header">Header</div>
                  <div class="card-body">
                    <h4 class="card-title">Primary card title</h4>
                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                  </div>
                </div>
           </div> 
