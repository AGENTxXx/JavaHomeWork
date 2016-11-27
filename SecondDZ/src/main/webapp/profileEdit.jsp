<%@ page import="ru.innopolis.modal.Article" %>
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
      if (request.getAttribute("login") != null) {
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
  <div class="form-group">
    <label for="firstname">Имя</label>
    <input type="text" class="form-control" id="firstname" value="<%=request.getAttribute("firstname")%>" placeholder="Имя">
  </div>
  <div class="form-group">
    <label for="lastname">Фамилия</label>
    <input type="text" class="form-control" id="lastname" value="<%=request.getAttribute("lastname")%>" placeholder="Фамилия">
  </div>
  <div class="form-group">
    <label for="email">Email address</label>
    <input type="email" class="form-control" id="email" value="<%=request.getAttribute("email")%>" placeholder="Email">
  </div>
  <div class="form-group">
    <label for="password">Password</label>
    <input type="password" class="form-control" id="password" placeholder="Password">
  </div>
  <button type="button" class="btn btn-primary" onclick="editUser()">Сохранить</button>
</div>

</body>
</html>
