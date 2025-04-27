package ir.hamtasharif.filemanager.service;

import ir.hamtasharif.filemanager.model.User;
import ir.hamtasharif.filemanager.repository.UserRepository;
import ir.hamtasharif.filemanager.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final PermissionRepository permissionRepository;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        permissionRepository.deleteAll(
                permissionRepository.findAll().stream()
                        .filter(p -> p.getUser().getId().equals(user.getId()))
                        .toList()
        );


        userRepository.deleteById(id);
    }
}
