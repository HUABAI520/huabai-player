package com.ithe.huabaiplayer.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ithe.huabaiplayer.common.exception.RestClientException;
import com.ithe.huabaiplayer.common.model.dto.RestClient.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RestClient {
    private final OkHttpClient okHttpClient;
    private final ObjectMapper pythonObjectMapper;

    @Autowired
    public RestClient(OkHttpClient okHttpClient, @Qualifier("pythonObjectMapper") ObjectMapper pythonObjectMapper) {
        this.okHttpClient = okHttpClient;
        this.pythonObjectMapper = pythonObjectMapper;
    }

    public RequestBuilder get(String url) {
        return new RequestBuilder(this, "GET", url);
    }

    public RequestBuilder post(String url) {
        return new RequestBuilder(this, "POST", url);
    }

    public RequestBuilder put(String url) {
        return new RequestBuilder(this, "PUT", url);
    }

    public RequestBuilder delete(String url) {
        return new RequestBuilder(this, "DELETE", url);
    }

    private <T> ApiResponse<T> executeRequest(Request request, Class<T> responseType) {
        try (Response response = okHttpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (body == null) {
                return new ApiResponse<>(response.code(), null);
            }

            if (responseType == String.class) {
                return new ApiResponse<>(response.code(), (T) body.string());
            }

            return new ApiResponse<>(
                    response.code(),
                    pythonObjectMapper.readValue(body.byteStream(), responseType)
            );
        } catch (IOException e) {
            throw new RestClientException("Request execution failed", e);
        }
    }

    public static class RequestBuilder {
        private final RestClient restClient;
        private final String method;
        private String url;
        private final HttpUrl.Builder urlBuilder;
        private final FormBody.Builder formBuilder;
        private Object jsonBody;
        private final Headers.Builder headers = new Headers.Builder();

        private RequestBuilder(RestClient restClient, String method, String url) {
            this.restClient = restClient;
            this.method = method;
            this.url = url;
            this.urlBuilder = HttpUrl.parse(url).newBuilder();
            this.formBuilder = new FormBody.Builder();
        }

        public RequestBuilder pathParam(String key, Object value) {
            url = url.replace("{" + key + "}", value.toString());
            return this;
        }

        public RequestBuilder queryParam(String key, Object value) {
            if (value != null && !value.toString().isEmpty()) {
                urlBuilder.addQueryParameter(key, value.toString());
            }
            return this;
        }


        public RequestBuilder formParam(String key, Object value) {
            formBuilder.add(key, value.toString());
            return this;
        }

        public RequestBuilder jsonBody(Object body) {
            this.jsonBody = body;
            return this;
        }

        public RequestBuilder header(String name, String value) {
            headers.add(name, value);
            return this;
        }

        public <T> ApiResponse<T> execute(Class<T> responseType) {
            try {
                HttpUrl httpUrl = urlBuilder.build();  // 直接构建最终 URL
                log.info("Request URL: {}", httpUrl);
                RequestBody body = null;
                if (jsonBody != null) {
                    String json = restClient.pythonObjectMapper.writeValueAsString(jsonBody);
                    body = RequestBody.create(json, MediaType.get("application/json"));
                } else if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
                    body = formBuilder.build();
                }

                Request request = new Request.Builder()
                        .url(httpUrl)
                        .method(method, body)
                        .headers(headers.build())
                        .build();

                return restClient.executeRequest(request, responseType);
            } catch (JsonProcessingException e) {
                throw new RestClientException("JSON serialization failed", e);
            }
        }
    }
}