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
      <% if ((boolean)request.getAttribute("edit")) { %>
        <div style="margin-bottom: 10px;">
          <% if (!article.getModeration()) { %>
            <button id="moderation_btn_<%=article.getId()%>" class="btn btn-success" type="button" onclick="articleModeration(<%=article.getId()%>)">Отправить на модерацию</button>
          <%} %>
          <a href="/MyApp/article/update/<%=article.getId()%>">
            <button class="btn btn-primary" type="button">Редактировать</button>
          </a>
          <button class="btn btn-danger" type="button" onclick="removeArticle(<%=article.getId()%>)">Удалить</button>
        </div>
      <% if (article.getChecking()) { %>
      <span id="label_<%=article.getId()%>" class="label label-success">Опубликована!</span>
      <% } else if (article.getModeration()) { %>
      <span id="label_<%=article.getId()%>" class="label label-primary">Ждёт проверки модератором</span>
      <% } else { %>
      <span id="label_<%=article.getId()%>" class="label label-warning">В режиме создания</span>
      <%} %>
      <%} %>
  </div>
</div>