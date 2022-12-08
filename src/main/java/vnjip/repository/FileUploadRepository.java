package vnjip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vnjip.entity.FileUpload;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
	@Query(value = "SELECT f from FileUpload f where f.fileName LIKE '%' || :word || '%' "
			+ "OR f.folderName LIKE '%' || :word || '%' " + "OR f.dateUpload LIKE '%' || :word || '%' "
			+ "OR f.fileNumber LIKE '%' || :word || '%' ")
	public List<FileUpload> search(@Param("word") String word);

	public FileUpload findFirstByOrderByFileNumberDesc();

	public List<FileUpload> findByFileName(String fileName);
}
