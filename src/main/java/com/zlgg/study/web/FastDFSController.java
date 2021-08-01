package com.zlgg.study.web;

import com.zlgg.study.fastdfs.FastdfsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: wzl
 * Date: 2021-06-20
 * Time: 15:48
 * Description:
 */
@RestController
@RequestMapping("/fastDFS")
public class FastDFSController {

    @Autowired
    FastdfsUtils fastdfsUtils;

    @RequestMapping("/dowload")
    public void dowload(HttpServletResponse response) {
        String path = "group1/M00/00/00/wKipAmDO7LuARZAaAAAAFvguCSs979.txt";
        try {
            fastdfsUtils.download(path, null, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
