<%--
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
  <jsp:include page="head.jsp" />
  <script type="text/javascript" src="js/tiny_mce/tinymce.min.js"></script>
  <script type="text/javascript">
    tinymce.init({
      // General options
      selector: 'textarea'
    });
  </script>
<!-- /TinyMCE -->
  <title>Zinger LTD </title>
</head>
<body>

<jsp:include page="headerAuth.jsp" />
<div style="padding:10px;">
  <h2>Создание новой статьи</h2>
  <div>
    <div>
      <div class="form-group">
        <label for="title"></label>
        <input class="form-control" id="title" name="title" placeholder="Заголовок статьи" type="text">
      </div>
    </div>
    <div class="form-group">
        <label for="content">Контент</label>
        <textarea name="content" id="content"></textarea>
    </div>
  </div>
  <button class="btn btn-warning" type="button" onclick="saveArticle('create')">Сохранить</button>
</div>
</body>
</html>
