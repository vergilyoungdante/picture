package com.example.picture.service;

import com.example.picture.entity.Picture;
import com.example.picture.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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

    public List<Picture> findById(Long id) {
        List<Picture> result = pictureRepository.findAllById(Collections.singleton(id));
        return result;
    }
}
