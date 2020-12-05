package com.yys.land.utils;

import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * description: 将json中日期自动转换成实体的Date
 * 在Application中配置:
 *
 * DateJsonDeserializer dateJsonDeserializer = new DateJsonDeserializer();
 * Gson gson = GsonUtils.getGson().newBuilder()
 *         .registerTypeAdapter(Date.class, dateJsonDeserializer)
 * GsonUtils.setGsonDelegate(gson);
 *
 * @author    : 李大发
 * date       : 2020/10/17 on 12:24
 */
public class DateJsonDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        boolean isJsonNull = json.isJsonNull();
        if (isJsonNull) return null;

        String string = json.getAsString();
        if ("".equals(string)) return null;

        Date date = TimeUtils.string2Date(string);//yyyy-MM-dd HH:mm:ss
        if (date == null) {
            date = TimeUtils.string2Date(string, "yyyy-MM-dd");
        }
        return date;//@Nullable
    }
}
