package ost.file.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ost")
public class OstFileProperties {
	private String filePath;

	private Boolean compress = true;

	private Float compressRate = 1.1f;

	public Boolean getCompress() {
		return compress;
	}

	public void setCompress(Boolean compress) {
		this.compress = compress;
	}

	public Float getCompressRate() {
		return compressRate;
	}

	public void setCompressRate(Float compressRate) {
		this.compressRate = compressRate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
