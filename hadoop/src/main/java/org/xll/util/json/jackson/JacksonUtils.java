package org.xll.util.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

import java.util.List;
import java.util.Map;

/**
 * @author 李昊哲
 * @version 1.0
 */
public class JacksonUtils {
  /**
   * 实例化 ObjectMapper
   */
  private final static ObjectMapper objectMapper = new ObjectMapper();

  /**
   * 将对象转换成json字符串。
   * <p>Title: pojoToJson</p>
   * <p>Description: </p>
   *
   * @param bean JavaBean对象
   * @return json格式字符串
   */
  public static String bean2json(Object bean) {
    try {
      return objectMapper.writeValueAsString(bean);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }


  /**
   * 将json结果集转化为对象
   *
   * @param jsonString json格式字符串
   * @param beanType   对象类型
   * @return JavaBean对象
   */
  public static <T> T json2bean(String jsonString, Class<T> beanType) {
    try {
      return objectMapper.readValue(jsonString, beanType);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 将json数据转换成pojo对象list
   * <p>Title: jsonToList</p>
   * <p>Description: </p>
   *
   * @param jsonString json格式字符串
   * @param beanType   对象类型
   * @return List对象
   */
  public static <T> List<T> json2list(String jsonString, Class<T> beanType) {
    JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
    try {
      return objectMapper.readValue(jsonString, javaType);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 将json数据转换成pojo对象list
   * <p>Title: jsonToList</p>
   * <p>Description: </p>
   *
   * @param jsonString json格式字符串
   * @param keyType    key对象类型
   * @param valueType  value对象类型
   * @return List对象
   */
  public static <K, V> Map<K, V> json2map(String jsonString, Class<K> keyType, Class<V> valueType) {
    MapType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, keyType, valueType);
    try {
      return objectMapper.readValue(jsonString, mapType);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
