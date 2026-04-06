package offeria.audit_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for AuditLog.
 * Used for sending audit log data to clients.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogDto {
    private UUID id;
    private String eventType;
    private String userId;
    private String resourceId;
    private String resourceType;
    private String description;
    private String previousState;
    private String currentState;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime timestamp;
}
