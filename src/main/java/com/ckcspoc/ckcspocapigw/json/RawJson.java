package com.ckcspoc.ckcspocapigw.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.springframework.util.ClassUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(exclude = "mapper")
@JsonSerialize(using = RawJsonSerializer.class)
@JsonDeserialize(using = RawJsonDeserializer.class)
public class RawJson {

    private String json;

    private List<RawJson> jsons;

    private ObjectMapper mapper;

    private boolean multiple;

    public RawJson(String json, ObjectMapper mapper) {
        this.json = json;
        this.mapper = mapper;
        this.multiple = false;
    }

    public RawJson(List<RawJson> jsons, ObjectMapper mapper) {
        this.jsons = jsons;
        this.mapper = mapper;
        this.multiple = true;
    }

    public RawJson(RawJson source) {
        this(source.json, source.jsons, source.mapper);
    }

    private RawJson(String json, List<RawJson> jsons, ObjectMapper mapper) {
        this.json = json;
        this.multiple = false;
        if (jsons != null) {
            this.jsons = jsons.stream().map(RawJson::copy).collect(Collectors.toList());
            this.multiple = true;
        }
        this.mapper = mapper;
    }

    public RawJson copy() {
        return new RawJson(this);
    }

    public <T> T as(Class<T> clazz) {
        return readValue(this.json, clazz);
    }

    public <T> T as(TypeReference<T> typeReference) {
        return readValue(this.json, typeReference);
    }

    public <T> Set<T> asSet(Class<T> clazz) {
        return (Set<T>) asCollection(clazz, HashSet::new);
    }

    public <T> Set<T> asSet(TypeReference<T> typeReference) {
        return (Set<T>) asCollection(typeReference, HashSet::new);
    }

    public <T> List<T> asList(Class<T> clazz) {
        return (List<T>) asCollection(clazz, ArrayList::new);
    }

    public <T> List<T> asList(TypeReference<T> typeReference) {
        return (List<T>) asCollection(typeReference, ArrayList::new);
    }

    public <T> Collection<T> asCollection(Class<T> clazz, Supplier<? extends Collection<T>> collectionFactory) {
        Collection<T> collection = collectionFactory.get();
        if (this.jsons != null) {
            for (RawJson value : this.jsons) {
                collection.add(value == null ? null : value.as(clazz));
            }
        } else {
            collection.add(as(clazz));
        }
        return collection;
    }

    public <T> Collection<T> asCollection(TypeReference<T> typeReference, Supplier<? extends Collection<T>> collectionFactory) {
        Collection<T> collection = collectionFactory.get();
        if (this.jsons != null) {
            for (RawJson value : this.jsons) {
                collection.add(value == null ? null : value.as(typeReference));
            }
        } else {
            collection.add(as(typeReference));
        }
        return collection;
    }

    public RawJson update(Object data) {
        if (data == null) {
            //rewrite json with empty JSON
            this.json = "{}";
            this.jsons = null;
        } else if (data instanceof RawJson) {
            this.json = ((RawJson) data).getJson();
            this.jsons = ((RawJson) data).getJsons();
        } else if (data instanceof Iterable) {
            this.jsons = new ArrayList<>();
            for (Object obj : (Iterable) data) {
                this.jsons.add(new RawJson(writeJson(obj), this.mapper));
            }
            this.json = null;
        } else if (ClassUtils.isPrimitiveOrWrapper(data.getClass())
                || data instanceof Date
                || data instanceof Instant) {
            this.json = writeJson(data);
            this.jsons = null;
        } else {
            this.json = writeJson(data);
            this.jsons = null;
        }
        this.multiple = this.jsons != null;
        return this;
    }

    public RawJson merge(Object data) {
        return merge(data, false);
    }

    public RawJson merge(Object data, boolean sourceFieldsOnly) {
        if (data == null) {
            //do nothing
            return this;
        } else if (data instanceof Iterable) {
            // tricky merging arrays
            // Simple approach: Rewrite array with items which are merged themselves,
            // If items don't have source, then just write them as new JSONs
            // In other words, we still overwrite arrays.
            this.jsons = new ArrayList<>();
            for (Object obj : (Iterable) data) {
                this.jsons.add(new RawJson(writeJson(obj), this.mapper));
            }
            this.json = null;
        } else if (ClassUtils.isPrimitiveOrWrapper(data.getClass())
                || data instanceof Date
                || data instanceof Instant) {
            //Simple types are just overwritten
            this.json = writeJson(data);
            this.jsons = null;
        } else {
            // merge JSONs
            // Simple approach, merge only top level properties, without deep traversing through the JSON structure
            JsonContainer source = Optional.ofNullable(this.json).map(j -> readValue(j, JsonContainer.class))
                    .orElseGet(JsonContainer::new);
            Map<String, Object> sourceMap = source.getAsMap();
            Map<String, Object> overrideMap = readValue(writeJson(data), JsonContainer.class).getAsMap();
            if (sourceFieldsOnly) {
                overrideMap.entrySet().stream()
                        .filter(entry -> sourceMap.containsKey(entry.getKey()))
                        .forEach(entry -> sourceMap.put(entry.getKey(), entry.getValue()));
            } else {
                sourceMap.putAll(overrideMap);
            }
            this.json = writeJson(source);
            this.jsons = null;
        }
        this.multiple = this.jsons != null;
        return this;
    }

    @SneakyThrows
    public RawJson stripNulls() {
        if (this.json != null) {
            JsonNode node = this.mapper.readTree(this.json);
            stripNulls(node);
            this.json = this.mapper.writeValueAsString(node);
        } else if (this.jsons != null) {
            this.jsons.forEach(RawJson::stripNulls);
        }
        return this;
    }

    public RawJson extract(String... fields) {
        RawJson copy = copy();
        return copy.update(copy.as(JsonContainer.class).getFields(fields));
    }

    @Override
    public String toString() {
        return Optional.ofNullable(this.json)
                .orElseGet(() -> String.valueOf(this.jsons));
    }

    public boolean isMultiple() {
        return this.multiple;
    }

    @SneakyThrows
    private String writeJson(Object obj) {
        return this.mapper.writer().without(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .writeValueAsString(obj);
    }

    @SneakyThrows
    private <T> T readValue(String source, Class<T> clazz) {
        return readValue(source, this.mapper.constructType(clazz));
    }

    @SneakyThrows
    private <T> T readValue(String source, TypeReference<T> typeReference) {
        return readValue(source, this.mapper.getTypeFactory().constructType(typeReference));
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private <T> T readValue(String source, JavaType type) {
        if (type.getRawClass().isAssignableFrom(RawJson.class)) {
            return (T) type.getRawClass().cast(this);
        }
        T bean = this.mapper.readValue(source, type);
        return bean;
    }

    private void stripNulls(JsonNode node) {
        Iterator<JsonNode> it = node.iterator();
        while (it.hasNext()) {
            JsonNode child = it.next();
            if (child.isNull()) {
                it.remove();
            } else {
                stripNulls(child);
            }
        }
    }
}
