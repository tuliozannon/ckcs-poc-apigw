package com.ckcspoc.ckcspocapigw.json;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
public class JsonContainer {

    @JsonIgnore
    private Map<String, Object> data = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getData() {
        return this.data;
    }

    @JsonAnySetter
    public JsonContainer add(String field, RawJson part) {
        this.data.put(field, part);
        return this;
    }

    public JsonContainer add(String field, Object part) {
        this.data.put(field, part);
        return this;
    }

    public JsonContainer add(Map<String, Object> part) {
        this.data.putAll(part);
        return this;
    }

    public JsonContainer add(JsonContainer part) {
        return add(part.getAsMap());
    }

    public RawJson getRaw(String field) {
        return (RawJson) this.data.get(field);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String field) {
        return (T) this.data.get(field);
    }

    public JsonContainer getFields(String... fields) {
        JsonContainer result = new JsonContainer();
        Map<String, Object> allFields = getAsMap();
        Arrays.stream(fields)
                .filter(allFields::containsKey)
                .forEach(f -> result.add(f, allFields.get(f)));
        return result;
    }

    public JsonContainer rename(String from, String to) {
        return rename(from, to, false);
    }

    public JsonContainer rename(String from, String to, boolean overwrite) {
        if (overwrite || !this.data.containsKey(to) && this.data.containsKey(from)) {
            this.add(to, (Object) get(from));
        }
        return remove(from);
    }

    public JsonContainer remove(String... fields) {
        Arrays.stream(fields)
                .forEach(f -> this.data.remove(f));
        return this;
    }

    @JsonIgnore
    public Map<String, Object> getAsMap() {
        return this.data;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String field, Class<T> clazz) {
        Object o = get(field);
        if (o instanceof RawJson) {
            return ((RawJson) o).as(clazz);
        }
        return (T) o;
    }
}
