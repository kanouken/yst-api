var projectTag = function() {
	var handleBackToList = function(){
		$(".backToList").on("click",function(){
			
			window.location.href=app.ctx()+"/tags/project/list";
		});
		
	};
	
	var  handleShowToggle  = function(){
		$("#toggleToList").click(function(){
			$tagId = $("input[name='tagId']").val();
			window.location.href=app.ctx()+"/tags/project/relateEvents/"+$tagId+"/list";
		});
		
		$("#toggleToTimeLine").click(function(){
			$tagId = $("input[name='tagId']").val();
			window.location.href=app.ctx()+"/tags/project/relateEventsTimeLine/"+$tagId+"/list";
		});
		
		
	};
	var handleRelatedEvents = function(){
		$(".showRelateEvent").each(function(i,d){
			$(d).on("click",function(){
				$tr = $(this).parents("tr");
				$tagId = $.trim($tr.attr("id"));
				window.location.href=app.ctx()+"/tags/project/relateEventsTimeLine/"+$tagId+"/list";
			});
		});
		
	};
	
	var handelSearch = function() {

		$("#btn_pTag_search").on("click", function() {
			$form = $("#page");
			$("input[name='pTName']", $form).val($("#pTName").val());
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