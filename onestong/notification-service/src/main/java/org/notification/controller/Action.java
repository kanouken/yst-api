package org.notification.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class Action {
	public static void renderStream(HttpServletResponse res, byte[] s, String contextType) {
		try {
			res.setHeader("Pragma", "no-cache");
			res.setHeader("Cache-Control", "no-cache");
			res.setDateHeader("Expires", 0);
			res.setContentType(contextType);
			res.getOutputStream().write(s);
		} catch (IOException eio) {
		} finally {
			try {
				res.flushBuffer();
			} catch (IOException e) {
			}
		}
	}

}
