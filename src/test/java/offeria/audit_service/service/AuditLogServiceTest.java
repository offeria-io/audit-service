package offeria.audit_service.service;

import offeria.audit_service.dto.AuditLogDto;
import offeria.audit_service.dto.CreateAuditLogRequest;
import offeria.audit_service.exception.ResourceNotFoundException;
import offeria.audit_service.mapper.AuditLogMapper;
import offeria.audit_service.model.AuditLog;
import offeria.audit_service.repository.AuditLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditLogServiceTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @Mock
    private AuditLogMapper auditLogMapper;

    @InjectMocks
    private AuditLogServiceImpl auditLogService;

    private CreateAuditLogRequest request;
    private AuditLog auditLog;
    private AuditLogDto auditLogDto;
    private UUID logId;

    @BeforeEach
    void setUp() {
        logId = UUID.randomUUID();
        request = CreateAuditLogRequest.builder()
                .eventType("USER_LOGIN")
                .userId("user-123")
                .description("User logged in")
                .build();

        auditLog = AuditLog.builder()
                .id(logId)
                .eventType("USER_LOGIN")
                .userId("user-123")
                .build();

        auditLogDto = AuditLogDto.builder()
                .id(logId)
                .eventType("USER_LOGIN")
                .userId("user-123")
                .build();
    }

    @Test
    void logEvent_ShouldReturnSavedLog() {
        // Arrange
        when(auditLogMapper.toEntity(any(CreateAuditLogRequest.class))).thenReturn(auditLog);
        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(auditLog);
        when(auditLogMapper.toDto(any(AuditLog.class))).thenReturn(auditLogDto);

        // Act
        AuditLogDto result = auditLogService.logEvent(request);

        // Assert
        assertNotNull(result);
        assertEquals(logId, result.getId());
        verify(auditLogRepository, times(1)).save(any(AuditLog.class));
    }

    @Test
    void getAuditLogById_WhenIdExists_ShouldReturnLog() {
        // Arrange
        when(auditLogRepository.findById(logId)).thenReturn(Optional.of(auditLog));
        when(auditLogMapper.toDto(auditLog)).thenReturn(auditLogDto);

        // Act
        AuditLogDto result = auditLogService.getAuditLogById(logId);

        // Assert
        assertNotNull(result);
        assertEquals(logId, result.getId());
    }

    @Test
    void getAuditLogById_WhenIdDoesNotExist_ShouldThrowException() {
        // Arrange
        when(auditLogRepository.findById(logId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> auditLogService.getAuditLogById(logId));
    }
}
