package com.elasticsearch.controller.index;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("index")
public class Index {
    public static final String indexName = "cbh_index";
    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 1 创建索引
     * @return
     * @throws IOException
     */
    @GetMapping("create")
    public String createIndex() throws IOException {
        /**
         * 1. 创建索引请求
         * 2. 客户端执行请求
         */

        //1. 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        //2. 客户端 indicesClient 执行请求，请求后获得响应
        CreateIndexResponse response =
                restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
        return "索引创建成功!";

    }

    /**
     * 2 查询索引是否存在
     * @return
     */
    @GetMapping("/get")
    public String getIndex() throws IOException {
        //1.查询索引请求
        GetIndexRequest request = new GetIndexRequest(indexName);
        //2.客户端执行请求
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println("索引是否存在："+ exists);
        return "索引获取成功";
    }

    /**
     * 3 删除索引
     */
    @DeleteMapping("/delete")
    public String deleteIndex() throws IOException {
        //1. 创建删除请求
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        //2. 客户端执行删除
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);

        System.out.println(delete.isAcknowledged());
        return "索引删除成功";
    }

    /**
     * 4 查询索引(根据条件)
     *  SearchRequest 搜索请求
     *      SearchSourceBuilder 条件构造
     *          TermQueryBuilder 精确匹配
     *          MatchAllQueryBuilder 匹配所有
     *          HighlightBuilder 构造高亮
     *
     *
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
