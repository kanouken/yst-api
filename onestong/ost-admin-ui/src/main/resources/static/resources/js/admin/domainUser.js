var domain = function(){
	var handlePurchaseHistory =function(){
		$(".history").each(function(i,d){
			$(d).on("click",function(){
				
				$tr = $(this).parents("tr");
				$domainId = $.trim($tr.attr("id"));
				window.location.href=app.ctx()+"/ost/domain/order/history/list.html?domainId="+$domainId;
				
			});
			
		});
		
	};
	
	//添加激活码
		
	var handleAddActiveCode = function(){
		$modalBody  =  $("#domain_activeCode_add");
		 $modalBody.on("open.modal.amui",function(data){

			  var  domainId = data.relatedTarget.domainId;
			  if(domainId == undefined){
				  
				  var  target = data.relatedTarget;
				  
				  domainId =   $.trim(target.getAttribute("data-id"));
			  }
			 $ctx =  app.ctx();
			 $.getJSON($ctx+"/ost/domainUser/preAddActiveCode?"+Math.floor(Math.random() * 100),{domainId:domainId},function(data){
				console.log(data);
				  if(data != undefined){
					  if(data.statusCode == HTTP200){
						  var domain = data.data;
						  $("input[name='domainId']",$modalBody).val(domain.domainId);
						  $("#domain_active_add_title",$modalBody).text(domain.domainName);
						
					  }
					  
				  }   
				 
			 });
			 
		 });
		
		 $("#activeCodeAdd").on("submit",function(event){
			
			 	if(event.result){
			 		$action = $.trim($(this).attr("action"));
					$.post($action + "?" + Math.floor(Math.random() * 100), $
							.param($(this).serializeArray()), function(data) {
						if (data != undefined) {

							if (data.statusCode == HTTP200
									&& data.statusCode != undefined) {
								alert(data.description);
								$("#page").submit();
							} else {
								alert(data.description);

							}
						}

					}, 'json');
			 		
			 	}
			 	
				
				return false;
			
		 });
		
		
		
		
		
	};
	
	var handleUpdate =function(){
		
		$(".edit_domain_btn").each(function(i,d){
			$(d).on("click",function(){
				$tr = $(this).parents("tr");
				$domainId = $.trim($tr.attr("id"));
				window.location.href=app.ctx()+"/ost/domainUser/update.html?domainId="+$domainId;
			});
			
		});
	};
	var handleSave = function(){
		 $("#domain_form,#domain_update_form").on("submit",function(event){
			 if(event.result){
				 
				 
			 
			//ajax post
			$action = $.trim($(this).attr("action"));
			$.post($action + "?" + Math.floor(Math.random() * 100), $
					.param($(this).serializeArray()), function(data) {
				if (data != undefined) {

					if (data.statusCode == HTTP200
							&& data.statusCode != undefined) {
						alert(data.description);
						window.location.href = app.ctx()
								+ "/ost/domainUser/list";

					} else {
						alert(data.description);

					}
				}

			}, 'json');
			 }
			
			return false;
			
			
		});
		
	};
	
	return{
		init:function(){
			handleSave();
			handlePurchaseHistory();
			handleUpdate();
			handleAddActiveCode();
			
		}
		
	};
}();