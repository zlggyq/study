package com.zlgg.study;

import com.zlgg.study.fastdfs.FastdfsUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudyApplicationTests {

    @Autowired
    FastdfsUtils fastdfsUtils;

    @Test
    void contextLoads() {
        System.out.println(123);

    }

}
