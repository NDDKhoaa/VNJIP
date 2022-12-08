package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.FileUpload;
import vnjip.repository.FileUploadRepository;
import vnjip.services.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private FileUploadRepository fileRepository;

	public List<FileUpload> listAll() {
		return (List<FileUpload>) fileRepository.findAll();
	}

	public void save(FileUpload file) {
		fileRepository.save(file);
	}

	public FileUpload findByNumber(long fileNumber) {
		Optional<FileUpload> rs = fileRepository.findById(fileNumber);
		return rs.orElse(null);
	}

	public void delete(FileUpload file) {
		fileRepository.delete(file);
	}

	public void deleteByNumber(long fileNumber) {
		fileRepository.deleteById(fileNumber);
	}

	public FileUpload findTopFileNumber() {
		return fileRepository.findFirstByOrderByFileNumberDesc();
	}

	public boolean findByFileName(String fileName) {
		List<FileUpload> list = fileRepository.findByFileName(fileName);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean findByFileNameModify(String fileName) {
		List<FileUpload> list = fileRepository.findByFileName(fileName);
		if (list.size() > 1) {
			return true;
		} else {
			return false;
		}
	}
}
