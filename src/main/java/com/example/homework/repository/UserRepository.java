package com.example.homework.repository;

import com.example.homework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>, UserCustomRepository {
}
