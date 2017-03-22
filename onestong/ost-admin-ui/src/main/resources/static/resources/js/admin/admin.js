var admin =function(){
	var handleLogin = function(){
		
		$("#loginBtn").on("click", function() {
			$form = $(this).parents("form");
			$url = $.trim($form.attr("action"));

			$.post($url, $.param($form.serializeArray()), function(data) {
				var code = data.statusCode;
				if (code != undefined && code == '200') {
					window.location.href = app.ctx() + "/ost/admin/index";

				} else if (code == 'g_p_not_c') {
					alert("请填写用户名或密码！");

				} else {
					alert("登陆失败！");

				}

			}, "json");

		});
		
		
	};
	return {
		init:function(){
			handleLogin();
			
		}
		
	};
}();