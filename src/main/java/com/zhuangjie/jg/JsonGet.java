package com.zhuangjie.gather;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
class JsonGetResources {
    private String path;

    public JsonGetResources load(String path) {
        this.path = path;
        return this;
    }

    public String read() throws IOException {
        InputStream resourceAsStream = _4_LinkedHashSet.class.getResourceAsStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
        char[] chars = new char[1024];
        int readLen = 0;
        String text = "";
        while ((readLen = inputStreamReader.read(chars)) != -1) {
            String unitStr = new String(chars, 0, readLen);
            text += unitStr;
        }
        return text;
    }

}


class JsonNode {
    private Object parent;
    private String key;
    private Object data;
    private Class type;
    private List<JsonNode> fruits;

    public JsonNode() {
    }

    public JsonNode(Object parent, String key, Object data, Class type, List<JsonNode> fruits) {
        this.parent = parent;
        this.key = key;
        this.data = data;
        this.type = type;
        this.fruits = fruits;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public List<JsonNode> getFruits() {
        return fruits;
    }

    public void setFruits(List<JsonNode> fruits) {
        this.fruits = fruits;
    }

    @Override
    public String toString() {
        return key;
    }
}



public class JsonGet {

    private JsonNode jsonTree;
    private JsonNode pos;
    private class Null {
    }
    public JsonGet(String json) {
        this.jsonTree = new JsonNode();
        jsonTree.setKey("^");
        Object initJsonObject = JSON.parse(json);
        jsonTree.setData(initJsonObject);
        jsonTree.setType(nGetClass(initJsonObject));
        loadTrue((JSONObject) initJsonObject, jsonTree);
        this.pos = jsonTree;

    }

    //构建我的json树
    private static void loadTrue(Object jsonObject, JsonNode jsonNode) {
        if (jsonNode.getFruits() == null) {
            jsonNode.setFruits(new ArrayList<JsonNode>());
        }

        if (jsonObject instanceof char[] || jsonObject instanceof Number) {
            JsonNode childNode = new JsonNode();
            childNode.setKey(null);
            childNode.setData(jsonNode);
            childNode.setType(nGetClass(jsonObject));
            childNode.setFruits(null);
            if (jsonNode instanceof JsonNode) {
                childNode.setParent(jsonNode);
            }

            jsonNode.getFruits().add(childNode);
            return;
        }
        if (jsonObject instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) jsonObject;
            for (Map.Entry entry : map.entrySet()) {
                JsonNode childNode = new JsonNode();
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                childNode.setKey(key);
                childNode.setData(value);
                childNode.setType(nGetClass(value));
                childNode.setFruits(null);
                if (jsonNode instanceof JsonNode) {
                    childNode.setParent(jsonNode);
                }

                jsonNode.getFruits().add(childNode);
                if (value instanceof Map || value instanceof List) {
                    loadTrue(value, childNode);
                }
            }
        } else if (jsonObject instanceof List) {
            List<Object> list = (List<Object>) jsonObject;
            int index = 0;
            for (Object obj : list) {
                JsonNode childNode = new JsonNode();
                childNode.setKey(index + "");
                childNode.setData(obj);
                childNode.setType(nGetClass(obj));
                childNode.setParent(jsonNode);
                childNode.setFruits(null);

                jsonNode.getFruits().add(childNode);
                if (obj instanceof Map || obj instanceof List) {
                    loadTrue(obj, childNode);
                }
                index++;

            }
        }


    }

    //判断类型
    private static Class nGetClass(Object obj) {
        if (obj instanceof Map) {
            return Map.class;
        } else if (obj instanceof List) {
            return List.class;
        } else if (obj instanceof String) {
            return String.class;
        } else if (obj instanceof Boolean) {
            return Boolean.class;
        } else if (obj instanceof Number) {
            return Number.class;
        } else if (obj == null) {
            return Null.class;
        }
        return Object.class;
    }

    public JsonGet down(int index) {
        return down(index + "");
    }

    public JsonGet down(String key) {
        List<JsonNode> fruits = pos.getFruits();
        for (JsonNode jn : fruits) {
            if (jn.getKey().equals(key)) {
                pos = jn;
                return this;
            }
        }
        return null;
    }

    public JsonGet up(String key) {

        JsonNode parent = pos;
        while (parent != null) {
            String k = parent.getKey();
            if (k.equals(key)) {
                if (parent != null) {
                    pos =  parent;
                }

                return this;
            }
            Object tmp = parent.getParent();
            if (tmp != null) {
                parent = (JsonNode)tmp;
            }
        }

        return null;
    }

    public String get() {
        return JSON.toJSONString(pos.getData());
    }
    public String get(int key) {
        return get(key+"");
    }
    public String get(String key) {
        List<JsonNode> fruits = pos.getFruits();
        for (JsonNode jn : fruits) {
            if (jn.getKey().equals(key)) {
                return JSON.toJSONString(jn.getData());
            }
        }
        return null;

    }

    //将json字符串转为map
    private static Map<String, String> parseMap(String json) {
        Object parse = JSON.parse(json);
        Map<String, JSONObject> maps = (Map<String, JSONObject>) parse;
        Map<String, String> json_map = new HashMap();
        //由 Map<String,JSONObject> 转为 Map<String, String>
        for (Map.Entry entry : maps.entrySet()) {
            json_map.put(entry.getKey().toString(), entry.getValue() + "");
        }
        return json_map;
    }

    private List<Object> parseList(String json) {
        List<Object> parse = (List<Object>) JSON.parse(json);
        return parse;
    }





}