package com.manastudent.core.util;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JacksonUtil {

    private static Log log = LogFactory.get();

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 允许pojo中有在json串中不存在的字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Null 不显示
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(javaTimeModule);
    }

    /**
     * 对象转Json格式字符串
     *
     * @param obj 对象
     * @return Json格式字符串
     */
    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("Parse Object to String error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 对象转Json格式字符串(格式化的Json字符串)
     *
     * @param obj 对象
     * @return 美化的Json格式字符串
     */
    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("Parse Object to String error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 字符串转换为自定义对象
     *
     * @param str   要转换的字符串
     * @param clazz 自定义对象的class对象
     * @return 自定义对象
     */
    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error : {}", e.getMessage());
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<?> collectionClazz, Class<?>... elementClazzes) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClazz, elementClazzes);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            log.warn("Parse String to Object error : {}" + e.getMessage());
            return null;
        }
    }


    public static String parseString(String body, String field) {
        JsonNode node = null;
        try {
            node = objectMapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null)
                return leaf.asText();
        } catch (IOException e) {
            log.warn("Parse String to Object error : {}" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> parseStringList(String body, String field) {
        JsonNode node = null;
        try {
            node = objectMapper.readTree(body);
            JsonNode leaf = node.get(field);

            if (leaf != null)
                return objectMapper.convertValue(leaf, new TypeReference<List<String>>() {
                });
        } catch (IOException e) {
            log.warn("Parse String to Object error : {}" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
//
//	public static Integer parseInteger(String body, String field) {
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode node = null;
//		try {
//			node = mapper.readTree(body);
//			JsonNode leaf = node.get(field);
//			if (leaf != null)
//				return leaf.asInt();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static List<Integer> parseIntegerList(String body, String field) {
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode node = null;
//		try {
//			node = mapper.readTree(body);
//			JsonNode leaf = node.get(field);
//
//			if (leaf != null)
//				return mapper.convertValue(leaf, new TypeReference<List<Integer>>() {
//				});
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static Boolean parseBoolean(String body, String field) {
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode node = null;
//		try {
//			node = mapper.readTree(body);
//			JsonNode leaf = node.get(field);
//			if (leaf != null)
//				return leaf.asBoolean();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static Short parseShort(String body, String field) {
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode node = null;
//		try {
//			node = mapper.readTree(body);
//			JsonNode leaf = node.get(field);
//			if (leaf != null) {
//				Integer value = leaf.asInt();
//				return value.shortValue();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static Byte parseByte(String body, String field) {
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode node = null;
//		try {
//			node = mapper.readTree(body);
//			JsonNode leaf = node.get(field);
//			if (leaf != null) {
//				Integer value = leaf.asInt();
//				return value.byteValue();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static <T> T parseObject(String body, String field, Class<T> clazz) {
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode node = null;
//		try {
//			node = mapper.readTree(body);
//			node = node.get(field);
//			return mapper.treeToValue(node, clazz);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static Object toNode(String json) {
//		if (json == null) {
//			return null;
//		}
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			JsonNode jsonNode = mapper.readTree(json);
//			return jsonNode;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

}
