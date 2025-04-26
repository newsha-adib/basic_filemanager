package ir.hamtasharif.filemanager.service;

import ir.hamtasharif.filemanager.model.Directory;
import ir.hamtasharif.filemanager.model.StoredFile;
import ir.hamtasharif.filemanager.repository.DirectoryRepository;
import ir.hamtasharif.filemanager.repository.StoredFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StoredFileService {

    private final StoredFileRepository fileRepository;
    private final DirectoryRepository directoryRepository;
    private final ActivityLogService activityLogService;

    //    private final String uploadRoot = "uploads/";
    private final String uploadRoot = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;


    public StoredFileService(StoredFileRepository fileRepository, DirectoryRepository directoryRepository, ActivityLogService activityLogService) {
        this.fileRepository = fileRepository;
        this.directoryRepository = directoryRepository;
        this.activityLogService = activityLogService;
    }

    public StoredFile saveFile(Long directoryId, MultipartFile multipartFile) throws IOException {
        Directory directory = directoryRepository.findById(directoryId)
                .orElseThrow(() -> new RuntimeException("Directory not found"));

        String dirPath = uploadRoot + directory.getId();
        new File(dirPath).mkdirs(); // create directory if not exists

        String filePath = dirPath + "/" + multipartFile.getOriginalFilename();
        File file = new File(filePath);
        multipartFile.transferTo(file);

        StoredFile storedFile = StoredFile.builder()
                .name(multipartFile.getOriginalFilename())
                .type(multipartFile.getContentType())
                .path(filePath)
                .size(multipartFile.getSize())
                .directory(directory)
                .build();

        StoredFile savedFile = fileRepository.save(storedFile);

        activityLogService.log(
                directory.getOwner().getUsername(),
                "UPLOAD",
                "Uploaded file: " + multipartFile.getOriginalFilename()
        );

        return savedFile;
    }

    public List<StoredFile> listFiles(Long directoryId) {
        return fileRepository.findByDirectoryId(directoryId);
    }

    public Optional<StoredFile> getFile(Long fileId) {
        return fileRepository.findById(fileId);
    }

    public void deleteFile(Long fileId) {
        StoredFile storedFile = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        File file = new File(storedFile.getPath());
        if (file.exists()) file.delete();

        fileRepository.deleteById(fileId);

        activityLogService.log(
                storedFile.getDirectory().getOwner().getUsername(),
                "DELETE",
                "Deleted file: " + storedFile.getName()
        );
    }
}

