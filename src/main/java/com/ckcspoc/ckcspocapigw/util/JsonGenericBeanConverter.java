package com.ckcspoc.ckcspocapigw.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

@Component
public class JsonGenericBeanConverter {
    private ObjectMapper jsonMapper;

    public JsonGenericBeanConverter(){
        this.jsonMapper = new ObjectMapper();
        this.jsonMapper.registerModule(new JavaTimeModule());
    }

    public <T> T as(String source, Class<T> targetClazz){
        try{
            return this.jsonMapper.readValue(source, targetClazz);
            //return this.jsonMapper.convertValue(source, targetClazz);
        }
        catch (Exception e){
            return null;
        }
    }
}
