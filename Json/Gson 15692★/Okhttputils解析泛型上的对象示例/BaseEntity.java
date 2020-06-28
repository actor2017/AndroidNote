package com.zhy.sample_okhttp;

/**
 * Created by Git@Smark on 2016/7/21.
 *
 * https://github.com/hongyangAndroid/okhttputils/pull/265
 * https://github.com/hongyangAndroid/okhttputils/blob/416628088c65ba4872a37214a406de8a043b65c9/sample-okhttp/src/main/java/com/zhy/sample_okhttp/BaseEntity.java
 */
public class BaseEntity<T> {
    public int Code;
    public String msg;
    public T data;
}