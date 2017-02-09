package org.common.tools.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密类
 *
 */
public class Md5Util {
	public static String md5(String plainText) throws NoSuchAlgorithmException {
		StringBuffer buf = new StringBuffer("");
		
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		
//		/**16位的加密*/
//		return buf.toString().substring(8, 24);
		return buf.toString();
	}
}

