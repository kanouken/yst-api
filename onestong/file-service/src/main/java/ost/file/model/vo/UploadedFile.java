package ost.file.model.vo;

public class UploadedFile {
	private String fid;

	private String fidThum;
	private String fileName;

	private String type;

	private String size;

	public String getFidThum() {
		return fidThum;
	}

	public void setFidThum(String fidThum) {
		this.fidThum = fidThum;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
