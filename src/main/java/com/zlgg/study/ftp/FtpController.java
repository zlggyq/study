package com.zlgg.study.ftp;

import com.zlgg.study.common.ftp.FtpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author: wzl
 * Date: 2021-07-22
 * Time: 22:02
 * Description:
 */
@RestController
@RequestMapping("/ftp")
public class FtpController {

    @Autowired
    FtpClient ftpClient;

    @RequestMapping("/ftpUpload")
    public String ftpUpload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = servletRequest.getFileNames();
        while (iterator.hasNext()) {
            MultipartFile multipartFile = servletRequest.getFile(iterator.next());
            //文件上传
            ftpClient.uploadFile(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        }
        return "success";
    }
}
