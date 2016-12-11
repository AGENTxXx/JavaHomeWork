<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.innopolis.models.Article" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.innopolis.models.User" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 23.11.2016
  Time: 0:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html lang="ru">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <jsp:include page="head.jsp" />
  <title>Zinger LTD</title>
</head>
<body>
<jsp:include page="headerAdminAuth.jsp" />
<div style="padding:10px;">

  <h2>
    Список статей на модерации
  </h2>
  <div>
    <%
      List<Article> articles = (List<Article>)request.getAttribute("articles");
      if (articles != null && articles.size() > 0) {
        for (Article article : articles ) {
          request.setAttribute("article", article);
          %>
            <jsp:include page="articleManage.jsp" />
          <%
          }
      }
      else {
        %>
        <div>Список пуст</div>
        <%
          }
        %>
  </div>
</div>

</body>
</html>
