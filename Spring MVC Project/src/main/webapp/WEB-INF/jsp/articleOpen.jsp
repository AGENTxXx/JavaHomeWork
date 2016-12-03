<%@ page import="ru.innopolis.models.Article" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
  <jsp:include page="head.jsp" />
  <title>Zinger LTD </title>
</head>
<body>

<jsp:include page="headerAuth.jsp" />
<div style="padding:10px;">
  <h2><c:out value="${article.title}"/></h2>
  <div>
    <%=((Article)request.getAttribute("article")).getContent()%>
  </div>
</div>
</body>
</html>
