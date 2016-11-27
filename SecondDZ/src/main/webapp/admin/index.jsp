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
  <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
  <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css">
  <script src="js/jquery-3.1.1.js"></script>
  <script src="js/bootstrap.js"></script>
  <script src="js/core.js"></script>
  <meta charset="UTF-8">
  <% int i = (Integer) request.getAttribute("i");%>
  <title>Zinger LTD <%=i%></title>
</head>
<body>

<div style="background:indigo; height: 50px;" class="header">
  <div style="display: inline-block; color:white; font-size:26px; padding-left:5px; padding-top:6px;">
    Zinger LTD
  </div>
  <div style="margin-top: 5px;  float: right; display: inline-block; padding-right: 5px;">
    <div class="form-inline">
      <div class="form-group"><input placeholder="Логин" class="form-control" type="text" id="login" name="login" value=""></div>
      <div class="form-group"><input placeholder="Пароль" class="form-control" type="password" id="password" name="password" value=""></div>
      <button class="btn btn-primary" type="button" onclick="sendAuthQuery()">OK</button>
    </div>
  </div>
</div>

</body>
</html>
