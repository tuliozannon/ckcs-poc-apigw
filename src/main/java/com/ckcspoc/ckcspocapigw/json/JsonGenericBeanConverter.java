package com.ckcspoc.ckcspocapigw.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
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

    public <SOURCE, TARGET> TARGET convert(SOURCE source, Class<? extends TARGET> targetClazz) {
        if (source instanceof RawJson) {
            return ((RawJson) source).as(targetClazz);
        }
        return this.jsonMapper.convertValue(source, targetClazz);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <SOURCE> SOURCE merge(SOURCE source, Object update, boolean sourceFieldsOnly) {
        if (update instanceof String) {
            // Convert strings (possibly JSONs) to RawJson
            // It doesn't hurt primitive Strings
            // But it won't overwrite source with single string containing JSON text
            update = this.jsonMapper.readValue((String) update, RawJson.class);
        }
        RawJson src;
        if (source instanceof RawJson) {
            src = (RawJson) source;
        } else {
            src = this.jsonMapper.convertValue(source, RawJson.class);
        }
        Object updated = src.merge(update, sourceFieldsOnly)
                .as(source.getClass());
        if (updated instanceof RawJson) {
            return (SOURCE) updated;
        }
        return this.jsonMapper.updateValue(source, updated);
    }

}
