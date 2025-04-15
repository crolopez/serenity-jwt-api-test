package org.crolopez.infrastructure.mapper;

import org.crolopez.domain.model.UserEntity;
import org.crolopez.infrastructure.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(UserEntity user);

    UserEntity toDomain(UserDTO dto);
}