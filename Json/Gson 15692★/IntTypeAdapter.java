package com.actor.myandroidframework.utils.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * description: 解决Gson 把 "" 转 int 报错问题(反序列化阶段)
 * 在Application中配置:
 *
 * IntTypeAdapter intTypeAdapter = new IntTypeAdapter();
 * Gson gson = GsonUtils.getGson().newBuilder()
 *         .registerTypeAdapter(int.class, intTypeAdapter)
 *         .registerTypeAdapter(Integer.class, intTypeAdapter).create();
 * GsonUtils.setGsonDelegate(gson);
 *
 * @author : 李大发
 * date       : 2020/6/4 on 18:14
 * @version 1.0
 */
public class IntTypeAdapter extends TypeAdapter<Integer> {

    @Override
    public void write(JsonWriter out, Integer value) throws IOException {
        out.value(value);
    }

    @Override
    public Integer read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {// {age:null}
            in.nextNull();
            return null;
        }
        String result = in.nextString();
        if ("".equals(result)) return null;
        return Integer.parseInt(result);
    }
}
