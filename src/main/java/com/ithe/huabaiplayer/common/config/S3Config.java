//package com.ithe.huabaiplayer.common.config;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author L
// */
//@Configuration
//public class S3Config {
//
//    @Bean
//    public AmazonS3 s3Client() {
//        // 设置 AWS 凭证
//        BasicAWSCredentials awsCreds = new BasicAWSCredentials("kU28Er26i8lMqhYo", "8IC2OYv8ugj9niNb1qUGjtx37M9pwS");
//
//        return AmazonS3ClientBuilder.standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
//                        "https://cn-sy1.rains3.com", "us-east-1"))
//                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
//                .build();
//    }
//}
