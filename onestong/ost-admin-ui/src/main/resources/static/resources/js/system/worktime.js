var worktime = function(){
	var handleUpdate =function(){
		 $("#wt_update_form").on("submit",function(event){
			 if(event.result){
			//ajax post
			$action = $.trim($(this).attr("action"));
			$.post($action + "?" + Math.floor(Math.random() * 100), $
					.param($(this).serializeArray()), function(data) {
				if (data != undefined) {

					if (data.statusCode == HTTP200
							&& data.statusCode != undefined) {
						alert(data.description);

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
		handleUpdate();	
			
		}
		
	};
	
}();