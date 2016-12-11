<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.innopolis.models.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User user = (User)request.getAttribute("user"); %>
<tr id="user_<%=user.getId()%>">
  <td><%=user.getId()%></td>
  <td><%=user.getLogin()%></td>
  <td><%=user.getEmail()%></td>
  <td><%=user.getFirstname()%></td>
  <td><%=user.getLastname()%></td>
  <td><%=user.getIsLocked()%></td>
  <td>
    <c:choose>
      <c:when test="${user.getIsLocked() == true}">
        <button id="lock_unlok_${user.getId()}" class="btn btn-success btn-add-width" type="button" onclick="unlockUser(${user.getId()})">
                Unlock
        </button>
      </c:when>
      <c:when test="${user.getIsLocked() == false}">
        <button id="lock_unlok_${user.getId()}" class="btn btn-warning btn-add-width" type="button" onclick="lockUser(${user.getId()})">
          Lock
        </button>
      </c:when>
    </c:choose>
    <button class="btn btn-danger" type="button" onclick="removeUser(${user.getId()})">
      Удалить
    </button>
  </td>
</tr>
