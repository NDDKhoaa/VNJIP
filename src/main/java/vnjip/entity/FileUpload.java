package vnjip.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import vnjip.model.BaseModel;

@Entity
@Table(name = "fileupload")
public class FileUpload {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_number", nullable = false, unique = true)
	private Long fileNumber;
	@Column(name = "file_name", nullable = true, unique = true, length = 512)
	private String fileName;
	@Column(name = "folder_name", nullable = true, length = 512)
	private String folderName;
	@Lob
	@Column(name = "content", nullable = true)
	private byte[] content;
	@Column(name = "size", nullable = true)
	private long size;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "date_upload", nullable = true)
	private Date dateUpload;

	public FileUpload() {
		super();
	}

	public FileUpload(BaseModel model) {
		super();
		this.fileName = model.getFileName();
		this.folderName = model.getFilefolderName();
		this.content = model.getFilecontent();
		this.size = model.getFilesize();
		this.dateUpload = model.getFileDateUpload();
	}

	public Date getDateUpload() {
		return dateUpload;
	}

	public void setDateUpload(Date dateUpload) {
		this.dateUpload = dateUpload;
	}

	public Long getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(Long fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}