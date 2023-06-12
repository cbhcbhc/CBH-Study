package com.elasticsearch.controller.search;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
@RestController
public class Search {
    public static final String indexName = "cbh_index";
    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     *  查询索引(根据条件)
     *  SearchRequest 搜索请求
     *      SearchSourceBuilder 条件构造
     *          TermQueryBuilder 精确匹配
     *          MatchAllQueryBuilder 匹配所有
     *          HighlightBuilder 构造高亮
     */
    @GetMapping("/search")
    public String search() throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        // 搜索条件构造器
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //查询条件，可以用QueryBuilders 工具类来实现
        // QueryBuilders.termQuery 精确查询
        //QueryBuilders.matchAllQuery 匹配所有
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "cbh");
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //把条件放到请求里面
        searchRequest.source(sourceBuilder);
        // 查询
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println("=============================");

        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }

        return JSON.toJSONString(searchResponse.getHits());
    }

}
