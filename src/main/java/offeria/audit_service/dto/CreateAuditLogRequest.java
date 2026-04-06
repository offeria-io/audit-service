package offeria.audit_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Request DTO for creating an audit log entry.
 * Typically used when receiving events from other services via REST or internal methods.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAuditLogRequest {

    @NotBlank(message = "Event type is required")
    private String eventType;

    @NotBlank(message = "User ID is required")
    private String userId;

    private String resourceId;
    private String resourceType;
    private String description;
    private String previousState;
    private String currentState;
    private String ipAddress;
    private String userAgent;
}
