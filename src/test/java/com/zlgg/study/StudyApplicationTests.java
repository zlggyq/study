package com.zlgg.study;

import com.zlgg.study.common.elasticsearch.EsConstant;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.ml.PutDatafeedRequest;
import org.elasticsearch.tasks.TaskId;

import cn.hutool.core.thread.ThreadUtil;
import com.zlgg.study.fastdfs.FastdfsUtils;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class StudyApplicationTests {

    @Autowired
    FastdfsUtils fastdfsUtils;

    @Autowired
    RestHighLevelClient client;

    //索引名称
    private static final String indexName = "zlgg";

    @Test
    void test() throws IOException {
        //检查索引是否存在
        check();
        // 创建索引
        createIndex();
        // 添加文档
        addDoc();
    }

    /**
     * 检查索引是否存在
     */
    private void check() {
        // 创建请求参数
        GetIndexRequest request = new GetIndexRequest();
        String[] indices = request.indices();

    }

    /**
     * 创建索引
     */
    private void createIndex() throws IOException {
        // 1、创建 创建索引request 参数：索引名mess
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        // 2、设置索引的settings
        request.settings(Settings.builder().put("index.number_of_shards", 3) // 分片数
                .put("index.number_of_replicas", 2) // 副本数
                .put("analysis.analyzer.default.tokenizer", "ik_max_word") // 默认分词器
        );
        // 3、设置索引的mappings
        request.mapping(
                "{\n" +
                        "    \"properties\": \n" +
                        "    {\n" +
                        "      \"message\": \n" +
                        "      {\n" +
                        "        \"type\": \"text\"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }"
                , XContentType.JSON);
        // 4、 设置索引的别名
        request.alias(new Alias(indexName+"2"));

        // 5、 发送请求
        // 5.1 同步方式发送请求
        /*CreateIndexResponse createIndexResponse = client.indices()
                .create(request, RequestOptions.DEFAULT);

        // 6、处理响应
        print(createIndexResponse);*/

        // 5.2 异步方式发送请求
        ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {
            @Override
            public void onResponse(CreateIndexResponse createIndexResponse) {
                // 6、处理响应
                print(createIndexResponse);
            }
            @Override
            public void onFailure(Exception e) {
                System.out.println("创建索引异常：" + e.getMessage());
            }
        };
        client.indices().createAsync(request, RequestOptions.DEFAULT, listener);
        ThreadUtil.sleep(5000);
    }

    /**
     * 添加文档
     */
    private void addDoc() {
    }

    /**
     * 打印结果
     * @param createIndexResponse
     */
    private void print(CreateIndexResponse createIndexResponse) {
        boolean acknowledged = createIndexResponse.isAcknowledged();
        boolean shardsAcknowledged = createIndexResponse
                .isShardsAcknowledged();
        System.out.println("acknowledged = " + acknowledged);
        System.out.println("shardsAcknowledged = " + shardsAcknowledged);
    }
}
