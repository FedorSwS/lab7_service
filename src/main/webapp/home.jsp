<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Личный кабинет — Вариант 4 (Session &amp; Cookies)</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f6f9; max-width: 860px; margin: 30px auto; padding: 0 16px; line-height: 1.6; }
        .card { background: #fff; padding: 20px 24px; border-radius: 8px; margin-bottom: 18px; box-shadow: 0 2px 8px rgba(0,0,0,0.06); }
        h1, h2 { color: #1e3a5f; margin-top: 0; }
        pre { background: #f3f4f6; padding: 12px; border-radius: 6px; white-space: pre-wrap; }
        button { padding: 10px 18px; background: #1e3a5f; color: #fff; border: none; border-radius: 5px; cursor: pointer; }
        .logout { float: right; color: #dc2626; text-decoration: none; font-weight: bold; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border: 1px solid #d1d5db; padding: 8px; text-align: left; }
        th { background: #eef4fa; }
    </style>
</head>
<body>
    <div class="card">
        <a class="logout" href="${pageContext.request.contextPath}/login?action=logout">Выйти</a>
        <h1>Добро пожаловать, ${sessionScope.loggedInUser}!</h1>
        <p>
            <strong>Роль:</strong> ${sessionScope.userRole} |
            <strong>Способ входа:</strong> ${sessionScope.authType} |
            <strong>Дата входа:</strong> ${sessionScope.loginDate} |
            <strong>Время работы в приложении:</strong> ${workTime}
        </p>
        <p><strong>ID сессии:</strong> <code>${pageContext.session.id}</code> (MaxInactiveInterval: ${pageContext.session.maxInactiveInterval} сек.)</p>
    </div>

    <div class="card">
        <h2>Индивидуальное задание (Вариант 4): Вопросительные предложения в Cookie</h2>
        <p>Нажмите кнопку ниже, чтобы прочитать текстовый файл <code>WEB-INF/data/sample_text.txt</code>, выделить все вопросительные предложения и сохранить их в файл Cookie (<code>question_sentences</code>).</p>

        <form action="${pageContext.request.contextPath}/extract-questions" method="POST">
            <button type="submit">Извлечь вопросительные предложения и записать в Cookie</button>
        </form>

        <c:if test="${not empty sourceText}">
            <h3>Исходный текст из файла:</h3>
            <pre><c:out value="${sourceText}"/></pre>
        </c:if>

        <c:if test="${not empty questions}">
            <h3>Найденные вопросительные предложения (записаны в Cookie):</h3>
            <ol>
                <c:forEach var="q" items="${questions}">
                    <li><c:out value="${q}"/></li>
                </c:forEach>
            </ol>
        </c:if>

        <c:if test="${not empty savedCookieDecoded}">
            <h3>Содержимое Cookie <code>question_sentences</code> (декодированное):</h3>
            <pre><c:out value="${savedCookieDecoded}"/></pre>
        </c:if>
    </div>

    <c:if test="${sessionScope.isAdmin}">
        <div class="card">
            <h2>Панель администратора — Список пользователей</h2>
            <table>
                <tr><th>Имя</th><th>Email</th><th>Роль</th><th>Последний вход</th></tr>
                <c:forEach var="p" items="${sessionScope.allPersons}">
                    <tr>
                        <td><c:out value="${p.name}"/></td>
                        <td><c:out value="${p.email}"/></td>
                        <td><c:out value="${p.role}"/></td>
                        <td><c:out value="${p.loginDate}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
</body>
</html>
