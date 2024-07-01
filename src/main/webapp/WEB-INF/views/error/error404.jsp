<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404 Error</title>
<c:set var="path" value="${pageContext.servletContext.contextPath}"/>
</head>
<body>
<h1>404: 페이지를 찾을 수 없지 말입니다.</h1>
<img src="${path}/resources/icon/yangxgeon.png" alt="yangxgeon">
</body>
</html>