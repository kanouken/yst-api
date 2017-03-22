var customerTag = function() {
	var handleBackToList = function(){
		$(".backToList").on("click",function(){
			
			window.location.href=app.ctx()+"/tags/customer/list";
		});
		
	};
	
	var  handleShowToggle  = function(){
		$("#toggleToList").click(function(){
			$tagId = $("input[name='tagId']").val();
			window.location.href=app.ctx()+"/tags/customer/relateEvents/"+$tagId+"/list";
		});
		
		$("#toggleToTimeLine").click(function(){
			$tagId = $("input[name='tagId']").val();
			window.location.href=app.ctx()+"/tags/customer/relateEventsTimeLine/"+$tagId+"/list";
		});
		
		
	};
	var handleRelatedEvents = function(){
		$(".showRelateEvent").each(function(i,d){
			$(d).on("click",function(){
				$tr = $(this).parents("tr");
				$tagId = $.trim($tr.attr("id"));
				window.location.href=app.ctx()+"/tags/customer/relateEventsTimeLine/"+$tagId+"/list";
			});
		});
		
	};
	
	var handelSearch = function() {

		$("#btn_cTag_search").on("click", function() {
			$form = $("#page");
			$("input[name='cTName']", $form).val($("#cTName").val());
			$("input[name='curPage']",$form).val(1);
			$form.submit();
		});

	};
	
	return {
		init : function() {
			handelSearch();
			handleRelatedEvents();
			handleShowToggle();
			handleBackToList();
		}

	};

}();