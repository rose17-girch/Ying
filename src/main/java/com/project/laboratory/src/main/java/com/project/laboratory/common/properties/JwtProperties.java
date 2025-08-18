package com.project.laboratory.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String tokenName; // 请求头中的token名称
    private String secretKey; // JWT签名密钥
    private String ttl;
}