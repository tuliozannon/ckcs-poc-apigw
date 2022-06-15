package com.ckcspoc.ckcspocapigw.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class RawJsonDeserializer extends StdDeserializer<RawJson> {

    public RawJsonDeserializer() {
        super(RawJson.class);
    }

    @Override
    public RawJson deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return readElement(treeNode, parser, context, false);
    }

    private RawJson readElement(TreeNode node, JsonParser parser, DeserializationContext context, boolean array) throws IOException {
        if (node.isArray()) {
            ArrayList<RawJson> jsons = new ArrayList<>();
            Iterator<JsonNode> elements = ((ArrayNode) node).elements();
            while (elements.hasNext()) {
                jsons.add(readElement(elements.next(), parser, context, true));
            }
            return new RawJson(jsons, (ObjectMapper) parser.getCodec());
        }
        if (node instanceof NullNode) {
            //avoid "null" string JSON, it can not be used for future transformations
            return array ? null : new RawJson("{}", (ObjectMapper) parser.getCodec());
        }
        return new RawJson(((ObjectMapper) parser.getCodec()).writeValueAsString(node),
                (ObjectMapper) parser.getCodec());
    }
}
