<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
  <jsp:include page="head.jsp" />
  <script type="text/javascript" src="<c:url value="/resources/js/tiny_mce/tinymce.min.js" />"></script>
  <script type="text/javascript">
    tinymce.init({
      force_br_newlines : true,
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
        <div id="title_error_block" class="alert alert-danger alert-dismissible fade in hide" role="alert">
          <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
          <strong>Ошибка!</strong> <span id="title_error"></span>
        </div>
      </div>
    </div>
    <div class="form-group">
        <label for="content">Контент</label>
        <textarea name="content" id="content"></textarea>
    </div>
  </div>
  <button class="btn btn-primary" type="button" onclick="saveArticle('create')">Сохранить</button>
</div>
</body>
</html>
