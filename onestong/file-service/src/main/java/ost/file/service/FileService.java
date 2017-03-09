package ost.file.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.common.tools.exception.ApiException;
import org.common.tools.image.ImageUtils;
import org.common.tools.resources.ResourceHelper;
import org.common.tools.spring.UniqueTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.joran.conditional.IfAction;
import ost.file.model.vo.UploadedFile;
import ost.file.properties.OstFileProperties;

@Service
public class FileService {

	@Autowired
	private OstFileProperties properties;

	public UploadedFile save(MultipartFile _file, String box) throws Exception {
		UploadedFile file = new UploadedFile();
		// 附件照片 可选
		if (null != _file && _file.getSize() > 0) {
			String orginalName = _file.getOriginalFilename();
			String suffix = orginalName.substring(orginalName.lastIndexOf("."), orginalName.length());

			String fileName = ResourceHelper.saveFile(_file,
					properties.getFilePath() + File.separator + (StringUtils.isNotEmpty(box) ? box : ""));

			if (StringUtils.isNotEmpty(box)) {
				fileName = box + File.separator + fileName;
			}
			// if ((suffix.equalsIgnoreCase(".jpg") ||
			// suffix.equalsIgnoreCase(".png") ||
			// suffix.equalsIgnoreCase(".bmp"))
			// && properties.getCompress()) {
			// // 图片需要压缩 44*3 44*
			// String thumPic = new
			// StringBuilder(fileName).insert(fileName.lastIndexOf("."),
			// "_thum").toString();
			// ImageUtils.compressJpegFile(properties.getFilePath() +
			// File.separator + fileName,
			// properties.getFilePath() + File.separator + thumPic,
			// properties.getCompressRate());
			// // 小图 文件名
			//
			// file.setFidThum(thumPic);
			//
			// }
			file.setFid(fileName);
			file.setFileName(orginalName);
			file.setSize(String.valueOf(_file.getSize()));
			file.setType(suffix);
		}

		return file;
	}

	public List<UploadedFile> saveAll(MultipartFile[] _file, String box) throws Exception {
		List<UploadedFile> files = new ArrayList<UploadedFile>();
		for (int i = 0; i < _file.length; i++) {
			UploadedFile _uploadedFile = this.save(_file[i], box);
			files.add(_uploadedFile);
		}
		return files;
	}

	public ResponseEntity<InputStreamResource> getFile(String fid) throws IOException {

		String filePath = this.properties.getFilePath() + File.separator + fid;
		FileSystemResource file = new FileSystemResource(filePath);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(file.getInputStream()));

	}

	public UploadedFile zipFiles(List<UploadedFile> files) {
		
		if(CollectionUtils.isEmpty(files)){
			throw new ApiException("参数不完全");
		}
		String basePath = files.get(0).getFid();
		
		List<String > allFiles  = files.stream().map(file-> properties.getFilePath() + File.separator+file.getFid()).collect(Collectors.toList());
		String zipFileParent = allFiles.get(0).substring(0,allFiles.get(0).lastIndexOf(File.separator));
		
		String zipFileName = UniqueTokenGenerator.generateUniqueToken() +".zip";
		
		this.ZipMultiFile(allFiles, zipFileParent+File.separator + zipFileName);
		
		UploadedFile file  = new  UploadedFile();
		file.setFid(basePath.substring(0, basePath.lastIndexOf(File.separator)) + File.separator + zipFileName );
		file.setFileName(zipFileName);
		file.setType(".zip");
		file.setSize(new File(zipFileParent+File.separator + zipFileName).length()+"");
		return file;
	}

	public void ZipMultiFile(List<String> files, String zippath) {
		try {
			File zipFile = new File(zippath);
			InputStream input = null;
			ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
			for (int i = 0; i < files.size(); ++i) {
				input = new FileInputStream(new File(files.get(i)));
				zipOut.putNextEntry(new ZipEntry(files.get(i)));
				int temp = 0;
				while ((temp = input.read()) != -1) {
					zipOut.write(temp);
				}
				input.close();
			}
			zipOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
