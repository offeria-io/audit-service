package offeria.audit_service.service;

import offeria.audit_service.dto.AuditLogDto;
import offeria.audit_service.dto.CreateAuditLogRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service interface for Audit Log operations.
 * Defines the contract for business logic.
 */
public interface AuditLogService {

    /**
     * Creates and stores a new audit log entry.
     */
    AuditLogDto logEvent(CreateAuditLogRequest request);

    /**
     * Retrieves an audit log by its unique ID.
     */
    AuditLogDto getAuditLogById(UUID id);

    /**
     * Retrieves all audit logs with pagination.
     */
    Page<AuditLogDto> getAllAuditLogs(Pageable pageable);

    /**
     * Retrieves audit logs for a specific user.
     */
    Page<AuditLogDto> getAuditLogsByUser(String userId, Pageable pageable);

    /**
     * Retrieves audit logs by event type.
     */
    Page<AuditLogDto> getAuditLogsByEventType(String eventType, Pageable pageable);

    /**
     * Retrieves audit logs by resource.
     */
    Page<AuditLogDto> getAuditLogsByResource(String resourceType, String resourceId, Pageable pageable);

    /**
     * Retrieves audit logs within a time range.
     */
    Page<AuditLogDto> getAuditLogsByTimeRange(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
