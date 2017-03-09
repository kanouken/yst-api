package ost.file.controller;

import java.io.IOException;
import java.util.List;

import org.common.tools.OperateResult;
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
	public OperateResult<UploadedFile> upload(@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "box", required = false) String box) throws Exception {
		return new OperateResult<UploadedFile>(fileService.save(file, box));
	}

	@PostMapping(value = "file/uploads")
	public OperateResult<List<UploadedFile>> upload(@RequestParam(value = "file") MultipartFile[] files,
			@RequestParam(value = "box", required = false) String box) throws Exception {
		return new OperateResult<List<UploadedFile>>(fileService.saveAll(files, box));
	}

	@GetMapping(value = "file/download")
	public ResponseEntity<InputStreamResource> upload(@RequestParam(value = "id") String fid) throws IOException {
		return fileService.getFile(fid);
	}

	@PostMapping(value = "file/zip")
	public OperateResult<UploadedFile> upload(@RequestBody List<UploadedFile> files) {
		return new OperateResult<UploadedFile>(fileService.zipFiles(files));
	}
}
