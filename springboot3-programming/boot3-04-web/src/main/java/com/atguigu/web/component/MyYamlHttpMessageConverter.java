package com.atguigu.web.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author Roger
 * @Description
 * @create 2024-07-28
 */
public class MyYamlHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private ObjectMapper objectMapper = null; // 把物件轉成 YAML

    public MyYamlHttpMessageConverter() {
        // 告訴 springboot 這個 MessageConverter 支持哪種媒體類型
        super(new MediaType("text", "yaml", Charset.forName("UTF-8")));
        // 媒體類型
        // MediaType mediaType = new MediaType("application", "yaml", Charset.forName("UTF-8"));
        YAMLFactory factory = new YAMLFactory()
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        this.objectMapper = new ObjectMapper(factory);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        // 只要是物件類型，不是基本類型
        return true;
    }

    @Override // @RequestBody
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override // ResponseBody 把物件怎麼寫出去
    protected void writeInternal(Object methodReturnValue, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStream body = outputMessage.getBody();
        try {
            this.objectMapper.writeValue(body,methodReturnValue);
        } finally {
            body.close();
        }

        /*
            以下為 try-with-resource 寫法，自動關流
            try (OutputStream os = outputMessage.getBody()) {
                this.objectMapper.writeValue(os, methodReturnValue);
            }
         */

    }
}
