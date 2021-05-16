<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Вход</title>
</head>
<body>
    <form action='<c:url value="/controller?cmd=login" />' method="post" >
        <label>
            Логин:
            <input name="userNickname" type="text" placeholder= "Введите логин">
        </label>
        <button>
            Войти
        </button>
    </form>
</body>
</html>
