package com.ithe.huabaiplayer;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;

public class ElasticsearchIndexCreation {

    public static void main(String[] args) {
        // 创建客户端，确保使用 HTTP
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "6vsp3=-pJtM9NHakIWJP"));

        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)); // 添加凭据

        RestHighLevelClient client = new RestHighLevelClient(builder);

        // 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("anime_index");
//        DeleteIndexRequest request = new DeleteIndexRequest("anime_index");
        request.mapping("{"
                + "  \"properties\": {"
                + "    \"id\": { \"type\": \"integer\" },"
                + "    \"name\": { \"type\": \"text\", \"fields\": { \"keyword\": { \"type\": \"keyword\" } } },"
                + "    \"intro\": { \"type\": \"text\" },"
                + "    \"issue_time\": { \"type\": \"date\" },"
                + "    \"month\": { \"type\": \"short\" },"
                + "    \"is_new\": { \"type\": \"short\" },"
                + "    \"status\": { \"type\": \"keyword\" },"
                + "    \"act_role\": { \"type\": \"text\" },"
                + "    \"director\": { \"type\": \"text\" },"
                + "    \"language\": { \"type\": \"keyword\" },"
                + "    \"score\": { \"type\": \"float\" },"
                + "    \"number\": { \"type\": \"integer\" },"
                + "    \"type\": { \"type\": \"short\" },"
                + "    \"folder\": { \"type\": \"long\" },"
                + "    \"image\": { \"type\": \"keyword\" },"
                + "    \"create_time\": { \"type\": \"date\" },"
                + "    \"update_Time\": { \"type\": \"date\" }"
                + "  }"
                + "}", XContentType.JSON);

        try {
            // 执行创建索引请求
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
//            AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
            if (createIndexResponse.isAcknowledged()) {
                System.out.println("索引创建成功");
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
