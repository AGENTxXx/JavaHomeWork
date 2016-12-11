<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
<c:set var="headerTpl" scope="session" value="header.jsp"/>
<sec:authorize access="authenticated">
  <sec:authorize access="hasRole('ROLE_ADMIN')">
    <c:set var="headerTpl" scope="session" value="headerAdminAuth.jsp"/>
  </sec:authorize>
  <sec:authorize access="hasRole('ROLE_USER')">
    <c:set var="headerTpl" scope="session" value="headerAuth.jsp"/>
  </sec:authorize>
</sec:authorize>
<jsp:include page="${headerTpl}" />
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
