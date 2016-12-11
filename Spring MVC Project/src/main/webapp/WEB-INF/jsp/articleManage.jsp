<%@ page import="ru.innopolis.models.Article" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Article article = (Article)request.getAttribute("article"); %>
<div id="article_<%=article.getId()%>" class="panel panel-primary">
  <div class="panel-heading">
    <h3 class="panel-title">
      <a href="/MyApp/articles/<%=article.getId()%>"><%=article.getTitle()%></a>
    </h3>
  </div>
  <div class="panel-body">
    <p>
    <%=article.getPreview()%>
    </p>
          <button id="publish_btn_<%=article.getId()%>" class="btn btn-warning" type="button" onclick="publishArticle(<%=article.getId()%>)">Опубликовать</button>
          <button class="btn btn-danger" type="button" onclick="removeArticle(<%=article.getId()%>)">Удалить</button>
  </div>
</div>