package ir.hamtasharif.filemanager.controller;

import ir.hamtasharif.filemanager.model.Directory;
import ir.hamtasharif.filemanager.service.DirectoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directories")
public class DirectoryController {

    private final DirectoryService directoryService;

    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<Directory> createDirectory(@PathVariable String username, @RequestBody Directory directory) {
        return ResponseEntity.ok(directoryService.createDirectory(username, directory));
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Directory>> getUserDirectories(@PathVariable String username) {
        return ResponseEntity.ok(directoryService.getDirectoriesByUsername(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirectory(@PathVariable Long id) {
        directoryService.deleteDirectory(id);
        return ResponseEntity.noContent().build();
    }
}
