<%@ page import="ru.innopolis.models.Article" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
  <jsp:include page="head.jsp" />
  <title>Zinger LTD </title>
</head>
<body>
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
  <h2><c:out value="${article.title}"/></h2>
  <div>
    <%=((Article)request.getAttribute("article")).getContent()%>
  </div>
</div>
</body>
</html>
