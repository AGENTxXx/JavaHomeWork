<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 23.11.2016
  Time: 0:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
  <jsp:include page="head.jsp" />
  <script type="text/javascript" src="js/tiny_mce/tinymce.min.js"></script>
  <script type="text/javascript">
  tinymce.init({
    // General options
    selector: 'textarea',
        setup : function(ed)
        {
            ed.on('init', function(evt)
            {
                ed.setContent('<%=request.getAttribute("content")%>');
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
        <input value="<%=request.getAttribute("title")%>" class="form-control" id="title" name="title" placeholder="Заголовок статьи" type="text">
      </div>
    </div>
    <div class="form-group">
        <label for="content">Контент</label>
        <textarea name="content" id="content"></textarea>
    </div>
  </div>
  <button class="btn btn-warning" type="button" onclick="saveArticle('edit',<%=request.getAttribute("id")%>)">Сохранить</button>
</div>
</body>
</html>
