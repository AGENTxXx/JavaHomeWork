
function sendAjax(data, succesFunc, failFunc) {
    $.ajax({
        type: "POST",
        url: data.url,
        data: data,
        async:false,
        dataType: "text",
        //dataType: "json",
        success: function(data){
            succesFunc(data);
        },
        fail: function(errMsg) {
            failFunc(errMsg);
        }
    });
}

function checkData(data) {
    $(".alert").hide();
    error = false;
    switch (data.method) {
        case "reg":
            if (data.firstname.length < 2 || data.firstname.length > 50) {
                $("#firstname_error").text("Имя должно содержать минимум от 2 до 50 символов!");
                $("#firstname_error_block").show();
                $("#firstname_error_block").removeClass("hide");
                error = true;
            }
            else if (!inputValidator(data.firstname, textValidator)) {
                $("#firstname_error").text("Поле должно содержать только цифры, русские и латинские буквы, знак подчеркивания, точки и дефис!");
                $("#firstname_error_block").show();
                $("#firstname_error_block").removeClass("hide");
                error = true;
            }

            if (data.lastname.length < 2 || data.lastname.length > 50) {
                $("#lastname_error").text("Фамилия должна содержать от 2 до 50 символов!");
                $("#lastname_error_block").show();
                $("#lastname_error_block").removeClass("hide");
                error = true;
            }
            else if (!inputValidator(data.lastname, textValidator)) {
                $("#lastname_error").text("Поле должно содержать только цифры, русские и латинские буквы, знак подчеркивания, точки и дефис!");
                $("#lastname_error_block").show();
                $("#lastname_error_block").removeClass("hide");
                error = true;
            }

            if (data.email.length < 6 || !inputValidator(data.email, emailValidator)) {
                $("#email_error").text("Email адрес введён не верно!");
                $("#email_error_block").show();
                $("#email_error_block").removeClass("hide");
                error = true;
            }

            if (data.login.length < 3 || data.login.length > 32) {
                $("#login_error").text("Логин должен содержать минимум от 3 до 32 символов!");
                $("#login_error_block").show();
                $("#login_error_block").removeClass("hide");
                error = true;
            }
            else if (!inputValidator(data.login, textValidator)) {
                $("#login_error").text("Поле должно содержать только цифры, русские и латинские буквы, знак подчеркивания, точки и дефис!");
                $("#login_error_block").show();
                $("#login_error_block").removeClass("hide");
                error = true;
            }

            if (data.password.length < 6 || data.password.length > 32) {
                $("#password_error").text("Пароль должен содержать от 6 до 32 символов!");
                $("#password_error_block").show();
                $("#password_error_block").removeClass("hide");
                error = true;
            }
            else if (!inputValidator(data.password, textValidator)) {
                $("#password_error").text("Поле должно содержать только цифры, русские и латинские буквы, знак подчеркивания, точки и дефис!");
                $("#password_error_block").show();
                $("#password_error_block").removeClass("hide");
                error = true;
            }
            break;

        case "add":

            break;

        case "update":

            break;
    }

    return !error;
}


function inputValidator(value, regex)
{
    if (value)
    {
        if (regex.test(value))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}