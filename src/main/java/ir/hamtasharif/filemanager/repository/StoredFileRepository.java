package ir.hamtasharif.filemanager.repository;

import ir.hamtasharif.filemanager.model.StoredFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoredFileRepository extends JpaRepository<StoredFile, Long> {
    List<StoredFile> findByDirectoryId(Long directoryId);
}
