package store.streetvendor.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
        .configure(FAIL_ON_EMPTY_BEANS, false);

    public static <T> T toObject(String input, Class<T> toClass) {
        try {
            return OBJECT_MAPPER.readValue(input, toClass);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("역직렬화 중 에러가 발생하였습니다. input: (%s) toClass: (%s) message: (%s)", input, toClass.getSimpleName(), e.getMessage()));
        }
    }

    public static <T> String toJson(T input) {
        try {
            return OBJECT_MAPPER.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(String.format("직렬화 중 에러가 발생하였습니다. input: (%s) message: (%s)", input, e.getMessage()));
        }
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            JavaType listType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            ObjectReader reader = OBJECT_MAPPER.readerFor(listType);
            List<T> listValue = reader.readValue(toJsonNode(json));
            return listValue == null ? List.of() : listValue;
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("List 직렬화 중 에러가 발생하였습니다. input: <%s> message: <%s>", json, e.getMessage()));
        }
    }

    public static <T> List<T> toList(JsonNode jsonNode, Class<T> clazz) {
        try {
            JavaType listType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            ObjectReader reader = OBJECT_MAPPER.readerFor(listType);
            List<T> listValue = reader.readValue(jsonNode);
            return listValue == null ? List.of() : listValue;
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("List 직렬화 중 에러가 발생하였습니다. input: <%s> message: <%s>", jsonNode, e.getMessage()));
        }
    }

    public static JsonNode toJsonNode(String json) {
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("JsonNode 직렬화 중 에러가 발생하였습니다. input: <%s> message: <%s>", json, e.getMessage()));
        }
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

}
