package ir.hamtasharif.filemanager.controller;

import ir.hamtasharif.filemanager.model.StoredFile;
import ir.hamtasharif.filemanager.service.StoredFileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class StoredFileController {

    private final StoredFileService storedFileService;

    public StoredFileController(StoredFileService storedFileService) {
        this.storedFileService = storedFileService;
    }

    //    @PostMapping("/upload/{directoryId}")
    @PostMapping(value = "/upload/{directoryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StoredFile> uploadFile(@PathVariable Long directoryId,
                                                 @RequestParam("file") MultipartFile file) throws IOException {
        StoredFile storedFile = storedFileService.saveFile(directoryId, file);
        return ResponseEntity.ok(storedFile);
    }

    @GetMapping("/list/{directoryId}")
    public ResponseEntity<List<StoredFile>> listFiles(@PathVariable Long directoryId) {
        return ResponseEntity.ok(storedFileService.listFiles(directoryId));
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        return storedFileService.getFile(fileId)
                .map(storedFile -> {
                    Resource resource = new FileSystemResource(storedFile.getPath());
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentDisposition(ContentDisposition.builder("attachment")
                            .filename(storedFile.getName()).build());
                    headers.setContentType(MediaType.parseMediaType(storedFile.getType()));
                    return new ResponseEntity<>(resource, headers, HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        storedFileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }
}
