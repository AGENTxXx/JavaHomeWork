<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.innopolis.models.User" %>
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

<jsp:include page="headerAdminAuth.jsp" />
<div style="padding:10px;">

  <h2>
    СПИСОК ПОЛЬЗОВАТЕЛЕЙ
  </h2>
  <div>
    <%
      List<User> users = (List<User>)request.getAttribute("users");
      if (users != null && users.size() > 0) {
      %>
        <table>
          <tbody>
            <tr>
              <th style="width:50px">Id</th>
              <th style="width:200px">Login</th>
              <th style="width:200px">Email</th>
              <th style="width:100px">Имя</th>
              <th style="width:100px">Фамилия</th>
              <th style="width:200px">Заблокирован</th>
              <th style="width:200px">Действия</th>
            </tr>
      <%
        for (User user : users ) {
          if (!"admin".equals(user.getLogin())) {
            request.setAttribute("user", user);
            %>
            <jsp:include page="userPreview.jsp" />
            <%
          }
        }
        %>
          </tbody>
        </table>
        <%
      }
      else {
      %>
        <div>Список пользователей пуст!</div>
      <%
      }
      %>
  </div>
</div>

</body>
</html>
