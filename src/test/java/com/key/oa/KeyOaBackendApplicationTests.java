package com.key.oa;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@Suite
@WebAppConfiguration
@SelectPackages("com.key.oa.util")
class KeyOaBackendApplicationTests {
}
