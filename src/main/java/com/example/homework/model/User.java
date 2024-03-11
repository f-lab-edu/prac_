package com.example.homework.model;

import com.example.homework.dto.UserRequestDto;
import com.example.homework.util.AuthConstants;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class User extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();

    protected User() {}

    public void setRoles(List<Authority> role) {
        this.roles = role;
        role.forEach(o -> o.setUser(this));
    }

    public static User createJoinUser(UserRequestDto.JoinUser requestDto) {
        User user = User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .build();
        user.setRoles(Authority.createUserAuthority());
        return user;

    }
}
