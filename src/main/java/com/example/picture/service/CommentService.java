package com.example.picture.service;

import com.example.picture.entity.Comment;
import com.example.picture.entity.Picture;

import java.util.List;

public interface CommentService {
    public List<Comment> findByPictureID(Long pictureId);
}
