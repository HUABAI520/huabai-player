package com.ithe.huabaiplayer;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @ClassName ElasticsearchIndexDe
 * @Author hua bai
 * @Date 2024/9/28 14:55
 **/
public class ElasticsearchIndexDe {
    public static void main(String[] args) {
        // 创建客户端，确保使用 HTTP
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "6vsp3=-pJtM9NHakIWJP"));

        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)); // 添加凭据

        RestHighLevelClient client = new RestHighLevelClient(builder);

        // 创建索引请求
//        CreateIndexRequest request = new CreateIndexRequest("anime_index");
        DeleteIndexRequest request = new DeleteIndexRequest("anime_index");

        try {
            // 执行创建索引请求
//            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
            if (delete.isAcknowledged()) {
                System.out.println("索引删除成功");
            } else {
                System.out.println("索引创建失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭客户端
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
