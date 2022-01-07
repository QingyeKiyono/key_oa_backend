# 关于JsonResponse类和ResponseInfo类

通常情况下，一个restful后端返回的Json数据应包含三部分：**返回的状态码**、**正确/错误的提示信息**、
**返回的数据**（如果有数据的话）。那么此时JsonResponse类 就需要包含String code、String message和data。

```java
import lombok.*;

@Getter
@Setter
public class JsonResponse {
    private String code;
    private String message;
    private Object data;

    // 方法略
}
```

为了便于确定返回的数据具体类型，需要将JsonResponse类变成泛型类，data的类型就是T。 此时代码如下：

```java
import lombok.*;

@Getter
@Setter
public class JsonResponse<T> {
    private String code;
    private String message;
    private T a;

    // 方法略
}
```

而返回的状态码与提示信息几乎都是一一对应的，因此额外引入了一个枚举类型
**ResponseInfo**

```java
public enum ResponseInfo {

    /**
     * 没有异常，程序正常返回
     */
    OK("000");

    private final String code;

    ResponseInfo(String code) {
        this.code = code;
    }

    // 其余方法略
}
```

在之后的开发中，即使遇到少数需要更改message的情况，也可以在JsonResponse类 中重新设置message的内容。

## 关于为什么使用泛型类

[Java为什么要有泛型？](https://www.zhihu.com/question/315232030)
