package vnjip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.FileUpload;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
	public FileUpload findFirstByOrderByFileNumberDesc();

	public List<FileUpload> findByFileName(String fileName);
}
