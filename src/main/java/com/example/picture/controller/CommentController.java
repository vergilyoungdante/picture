package com.example.picture.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.picture.entity.Comment;
import com.example.picture.entity.User;
import com.example.picture.repository.CommentRepository;
import com.example.picture.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/showComments")
    public void showComments(HttpServletRequest request, HttpServletResponse response){
        String pictureId = request.getParameter("pictureId");

        List<Comment> result = commentService.findByPictureID(Long.parseLong(pictureId));

        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);

        JSONObject obj = new JSONObject();
        obj.put("code",200);
        obj.put("msg","read successes");
        obj.put("data", JSONArray.parseArray(JSONObject.toJSONString(result)));

        try{
            response.getWriter().write(obj.toString());
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }
    @PostMapping ("/comment/save")
    public void saveComments(HttpServletRequest request, HttpServletResponse response, @RequestBody String string){
        JSONObject jsonObject = JSONObject.parseObject(string);
        String text = jsonObject.getString("comment");
        String pictureId = jsonObject.getString("pictureId");

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();

        Comment comment = new Comment();
        comment.setCreateId(userId);
        comment.setContent(text);
        comment.setPictureID(Long.parseLong(pictureId));

        commentRepository.save(comment);
    }
}
