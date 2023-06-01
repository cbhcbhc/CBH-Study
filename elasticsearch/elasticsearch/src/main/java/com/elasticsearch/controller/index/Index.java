package com.elasticsearch.controller.index;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("index")
public class Index {
    @Resource
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("create")
    public String createIndex() throws IOException {
        /**
         * 1. 创建索引请求
         * 2. 客户端执行请求
         */

        //1. 创建索引请求
        CreateIndexRequest indexRequest = new CreateIndexRequest("cbh_index");
        //2. 客户端 indicesClient 执行请求，请求后获得响应
        CreateIndexResponse response =
                restHighLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response);
        return "索引创建成功!";
    }


}
