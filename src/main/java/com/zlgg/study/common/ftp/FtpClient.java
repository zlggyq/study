package com.zlgg.study.common.ftp;

import com.alibaba.fastjson.JSON;
import com.zlgg.study.common.ftp.FtpServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;

/**
 * @author: wzl
 * Date: 2021-07-22
 * Time: 22:51
 * Description:
 */
@Slf4j
@Component
public class FtpClient {

    @Autowired
    private FtpServer ftpServer;

    /**
     * 连接ftp
     * @return
     */
    private FTPClient getConnection() {
        FTPClient ftpClient = new FTPClient();
        try {
            // 设置连接机器
            ftpClient.connect(ftpServer.getHostname(), ftpServer.getPort());
            ftpClient.login(ftpServer.getUsername(), ftpServer.getPassword());
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("ftp连接失败");
                ftpClient.disconnect(); // 断开连接
                return null;
            } else {
                log.info("ftp连接成功");
            }

            // 将文件类型设置成二进制
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 创建要存储文件夹的目录: 主要目录只能一级一级创建，不能一次创建多层; 在 选择创建目录是一定要看是否有写权限，不然失败
            ftpClient.makeDirectory(ftpServer.getSavePath());
            // 改变默认存放的位置
            ftpClient.changeWorkingDirectory(ftpServer.getSavePath());
            //开启被动模式，否则文件上传不成功，也不报错
            ftpClient.enterLocalPassiveMode();
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
        return ftpClient;
    }


    /**
     * 上传文件
     * @param fileName
     * @param inputStream
     */
    public void uploadFile(String fileName, InputStream inputStream) {

        FTPClient ftpClient = getConnection();
        if (ftpClient == null) {
            return;
        }
        try {
            boolean result = ftpClient.storeFile(fileName, inputStream);
            log.info("文件是否保存成功：" + result);
            inputStream.close();
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载
     * @param fileName
     * @param localPath  不指定表示下载到当前项目下
     */
    public void downloadFile( String localPath, String fileName) {
        FTPClient ftpClient = getConnection();
        if (ftpClient == null) {
            return;
        }
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile ftpFile : ftpFiles) {
                if (fileName.equals(ftpFile.getName())) {
                    File file = new File(localPath+fileName);
                    OutputStream outputStream = new FileOutputStream(file);
                    boolean result = ftpClient.retrieveFile(ftpFile.getName(), outputStream);
                    log.info("下载结果：" + result);
                    outputStream.close();
                }
            }
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
