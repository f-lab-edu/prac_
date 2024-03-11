package com.example.homework.model;


import com.example.homework.util.AuthConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Authority extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column
    private String authorityName;

    @JoinColumn(name = "user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    protected Authority() {}

    public void setUser(User user) {
        this.user = user;
    }

    public static List<Authority> createUserAuthority() {
        Authority auth = Authority.builder()
                .authorityName(AuthConstants.ROLE_USER)
                .build();
        return Collections.singletonList(auth);
    }
}
