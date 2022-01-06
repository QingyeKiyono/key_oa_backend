package com.key.oa.util;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Locale;

/**
 * @author 孙强
 * 对于JavaFaker的一些自定义功能
 * 因为这些功能都是在测试时调用，因此FakeValuesService对象单独生成即可
 */
public class FakeValue {
    /**
     * 生成密码组合，8到16位字母、数字、下划线
     *
     * @return 密码的明文
     */
    public static String password() {
        FakeValuesService fakeValuesService = new FakeValuesService(Locale.CHINA, new RandomService());
        return fakeValuesService.regexify("[0-9a-zA-Z_]{8,16}");
    }

    /**
     * 生成电子邮箱组合，详见文档
     *
     * @return 邮箱地址
     */
    public static String email() {
        FakeValuesService fakeValuesService = new FakeValuesService(Locale.ENGLISH, new RandomService());
        return fakeValuesService.regexify("(\\w+((-\\w+)|(\\.\\w+))*)\\+\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+");
    }

    /**
     * 生成身份证号，这里只支持18位
     *
     * @return 身份证号，不过在生成的时候可能会出现某个人在2072年出生的情况（出生日期：2072-10-04）
     */
    public static String identity() {
        FakeValuesService fakeValuesService = new FakeValuesService(Locale.ENGLISH, new RandomService());
        return fakeValuesService.regexify("[1-9][0-9]{5}(18|19|20)[0-9]{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)[0-9]{3}([0-9]|(X|x))");
    }
}
