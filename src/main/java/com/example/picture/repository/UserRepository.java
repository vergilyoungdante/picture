package com.example.picture.repository;

import com.example.picture.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public String findNameById(Long id);
    public User findByName(String name);
}
