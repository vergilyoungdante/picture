package com.example.picture.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@Table(name = "comment")
//图片评论实体类
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Date createDate;
    private Long pictureID;
    private Long createId;

    @PrePersist
    void createdAt(){
        this.createDate = new Date();
    }
}
