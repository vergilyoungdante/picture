package com.example.picture.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.picture.entity.Picture;
import com.example.picture.entity.User;
import com.example.picture.repository.UserRepository;
import com.example.picture.service.CommentService;
import com.example.picture.service.PictureService;
import com.example.picture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


@Controller
public class PictureController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Value("${spring.profiles.active}")
    private String systemParam;

    @Value("${file.uploadFolder}")
    private String uploadPath;  //这个是文件存储的路径，会根据不同的环境发生改变

    @RequestMapping("/findAll")
    public String findAll(Model model) {
        model.addAttribute("title", "查询所有用户");
        model.addAttribute("allUsers", userService.findAll());
        return "showAll";
    }

    @RequestMapping("/home")
    public String showHome(HttpServletRequest request, HttpServletResponse response){

        return "home";
    }

    @RequestMapping("/upload")
    public String upLoad(HttpServletRequest request, HttpServletResponse response){

        return "upload";
    }

    @RequestMapping(value = "/uploadPicture")
    public void savePicture(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @RequestParam("file") MultipartFile file) throws IOException {
        //如果选择了上传文件，将文件上传到指定的目录uploadFiles
        if(!file.isEmpty()) {
            //获得上传文件原名
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
            String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            String fileName = sf.format(new Date()) + "."+suffixName;
            File filePath = new File(uploadPath+File.separator+fileName);
            //将上传文件保存到一个目标文件中
            try{
                file.transferTo(filePath);
                System.out.println("成功了");
            }catch (IOException e){
                System.out.println(e.toString());
            }
            //这里拿到当前用户登陆的信息
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Picture picture= new Picture();
            picture.setTitle(title);
            picture.setUrl(filePath.toString());
            picture.setContent(new String(content.getBytes(StandardCharsets.UTF_8)));
            picture.setCreateId(user.getId());
            picture.setName(file.getOriginalFilename());

            //存照片，日志时间是自动生成的。
            pictureService.save(picture);

            response.setContentType("application/json;charset=UTF-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",200);
            jsonObject.put("message","hxd，你传上去了");
            try{
                //通过response返回应答信息
                response.getWriter().write(jsonObject.toString());
            } catch (IOException e){
                System.out.println(e.toString());
            }

        }
    }

    @GetMapping("/show")
    public void show(HttpServletRequest request, HttpServletResponse response){
        String page = request.getParameter("page");
        String limit = "5";

        Pageable pageable = PageRequest.of(Integer.parseInt(page)-1,Integer.parseInt(limit));
        Page<Picture> result = pictureService.findAll(pageable);

        //静态资源地址生成。由于配置了虚拟地址映射，这里要传给前端虚拟地址，避免浏览器拒绝访问本地磁盘地址
        for(Picture picture:result.getContent()){
            String url = picture.getUrl();
            int index = 0;

            if(systemParam.equals("prod")){
                index = url.lastIndexOf("/");            //远程服务器用这个
            }else {
                index = url.lastIndexOf("\\");            //本地测试用这个
            }
            url = url.substring(index+1);
            picture.setUrl(url);
        }

        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);

        JSONObject obj = new JSONObject();
        obj.put("code",200);
        obj.put("msg","read successes");
        obj.put("count",result.getTotalPages());
        obj.put("data",JSONArray.parseArray(JSONObject.toJSONString(result.getContent())));

        try{
            response.getWriter().write(obj.toString());
        } catch (IOException e){
            System.out.println(e.toString());
        }

    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(HttpServletRequest request, @PathVariable String id){
        Optional<Picture> picture = pictureService.findById(Long.parseLong(id));
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("/detail");
        if(picture.isPresent()){
            Picture result = picture.get();

            Optional<User> name = userRepository.findById(result.getCreateId());

            String url = result.getUrl();
            int index = 0;

            if(systemParam.equals("prod")){
                index = url.lastIndexOf("/");            //远程服务器用这个
            }else {
                index = url.lastIndexOf("\\");            //本地测试用这个
            }
            url = url.substring(index+1);

            //如果用addObject(object)那个方法会怎么样？
            modelAndView.addObject("title",result.getTitle());
            modelAndView.addObject("url",url);
            modelAndView.addObject("content",result.getContent());
            modelAndView.addObject("id",result.getId());
//            modelAndView.addObject("createName",name);
            modelAndView.setViewName("/detail");
        }
        return modelAndView;
    }
}
