var user = function() {
	var handleBackToList = function(){
		$("#backTolist",$("#user_form")).on("click",function(){
			
			window.location.href=app.ctx()+"/user/list";
		});
		
	};
	
	var handleUnbindDevice = function(){
		
		$(".unbindDevice").each(function(i,d){
			$(d).click(function(){
			    $('#unbindConfirm').modal({
			        relatedTarget: this,
			        onConfirm: function(options) {
			        	$userId = $(this.relatedTarget).parents("tr").attr("id");
			        	app.openWait();
			        	
			        	
			        	setTimeout(function(){
			        		
			        		$.ajax({
				    			url:app.ctx()+"/user/unBindUser?_="+Math.random(),
				    			data:{userId:$userId},
				    			dataType:"json",
				    			type:'post',
				    			async:false,
				    			success:function(data){
				    				app.closeWait();
				    				if (data.statusCode == HTTP200
											&& data.statusCode != undefined) {
										
				    					app.kanoukenAlert({title:"温馨提示",content:data.description},function(){
				    						
				    						window.location.href = app.ctx()
											+ "/user/list";
				    					});
										
									} else {
										app.kanoukenAlert({title:"温馨提示",content:data.description});
									}
				    			
				    			},
				    			ajaxComplete:function(){
				    				app.closeWait();
				    				
				    			},
				    			ajaxError:function(){
				    				app.closeWait();
				    				
				    			}
				    		});
			        		
			        	},500);
			        	
			        	
			        	
			        	
			        },
			        onCancel: function() {
			         
			        }
			      });
				
			});
			
		});
		
	};
	
	
	
	var handleDeleteUser = function(){
		
		$(".deleteUser").each(function(i,d){
			$(d).click(function(){
			    $('#deleteUserConfirm').modal({
			        relatedTarget: this,
			        onConfirm: function(options) {
			        	$userId = $(this.relatedTarget).parents("tr").attr("id");
			        	app.openWait();
			        	
			        	
			        	setTimeout(function(){
			        		
			        		$.ajax({
				    			url:app.ctx()+"/user/deleteUser?_="+Math.random(),
				    			data:{userId:$userId},
				    			dataType:"json",
				    			type:'post',
				    			async:false,
				    			success:function(data){
				    				app.closeWait();
				    				if (data.statusCode == HTTP200
											&& data.statusCode != undefined) {
										
				    					app.kanoukenAlert({title:"温馨提示",content:data.description},function(){
				    						
				    						window.location.href = app.ctx()
											+ "/user/list";
				    					});
										
									} else {
										app.kanoukenAlert({title:"温馨提示",content:data.description});
									}
				    			
				    			},
				    			ajaxComplete:function(){
				    				app.closeWait();
				    				
				    			},
				    			ajaxError:function(){
				    				app.closeWait();
				    				
				    			}
				    		});
			        		
			        	},500);
			        	
			        	
			        	
			        	
			        },
			        onCancel: function() {
			         
			        }
			      });
				
			});
			
		});
		
	};
	
	
	var handleUpdate =function(){
		
		$(".edit_user_btn").each(function(i,d){
			$(d).on("click",function(){
				$tr = $(this).parents("tr");
				$userId = $.trim($tr.attr("id"));
				window.location.href=app.ctx()+"/user/update/"+$userId;
			});
			
		});
	};
	
	var handleAdd = function() {
		 $("#user_form").on("submit",function(event){
			 if(event.result){
			//ajax post
			$action = $.trim($(this).attr("action"));
			app.openWait();
	      	setTimeout(function(){
	      		
	      		
	      		$.ajax({
	    			url:$action + "?" + Math.floor(Math.random() * 100),
	    			data:{
	    				
	    				userId:$("input[name='userId']").val(),
	    				accountId:$("input[name='accountId']").val(),
	    				domainId:$("input[name='domainId']").val(),
	    				realname:$("input[name='realname']").val(),
	    				email:$("input[name='email']").val(),	
	    				phoneNum:$("input[name='phoneNum']").val(),
	    				loginPassword:$("input[name='loginPassword']").val(),
	    				optional1:$("input[name='optional1']").val(),
	    				deptId:$("#deptId").val(),
	    				position:$("input[name='position']").val(),
	    				roleId:$("#roleId").val()
	    			},
	    			dataType:"json",
	    			type:'post',
	    			async:false,
	    			success:function(data){
	    				app.closeWait();
	    				if (data.statusCode == HTTP200
								&& data.statusCode != undefined) {
							
	    					app.kanoukenAlert({title:"温馨提示",content:data.description},function(){
	    						
	    						window.location.href = app.ctx()
								+ "/user/list";
	    					});
							
						} else {
							app.kanoukenAlert({title:"温馨提示",content:data.description});
						}
	    			
	    			},
	    			ajaxComplete:function(){
	    				app.closeWait();
	    				
	    			},
	    			ajaxError:function(){
	    				app.closeWait();
	    				
	    			}
	    		});
	      		
        	},500);
			
			
			 }
			
			return false;
			
		});
		
	};

	var handelSearch = function() {

		$("#btn_user_search").on("click", function() {
			$form = $("#page");
			$("input[name='realname']", $form).val($("#realname").val());
			$("input[name='curPage']",$form).val(1);
			$form.submit();
		});

	};
	var handleLogin = function() {

		$("#customer_login").on("submit", function(event) {
			
			 if(event.result){
			$form = $(this);
			$url = $.trim($form.attr("action"));
			var paramArray =  $form.serializeArray();
			paramArray[1].value=md5(paramArray[1].value);
			$.post($url, $.param(paramArray), function(data) {
				var code = data.statusCode;
				if (code != undefined && code == HTTP200) {
					window.location.href = app.ctx() + "/user/list";

				} else if (code == 'g_p_not_c') {
					alert("请填写用户名或密码！");
				} else {
					alert(data.description);
				}

			}, "json");
			
			 }
			 	return false;
		});
		
		

	};
	return {
		init : function() {
			handelSearch();
			handleLogin();
			handleAdd();
			handleUpdate();
			handleUnbindDevice();
			handleDeleteUser();
			
		}

	};

}();