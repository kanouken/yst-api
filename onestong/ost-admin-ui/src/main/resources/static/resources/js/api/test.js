var test =function(){
	var handelTokenToUrl = function(){
		$("#event_signIn").on("submit",function(){
			$form  = $("#event_signIn");
			$token = $("input[name='test_token']",$form).val();
			$ctx = $("#ctx").val();
			
			
			$url = $ctx+"/api/attenceEvent/signIn";
			$form.attr("action",$url+"/"+$.trim($token));
			
			return true;
			
			
		});
		
		
		$("#event_signOut").on("submit",function(){
			$form  = $("#event_signOut");
			$token = $("input[name='test_token']",$form).val();
			$ctx = $("#ctx").val();
			
			
			$url = $ctx+"/api/attenceEvent/signOut";
			$form.attr("action",$url+"/"+$.trim($token));
			
			return true;
			
			
		});
		
		
		$("#event_signStatusForToday").on("submit",function(){
			$form  = $("#event_signStatusForToday");
			$token = $("input[name='test_token']",$form).val();
			$ctx = $("#ctx").val();
			
			
			$url = $ctx+"/api/attenceEvent/signStatusForToday";
			$form.attr("action",$url+"/"+$.trim($token));
			
			return true;
			
			
		});
		
		$("#event_signOutDirectly").on("submit",function(){
			$form  = $("#event_signOutDirectly");
			$token = $("input[name='test_token']",$form).val();
			$ctx = $("#ctx").val();
			
			
			$url = $ctx+"/api/attenceEvent/signOutDirectly";
			$form.attr("action",$url+"/"+$.trim($token));
			
			return true;
			
			
		});
		
		$("#event_records").on("submit",function(){
			$form  = $("#event_records");
			$token = $("input[name='test_token']",$form).val();
			$ctx = $("#ctx").val();
			
			
			$url = $ctx+"/api/attenceEvent/records";
			$form.attr("action",$url+"/"+$.trim($token));
			
			return true;
			
			
		});
		
		
		
		$("#event_visitIn").on("submit",function(){
			$form  = $("#event_visitIn");
			$token = $("input[name='test_token']",$form).val();
			$ctx = $("#ctx").val();
			
			
			$url = $ctx+"/api/visitEvent/visitIn";
			$form.attr("action",$url+"/"+$.trim($token));
			
			return true;
			
			
		});
		
		
		$("#event_visitOut").on("submit",function(){
			$form  = $("#event_visitOut");
			$token = $("input[name='test_token']",$form).val();
			$ctx = $("#ctx").val();
			
			
			$url = $ctx+"/api/visitEvent/visitOut";
			$form.attr("action",$url+"/"+$.trim($token));
			
			return true;
			
			
		});
	};
	
	
	return {
		init:function(){
			
			handelTokenToUrl();
		}
		
		
		
	};
	
}();