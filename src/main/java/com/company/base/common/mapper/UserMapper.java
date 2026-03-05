package com.company.base.common.mapper;

import com.company.base.dto.response.admin.UserResponse;
import com.company.base.entity.User;
import com.company.base.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserResponse, User> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Override
    @Mapping(target = "role", source = "roles", qualifiedByName = "mapRolesToString")
    UserResponse toDto(User entity);

    @Named("mapRolesToString")
    default String mapRolesToString(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.joining(", "));
    }
}
