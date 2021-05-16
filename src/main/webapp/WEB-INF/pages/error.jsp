<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ошибка</title>
</head>
<body>
    <p>
        <c:out value="${errorMessage}" default="Что-то пошло не так" />
    </p>
    <a href='<c:url value="/" />'>На главную</a>
</body>
</html>