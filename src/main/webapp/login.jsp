<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Авторизация — Лабораторная работа №7</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f6f9; display: flex; justify-content: center; padding-top: 60px; }
        .box { background: #fff; padding: 28px 32px; border-radius: 8px; box-shadow: 0 2px 12px rgba(0,0,0,0.08); width: 380px; }
        h2 { margin-top: 0; color: #1e3a5f; }
        label { display: block; margin-top: 12px; font-weight: bold; }
        input[type=text], input[type=password], select { width: 100%; padding: 8px; margin-top: 4px; box-sizing: border-box; }
        button { margin-top: 18px; width: 100%; padding: 10px; background: #1e3a5f; color: #fff; border: none; border-radius: 5px; cursor: pointer; font-size: 1rem; }
        .hint { margin-top: 16px; font-size: 0.85rem; color: #4b5563; background: #eef4fa; padding: 10px; border-radius: 5px; }
    </style>
</head>
<body>
<div class="box">
    <h2>Вход в систему</h2>
    <form action="${pageContext.request.contextPath}/login" method="POST">
        <label for="authType">Способ входа:</label>
        <select name="authType" id="authType">
            <option value="name">По имени пользователя</option>
            <option value="email">По Email</option>
        </select>

        <label for="loginValue">Логин / Email:</label>
        <input type="text" id="loginValue" name="loginValue" required placeholder="student или student@bsac.by">

        <label for="psw">Пароль:</label>
        <input type="password" id="psw" name="psw" required placeholder="Введите пароль">

        <button type="submit">Войти</button>
    </form>

    <div class="hint">
        <strong>Тестовые учётные записи:</strong><br>
        • <code>student</code> / <code>student123</code> (<code>student@bsac.by</code>)<br>
        • <code>admin</code> / <code>admin123</code> (<code>admin@bsac.by</code>)<br>
        Неудачных попыток в текущей сессии: <strong>${sessionScope.failedLoginAttemptsCount != null ? sessionScope.failedLoginAttemptsCount : 0} из 3</strong>
    </div>
</div>
</body>
</html>
