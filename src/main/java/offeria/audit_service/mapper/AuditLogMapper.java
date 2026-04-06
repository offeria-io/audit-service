package offeria.audit_service.mapper;

import offeria.audit_service.dto.AuditLogDto;
import offeria.audit_service.dto.CreateAuditLogRequest;
import offeria.audit_service.model.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper for AuditLog related conversions.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditLogMapper {

    // Converts Entity to DTO
    AuditLogDto toDto(AuditLog auditLog);

    // Converts Create Request to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    AuditLog toEntity(CreateAuditLogRequest request);
}
