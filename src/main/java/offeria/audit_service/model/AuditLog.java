package offeria.audit_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing an audit log entry in the system.
 * This table stores all system activities, user actions, and RFQ lifecycle changes.
 */
@Entity
@Table(name = "audit_logs", indexes = {
    @Index(name = "idx_audit_user_id", columnList = "userId"),
    @Index(name = "idx_audit_event_type", columnList = "eventType"),
    @Index(name = "idx_audit_resource_id", columnList = "resourceId")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Type of the event (e.g., USER_LOGIN, RFQ_CREATED, RFQ_STATUS_CHANGED)
    @Column(nullable = false)
    private String eventType;

    // The ID of the user who performed the action
    @Column(nullable = false)
    private String userId;

    // The resource affected by this action (e.g., RFQ ID)
    private String resourceId;

    // The type of resource (e.g., RFQ, USER, ACCOUNT)
    private String resourceType;

    // Detailed description of the action
    @Column(columnDefinition = "TEXT")
    private String description;

    // The state of the resource before the change (JSON format recommended)
    @Column(columnDefinition = "TEXT")
    private String previousState;

    // The state of the resource after the change (JSON format recommended)
    @Column(columnDefinition = "TEXT")
    private String currentState;

    // IP address of the user who performed the action
    private String ipAddress;

    // User agent of the client
    private String userAgent;

    // Timestamp when the event occurred
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;
}
