/**
 * Created by Administrator on 23.11.2016.
 */

var prefix_url = "/MyApp/api";
function sendAuthQuery() {
    var query = new Object();
    query.login = $("#login").val();
    query.password = $("#password").val();
    query.method = "auth";


    $.ajax({
        type: "POST",
        url: prefix_url,
        data: query,
        async:false,
        dataType: "text",
        //dataType: "json",
        success: function(data){
            authAnswer(data);
        },
        failure: function(errMsg) {
            alert(errMsg);
        }
    });
}

function authAnswer(data) {
    var result = JSON.parse(data);
    if (result.auth == true) {
        document.location.href = document.location.href;
    }
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
    query.method = "reg";

    $.ajax({
        type: "POST",
        url: prefix_url,
        data: query,
        async:false,
        dataType: "text",
        //dataType: "json",
        success: function(data){
            regAnswer(data);
        },
        failure: function(errMsg) {
            alert(errMsg);
        }
    });
}

function regAnswer(data) {
    var result = JSON.parse(data);
    if (result.registration == true) {
        alert("Регистрация прошла успешно! Теперь вы можете авторизоваться =)");
        $('#regModal').modal('toggle');
    }
}



function sendAjax(data, succesFunc, failureFunc) {
    $.ajax({
        type: "POST",
        url: prefix_url,
        data: data,
        async:false,
        dataType: "text",
        //dataType: "json",
        success: function(data){
            succesFunc(data.method, data);
        },
        failure: function(errMsg) {
            failureFunc(errMsg);
        }
    });
}

function ErrorRequest(errMsg) {
    alert(errMsg);
}

function editUser() {
    var query = new Object();
    query.firstname = $("#firstname").val();
    query.lastname = $("#lastname").val();
    query.email = $("#email").val();
    query.password = $("#password").val();

    query.method = "edit";
    sendAjax(query,userAnswer,ErrorRequest);
}

function lockUser(userId) {
    var query = new Object();
    query.userId = userId;
    query.method = "lock";
    sendAjax(query,userAnswer,ErrorRequest);
}

function unlockUser(userId) {
    var query = new Object();
    query.userId = userId;
    query.method = "unlock";
    sendAjax(query,userAnswer,ErrorRequest);
}

function deleteUser(userId) {
    var query = new Object();
    query.userId = userId;
    query.method = "delete";
    sendAjax(query,userAnswer,ErrorRequest);
}

function userAnswer(method, data, afterFunc) {
    var result = JSON.parse(data);
    if (result.success) {
        switch (method) {
            case 'delete':
                showMessage("Пользователь успешно удалён");
                break;
            case 'lock':
                showMessage("Пользователь успешно заблокирован");
                break;
            case 'unlock':
                showMessage("Пользователь успешно разблокирован");
                break;
            case 'edit':
                showMessage("Профиль пользователя сохранён");
                break;
            default:
                break;
        }

        if (afterFunc != null) {
            afterFunc(data);
        }
    }
}

function articleActivate(articleId) {
    var query = new Object();
    query.userId = articleId;
    query.method = "activate";
    sendAjax(query,articleAnswer,ErrorRequest);
}

function articleDelete(articleId) {
    var query = new Object();
    query.userId = articleId;
    query.method = "delete";
    sendAjax(query,articleAnswer,ErrorRequest);
}

function saveArticle(method, articleId) {
    var query = new Object();
    query.title = $("#title").val();
    query.content = tinyMCE.activeEditor.getContent({format : 'raw'});

    if (method == "create") {
        query.method = "create";
    }
    else {
        query.articleId = articleId;
        query.method = "edit";
    }
    sendAjax(query,articleAnswer,ErrorRequest);
}


function articleAnswer(method, data) {
    var result = JSON.parse(data);
    if (result.success) {
        switch (method) {
            case 'delete':
                showMessage("Статья успешно удалена");
                $("#article_" + data.articleId).remove();
                break;
            case 'activate':
                showMessage("Статья успешно опубликована");
                break;
            case 'edit':
                showMessage("Статья успешно отредактирована");
                break;
            case 'create':
                showMessage("Статья успешно создана и ожидает проверки администратором");
                break;
            default:
                break;
        }
    }
}

function showMessage(message, title) {
    alert(message);
}