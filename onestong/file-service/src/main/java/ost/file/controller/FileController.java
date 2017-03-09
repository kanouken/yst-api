package ost.file.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ost.file.model.vo.UploadedFile;
import ost.file.service.FileService;

@RestController
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping(value = "file/upload")
	public Object upload(@RequestParam(value = "file") MultipartFile file) {
		return fileService.save(file);
	}

	@PostMapping(value = "file/uploads")
	public Object upload(@RequestParam(value = "file") MultipartFile[] files) {
		return fileService.saveAll(files);
	}

	@GetMapping(value = "file/download")
	public ResponseEntity<InputStreamResource> upload(@RequestParam(value = "id") String fid) throws IOException {
		return fileService.getFile(fid);
	}
	
	
	@GetMapping(value = "file/zip")
	public  UploadedFile  upload(@RequestBody List<UploadedFile> files)  {
		return fileService.zipFiles(files);
	}
}
