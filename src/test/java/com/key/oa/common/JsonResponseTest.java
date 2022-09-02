package com.key.oa.common;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsonResponseTest implements WithAssertions {
    @Test
    public void testJsonResponseConstructors() {
        // 无参数构造函数，三个属性都有默认值
        JsonResponse<Integer> response = JsonResponse.success();
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(response.getCode())
                .as("Code should be '00000'.")
                .isEqualTo("00000");
        assertions.assertThat(response.getMessage())
                .as("Message should be 'OK'.")
                .isEqualTo("OK");
        assertions.assertThat(response.getData())
                .as("Data should be null.")
                .isNull();
        assertions.assertAll();

        // 带一个参数，其中code和message具有默认值，data手动指定
        response = JsonResponse.success(1);
        assertions = new SoftAssertions();
        assertions.assertThat(response.getCode())
                .as("Code should be '00000'.")
                .isEqualTo("00000");
        assertions.assertThat(response.getMessage())
                .as("Message should be 'OK'.")
                .isEqualTo("OK");
        assertions.assertThat(response.getData())
                .as("Data should be 1.")
                .isEqualTo(1);
        assertions.assertAll();

        // 带两个参数，data是null，其余都需要手动指定
        response = JsonResponse.error("1", "123");
        assertions = new SoftAssertions();
        assertions.assertThat(response.getCode())
                .as("Code should be '1'.")
                .isEqualTo("1");
        assertions.assertThat(response.getMessage())
                .as("Message should be '123'.")
                .isEqualTo("123");
        assertions.assertThat(response.getData())
                .as("Data should be null.")
                .isNull();
        assertions.assertAll();

        // 带三个参数，三个属性全部需要手动指定
        response = JsonResponse.error("2", "234", 1);
        assertions = new SoftAssertions();
        assertions.assertThat(response.getCode())
                .as("Code should be '2'.")
                .isEqualTo("2");
        assertions.assertThat(response.getMessage())
                .as("Message should be '234'.")
                .isEqualTo("234");
        assertions.assertThat(response.getData())
                .as("Data should be 1.")
                .isEqualTo(1);
        assertions.assertAll();
    }

    @Test
    public void testSetters() {
        JsonResponse<Integer> response = JsonResponse.success();
        response.setCode("code");
        response.setMessage("message");
        response.setData(10);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(response.getCode())
                .as("Code should be 'code'.")
                .isEqualTo("code");
        assertions.assertThat(response.getMessage())
                .as("Message should be 'message'.")
                .isEqualTo("message");
        assertions.assertThat(response.getData())
                .as("Data should be 10.")
                .isEqualTo(10);
        assertions.assertAll();
    }
}
