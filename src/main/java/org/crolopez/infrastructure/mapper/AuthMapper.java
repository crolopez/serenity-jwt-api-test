package org.crolopez.infrastructure.mapper;

import org.crolopez.domain.model.AuthRequest;
import org.crolopez.infrastructure.dto.AuthRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    AuthRequest toDomain(AuthRequestDTO dto);
    AuthRequestDTO toDTO(AuthRequest request);
} 