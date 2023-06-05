package com.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class elasticSearchConfig {
    /**
     * 配置es连接信息
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient(){
       return new RestHighLevelClient(RestClient.builder(
                new HttpHost("139.159.161.154",9200,"http")));
    }
}
