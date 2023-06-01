package com.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ElasticsearchApplicationTests {
    @Resource
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient restHighLevelClient;

    @Test
    void contextLoads() {
    }

}
