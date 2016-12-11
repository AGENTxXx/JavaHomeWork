<%@ page import="ru.innopolis.models.Article" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 23.11.2016
  Time: 0:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Article article = (Article)request.getAttribute("article"); %>
<html lang="ru">
<head>
  <jsp:include page="head.jsp" />
  <script type="text/javascript" src="<c:url value="/resources/js/tiny_mce/tinymce.min.js" />"></script>
  <script type="text/javascript">
  tinymce.init({
    // General options
    force_br_newlines : true,
    selector: 'textarea',
        setup : function(ed)
        {
            ed.on('init', function(evt)
            {
                ed.setContent('<%=article.getContent().replaceAll("\n","<br>").replaceAll("\'","&rsquo;")%>');
            });
        }
  });
</script>
<!-- /TinyMCE -->
  <title>Zinger LTD </title>
</head>
<body>

<jsp:include page="headerAuth.jsp" />

<div style="padding:10px;">
  <h2>Редактирование статьи</h2>
  <div>
    <div>
      <div class="form-group">
        <label for="title"></label>
        <input value="<%=article.getTitle()%>" class="form-control" id="title" name="title" placeholder="Заголовок статьи" type="text">
      </div>
    </div>
    <div class="form-group">
        <label for="content">Контент</label>
        <textarea name="content" id="content"></textarea>
    </div>
    <div id="article_info_block" class="alert alert-success alert-add-css alert-dismissible fade in hide" role="alert">
      <strong id="article_info">Статья успешно изменена!</strong>
    </div>
  </div>
  <button class="btn btn-primary" type="button" onclick="saveArticle('update',<%=article.getId()%>)">Сохранить</button>
</div>
</body>
</html>
