package com.example.tensquare_user.controller;

import com.example.tensquare_user.Util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController {
    @RequestMapping("/upload")
    public String upload(){
        System.out.println("success");
        return "index";
    }
    @ResponseBody
    @RequestMapping(value = "/uploadfile",method = RequestMethod.POST)
    public Map uploadFile(HttpServletRequest request){
       Map resultMap= new HashMap<>();
        MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        String name=request.getParameter("content");
        System.out.println(file.getOriginalFilename()+name);
       String savePath="C:\\Users\\Administrator\\Desktop\\temp2\\";
       String filename=file.getOriginalFilename();

        String message="";
        int status=0;
            try {
                FileUtil.uploadFile(file.getBytes(),savePath,filename);
                message="文件上传成功";
                status=1;
            } catch (Exception e) {
                message="文件上传失败";
                e.printStackTrace();
            }
            resultMap.put("message",message);
            resultMap.put("status",status);
        return resultMap;
    }
}
