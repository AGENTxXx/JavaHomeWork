<%@ page import="ru.innopolis.models.Article" %>
<%@ page import="java.util.List" %><%--
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

<%
  if (request.getAttribute("user") != null) {
    %>
    <jsp:include page="headerAuth.jsp" />
    <%
  }
  else {
  %>
    <jsp:include page="header.jsp" />
  <%
  }
%>



<div style="padding:10px;">
  <div class="form-group">
    <form action="/MyApp/" method="post">
      <label for="search_article">Поиск статьи</label>
      <input type="text" class="form-control" id="search_article" name="search_article" placeholder="Введите название статьи">
      <button class="btn btn-primary" type="submit">Найти</button>
    </form>
  </div>
  <h2>Новые статьи</h2>
  <div>
    <div>
      <%
        List<Article> articles = (List<Article>)request.getAttribute("articles");
        if (articles.size() > 0) {
          for (Article article : articles ) {
            request.setAttribute("id", article.getId());
            request.setAttribute("title", article.getTitle());
            request.setAttribute("content", article.getContent());
      %>
        <jsp:include page="articlePreview.jsp" />
      <%
          }
        }
        else {
          %>
          <div>По вашему запросу ни чего не найдено</div>
          <%
          }
      %>
    </div>
  </div>
</div>
<%@include file="footer.jspf"%>
</body>
</html>
