package com.zlgg.study.common.elasticsearch;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.zlgg.study.common.elasticsearch.EsConstant.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: wzl
 * Date: 2021-12-22
 * Time: 22:59
 * Description:
 */
@Slf4j
@Service
public class EsIndexOperation {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 判断索引是否存在
     */
    public boolean checkIndex(String indexName) {
        try {
            return restHighLevelClient.indices().exists(new GetIndexRequest(indexName), DEFAULT_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 创建索引
     */
    public boolean createIndex(String indexName, Map<String, ?> mapping) {
        return createIndex(indexName, null, null, mapping);
    }

    /**
     * 创建索引
     */
    public boolean createIndex(String indexName, Map<String, ?> aliases, Map<String, ?> settings, Map<String, ?> properties) {
        if (checkIndex(indexName)) {
            return Boolean.TRUE;
        }
        try {
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            if (aliases != null && aliases.size() > 0) {
                request.aliases(aliases);
            }
            if (settings != null && settings.size() > 0) {
                request.settings(settings);
            }
            if (properties != null && properties.size() > 0) {
                Map<String, Object> mapping = new HashMap<>();
                mapping.put(INDEX_MAPPING_PROPERTIES, properties);
                // 禁止自动添加字段
                mapping.put(INDEX_MAPPING_DYNAMIC, INDEX_MAPPING_DYNAMIC_STRICT);
                request.mapping(mapping);
            }
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, DEFAULT_OPTIONS);
            log.info("createIndexResponse:{}", JSON.toJSONString(createIndexResponse));
            return Boolean.TRUE;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex(String indexName) {
        try {
            if (checkIndex(indexName)) {
                DeleteIndexRequest request = new DeleteIndexRequest(indexName);
                AcknowledgedResponse response = restHighLevelClient.indices().delete(request, DEFAULT_OPTIONS);
                return response.isAcknowledged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }
}
