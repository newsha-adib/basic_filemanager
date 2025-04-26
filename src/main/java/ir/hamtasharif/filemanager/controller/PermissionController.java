package ir.hamtasharif.filemanager.controller;

import ir.hamtasharif.filemanager.model.Permission;
import ir.hamtasharif.filemanager.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/{username}/{directoryId}")
    public ResponseEntity<Permission> setPermission(
            @PathVariable String username,
            @PathVariable Long directoryId,
            @RequestParam Permission.AccessType accessType) {
        return ResponseEntity.ok(permissionService.setPermission(username, directoryId, accessType));
    }

    @GetMapping("/{username}/{directoryId}")
    public ResponseEntity<Permission.AccessType> getPermission(
            @PathVariable String username,
            @PathVariable Long directoryId) {
        Optional<Permission.AccessType> permission = permissionService.getPermission(username, directoryId);
        return permission.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
