package com.elasticsearch.controller.index;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

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
     * 2 查询索引
     * @return
     */
    @GetMapping("/get")
    public String getIndex() throws IOException {
        //1.查询索引请求
        GetIndexRequest request = new GetIndexRequest(indexName);
        //2.客户端执行请求
        GetIndexResponse response = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);
        System.out.println(response);
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

}
