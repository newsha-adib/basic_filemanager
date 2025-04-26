package ir.hamtasharif.filemanager.repository;

import ir.hamtasharif.filemanager.model.ActivityLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityLogRepository extends MongoRepository<ActivityLog, String> {
}
