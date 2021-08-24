package com.example.picture.service;

import com.example.picture.entity.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PictureService {
    public List<Picture> findAll();
    public List<Picture> findByName(String name);
    public Picture save(Picture picture);
    public Page<Picture> findAll(Pageable pageable);
    Optional<Picture> findById(Long id);
}
