package org.common.tools.string;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 本类提供字符串的自定义处理方式
 * 
 * 
 * @author
 * @version 1.0
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class StringUtil {

	private StringUtil() {
	}

	/**
	 * 干掉回车符<\n>，干掉空格<\s>，干掉换行<\r>，干掉制表符<\t>
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String ing = "\\s*|\n|\r|\t";
		if (str != null) {
			Pattern pt = Pattern.compile(ing);
			Matcher mt = pt.matcher(str);
			str = mt.replaceAll("");
		}
		return str;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotNull(Object s) {
		if (s != null && !s.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否为Int型
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isIntChar(String s) {
		String st = "0123456789";
		if (s.length() == 0) {
			return false;
		}
		int j = 0;
		for (int i = 0; i < s.length(); i++) {
			j = st.indexOf(s.charAt(i));
			if (j == -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 补齐4位，并返回4位字符串
	 * 
	 * @param i
	 * @return
	 */
	public String get4Codevalue(long i) {
		String s = String.valueOf(i);
		if (s.length() == 1) {
			s = "000" + i;
		}
		if (s.length() == 2) {
			s = "00" + i;
		}
		if (s.length() == 3) {
			s = "0" + i;
		}
		return s;
	}

	/**
	 * 补齐length位，并返回length位字符串
	 * 
	 * @param i
	 * @param length
	 * @return
	 */
	public static String getCodevalue(long i, int length) {
		String s = String.valueOf(i);
		String m = "";
		for (int n = 0; n < (length - s.length()); n++) {
			m = m + "0";
		}
		return m + s;
		/*
		 * if (s.length() == 1) { s = "000" + i; } if (s.length() == 2) { s =
		 * "00" + i; } if (s.length() == 3) { s = "0" + i; } return s;
		 */
	}

	public static String genRandomColor(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'R', 'G', 'B', 'Y', 'W' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}

		return pwd.toString();
	}

	public static String genRandomOnlyNum(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}

		return pwd.toString();
	}

	/**
	 * 生成随即密码
	 * 
	 * @param pwd_len
	 *            生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String genRandomNum(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}

		return pwd.toString();
	}

	/**
	 * 把加密后的字符串控制在hexDigits[]出现过的字符内
	 */
	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	/**
	 * 把字符串通过MD5加密
	 * 
	 * @param s
	 *            将要被加密的字符串
	 * @return 被加密后的字符串
	 * @throws Exception
	 */
	public static String MD5(String s) throws Exception {
		MessageDigest messagedigest = MessageDigest.getInstance("MD5");
		messagedigest.reset();
		byte abyte0[] = messagedigest.digest(s.getBytes());
		return byteToString(abyte0);
	}

	/**
	 * 把字节变为String字符串
	 * 
	 * @param abyte0
	 *            将要变字符串的字节
	 * @return 转换后的字符串
	 */
	private static String byteToString(byte abyte0[]) {
		int i = abyte0.length;
		char ac[] = new char[i * 2];
		int j = 0;
		for (int k = 0; k < i; k++) {
			byte byte0 = abyte0[k];
			ac[j++] = hexDigits[byte0 >>> 4 & 0xf];
			ac[j++] = hexDigits[byte0 & 0xf];
		}

		return new String(ac);
	}

	public static String convertStr(String s)

	{
		String s1 = null;
		try {
			if (s == null) {
				s1 = "";
			} else {
				s1 = new String(s.getBytes("ISO8859_1"), "GBK").trim();
			}

		} catch (Exception e) {
			return s;
		}
		return s1;
	}

	public static String convertSqlStr(String s) throws Exception {
		if (s == null)
			return "";
		else
			return s;
	}

	public static String convertSqlStr(String s, String i) throws Exception {
		String s1 = null;
		if (s == null) {
			return "";
		} else {
			if (i != null && i.equals("1")) {
				s1 = new String(s.getBytes("GBK"));
			} else if (i != null && i.equals("2")) {
				s1 = new String(s.getBytes(), "GBK");
			} else if (i != null && i.equals("3")) {
				s1 = new String(s.getBytes("GBK"), "ISO8859_1");
			} else if (i != null && i.equals("4")) {
				s1 = new String(s.getBytes("ISO8859_1"));
			} else if (i != null && i.equals("5")) {
				s1 = new String(s.getBytes(), "ISO8859_1");
			} else if (i != null && i.equals("6")) {
				s1 = new String(s.getBytes("ISO8859_1"), "GBK");
			} else {
				s1 = s;
			}

			return s1;
		}
	}

	public static String formatTitle(String s, int i) throws Exception {
		s = s.trim();
		if (s.length() >= i)
			if (i >= 3)
				s = s.substring(0, i - 3).trim() + "...";
			else
				s = s.substring(0, i).trim();
		return s;
	}

	/**
	 * 时间戳的后n位的字符串
	 * 
	 * @param 截取时间的后i位
	 * @return 数字字符串
	 * @throws Exception
	 */
	public static String getName(int i) throws Exception {
		Date d = new Date();
		String s = String.valueOf(d.getTime());
		s = s.substring(s.length() - i, s.length());
		return s;
	}

	public static boolean isNullOrEmpty(Object obj) {
		return obj == null || "".equals(obj.toString());
	}

	public static String toString(Object obj) {
		if (obj == null)
			return "null";
		return obj.toString();
	}

	@SuppressWarnings("rawtypes")
	public static String join(Collection s, String delimiter) {
		StringBuffer buffer = new StringBuffer();
		Iterator iter = s.iterator();
		while (iter.hasNext()) {
			buffer.append(iter.next());
			if (iter.hasNext()) {
				buffer.append(delimiter);
			}
		}
		return buffer.toString();
	}

	/**
	 * 创建页面
	 * 
	 * @param path
	 * @param title
	 * @param author
	 * @param content
	 * @return
	 */
	public static String createHtml(String path, String filepath, String fileName, String content) {
		try {
			// 模板路径
			String filePath = path + fileName;
			String templateContent = "";
			FileInputStream fileinputstream = new FileInputStream(filePath);// 读取模板文件
			int lenght = fileinputstream.available();
			byte bytes[] = new byte[lenght];
			fileinputstream.read(bytes);
			fileinputstream.close();
			templateContent = new String(bytes);
			templateContent = templateContent.replaceAll("###content###", content);
			System.out.print(templateContent);

			// 根据时间得文件名
			Calendar calendar = Calendar.getInstance();
			String files = String.valueOf(calendar.getTimeInMillis()) + ".html";
			String fileame = filepath + files;// 生成的html文件保存路径。
			FileOutputStream fileoutputstream = new FileOutputStream(fileame);// 建立文件输出流
			byte tag_bytes[] = templateContent.getBytes();
			fileoutputstream.write(tag_bytes);
			fileoutputstream.close();
			return files;
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		return "";
	}

	/**
	 * 时间戳转换为年月日时间格式 string to string
	 * 
	 * @param time
	 * @return
	 */
	public static final String stampToDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd = sdf.format(new Date(Long.parseLong(time)));
		return sd;
	}

	/**
	 * 方法:处理URL // 替换为\
	 * <p>
	 * 描述 : 将字符串中的html转义字符进行转义
	 * </p>
	 * <p>
	 * 备注 :
	 * </p>
	 * 
	 * @param srcStr
	 *            含有html要转义的字符的字符串
	 * @return String
	 */
	public static String urlRep(String srcStr) {
		// srcStr = srcStr.replaceAll("/", ConstantsInfo.FILE_SEPARATOR);
		srcStr = srcStr.replaceAll("\\\\", "/");
		return srcStr;
	}
	// public static void main(String[] args) throws Exception{
	// System.out.println(StringUtil.getName(14));
	// }

	/**
	 * 中文數字转阿拉伯数组【十万九千零六十 --> 109060】
	 * 
	 * @author 雪见烟寒
	 * @param chineseNumber
	 * @return
	 */
	public static int chineseNumber2Int(String chineseNumber) {
		int result = 0;
		int temp = 1;// 存放一个单位的数字如：十万
		int count = 0;// 判断是否有chArr
		char[] cnArr = new char[] { '一', '二', '三', '四', '五', '六', '七', '八', '九' };
		char[] chArr = new char[] { '十', '百', '千', '万', '亿' };
		for (int i = 0; i < chineseNumber.length(); i++) {
			boolean b = true;// 判断是否是chArr
			char c = chineseNumber.charAt(i);
			for (int j = 0; j < cnArr.length; j++) {// 非单位，即数字
				if (c == cnArr[j]) {
					if (0 != count) {// 添加下一个单位之前，先把上一个单位值添加到结果中
						result += temp;
						temp = 1;
						count = 0;
					}
					// 下标+1，就是对应的值
					temp = j + 1;
					b = false;
					break;
				}
			}
			if (b) {// 单位{'十','百','千','万','亿'}
				for (int j = 0; j < chArr.length; j++) {
					if (c == chArr[j]) {
						switch (j) {
						case 0:
							temp *= 10;
							break;
						case 1:
							temp *= 100;
							break;
						case 2:
							temp *= 1000;
							break;
						case 3:
							temp *= 10000;
							break;
						case 4:
							temp *= 100000000;
							break;
						default:
							break;
						}
						count++;
					}
				}
			}
			if (i == chineseNumber.length() - 1) {// 遍历到最后一个字符
				result += temp;
			}
		}
		return result;
	}

	public static String numToChinaNum(int d) {
		String[] str = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" ,"十","十一","十二"};
		return str[d]; 
		
	}

}
