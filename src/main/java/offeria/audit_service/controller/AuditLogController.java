package offeria.audit_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import offeria.audit_service.dto.AuditLogDto;
import offeria.audit_service.dto.CreateAuditLogRequest;
import offeria.audit_service.service.AuditLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * REST Controller for Audit Log API.
 * Provides endpoints to query and create audit logs.
 */
@RestController
@RequestMapping("/api/v1/audits")
@RequiredArgsConstructor
@Slf4j
public class AuditLogController {

    private final AuditLogService auditLogService;

    /**
     * Endpoint to manually log an event.
     */
    @PostMapping
    public ResponseEntity<AuditLogDto> logEvent(@Valid @RequestBody CreateAuditLogRequest request) {
        log.info("REST request to log event: {}", request.getEventType());
        AuditLogDto createdLog = auditLogService.logEvent(request);
        return new ResponseEntity<>(createdLog, HttpStatus.CREATED);
    }

    /**
     * Endpoint to get a specific audit log by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuditLogDto> getAuditLogById(@PathVariable UUID id) {
        log.info("REST request to get audit log: {}", id);
        return ResponseEntity.ok(auditLogService.getAuditLogById(id));
    }

    /**
     * Endpoint to get all audit logs with pagination and optional filtering.
     */
    @GetMapping
    public ResponseEntity<Page<AuditLogDto>> getAllAuditLogs(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String eventType,
            @RequestParam(required = false) String resourceType,
            @RequestParam(required = false) String resourceId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            Pageable pageable) {
        
        log.info("REST request to query audit logs with pagination");
        
        Page<AuditLogDto> result;
        
        if (userId != null) {
            result = auditLogService.getAuditLogsByUser(userId, pageable);
        } else if (eventType != null) {
            result = auditLogService.getAuditLogsByEventType(eventType, pageable);
        } else if (resourceType != null && resourceId != null) {
            result = auditLogService.getAuditLogsByResource(resourceType, resourceId, pageable);
        } else if (start != null && end != null) {
            result = auditLogService.getAuditLogsByTimeRange(start, end, pageable);
        } else {
            result = auditLogService.getAllAuditLogs(pageable);
        }
        
        return ResponseEntity.ok(result);
    }
}
