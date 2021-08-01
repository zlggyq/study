package com.zlgg.study.upload;

import javafx.application.Application;
import org.apache.catalina.core.ApplicationPart;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author: wzl
 * Date: 2021-07-18
 * Time: 11:05
 * Description:
 */
@RestController
@RequestMapping("/fileUpload")
public class FileUploadController {

    @RequestMapping("/upload")
    public String upload(HttpServletRequest request, MultipartFile multipartFile, String wzl) throws IOException, ServletException {
        MultipartHttpServletRequest request1 = (MultipartHttpServletRequest) request;
        return "true";

    }
}
