var version = function() {
	var handleUpdate = function(obj) {

		$tr = $(obj).parents("tr");

		$version_id = $.trim($tr.attr("id"));
		$versionNameTd = $tr.children("td").eq(2).children("input");
		$download_urlTd = $tr.children("td").eq(3).children("input");
		$update_now = $tr.children("td").eq(4).children("select");
		// ajax here
		$action = app.ctx() + "/ost/setting/app_version/update";
		$.post($action + "?" + Math.floor(Math.random() * 100), {
			"version_id" : $version_id,
			"version_name" : $versionNameTd.val(),
			"download_url" : $download_urlTd.val(),
			"update_now" : $update_now.val()
		},
				function(data) {
					if (data != undefined) {

						if (data.statusCode == HTTP200
								&& data.statusCode != undefined) {
							alert(data.description);
							window.location.reload();

						} else {
							alert(data.description);

						}
					}
				}, 'json');
		// window.location.reload();
	};

	var initUpdate = function() {
		$(".edit_version_btn")
				.each(
						function(i, d) {
							$(d)
									.on(
											"click",
											function() {

												$confirmBtn = $(this).siblings(
														".confirm_version_btn");
												$confirmBtn.fadeIn();

												$confirmBtn.click(function() {
													handleUpdate($(this));

												});
												$(this).fadeOut();
												$tr = $(this).parents("tr");
												$userId = $
														.trim($tr.attr("id"));
												$versionNameTd = $tr.children(
														"td").eq(2)
												$download_urlTd = $tr.children(
														"td").eq(3);
												$update_now = $tr
														.children("td").eq(4);

												$("<input type='text'  />")
														.val(
																$versionNameTd
																		.html())
														.appendTo(
																$versionNameTd
																		.empty());
												$("<input type='text'  />")
														.val(
																$download_urlTd
																		.html())
														.appendTo(
																$download_urlTd
																		.empty());

												$(
														"<select name='update_now' > <option value='0'>否</option>  <option value='1'>是</option>   </select>")
														.appendTo(
																$update_now
																		.empty());

											});

						});

	};

	return {

		init : function() {
			initUpdate();

		}

	};

}();