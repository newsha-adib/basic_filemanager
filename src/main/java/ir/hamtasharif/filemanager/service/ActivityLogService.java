package ir.hamtasharif.filemanager.service;

import ir.hamtasharif.filemanager.model.ActivityLog;
import ir.hamtasharif.filemanager.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    public ActivityLogService(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    public void log(String username, String action, String details) {
        ActivityLog log = ActivityLog.builder()
                .username(username)
                .action(action)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();
        activityLogRepository.save(log);
    }
}
