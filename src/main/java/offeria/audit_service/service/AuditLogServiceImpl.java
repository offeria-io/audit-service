package offeria.audit_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import offeria.audit_service.dto.AuditLogDto;
import offeria.audit_service.dto.CreateAuditLogRequest;
import offeria.audit_service.exception.ResourceNotFoundException;
import offeria.audit_service.mapper.AuditLogMapper;
import offeria.audit_service.model.AuditLog;
import offeria.audit_service.repository.AuditLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service implementation for managing audit logs.
 * Handles business logic and coordinates with the repository and mapper.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    @Override
    @Transactional
    public AuditLogDto logEvent(CreateAuditLogRequest request) {
        log.info("Logging event of type: {} for user: {}", request.getEventType(), request.getUserId());
        
        // Convert request DTO to Entity
        AuditLog auditLog = auditLogMapper.toEntity(request);
        
        // Save to database
        AuditLog savedLog = auditLogRepository.save(auditLog);
        
        // Return mapped DTO
        return auditLogMapper.toDto(savedLog);
    }

    @Override
    @Transactional(readOnly = true)
    public AuditLogDto getAuditLogById(UUID id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditLog", "id", id));
        return auditLogMapper.toDto(auditLog);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogDto> getAllAuditLogs(Pageable pageable) {
        return auditLogRepository.findAll(pageable)
                .map(auditLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogDto> getAuditLogsByUser(String userId, Pageable pageable) {
        return auditLogRepository.findByUserId(userId, pageable)
                .map(auditLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogDto> getAuditLogsByEventType(String eventType, Pageable pageable) {
        return auditLogRepository.findByEventType(eventType, pageable)
                .map(auditLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogDto> getAuditLogsByResource(String resourceType, String resourceId, Pageable pageable) {
        return auditLogRepository.findByResourceTypeAndResourceId(resourceType, resourceId, pageable)
                .map(auditLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogDto> getAuditLogsByTimeRange(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return auditLogRepository.findByTimestampBetween(start, end, pageable)
                .map(auditLogMapper::toDto);
    }
}
