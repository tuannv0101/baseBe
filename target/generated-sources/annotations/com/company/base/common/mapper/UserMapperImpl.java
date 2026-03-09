package com.company.base.common.mapper;

import com.company.base.dto.response.admin.UserResponse;
import com.company.base.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-06T08:00:36+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserResponse dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( dto.getEmail() );
        user.id( dto.getId() );
        user.username( dto.getUsername() );

        return user.build();
    }

    @Override
    public List<User> toEntity(List<UserResponse> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserResponse userResponse : dtoList ) {
            list.add( toEntity( userResponse ) );
        }

        return list;
    }

    @Override
    public List<UserResponse> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserResponse> list = new ArrayList<UserResponse>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public UserResponse toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.role( mapRolesToString( entity.getRoles() ) );
        userResponse.createdAt( entity.getCreatedAt() );
        userResponse.email( entity.getEmail() );
        userResponse.id( entity.getId() );
        userResponse.username( entity.getUsername() );

        return userResponse.build();
    }
}
