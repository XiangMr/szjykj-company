package com.dome.szjykjcompany.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: HuYi.Zhang
 * @create: 2018-04-24 17:20
 **/
@Slf4j
public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    public static <T> T parse(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }
    public static <T> T deserialize(ObjectMapper objectMapper, String json, TypeReference<T> typeRef) throws IOException {
                 return objectMapper.readValue(json, typeRef);
             }

    public static <E> List<E> toList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

   /* public static void main(String[] args) {
        User user =new User(1L,"Jack",19);
        String json="[{\"id\":1,\"name\":\"Jack\",\"age\":19}";
        //,{"id":1,"name":"J2312ack","age":19}]
        System.out.println("--->"+JsonUtils.toString(user));
        List<User> user1 = JsonUtils.nativeRead(json, new TypeReference<List<User>>(){});
        for (User user2 : user1) {
            System.out.println(user2.getName());
        }
        System.out.println("-------------------------------------");
        User parse = JsonUtils.parse(json, User.class);
        System.out.println(parse.getName());
    }*/
}
