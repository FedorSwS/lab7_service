<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Ошибка входа</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f6f9; display: flex; justify-content: center; padding-top: 60px; }
        .box { background: #fff; padding: 28px 32px; border-radius: 8px; border-left: 5px solid #dc2626; width: 420px; }
        a { display: inline-block; margin-top: 14px; color: #1e3a5f; font-weight: bold; }
    </style>
</head>
<body>
<div class="box">
    <h2>Неверный логин или пароль</h2>
    <p>Использовано неудачных попыток входа: <strong>${sessionScope.failedLoginAttemptsCount} из 3</strong>.</p>
    <p>После 3-й неудачной попытки доступ в текущей сессии будет заблокирован.</p>
    <a href="${pageContext.request.contextPath}/login.jsp">Попробовать снова</a>
</div>
</body>
</html>
