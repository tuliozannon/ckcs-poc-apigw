package com.ckcspoc.ckcspocapigw.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class RawJsonSerializer extends StdSerializer<RawJson> {

    public RawJsonSerializer() {
        super(RawJson.class);
    }

    @Override
    public void serialize(RawJson value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (value.getJson() == null) {
            if (value.getJsons() == null) {
                generator.writeNull();
            } else {
                generator.writeStartArray();
                for (RawJson v : value.getJsons()) {
                    generator.writeObject(v);
                }
                generator.writeEndArray();
            }
        } else {
            generator.writeRawValue(value.getJson());
        }
    }
}
