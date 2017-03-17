(function($) {
  'use strict';

  $(function() {
    var $fullText = $('.admin-fullText');
    $('#admin-fullscreen').on('click', function() {
      $.AMUI.fullscreen.toggle();
    });

    $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
      $.AMUI.fullscreen.isFullscreen ? $fullText.text('关闭全屏') : $fullText.text('开启全屏');
    });
    //logout
    $(".quit").click(function() {
		window.location.href = app.ctx() + "/logout";

	});
    
    $(".admin_logout").click(function() {
		window.location.href = app.ctx() + "/ost/admin/logout";

	});
    //公共调用的 alert
    $('#alert').on('open.modal.amui', function(data){
    	var model = $("#alert");
    	  var target = data.relatedTarget;
    	  $("#title",model).text(target.title);
    	  $("#content",model).text(target.content);
    	});
    
    $('#modal-loading').on('closed.modal.amui', function(data){
    	$(this).removeData('am.modal');
    	});
   
  });
})(jQuery);
var OPARATION_SUCCESS = "1";
var OPARATION_FAILED = "0";

var HTTP200 ="200";
var app = function(){
	var handleLogout = function() {

		$(".quit").click(function() {
			window.location.href = app.ctx() + "/logout";

		});

	};
	//var 
	
	var getCtx = function(){
		return  $.trim($("#ctx").val());
		
	};
	return {
		
		ctx:function(){
			
			return getCtx();
		},
		getCheckboxValue:function(){
			
			
		},
		openWait:function(){
			$("#modal-loading").modal("toggle");
			
		},
		closeWait:function(){
			$("#modal-loading").modal("toggle");
			
		},
		kanoukenAlert:function(obj,calConfirm){
			$('#alert').modal({
                relatedTarget: obj,
                onConfirm:calConfirm
              });
			
		}
		
	};
	
}();