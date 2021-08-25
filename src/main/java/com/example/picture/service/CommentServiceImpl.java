package com.example.picture.service;

import com.example.picture.entity.Comment;
import com.example.picture.entity.Picture;
import com.example.picture.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findByPictureID(Long pictureId) {
        List<Comment> result = commentRepository.findByPictureID(pictureId);
        return result;
    }
}
