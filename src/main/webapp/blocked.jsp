<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Login failed — Доступ заблокирован</title>
    <style>
        body { font-family: Arial, sans-serif; background: #fef2f2; display: flex; justify-content: center; padding-top: 60px; }
        .box { background: #fff; padding: 28px 32px; border-radius: 8px; border: 2px solid #dc2626; width: 460px; }
        h2 { color: #b91c1c; margin-top: 0; }
    </style>
</head>
<body>
<div class="box">
    <h2>Доступ заблокирован</h2>
    <p>You are blocked since you tried to type incorrect login or password 3 times.</p>
    <p><strong>Blocked date:</strong> ${blockedDate}</p>
    <p><strong>Blocked time:</strong> ${blockedTime}</p>
</div>
</body>
</html>
