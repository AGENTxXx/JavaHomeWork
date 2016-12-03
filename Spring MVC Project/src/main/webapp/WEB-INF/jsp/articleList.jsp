<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

  <h2>
    <c:if test="${searchArticle == null}">
      Список последних статей
    </c:if>
    <c:if test="${searchArticle != null}">
      Поиск статей по слову: "<c:out value="${searchArticle}"/>"
    </c:if>
  </h2>
  <form action="./articles" method="post">
    <div class="input-group">
    <input class="form-control" id="search_article" name="search_article" placeholder="Введите название статьи" type="text">
    <span class="input-group-btn">
        <button type="submit" class="btn btn-primary">Найти</button>
   </span>
  </div>
  </form>
  <div>
    <%
      List<Article> articles = (List<Article>)request.getAttribute("articles");
      if (articles != null && articles.size() > 0) {
        for (Article article : articles ) {
          request.setAttribute("article", article);
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

</body>
</html>
