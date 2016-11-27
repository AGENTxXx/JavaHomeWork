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
  <h2><%=request.getAttribute("title")%></h2>
  <div>
    <%=request.getAttribute("content")%>
  </div>
</div>

</body>
</html>
