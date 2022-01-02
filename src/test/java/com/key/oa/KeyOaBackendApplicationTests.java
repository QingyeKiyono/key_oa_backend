package com.key.oa;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SelectClasses(EmployeeControllerTest.class)
@Suite
class KeyOaBackendApplicationTests {

}
