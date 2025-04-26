package ir.hamtasharif.filemanager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "activity_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLog {

    @Id
    private String id;

    private String username;

    private String action; // مثل "UPLOAD", "DELETE", "CREATE_DIRECTORY" و غیره

    private String details; // توضیحات اضافی

    private LocalDateTime timestamp;
}
