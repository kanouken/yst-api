var dept = function() {
	
	var handleBackToList = function(){
		$("#backTolist",$("#dept_form_add")).on("click",function(){
			
			window.location.href=app.ctx()+"/department/list";
		});
		
	};
	var saveDept = function() {
		$("#dept_form_add").on(
				"submit",
				function(event) {
					
					 if(event.result){
						 app.openWait();
						 
					setTimeout(function(){
						$df = $("#dept_form_add");
						$action = $.trim($df.attr("action"));
						$.ajax({
			    			url:$action+"?"+Math.floor(Math.random() * 100),
			    			data:$.param($df.serializeArray()),
			    			dataType:"json",
			    			type:'post',
			    			async:false,
			    			success:function(data){
			    				app.closeWait();
			    				if (data.statusCode == HTTP200
										&& data.statusCode != undefined) {
									
			    					app.kanoukenAlert({title:"温馨提示",content:data.description},function(){
			    						console.log("sfsfsffsfcc");
			    						window.location.href = app.ctx()
										+ "/department/list";
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
						
					},500)	; 
					 }
					return false;
				});

	};

	var handleUpdate = function(){
		$modalBody  =  $("#dept_info_update");
		 $modalBody.on("open.modal.amui",function(data){

			//console.log(data.relatedTarget);
			  var  deptId = data.relatedTarget.deptId;
			  if(deptId == undefined){
				  
				  var  target = data.relatedTarget;
				  
				  deptId =   $.trim(target.getAttribute("data-id"));
			  }
			 $ctx =  app.ctx();
			 $.getJSON($ctx+"/department/pre_update_department?"+Math.floor(Math.random() * 100),{deptId:deptId},function(data){
				
				  if(data != undefined){
					  if(data.statusCode == OPARATION_SUCCESS){
						  var dept = data.data.dept;
						  var  depts = data.data.departments;
						  $("#deptId",$("#dept_update_form")).val(dept.deptId);
						  $("#name",$("#dept_update_form")).val(dept.name);
						  $("#description",$("#dept_update_form")).val(dept.description);
						  $("#isLeaf",$("#dept_update_form")).val(dept.isLeaf);
						
						  $("#parentId",$("#dept_update_form")).empty();
						for(var i =0;i<depts.length;i++){
							if(depts[i].deptId ==dept.parentId){
								
								$("<option  selected='selected' value='"+depts[i].deptId+"'>" + depts[i].name + "  </option>").appendTo($("#parentId",$("#dept_update_form")));
							}else{
								
								$("<option  value='"+depts[i].deptId+"'>" + depts[i].name + "  </option>").appendTo($("#parentId",$("#dept_update_form")));

							}
						}
						
						
					  }
					  
				  }   
				 
			 });
			 
		 });
		
		
		 $("#dept_update",$("#dept_update_form")).on("click",function(){
			 $df = $(this).parents("form");
				$action = $.trim($df.attr("action"));
				$.post($action + "?" + Math.floor(Math.random() * 100), $
						.param($df.serializeArray()), function(data) {
					if (data != undefined) {

						if (data.statusCode == OPARATION_SUCCESS
								&& data.statusCode != undefined) {
							alert(data.description);
							//刷新 search 页面结果
							$("#page").submit();

						} else {
							alert(data.description);

						}
					}

				}, 'json');
			
		 });
		
		
		
		
		
	};
	
	
	var handelSearch = function() {

		$("#btn_dept_search").on("click", function() {
			$form = $("#page");
			$("input[name='name']", $form).val($("#name").val());
			$form.submit();
		});

	};

	var handleCrud = function() {

		saveDept();
	};

	return {

		init : function() {
			handleBackToList();
			handleCrud();
			
			handelSearch();
			
			handleUpdate();
		}
	};

}();