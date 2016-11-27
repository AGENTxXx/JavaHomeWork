<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="background:indigo; height: 50px;" class="header">
    <div style="display: inline-block; color:white; font-size:26px; padding-left:5px; padding-top:6px;">
        <a href="/MyApp/">Zinger LTD</a>
    </div>
    <div style="margin-top: 5px;  float: right; display: inline-block; padding-right: 5px;">
        <div class="form-inline">
            <div style="display: inline-block;color: white;"><%=request.getAttribute("login")%></div>
            <a href="/MyApp/profileEdit">
                <button class="btn btn-primary" type="button" >Редактировать профиль</button>
            </a>
            <a href="/MyApp/myArticles">
                <button class="btn btn-info" type="button" >Мои статьи</button>
            </a>
            <a href="/MyApp/createArticle">
                <button class="btn btn-success" type="button" >Новая статья</button>
            </a>
            <a href="/MyApp/?logout=true">
                <button class="btn btn-danger" type="button">Выйти</button>
            </a>
        </div>
    </div>
</div>