<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Authorization</title>
</head>
<body>
<h1>Вход</h1>
<hr>
<form method="post">
    <p><label for="login">Введите логин </label></p>
    <input type="text" id="login" name="login" placeholder="Логин">
    <p><label for="password">Введите пароль</label></p>
    <input type="password" id="password" name="password" placeholder="Пароль">
    <button type="submit">Войти</button>
</form>
<a href="/registration">Зарегистрироваться</a>
<p>${error}</p>
</body>
</html>
