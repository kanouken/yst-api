

/**
 * Created by xnq on 14-6-4.
 */
var page = function() {
    // 采用通用配置
    var handleButton = function (){



    };
    var highLigtIndex =function(index){
    	//jQuery(".index").each
    	
    }
    
    
    
    return {
        init : function() {
            handleButton();
            jQuery('.index').each(function() {
            	//当前页index hover 样式
//            	 if(parseInt(jQuery(this).text().trim())==parseInt(jQuery("#curPage").val())){
//            		 jQuery(this).css("background","#5c9600").css("color","#fff");
//                 	
//                 }
                // console.log(jQuery(this).text());
                jQuery(this).click(function(e) {
                    jQuery("#curPage").val(jQuery(this).text());
                    jQuery("#page").submit();
                    // page.form.submit();
                    // alert("tiaozhuan");
                   

                });

               

               

            });
            jQuery("#pre").click(function() {
                var tmp =jQuery("#curPage").val()-1;
                  
                if(tmp==0){
              	  
              	  return;
                }
                jQuery("#curPage").val(tmp);
                  jQuery("#page").submit();
              });
            jQuery("#next").click(function() {
                var tmp = parseInt(jQuery("#curPage").val())+1;
            	if(tmp>parseInt(jQuery("#pageCount").val())){
            		
            		return;
            	}
            	jQuery("#curPage").val(tmp);
                jQuery("#page").submit();
            });
            
            //首页
            jQuery("#home").click(function() {
              
                jQuery("#curPage").val('1');
                  jQuery("#page").submit();
              });
          //末页
            jQuery("#end").click(function() {
              
                jQuery("#curPage").val(parseInt(jQuery("#pageCount").val()));
                  jQuery("#page").submit();
              });

        }

    }

}();