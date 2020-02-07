
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="row justify-content-center">
    <form action="login" method="POST">
        <div class="card" style="width: 24rem; margin-top: 50px ">
          <div class="card-body">
            <h5 class="card-title">Аутентификация</h5>
            <p class="card-text">${info}</p>
            <p class="card-text">
                <div class="form-group">
                   <label for="login">Логин:</label>
                   <input class="form-control" type="text" name="login" id="login">
                </div>
                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input class="form-control"  type="password" id="password" name="password">
                </div>
            </p>
            <p class="text-center"><input class="btn btn-secondary" style="width: 100%;" type="submit" value="Войти"></p>

          </div>
            <p class="text-center">Нет учетной записи? <a href="newReader">Зарегистрироваться</a></p>
        </div>

    </form>
</div>
       
    
