package org.common.tools.resources;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.common.tools.spring.UniqueTokenGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件资源帮助类
 * 
 * @author wangliang
 *
 */
public class ResourceHelper {
	protected static Log logger = LogFactory.getLog(ResourceHelper.class);

	/**
	 * 保存文件
	 * 
	 * @param file
	 *            文件
	 * @return String 保存后的文件名称 和后缀
	 * @throws IOException
	 */
	public static String saveFile(MultipartFile file, String parentFath) throws IOException {

		String orginalName = file.getOriginalFilename();
		String suffix = orginalName.substring(orginalName.lastIndexOf("."), orginalName.length());
		String filename = UniqueTokenGenerator.generateUniqueToken();
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String tmp = df.format(now);
		File parent = new File(parentFath + File.separator + tmp);
		logger.info("write filet parent = " + parent);
		if (!parent.exists()) {
			logger.info("write filet create path = " + parent);
			parent.mkdirs();
		}
		File newfile = new File(parent, filename + suffix);
		filename = tmp +File.separator+ filename + suffix;
		logger.info("write file name = " + newfile);
		try {
			FileUtils.writeByteArrayToFile(newfile, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("write file name = " + newfile, e);
		}
		return filename;
	}

	/**
	 * 下载文件
	 * 
	 * @param filename
	 *            文件名称
	 * @return ResponseEntity<byte[]> 文件流
	 * @throws IOException
	 */
	@Deprecated
	public static ResponseEntity<byte[]> downloadFile(String filename, String type) throws IOException {
		// String path = Constants.FILE_SAVE_DIR + filename+"."+type;
		// HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// headers.setContentDispositionFormData("attachment",
		// filename+"."+type);
		// try {
		// return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new
		// File(path)), headers, HttpStatus.CREATED);
		// } catch (IOException e) {
		// throw e;
		// }
		return null;
	}

	public static void test(String[] args) {
		String fileName = "sfsfsf.jpg";
		String thumPic = new StringBuilder(fileName).insert(fileName.lastIndexOf("."), "_thum").toString();

		System.out.println(thumPic);
	}

}
