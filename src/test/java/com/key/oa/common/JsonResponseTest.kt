package com.key.oa.common

import com.key.oa.common.JsonResponse.Companion.error
import com.key.oa.common.JsonResponse.Companion.success
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JsonResponseTest : WithAssertions {
    @Test
    fun testConstructors() {
        // 无参数构造函数，三个属性都有默认值
        var response = success<Int>()
        var assertions = SoftAssertions()
        assertions.assertThat(response.code)
            .`as`("Code should be '00000'.")
            .isEqualTo("00000")
        assertions.assertThat(response.message)
            .`as`("Message should be 'OK'.")
            .isEqualTo("OK")
        assertions.assertThat(response.data)
            .`as`("Data should be null.")
            .isNull()
        assertions.assertAll()

        // 带一个参数，其中code和message具有默认值，data手动指定
        response = success(1)
        assertions = SoftAssertions()
        assertions.assertThat(response.code)
            .`as`("Code should be '00000'.")
            .isEqualTo("00000")
        assertions.assertThat(response.message)
            .`as`("Message should be 'OK'.")
            .isEqualTo("OK")
        assertions.assertThat(response.data)
            .`as`("Data should be 1.")
            .isEqualTo(1)
        assertions.assertAll()

        // 带两个参数，data是null，其余都需要手动指定
        response = error("1", "123")
        assertions = SoftAssertions()
        assertions.assertThat(response.code)
            .`as`("Code should be '1'.")
            .isEqualTo("1")
        assertions.assertThat(response.message)
            .`as`("Message should be '123'.")
            .isEqualTo("123")
        assertions.assertThat(response.data)
            .`as`("Data should be null.")
            .isNull()
        assertions.assertAll()

        // 带三个参数，三个属性全部需要手动指定
        response = error("2", "234", 1)
        assertions = SoftAssertions()
        assertions.assertThat(response.code)
            .`as`("Code should be '2'.")
            .isEqualTo("2")
        assertions.assertThat(response.message)
            .`as`("Message should be '234'.")
            .isEqualTo("234")
        assertions.assertThat(response.data)
            .`as`("Data should be 1.")
            .isEqualTo(1)
        assertions.assertAll()
    }
}