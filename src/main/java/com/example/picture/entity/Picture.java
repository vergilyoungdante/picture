package com.example.picture.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
@Entity
@Data
@Table(name = "picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "必须有标题")
    private String title;
    private String url;
    private String content;
    private String name;

    private Long createId;
    private Date createDate;

    @PrePersist
    void createdAt(){
        this.createDate = new Date();
    }
}
