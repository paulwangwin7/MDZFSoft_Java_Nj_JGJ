<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="error msg" id="changePswdMsg" style="display:none" onclick="hideObj($(this).attr('id'))"></div>
<div class="new_form">
<form id="changePswdForm" class="uniform" method="post" onsubmit="return changePswdSubmit()">
<ul class="form_list">
	<li class="form_item">
		<label class="input_hd">旧密码:</label>
		<input type="password" id="oldpassword" class="input_130x20" />
	</li>
	<li class="form_item">
		<label class="input_hd">新密码:</label>
		<input type="password" id="newpassword" class="input_130x20" />
	</li>
	<li class="form_item">
		<label class="input_hd">确认密码:</label>
		<input type="password" id="repassword" class="input_130x20" />
	</li>
	<li class="form_item">
		<input type="submit" class="blue_mod_btn" value="确认修改" />
 	</li>
</ul>
</form>
</div>