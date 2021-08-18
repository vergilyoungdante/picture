package com.example.picture.repository;

import com.example.picture.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    public List<Comment> findByPictureID(Long id);
}
