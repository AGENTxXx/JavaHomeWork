<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Modal -->
<div class="modal fade" id="regModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Регистрация</h4>
      </div>
      <div class="modal-body">
        <from id="reg_form">
          <div class="form-group">
            <label for="reg_firstname">Имя</label>
            <input type="text" class="form-control" id="reg_firstname" placeholder="Имя">
            <div id="firstname_error_block" class="alert alert-danger alert-add-css alert-dismissible fade in hide" role="alert">

              <strong>Ошибка!</strong> <span id="firstname_error"></span>
            </div>
          </div>
          <div class="form-group">
            <label for="reg_lastname">Фамилия</label>
            <input type="text" class="form-control" id="reg_lastname" placeholder="Фамилия">
            <div id="lastname_error_block" class="alert alert-danger alert-add-css alert-dismissible fade in hide" role="alert">

              <strong>Ошибка!</strong> <span id="lastname_error"></span>
            </div>
          </div>
          <div class="form-group">
            <label for="reg_email">Email address</label>
            <input type="email" class="form-control" id="reg_email" placeholder="Email">
            <div id="email_error_block" class="alert alert-danger alert-add-css alert-dismissible fade in hide" role="alert">

              <strong>Ошибка!</strong> <span id="email_error"></span>
            </div>
          </div>
          <div class="form-group">
            <label for="reg_login">Login</label>
            <input type="text" class="form-control" id="reg_login" placeholder="Логин">
            <div id="login_error_block" class="alert alert-danger alert-add-css alert-dismissible fade in hide" role="alert">

              <strong>Ошибка!</strong> <span id="login_error"></span>
            </div>
          </div>
          <div class="form-group">
          <label for="reg_password">Password</label>
          <input type="password" class="form-control" id="reg_password" placeholder="Password">
          <div id="password_error_block" class="alert alert-danger alert-add-css alert-dismissible fade in hide" role="alert">

            <strong>Ошибка!</strong> <span id="password_error"></span>
          </div>
        </div>
        </from>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
        <button type="button" class="btn btn-primary" onclick="userRegistrationQuery()">Зарегестрироваться</button>
      </div>
    </div>
  </div>
</div>

<div style="background:indigo; height: 50px;" class="header">
  <div style="display: inline-block; color:white; font-size:26px; padding-left:5px; padding-top:6px;">
    <a href="/MyApp/">Zinger LTD</a>
  </div>
  <div style="margin-top: 5px;  float: right; display: inline-block; padding-right: 5px;">
    <div class="form-inline">
      <div class="form-group"><input placeholder="Логин" class="form-control" type="text" id="login" name="login" value=""></div>
      <div class="form-group"><input placeholder="Пароль" class="form-control" type="password" id="password" name="password" value=""></div>
      <button class="btn btn-primary" type="button" onclick="sendAuthQuery()">OK</button>
      <button class="btn btn-warning" type="button" data-toggle="modal" data-target="#regModal">Регистрация</button>
    </div>
  </div>
  <div id="auth_info_block" class="alert alert-danger alert-add-css alert-dismissible fade in hide" role="alert">
    <strong id="auth_info_msg">Логин и/или пароль введен не верно!</strong>
  </div>
</div>