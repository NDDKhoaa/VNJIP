package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.FileUpload;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
	public FileUpload findFirstByOrderByFileNumberDesc();
}
