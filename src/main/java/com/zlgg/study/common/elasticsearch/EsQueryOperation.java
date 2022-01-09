package com.zlgg.study.common.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.zlgg.study.common.elasticsearch.EsConstant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: wzl
 * Date: 2021-12-22
 * Time: 23:01
 * Description:
 */
@Slf4j
@Service
public class EsQueryOperation {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 查询总数
     */
    public Long countAll(String indexName) {
        CountRequest countRequest = new CountRequest(indexName);
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        countRequest.query(queryBuilder);
        try {
            CountResponse countResponse = restHighLevelClient.count(countRequest, DEFAULT_OPTIONS);
            return countResponse.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 查询总数
     */
    public Long countMatches(String indexName, Map<String, Object> matches) {
        CountRequest countRequest = new CountRequest(indexName);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        matches.forEach((k, v) -> boolQueryBuilder.must(QueryBuilders.matchQuery(k, v)));
        log.info("boolQueryBuilder:{}", boolQueryBuilder);
        countRequest.query(boolQueryBuilder);
        try {
            CountResponse countResponse = restHighLevelClient.count(countRequest, DEFAULT_OPTIONS);
            return countResponse.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 分页查询
     */
    public List<Map<String, Object>> queryMatches(String indexName, Map<String, Object> matches, Pager pager) {
        // 查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 分页参数
        searchSourceBuilder.from(pager.getOffset());
        searchSourceBuilder.size(pager.getPageSize());
        log.info("pager:{}, offset:{}", pager, pager.getOffset());
        // 过滤参数
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        matches.forEach((k, v) -> boolQueryBuilder.must(QueryBuilders.matchQuery(k, v)));
        log.info("boolQueryBuilder:{}", boolQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        // 排序
        searchSourceBuilder.sort("updatedDt", SortOrder.DESC);
        searchSourceBuilder.sort("buildDate", SortOrder.ASC);
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResp = restHighLevelClient.search(searchRequest, DEFAULT_OPTIONS);
            List<Map<String, Object>> data = new ArrayList<>();
            SearchHit[] searchHitArr = searchResp.getHits().getHits();
            for (SearchHit searchHit : searchHitArr) {
                Map<String, Object> temp = searchHit.getSourceAsMap();
                data.add(temp);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
