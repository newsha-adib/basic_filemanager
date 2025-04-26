package ir.hamtasharif.filemanager.service;

import ir.hamtasharif.filemanager.model.Directory;
import ir.hamtasharif.filemanager.model.User;
import ir.hamtasharif.filemanager.repository.DirectoryRepository;
import ir.hamtasharif.filemanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectoryService {

    private final DirectoryRepository directoryRepository;
    private final UserRepository userRepository;

    public DirectoryService(DirectoryRepository directoryRepository, UserRepository userRepository) {
        this.directoryRepository = directoryRepository;
        this.userRepository = userRepository;
    }

    public Directory createDirectory(String username, Directory directory) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        directory.setOwner(user);
        return directoryRepository.save(directory);
    }

    public List<Directory> getDirectoriesByUsername(String username) {
        return directoryRepository.findByOwnerUsername(username);
    }

    public void deleteDirectory(Long id) {
        directoryRepository.deleteById(id);
    }
}
