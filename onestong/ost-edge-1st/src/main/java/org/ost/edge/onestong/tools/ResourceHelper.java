package org.ost.edge.onestong.tools;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.common.tools.spring.UniqueTokenGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件资源帮助类
 * @author wangliang
 *
 */
public class ResourceHelper {
	/**
	 * 保存文件
	 * @param file 文件
	 * @return String 保存后的文件名称 和后缀
	 * @throws IOException
	 */
	public static String saveFile(MultipartFile file, String seed)throws IOException{
		int index = seed.indexOf("@");
		String orginalName =  file.getOriginalFilename();
		String suffix = orginalName.substring(orginalName.lastIndexOf("."),orginalName.length());
		if(index != -1){
			seed = seed.substring(0, index);
		}
		seed = seed.replace(".", "_");
		String filename = UniqueTokenGenerator.generateUniqueFileName(seed);
		File newfile = new File(Constants.FILE_SAVE_DIR, filename+suffix);
		try {
			FileUtils.writeByteArrayToFile(newfile, file.getBytes());
		} catch (IOException e) {
			throw e;
		}
		return filename+suffix;
	}
	
	/**
	 * 下载文件
	 * @param filename 文件名称
	 * @return ResponseEntity<byte[]> 文件流
	 * @throws IOException
	 */
	public static ResponseEntity<byte[]> downloadFile(String filename,String type)throws IOException{
		String path = Constants.FILE_SAVE_DIR + filename+"."+type;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", filename+"."+type);
		try {
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path)), headers, HttpStatus.CREATED);
		} catch (IOException e) {
			throw e;
		}
	}
	public static void main(String[] args) {
		String fileName ="sfsfsf.jpg";
		String thumPic  = new StringBuilder(fileName).insert(fileName.lastIndexOf("."), "_thum").toString();
		
		System.out.println(thumPic);
	}
	
}
