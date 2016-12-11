<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.innopolis.models.User" %>

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
    User user = (User)request.getAttribute("user");
%>
<c:set var="headerTpl" scope="session" value="header.jsp"/>
<sec:authorize access="authenticated">
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <c:set var="headerTpl" scope="session" value="headerAdminAuth.jsp"/>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_USER')">
        <c:set var="headerTpl" scope="session" value="headerAuth.jsp"/>
    </sec:authorize>
</sec:authorize>
<jsp:include page="${headerTpl}" />

<div style="padding:10px;">
  <div class="form-group">
    <label for="firstname">Имя</label>
    <input type="text" class="form-control" id="firstname" value="<%=user.getFirstname()%>" placeholder="Имя">
  </div>
  <div class="form-group">
    <label for="lastname">Фамилия</label>
    <input type="text" class="form-control" id="lastname" value="<%=user.getLastname()%>" placeholder="Фамилия">
  </div>
  <div class="form-group">
    <label for="email">Email address</label>
    <input type="email" class="form-control" id="email" value="<%=user.getEmail()%>" placeholder="Email">
  </div>
  <div class="form-group">
    <label for="password">Password</label>
    <input type="password" class="form-control" id="password" placeholder="Password">
  </div>
    <div id="profile_info_block" class="alert alert-success alert-add-css alert-dismissible fade in hide" role="alert">
        <strong id="profile_info">Профиль успешно обновлён!</strong>
    </div>
  <button type="button" class="btn btn-primary" onclick="updateUser()">Сохранить</button>
</div>

</body>
</html>
