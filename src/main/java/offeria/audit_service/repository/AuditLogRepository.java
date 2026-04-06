package offeria.audit_service.repository;

import offeria.audit_service.model.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository interface for AuditLog entity.
 * Provides standard CRUD and specialized query methods.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

    // Filter by user ID with pagination
    Page<AuditLog> findByUserId(String userId, Pageable pageable);

    // Filter by event type with pagination
    Page<AuditLog> findByEventType(String eventType, Pageable pageable);

    // Filter by resource ID and resource type
    Page<AuditLog> findByResourceTypeAndResourceId(String resourceType, String resourceId, Pageable pageable);

    // Query logs within a specific time range
    Page<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    // Search by partial description
    Page<AuditLog> findByDescriptionContainingIgnoreCase(String keyword, Pageable pageable);
}
