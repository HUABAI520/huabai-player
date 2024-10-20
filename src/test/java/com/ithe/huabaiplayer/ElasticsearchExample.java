package com.ithe.huabaiplayer;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Arrays;

public class ElasticsearchExample {
    private final RestHighLevelClient client;

    public ElasticsearchExample(RestHighLevelClient client) {
        this.client = client;
    }

    public void getAllAnimeData() {
        SearchRequest searchRequest = new SearchRequest("anime_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(1000); // 设置每次获取的数据条数
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            // 处理搜索结果
            Arrays.stream(searchResponse.getHits().getHits()).forEach(hit -> {
                System.out.println(hit.getSourceAsString()); // 输出每个文档的数据
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchAnimeDataByKeyword(String keyword) {
        SearchRequest searchRequest = new SearchRequest("anime_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 构建查询条件，这里以匹配所有字段为例
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword, "name", "intro", "director", "status", "act_role");

        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10); // 设置每次获取的数据条数

        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            // 处理搜索结果
            Arrays.stream(searchResponse.getHits().getHits()).forEach(hit -> {
                System.out.println(hit.getSourceAsString()); // 输出每个文档的数据
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void searchAnimeDataByKeyword(String keyword, int pageNumber, int pageSize) {
        SearchRequest searchRequest = new SearchRequest("anime_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 构建 multi_match 查询条件
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword, "name", "intro", "director", "status");
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(pageSize); // 设置每次获取的数据条数
        searchSourceBuilder.from((pageNumber - 1) * pageSize); // 设置起始位置

        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            // 处理搜索结果
            Arrays.stream(searchResponse.getHits().getHits()).forEach(hit -> {
                System.out.println(hit.getSourceAsString()); // 输出每个文档的数据
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // 设置用户名和密码
        String username = "elastic";
        String password = "6vsp3=-pJtM9NHakIWJP";

        // 创建凭证提供者
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        // 创建客户端
        try (RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
                        .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
        )) {
            ElasticsearchExample example = new ElasticsearchExample(client);
//            example.getAllAnimeData();
            example.searchAnimeDataByKeyword("轻声密语", 1, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
