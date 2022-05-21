package com.key.oa.common;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class JsonResponseTest {
    @Test
    public void testJsonResponseConstructors() {
        // 无参数构造函数，三个属性都有默认值
        JsonResponse<Integer> response = new JsonResponse<>();
        Assert.isNull(response.getData(), "默认构造函数的data属性不是null！");
        Assert.isTrue("00000".equals(response.getCode()), "默认构造函数的code不是'00000'！");
        Assert.isTrue("OK".equals(response.getMessage()), "默认构造函数的message不是'OK'！");

        // 带一个参数，其中code和message具有默认值，data手动指定
        response = new JsonResponse<>(1);
        Assert.notNull(response.getData(), "带参数构造函数的data属性不是指定值！");
        Assert.isTrue("00000".equals(response.getCode()), "带参数构造函数的code不是'00000'！");
        Assert.isTrue("OK".equals(response.getMessage()), "带参数构造函数的message不是'OK'！");

        // 带两个参数，data是null，其余都需要手动指定
        response = new JsonResponse<>("1", "123");
        Assert.isNull(response.getData(), "二参数构造函数的data属性不是null！");
        Assert.isTrue("1".equals(response.getCode()), "二参数构造函数的code不是指定值！");
        Assert.isTrue("123".equals(response.getMessage()), "二参数构造函数的message不是指定值！");

        // 带三个参数，三个属性全部需要手动指定
        response = new JsonResponse<>("2", "234", 1);
        Assert.isTrue(response.getData() == 1, "三参数构造函数的data属性不是指定值！");
        Assert.isTrue("2".equals(response.getCode()), "三参数构造函数的code不是指定值！");
        Assert.isTrue("234".equals(response.getMessage()), "三参数构造函数的message不是指定值！");
    }

    @Test
    public void testSetters() {
        JsonResponse<Integer> response = new JsonResponse<>();
        response.setCode("code");
        response.setMessage("message");
        response.setData(10);
        Assert.isTrue("code".equals(response.getCode()), "Setter错误！");
        Assert.isTrue("message".equals(response.getMessage()), "Setter错误！");
        Assert.isTrue(response.getData() == 10, "Setter错误！");
    }
}
