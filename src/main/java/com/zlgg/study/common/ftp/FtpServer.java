package com.zlgg.study.common.ftp;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: wzl
 * Date: 2021-07-22
 * Time: 22:38
 * Description:
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "ftp")
public class FtpServer {
    /**
     * 机器的ip
     */
    private String hostname;
    /**
     * 端口号
     */
    private int port;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 存储的位置
     */
    private String savePath;

    @PostConstruct
    private void init() {
        log.info("ftp始化完成.{}", JSON.toJSONString(this));
    }
}
