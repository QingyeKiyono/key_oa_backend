package com.key.oa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author admin
 * 测试功能是否正常
 */
@SpringBootApplication
public class KeyOaBackendApplication {
    // 禁用druid的usePingMethod方法
    // 不然每隔一段时间druid就会发出警告"discard long time none received connection"
    static {
        System.setProperty("druid.mysql.usePingMethod", "false");
    }

    public static void main(String[] args) {
        SpringApplication.run(KeyOaBackendApplication.class, args);
    }

}
