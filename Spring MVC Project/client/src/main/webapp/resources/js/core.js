/**
 * Created by Administrator on 23.11.2016.
 */

function testAuth() {
    var query = new Object();
    query.j_username = "admin";
    query.j_password = "admin";
    query.url = "/MyApp/user/login";
    query.method = "login";
    sendAjax(query,authAnswer,authError)
}

function sendAuthQuery() {
    var query = new Object();
    query.login = $("#login").val();
    query.password = $("#password").val();
    query.url = "/MyApp/user/auth";
    query.method = "auth";
    $(".alert").hide();
    sendAjax(query,authAnswer,authError)
}

function authAnswer(data) {
    var result = JSON.parse(data);
    if (result.success == true) {
        document.location.href = '/MyApp/articles';
    }
    else {
        $("#auth_info_msg").text("Логин и/или пароль введен не верно!");
        $("#auth_info_block").removeClass("alert-success");
        $("#auth_info_block").addClass("alert-danger");
        $("#auth_info_block").show();
        $("#auth_info_block").removeClass('hide');
    }
}

function authError() {
    $("#auth_info_msg").text("Сервер недоступен! Повторите запрос позже");
    $("#auth_info_block").removeClass("alert-success");
    $("#auth_info_block").addClass("alert-warning");
    $("#auth_info_block").show();
    $("#auth_info_block").removeClass('hide');
}

function userExit() {
    document.location.href = '/MyApp/?logout=true';
}

function userRegistrationQuery() {
    var query = new Object();
    query.firstname = $("#reg_firstname").val();
    query.lastname = $("#reg_lastname").val();
    query.email = $("#reg_email").val();
    query.login = $("#reg_login").val();
    query.password = $("#reg_password").val();
    query.url = "/MyApp/user/create";
    query.method = "reg";
    if (checkData(query)) {
        sendAjax(query,regAnswer, errorRequest)
    }

}

function regAnswer(data) {
    var result = JSON.parse(data);
    if (result.success == true) {
        $('#regModal').modal('toggle');
        $("#auth_info_msg").text("Вы зарегестрированы! Теперь можете авторизоваться!");
        $("#auth_info_block").removeClass("alert-danger");
        $("#auth_info_block").addClass("alert-success");
        $("#auth_info_block").removeClass("hide");
        $("#auth_info_block").show();


        $("#reg_firstname").val("");
        $("#reg_lastname").val("");
        $("#reg_email").val("");
        $("#reg_login").val("");
        $("#reg_password").val("");
    }
    else {
        if (result.error != null && result.error == "exist") {
            $("#firstname_error").text("Пользователь с таким логином уже существует!");
            $("#firstname_error_block").show();
            $("#firstname_error_block").removeClass("hide");
        }
        else {
            alert("Неизвестная ошибка! Попробуйте повторить регистрацию позже!");
        }
    }
}

function errorRequest(errMsg) {
    alert(errMsg);
}

function updateUser() {
    var query = new Object();
    query.firstname = $("#firstname").val();
    query.lastname = $("#lastname").val();
    query.email = $("#email").val();
    query.password = $("#password").val();
    query.url = "/MyApp/user/update";
    query.method = "update";
    sendAjax(query,userAnswer,errorRequest);
}

function articleModeration(articleId) {
    var query = new Object();
    query.url = "/MyApp/article/moderation/" + articleId;
    query.method = "moderation";
    sendAjax(query,articleAnswer,errorRequest);
}

function removeArticle(articleId) {
    var query = new Object();
    query.url = "/MyApp/article/remove/" + articleId;
    query.method = "remove";
    sendAjax(query,articleAnswer,errorRequest);
}

function userAnswer(data) {
    var result = JSON.parse(data);
    if (result.success) {
        method = result.method;
        switch (method) {
            case 'update':
                $("#profile_info").text("Профиль успешно обновлён!");
                $("#profile_info_block").removeClass('hide');
                $("#profile_info_block").show();
                break;
            default:
                break;
        }
    }
}

function articleActivate(articleId) {
    var query = new Object();
    query.articleId = articleId;
    query.method = "activate";
    sendAjax(query,articleAnswer,errorRequest);
}


function saveArticle(method, articleId) {
    var query = new Object();
    query.title = $("#title").val();
    query.content = tinyMCE.activeEditor.getContent();
    if (method == "create") {
        query.url = "/MyApp/article/create";
        query.method = "create";
    }
    else {
        query.url = "/MyApp/article/update/" + articleId;
        query.method = "update";
    }
    sendAjax(query,articleAnswer,errorRequest);
}


function articleAnswer(data) {
    var result = JSON.parse(data);
    if (result.success) {
        method = result.method;
        switch (method) {
            case 'remove':
                $("#article_" + result.articleId).remove();
                break;
            case 'moderation':
                $("#moderation_btn_" + result.articleId).hide();
                $("#label_" + result.articleId).removeClass('label-warning');
                $("#label_" + result.articleId).addClass('label-primary');
                $("#label_" + result.articleId).text('Ждёт проверки модератором');
                break;
            case 'update':
                $("#article_info").text('Статья успешно изменена!');
                $("#article_info_block").removeClass('hide');
                $("#article_info_block").show();
                break;
            case 'create':
                document.location.href = '/MyApp/articles/current';
                break;
            default:
                break;
        }
    }
}

function showMessage(message, title) {
    alert(message);
}