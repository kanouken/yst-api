package com.oz.onestong.tools;

import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;

/**
 * 唯一标识生成类
 * @author wangliang
 *
 */
public class UniqueTokenGenerator {
	private static Logger logger = Logger.getLogger(UniqueTokenGenerator.class);
	/**
	 * 生成唯一token
	 * @return
	 */
	public static String generateUniqueToken(){
		logger.info("generate unique token.");
		String token = UUID.randomUUID().toString().replace("-", "");
		logger.info("token generate success, token is " + token);
		return token;
	}
	
	/**
	 * 生成唯一文件名
	 * @return
	 */
	public static String generateUniqueFileName(String seed){
		logger.info("generate unique file name.");
		String filename = (seed + System.currentTimeMillis()) + new Random().nextInt(10000);
		logger.info("generate unique file name success, filename is " + filename);
		return filename;
	}
}
