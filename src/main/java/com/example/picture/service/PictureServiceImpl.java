package com.example.picture.service;

import com.example.picture.entity.Picture;
import com.example.picture.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService{
    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public List<Picture> findAll() {
        List<Picture> result = pictureRepository.findAll();
        return result;
    }

    @Override
    public List<Picture> findByName(String name) {
        List<Picture> result = pictureRepository.findByTitle(name);
        return result;
    }

    @Override
    public Picture save(Picture picture){
        Picture newPicture=pictureRepository.save(picture);
        return newPicture;
    }

    @Override
    public Page<Picture> findAll(Pageable pageable) {
        org.springframework.data.domain.Page<Picture> page = pictureRepository.findAll(pageable);
        Page<Picture> contentPage = new PageImpl<Picture>(page.getContent(),pageable,page.getTotalElements());
        return contentPage;
    }

    @Override
    public Optional<Picture> findById(Long id) {
        Optional<Picture> picture = pictureRepository.findById(id);
        return picture;
    }

}
