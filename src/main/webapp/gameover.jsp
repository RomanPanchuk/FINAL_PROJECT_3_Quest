<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <link href="style.css" rel="stylesheet">
    <title>Кінець ігри</title>
</head>
<body class="${sessionScope.bodyClass}">
<div class="container">

    <br><br>
    <h1>Кінець ігри</h1><br>

    <p class="game-over-message">${sessionScope.gameOverMessage}</p><br>
    <div style="display: flex; justify-content: center;">
        <a href="./game" class="btn btn-secondary btn-custom1">ПОЧАТИ ЗНОВУ</a>
        <br><br>
    </div>
    <br>
    <p><h5>Статистика:</h5>
    <p class="games-played-text"> Ім'я: ${sessionScope.playerName}</p>
    <p class="games-played-text">Ігор зіграно: ${sessionScope.gamesPlayed}</p>
</div>
</body>
</html>