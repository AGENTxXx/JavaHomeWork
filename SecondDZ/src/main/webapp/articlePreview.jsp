<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="article_<%=request.getAttribute("id")%>" class="panel panel-primary">
  <div class="panel-heading">
    <h3 class="panel-title">
      <a href="/MyApp/articleList/<%=request.getAttribute("id")%>"><%=request.getAttribute("title")%></a>
    </h3>
  </div>
  <div class="panel-body">
    <p>
    <%=request.getAttribute("content")%>
    </p>
      <% if ((boolean)request.getAttribute("edit")) { %>
        <div style="margin-bottom: 10px;">
          <button class="btn btn-success" type="button" onclick="articlePublish(<%=request.getAttribute("id")%>)">Отправить на модерацию</button>
          <a href="/MyApp/myArticles/edit/<%=request.getAttribute("id")%>">
            <button class="btn btn-primary" type="button">Редактировать</button>
          </a>
          <button class="btn btn-danger" type="button" onclick="articleDelete(<%=request.getAttribute("id")%>)">Удалить</button>
        </div>
      <span class="label label-primary">Ждёт проверки</span>
      <%} %>
  </div>
</div>