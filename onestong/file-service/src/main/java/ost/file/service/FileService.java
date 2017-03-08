package ost.file.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ost.file.model.vo.UploadedFile;
import ost.file.properties.OstFileProperties;

@Service
public class FileService {

	@Autowired
	private OstFileProperties properties;

	public UploadedFile save(MultipartFile _file) {

		UploadedFile file = new UploadedFile();
		return file;
	}

	public List<UploadedFile> saveAll(MultipartFile[] _file) {
		UploadedFile file = new UploadedFile();
		List<UploadedFile> files = new ArrayList<UploadedFile>();
		files.add(file);
		return files;
	}

	public ResponseEntity<InputStreamResource> getFile(String fid) throws IOException {

		String filePath = this.properties.getFilePath() + fid;
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
		// TODO Auto-generated method stub
		return null;
	}

	public void ZipMultiFile(File[] files, String zippath) {
		try {
			File zipFile = new File(zippath);
			InputStream input = null;
			ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
			for (int i = 0; i < files.length; ++i) {
				input = new FileInputStream(files[i]);
				zipOut.putNextEntry(new ZipEntry(properties.getFilePath() + File.separator + files[i].getName()));
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
