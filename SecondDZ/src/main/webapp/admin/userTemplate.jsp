<%@ page contentType="text/html; charset=UTF-8" %>
<tr id="student_<%=request.getAttribute("id")%>">
  <td id="login_<%=request.getAttribute("id")%>"><%=request.getAttribute("login")%></td>
  <td id="firstname_<%=request.getAttribute("id")%>"><%=request.getAttribute("firstname")%></td>
  <td id="lastname_<%=request.getAttribute("id")%>"><%=request.getAttribute("lastname")%></td>
  <td>
    <button type="button" class="btn btn-info" onclick="userArticle(<%=request.getAttribute("id")%>)">
      <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
    </button>
    <button type="button" class="btn btn-danger" onclick="lockUser(<%=request.getAttribute("id")%>)">
      <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
    </button>
    <button type="button" class="btn btn-info" onclick="unlockUser(<%=request.getAttribute("id")%>)">
      <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
    </button>
    <button type="button" class="btn btn-danger" onclick="deleteUser(<%=request.getAttribute("id")%>)">
      <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
    </button>
  </td>
</tr>