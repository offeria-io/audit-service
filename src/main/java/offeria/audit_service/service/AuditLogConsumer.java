package offeria.audit_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import offeria.audit_service.dto.CreateAuditLogRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka Consumer service that listens for audit events from other microservices.
 * This enables asynchronous audit logging across the system.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuditLogConsumer {

    private final AuditLogService auditLogService;

    /**
     * Listens to the 'audit-events' topic.
     * When an event is received, it's passed to the AuditLogService for storage.
     */
    @KafkaListener(topics = "${app.kafka.audit-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeAuditEvent(CreateAuditLogRequest request) {
        log.info("Received audit event via Kafka: {} for user: {}", request.getEventType(), request.getUserId());
        try {
            auditLogService.logEvent(request);
        } catch (Exception e) {
            log.error("Error processing audit event from Kafka", e);
            // In a production environment, we might want to send this to a DLQ (Dead Letter Queue)
        }
    }
}
