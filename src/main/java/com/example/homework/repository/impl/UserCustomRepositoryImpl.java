package com.example.homework.repository.impl;

import com.example.homework.dto.UserDto;
import com.example.homework.model.Authority;
import com.example.homework.model.QUser;
import com.example.homework.model.User;
import com.example.homework.repository.UserCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserDto> findUserByEmail(String email) {
        User user = queryFactory
                .selectFrom(QUser.user)
                .where(QUser.user.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(user != null ? entityToUserDto(user) : null);
    }

    /*******************************************************************************************************************
     * DESC : entity -> dto transferMethod
     *******************************************************************************************************************/

    private UserDto entityToUserDto(User user) {
        List<String> authorityList = user.getRoles().stream()
                .map(Authority::getAuthorityName)
                .collect(Collectors.toList());

        return UserDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorityList(authorityList)
                .build();
    }

}
