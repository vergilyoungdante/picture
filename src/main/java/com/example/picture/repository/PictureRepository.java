package com.example.picture.repository;

import com.example.picture.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture,Long> {
    public List<Picture> findByTitle(String name);
    public Picture save(Picture picture);
}
