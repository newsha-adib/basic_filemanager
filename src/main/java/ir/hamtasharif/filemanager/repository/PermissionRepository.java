package ir.hamtasharif.filemanager.repository;

import ir.hamtasharif.filemanager.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByUserUsernameAndDirectoryId(String username, Long directoryId);
}
