package vnjip.services;

import java.util.List;

import vnjip.entity.FileUpload;

public interface FileUploadService {

	public List<FileUpload> listAll();

	public void save(FileUpload file);

	public FileUpload findByNumber(long fileNumber);

	public void delete(FileUpload file);

	public void deleteByNumber(long fileNumber);

	public FileUpload findTopFileNumber();
}
