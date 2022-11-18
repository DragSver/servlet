<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Регистрация</h1>
<hr>
<form method="post">
    <p><label for="email">Введите электронную почту</label></p>
    <input type="email" id="email" name="email" placeholder="Электронная почта">
    <p><label for="login">Введите логин </label></p>
    <input type="text" id="login" name="login" placeholder="Логин">
    <p><label for="firstPassword">Введите пароль</label></p>
    <input type="password" id="firstPassword" name="firstPassword" placeholder="Пароль">
    <p><label for="secondPassword">Повторите пароль</label></p>
    <input type="password" id="secondPassword" name="secondPassword" placeholder="Пароль">
    <button type="submit">Зарегистрироваться</button>
</form>
<a href="/login">Войти</a>
<p>${error}</p>
</body>
</html>
