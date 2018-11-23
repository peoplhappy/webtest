package org.jtest.app.model.requests;

/**
 * 
 * 记载文件的路径和文件名和contenttype
 * 
 * @author Administrator
 *
 */
public class FileInfo {
	private String fileName;
	private String filePath;
	private String content_type;

	public FileInfo(String fileName, String filePath, String content_type) {
		this.fileName = fileName;
		this.filePath = filePath;
		this.content_type = content_type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

}
