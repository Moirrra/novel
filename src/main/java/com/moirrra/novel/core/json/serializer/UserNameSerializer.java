package com.moirrra.novel.core.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-21
 * @Description: 用户名序列化器 （用户名是敏感信息，不完全显示在页面上）
 * @Version: 1.0
 */

public class UserNameSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(s.substring(0, 4) + "****" + s.substring(8));
    }
}
