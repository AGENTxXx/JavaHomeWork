function lockUser(userId) {
    var query = new Object();
    query.userId = userId;
    query.url = "/MyApp/admin/user/lock/" + userId;
    query.method = "lock";
    sendAjax(query,userAnswer,errorRequest);
}

function unlockUser(userId) {
    var query = new Object();
    query.userId = userId;
    query.url = "/MyApp/admin/user/unlock/" + userId;
    query.method = "unlock";
    sendAjax(query,userAnswer,errorRequest);
}

function removeUser(userId) {
    var query = new Object();
    query.userId = userId;
    query.url = "/MyApp/admin/user/remove/" + userId;
    query.method = "remove";
    sendAjax(query,userAnswer,errorRequest);
}


function publishArticle(articleId) {
    var query = new Object();
    query.articleId = articleId;
    query.url = "/MyApp/admin/article/publish/" + articleId;
    query.method = "publish";
    sendAjax(query,articleAnswer,errorRequest);
}



function articleAnswer(data) {
    var result = JSON.parse(data);
    if (result.success) {
        method = result.method;
        switch (method) {
            case 'publish':
                $("#publish_btn_" + result.articleId).remove();
                break;
            default:
                break;
        }
    }
}

function userAnswer(data) {
    var result = JSON.parse(data);
    if (result.success) {
        method = result.method;
        switch (method) {
            case 'remove':
                $("#user_" + result.userId).remove();
                break;
            case 'lock':
                $("#lock_unlok_" + result.userId).text("Unlock");
                $("#lock_unlok_" + result.userId).removeClass('btn-warning');
                $("#lock_unlok_" + result.userId).addClass('btn-success');
                $("#lock_unlok_" + result.userId).attr('onclick','unlockUser('+result.userId+')');
                break;
            case 'unlock':
                $("#lock_unlok_" + result.userId).text("Lock");
                $("#lock_unlok_" + result.userId).removeClass('btn-success');
                $("#lock_unlok_" + result.userId).addClass('btn-warning');
                $("#lock_unlok_" + result.userId).attr('onclick','lockUser('+result.userId+')');
                break;
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

function showMessage(message, title) {
    alert(message);
}