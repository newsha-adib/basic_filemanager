package ir.hamtasharif.filemanager.repository;

import ir.hamtasharif.filemanager.model.Directory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DirectoryRepository extends JpaRepository<Directory, Long> {
    List<Directory> findByOwnerUsername(String username);
}
