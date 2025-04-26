package ir.hamtasharif.filemanager.service;

import ir.hamtasharif.filemanager.model.Directory;
import ir.hamtasharif.filemanager.model.Permission;
import ir.hamtasharif.filemanager.model.User;
import ir.hamtasharif.filemanager.repository.DirectoryRepository;
import ir.hamtasharif.filemanager.repository.PermissionRepository;
import ir.hamtasharif.filemanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final DirectoryRepository directoryRepository;

    public PermissionService(PermissionRepository permissionRepository,
                             UserRepository userRepository,
                             DirectoryRepository directoryRepository) {
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
        this.directoryRepository = directoryRepository;
    }

    public Permission setPermission(String username, Long directoryId, Permission.AccessType accessType) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Directory directory = directoryRepository.findById(directoryId)
                .orElseThrow(() -> new RuntimeException("Directory not found"));

        Permission permission = permissionRepository
                .findByUserUsernameAndDirectoryId(username, directoryId)
                .orElse(new Permission());

        permission.setUser(user);
        permission.setDirectory(directory);
        permission.setAccessType(accessType);

        return permissionRepository.save(permission);
    }

    public Optional<Permission.AccessType> getPermission(String username, Long directoryId) {
        return permissionRepository
                .findByUserUsernameAndDirectoryId(username, directoryId)
                .map(Permission::getAccessType);
    }
}
