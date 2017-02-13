package org.ost.edge.onestong.controller.api.resource;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.common.resouces.ResourceService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/resource")
public class ResourceDataApi extends Action {

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private UsersService usersService;

	/**
	 * 下载资源 请求方式 GET
	 * 
	 * @param filename
	 *            下载图片名称
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/events/file/{filename}/{type}")
	public void downloadFile(@PathVariable("filename") String filename,
			@PathVariable("type") String type, HttpServletResponse res) {
		res.reset();
		File f = null;
		OutputStream out = null;
		try {
			String path = Constants.FILE_SAVE_DIR + filename + "." + type;
			res.addHeader("Content-Disposition", "attachment;filename=\""
					+ new String((filename + "." + type).getBytes("GBK"),
							"ISO8859_1") + "\"");
			out = res.getOutputStream();
			f = new File(path);
			if (f.exists()) {
				out.write(FileUtils.readFileToByteArray(new File(path)));
			} else {
				//out.write(null);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {

				logger.error("文件下载失败", e);
			}
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/user/pic/{userId}")
	public void findHeadPicByUser(@PathVariable("userId") Integer userId,
			HttpServletResponse res) {
		res.reset();
		File f = null;
		OutputStream out = null;
		User user = null;
		try {
			user = this.usersService.findOneById(userId);

			if (null == user) {

				return;

			}
			String path = Constants.FILE_SAVE_DIR + user.getPic();
			f = new File(path);
			res.addHeader("Content-Disposition", "attachment;filename=\""
					+ new String((user.getPic()).getBytes("GBK"), "ISO8859_1")
					+ "\"");
			out = res.getOutputStream();
			if (f.exists()) {
				out.write(FileUtils.readFileToByteArray(f));
			} else {
				//out.write(null);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("获取用户头像失败", e);
			}
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
