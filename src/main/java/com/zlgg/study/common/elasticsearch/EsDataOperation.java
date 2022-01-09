package com.zlgg.study.common.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.zlgg.study.common.elasticsearch.EsConstant.*;

import java.util.List;
import java.util.Map;

/**
 * @author: wzl
 * Date: 2021-12-22
 * Time: 23:00
 * Description:
 */
@Slf4j
@Service
public class EsDataOperation {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 写入数据
     */
    public boolean insert(String indexName, Map<String, Object> source) {
        try {
            IndexRequest request = new IndexRequest(indexName).id(source.get(ID).toString())
                    // opType:create：id不能为空，必须指定id，id相同时报错
                    // opType:index：id可以为空，不指定id时自动生成，id相同时覆盖
                    .opType(DocWriteRequest.OpType.CREATE).source(source);
            IndexResponse indexResponse = restHighLevelClient.index(request, DEFAULT_OPTIONS);
            log.info("indexResponse:{}", indexResponse);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 批量写入数据
     */
    public boolean batchInsert(String indexName, List<Map<String, Object>> sourceList) {
        try {
            BulkRequest request = new BulkRequest();
            for (Map<String, Object> source : sourceList) {
                request.add(new IndexRequest(indexName).id(source.get(ID).toString())
                        // opType:create：id不能为空，必须指定id，id相同时报错
                        // opType:index：id可以为空，不指定id时自动生成，id相同时覆盖
                        .opType(DocWriteRequest.OpType.CREATE).source(source));
            }
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, DEFAULT_OPTIONS);
            log.info("bulkResponse:{}", bulkResponse);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 更新数据，可以直接修改索引结构
     */
    public boolean update(String indexName, Map<String, Object> source) {
        try {
            UpdateRequest updateRequest = new UpdateRequest(indexName, source.get(ID).toString());
            updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            // 此处大坑 如果要使用Map方法, 入参必须为Map<String, Object>类型
            updateRequest.doc(source);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, DEFAULT_OPTIONS);
            log.info("updateResponse:{}", updateResponse);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 删除数据
     */
    public boolean delete(String indexName, String id) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(indexName, id);
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, DEFAULT_OPTIONS);
            log.info("deleteResponse:{}", deleteResponse);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }
}
