<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <link href="style.css" rel="stylesheet">
    <title>Ігра</title>
</head>
<body class="${sessionScope.bodyClass}">
<div class="container">
    <br><br>
    <h1>${question.questionText}</h1> <br><br>
    <c:forEach var="answer" items="${question.answers}">
        <form action="${pageContext.request.contextPath}/game" method="post" class="mb-3 form-center">
            <input type="hidden" name="id" value="${question.id}"><br>
            <button class="btn btn-secondary" type="submit" name="answerId"
                    value="${answer.id}">${answer.answerText}</button>
        </form>
    </c:forEach>
    <p class="games-played-text">Ігор зіграно: ${sessionScope.gamesPlayed}</p>
</div>
</body>
</html>