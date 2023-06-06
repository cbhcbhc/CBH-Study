package com.elasticsearch.controller.document;

import com.alibaba.fastjson.JSON;
import com.elasticsearch.entity.User;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/document")
public class Document {
    public static final String indexName = "cbh_index";

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 1 添加文档
     * @return
     * @throws IOException
     */
    @GetMapping("create")
    public String createDocument() throws IOException {
        //1. 创建对象
        User user = new User("cbh", 18);
        //2. 创建请求
        IndexRequest request = new IndexRequest(indexName);

        //规则 put /cbh_index/_doc/1
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
//        request.timeout("1s");

        //将数据放入请求
        request.source(JSON.toJSONString(user), XContentType.JSON);

        //客户端发送请求,获取响应的结果
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());
        System.out.println(response.status());

        return "文档创建成功";
    }

    /**
     * 2 判断文档是否存在
     */
    @GetMapping("/exist")
    public String existDocument() throws IOException {
        GetRequest request = new GetRequest(indexName, "1");

        //不获取返回的_source 的上下文了
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");

        boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        System.out.println("文档是否存在"+ exists);
        return "文档是否存在"+ exists;
    }


    /**
     * 3 获取文档信息 get /index/_doc/1
     */
    @GetMapping("/get")
    public String getDocument() throws IOException {
        GetRequest request = new GetRequest(indexName, "1");
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        //打印文档的内容
        System.out.println(response.getSourceAsString());

        // 返回文档_source下的所有信息
        return JSON.toJSONString(response);
    }

    /**
     * 4 更新文档信息
     */
    @GetMapping("/update")
    public String updateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(indexName,"1");
        updateRequest.timeout("1s");

        User user = new User("cbh", 18);
        updateRequest.doc(JSON.toJSONString(user),XContentType.JSON);

        UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);

        System.out.println(response.status());
        return JSON.toJSONString(response);
    }


    /**
     * 5 删除文档信息
     */
    @GetMapping("/delete")
    public String deleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest(indexName, "1");
        request.timeout("1s");

        DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status());

        return "文档删除成功";

    }

    /**
     * 6 特殊的 真实的项目一般都会批量插入数据
     */
    @GetMapping("/batch/insert")
    public String batchInsert() throws IOException {
        //批处理请求
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User("aaa1",1));
        userList.add(new User("aaa2",2));
        userList.add(new User("aaa2",3));
        userList.add(new User("aaa4",4));
        userList.add(new User("aaa5",5));
        userList.add(new User("aaa6",6));

        for (int i = 0; i < userList.size(); i++) {
            //批量删除，更新同样修改add方法里面的请求即可
            bulkRequest.add(new IndexRequest(indexName)
                    //不指定id会随机生成
                    .id(""+(i+1))
                    .source(JSON.toJSONString(userList.get(i)),XContentType.JSON) );
        }

        BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        //false代表成功
        System.out.println("批处理是否失败"+ response.hasFailures());

        return "批处理执行成功";

    }


}
