var role = function(){
	var handleBackToList = function(){
		$("#backTolist",$("#dept_form")).on("click",function(){
			
			window.location.href=app.ctx()+"/department/list";
		});
		
	};
	
	var updateRole= function(){
		$(".role_update_btn").each(function(i,d){
				
			$(d).click(function(){
				$tr = $(this).parents("tr");
				$roleId = $.trim($tr.attr("id"));
				
				window.location.href=app.ctx()+"/role/update/"+$roleId;
			
				
			});
			
		});
		
	};
	
	
	var getMoudlePerms= function(obj){
		$array= [];
		
		
		
		$("input[name='"+obj+"']:checked").each(function(){
            
            $array.push($(this).val());
        });
           
       return $array;
		
		
	};
	
	var saveRole = function() {
		$("#role_add_btn,#role_update_btn").on(
				"click",
				function() {
					$rf = $(this).parents("form");
					$action = $.trim($rf.attr("action"));
					
					 var saveDataAry=[];  
				        var data1={"roleName":$("input[name='roleName']",$rf).val()};  
				        var data2={"ost_moudle_users":getMoudlePerms("ost_moudle_users")};  
				        var data3={"ost_moudle_depts":getMoudlePerms("ost_moudle_depts")};  
				        var data4={"ost_moulde_roles":getMoudlePerms("ost_moulde_roles")};  
				        var data5={"ost_moudle_chart":getMoudlePerms("ost_moudle_chart")};  
				        var data6={"ost_moudle_p_tags":getMoudlePerms("ost_moudle_p_tags")};  
				        var data7={"ost_moudle_c_tags":getMoudlePerms("ost_moudle_c_tags")}; 
				        var data8={"ost_moudle_worktime":getMoudlePerms("ost_moudle_worktime")}; 
				        
				        
				        
				        //app模块
				        var data9={"ost_app_moudle_events":getMoudlePerms("ost_app_moudle_events")};
				        var data10={"ost_app_moudle_my":getMoudlePerms("ost_app_moudle_my")};
				        
				        var data11={"ost_app_moudle_find":getMoudlePerms("ost_app_moudle_find")};
				        //其他
				        var data12= {"roleId":$("input[name='roleId']",$rf).val()};//roleid
				        var data13= {"level":$("input[name='level']",$rf).val()};//级别
				        
				        saveDataAry.push(data1);  
				        saveDataAry.push(data2);
				        saveDataAry.push(data3);
				        saveDataAry.push(data4);
				        saveDataAry.push(data5);
				        saveDataAry.push(data6);
				        saveDataAry.push(data7);
				        saveDataAry.push(data8);
				        saveDataAry.push(data9);
				        saveDataAry.push(data10);
				        saveDataAry.push(data11);
				        saveDataAry.push(data12);
				        saveDataAry.push(data13);
					
				      console.log(JSON.stringify(saveDataAry));
					$.ajax({ 
			            type:"POST", 
			            url:$action + "?" + Math.floor(Math.random() * 100), 
			            dataType:"json",      
			            contentType:"application/json",               
			            data:JSON.stringify(saveDataAry), 
			            success:function(data){ 
			            	if (data.statusCode == HTTP200
									&& data.statusCode != undefined) {
								alert(data.description);
								window.location.href = app.ctx()
										+ "/role/list";

							} else {
								alert(data.description);

							}            
			            } 
			         }); 
					
					
				});

	};

	var handleUpdate = function(){
		$modalBody  =  $("#dept_info_update");
		 $modalBody.on("open.modal.amui",function(data){

			
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

		$("#btn_role_search").on("click", function() {
			$form = $("#page");
			$("input[name='roleName']", $form).val($("#roleName").val());
			$form.submit();
		});

	};
	return {
		
		init:function(){
			handelSearch();
			saveRole();
			updateRole();
		}
		
	};
}();